package com.datasolutions.eventstreaming.entities;

import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Version;

import java.util.Date;

@Builder
public class BaseEntity {

    @CreatedDate
    private Date createdDate;

    @Version
    private int version;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
