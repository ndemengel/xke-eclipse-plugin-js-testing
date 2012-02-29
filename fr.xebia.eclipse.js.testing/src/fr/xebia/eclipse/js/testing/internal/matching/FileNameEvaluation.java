package fr.xebia.eclipse.js.testing.internal.matching;

/**
 * The result of evaluating a file name with a {@link TestFileNamePattern}.
 */
public class FileNameEvaluation {

	private final boolean testFile;
	private final String correspondingFileName;

	public FileNameEvaluation(boolean testFile, String correspondingFileName) {
		this.testFile = testFile;
		this.correspondingFileName = correspondingFileName;
	}

	public boolean isTestFile() {
		return testFile;
	}

	public String getCorrespondingFileName() {
		return correspondingFileName;
	}
}
