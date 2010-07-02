package logicamente.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the generated parser.
 * 
 * @author Turma Métodos Ágeis PPGCA UTFPR 2010
 * 
 */
public class ParserTest {

	private Parser parser;

	@Before
	public void setUp() {
		parser = new Parser();
	}

	@Test
	public void parsingAtomicFormulas(){
		assertEquals("A", parser.parse("A").getFormula().toString());
		assertEquals("A", parser.parse("A  ").getFormula().toString());
		assertEquals("A", parser.parse("  A  ").getFormula().toString());
		assertEquals("A123", parser.parse("A123  ").getFormula().toString());
	}
	
	@Test
	public void parsingWrongAtomicFormulas(){
		assertFalse(parser.parse("  123  ").parseCorrect());
	}

	@Test
	public void parsingNegatedFormulas() throws Exception {
		assertEquals("A", parser.parse("A").getFormula().toString());
		assertEquals("!A", parser.parse("! A").getFormula().toString());
		assertEquals("(A&B)", parser.parse("A  & B").getFormula().toString());
		assertEquals("(A|B)", parser.parse("A  | B").getFormula().toString());
		assertEquals("(A->B)", parser.parse("   A  -> B  ").getFormula().toString());
		assertEquals("((A&B)&C)", parser.parse(" A  & B  & C").getFormula().toString());
		assertEquals("((A|B)|C)", parser.parse(" A  | B  | C").getFormula().toString());
		assertEquals("(A->(B->C))", parser.parse("   A  -> B  -> C").getFormula().toString());
		assertEquals("!(A->!(!B->C))", parser.parse("! (A  -> !(!B  -> C))").getFormula()
				.toString());
	}

}
