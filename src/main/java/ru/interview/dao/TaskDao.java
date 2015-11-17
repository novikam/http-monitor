package ru.interview.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.interview.model.TaskEntity;

/**
 * Created by novikam on 16.11.15.
 */
@Component
public interface TaskDao extends JpaRepository<TaskEntity, Long> {

}
