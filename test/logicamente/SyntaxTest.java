package logicamente;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class SyntaxTest {

	private SyntaxTreeGenerator stg;

	@Before
	public void setUp() {
		stg = new SyntaxTreeGenerator();
	}

	@Test
	public void verificaSintaxeFórmulaBinária() {

		assertTrue(stg.isBinaryFormula("p" + SyntaxTreeGenerator.AND_SYMBOL
				+ "q"));
		assertTrue(stg.isBinaryFormula("p" + SyntaxTreeGenerator.OR_SYMBOL
				+ "q"));
		assertTrue(stg.isBinaryFormula("p" + SyntaxTreeGenerator.IMPLIES_SYMBOL
				+ "q"));
	}

	@Test
	public void verificaConectorEmString() {
		assertEquals(stg.indexOfBinaryConnector("p"
				+ SyntaxTreeGenerator.IMPLIES_SYMBOL + "q"), 1);
		assertEquals(stg.indexOfBinaryConnector("p"
				+ SyntaxTreeGenerator.AND_SYMBOL + "q"), 1);
		assertEquals(stg.indexOfBinaryConnector("p"
				+ SyntaxTreeGenerator.OR_SYMBOL + "q"), 1);
		assertEquals(stg.indexOfBinaryConnector("p"),-1);
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
		assertFalse(stg.isAtomicFormula("1"));

	}

}
