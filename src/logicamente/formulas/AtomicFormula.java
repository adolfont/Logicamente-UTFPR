package logicamente.formulas;

import java.util.ArrayList;
import java.util.Collection;

public class AtomicFormula implements Formula {
	
	private String atom;
	
	private static ArrayList<Formula> EMPTY = new ArrayList<Formula>();

	public AtomicFormula(String string) {
		this.atom=string;
	}

	@Override
	public String toString() {
		return atom;
	}

	@Override
	public int getComplexity() {
		return 1;
	}

	@Override
	public int getHeight() {
		return 0;
	}

	@Override
	public int getNegationDegree() {
		return 0;
	}

	@Override
	public Collection<Formula> getChildren() {
		return EMPTY;
	}
}
