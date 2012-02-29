package fr.xebia.eclipse.js.testing.internal.matching;

import org.eclipse.core.resources.IFile;
import org.eclipse.search.core.text.TextSearchEngine;

import fr.xebia.eclipse.js.testing.internal.Logger;
import fr.xebia.eclipse.js.testing.internal.Preferences;

public class FileMatcher {

	private final TextSearchEngine searchEngine;
	private final Preferences preferences;
	private final Logger logger;

	public FileMatcher(TextSearchEngine searchEngine, Preferences preferences, Logger logger) {
		this.searchEngine = searchEngine;
		this.preferences = preferences;
		this.logger = logger;
	}

	public IFile match(IFile file) {
		// TODO XKE: search for corresponding test/source file
		// * create search scope
		// * use searchEngine to search for corresponding file
		// * return result
		return null;
	}
}
