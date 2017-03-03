package net.ukrtel.ddns.ff.organizer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping({"/", "/home", "/homepage"})
public class MainController {
    @RequestMapping(method = RequestMethod.GET)
    public String main() {
        return "main";
    }
}
