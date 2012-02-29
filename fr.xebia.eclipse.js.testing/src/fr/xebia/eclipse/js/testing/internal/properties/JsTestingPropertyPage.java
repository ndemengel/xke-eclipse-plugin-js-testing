package fr.xebia.eclipse.js.testing.internal.properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

import fr.xebia.eclipse.js.testing.JsTesting;
import fr.xebia.eclipse.js.testing.internal.Preferences;
import fr.xebia.eclipse.js.testing.internal.Preferences.ProjectPreferences;
import fr.xebia.eclipse.js.testing.internal.matching.TestFileNamePattern;

public class JsTestingPropertyPage extends PropertyPage {

	private final Preferences preferences;
	private Text testFileTemplateField;

	public JsTestingPropertyPage() {
		preferences = JsTesting.get().getPreferences();
	}

	@Override
	protected Control createContents(Composite parent) {
		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginRight = 10;
		layout.numColumns = 2;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		createFields(composite);

		Dialog.applyDialogFont(composite);

		return parent;
	}

	private void createFields(Composite parent) {
		ProjectPreferences projectPreferences = preferences.get((IProject) getElement());

		GridData labelAndFieldLayout = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		labelAndFieldLayout.horizontalIndent = 30;

		Label testFileTemplateLabel = new Label(parent, SWT.NONE);
		testFileTemplateLabel.setText("Rule for naming test files:");

		testFileTemplateField = new Text(parent, SWT.SINGLE | SWT.BORDER);
		testFileTemplateField.setLayoutData(labelAndFieldLayout);
		testFileTemplateField.setText(projectPreferences.getTestFileNameTemplate());
		// TODO XKE: trigger validation on field change
		
		GridData rowLayout = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		rowLayout.horizontalSpan = 2;

		Label explainationLabel = new Label(parent, SWT.NONE);
		explainationLabel.setLayoutData(rowLayout);
		explainationLabel.setText("Use the variable " + TestFileNamePattern.SRC_FILE_VARIABLE
				+ " to represent the production source file.");
	}

	@Override
	public boolean performOk() {
		saveProperties();
		return super.performOk();
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		testFileTemplateField.setText(Preferences.DEFAULT_TEST_FILE_NAME_TEMPLATE);
	}

	private void validate() {
		// TODO XKE: validate user template
		// setMessage(errorMsg, IMessageProvider.ERROR);
		// setValid(false);
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (!visible) {
			return;
		}

		validate();
	}

	private void saveProperties() {
		// TODO XKE: save user template (use preferences)
	}
}
