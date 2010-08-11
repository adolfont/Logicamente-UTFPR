package logicamente.drawer;

import static org.junit.Assert.assertArrayEquals;
import logicamente.parser.Parser;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class SyntaxTreeDrawerTest {

	private SyntaxTreeDrawer std;
	private Parser parser;

	@Before
	public void setUp() throws Exception {
		std = new SyntaxTreeDrawer();
		parser = new Parser();
	}

	@Test
	public void testGetScreenPutsAtomicFormulasInTheRightPlace() {
		assertUsingDSL("A", new String[]{"x"});
	}

	@Test
	public void testGetScreenPutsNegatedAtomicFormulasInTheRightPlace() {
		assertUsingDSL("!A", new String[] { "ox", "xo" });
	}

//	for (int i = 0; i < std.getScreen().length; i++) {
//	for (int j = 0; j < std.getScreen()[i].length; j++) {
//		System.out.print(std.getScreen()[i][j]);
//	}
//	System.out.println();
//}

	@Test
	public void testGetScreenPutsCompositeFormulasInTheRightPlace() {
		assertUsingDSL("A->B", new String[] { "oxo", "xox" });
		
		assertUsingDSL("A->B->C", new String[] { "oxooo", 
												 "xooxo",
												 "ooxox" });
		assertUsingDSL("(A->B)->C", new String[] 
		       { "oooxo", 
				 "oxoox",
				 "xoxoo" });
	}
	
	// TODO:
	
	// A partir de screen
	// desenhar a árvore com
	// drawNode (x,y,string)
	// drawLineConnectingNodes (x1,y1, x2,x2)  ==> COMO SABER QUE NÓS ESTÃO CONECTADOS?

	public void assertUsingDSL(String formula, String[] screen){
		std.setFormula(formula);
		assertArrayEquals(screen,
				convertByteArrayToStringArray(std.getScreen()));
	}
	
	
	@Test
	public void testTransformByteToString() {
		byte[][] screen = { { 0, 1, 0 }, { 1, 0, 1 } };
		String[] screenS = { "oxo", "xox" };
		assertArrayEquals(screenS, convertByteArrayToStringArray(screen));

		byte[][] screen2 = { { 1 }, { 1 } };
		String[] screenS2 = { "x", "x" };
		assertArrayEquals(screenS2, convertByteArrayToStringArray(screen2));
	}

	private String[] convertByteArrayToStringArray(byte[][] bytes) {
		String[] screen = new String[bytes.length];

		for (int i = 0; i < bytes.length; i++) {
			screen[i] = "";
			for (int j = 0; j < bytes[i].length; j++) {
				if (bytes[i][j] == 0)
					screen[i] += "o";
				else
					screen[i] += "x";
			}
		}
		return screen;
	}

}
