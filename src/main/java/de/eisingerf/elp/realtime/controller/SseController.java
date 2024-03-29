package de.eisingerf.elp.realtime.controller;

import de.eisingerf.elp.realtime.service.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * Controller handling Server-Side-Events.
 * Events like a new journal entry, new patient, status change of a unit etc will send to the client in real-time with this.
 */
@RestController
@PreAuthorize("hasRole('USER')")
public class SseController {

	private final SseService sseService;

	@Autowired
	public SseController(SseService sseService) {
		this.sseService = sseService;
	}

	@GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter subscribe() {
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
		sseService.addEmitter(emitter);
		return emitter;
	}
}
