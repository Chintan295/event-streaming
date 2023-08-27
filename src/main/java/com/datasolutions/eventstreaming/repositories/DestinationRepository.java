package com.datasolutions.eventstreaming.repositories;

import com.datasolutions.eventstreaming.entities.Destination;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface DestinationRepository extends CrudRepository<Destination, String> {
    Optional<Destination> findByDestinationId(String destinationId);
}
