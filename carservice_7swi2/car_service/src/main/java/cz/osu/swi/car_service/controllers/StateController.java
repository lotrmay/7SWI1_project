package cz.osu.swi.car_service.controllers;

import cz.osu.swi.car_service.models.RegistrationTime;
import cz.osu.swi.car_service.models.State;
import cz.osu.swi.car_service.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/OrderCreation")
@CrossOrigin(origins = "http://localhost:3001")
public class StateController {

    private final StateService stateService;

    @Autowired
    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping("/States")
    public List<State> showStates(){
        return stateService.getStates();
    }
}
