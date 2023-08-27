package com.datasolutions.eventstreaming.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;
import java.util.Date;

@Builder
@Data
@Entity
@Table(name = "events", indexes = @Index(name="create_date_index",columnList = "createdDate"))
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long eventId;
    private String sourceId;
    private String payload;

    @CreatedDate
    private Date createdDate;
}