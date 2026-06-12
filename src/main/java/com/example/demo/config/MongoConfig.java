package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig {

    private static final String URI = "mongodb://localhost:27017";
private static final String DATABASE = "tradeglobal";

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(URI);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), DATABASE);
    }
}