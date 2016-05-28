package com.gutnik.configuration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by meirg
 */
@Configuration
@EnableMongoRepositories
public class MongoConfiguration extends AbstractMongoConfiguration {

    @Value("${mongodb.db}")
    private String MONGO_DB;

    @Value("${mongodb.host}")
    private String MONGO_HOST;

    @Value("${mongodb.port}")
    private Integer MONGO_PORT;

    @Override
    protected String getDatabaseName() {
        return MONGO_DB;
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(MONGO_HOST, MONGO_PORT);
    }
}
