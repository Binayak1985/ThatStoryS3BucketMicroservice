package com.startup.thatstory_multimedia.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.startup.thatstory_multimedia.awsutility.S3Util;
import com.startup.thatstory_multimedia.bean.AWSS3Client;
import com.startup.thatstory_multimedia.customexception.CustomS3Exception;
import com.startup.thatstory_multimedia.dto.ResponseDTO;
import com.startup.thatstory_multimedia.properties.S3ClientProperties;



@Service
@EnableConfigurationProperties(S3ClientProperties.class)
public class AWSService {

	@Autowired
	S3ClientProperties s3clientproperties;

	
	public ResponseDTO upload(String bucket_name, String foldername, MultipartFile file, AWSS3Client awss3client) throws IOException {

//		S3Client s3client = awss3client.getS3Client(s3clientproperties);
		System.out.println(" folder name is "+foldername);
		
		if(S3Util.createBucket(bucket_name)!=null)
		{
		
		InputStream inputStream = file.getInputStream();
		
//		PutObjectRequest request = PutObjectRequest.bucket(bucket_name).key(foldername+"/"+file.getOriginalFilename()).build();
		   Map<String, String> metadata = new HashMap<>();
           metadata.put("src", "UI");

		boolean isSuccessful = S3Util.addObject(bucket_name,foldername+"/"+file.getOriginalFilename() , file.getInputStream());

		if(isSuccessful)
		{
		
		return ResponseDTO.builder().bucketname(bucket_name).filename(foldername).timestamp(new Date()).build();
		}
		else
		{
		throw new CustomS3Exception("S3 error: Unable to upload message. Contact Administrator");	
		}
		}
		else
		{
			throw new CustomS3Exception("S3 error: Unable to create S3 bucket to upload file. Contact Administrator");
		}
	}

	public ResponseDTO delete(String bucketname, String foldername, String filename, AWSS3Client awss3client) {
//		   AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
//                   .withCredentials(new ProfileCredentialsProvider())
//                   .withRegion(Regions.US_EAST_1)
//                   .build();
//		DeleteObjectRequest request = DeleteObjectRequest.builder().bucket(bucketname).key(foldername+"/"+filename).build();
		boolean isDeleted= S3Util.deleteObject(bucketname,foldername+"/"+filename);	

		if(isDeleted)
		{
			return ResponseDTO.builder().bucketname(bucketname).filename(filename).timestamp(new Date()).build();
		}
		else
		{
		throw new CustomS3Exception("S3 error: Unable to upload message. Contact Administrator");	
		}

	}

	
}
