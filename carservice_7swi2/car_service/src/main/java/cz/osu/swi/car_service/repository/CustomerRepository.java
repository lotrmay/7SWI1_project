package cz.osu.swi.car_service.repository;

import cz.osu.swi.car_service.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository
        extends JpaRepository<Customer,Long> {

    @Query("SELECT c FROM Customer c WHERE " +
            "c.firstName = :first_name and " +
            "c.surname = :surname and " +
            "c.telephoneNumber = :telephone_number and " +
            "c.email = :email")
    Customer checkIfCustomerExists(@Param("first_name") String name,
                                   @Param("surname") String surname,
                                   @Param("telephone_number") String telephone_number,
                                   @Param("email") String email);

}
