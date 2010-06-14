package logicamente;

import java.util.regex.Pattern;

public class SyntaxTreeGenerator {

	private Pattern atomicFormulaPattern = Pattern.compile("^[A-Za-z0-9]+$");

	public boolean isAtomicFormula(String inputString) {
		inputString = inputString.trim();

		boolean found = atomicFormulaPattern.matcher(inputString).find();

		return inputString.length() > 0 && found;
	}

}
