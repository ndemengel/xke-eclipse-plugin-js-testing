package fr.xebia.eclipse.js.testing.internal.matching;

/**
 * A test file name pattern in charge to evaluate whether source files are test files (ie. whether they conform to this
 * pattern).
 */
public class TestFileNamePattern {

	/**
	 * Represents the name of the tested source file within the test file name.
	 * <p>
	 * User must use this variable to specify his naming pattern for test files, for instance: <tt>${srcFile}Test</tt>
	 * specifies that test files must be named the same as their corresponding source file, with the suffix
	 * <tt>Test</tt>.
	 * </p>
	 */
	public static final String SRC_FILE_VARIABLE = "${srcFile}";

	private final String prefix;
	private final String suffix;

	public TestFileNamePattern(String template) {
		int varStart = template.indexOf("$");
		int varEnd = template.indexOf("}");

		prefix = template.substring(0, varStart);
		suffix = varEnd + 1 == template.length() ? "" : template.substring(varEnd + 1);
	}

	/**
	 * Evaluates whether the file name given is that of a test file.
	 * 
	 * @param fileBaseName
	 *            the file name to test
	 * @return the result of the evaluation
	 * @see {@link FileNameEvaluation}
	 */
	public FileNameEvaluation evaluate(String fileBaseName) {
		if (fileBaseName.startsWith(prefix) && fileBaseName.endsWith(suffix)) {
			String srcFile = fileBaseName.replaceFirst("^" + prefix, "").replaceFirst(suffix + "$", "");
			return new FileNameEvaluation(true, srcFile);
		}

		String testFile = String.format("%s%s%s", prefix, fileBaseName, suffix);
		return new FileNameEvaluation(false, testFile);
	}
}
