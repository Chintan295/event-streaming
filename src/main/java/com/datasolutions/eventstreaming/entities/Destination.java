package com.datasolutions.eventstreaming.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Builder
@Data
@Entity
@Table(name = "destinations")
@AllArgsConstructor
@NoArgsConstructor
public class Destination {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String destinationId;
    private String endPoint;
    private Date cursor;
    private long retryCount;
    private long retryThreshold;
}
