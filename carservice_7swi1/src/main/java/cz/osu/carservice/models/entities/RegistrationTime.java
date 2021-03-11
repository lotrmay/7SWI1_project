package cz.osu.carservice.models.entities;

import cz.osu.carservice.models.utils.ConversionUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Set;

@Entity
@Table(name = "registration_times")
@NamedQueries({
        @NamedQuery(name = "RegistrationTime.findAll", query = "SELECT c FROM RegistrationTime c"),
        @NamedQuery(name = "RegistrationTime.findByTime", query = "SELECT c FROM RegistrationTime c WHERE c.time = :time")})
public class RegistrationTime implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id",unique=true,nullable = false)
    private long id;

    @Column(name = "time_of_registration",nullable = false)
    private Time time;

    @OneToMany(mappedBy = "time",cascade = CascadeType.PERSIST)
    private Set<Order> orders;

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

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
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
