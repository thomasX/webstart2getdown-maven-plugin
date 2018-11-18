package info.margreiter.getdown.maven.plugin.mojo;

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
	 private String keystorepass;
	 
	 @Parameter (defaultValue="null")
	 private String keyalias;
		 
    public void execute() throws MojoExecutionException {
    	getLog().debug("used appdir: " + appdir);
    	try {
    		if (! ("null".equals(keystore.toString())))  {
    			File ks=new File(keystore);
    			getLog().debug("usedKeyStore:" + ks + "   isexisting: " + ks.exists());
    			Digester.createDigests(new File(appdir), ks, keystorepass,keyalias);
    		}
    		if (("null".equals(keystore.toString())))  {
    			Digester.createDigests(new File(appdir), null ,null ,null);
    		}
		} catch (IOException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			throw new MojoExecutionException("Failed to create digest", e);
		}
    }
    
    
    
    
    
    
    
}
