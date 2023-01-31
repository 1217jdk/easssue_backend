package com.limemul.easssue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoDBConfig {

    private final MongoMappingContext mongoMappingContext;

    public MongoDBConfig(MongoMappingContext mongoMappingContext) {
        this.mongoMappingContext = mongoMappingContext;
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter(
            MongoDatabaseFactory databaseFactory,MongoMappingContext mappingContext){
        DbRefResolver dbRefResolver=new DefaultDbRefResolver(databaseFactory);
        MappingMongoConverter converter=new MappingMongoConverter(dbRefResolver,mappingContext);

        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }
}
