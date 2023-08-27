package com.datasolutions.eventstreaming.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Builder
@Data
@Entity
@Table(name = "destinations_config")
@AllArgsConstructor
@NoArgsConstructor
public class DestinationConfig {
    @Id
    private String destinationId;
    private String endPoint;
    private Date cursor;
    private long retryCount;
    private long retryThreshold;
    private long retryTimeout;
}
