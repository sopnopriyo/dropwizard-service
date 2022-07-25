package com.example.helloworld.resources;

import com.example.helloworld.core.Task;
import com.example.helloworld.db.DailyTaskDAO;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link PersonResource}.
 */
@ExtendWith(DropwizardExtensionsSupport.class)
class DailyTaskResourceTest {
    private static final DailyTaskDAO DAO = mock(DailyTaskDAO.class);
    public static final ResourceExtension RULE = ResourceExtension.builder()
            .addResource(new DailyTaskResource(DAO))
            .build();

    @AfterEach
    void tearDown() {
        reset(DAO);
    }

    @Test
    void getPersonSuccess() {
        final Task person = new Task();
        person.setId(1L);

        when(DAO.findById(1L)).thenReturn(person);

        Task found = RULE.target("/daily-tasks/1").request().get(Task.class);

        assertThat(found.getId()).isEqualTo(person.getId());
        verify(DAO).findById(1L);
    }

    @Test
    void getPersonNotFound() {
        when(DAO.findById(2L)).thenReturn(null);
        final Response response = RULE.target("/daily-tasks/2").request().get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(DAO).findById(2L);
    }
}
