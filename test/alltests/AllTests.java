/**
 * 
 */
package alltests;

import org.junit.runner.RunWith;

import logicamente.*;
import logicamente.formulas.*;
import logicamente.parser.*;

import org.junit.runners.Suite;

/**
 * Suite that runs all our tests. 
 * 
 * @author Turma Métodos Ágeis PPGCA UTFPR 2010
 * 
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( { SyntaxTest.class, FormulasTest.class,
		GeneratedParserTest.class, ParserTest.class })
public class AllTests {

}
