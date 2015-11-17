package ru.interview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.interview.model.TaskEntity;
import ru.interview.service.RequestService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by novikam on 16.11.15.
 */

@Component
@Path("/")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GET
    @Path("application-status/{host}/{port}/{path}")
    @Produces("application/json")
    public Response getStatus(@PathParam("host") String host,
                            @PathParam("port") int port,
                            @PathParam("path") String path){
        path = "/" + path;
        Integer status = requestService.getStatus(host, port, path);
        if(status != null){
            String statusJson = "{\"status\":" + status + "}";
            return Response.ok(statusJson, MediaType.APPLICATION_JSON_TYPE).build();
        }
        return Response.status(HttpStatus.NOT_FOUND.value()).build();
    }

    @POST
    @Path("application-check")
    @Consumes("application/json")
    public Response addRequest(TaskEntity entity){
        if (requestService.addTask(entity)){
            return Response.accepted().build();
        }
        return Response.status(HttpStatus.SERVICE_UNAVAILABLE.value()).build();
    }

}
