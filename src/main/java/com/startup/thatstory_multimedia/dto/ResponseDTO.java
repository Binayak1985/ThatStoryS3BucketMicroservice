package com.startup.thatstory_multimedia.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ResponseDTO {

	private String bucketname;
	private String filename;
	private Date timestamp;
}
