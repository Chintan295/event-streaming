package com.datasolutions.eventstreaming.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Data
public class Source {
    @Id
    private String sourceId;
}
