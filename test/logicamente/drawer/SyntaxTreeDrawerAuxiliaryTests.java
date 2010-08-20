package logicamente.drawer;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import logicamente.formulas.Formula;
import logicamente.parser.Parser;

import org.junit.Before;
import org.junit.Test;

public class SyntaxTreeDrawerAuxiliaryTests {

	Formula f1, f2, f3, f4;
	Parser parser;
	SyntaxTreeDrawer std;

	@Before
	public void setUp() throws Exception {
		parser = new Parser();
		std = new SyntaxTreeDrawer();
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

	@Test
	public void testGetTreeNodes_for_an_AtomicFormula() {
		std.setFormula("A");
		std.calculatePositionOfTreeNodes();
		assertEquals("A (1,1)-", convert(std.getTreeNodes()));
	}

	@Test
	public void testGetTreeNodes_for_a_NegatedAtomicFormula() {
		std.setFormula("!A");
		std.calculatePositionOfTreeNodes();
		assertEquals("¬ (1,1)-A (1,2) (¬ (1,1))-", convert(std.getTreeNodes()));
	}

	@Test
	public void testGetTreeNodes_for_a_SimpleCompositeFormula() {
		std.setFormula("A&B");
		std.calculatePositionOfTreeNodes();
		assertEquals("A (1,2) (∧ (2,1))-∧ (2,1)-B (3,2) (∧ (2,1))-",
				convert(std.getTreeNodes()));
	}

	@Test
	public void testGetTreeNodes_for_a_MoreComplexCompositeFormula() {
		std.setFormula("A&B&C");
		std.calculatePositionOfTreeNodes();
		assertEquals(
				"A (1,3) (∧ (2,2) (∧ (4,1)))-∧ (2,2) (∧ (4,1))-B (3,3) (∧ (2,2) (∧ (4,1)))-∧ (4,1)-C (5,2) (∧ (4,1))-",
				convert(std.getTreeNodes()));
	}

	@Test
	public void testGetTreeNodes_for_the_negation_of_a_ComplexCompositeFormula() {
		std.setFormula("!(A&B)");
		std.calculatePositionOfTreeNodes();
		assertEquals("A (1,3) (∧ (2,2) (¬ (2,1)))-" + "¬ (2,1)-"
				+ "∧ (2,2) (¬ (2,1))-" + "B (3,3) (∧ (2,2) (¬ (2,1)))-",
				convert(std.getTreeNodes()));
	}

	@Test
	public void testGetTreeNodes_for_a_composition_with_the_negation_of_a_ComplexCompositeFormula() {
		std.setFormula("A->!(A&B)");
		std.calculatePositionOfTreeNodes();
		assertEquals("A (1,2) (→ (2,1))-→ (2,1)-A (3,4) (∧ (4,3) (¬ (4,2) (→ (2,1))))-¬ (4,2) (→ (2,1))-∧ (4,3) (¬ (4,2) (→ (2,1)))-B (5,4) (∧ (4,3) (¬ (4,2) (→ (2,1))))-",
				convert(std.getTreeNodes()));
	}

	private String convert(List<GridNode> list) {
		Comparator<GridNode> comparator = new GridNodeComparator();

		String result = "";
		Collections.sort(list, comparator);
		for (GridNode node : list) {
			result += node.toString() + "-";
		}

		System.out.println("result: " + result);
		return result;
	}

	private class GridNodeComparator implements Comparator<GridNode> {

		@Override
		public int compare(GridNode arg0, GridNode arg1) {
			return (arg0.getX() > arg1.getX() ? 1
					: (arg0.getX() < arg1.getX() ? -1 : arg0.getY() < arg1
							.getY() ? -1 : 1));
		}
	}

}
