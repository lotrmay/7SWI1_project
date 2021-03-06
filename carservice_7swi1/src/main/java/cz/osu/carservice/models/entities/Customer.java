package cz.osu.carservice.models.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id",unique=true,nullable = false)
    private long id;

    @Column(name = "firstName",nullable = false,length = 15)
    private String firstName;

    @Column(name = "surname",nullable = false,length = 15)
    private String surname;

    @Column(name = "telephoneNumber",nullable = false,length = 15)
    private String telephoneNumber;

    @Column(name = "email",nullable = false,length = 40)
    private String email;

    @ManyToOne
    @JoinColumn(name = "id_address")
    private Address address;

    @OneToMany(mappedBy = "customer",cascade=CascadeType.PERSIST)
    private Set<Order> orders;

    public Customer() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                '}';
    }
}
