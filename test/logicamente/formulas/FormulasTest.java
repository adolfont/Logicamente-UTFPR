package logicamente.formulas;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FormulasTest {

	AtomicFormula a1;
	AtomicFormula a2;
	AtomicFormula a3;
	CompositeFormula cf1;
	CompositeFormula cf2;
	CompositeFormula cf3;

	@Before
	public void setup() {
		a1 = new AtomicFormula("A");
		a2 = new AtomicFormula("B");
		a3 = new AtomicFormula("C");
		cf1 = new CompositeFormula(Formula.AND, a1, a2);
		cf2 = new CompositeFormula(Formula.AND, cf1, a3);
		cf3 = new CompositeFormula(Formula.AND, a1, new CompositeFormula(
				Formula.AND, a2, a3));
	}

	@Test
	public void testAtomicFormula() {
		AtomicFormula a1 = new AtomicFormula("A");
		assertEquals("A", a1.toString());

		AtomicFormula a2 = new AtomicFormula("ABC");
		assertEquals("ABC", a2.toString());
	}

	@Test
	public void testBinaryCompositeFormula() {

		assertEquals("(A&B)", cf1.toString());
		assertEquals("((A&B)&C)", cf2.toString());
		assertEquals("(A&(B&C))", cf3.toString());

		CompositeFormula cf4 = new CompositeFormula(Formula.OR, a1,
				new CompositeFormula(Formula.IMPLIES, a2, a3));
		assertEquals("(A|(B->C))", cf4.toString());
		assertEquals("((A&(B&C))->(A|(B->C)))", new CompositeFormula(
				Formula.IMPLIES, cf3, cf4).toString());

	}

	@Test
	public void testUnaryCompositeFormula() {
		CompositeFormula nf1 = new CompositeFormula(Formula.NOT, a1);
		assertEquals("!A", nf1.toString());

		CompositeFormula nf2 = new CompositeFormula(Formula.NOT, cf3);
		assertEquals("!(A&(B&C))", nf2.toString());

	}
	
	@Test 
	public void testGetConnective(){
		assertEquals(Formula.AND, cf3.getConnective());
	}

}
