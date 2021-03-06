package cz.osu.carservice.models.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "address")
@NamedQueries({
        @NamedQuery(name = "Address.findAll", query = "SELECT c FROM Address c")})
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id",unique=true,nullable = false)
    private long id;

    @Column(name = "city",nullable = false,length = 30)
    private String city;

    @Column(name = "street",nullable = false,length = 20)
    private String street;

    @Column(name = "street_number",nullable = false,length = 15)
    private String streetNumber;

    @Column(name = "post_code",nullable = false,length = 15)
    private String postCode;

    @ManyToOne
    @JoinColumn(name = "id_state")
    private State state;

    @OneToMany(mappedBy = "address",cascade=CascadeType.PERSIST)
    private Set<Customer> customers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }


    public Address() {
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", postCode='" + postCode + '\'' +
                ", state=" + state +
                '}';
    }
}
