package com.datasolutions.eventstreaming.controllers.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class EventSendRequest {
    private String sourceId;
    private String payload;
}
