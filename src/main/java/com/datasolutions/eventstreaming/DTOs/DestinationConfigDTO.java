package com.datasolutions.eventstreaming.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DestinationConfigDTO {
    private String destinationId;
    private String endPoint;
    private Date cursor;
    private long retryCount;
    private long retryThreshold;
    private long retryTimeout;
}
