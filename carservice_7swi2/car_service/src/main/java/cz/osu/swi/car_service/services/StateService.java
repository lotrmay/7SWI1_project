package cz.osu.swi.car_service.services;

import cz.osu.swi.car_service.models.State;
import cz.osu.swi.car_service.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService {

    private final StateRepository stateRepository;

    @Autowired
    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public List<State> getStates() {
        return stateRepository.findAll();
    }

    public State getStateByShortcut(String shortcut){
        return stateRepository.getStateShortcut(shortcut);
    }
}
