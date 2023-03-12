package com.aotscc.sqs.consumer;

import com.aotscc.sqs.dto.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
	private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

	// Get the message and convert it to "Student"
	// Delete the message On Success
	@SqsListener(value = "${cloud.aws.end-point.uri}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	public void receive(Student student) {

		logger.info("Message Received using SQS Listener " + student);
	}
}
