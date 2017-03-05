package net.ukrtel.ddns.ff.organizer.services;

import net.ukrtel.ddns.ff.organizer.domain.Task;
import net.ukrtel.ddns.ff.organizer.domain.User;
import net.ukrtel.ddns.ff.organizer.exceptions.IllegalTaskStateException;
import net.ukrtel.ddns.ff.organizer.repositories.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static net.ukrtel.ddns.ff.organizer.domain.TaskStatus.*;

@Service
public class TasksService {
    private TasksRepository tasksRepository;

    @Autowired
    public TasksService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public void createTask(User author, String description) {
        Task task = new Task();

        task.setAuthor(author);
        task.setDescription(description);
        task.setTimestampOfCreation(System.currentTimeMillis());
        task.setStatus(CREATED);

        tasksRepository.save(task);
    }

    public void takeTask(User performer, long taskId, long expectedTimestampOfClose) throws IllegalTaskStateException {
        Task task = tasksRepository.findOne(taskId);

        if (task.getStatus() == CREATED || task.getStatus() == ABANDONED) {
            task.setPerformer(performer);
            if (expectedTimestampOfClose < 0) {         // if it's not defined - lets use 'default' period of 2 days
                long twoDays = 2 * 24 * 60 * 60 * 1000;
                task.setTimestampOfClose(System.currentTimeMillis() + twoDays);
            } else task.setTimestampOfClose(expectedTimestampOfClose);
            task.setStatus(IN_PROCESS);
        } else throw new IllegalTaskStateException("Can't take task #" + taskId + "!" +
                "Task status is " + task.getStatus() + ".");

        tasksRepository.save(task);
    }

    public void takeTask(User performer, long taskId) throws IllegalTaskStateException {
        takeTask(performer, taskId, -1);
    }

    public void leaveTask(long taskId) throws IllegalTaskStateException {
        Task task = tasksRepository.findOne(taskId);

        if (task.getStatus() == IN_PROCESS) {
            task.setStatus(ABANDONED);
        } else throw new IllegalTaskStateException("Can't leave task #" + taskId + "!" +
                "Task status is " + task.getStatus() + ".");

        tasksRepository.save(task);
    }

    public void taskFinished(long taskId) throws IllegalTaskStateException {
        Task task = tasksRepository.findOne(taskId);

        if (task.getStatus() == IN_PROCESS) {
            task.setStatus(CLOSED);
            task.setTimestampOfClose(System.currentTimeMillis());
        } else throw new IllegalTaskStateException("Can't close task #" + taskId + "!" +
                "Task status is " + task.getStatus() + ".");

        tasksRepository.save(task);
    }

    public List<Task> listAllTasks() {
        return tasksRepository.findAll();
    }
}
