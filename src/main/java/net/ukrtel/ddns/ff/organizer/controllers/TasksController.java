package net.ukrtel.ddns.ff.organizer.controllers;

import net.ukrtel.ddns.ff.organizer.domain.Task;
import net.ukrtel.ddns.ff.organizer.exceptions.NoTaskFoundException;
import net.ukrtel.ddns.ff.organizer.services.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

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
        List<Task> tasks = tasksService.listAllTasks();
        if (tasks == null || tasks.isEmpty()) throw new NoTaskFoundException("There are no tasks at server.");
        model.addAttribute("tasksList", tasks);
        return "tasks";
    }

    @RequestMapping("/{taskId}")
    public String showTask(@PathVariable long taskId, Model model) {
        Task task = tasksService.getTaskById(taskId);
        if (task == null) throw new NoTaskFoundException("There is no task with id '" + taskId + "'.");
        model.addAttribute(task);
        return "task";
    }
}
