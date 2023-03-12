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

