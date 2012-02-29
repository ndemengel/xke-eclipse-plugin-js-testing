package fr.xebia.eclipse.js.testing;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import fr.xebia.eclipse.js.testing.internal.Logger;
import fr.xebia.eclipse.js.testing.internal.Preferences;

/**
 * The plugin activator, also serves as a provider for preferences and logger.
 */
public class JsTesting extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "fr.xebia.eclipse.js.testing";

	private static JsTesting instance;

	private Logger logger;
	private Preferences preferences;

	public static JsTesting get() {
		return instance;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		instance = this;

		logger = new Logger(getLog());
		preferences = new Preferences(logger);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		logger = null;

		instance = null;
		super.stop(bundleContext);
	}

	public Logger getLogger() {
		return logger;
	}

	public Preferences getPreferences() {
		return preferences;
	}
}
