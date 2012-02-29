package fr.xebia.eclipse.js.testing.internal.commands;

//import static org.eclipse.swtbot.eclipse.finder.waits.Conditions.waitForEditor;
import static org.junit.Assert.assertEquals;

import org.eclipse.core.resources.IFile;
//import org.eclipse.swt.SWT;
//import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
//import org.eclipse.swtbot.swt.finder.keyboard.KeyboardFactory;
//import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
//import org.eclipse.ui.IEditorReference;
//import org.eclipse.ui.PartInitException;
//import org.hamcrest.BaseMatcher;
//import org.hamcrest.Description;
import org.junit.Test;

public class JumpActionHandlerTest extends JsProjectTestCase {

	private static final String JUMP_COMMAND = "fr.xebia.eclipse.js.testing.commands.jumpCommand";

	@Test
	public void should_open_test_file_when_in_source_file() throws Exception {
		// given
		IFile sourceFile = createFile("SomeConcept.js", "function someJsFun() { }");
		IFile testFile = createFile("SomeConceptTest.js", "assert(true);");

		openEditor(sourceFile);

		// when
		executeCommand(JUMP_COMMAND);

		// then
		assertEquals(testFile, getFileInActiveEditor());
	}

	@Test
	public void should_open_source_file_when_in_test_file() throws Exception {
		// given
		IFile sourceFile = createFile("SomeConcept.js", "function someJsFun() { }");
		IFile testFile = createFile("SomeConceptTest.js", "assert(true);");

		openEditor(testFile);

		// when
		executeCommand(JUMP_COMMAND);

		// then
		assertEquals(sourceFile, getFileInActiveEditor());
	}

//	@Test
//	public void should_inform_user_when_no_matching_file_can_be_found() throws Exception {
//		SWTWorkbenchBot bot = new SWTWorkbenchBot();
//		SWTBotPreferences.KEYBOARD_LAYOUT = "FR_FR";
//
//		// given
//		final IFile sourceFile = createFile("SomeConcept.js", "function someJsFun() { }");
//
//		openEditor(sourceFile);
//
//		bot.waitUntil(waitForEditor(new WithInput(sourceFile)));
//
//		// when
//		KeyboardFactory.getAWTKeyboard().pressShortcut(SWT.CTRL, 'j');
//
//		// then
//		assertEquals(sourceFile, bot.activeEditor().getReference().getEditorInput().getAdapter(IFile.class));
//	}
//
//	private static class WithInput extends BaseMatcher<IEditorReference> {
//
//		private final IFile sourceFile;
//
//		public WithInput(IFile sourceFile) {
//			this.sourceFile = sourceFile;
//		}
//
//		@Override
//		public boolean matches(Object editorRef) {
//			try {
//				return editorRef instanceof IEditorReference
//						&& sourceFile.equals(((IEditorReference) editorRef).getEditorInput().getAdapter(IFile.class));
//			} catch (PartInitException e) {
//				throw new RuntimeException(e);
//			}
//		}
//
//		@Override
//		public void describeTo(Description description) {
//			description.appendText("");
//		}
//	}
}
