package com.eletronicodigitalmobile.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {
	
	@Autowired
	private AmazonS3 s3Client;
	
	@Value("${s3.bucket}")
	String bucketName;
	
	private Logger LOG = LoggerFactory.getLogger(S3Service.class);
	
	
	public void upLoadFile(String localFilePath) {
		
		try {

			LOG.info("Iniciando o Upload");		
				File file = new File(localFilePath);
			LOG.info("Upload Finalizado");
			
		s3Client.putObject(new PutObjectRequest(bucketName, "teste", file));
		
	}catch(AmazonServiceException e) {
		LOG.info("AmazonServiceException "+ e.getErrorMessage());
		LOG.info("Status code "+ e.getErrorCode());
    } catch(AmazonClientException e) {
    	LOG.info("AmazonClientException "+ e.getMessage());

    }
  }
}