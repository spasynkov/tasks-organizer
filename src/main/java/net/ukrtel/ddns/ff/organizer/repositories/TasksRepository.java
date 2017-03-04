package net.ukrtel.ddns.ff.organizer.repositories;

import net.ukrtel.ddns.ff.organizer.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasksRepository extends JpaRepository<Task, Long> {
}
