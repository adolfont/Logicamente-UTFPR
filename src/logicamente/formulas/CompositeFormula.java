package logicamente.formulas;

public class CompositeFormula implements Formula {

	private String conect;

	private Formula formulaDir;
	private Formula formulaEsq;

	public CompositeFormula(String con, Formula a1, Formula a2) {
		this.conect = con;
		this.formulaEsq = a1;
		this.formulaDir = a2;
	}

	public CompositeFormula(String con, Formula a1) {
		this.conect = con;
		this.formulaEsq = a1;
		this.formulaDir = null;
	}

	@Override
	public String toString() {
		if (conect == Formula.NOT) {
			return conect + formulaEsq.toString();
		}
		return "(" + formulaEsq.toString() + conect + formulaDir.toString()
				+ ")";
	}

	public String getConnective() {
		return conect;
	}

	public Formula getRightFormula() {
		return formulaDir;
	}

	public Formula getLeftFormula() {
		return formulaEsq;
	}

	@Override
	public int getComplexity() {
		return 1
				+ getLeftFormula().getComplexity()
				+ (getRightFormula() != null ? getRightFormula()
						.getComplexity() : 0);
	}

	@Override
	public int getHeight() {
		return 1 + Math
				.max(getLeftFormula().getHeight(),
						(getRightFormula() != null ? getRightFormula()
								.getHeight() : 0));
	}

}
