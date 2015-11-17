package ru.interview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.interview.dao.TaskDao;
import ru.interview.dao.TaskResultDao;
import ru.interview.model.TaskEntity;
import ru.interview.model.TaskResultEntity;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by novikam on 16.11.15.
 */
@Component
@PropertySource("classpath:application.properties")
public class RequestService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TaskResultDao taskResultDao;

    @Autowired
    private EntityManager entityManager;

    @Value("${requestbuffer.size}")
    private int maximumBufferSize;

    private ArrayBlockingQueue<TaskEntity> requestCache;

    private Client client;

    @PostConstruct
    private void init() {
        requestCache = new ArrayBlockingQueue(maximumBufferSize);
        client = ClientBuilder.newClient();
        createTaskHandler();
        List<TaskEntity> all = taskDao.findAll();
        if (!all.isEmpty()) {
            all.stream().forEach(task -> requestCache.add(task));
        }
    }

    public boolean addTask(TaskEntity taskEntity) {
        if (!requestCache.contains(taskEntity)) {
            if (requestCache.remainingCapacity() < 1) {
                return false;
            }
            taskDao.saveAndFlush(taskEntity);
            requestCache.add(taskEntity);
        }
        return true;
    }

    private void createTaskHandler() {
        Thread handler = new Thread(() -> {
            while (true) {
                if (!requestCache.isEmpty()) {
                    TaskEntity peek = requestCache.peek();
                    executeTask(peek);
                    requestCache.remove(peek);
                    taskDao.delete(peek);
                }
            }
        });
        handler.start();
    }

    private void executeTask(TaskEntity entity) {
        int status;
        try {
            status = client.target(entity.buildRequestPath()).request().get().getStatus();
        } catch (Exception e) {
            status = HttpStatus.BAD_GATEWAY.value();
        }
        TaskResultEntity taskResultFromDB = taskResultDao.find(entityManager, entity);
        if (taskResultFromDB != null) {
            if (taskResultFromDB.getStatus() != status) {
                taskResultFromDB.setStatus(status);
                taskResultDao.save(taskResultFromDB);
            }
        } else {
            TaskResultEntity taskResultEntity = TaskResultEntity.fromTaskEntity(entity);
            taskResultEntity.setStatus(status);
            taskResultDao.save(taskResultEntity);
        }

    }

    public Integer getStatus(String host, int port, String path) {
        TaskResultEntity taskResultEntity = taskResultDao.find(entityManager, host, port, path);
        return taskResultEntity != null ? taskResultEntity.getStatus() : null;
    }
}
