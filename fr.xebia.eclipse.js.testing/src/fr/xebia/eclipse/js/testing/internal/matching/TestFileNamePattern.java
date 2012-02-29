package fr.xebia.eclipse.js.testing.internal.matching;

public class TestFileNamePattern {

	public static final String SRC_FILE_VARIABLE = "${srcFile}";

	private final String prefix;
	private final String suffix;

	public TestFileNamePattern(String template) {
		int varStart = template.indexOf("$");
		int varEnd = template.indexOf("}");

		prefix = template.substring(0, varStart);
		suffix = varEnd + 1 == template.length() ? "" : template.substring(varEnd + 1);
	}

	public FileNameEvaluation evaluate(String fileBaseName) {
		if (fileBaseName.startsWith(prefix) && fileBaseName.endsWith(suffix)) {
			String srcFile = fileBaseName.replaceFirst("^" + prefix, "").replaceFirst(suffix + "$", "");
			return new FileNameEvaluation(true, srcFile);
		}

		String testFile = String.format("%s%s%s", prefix, fileBaseName, suffix);
		return new FileNameEvaluation(false, testFile);
	}
}
