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
}
