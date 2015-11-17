package ru.interview.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by novikam on 16.11.15.
 */
@JsonAutoDetect
@Entity
@Table(name = "task")
public class TaskEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String host;

    private int port;

    private String path;

    public TaskEntity() {
    }

    public TaskEntity(String host, int port, String path) {
        this.host = host;
        this.port = port;
        this.path = path;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskEntity that = (TaskEntity) o;

        if (port != that.port) return false;
        if (!host.equals(that.host)) return false;
        return path.equals(that.path);

    }

    @Override
    public int hashCode() {
        int result = host.hashCode();
        result = 31 * result + port;
        result = 31 * result + path.hashCode();
        return result;
    }

    public String buildRequestPath() {
        StringBuilder sb = new StringBuilder("http://");
        sb.append(host).append(":").append(port).append("/").append(path);
        return sb.toString();
    }
}
