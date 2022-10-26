package br.com.taskregister.service;

import br.com.taskregister.exceptionhandler.BusinessException;
import br.com.taskregister.model.Task;
import br.com.taskregister.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task save (Task task){

        Task existentTitle = taskRepository.findByTitle(task.getTitle());

        if(existentTitle !=null && !existentTitle.equals(task)) {
            throw new BusinessException("Title already registered!");
        }

        Task existentHourAndDate = taskRepository.findByHourAndDate(task.getHour(),task.getDate());

        if(existentHourAndDate !=null && !existentHourAndDate.equals(task)) {
            throw new BusinessException("Date and hour selected already have a compromisse scheduled");
        }

        return taskRepository.save(task);
    }

    public void delete (Long id) {
        taskRepository.deleteById(Math.toIntExact(id));
    }
}
