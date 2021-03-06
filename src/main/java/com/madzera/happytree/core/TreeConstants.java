package com.madzera.happytree.core;

public class TreeConstants {
	
	protected TreeConstants() {}

	
	/*
	 * Configuration constants to be accessed within the ATP package.
	 */
	protected class Config {
		static final String ERROR_MESSAGE_FILE_LOCATION = "exception.properties";
		
		protected Config() {}
		
		protected String getExceptionFileLocation() {
			return ERROR_MESSAGE_FILE_LOCATION;
		}
	}
	
	class Error {
		private Error() {}
		//---------------RUNTIME--------------
		static final String INVALID_INPUT_ELEMENTS = "com.madzera.happytree.error.runtime.params";
		//----------------TREE----------------
		static final String DUPLICATED_ID_ERROR = "com.madzera.happytree.error.checked.tree.duplicatedid";
		static final String INCORRECT_SESSION = "com.madzera.happytree.error.checked.tree.notbelongstosession";
		static final String DIFFERENT_TYPES_ERROR = "com.madzera.happytree.error.checked.tree.mismatchelement";
		static final String CUT_COPY_DETACHED_ELEMENT = "com.madzera.happytree.error.checked.tree.copycutremote.detached";
		static final String PERSIST_ATTACHED_ELEMENT = "com.madzera.happytree.error.checked.tree.persist.attached";
		static final String UPDATE_NOT_EXISTED_ELEMENT = "com.madzera.happytree.error.checked.tree.update.notexisted";
		static final String HANDLE_ROOT = "com.madzera.happytree.error.checked.tree.root";
		//---------------SESSION--------------
		static final String DUPLICATED_SESSION_ID_ERROR = "com.madzera.happytree.error.checked.session.duplicatedid";
		static final String NO_DEFINED_SESSION = "com.madzera.happytree.error.checked.session.nodefinedsession";
		static final String NO_ACTIVE_SESSION = "com.madzera.happytree.error.checked.session.noactivesession";
		
		class Internal {
			private Internal() {}
			//---------------RUNTIME--------------
			static final String GENERAL_ERROR = "com.madzera.happytree.error.runtime.general";
		}
	}
}
