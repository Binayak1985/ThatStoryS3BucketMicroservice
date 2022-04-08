package com.startup.thatstory_multimedia.properties;

import java.net.URI;


import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties("s3")
@Data
public class S3ClientProperties {
	
	private	URI endpointURI;

}
