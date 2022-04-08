package com.startup.thatstory_multimedia.customexception;

public class CustomS3Exception extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CustomS3Exception(String message)
	{
		super(message);
	}
}
