package fullstack.examples.controller;

import fullstack.examples.domain.Event;
import fullstack.examples.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EventViewController {

    private final EventService service;

    public EventViewController(EventService service) {
        this.service = service;
    }

    @GetMapping("/events/view")
    public String viewEvents(Model model) {
        model.addAttribute("events", service.getAllEvents());
        return "events";
    }

    @PostMapping("/events/add")
    public String addEvent(@RequestParam String name,
                           @RequestParam String location,
                           Model model) {
        try {
            service.createEvent(new Event(name, location));
            return "redirect:/events/view";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("events", service.getAllEvents());
            return "events";
        }
    }

    @PostMapping("/events/delete/{id}")
    public String deleteEvent(@PathVariable Integer id) {
        service.deleteEvent(id);
        return "redirect:/events/view";
    }

}
