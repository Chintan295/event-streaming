package com.datasolutions.eventstreaming.repositories;


import com.datasolutions.eventstreaming.entities.Event;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

    Optional<Event> findFirstByCreatedDateAfter(Date date);

    @Modifying
    @Transactional
    @Query("DELETE FROM Event e WHERE e.createdDate <= :date")
    void deleteEventsBeforeDate(Date date);

}
