package de.eisingerf.elp.journal;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.eisingerf.elp.journal.controller.dto.JournalEntryDto;
import de.eisingerf.elp.journal.controller.dto.input.CreateJournalEntryDto;
import de.eisingerf.elp.journal.controller.dto.input.DeleteJournalEntryDto;
import de.eisingerf.elp.journal.entity.JournalEntry;
import de.eisingerf.elp.journal.service.JournalService;
import de.eisingerf.elp.operation.service.OperationService;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class JournalFuncTests {
	//https://mbahardogan.medium.com/the-integration-test-with-spring-boot-4893d8151278
	@Autowired
	MockMvc mockMvc;

	@Autowired
	OperationService operationService;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	UserDetailsManager userDetailsManager;

	@Autowired
	JournalService journalService;

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	SessionFactory sessionFactory;

	private UUID operationId;

	@BeforeEach
	void beforeEach() {
		// Clean database
		sessionFactory.getSchemaManager().truncateMappedObjects();
		// Create User
		userDetailsManager.createUser(User.builder().username("user").password(
				"password").roles("USER").build());

		// Create Operation
		var operation = operationService.createOperation("Test", null);
		operationId = operation.getId();
	}

	@Nested
	class Create {
		@Test
		void shouldCreateJournalEntry() throws Exception {
			var body = objectMapper.writeValueAsString(new CreateJournalEntryDto(
					operationId,
					"Test Entry"));
			var response = mockMvc.perform(post("/journal").contentType(
					MediaType.APPLICATION_JSON_VALUE).content(body));

			response.andExpect(status().isCreated()).andDo(print());
			var createdEntry = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(),
													  JournalEntryDto.class);
			System.out.println(createdEntry);
		}
	}

	@Nested
	class Update {
		@Test
		void shouldUpdateTextOfJournalEntry() {}

		@Test
		void shouldUpdateVersionOfJournalEntry() {}
	}

	@Nested
	class Delete {
		JournalEntry entry;

		@BeforeEach
		void createJournalEntry() {
			entry = journalService.create(operationId, "Test Entry");
		}

		@Test
		void shouldCreateJournalEntryWhenReasonIsSupplied() {}

		@Test
		void shouldDeleteEntry() throws Exception {
			var body = objectMapper.writeValueAsString(new DeleteJournalEntryDto(
					"Delete entry"));

			var response = mockMvc.perform(delete("/journal/" + entry.getId().toString()).contentType(
					MediaType.APPLICATION_JSON_VALUE).content(body));

			response.andExpect(status().isNoContent());
			var deletedEntryResponse = mockMvc.perform(get("/journal/" + entry.getId().toString()));
			deletedEntryResponse.andExpect(status().isOk());
			var deletedEntry = objectMapper.readValue(deletedEntryResponse.andReturn().getResponse().getContentAsString(),
													  JournalEntryDto.class);
			assertTrue(deletedEntry.isDeleted());
		}

		@Test
		void shouldUpdateVersionOfJournalEntry() {}
	}
}
