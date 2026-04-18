package fullstack.examples;

import fullstack.examples.domain.Event;
import fullstack.examples.repositories.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventRepositoryTest {

    @Autowired
    private EventRepository repository;

    @Test
    void testSave() {
        Event event = new Event("Test Event", "Room A");
        Event saved = repository.save(event);

        assertNotNull(saved.getId());
    }

    @Test
    void testUpdateEvent() {
        // Create
        Event event = new Event("Initial", "Room A");
        Event saved = repository.save(event);

        // Update
        saved.setName("Updated");
        saved.setLocation("Room B");
        repository.save(saved);

        // Verify
        Event updated = repository.findById(saved.getId()).orElseThrow();

        assertAll(
                () -> assertEquals("Updated", updated.getName()),
                () -> assertEquals("Room B", updated.getLocation())
        );
    }

    @Test
    void testDeleteEvent() {
        // Create
        Event event = new Event("To Delete", "Room X");
        Event saved = repository.save(event);

        // Delete
        repository.deleteById(saved.getId());

        // Verify
        boolean exists = repository.existsById(saved.getId());

        assertFalse(exists);
    }
}

