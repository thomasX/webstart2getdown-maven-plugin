package info.margreiter.getdown.maven.plugin.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import info.margreiter.getdown.maven.plugin.uitl.JnlpReader;

public class JnlpReaderTest {

	private String testJnlpFileName="testJnlp.jnlp";
	private String testJnlpFileNameWithoutHeap="testJnlp2.jnlp";
    private JnlpReader reader=null;
	@Before
	public void setUp() throws Exception {
		initReader(testJnlpFileName);
	}
	private void initReader(String jnlpfileName) throws IOException, URISyntaxException {
		// TODO TEST 15.11.2018
		URL res = getClass().getResource(jnlpfileName);
		File jnlp = new File(res.toURI());
		reader=new JnlpReader(jnlp);
	}
	//TODO Test 15.11.2018

	@After
	public void tearDown() throws Exception {
		reader=null;
	}
	
	@Test
	public void testJnlpReader() {
		
		assertNotNull(reader);
	}

	@Test
	public void testGetJnlpFile() {
		assertNotNull(reader);
		assertTrue(reader.getJnlpFile().exists());
		assertEquals("testJnlp.jnlp",reader.getJnlpFile().getName());		
	}

	@Test
	public void testGetRequestedJars() {
		Vector<String> jars = reader.getRequestedJars();
		assertNotNull(jars);
		assertEquals(13,jars.size());
		
	}

	@Test
	public void testGetInitialHeapSize() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMaxHeapSize() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetJavaVersion() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMainClass() {
		fail("Not yet implemented");
	}
}
