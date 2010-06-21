package logicamente;

import java.util.Arrays;
import java.util.regex.Pattern;

public class SyntaxTreeGenerator {

	public static final String NOT_SYMBOL = "!";
	public static final String AND_SYMBOL = "&";
	public static final String OR_SYMBOL = "|";
	public static final String IMPLIES_SYMBOL = "-";

	// TODOS OS CONECTORES DEVEM ESTAR NESTA LISTA
	public static final String[] BINARY_CONNECTORS = {AND_SYMBOL, OR_SYMBOL, IMPLIES_SYMBOL};
	
	private Pattern atomicFormulaPattern = Pattern
			.compile("^[A-Za-z]{1,1}[A-Za-z0-9]*$");

	public boolean isAtomicFormula(String inputString) {
		inputString = inputString.trim();

		boolean found = atomicFormulaPattern.matcher(inputString).find();

		return inputString.length() > 0 && found;
	}

	public boolean isNegatedAtomicFormula(String inputString) {
		inputString = inputString.trim();

		return (inputString.length() > 1)
				&& (inputString.substring(0, 1).equals(NOT_SYMBOL))
				&& isAtomicFormula(inputString.substring(1));
	}

	public boolean isBinaryFormula(String inputString) {
		int index = indexOfBinaryConnector(inputString);

		if (index == -1)
			return false;
		else {
			return isAtomicFormula(inputString.substring(0, index))
					&& isAtomicFormula(inputString.substring(index + 1));
		}
	}

	public int indexOfBinaryConnector(String inputString) {
		for (String connector : Arrays.asList(BINARY_CONNECTORS)) {
			int index = inputString.indexOf(connector);
			if(index >= 0)
				return index;
		}
		return -1;
	}
	
	

}
