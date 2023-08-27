package com.datasolutions.eventstreaming.DTOs;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@Builder
public class DestinationConfigDTO {
    private String destinationId;
    private String endPoint;
    private Date cursor;
    private long retryCount;
    private long retryThreshold;
    private long retryTimeout;
}
