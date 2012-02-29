package fr.xebia.eclipse.js.testing.internal.commands;

import java.util.Collection;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.search.core.text.TextSearchEngine;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;

import fr.xebia.eclipse.js.testing.JsTesting;
import fr.xebia.eclipse.js.testing.internal.Logger;
import fr.xebia.eclipse.js.testing.internal.Messages;
import fr.xebia.eclipse.js.testing.internal.matching.FileMatcher;

public class JumpActionHandler extends AbstractHandler {

	private final Logger logger;
	private final FileMatcher fileMatcher;

	public JumpActionHandler() {
		this(JsTesting.get(), new FileMatcher(TextSearchEngine.create(), JsTesting.get().getPreferences(), JsTesting
				.get().getLogger()));
	}

	public JumpActionHandler(JsTesting plugin, FileMatcher fileMatcher) {
		this.logger = plugin.getLogger();
		this.fileMatcher = fileMatcher;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IFile selectedFile = getSelectedJsFile(event);
		if (selectedFile == null) {
			return null;
		}

		IFile result = fileMatcher.match(selectedFile);

		if (result == null) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event), Messages.pluginName,
					Messages.jump_noMatchingFile);
		} else {
			openEditor(result, event);
		}

		return null;
	}

	private IFile getSelectedJsFile(ExecutionEvent event) {
		Object firstElement = getUniqueSelectedElement(event);
		if (firstElement instanceof IAdaptable) {
			IFile file = getJsFileFrom((IAdaptable) firstElement);
			if (file != null) {
				return file;
			}
		}

		IFile file = getJsFileFrom(HandlerUtil.getActiveEditorInput(event));
		if (file != null) {
			return file;
		}

		return null;
	}

	private Object getUniqueSelectedElement(ExecutionEvent event) {
		if (!(event.getApplicationContext() instanceof EvaluationContext)) {
			logger.warning("Unsupported context: " + event.getApplicationContext()); //$NON-NLS-1$
			return null;
		}

		EvaluationContext context = (EvaluationContext) event.getApplicationContext();

		Collection<?> selectedElements = (Collection<?>) context.getDefaultVariable();
		if (selectedElements == null || selectedElements.size() != 1) {
			return null;
		}

		return selectedElements.iterator().next();
	}

	private IFile getJsFileFrom(IAdaptable adaptable) {
		if (adaptable != null) {
			IFile file = (IFile) adaptable.getAdapter(IFile.class);
			if (file != null && "js".equals(file.getFileExtension())) { //$NON-NLS-1$
				return file;
			}
		}
		return null;
	}

	private void openEditor(IFile file, ExecutionEvent event) {
		IWorkbenchPage activePage = getActivePage(event);
		if (activePage == null) {
			return;
		}

		try {
			IDE.openEditor(activePage, file);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	private static IWorkbenchPage getActivePage(ExecutionEvent event) {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		return window == null ? null : window.getActivePage();
	}
}
