package ru.interview.model;

import javax.persistence.*;

/**
 * Created by novikam on 16.11.15.
 */
@Entity
@Table(name = "task_result")
@NamedQuery(name = "TaskResultEntity.find",
        query = "select t from TaskResultEntity t where t.host = :host and t.port = :port and t.path = :path")
public class TaskResultEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String host;

    private int port;

    private String path;

    private int status;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public TaskResultEntity() {
    }

    public TaskResultEntity(String host, int port, String path) {
        this.host = host;
        this.port = port;
        this.path = path;
    }

    public static TaskResultEntity fromTaskEntity(TaskEntity taskEntity){
        String host = taskEntity.getHost();
        int port = taskEntity.getPort();
        String path = taskEntity.getPath();
        return new TaskResultEntity(host,port,path);
    }
}
