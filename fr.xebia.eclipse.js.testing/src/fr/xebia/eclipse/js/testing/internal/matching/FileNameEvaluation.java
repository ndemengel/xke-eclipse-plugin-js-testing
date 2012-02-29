package fr.xebia.eclipse.js.testing.internal.matching;

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
