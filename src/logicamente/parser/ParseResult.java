/**
 * 
 */
package logicamente.parser;

import logicamente.formulas.Formula;

/**
 * A class for holding the results of the parse. 
 * 
 * @author Turma Métodos Ágeis PPGCA UTFPR 2010
 *
 */
public class ParseResult {
	

	private boolean parseCorrect;
	private Formula formula;
	
	public boolean parseCorrect() {
		return parseCorrect;
	}
	
	public Formula getFormula() {
		return formula;
	}
	
	public void setParseCorrect(boolean parseCorrect) {
		this.parseCorrect = parseCorrect;
	}

	public void setFormula(Formula formula) {
		this.formula = formula;
	}

}
