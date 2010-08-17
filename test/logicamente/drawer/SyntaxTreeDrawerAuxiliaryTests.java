package logicamente.drawer;

import static org.junit.Assert.assertEquals;
import logicamente.formulas.Formula;
import logicamente.parser.Parser;

import org.junit.Before;
import org.junit.Test;

public class SyntaxTreeDrawerAuxiliaryTests {

	Formula f1, f2, f3, f4;
	Parser parser;

	@Before
	public void setUp() throws Exception {
		parser = new Parser();
	}

	@Test
	public void testGetLeftSize_SimpleFormulas() {
		f1 = parser.parse("A").getFormula();
		f2 = parser.parse("!A").getFormula();
		f3 = parser.parse("!!(A&B)").getFormula();
		f4 = parser.parse("A->!!(A&B)").getFormula();
		assertEquals(0, SyntaxTreeDrawer.getLeftSize(f1));
		assertEquals(0, SyntaxTreeDrawer.getLeftSize(f2));
		assertEquals(1, SyntaxTreeDrawer.getLeftSize(f3));
		assertEquals(1, SyntaxTreeDrawer.getLeftSize(f4));
	}

	@Test
	public void testGetLeftSize_MoreComplexFormulas() {
		f1 = parser.parse("A&(B&C)").getFormula();
		f2 = parser.parse("(A&B)&C").getFormula();
		f3 = parser.parse("!!(A&B)").getFormula();
		f4 = parser.parse("A->!!(A&B)").getFormula();
		assertEquals(1, SyntaxTreeDrawer.getLeftSize(f1));
		assertEquals(3, SyntaxTreeDrawer.getLeftSize(f2));
		assertEquals(1, SyntaxTreeDrawer.getLeftSize(f3));
		assertEquals(1, SyntaxTreeDrawer.getLeftSize(f4));
	}

}
