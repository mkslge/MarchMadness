package org.example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
public class Controller {
    @GetMapping("")
    public String getString() throws IOException {
        Simulation simulation = new Simulation();

        return simulation.getWinner();
    }
}
