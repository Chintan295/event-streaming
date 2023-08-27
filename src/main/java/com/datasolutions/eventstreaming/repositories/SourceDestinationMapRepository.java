package com.datasolutions.eventstreaming.repositories;

import com.datasolutions.eventstreaming.entities.SourceDestinationMap;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SourceDestinationMapRepository extends CrudRepository<SourceDestinationMap, Long> {

    long countBySourceIdAndDestinationId(String sourceId, String destinationId);
}
