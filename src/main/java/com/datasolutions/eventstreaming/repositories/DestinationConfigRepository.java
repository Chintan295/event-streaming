package com.datasolutions.eventstreaming.repositories;

import com.datasolutions.eventstreaming.entities.DestinationConfig;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface DestinationConfigRepository extends CrudRepository<DestinationConfig, String> {
    Optional<DestinationConfig> findByDestinationId(String destinationId);

    @Query("SELECT MIN(d.cursor) FROM DestinationConfig d")
    Date findMinimumDate();
}
