package cz.osu.swi.car_service.services;

import cz.osu.swi.car_service.models.Address;
import cz.osu.swi.car_service.models.State;
import cz.osu.swi.car_service.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address getAddressIfExists(String city,String street,String streetNumber,String postCode, State state){
        return addressRepository.checkAddressIfExists(city, street, streetNumber, state, postCode);
    }

    public void saveAddress(Address address){
        this.addressRepository.save(address);
    }
}
