package dev.aeld.weather.entities;

import java.util.Date;



import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name =  "weather")
@Getter
@Setter
public class WeatherLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String user;

    @Column
    private String endpoint;

    @Column
    private String city;

    @Column
    private String response;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;
}
