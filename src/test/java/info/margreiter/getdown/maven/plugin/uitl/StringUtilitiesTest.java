package info.margreiter.getdown.maven.plugin.uitl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StringUtilitiesTest {

	@Before
	public void setUp() throws Exception {
	}
	//TODO Test 16.11.2018

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRemoveInvalidCharacter() {
		String src="asdfvvvsafdVv";
		assertEquals("asdfsafdV",StringUtilities.removeInvalidCharacter(src,'v'));
		assertEquals("sdfvvvsfdVv",StringUtilities.removeInvalidCharacter(src,'a'));
		assertEquals("asdfvvvsafdVv",StringUtilities.removeInvalidCharacter(src,'z'));
	}

	@Test
	public void testRemoveCRuLF() {
		String src="asdfvvvsafdVv\rdsafad\nasdfs\r\n";
		assertEquals("asdfvvvsafdVvdsafadasdfs",StringUtilities.removeCRuLF(src));
	}

	@Test
	public void testRemoveInvalidStartingCharacters() {
		String src="   \t \t\t     Hallo Welt \t\t\twie gehts";
		assertEquals("Hallo Welt \t\t\twie gehts",StringUtilities.removeInvalidStartingCharacters(src, "\t "));
	}
	@Test
	public void testRemoveInvalidEndingCharacters() {
		assertEquals("Hallo Welt twie gehtsD",StringUtilities.removeInvalidEndingCharacters("Hallo Welt twie gehtsDF", "F"));
		assertEquals("Hallo Welt twie gehtsD",StringUtilities.removeInvalidEndingCharacters("Hallo Welt twie gehtsDFF", "F"));
		assertEquals("Hallo Welt twie gehtsDBF",StringUtilities.removeInvalidEndingCharacters("Hallo Welt twie gehtsDBF", "B"));
		String src="   \t \t\t     Hallo Welt \t\t\twie gehts  \t \t";
		assertEquals("   \t \t\t     Hallo Welt \t\t\twie gehts",StringUtilities.removeInvalidEndingCharacters(src, "\t "));
		
	}
	
	@Test
	public void testExtendedTrim() {
		assertEquals("Hallo \t Welt", StringUtilities.extendedTrim(" \t \tHallo \t Welt    \t "));
	}
}
