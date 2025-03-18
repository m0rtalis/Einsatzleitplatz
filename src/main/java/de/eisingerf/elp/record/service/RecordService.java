package de.eisingerf.elp.record.service;

import de.eisingerf.elp.shared.user.GetAuthenticatedUserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RecordService {

	Logger logger = LoggerFactory.getLogger(RecordService.class.getName());

	private final GetAuthenticatedUserId getAuthenticatedUserId;

	public RecordService(GetAuthenticatedUserId getAuthenticatedUserId) {
		this.getAuthenticatedUserId = getAuthenticatedUserId;
	}

	public void record(UUID operationId, String text) {
		UUID userId = getAuthenticatedUserId.getAuthenticatedUserId();
		logger.info("Operation: {}; User: {}; Text: {}",
					operationId,
					userId,
					text);
	}

}
