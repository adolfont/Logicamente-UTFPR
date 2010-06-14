package logicamente;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class SyntaxTest {

	private SyntaxTreeGenerator stg;

	@Before
	public void setUp() {
		stg = new SyntaxTreeGenerator();
	}

	@Test
	public void verificaSintaxeNegaçãoFórmulaAtômica() {
		assertTrue(stg.isNegatedAtomicFormula(SyntaxTreeGenerator.NOT_SYMBOL
				+ "p"));
		assertTrue(stg.isNegatedAtomicFormula(SyntaxTreeGenerator.NOT_SYMBOL
				+ "p1"));
		assertTrue(stg.isNegatedAtomicFormula(SyntaxTreeGenerator.NOT_SYMBOL
				+ "pqr"));

		assertFalse(stg.isNegatedAtomicFormula(SyntaxTreeGenerator.NOT_SYMBOL
				+ ""));
		assertFalse(stg.isNegatedAtomicFormula(""));
		assertFalse(stg.isNegatedAtomicFormula("p"));
	}

	@Test
	public void verificaSintaxeFórmulaAtômica() {

		assertTrue(stg.isAtomicFormula("p"));

		assertFalse(stg.isAtomicFormula(""));
		assertFalse(stg.isAtomicFormula(" "));
		assertFalse(stg.isAtomicFormula(","));
		assertFalse(stg.isAtomicFormula(" ,"));

		assertTrue(stg.isAtomicFormula("prq"));
		assertTrue(stg.isAtomicFormula("p1"));
		assertTrue("esperava true", stg.isAtomicFormula("p12"));

	}

}
