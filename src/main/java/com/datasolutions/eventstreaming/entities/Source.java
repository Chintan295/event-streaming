package com.datasolutions.eventstreaming.entities;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;

@Builder
@Data
public class Source {
    @Id
    private String sourceId;
}
