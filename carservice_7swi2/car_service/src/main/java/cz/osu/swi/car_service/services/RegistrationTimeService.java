package cz.osu.swi.car_service.services;

import cz.osu.swi.car_service.models.RegistrationTime;
import cz.osu.swi.car_service.repository.RegistrationTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationTimeService {

    private final RegistrationTimeRepository registrationTimeRepository;

    @Autowired
    public RegistrationTimeService(RegistrationTimeRepository registrationTimeRepository) {
        this.registrationTimeRepository = registrationTimeRepository;
    }

    public List<RegistrationTime> getRegistrationTime(){
        return registrationTimeRepository.findAll();
    }
}
