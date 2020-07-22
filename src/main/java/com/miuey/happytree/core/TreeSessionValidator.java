package com.miuey.happytree.core;

import java.util.List;

import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeSession;
import com.miuey.happytree.exception.TreeException;

class TreeSessionValidator extends TreeValidator {

	protected TreeSessionValidator(TreeManager manager) {
		super(manager);
	}

	
	void validateMandatorySessionId(TreePipeline pipeline) {
		String sessionId = (String) pipeline.getAttribute("sessionId");
		
		if (sessionId == null || sessionId.length() == 0) {
			throw this.throwIllegalArgumentException(TreeRepositoryMessage.
					INVALID_INPUT);
		}
	}
	
	void validateDuplicatedSessionId(TreePipeline pipeline)
			throws TreeException {
		String sessionId = (String) pipeline.getAttribute("sessionId");
		List<TreeSession> sessions = getManager().getTransaction().sessions();

		for (TreeSession session : sessions) {
			if (sessionId.equals(session.getSessionId())) {
				throw this.throwTreeException(TreeRepositoryMessage.
						DUPLICATED_SESSION);
			}
		}
	}
	
	void validateNoDefinedSession() throws TreeException {
		TreeSession session = getManager().getTransaction().currentSession();
		
		if (session == null) {
			throw this.throwTreeException(TreeRepositoryMessage.
					NO_DEFINED_SESSION);
		}
	}
	
	void validateNoActiveSession() throws TreeException {
		TreeSession session = getManager().getTransaction().currentSession();
		
		if (session != null && !session.isActive()) {
			throw this.throwTreeException(TreeRepositoryMessage.
					NO_ACTIVE_SESSION);
		}
	}
}
