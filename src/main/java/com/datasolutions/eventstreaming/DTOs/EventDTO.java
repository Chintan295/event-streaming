package com.datasolutions.eventstreaming.DTOs;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode()
@Builder
@Data
public class EventDTO {
    private Long eventId;
    private String sourceId;
    private String payload;
    private Date createdDate;
}
