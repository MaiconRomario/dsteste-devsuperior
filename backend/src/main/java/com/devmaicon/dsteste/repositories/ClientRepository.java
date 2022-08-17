package com.devmaicon.dsteste.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devmaicon.dsteste.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
