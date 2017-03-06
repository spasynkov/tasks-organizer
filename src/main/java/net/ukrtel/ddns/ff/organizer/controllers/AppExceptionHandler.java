package net.ukrtel.ddns.ff.organizer.controllers;

import net.ukrtel.ddns.ff.organizer.exceptions.UserNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFound(UserNotFoundException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "errors/exception";
    }
}
