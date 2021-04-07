package cz.osu.swi.car_service.repository;

import cz.osu.swi.car_service.models.Address;
import cz.osu.swi.car_service.models.Order;
import cz.osu.swi.car_service.models.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AddressRepository
        extends JpaRepository<Address,Long> {


    @Query("SELECT c FROM Address c WHERE " +
            "c.city = :city and " +
            "c.street = :street and " +
            "c.streetNumber = :street_number and " +
            "c.state = :state and " +
            "c.postCode = :post_code")
    Address checkAddressIfExists(@Param("city") String city,
                                            @Param("street") String street,
                                            @Param("street_number") String streetNumber,
                                            @Param("state") State state,
                                            @Param("post_code") String postCode);

}
