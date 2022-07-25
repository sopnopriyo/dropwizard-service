package com.example.helloworld.resources;

import com.example.helloworld.core.Task;
import com.example.helloworld.db.DailyTaskDAO;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Path("daily-tasks")
@Produces(MediaType.APPLICATION_JSON)
public class DailyTaskResource {
    private DailyTaskDAO taskDAO;

    public DailyTaskResource(DailyTaskDAO taskDAO){
        this.taskDAO = taskDAO;
    }

    @GET
    @UnitOfWork
    public List<Task> allDailyTasks() {
        return taskDAO.findAll();
    }

    @GET
    @Path("{id}")
    @UnitOfWork
    public Task getDailyTask(@PathParam("id") Long id) {
        Task task = taskDAO.findById(id);
        if (task == null) {
            throw  new NotFoundException("No such user.");
        }
        return task;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Task addDailyTask(Task newTask){
        newTask.setCreatedAt(new Date());
        newTask.setUpdatedAt(new Date());
        return taskDAO.create(newTask);
    }

    @Path("{id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @UnitOfWork
    public Task updateDailyTask(@PathParam("id") Long id, Task updatedTask) {
        Task existingTask = taskDAO.findById(id);
        if( existingTask == null ) {
            throw new WebApplicationException("No such element found", Response.Status.NOT_FOUND);
        }
        existingTask.setUpdatedAt(new Date());
        existingTask.setName(updatedTask.getName());
        existingTask.setCompleted(updatedTask.getCompleted());
        return taskDAO.update(existingTask);
    }

    @Path("{id}")
    @DELETE
    @UnitOfWork
    public void delete( @PathParam("id") Long id ) {
        if(!taskDAO.deleteById(id)) {
            throw new WebApplicationException("No such element found", Response.Status.NOT_FOUND);
        }
    }
}
