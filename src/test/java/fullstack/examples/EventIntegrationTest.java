package fullstack.examples;

import fullstack.examples.domain.Event;
import fullstack.examples.repositories.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventIntegrationTest {

    @Autowired
    private EventRepository repository;

    @Test
    void testSaveAndFindAll() {
        Event event = new Event("Integration Event", "Hall B");
        repository.save(event);

        List<Event> events = repository.findAll();

        assertAll(
                () -> assertFalse(events.isEmpty()),
                () -> assertTrue(events.stream().anyMatch(e -> e.getName().equals("Integration Event")))
        );
    }

    @Test
    void testFindByLocationIgnoreCase() {
        repository.save(new Event("Event One", "Hall C"));
        repository.save(new Event("Event Two", "hall c"));

        List<Event> results = repository.findByLocationIgnoreCase("HALL C");

        assertEquals(2, results.size());
    }


}

