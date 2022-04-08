package com.startup.thatstory_multimedia.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.startup.thatstory_multimedia.bean.AWSS3Client;
import com.startup.thatstory_multimedia.customexception.CustomS3Exception;
import com.startup.thatstory_multimedia.dto.ResponseDTO;
import com.startup.thatstory_multimedia.services.AWSService;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.model.S3Exception;

@RestController
public class thatstory_multimediacontroller {

	@Autowired
	AWSService awsservice;
	
	@Autowired
	AWSS3Client awss3client;
	
	@PostMapping("/multimedia")
   public ResponseEntity<ResponseDTO> postMultimedia(MultipartFile file) throws S3Exception, AwsServiceException, SdkClientException, IOException, CustomS3Exception
   {
	   
		ResponseDTO responsedto = awsservice.upload(file, awss3client);
		return ResponseEntity.ok(responsedto);
   }
	 
	@DeleteMapping("/multimedia")
	   public ResponseEntity<ResponseDTO> deleteMultimedia(@RequestParam String filename) throws S3Exception, AwsServiceException, SdkClientException, IOException, CustomS3Exception
	   {
		   
			ResponseDTO responsedto = awsservice.delete(filename, awss3client);
			return ResponseEntity.ok(responsedto);
	   }
	
	@PutMapping("/multimedia")
	   public ResponseEntity<ResponseDTO> updateMultimedia(MultipartFile file) throws S3Exception, AwsServiceException, SdkClientException, IOException
	   {
		   
			ResponseDTO responsedto = awsservice.upload(file, awss3client);
			return ResponseEntity.ok(responsedto);
	   }
	
	@ExceptionHandler(com.startup.thatstory_multimedia.customexception.CustomS3Exception.class)
	public ResponseEntity<String> HandleException(CustomS3Exception exception)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("S3 Object not found");
	}
}
