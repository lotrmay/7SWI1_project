package cz.osu.swi.car_service.services;

import cz.osu.swi.car_service.models.Customer;
import cz.osu.swi.car_service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService{
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerIfExists(String name,String surname,String telephone_number,String email){
        return this.customerRepository.checkIfCustomerExists(name, surname, telephone_number, email);
    }

    public void saveCustomer(Customer customer){
        this.customerRepository.save(customer);
    }

}
