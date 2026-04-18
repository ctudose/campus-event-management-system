package fullstack.examples;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllEvents() throws Exception {
        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateEventWithValidData() throws Exception {
        String json = """
                {
                  "name": "MockMvc Event",
                  "location": "Room Z"
                }
                """;

        mockMvc.perform(post("/api/events")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("MockMvc Event"))
                .andExpect(jsonPath("$.location").value("Room Z"));
    }

    @Test
    void testCreateEventWithInvalidData() throws Exception {
        String json = """
                {
                  "name": "",
                  "location": ""
                }
                """;

        mockMvc.perform(post("/api/events")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void testCreateDuplicateEvent() throws Exception {
        String json = """
                {
                  "name": "Duplicate Event",
                  "location": "Hall D"
                }
                """;

        mockMvc.perform(post("/api/events")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/events")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Duplicate event"));
    }

    @Test
    void testGetEventByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/events/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteEvent() throws Exception {
        String json = """
                {
                  "name": "Delete Target",
                  "location": "Room Y"
                }
                """;

        String response = mockMvc.perform(post("/api/events")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // For a beginner-friendly lab, you may manually inspect the response.
        // In later refinement, students can parse the ID from JSON and delete dynamically.
    }

    @Test
    void testDeleteNonExistingEvent() throws Exception {
        mockMvc.perform(delete("/api/events/99999"))
                .andExpect(status().isNotFound());
    }


}
