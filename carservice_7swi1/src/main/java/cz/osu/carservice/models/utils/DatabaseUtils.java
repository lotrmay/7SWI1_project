package cz.osu.carservice.models.utils;

import cz.osu.carservice.models.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.sql.Time;
import java.time.LocalDate;

public class DatabaseUtils {
    public static RegistrationTime getRegistrationTimeForOrder(EntityManager entityManager,Time time){
        RegistrationTime timeForRegistration = null;

        try {
            timeForRegistration = entityManager.createNamedQuery("RegistrationTime.findByTime", RegistrationTime.class)
                    .setParameter("time",time)
                    .getSingleResult();
        }catch (Exception exception){
            System.err.println(exception.getMessage());
        }

        return timeForRegistration;
    }
    public static Address getAddressIfExists(EntityManager entityManager, String city, String street, String streetNumber, String postCode, State state)
            throws NoResultException {
        return entityManager.createNamedQuery("Address.checkIfAddressExist", Address.class)
                .setParameter("city",city)
                .setParameter("street",street)
                .setParameter("street_number",streetNumber)
                .setParameter("state",state)
                .setParameter("post_code",postCode)
                .getSingleResult();

    }
    public static Customer getCustomerIfExists(EntityManager entityManager, String name, String surname, String telephoneNumber, String email)
            throws NoResultException{
        return entityManager.createNamedQuery("Customer.checkIfCustomerExists", Customer.class)
                .setParameter("first_name",name)
                .setParameter("surname",surname)
                .setParameter("telephone_number",telephoneNumber)
                .setParameter("email",email)
                .getSingleResult();

    }
    public static Order checkIfRegistrationTimeIsReserved(EntityManager entityManager, RegistrationTime time, LocalDate date)
            throws NoResultException{
        return entityManager.createNamedQuery("Order.checkReservationTime", Order.class)
                .setParameter("dateOfFulfillment",date)
                .setParameter("time",time)
                .getSingleResult();
    }
    public static Customer getCustomerForOrder(EntityManager entityManager,String name,String surname,String telephoneNumber,String email, Address address){
        Customer customer = null;

        try {
            customer = getCustomerIfExists(entityManager,name,surname,telephoneNumber,email);
            if(customer.getAddress().getId() != address.getId()){
                entityManager.getTransaction().begin();
                customer.setAddress(address);
                entityManager.getTransaction().commit();
            }
        }catch (NoResultException e) {
            customer = new Customer(name,surname,telephoneNumber,email,address);

            entityManager.getTransaction().begin();
            entityManager.persist(customer);
            entityManager.getTransaction().commit();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return customer;
    }
    public static Address getAddressForCustomer(EntityManager entityManager, String city,String street,String streetNumber,String postCode, String countryShortcut){
        State state = null;
        Address address = null;

        try {
            state = entityManager.createNamedQuery("State.findByShortCut", State.class)
                    .setParameter("state_shortcut",countryShortcut)
                    .getSingleResult();

            address = getAddressIfExists(entityManager,city,street,streetNumber,postCode,state);

        }catch (NoResultException e) {
            address = new Address(city,street,streetNumber,postCode,state);

            entityManager.getTransaction().begin();
            entityManager.persist(address);
            entityManager.getTransaction().commit();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return address;
    }
}
