package fr.xebia.eclipse.js.testing.internal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import fr.xebia.eclipse.js.testing.JsTesting;
import fr.xebia.eclipse.js.testing.internal.matching.TestFileNamePattern;

public class Preferences {

	private final Map<IProject, ScopedPreferenceStore> projectStores = new HashMap<IProject, ScopedPreferenceStore>();

	private static final String BASE = "fr.xebia.eclipse.js.testing.prefs.";
	private static final String TEST_FILE_NAME_TEMPLATE = BASE + "testFileNameTemplate";
	
	public static final String DEFAULT_TEST_FILE_NAME_TEMPLATE = TestFileNamePattern.SRC_FILE_VARIABLE + "Test";

	private final Logger logger;

	public Preferences(Logger logger) {
		this.logger = logger;
	}

	public ProjectPreferences get(IProject project) {
		if (project == null) {
			throw new NullPointerException("project");
		}
		return new ProjectPreferences(project, getStore(project), logger);
	}

	private ScopedPreferenceStore getStore(IProject project) {
		if (projectStores.containsKey(project)) {
			return projectStores.get(project);
		}

		// AbstractUIPlugin comes with its own preference store, but it concerns the whole workspace
		// so let's create stores for each project using the plugin
		ProjectScope projectScope = new ProjectScope(project);
		ScopedPreferenceStore store = new ScopedPreferenceStore(projectScope, JsTesting.PLUGIN_ID);
		store.setSearchContexts(new IScopeContext[] { projectScope });
		projectStores.put(project, store);

		return store;
	}

	private static String orDefault(String value, String defaultValue) {
		return value != null && !value.isEmpty() ? value : defaultValue;
	}

	public static class ProjectPreferences {

		private final IProject project;
		private final ScopedPreferenceStore store;
		private final Logger logger;

		public ProjectPreferences(IProject project, ScopedPreferenceStore store, Logger logger) {
			this.project = project;
			this.store = store;
			this.logger = logger;
		}

		public void setTestFileNameTemplate(String pattern) {
			store.setValue(TEST_FILE_NAME_TEMPLATE, pattern);
		}

		public String getTestFileNameTemplate() {
			return orDefault(store.getString(TEST_FILE_NAME_TEMPLATE), DEFAULT_TEST_FILE_NAME_TEMPLATE);
		}

		public TestFileNamePattern getTestFileNamePattern() {
			return new TestFileNamePattern(getTestFileNameTemplate());
		}

		public void save() {
			try {
				store.save();
			} catch (IOException e) {
				logger.error("Could not save preferences for project: " + project.getName(), e);
			}
		}
	}
}
