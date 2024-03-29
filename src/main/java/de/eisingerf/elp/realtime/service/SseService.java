package de.eisingerf.elp.realtime.service;

import de.eisingerf.elp.shared.realtime.Event;
import de.eisingerf.elp.shared.realtime.EventStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
@Slf4j
public class SseService implements EventStream {

	private final CopyOnWriteArraySet<SseEmitter> emitters = new CopyOnWriteArraySet<>();

	public void addEmitter(SseEmitter emitter) {
		// Logging
		emitter.onCompletion(() -> {
			log.info("Emitter {} completed", emitter);
			this.removeEmitter(emitter);
		});
		emitter.onError(error -> {
			log.warn(String.format("Emitter %s error", emitter), error);
			this.removeEmitter(emitter);
		});
		emitter.onTimeout(() -> {
			log.warn("Emitter {} timeout", emitter);
			this.removeEmitter(emitter);
		});
		emitters.add(emitter);
		log.info("Add emitter {}. Now {} emitters.", emitter, emitters.size());
	}

	protected void removeEmitter(SseEmitter emitter) {
		emitter.complete();
		emitters.remove(emitter);
		log.info("Remove emitter {}. Now {} emitters.", emitter, emitters.size());
	}

	@Scheduled(fixedRate = 10_000)
	protected void sendKeepAlive() {
		log.trace("Send keep-alive");
		SseEmitter.SseEventBuilder builder = SseEmitter.event().comment("keep-alive").data("Tset");
		send(builder);
	}

	@Override
	public void send(Event event) {
		SseEmitter.SseEventBuilder builder = SseEmitter.event().id(event.id().toString()).name(event.name().toString()).data(event.data());
		if (event.comment() != null) {
			builder.comment(event.comment());
		}
		this.send(builder);
	}

	protected void send(SseEmitter.SseEventBuilder sseEventBuilder) {
		for (SseEmitter emitter : emitters) {
			try {
				emitter.send(sseEventBuilder);
			} catch (IOException e) {
				log.warn(String.format("Exception on send for emitter %s", emitter), e);
			}
		}
	}
}
