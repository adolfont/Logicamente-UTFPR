package logicamente.formulas;

import static org.junit.Assert.assertEquals;
import logicamente.parser.Parser;

import org.junit.Before;
import org.junit.Test;

public class FormulaComplexityTest {
	
	private Parser parser;
	
	@Before
	public void setUp() {
		parser = new Parser();
	}
	
	@Test
	public void testIfGetComplexityWorks() throws Exception {
		assertEquals(1,parseFormula("A").getComplexity());
		assertEquals(2,parseFormula("!A").getComplexity());
		assertEquals(3,parseFormula("A->B").getComplexity());
		assertEquals(4,parseFormula("A->!B").getComplexity());
		assertEquals(5,parseFormula("!A->!B").getComplexity());
	}

	private Formula parseFormula(String string) {
		return parser.parse(string).getFormula();
	}

}
