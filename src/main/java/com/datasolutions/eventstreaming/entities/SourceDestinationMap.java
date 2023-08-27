package com.datasolutions.eventstreaming.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@Entity
@Table(name = "source_destination_map")
@AllArgsConstructor
@NoArgsConstructor
public class SourceDestinationMap {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String sourceId;
    private String destinationId;
}
