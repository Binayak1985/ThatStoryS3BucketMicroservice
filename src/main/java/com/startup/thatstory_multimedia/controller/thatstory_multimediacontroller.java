package com.startup.thatstory_multimedia.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.reactive.function.client.WebClient;

import com.startup.thatstory_multimedia.bean.AWSS3Client;
import com.startup.thatstory_multimedia.customexception.CustomS3Exception;
import com.startup.thatstory_multimedia.dto.ResponseDTO;
import com.startup.thatstory_multimedia.properties.S3ClientProperties;
import com.startup.thatstory_multimedia.services.AWSService;


@RestController
public class thatstory_multimediacontroller {

	@Autowired
	AWSService awsservice;

	@Autowired
	AWSS3Client awss3client;

	@Autowired
	S3ClientProperties s3clientproperties;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping(value = "/")
	public String homePage() {
		return "welcome to story planner!!";
	}

	@PostMapping(value = "/multimedia", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<ResponseDTO> postMultimedia(@RequestPart("bucketname") String bucketname,
			@RequestPart("sequencenum") String sequencenum, @RequestPart("filename") String filename,
			@RequestPart("file") MultipartFile file)
			throws IOException, CustomS3Exception {

		logger.debug(" bucketname recieved is :: " + bucketname);
		ResponseDTO responsedto = awsservice.upload(bucketname, sequencenum, file, awss3client);
		return ResponseEntity.ok(responsedto);

	}

	@DeleteMapping("/multimedia")
	public ResponseEntity<ResponseDTO> deleteMultimedia(@RequestParam("bucketname") String bucketname,
			@RequestParam("sequencenum") String sequencenum, @RequestParam("filename") String filename)
			throws IOException, CustomS3Exception {

		ResponseDTO responsedto = awsservice.delete(bucketname, sequencenum, filename, awss3client);
		return ResponseEntity.ok(responsedto);
	}

	@PutMapping(value = "/multimedia", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<ResponseDTO> updateMultimedia(@RequestPart("bucketname") String bucketname,
			@RequestPart("sequencenum") String sequencenum, @RequestPart("oldfilename") String oldfilename,
			@RequestPart("newfilename") String newfilename, @RequestPart("newfile") MultipartFile newfile)
			throws IOException {
		ResponseDTO responsedto = null;
		awsservice.delete(bucketname, sequencenum, oldfilename, awss3client);
		responsedto = awsservice.upload(bucketname, sequencenum, newfile, awss3client);

		return ResponseEntity.ok(responsedto);

	}
	
	/*

	@GetMapping(value = "/multimedia/{bucketname}/{foldername}/{imgId}", produces = { MediaType.IMAGE_JPEG_VALUE,
			MediaType.IMAGE_PNG_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.IMAGE_GIF_VALUE })
	public @ResponseBody byte[] getImageAsResponseEntity(@PathVariable("bucketname") String bucketname,
			@PathVariable String foldername, @PathVariable String imgId) {
		HttpHeaders headers = new HttpHeaders();
		// WebClient webclient = WebClient.builder()
		// .baseUrl("")
		// .build();
		// headers.setCacheControl(CacheControl.noCache().getHeaderValue());

		byte[] media = WebClient
				.create(s3clientproperties.getEndpointURI() + bucketname + "/" + "/" + foldername + "/" + imgId).get()
				.accept(MediaType.IMAGE_JPEG).accept(MediaType.IMAGE_PNG).accept(MediaType.APPLICATION_OCTET_STREAM)
				.accept(MediaType.IMAGE_GIF).retrieve().bodyToMono(byte[].class).block();

		return media;
	}
	*/

	@ExceptionHandler(com.startup.thatstory_multimedia.customexception.CustomS3Exception.class)
	public ResponseEntity<ResponseDTO> HandleException(CustomS3Exception exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ResponseDTO.builder().status("Error").errormessage(exception.getMessage()).build());
	}
}
