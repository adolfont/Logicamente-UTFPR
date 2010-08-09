package logicamente.formulas;

import static org.junit.Assert.assertEquals;
import logicamente.parser.Parser;

import org.junit.Before;
import org.junit.Test;

public class FormulaHeightTest {
	
	private Parser parser;
	
	@Before
	public void setUp() {
		parser = new Parser();
	}
	
	@Test
	public void testIfGetHeightWorks() throws Exception {
		assertEquals(0,parseFormula("A").getHeight());
		assertEquals(1,parseFormula("!A").getHeight());
		assertEquals(1,parseFormula("A->B").getHeight());
		assertEquals(2,parseFormula("A->!B").getHeight());
		assertEquals(2,parseFormula("!A->!B").getHeight());
		assertEquals(3,parseFormula("!A->!!B").getHeight());
		assertEquals(4,parseFormula("(A->B->C)->D->E->F->G").getHeight());
		assertEquals(6,parseFormula("A->B->C->D->E->F->G").getHeight());
	}

	private Formula parseFormula(String string) {
		return parser.parse(string).getFormula();
	}

}
