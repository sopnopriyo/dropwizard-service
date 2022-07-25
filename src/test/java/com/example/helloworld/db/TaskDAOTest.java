package com.example.helloworld.db;

import com.example.helloworld.core.Task;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(DropwizardExtensionsSupport.class)
class TaskDAOTest {

    public DAOTestExtension daoTestRule = DAOTestExtension.newBuilder()
        .addEntityClass(Task.class)
        .build();

    private DailyTaskDAO dailyTaskDAO;

    @BeforeEach
    void setUp() throws Exception {
        dailyTaskDAO = new DailyTaskDAO(daoTestRule.getSessionFactory());
    }

    @Test
    void createPerson() {
        Task task = new Task();
        task.setName("Demo");
        task.setCompleted(true);

        final Task jeff = daoTestRule.inTransaction(() -> dailyTaskDAO.create(task));
        assertThat(jeff.getId()).isPositive();
        assertThat(jeff.getName()).isEqualTo(task.getName());
        assertThat(jeff.getCompleted()).isEqualTo(task.getCompleted());
        assertThat(dailyTaskDAO.findById(jeff.getId())).isEqualTo(jeff);
    }

    @Test
    void findAll() {
        Task task = new Task();
        task.setName("Demo");
        task.setCompleted(true);
        daoTestRule.inTransaction(() -> {
            dailyTaskDAO.create(task);
        });

        final List<Task> tasks = dailyTaskDAO.findAll();
        assertThat(tasks).extracting("name").containsOnly("Demo");
    }

//    @Test
//    void handlesNullFullName() {
//        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(()->
//            daoTestRule.inTransaction(() -> personDAO.create(new Person(null, "The null", 0))));
//    }
}
