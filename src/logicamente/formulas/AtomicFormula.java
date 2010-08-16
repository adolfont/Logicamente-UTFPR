package logicamente.formulas;

public class AtomicFormula implements Formula {
	
	private String atom;

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
}
