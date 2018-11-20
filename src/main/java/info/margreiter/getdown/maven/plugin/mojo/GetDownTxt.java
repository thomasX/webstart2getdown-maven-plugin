package info.margreiter.getdown.maven.plugin.mojo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;

import com.threerings.getdown.util.FileUtil;

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
	 private String jnlpfile;
	 
	 @Parameter (defaultValue="")
	 private String iconImage="";
	 
	 @Parameter (defaultValue="")
	 private String bgImage="";
	 
	 @Parameter (defaultValue="")
	 private String progressbarColor;
	 
	 @Parameter (defaultValue="")
	 private String progressTextColor;
	 
	 @Parameter (defaultValue="")
	 private String bgColor;
	 
	@Parameter(defaultValue = "${plugin}", readonly = true)
	protected PluginDescriptor plugin;
	 
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
			if ((null!=bgImage) &&(bgImage.toString().trim().length()>0)) {
				writer.println("# Background Image");
				writer.println("ui.background_image = " + bgImage);
				writer.println(" resource = " + bgImage);
				writer.println();
			}
			if ((null!=iconImage) && (iconImage.toString().trim().length()>0)) {
				writer.println("# Icon Image");
				writer.println("ui.icon = " + iconImage);
				writer.println(" resource = " + iconImage);
				writer.println();
			}
			if ((null!=progressbarColor) && (progressbarColor.toString().trim().length()>0)) {
				writer.println("# Progressbar Color");
				writer.println("ui.progress_bar = " + progressbarColor);
				writer.println();
			}
			if ((null!=progressTextColor) && (progressTextColor.toString().trim().length()>0)) {
				writer.println("# Progressbar TextColor");
				writer.println("ui.progress_text = " + progressTextColor);
				writer.println();
			}
			if ((null!=bgColor) &&(bgColor.toString().trim().length()>0)) {
				writer.println("# BackgroundColor  eg: ui.background = 3399AA");
				writer.println("ui.background = " + bgColor);
				writer.println();
			}
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
    		copyJarFiles(jnlpReader);
    		copyImgFiles(jnlpReader);
    		copyGetdownClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new MojoExecutionException("Failed to create digest", e);
		}
    }

	private void copyJarFiles(JnlpReader jnlpReader) throws IOException, MojoExecutionException {
		// TODO TEST 16.11.2018
		String srcDir = jnlpReader.getJnlpFile().getParent();
		getLog().debug("copyRefJarsFromJNLP-File :" + jnlpReader.getJnlpFile());
		getLog().debug("srcDir:" +srcDir);
		String destDir = appdir;
		getLog().debug("destDir:" +destDir);
		Vector<String> requestedJars = jnlpReader.getRequestedJars();
		for (String fileName : requestedJars) {
			File srcFile = new File(srcDir,fileName);
			File dstFile = new File(destDir,fileName);
			File dstDir=dstFile.getParentFile();
			makeDirectoryIfNecessary(dstDir);
			getLog().debug("srcFile:" +srcFile  +  "  --->  " + "destFile:" + dstFile);
			FileUtil.copy(srcFile,dstFile);
		}
	}
	private void copyImgFiles(JnlpReader jnlpReader) throws IOException, MojoExecutionException {
		// TODO TEST 16.11.2018
			if ((null!=bgImage) &&(bgImage.toString().trim().length()>0)) {
				copyImageSrc(jnlpReader,bgImage);
			}
			if ((null!=iconImage) && (iconImage.toString().trim().length()>0)) {
				copyImageSrc(jnlpReader,iconImage);
			}
	}

	private void copyImageSrc(JnlpReader jnlpReader, String image) throws MojoExecutionException, IOException {
		File srcFile=new File(jnlpReader.getJnlpFile().getParentFile(), image);
		File destFile=new File(appdir,image);
		String destDir = destFile.getParent();
		makeDirectoryIfNecessary(new File(destDir));
		FileUtils.copyFile(srcFile, destFile);
	}
	protected void copyGetdownClient() throws MojoExecutionException, IOException {
		getLog().info("Copying client jar");
		Artifact getdown = (Artifact) plugin.getArtifactMap().get("com.threerings:getdown");
		FileUtils.copyFile(getdown.getFile(), new File(appdir, "getdown.jar"));
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
		if (null==jnlpfile) throw new MojoExecutionException("'jnlpfile' missing");
		if (null==appbase) throw new MojoExecutionException("'appbase' missing");
		
	}
}
