package org.ssor.boss.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.ssor.boss.core.entity.Account;
import org.ssor.boss.core.entity.AccountType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class LambdaHandler implements RequestHandler<S3Event, Void>
{

  private static final Logger logger = Logger.getLogger(LambdaHandler.class.getName());
  private Connection conn = null;

  @Override
  public Void handleRequest(S3Event s3Event, Context context)
  {

    String srcBucket = s3Event.getRecords().get(0).getS3().getBucket().getName();
    String srcKey = s3Event.getRecords().get(0).getS3().getObject().getUrlDecodedKey();

    logger.info(() -> "Bucket Name: " + srcBucket);
    logger.info(() -> "Bucket Key: " + srcKey);

    AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
    S3Object s3Object = s3Client.getObject(new GetObjectRequest(
        srcBucket, srcKey));
    InputStream inputStream = s3Object.getObjectContent();

    String text = new BufferedReader(
        new InputStreamReader(inputStream, StandardCharsets.UTF_8))
        .lines()
        .collect(Collectors.joining("\n"));

    logger.info(() -> text);

    List<String> columns = Arrays.stream(text.split("\n")).collect(Collectors.toList());

    for (String column : columns)
    {
      List<String> row = Arrays.stream(column.split(",")).collect(Collectors.toList());
      Account account = new Account();
      account.setId(Long.parseLong(row.get(0)));
      account.setAccountType(AccountType.values()[Integer.parseInt(row.get(1))]);
      account.setBranchId(Integer.parseInt(row.get(2)));
      account.setName(Optional.ofNullable(row.get(3)).orElse("Generic Account"));
      account.setBalance(Float.parseFloat(row.get(4)));
      account.setOpened(LocalDate.parse(row.get(5)));

      if (null != row.get(6) && !row.get(6).equals("null"))
        account.setClosed(LocalDate.parse(row.get(6)));

      account.setConfirmed("true".equalsIgnoreCase(row.get(7)));
      account.setActive("true".equalsIgnoreCase(row.get(8)));

      try
      {
        logger.info("Getting remote connection with connection string from environment variables.");
        conn = getRemoteConnection();
        logger.info("Remote connection successful.");
        logger.info("Inserting account");
        insert(account);
      }
      catch (ClassNotFoundException | SQLException e)
      {
        logger.info("Remote connection failed.");
        e.printStackTrace();
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

    return null;
  }

  private Boolean insert(Account account) throws SQLException
  {
    PreparedStatement preparedStatement = conn.prepareStatement(
        "INSERT INTO boss.account (id, type_id, branch_id, name, balance, opened, closed, confirmed, active) " +
        "values (?,?,?,?,?,?,?,?,?)");
    preparedStatement.setLong(1, account.getId());
    preparedStatement.setInt(2, account.getAccountType().index());
    preparedStatement.setInt(3, account.getBranchId());
    preparedStatement.setString(4, account.getName());
    preparedStatement.setFloat(5, account.getBalance());
    preparedStatement.setDate(6, Date.valueOf(account.getOpened()));
    if (null != account.getClosed())
      preparedStatement.setDate(7, Date.valueOf(account.getClosed()));
    else
      preparedStatement.setNull(7, Types.DATE);
    preparedStatement.setBoolean(8, account.getConfirmed());
    preparedStatement.setBoolean(9, account.getActive());

    return preparedStatement.execute();
  }

  private static Connection getRemoteConnection() throws ClassNotFoundException, SQLException
  {

    String dbName = "boss";
    String userName = "admin";
    String password = "password";
    String hostname = "test-database.cdlblimwkjhs.us-east-2.rds.amazonaws.com";
    String port = "3306";
    String jdbcUrl =
        "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
    Class.forName("com.mysql.jdbc.Driver");
    Connection con = DriverManager.getConnection(jdbcUrl);
    return con;
  }
}
