package com.startup.thatstory_multimedia.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.startup.thatstory_multimedia.bean.AWSS3Client;


@Configuration
public class AWSConfigurationBean {

	@Bean
	public AWSS3Client getS3Client() {
		return new AWSS3Client();
	}

}
