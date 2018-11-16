package info.margreiter.getdown.maven.plugin.mojo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import info.margreiter.getdown.maven.plugin.uitl.JnlpReader;


/**
 * created: 14.11.2018
 * @author f3thomas
 */
@Mojo (name="getdowntxt")
public class GetDownTxt extends AbstractMojo{

	 @Parameter (defaultValue="target")
	 private String appdir;
	  
	 @Parameter (required = true)
	 private String appbase;

	 @Parameter (required = true)
	 private String ui_name;
		 
	 @Parameter (required = true)
	 private String mainclass;
		  
	 @Parameter (required = true)
	 private String jnlpfile;

    public void execute() throws MojoExecutionException {
    	// TODO TEST 14.11.2018
    	getLog().debug("used appdir: " + appdir);
    	getLog().debug("used appbase: " + appbase);
		getLog().debug("used jnlpFile: " + jnlpfile);
    	
    	try {
    		checkRequiredProperites();
    		JnlpReader jnlpReader = new JnlpReader(new File(jnlpfile));
    		getLog().debug("after read()");
    		makeDirectoryIfNecessary(new File(appdir));
    		PrintWriter writer = new PrintWriter(new File(appdir,"getdown.txt"), "UTF-8");
    		writer.println("# The URL from which the client is downloaded");
    		writer.println("appbase = " + appbase);
    		writer.println();
    		writer.println("# UI Configuration");
    		writer.println("ui.name = "+ jnlpReader.getUiName());
			writer.println();
			writer.println("# Application jar files");
			writeApplicationJarFiles(writer, jnlpReader.getRequestedJars());
			writer.println();
			writer.println("# The main entry point for the application");
			writer.println("class = " + jnlpReader.getMainClass());
			writer.println();
			if (jnlpReader.hasValidHeapSizeArguments()) {
				writer.println("# Initial Heap Size");
				writer.println("jvmarg = -Xms" + jnlpReader.getInitialHeapSize());
				writer.println();
				writer.println("# Max. Heap Size");
				writer.println("jvmarg = -Xmx" + jnlpReader.getMaxHeapSize());
				writer.println();
			}
    		writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new MojoExecutionException("Failed to create digest", e);
		}
    }

	private void writeApplicationJarFiles(PrintWriter writer, Vector<String> requestedJars) {
		// TODO TEST 14.11.2018
		for (String jar : requestedJars) {
			writer.println("code = " + jar);
		}
	}

	private void makeDirectoryIfNecessary( File dir ) throws MojoExecutionException {
		// TODO TEST 14.11.2018
        if ( !dir.exists() && !dir.mkdirs() ) {
            throw new MojoExecutionException( "Failed to create directory: " + dir );
        }
    }
	
	private void checkRequiredProperites() throws MojoExecutionException{
		// TODO TEST 14.11.2018
		if (null==mainclass) throw new MojoExecutionException("'mainclass' missing");
		if (null==appbase) throw new MojoExecutionException("'appbase' missing");
		if (null==ui_name) throw new MojoExecutionException("'ui_name' missing");
		
	}
}
