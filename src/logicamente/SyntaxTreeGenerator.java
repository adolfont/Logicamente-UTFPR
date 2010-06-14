package logicamente;

import java.util.regex.Pattern;

public class SyntaxTreeGenerator {

	public static final char NOT_SYMBOL = '!';

	private Pattern atomicFormulaPattern = Pattern.compile("^[A-Za-z0-9]+$");

	public boolean isAtomicFormula(String inputString) {
		inputString = inputString.trim();

		boolean found = atomicFormulaPattern.matcher(inputString).find();

		return inputString.length() > 0 && found;
	}

	public boolean isNegatedAtomicFormula(String inputString) {
		inputString = inputString.trim();

		return (inputString.length() > 1)
				&& (inputString.charAt(0) == NOT_SYMBOL)
				&& isAtomicFormula(inputString.substring(1));
	}

}
