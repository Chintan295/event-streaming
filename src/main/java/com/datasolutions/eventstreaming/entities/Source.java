package com.datasolutions.eventstreaming.entities;

import lombok.Builder;

import java.util.List;

@Builder
public class Source extends BaseEntity {
    private String sourceId;
    private String writeKey;
}
