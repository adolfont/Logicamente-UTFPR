package logicamente.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

import java.io.StringReader;

import logicamente.formulas.AtomicFormula;
import logicamente.formulas.CompositeFormula;
import logicamente.formulas.Formula;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the generated parser.
 * 
 * @author Turma Métodos Ágeis PPGCA UTFPR
 * 
 */
public class ParserTest {

	private StringReader reader;
	private SyntaxTreeLexer lexer;
	private SyntaxTreeParser parser;
	private Object f;
	private AtomicFormula af;
	private CompositeFormula cf;
	CompositeFormula leftFormula;
	Formula rightFormula;

	@Before
	public void setUp() {
		reader = new StringReader("");
		lexer = new SyntaxTreeLexer(reader);
		parser = new SyntaxTreeParser(lexer);
	}

	@Test
	public void parsingAtomicFormulas() throws Exception {
		reader = new StringReader("A1");
		lexer.yyreset(reader);
		f = parser.parse().value;
		assertTrue(f instanceof Formula);
		assertTrue(f instanceof AtomicFormula);
		af = ((AtomicFormula) f);
		assertEquals("A1", af.toString());

		reader = new StringReader("B");
		lexer.yyreset(reader);
		f = parser.parse().value;
		assertTrue(f instanceof Formula);
		assertTrue(f instanceof AtomicFormula);
		af = ((AtomicFormula) f);
		assertEquals("B", af.toString());
	}

	@Test
	public void parsingSimplestPossibleUnaryFormula() throws Exception {
		reader = new StringReader("!A1");
		lexer.yyreset(reader);

		f = parser.parse().value;
		assertTrue(f instanceof Formula);
		assertTrue(f instanceof CompositeFormula);
		cf = ((CompositeFormula) f);
		assertEquals(Formula.NOT, cf.getConnective());
		assertEquals("A1", cf.getLeftFormula().toString());
		assertNull(cf.getRightFormula());
	}
	
	@Test
	public void parsingSimplestPossibleBinaryFormula() throws Exception {
		reader = new StringReader("A&B");
		lexer.yyreset(reader);

		f = parser.parse().value;
		assertTrue(f instanceof Formula);
		assertTrue(f instanceof CompositeFormula);
		cf = ((CompositeFormula) f);
		assertEquals(Formula.AND, cf.getConnective());
		assertEquals("A", cf.getLeftFormula().toString());
		assertEquals("B", cf.getRightFormula().toString());
	}

	@Test
	public void parsingSimpleAndBinaryFormulasWherePrecedenceMatters() throws Exception {

		reader = new StringReader("A&B&C");
		lexer.yyreset(reader);

		f = parser.parse().value;
		assertTrue(f instanceof Formula);
		assertTrue(f instanceof CompositeFormula);
		cf = ((CompositeFormula) f);
		assertEquals(Formula.AND, cf.getConnective());
		assertTrue(cf.getLeftFormula() instanceof CompositeFormula);
		leftFormula = (CompositeFormula) cf.getLeftFormula();
		rightFormula = cf.getRightFormula();
		assertEquals("(A&B)", leftFormula.toString());
		assertEquals("C", rightFormula.toString());
	}
	
	@Test
	public void parsingSimpleOrBinaryFormulasWherePrecedenceMatters() throws Exception {
		reader = new StringReader("A|B|C");
		lexer.yyreset(reader);

		f = parser.parse().value;
		assertTrue(f instanceof Formula);
		assertTrue(f instanceof CompositeFormula);
		cf = ((CompositeFormula) f);
		assertEquals(Formula.OR, cf.getConnective());
		assertTrue(cf.getLeftFormula() instanceof CompositeFormula);
		leftFormula = (CompositeFormula) cf.getLeftFormula();
		rightFormula = cf.getRightFormula();
		assertEquals("(A|B)", leftFormula.toString());
		assertEquals("C", rightFormula.toString());
	}

	@Test
	public void parsingSimpleImpliesBinaryFormulasWherePrecedenceMatters() throws Exception {
		reader = new StringReader("A->B->C");
		lexer.yyreset(reader);

		f = parser.parse().value;
		assertTrue(f instanceof Formula);
		assertTrue(f instanceof CompositeFormula);
		cf = ((CompositeFormula) f);
		assertEquals(Formula.IMPLIES, cf.getConnective());
		assertEquals("A", cf.getLeftFormula().toString());
		assertEquals("(B->C)", cf.getRightFormula().toString());
	}

}
