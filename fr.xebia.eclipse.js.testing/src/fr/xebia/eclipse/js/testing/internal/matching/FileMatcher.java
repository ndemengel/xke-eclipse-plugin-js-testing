package fr.xebia.eclipse.js.testing.internal.matching;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.search.core.text.TextSearchEngine;
import org.eclipse.search.core.text.TextSearchRequestor;
import org.eclipse.search.core.text.TextSearchScope;

import fr.xebia.eclipse.js.testing.internal.Logger;
import fr.xebia.eclipse.js.testing.internal.Preferences;

public class FileMatcher {

	private static final Pattern ANY_CONTENT = Pattern.compile("");

	private final TextSearchEngine searchEngine;
	private final Preferences preferences;
	private final Logger logger;

	public FileMatcher(TextSearchEngine searchEngine, Preferences preferences, Logger logger) {
		this.searchEngine = searchEngine;
		this.preferences = preferences;
		this.logger = logger;
	}

	public IFile match(IFile file) {
		TextSearchScope newSearchScope = createSearchScope(file);

		ResultCollector resultCollector = new ResultCollector();

		search(newSearchScope, resultCollector);

		if (resultCollector.hasResults()) {
			return resultCollector.firstResult();
		}
		return null;
	}

	private TextSearchScope createSearchScope(IFile selectedFile) {
		TestFileNamePattern testFilePattern = preferences.get(selectedFile.getProject()).getTestFileNamePattern();

		String selectedFileBasename = selectedFile.getFullPath().removeFileExtension().lastSegment();

		String correspondingFileName = testFilePattern.evaluate(selectedFileBasename).getCorrespondingFileName();

		Pattern searchedFilePattern = Pattern.compile(correspondingFileName + "\\.js");

		IResource[] rootRessources = { selectedFile.getProject() };

		// to extend search to the whole workspace:
		// IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();

		return TextSearchScope.newSearchScope(rootRessources, searchedFilePattern, false);
	}

	private void search(TextSearchScope scope, TextSearchRequestor requestor) {
		try {
			IStatus searchStatus = searchEngine.search(scope, requestor, ANY_CONTENT, null);

			if (searchStatus.getCode() != IStatus.OK) {
				logger.warning("Search failed with status: " + searchStatus);
			}
		} catch (Exception e) {
			logger.error("Search failed", e);
		}
	}

	private static class ResultCollector extends TextSearchRequestor {

		private final Set<IFile> files = new HashSet<IFile>();

		@Override
		public boolean acceptFile(IFile file) throws CoreException {
			return files.add(file);
		}

		public IFile firstResult() {
			return files.iterator().next();
		}

		public boolean hasResults() {
			return !files.isEmpty();
		}
	}
}
