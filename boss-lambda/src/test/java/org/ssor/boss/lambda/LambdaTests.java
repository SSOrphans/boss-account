package org.ssor.boss.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.serialization.PojoSerializer;
import com.amazonaws.services.lambda.runtime.serialization.events.LambdaEventSerializers;
import com.amazonaws.services.s3.model.S3Object;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class LambdaTests
{

  LambdaHandler lambdaHandler;
  @Mock
  PreparedStatement preparedStatement;
  @Mock
  Connection conn;
  @Mock
  Context context;
  @Mock
  LambdaLogger logger;
  @Mock
  S3Object s3Object;


  @Before
  public void setUp()
  {
    MockitoAnnotations.initMocks(this);
    lambdaHandler = new LambdaHandler();
  }

  @Test
  public void testParseS3Content()
  {
    String raw = "398749873498235,1,1,Lambda Saving A,12.36,2019-08-19,2020-09-03,true,false\n" +
                 "420391249212312,2,1,Lambda Checking B,9999.99,2020-12-19,null,true,true";

    S3Object dummyObject = new S3Object();
    InputStream is = new ByteArrayInputStream(raw.getBytes(StandardCharsets.UTF_8));
    dummyObject.setObjectContent(is);

    assertEquals(raw, lambdaHandler.parseS3Content(dummyObject));
  }

  @Test
  public void testGetS3Object()
  {
    String bucket = "ssor-boss-data-upload";
    String key = "fourth.csv";

    S3Object receivedObject = lambdaHandler.getS3Object(bucket, key);
    assertNotNull(receivedObject);
  }

  @Test
  public void testInsert() throws SQLException, ClassNotFoundException, LambdaHandler.RowInvalidException
  {
    String row = lambdaHandler
        .validateAndFormatForQuery("420391249212312,2,1,Lambda Checking B,9999.99,2020-12-19,null,true,true");
    Mockito.when(conn.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);
    Mockito.when(preparedStatement.execute()).thenReturn(false);

    assertTrue(lambdaHandler.insert(row, conn));

  }

  @Test
  public void testHandler() throws IOException, SQLException, ClassNotFoundException
  {
    LambdaHandler lambdaHandlerSpy = Mockito.spy(lambdaHandler);
    Path eventFilePath = Path.of("src/test/resources/event.json");
    String eventJson = Files.readString(eventFilePath);
    PojoSerializer<S3Event> s3EventSerializer = LambdaEventSerializers
        .serializerFor(S3Event.class, ClassLoader.getSystemClassLoader());
    S3Event s3Event = s3EventSerializer.fromJson(eventJson);

    Mockito.when(context.getLogger()).thenReturn(logger);
    Mockito.doReturn(s3Object).when(lambdaHandlerSpy).getS3Object(Mockito.anyString(), Mockito.anyString());
    Mockito.doReturn("").when(lambdaHandlerSpy).parseS3Content(Mockito.any());
    Mockito.doReturn(conn).when(lambdaHandlerSpy).getConnection();
    lambdaHandlerSpy.handleRequest(s3Event, context);

    verify(lambdaHandlerSpy).handleRequest(s3Event, context);
  }

}
