package com.startup.thatstory_multimedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.startup.thatstory_multimedia.properties.S3ClientProperties;

@SpringBootApplication
@EnableConfigurationProperties(S3ClientProperties.class)
@EnableEurekaClient

public class ThatstoryMultimediaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThatstoryMultimediaApplication.class, args);
	}

}
