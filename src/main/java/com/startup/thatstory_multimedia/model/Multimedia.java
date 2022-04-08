package com.startup.thatstory_multimedia.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Multimedia {

	private String filename;
	private String createdate;
	private String createdby;
	private MultipartFile file;
}
