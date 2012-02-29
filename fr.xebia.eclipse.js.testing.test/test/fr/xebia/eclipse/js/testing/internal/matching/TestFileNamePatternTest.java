package fr.xebia.eclipse.js.testing.internal.matching;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestFileNamePatternTest {

	@Test
	public void should_evaluate_test_file_with_prefix() throws Exception {
		TestFileNamePattern pattern = new TestFileNamePattern("Pre${srcFile}");

		FileNameEvaluation evaluation = pattern.evaluate("PreMyFile");

		assertTrue(evaluation.isTestFile());
		assertEquals("MyFile", evaluation.getCorrespondingFileName());
	}

	@Test
	public void should_evaluate_test_file_with_suffix() throws Exception {
		TestFileNamePattern pattern = new TestFileNamePattern("${srcFile}_suffix");

		FileNameEvaluation evaluation = pattern.evaluate("SomeFile_suffix");

		assertTrue(evaluation.isTestFile());
		assertEquals("SomeFile", evaluation.getCorrespondingFileName());
	}

	@Test
	public void should_evaluate_test_file_with_prefix_and_suffix() throws Exception {
		TestFileNamePattern pattern = new TestFileNamePattern("prefix_${srcFile}Suf");

		FileNameEvaluation evaluation = pattern.evaluate("prefix_aFileSuf");

		assertTrue(evaluation.isTestFile());
		assertEquals("aFile", evaluation.getCorrespondingFileName());
	}

	@Test
	public void should_evaluate_src_file_with_prefix() throws Exception {
		TestFileNamePattern pattern = new TestFileNamePattern("Prefix${srcFile}");

		FileNameEvaluation evaluation = pattern.evaluate("MyFile");

		assertFalse(evaluation.isTestFile());
		assertEquals("PrefixMyFile", evaluation.getCorrespondingFileName());
	}

	@Test
	public void should_evaluate_src_file_with_suffix() throws Exception {
		TestFileNamePattern pattern = new TestFileNamePattern("${srcFile}_suf");

		FileNameEvaluation evaluation = pattern.evaluate("SomeFile");

		assertFalse(evaluation.isTestFile());
		assertEquals("SomeFile_suf", evaluation.getCorrespondingFileName());
	}

	@Test
	public void should_evaluate_src_file_with_prefix_and_suffix() throws Exception {
		TestFileNamePattern pattern = new TestFileNamePattern("pre_${srcFile}Suffix");

		FileNameEvaluation evaluation = pattern.evaluate("aFile");

		assertFalse(evaluation.isTestFile());
		assertEquals("pre_aFileSuffix", evaluation.getCorrespondingFileName());
	}
}
