package fullstack.examples.service;

import fullstack.examples.domain.Event;
import fullstack.examples.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public List<Event> getAllEvents() {
        return repository.findAll();
    }

    public Optional<Event> getEventById(Integer id) {
        return repository.findById(id);
    }

    public Event createEvent(Event event) {
        validate(event);
        checkDuplicate(event, null);
        return repository.save(event);
    }

    public Optional<Event> updateEvent(Integer id, Event updated) {
        validate(updated);
        checkDuplicate(updated, id);

        return repository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setLocation(updated.getLocation());
            return repository.save(existing);
        });
    }

    public boolean deleteEvent(Integer id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    public List<Event> getEventsByLocation(String location) {
        return repository.findByLocationIgnoreCase(location);
    }

    private void validate(Event event) {
        if (event.getName() == null || event.getName().isBlank() ||
                event.getLocation() == null || event.getLocation().isBlank()) {
            throw new IllegalArgumentException("Invalid event data");
        }
    }

    private void checkDuplicate(Event event) {
        boolean duplicate = repository.findByLocationIgnoreCase(event.getLocation())
                .stream()
                .anyMatch(existing ->
                        existing.getName().equalsIgnoreCase(event.getName())
                );

        if (duplicate) {
            throw new IllegalArgumentException("Duplicate event");
        }
    }

    private void checkDuplicate(Event event, Integer currentId) {
        boolean duplicate = repository.findByLocationIgnoreCase(event.getLocation())
                .stream()
                .anyMatch(existing ->
                        existing.getName().equalsIgnoreCase(event.getName()) &&
                                !existing.getId().equals(currentId)
                );

        if (duplicate) {
            throw new IllegalArgumentException("Duplicate event");
        }
    }


}
