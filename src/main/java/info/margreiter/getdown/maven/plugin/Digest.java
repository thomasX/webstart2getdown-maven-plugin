package info.margreiter.getdown.maven.plugin;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.threerings.getdown.tools.Digester;

/**
 * created: 14.11.2018
 * @author f3thomas
 */
@Mojo (name="digest")
public class Digest extends AbstractMojo{


	 @Parameter (defaultValue="target")
	 private String appdir;
	 
	 @Parameter (defaultValue="null")
	 private String keystore;
	 
	 @Parameter (defaultValue="null")
	 private String keystorePass;
	 
	 @Parameter (defaultValue="null")
	 private String keystoreAlias;
	 
	 @Parameter (defaultValue="hhhhhh" , required = true)
	 private String appbase;

	 @Parameter (required = true)
	 private String ui_name;
		 
	 @Parameter (required = true)
	 private String mainclass;
		 
	 @Parameter (required = true)
	 private String[] jarfiles;
		 
		 

    public void execute() throws MojoExecutionException {
    	getLog().debug("#############    used appdir: " + appdir);
    	getLog().debug("#############    used appbase: " + appbase);
    	try {
    		File ks=new File(keystore);
			Digester.createDigests(new File(appdir), ks, keystorePass,keystoreAlias);
		} catch (IOException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			throw new MojoExecutionException("Failed to create digest", e);
		}
    }
}
