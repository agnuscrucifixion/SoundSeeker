package ru.padwicki.soundseeker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.padwicki.soundseeker.config.auth.SpotifyAuth;
import ru.padwicki.soundseeker.controllersInterfaces.HubControllerInterface;
import ru.padwicki.soundseeker.service.HubService;


@Controller
public class HubController implements HubControllerInterface {

    HubService hubService;

    public HubController(HubService hubService) {
        this.hubService = hubService;
    }

    @Override
    public String startPage(Model model) {
        model.addAttribute("urlSpotify", hubService.authorization());
        return "hub";
    }
}
