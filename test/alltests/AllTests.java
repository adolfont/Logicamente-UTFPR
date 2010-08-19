package alltests;

import logicamente.drawer.SyntaxTreeDrawerAuxiliaryTests;
import logicamente.formulas.FormulaComplexityTest;
import logicamente.formulas.FormulaHeightTest;
import logicamente.formulas.FormulasTest;
import logicamente.parser.GeneratedParserTest;
import logicamente.parser.ParserTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * A suite that runs all our tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( { SyntaxTreeDrawerAuxiliaryTests.class,
		//
		FormulaComplexityTest.class, FormulaHeightTest.class,
		FormulasTest.class,
		//
		GeneratedParserTest.class, ParserTest.class })
public class AllTests {

}
