package cz.osu.carservice.models.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.Year;

@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(name = "Order.findAll", query = "SELECT c FROM Order c")})
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id",unique=true,nullable = false)
    private long id;

    @Column(name = "registration_plate",nullable = false,length = 8)
    private String registration_plate;

    @Column(name = "type_of_car",nullable = false,length = 30)
    private String type_of_car;

    @Column(name = "date_of_fulfillment",nullable = false)
    private Date date_of_fulfillment;

    @Column(name = "year_of_production",nullable = false)
    private int year_of_production;

    @Column(name = "car_service",nullable = false)
    private int car_service;

    @Column(name = "tire_service",nullable = false)
    private int tire_service;

    @Column(name = "other_service",nullable = false)
    private int other_service;

    @Column(name = "note",nullable = true)
    private String note;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "id_time")
    private RegistrationTime time;

    public Order() {
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", registration_plate='" + registration_plate + '\'' +
                ", type_of_car='" + type_of_car + '\'' +
                ", date_of_fulfillment=" + date_of_fulfillment +
                ", year_of_production=" + year_of_production +
                ", car_service=" + car_service +
                ", tire_service=" + tire_service +
                ", other_service=" + other_service +
                ", note='" + note + '\'' +
                ", customer=" + customer +
                ", time=" + time +
                '}';
    }
}
