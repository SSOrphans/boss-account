package org.ssor.boss.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class LambdaHandler implements RequestHandler<S3Event, Void>
{
  @Override
  public Void handleRequest(S3Event s3Event, Context context)
  {

    Logger logger = Logger.getLogger(LambdaHandler.class.getName());
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

    return null;
  }
}
