package net.ukrtel.ddns.ff.organizer.controllers;

import net.ukrtel.ddns.ff.organizer.services.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/tasks")
public class TasksController {
    private final TasksService tasksService;

    @Autowired
    public TasksController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showAllTasks(Model model) {
        model.addAttribute("tasksList", tasksService.listAllTasks());
        return "tasks";
    }

    @RequestMapping("/{taskId}")
    public String showTask(@PathVariable long taskId, Model model) {
        model.addAttribute(tasksService.getTaskById(taskId));
        return "task";
    }
}
