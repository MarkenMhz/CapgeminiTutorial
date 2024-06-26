package com.ccsw.tutorial.client;

import com.ccsw.tutorial.client.model.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends CrudRepository<Client, Long> {

    @Query("SELECT c FROM Client c WHERE c.name = :name")
    Client findByName(@Param("name") String name);
}
