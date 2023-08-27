package com.datasolutions.eventstreaming.controllers.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class EventAckResponse {
    private Long eventId;
    private String sourceId;
    private String payload;
    private Date createdDate;
}
