package net.ukrtel.ddns.ff.organizer.controllers;

import net.ukrtel.ddns.ff.organizer.domain.User;
import net.ukrtel.ddns.ff.organizer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/users")
public class UsersController {
    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = GET)
    public String usersList(Model model) {
        model.addAttribute("users", userService.collectAllUsersNames());
        return "users";
    }

    @RequestMapping(value = "/me", method = GET)
    public String myProfile(Principal principal, Model model) {
        User user = userService.findUserByName(principal.getName(), true);
        model.addAttribute(user);
        return "profile";
    }

    @RequestMapping(value = "/{userIdentifier}", method = GET)
    public String otherProfile(@PathVariable String userIdentifier, Model model) {
        User user = null;

        // will try to find user by id
        try {
            long id = Long.parseLong(userIdentifier);
            user = userService.findUserById(id, true);
        } catch (NumberFormatException e) {
            // do nothing
        }

        // if there was no user with such id - it could be username
        if (user == null) user = userService.findUserByName(userIdentifier, true);

        model.addAttribute(user);
        return "profile";
    }
}
