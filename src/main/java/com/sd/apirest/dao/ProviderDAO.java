package com.sd.apirest.dao;

import com.sd.apirest.model.Provider;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ProviderDAO extends MongoRepository<Provider, String> {

    @Query("{_id:'?0'}")
    Provider getProvider(String id);

}
