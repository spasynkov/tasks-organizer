package net.ukrtel.ddns.ff.organizer.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String catchAnyRuntimeException(RuntimeException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "errors/exception";
    }
}
