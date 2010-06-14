package logicamente;

import org.junit.Test;
import static junit.framework.Assert.*;


public class SyntaxTest {
	
	@Test public void verificaSintaxeNegaçãoFórmulaAtômica(){
		
	}
	
	@Test public void verificaSintaxeFórmulaAtômica(){
		
		SyntaxTreeGenerator stg = new SyntaxTreeGenerator();
		
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
