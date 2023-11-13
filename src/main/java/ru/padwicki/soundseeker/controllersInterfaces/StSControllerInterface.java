package ru.padwicki.soundseeker.controllersInterfaces;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


public interface StSControllerInterface {
    @RequestMapping("/convert")
    public void Convert();
}
