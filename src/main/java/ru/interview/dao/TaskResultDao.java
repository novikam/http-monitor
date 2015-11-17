package ru.interview.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.interview.model.TaskEntity;
import ru.interview.model.TaskResultEntity;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by novikam on 17.11.15.
 */
@Component
public interface TaskResultDao extends JpaRepository<TaskResultEntity, Long> {

    default TaskResultEntity find(EntityManager entityManager, String host, int port, String path) {
        List<TaskResultEntity> resultList = entityManager.createNamedQuery("TaskResultEntity.find", TaskResultEntity.class)
                .setParameter("host", host)
                .setParameter("port", port)
                .setParameter("path", path).getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    default TaskResultEntity find(EntityManager entityManager, TaskEntity taskEntity){
        return find(entityManager, taskEntity.getHost(), taskEntity.getPort(), taskEntity.getPath());
    }
}
