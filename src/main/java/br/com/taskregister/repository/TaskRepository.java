package br.com.taskregister.repository;

import br.com.taskregister.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Task findByTitle(String title);

    Task findByHourAndDate(LocalDateTime hour, LocalDate date);
}
