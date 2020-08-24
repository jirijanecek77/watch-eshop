package com.example.watcheshop.repository;

import com.example.watcheshop.model.Watch;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface WatchRepository extends CrudRepository<Watch, UUID> {

}
