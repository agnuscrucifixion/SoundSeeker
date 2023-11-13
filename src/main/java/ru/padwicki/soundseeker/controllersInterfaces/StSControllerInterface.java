package ru.padwicki.soundseeker.controllersInterfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/sts")
public interface StSControllerInterface {
    @GetMapping("/convert")
    public void Convert();
}
