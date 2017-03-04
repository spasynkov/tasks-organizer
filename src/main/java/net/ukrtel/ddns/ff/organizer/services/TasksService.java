package net.ukrtel.ddns.ff.organizer.services;

import net.ukrtel.ddns.ff.organizer.domain.Task;
import net.ukrtel.ddns.ff.organizer.domain.TaskStatus;
import net.ukrtel.ddns.ff.organizer.domain.User;
import net.ukrtel.ddns.ff.organizer.repositories.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        task.setStatus(TaskStatus.CREATED);

        tasksRepository.save(task);
    }

    public void takeTask(User performer, long taskId, long expectedTimestampOfClose) {
        Task task = tasksRepository.findOne(taskId);

        task.setPerformer(performer);
        if (expectedTimestampOfClose < 0) {
            long twoDays = 2 * 24 * 60 * 60 * 1000;
            task.setTimestampOfClose(System.currentTimeMillis() + twoDays);
        } else task.setTimestampOfClose(expectedTimestampOfClose);

        tasksRepository.save(task);
    }

    public void takeTask(User performer, long taskId) {
        takeTask(performer, taskId, -1);
    }

    public void leaveTask() {
    }

    public void taskFinished() {
    }

    public List<Task> listAllTasks() {
        return tasksRepository.findAll();
    }
}
