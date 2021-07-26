package org.ssor.boss.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LambdaHandler implements RequestHandler<S3Event, Void>
{

  LambdaLogger logger;
  private Connection conn = null;

  @Override
  public Void handleRequest(S3Event s3Event, Context context)
  {
    logger = context.getLogger();
    String srcBucket = s3Event.getRecords().get(0).getS3().getBucket().getName();
    String srcKey = s3Event.getRecords().get(0).getS3().getObject().getUrlDecodedKey();

    logger.log("Bucket Name: " + srcBucket);
    logger.log("Bucket Key: " + srcKey);

    AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
    S3Object s3Object = s3Client.getObject(new GetObjectRequest(srcBucket, srcKey));
    InputStream inputStream = s3Object.getObjectContent();

    String text = new BufferedReader(
        new InputStreamReader(inputStream, StandardCharsets.UTF_8))
        .lines()
        .collect(Collectors.joining("\n"));

    logger.log("Uploaded CSV File Content: " + text);

    List<String> rows = Arrays.stream(text.split("\n")).collect(Collectors.toList());

    try
    {
      logger.log("Getting remote connection with connection string from environment variables.");
      conn = getRemoteConnection();
      logger.log("Remote connection successful.");
    }
    catch (ClassNotFoundException | SQLException e)
    {
      logger.log("Remote connection failed.");
      e.printStackTrace();
    }

    for (String row : rows)
    {
      try
      {
        String validRow = validateAndFormatForQuery(row);
        logger.log("Inserting [" + validRow + "]");
        Boolean status = insert(validRow);
        logger.log("Insert returned: " + status);
      }
      catch (RowInvalidException e)
      {
        logger.log(e.getMessage());
        // TODO: SES
      }
    }

    try
    {
      conn.close();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }

    // TODO: SES
    return null;
  }

  private Boolean insert(String row)
  {
    String query = "INSERT INTO boss.account " +
                   "(id, type_id, branch_id, name, balance, opened, closed, confirmed, active) " +
                   "VALUES (" + row + ")";
    try (PreparedStatement preparedStatement = conn.prepareStatement(query))
    {
      logger.log("Preparing query: " + query);
      preparedStatement.execute();
      return true;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      return false;
    }
  }

  private static Connection getRemoteConnection() throws ClassNotFoundException, SQLException
  {

    String dbName = System.getenv("DB_NAME");
    String userName = System.getenv("DB_USERNAME");
    String password = System.getenv("DB_PASSWORD");
    String hostname = System.getenv("DB_HOSTNAME");
    String port = System.getenv("DB_PORT");
    String jdbcUrl =
        "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
    Class.forName(System.getenv("DB_DRIVER"));
    return DriverManager.getConnection(jdbcUrl);
  }

  private String validateAndFormatForQuery(String row) throws RowInvalidException
  {
    boolean valid;
    List<String> values = Arrays.stream(row.split(",")).collect(Collectors.toList());
    for (int i = 0; i < values.size(); i++)
    {
      switch (i)
      {
        case 0:
        case 1:
        case 2:
          valid = values.get(i).matches("\\d+");
          break;
        case 3:
          valid = values.get(i).endsWith("\"") && values.get(i).startsWith("\"");
          if (!valid)
            values.set(i, "\"" + values.get(i) + "\"");
          valid = true;
          break;
        case 5:
        case 6:
          if (!values.get(i).equals("null"))
            values.set(i, "STR_TO_DATE('" + values.get(i) + "', '%Y-%m-%d')");
          valid = true;
          break;
        default:
          valid = true;
      }
      if (!valid)
      {
        throw new RowInvalidException(row);
      }
    }
    return String.join(",", values);
  }

  private static class RowInvalidException extends Exception
  {
    public RowInvalidException(String row)
    {
      super("Row supplied is invalid: " + row);
    }
  }
}
