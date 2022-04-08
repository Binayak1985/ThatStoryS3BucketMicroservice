package com.startup.thatstory_multimedia.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.startup.thatstory_multimedia.bean.AWSS3Client;
import com.startup.thatstory_multimedia.customexception.CustomS3Exception;
import com.startup.thatstory_multimedia.dto.ResponseDTO;
import com.startup.thatstory_multimedia.properties.S3ClientProperties;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
@EnableConfigurationProperties(S3ClientProperties.class)
public class AWSService {

	@Autowired
	S3ClientProperties s3clientproperties;

	String BUCKET = "mytestbucket";
	
	public ResponseDTO upload(MultipartFile file, AWSS3Client awss3client) throws S3Exception, AwsServiceException, SdkClientException, IOException,S3Exception {

		InputStream inputStream = file.getInputStream();
		String filename = file.getOriginalFilename();
		S3Client s3client = awss3client.getS3Client(s3clientproperties);
		PutObjectRequest request = PutObjectRequest.builder().bucket(BUCKET).key(filename).build();

		PutObjectResponse s3response = s3client.putObject(request, RequestBody.fromInputStream(inputStream, inputStream.available()));

		if(s3response.sdkHttpResponse().isSuccessful())
		{
		return ResponseDTO.builder().bucketname(BUCKET).filename(filename).timestamp(new Date()).build();
		}
		else
		{
		throw new CustomS3Exception("S3 error: Unable to upload message. Contact Administrator");	
		}
	}

	public ResponseDTO delete(String filename, AWSS3Client awss3client) throws S3Exception, AwsServiceException, SdkClientException, IOException {
		S3Client s3client = awss3client.getS3Client(s3clientproperties);
		DeleteObjectRequest request = DeleteObjectRequest.builder().bucket(BUCKET).key(filename).build();
		DeleteObjectResponse s3response = s3client.deleteObject(request);	

		if(s3response.sdkHttpResponse().isSuccessful())
		{
			return ResponseDTO.builder().bucketname(BUCKET).filename(filename).timestamp(new Date()).build();
		}
		else
		{
		throw new CustomS3Exception("S3 error: Unable to upload message. Contact Administrator");	
		}

	}

}
