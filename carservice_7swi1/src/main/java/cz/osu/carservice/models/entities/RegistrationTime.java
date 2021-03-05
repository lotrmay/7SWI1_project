package cz.osu.carservice.models.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Entity
@Table(name = "registration_times")
@NamedQueries({
        @NamedQuery(name = "RegistrationTimes.findAll", query = "SELECT c FROM RegistrationTime c") })
public class RegistrationTime implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "time_of_registration")
    private Time time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public RegistrationTime() {
    }

    @Override
    public String toString() {
        return "RegistrationTime{" +
                "id=" + id +
                ", time=" + time +
                '}';
    }
}
