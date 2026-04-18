package fullstack.examples;

import fullstack.examples.domain.Event;
import fullstack.examples.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventServiceTest {

    @Autowired
    private EventService service;

    @Test
    void testCreateEventWithValidData() {
        Event event = new Event("JUnit Conference", "Room A");

        Event saved = service.createEvent(event);

        assertAll(
                () -> assertNotNull(saved.getId()),
                () -> assertEquals("JUnit Conference", saved.getName()),
                () -> assertEquals("Room A", saved.getLocation())
        );
    }

    @Test
    void testCreateEventWithInvalidData() {
        Event invalidEvent = new Event("", "");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.createEvent(invalidEvent)
        );

        assertEquals("Invalid event data", exception.getMessage());
    }

    @Test
    void testCreateDuplicateEvent() {
        Event first = new Event("Testing Workshop", "Lab 1");
        Event duplicate = new Event("Testing Workshop", "Lab 1");

        service.createEvent(first);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.createEvent(duplicate)
        );

        assertEquals("Duplicate event", exception.getMessage());
    }

    @Test
    void testDeleteExistingEvent() {
        Event event = new Event("Delete Me", "Room X");
        Event saved = service.createEvent(event);

        boolean deleted = service.deleteEvent(saved.getId());
        assertAll(
                () -> assertTrue(deleted),
                () -> assertTrue(service.getEventById(saved.getId()).isEmpty()));
    }

}
