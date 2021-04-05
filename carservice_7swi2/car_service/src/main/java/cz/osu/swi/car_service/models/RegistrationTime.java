package cz.osu.swi.car_service.models;

import cz.osu.swi.car_service.utils.ConversionUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Entity
@Table(name = "registration_times")
public class RegistrationTime implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id",unique=true,nullable = false)
    private long id;

    @Column(name = "time_of_registration",nullable = false)
    private Time time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return ConversionUtils.getStringFromTime(time);
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public RegistrationTime() {
    }

    @Override
    public String toString() {
        return "RegistrationTime{" +
                "id=" + id  +
                ", time=" + time  +
                '}';
    }
}
