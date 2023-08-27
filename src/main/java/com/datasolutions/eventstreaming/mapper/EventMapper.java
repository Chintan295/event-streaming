package com.datasolutions.eventstreaming.mapper;

import com.datasolutions.eventstreaming.DTOs.EventDTO;
import com.datasolutions.eventstreaming.controllers.request.EventSendRequest;
import com.datasolutions.eventstreaming.controllers.response.EventAckResponse;
import com.datasolutions.eventstreaming.entities.Event;

import java.util.Date;

public class EventMapper {
    public static EventDTO mapToEventDTO(Event event) {
        return EventDTO.builder()
                .eventId(event.getEventId())
                .payload(event.getPayload())
                .createdDate(event.getCreatedDate())
                .sourceId(event.getSourceId())
                .build();
    }

    public static Event mapToEvent(EventDTO eventDTO) {
        return Event.builder()
                .eventId(eventDTO.getEventId())
                .payload(eventDTO.getPayload())
                .createdDate(eventDTO.getCreatedDate())
                .sourceId(eventDTO.getSourceId())
                .build();
    }

    public static Event mapToEvent(EventSendRequest eventSendRequest) {
        return Event.builder()
                .sourceId(eventSendRequest.getSourceId())
                .payload(eventSendRequest.getPayload())
                .createdDate(new Date())
                .build();
    }

    public static EventAckResponse mapToEventAckResponse(Event event) {
        return EventAckResponse.builder()
                .eventId(event.getEventId())
                .payload(event.getPayload())
                .createdDate(event.getCreatedDate())
                .sourceId(event.getSourceId())
                .build();
    }
}
