package info.margreiter.getdown.maven.plugin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;


/**
 * created: 14.11.2018
 * @author f3thomas
 */
@Mojo (name="getdowntxt")
public class GetDownTxt extends AbstractMojo{

	 @Parameter (defaultValue="target")
	 private String appdir;
	 
	 @Parameter (defaultValue="null")
	 private String keystore;
	 
	 @Parameter (defaultValue="null")
	 private String keystorePass;
	 
	 @Parameter (defaultValue="null")
	 private String keystoreAlias;
	 
	 @Parameter (required = true)
	 private String appbase;

	 @Parameter (required = true)
	 private String ui_name;
		 
	 @Parameter (required = true)
	 private String mainclass;
		 
	 @Parameter (required = true)
	 private String[] jarfiles;

    public void execute() throws MojoExecutionException {
    	getLog().debug("used appdir: " + appdir);
    	getLog().debug("used appbase: " + appbase);
    	try {
    		checkRequiredProperites();
//    		new Util().makeDirectoryIfNecessary(appdir);
    		PrintWriter writer = new PrintWriter(new File(appdir,"getdown.txt"), "UTF-8");
    		writer.println("# The URL from which the client is downloaded");
    		writer.println("appbase = " + appbase);
    		writer.println();
    		writer.println("# UI Configuration");
    		writer.println("ui.name = "+ui_name);
			writer.println();
			writer.println("# Application jar files");
			writePlicationJarFiles(writer);
			
    		writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new MojoExecutionException("Failed to create digest", e);
		}
    }

	private void writePlicationJarFiles(PrintWriter writer) {
		// TODO TEST 14.11.2018
		writer.println(",..... jarfiles");
	}

	private void makeDirectoryIfNecessary( File dir ) throws MojoExecutionException {
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
