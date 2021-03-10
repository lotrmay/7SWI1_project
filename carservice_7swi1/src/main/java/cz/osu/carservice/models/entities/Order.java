package cz.osu.carservice.models.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(name = "Order.findAll", query = "SELECT c FROM Order c"),
        @NamedQuery(name = "Order.findAllByDate", query = "SELECT c FROM Order c WHERE c.date_of_fulfillment = :dateOfFulfillment"),
        @NamedQuery(name = "Order.checkReservationTime", query = "SELECT c FROM Order c WHERE " +
                "c.date_of_fulfillment = :dateOfFulfillment and " +
                "c.time = :time")})
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
    private LocalDate date_of_fulfillment;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRegistration_plate() {
        return registration_plate;
    }

    public void setRegistration_plate(String registration_plate) {
        this.registration_plate = registration_plate;
    }

    public String getType_of_car() {
        return type_of_car;
    }

    public void setType_of_car(String type_of_car) {
        this.type_of_car = type_of_car;
    }

    public LocalDate getDate_of_fulfillment() {
        return date_of_fulfillment;
    }

    public void setDate_of_fulfillment(LocalDate date_of_fulfillment) {
        this.date_of_fulfillment = date_of_fulfillment;
    }

    public int getYear_of_production() {
        return year_of_production;
    }

    public void setYear_of_production(int year_of_production) {
        this.year_of_production = year_of_production;
    }

    public int getCar_service() {
        return car_service;
    }

    public void setCar_service(int car_service) {
        this.car_service = car_service;
    }

    public int getTire_service() {
        return tire_service;
    }

    public void setTire_service(int tire_service) {
        this.tire_service = tire_service;
    }

    public int getOther_service() {
        return other_service;
    }

    public void setOther_service(int other_service) {
        this.other_service = other_service;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public RegistrationTime getTime() {
        return time;
    }

    public void setTime(RegistrationTime time) {
        this.time = time;
    }

    public Order() {
    }

    public Order(String registration_plate, String type_of_car, LocalDate date_of_fulfillment, int year_of_production, int car_service, int tire_service, int other_service, String note, Customer customer, RegistrationTime time) {
        this.registration_plate = registration_plate;
        this.type_of_car = type_of_car;
        this.date_of_fulfillment = date_of_fulfillment;
        this.year_of_production = year_of_production;
        this.car_service = car_service;
        this.tire_service = tire_service;
        this.other_service = other_service;
        this.note = note;
        this.customer = customer;
        this.time = time;
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
