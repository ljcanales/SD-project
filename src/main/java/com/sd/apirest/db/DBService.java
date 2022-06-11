package com.sd.apirest.db;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Service
public class DBService {
    @Value( "${database.uri}")
    String dbUri;
    @Value( "${database.name}")
    String dbName;

    private final MongoDatabase database;

    DBService() {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClient client = MongoClients.create(dbUri);
        database = client.getDatabase(dbName).withCodecRegistry(pojoCodecRegistry);
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
