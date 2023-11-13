package ru.padwicki.soundseeker.controllersInterfaces;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public interface HubControllerInterface {
    @GetMapping("/hub")
    public String startPage(Model model);
}
