package com.aotscc.sqs.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.core.env.ResourceIdResolver;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import java.util.Collections;
@Configuration
@EnableSqs
public class SpringCloudSQSConfig {
    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${springbootsqsexample.pollLength}")
    private int pollLength;

    @Value("${springbootsqsexample.maxMessages}")
    private int maxMessages;

    // Mapping Converter for serializing / deserializing the messages produced / consumed
    private MappingJackson2MessageConverter createMappingJackson2MessageConverter() {
        // Configure ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        MappingJackson2MessageConverter messageConverter =
                new MappingJackson2MessageConverter();

        messageConverter.setSerializedPayloadClass(String.class);
        messageConverter.setObjectMapper(objectMapper);

        return messageConverter;
    }

    // Insert the Mapping Message Converter into the QueueMessageTemplate
    // (I don't know what's going on with ResourceIdResolver error in Intellij)
    @Bean
    public QueueMessagingTemplate myMessagingTemplate(ResourceIdResolver resourceIdResolver) {
        return new QueueMessagingTemplate(amazonSQSAsync(), resourceIdResolver, createMappingJackson2MessageConverter() );
    }

    // Create the SimpleMessageListenerContainerFactory with the Mapping Message Converter
    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory() {
        SimpleMessageListenerContainerFactory factory =
                new SimpleMessageListenerContainerFactory();

        factory.setAmazonSqs(amazonSQSAsync());

        factory.setAutoStartup(true);

        // Maximum Number Of Messages to Consume at One Time
        factory.setMaxNumberOfMessages(maxMessages);

        // Poll Timeout
        factory.setWaitTimeOut(pollLength);

        factory.setQueueMessageHandler(
                new QueueMessageHandler(
                        Collections.singletonList(createMappingJackson2MessageConverter())));

        return factory;
    }


    // Amazon SQS Async
    // This has to be public because of AOP
    public AmazonSQSAsync amazonSQSAsync() {

        AmazonSQSAsyncClientBuilder amazonSQSAsyncClientBuilder =
                AmazonSQSAsyncClientBuilder.standard();

        amazonSQSAsyncClientBuilder.withRegion(region);

        BasicAWSCredentials basicAWSCredentials =
                new BasicAWSCredentials(accessKey, secretKey);

        amazonSQSAsyncClientBuilder.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials));

        return amazonSQSAsyncClientBuilder.build();
    }
}
