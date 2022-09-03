package com.gharib.dataDemo.data.models;


import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FLight {
    @Id
    @GeneratedValue
    private Long id;
    private  String origin;
    private  String destination;
    private LocalDateTime scheduleAt;


    @Override
    public String toString() {
        return "FLight{" +
            "id=" + id +
            ", origin='" + origin + '\'' +
            ", destination='" + destination + '\'' +
            ", scheduleAt=" + scheduleAt +
            '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getScheduleAt() {
        return scheduleAt;
    }

    public void setScheduleAt(LocalDateTime scheduleAt) {
        this.scheduleAt = scheduleAt;
    }
}
