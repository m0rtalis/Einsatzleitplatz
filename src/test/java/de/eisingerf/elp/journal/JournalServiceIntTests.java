package de.eisingerf.elp.journal;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.eisingerf.elp.journal.controller.dto.JournalEntryDto;
import de.eisingerf.elp.journal.controller.dto.input.CreateJournalEntryDto;
import de.eisingerf.elp.journal.controller.dto.input.DeleteJournalEntryDto;
import de.eisingerf.elp.journal.entity.Component;
import de.eisingerf.elp.journal.service.JournalService;
import de.eisingerf.elp.operation.service.OperationService;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class JournalServiceIntTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private OperationService operationService;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	UserDetailsManager userDetailsManager;

	@Autowired
	JournalService journalService;

	@Autowired
	private SessionFactory sessionFactory;

	private UUID operationId;

	@BeforeEach
	void createOperation() {
		sessionFactory.getSchemaManager().truncateMappedObjects();
		userDetailsManager.createUser(User.builder().username("user").password("password").roles("USER").build());

		var operation = operationService.createOperation("Test", null);
		operationId = operation.getId();
	}

	@Test
	void shouldCreateJournalEntry() throws Exception {
		var body = objectMapper.writeValueAsString(new CreateJournalEntryDto(operationId, "Test Entry"));
		var response = mockMvc.perform(post("/journal").contentType(MediaType.APPLICATION_JSON_VALUE).content(body));

		response.andExpect(status().isCreated());
		var createdEntry = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), JournalEntryDto.class);
		System.out.println(createdEntry);
	}

	@Test
	void shouldDeleteEntry() throws Exception {
		var entry = journalService.create(operationId, Component.JOURNAL, "Test Entry");

		var body = objectMapper.writeValueAsString(new DeleteJournalEntryDto("Delete entry"));
		var response = mockMvc.perform(delete("/journal/" + entry.getId().toString()).contentType(MediaType.APPLICATION_JSON_VALUE).content(body));

		response.andExpect(status().isNoContent());
		var deletedEntry = mockMvc.perform(get("/journal/" + entry.getId().toString()));
		deletedEntry.andExpect(status().isOk());
		deletedEntry.andExpect(content().json("{id: %s, isDeleted: true}".formatted(entry.getId())));
	}
}
