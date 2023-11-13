package ru.padwicki.soundseeker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.padwicki.soundseeker.controllersInterfaces.StSControllerInterface;
import ru.padwicki.soundseeker.pyBase.pyYandex;

@RestController
public class StSController implements StSControllerInterface {

    @Override
    public void Convert() {

    }
}
