package com.startup.thatstory_multimedia.awsutility;

import java.io.InputStream;
import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectRequest;

public class S3Util {

	static Regions clientRegion = Regions.DEFAULT_REGION;

	public static Bucket createBucket(String bucket_name) {
		final AmazonS3 s3 = AmazonS3Client.builder()
				               
				               .build();
		Bucket b = null;
			System.out.format("Bucket %s already exists.\n", bucket_name);
			b = getBucket(bucket_name);
	
			try {
				b = s3.createBucket(bucket_name);
			} catch (AmazonS3Exception e) {
				System.err.println(e.getErrorMessage());
			}
			return b;
	}
	
	

	public static Bucket getBucket(String bucket_name) {
		final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();
		Bucket named_bucket = null;
		List<Bucket> buckets = s3.listBuckets();
		for (Bucket b : buckets) {
			if (b.getName().equals(bucket_name)) {
				named_bucket = b;
			}
		}
		return named_bucket;
	}

	public static boolean addObject(String bucketName, String keyName, InputStream inputstream) {

		try {
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new ProfileCredentialsProvider())
					.withRegion(clientRegion).build();

			s3Client.putObject(bucketName, keyName, inputstream, null);

		} catch (AmazonServiceException e) {
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it, so it returned an error response.
			e.printStackTrace();
			return false;
		} catch (SdkClientException e) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public static boolean deleteObject(String bucketName, String keyName) {

		try {
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new ProfileCredentialsProvider())
					.withRegion(clientRegion).build();

			s3Client.deleteObject(new DeleteObjectRequest(bucketName, keyName));
		} catch (AmazonServiceException e) {
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it, so it returned an error response.
			e.printStackTrace();
			return false;
		} catch (SdkClientException e) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			e.printStackTrace();
			return false;
		}
		return true;

	}
}