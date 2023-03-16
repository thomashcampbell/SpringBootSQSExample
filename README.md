# SpringBootSQSExample
Example of Spring Boot AWS SQS Producer and Consumer

## Introduction

This is a project for showing how to use AWS SQS with Spring Boot 3.0.4.  When researching this I found a lot of different ways to do this but none were recent.  This is recent as of 3/11/2023 and uses specific package version numbers.

Do understand that this is cobbled together from lots of information out in the internet.  You may see some code from you or other sources that was copied.

## Caveat

The project needs to use 

```
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-messaging</artifactId>
            <version>5.3.25</version>
        </dependency>
```

Other than that it should work fine with latest versions as of 3/11/2023 (see the pom.xml)

## Converters

Another thing (easily) I couldn't find was the usage of Jackson to convert objects to and from string/json which AWS requires.  This project does that as well.

## 2.7.3 Spring Boot Starter Parent

If you want to use a Spring Starter before Version 3.0.4 such as the following:

```
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.3</version>
        <relativePath /> <!-- lookup parent from repository -->
    </parent>
```


You want to add the following to your SpringBootApplication to get it up and running without throwing errors
```
@SpringBootApplication(
        exclude = {
                org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration.class,
                org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration.class,
                org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration.class
        }
)
```

There is a branch named "SpringBootStarterParent273" that shows this configuration

