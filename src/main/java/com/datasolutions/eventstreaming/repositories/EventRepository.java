package com.datasolutions.eventstreaming.repositories;


import com.datasolutions.eventstreaming.entities.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

    Optional<Event> findFirstByCreatedDateAfter(Date date);

}
