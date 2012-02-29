package fr.xebia.eclipse.js.testing.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "fr.xebia.eclipse.js.testing.internal.messages"; //$NON-NLS-1$

	public static String jump_noMatchingFile;
	public static String pluginName;

	static {
		// populates static fields of this class with content of messages*.properties
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
