package com.example.helloworld.db;

import com.example.helloworld.core.Task;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class DailyTaskDAO extends AbstractDAO<Task> {

    public DailyTaskDAO(SessionFactory factory) {
        super(factory);
    }

    public Task findById(Long id) {
        return get(id);
    }

    public Task create(Task task) {
        return persist(task);
    }

    public Task update(Task task) {
        return (Task) currentSession().merge(task);
    }

    public List<Task> findAll() {
        return list(namedTypedQuery("findAll"));
    }

    public boolean deleteById(Long id) {
        Task task = findById(id);
        if ( task == null) {
            return false;
        }
        currentSession().delete(task);
        return true;
    }
}
