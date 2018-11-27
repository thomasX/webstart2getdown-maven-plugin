package info.margreiter.getdown.maven.plugin.mojo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
 * 
 * @author f3thomas
 */

@Mojo(name = "getdowntxt")
public class GetDownTxt extends AbstractMojo {

	/**
	 * path to jnlp - file
	 */
	@Parameter(required = true)
	private String jnlpfile;

	/**
	 * path to source - files
	 */
	@Parameter(required = true)
	private String appbase;

	/**
	 * output directory for getdown - files
	 */
	@Parameter(defaultValue = "target")
	private String appdir;

	/**
	 * if application is running in version mode this entry contains the
	 * specific version number
	 */
	@Parameter(defaultValue = "")
	private String version;

	/**
	 * if true it will alow getdown to run the application without reaching the
	 * downloadserver
	 */
	@Parameter(defaultValue = "")
	private String allow_offline;

	/**
	 * defines the color of the background
	 */
	@Parameter(defaultValue = "")
	private String ui_background;

	/**
	 * path to background image
	 */
	@Parameter(defaultValue = "")
	private String ui_background_image;

	/**
	 * path to background error image
	 */
	@Parameter(defaultValue = "")
	private String ui_error_background;

	/**
	 * path to the ui icon
	 */
	@Parameter(defaultValue = "")
	private String ui_icon;

	/**
	 * defines the dimensions of the rectangle in which the progress
	 * displays<br>
	 * 
	 * eg. "17,321,358,22" <br>
	 * -> 17 pixels from the left of the window <br>
	 * -> 321 pixels from the top of the window <br>
	 * -> 458 pixels wide <br>
	 * -> 22 pixels tall <br>
	 */
	@Parameter(defaultValue = "")
	private String ui_progress;

	/**
	 * defines the color of the progressbar
	 */
	@Parameter(defaultValue = "")
	private String ui_progress_bar;

	/**
	 * defines the color of the progressbar text
	 */
	@Parameter(defaultValue = "")
	private String ui_progress_text;

	/**
	 * defines the image for the progressbar
	 */
	@Parameter(defaultValue = "")
	private String ui_progress_image;

	/**
	 * defines the the dimenstion of the rectangle in which the status is shown
	 * (see ui_progress)
	 */
	@Parameter(defaultValue = "")
	private String ui_status;

	/**
	 * defines the color of the status text
	 */
	@Parameter(defaultValue = "")
	private String ui_status_text;

	/**
	 * specifies the color of the shadow for the text
	 */
	@Parameter(defaultValue = "")
	private String ui_text_shadow;

	/**
	 * defines if ui-decoration should be hidden
	 */
	@Parameter(defaultValue = "")
	private String ui_hide_decorations;

	/**
	 * defines the minimum show time for getdown
	 */
	@Parameter(defaultValue = "")
	private String ui_min_show_seconds;

	/**
	 * defines an URL which will be shown if an error occurs
	 */
	@Parameter(defaultValue = "")
	private String ui_install_error;

	/**
	 * defines the path to a mac dockicon
	 */
	@Parameter(defaultValue = "")
	private String ui_mac_dock_icon;

	/**
	 * to supply argumetns to the JVM <br>
	 * eg. -Djava.library.path=%APPDIR%/nativ<br>
	 * <br>
	 * 
	 * 
	 * <b>!!! -Xmx and -Xms allready set in jnlp - file<br>
	 * <br>
	 * !!! the '-' character has to be supplied!</b>
	 */
	@Parameter
	private String[] jvmargs;
	
	@Parameter
	private String[] java_locations;

	@Parameter(defaultValue = "")
	private String java_min_version;
	
	@Parameter
	private Boolean java_exact_version_required;
	
	/**
	 * to supply argumetns to the application<br>
	 * <br>
	 * 
	 * <b> !!! the '-' character has to be supplied!</b>
	 */
	@Parameter(defaultValue = "")
	private String[] appargs;

	@Parameter(defaultValue = "${plugin}", readonly = true)
	private PluginDescriptor plugin;

	public void execute() throws MojoExecutionException {
		// TODO TEST 14.11.2018
		getLog().debug("creating getdown.txt ...");
		getLog().debug("used appdir: " + appdir);
		getLog().debug("used appbase: " + appbase);
		getLog().debug("used jnlpFile: " + jnlpfile);

		try {
			checkRequiredProperites();
			JnlpReader jnlpReader = new JnlpReader(new File(jnlpfile));
			makeDirectoryIfNecessary(new File(appdir));
			PrintWriter writer = new PrintWriter(new File(appdir, "getdown.txt"), "UTF-8");
			writer.println("# The URL from which the client is downloaded");
			writer.println("appbase = " + appbase);
			writer.println();
			writer.println("# getdownclient ");
			writer.println("resource = " + "getdown.jar");
			writer.println();
			if (isParameterUsed(version)){
				writer.println("# Version");
				writer.println("version = " + version);
				writer.println();
			}
			if (isParameterUsed(allow_offline)){
				writer.println("# Allow offline execution");
				writer.println("allow_offline = " + allow_offline);
				writer.println();
			}
			writer.println("# UI Configuration");
			writer.println("ui.name = " + jnlpReader.getUiName());
			writer.println();
			if (isParameterUsed(ui_background)) {
				writer.println("# Background Color");
				writer.println("ui.background = " + ui_background);
				writer.println();
			}
			if (isParameterUsed(ui_background_image)) {
				writer.println("# Background Image");
				writer.println("ui.background_image = " + ui_background_image);
				writer.println("resource = " + ui_background_image);
				writer.println();
			}
			if (isParameterUsed(ui_error_background)) {
				writer.println("# Background Error Image");
				writer.println("ui.error_background = " + ui_error_background);
				writer.println("resource = " + ui_error_background);
				writer.println();
			}
			if (isParameterUsed(ui_icon)) {
				writer.println("# Icon Image");
				writer.println("ui.icon = " + ui_icon);
				writer.println("resource = " + ui_icon);
				writer.println();
			}
			if (isParameterUsed(ui_progress)) {
				writer.println("# Progressbar position");
				writer.println("ui.progress = " + ui_progress);
				writer.println();
			}
			if (isParameterUsed(ui_progress_bar)) {
				writer.println("# Progressbar Color");
				writer.println("ui.progress_bar = " + ui_progress_bar);
				writer.println();
			}
			if (isParameterUsed(ui_progress_text)) {
				writer.println("# Progressbar Text Color");
				writer.println("ui.progress_text = " + ui_progress_text);
				writer.println();
			}
			if (isParameterUsed(ui_progress_image)) {
				writer.println("# Progress Image");
				writer.println("ui.progress_image = " + ui_progress_image);
				writer.println("resource = " + ui_progress_image);
				writer.println();
			}
			if (isParameterUsed(ui_status)) {
				writer.println("# Status Dimension");
				writer.println("ui.status = " + ui_status);
				writer.println();
			}
			if (isParameterUsed(ui_status_text)) {
				writer.println("# Status Text Color");
				writer.println("ui.status_text = " + ui_status_text);
				writer.println();
			}
			if (isParameterUsed(ui_text_shadow)) {
				writer.println("# Text Shadow Color");
				writer.println("ui.text_shadow = " + ui_text_shadow);
				writer.println();
			}
			if (isParameterUsed(ui_hide_decorations)) {
				writer.println("# Hide UI Decorations");
				writer.println("ui.hide_decorations = " + ui_hide_decorations);
				writer.println();
			}
			if (isParameterUsed(ui_min_show_seconds)) {
				writer.println("# UI minimum show time");
				writer.println("ui.min_show_seconds = " + ui_min_show_seconds);
				writer.println();
			}
			if (isParameterUsed(ui_install_error)) {
				writer.println("# Install error URL");
				writer.println("ui.install_error = " + ui_install_error);
				writer.println();
			}
			if (isParameterUsed(ui_mac_dock_icon)) {
				writer.println("# UI mac docicon");
				writer.println("ui.mac_doc_icon = " + ui_mac_dock_icon);
				writer.println("resource = " + ui_mac_dock_icon);
				writer.println();
			}
			writer.println("# Application jar files");
			writeApplicationJarFiles(writer, jnlpReader.getRequestedJars());
			writer.println();
			writer.println("# The main entry point for the application");
			writer.println("class = " + jnlpReader.getMainClass());
			writer.println();
			
			/**
			 * we must be able to overwrite these settings, so we can't write 
			 * these into our getdown.txt
			 */
//			if (jnlpReader.hasValidHeapSizeArguments()) {
//				writer.println("# Initial Heap Size");
//				writer.println("jvmarg = -Xms" + jnlpReader.getInitialHeapSize());
//				writer.println();
//				writer.println("# Max. Heap Size");
//				writer.println("jvmarg = -Xmx" + jnlpReader.getMaxHeapSize());
//				writer.println();
//			}
			
			
			if (isParameterUsed(java_min_version)) {
				writer.println("# minimum Java Version requested");
				writer.println("java_min_version = " + java_min_version);
				writer.println();
			}
			if ((null!=  java_exact_version_required ) && (java_exact_version_required.toString().toLowerCase().equals(true))) {
				writer.println("# minimum Java Version requested");
				writer.println("java_exact_version_required = true");
				writer.println();
			}
			if (isParameterUsed(ui_install_error)) {
				writer.println("# Install error URL");
				writer.println("ui.install_error = " + ui_install_error);
				writer.println();
			}
			if ((null != java_locations) && (java_locations.length > 0) && (null != java_locations[0])) {
				writer.println("# JVM locations");
				writeArguments(writer, java_locations, "java_location");
				writer.println();
			}
			
			if ((null != jvmargs) && (jvmargs.length > 0) && (null != jvmargs[0])) {
				writer.println("# JVM Arguments");
				writeArguments(writer, jvmargs, "jvmarg");
				writer.println();
			}
			if ((null != appargs) && (appargs.length > 0) && (null != appargs[0])) {
				writer.println("# APP Arguments");
				writeArguments(writer, appargs, "apparg");
				writer.println();
			}
			writer.close();
			copyJarFiles(jnlpReader);
			copyImgFiles(jnlpReader);
			copyGetdownClient();
			generateExtrasFile(jnlpReader);
		} catch (IOException e) {
			throw new MojoExecutionException("Failed to create getdown.txt! ", e);
		}
	}

	private void generateExtrasFile(JnlpReader jnlpReader) throws FileNotFoundException, UnsupportedEncodingException, MojoExecutionException {
		// TODO TEST 23.11.2018
		File appDir = new File(appdir,"extras");
		makeDirectoryIfNecessary(appDir);
		PrintWriter writer = new PrintWriter(new File(appDir, "extras.txt"), "UTF-8");
		writer.println("-Xms" + jnlpReader.getInitialHeapSize());
		writer.println("-Xmx" + jnlpReader.getMaxHeapSize());
		writer.close();
	}

	private boolean isParameterUsed(String parameter) {
		// TODO TEST 16.11.2018
		return (null != parameter) && (parameter.toString().trim().length() > 0);
	}

	private void writeArguments(PrintWriter writer, String[] args, String name) {
		// TODO TEST 16.11.2018
		for (String arg : args) {
			if(isParameterUsed(arg)) writer.println(name + " = " + arg);
		}
	}

	private void copyJarFiles(JnlpReader jnlpReader) throws IOException, MojoExecutionException {
		// TODO TEST 16.11.2018
		String srcDir = jnlpReader.getJnlpFile().getParent();
		getLog().debug("copy referenced jar-files from JNLP - file: " + jnlpReader.getJnlpFile());
		getLog().debug("srcDir: " + srcDir);
		String destDir = appdir;
		getLog().debug("destDir: " + destDir);
		Vector<String> requestedJars = jnlpReader.getRequestedJars();
		for (String fileName : requestedJars) {
			File srcFile = new File(srcDir, fileName);
			File dstFile = new File(destDir, fileName);
			File dstDir = dstFile.getParentFile();
			makeDirectoryIfNecessary(dstDir);
			getLog().debug("copying srcFile:" + srcFile + "  --->  " + "destFile:" + dstFile);
			FileUtil.copy(srcFile, dstFile);
		}
	}

	private void copyImgFiles(JnlpReader jnlpReader) throws IOException, MojoExecutionException {
		// TODO TEST 16.11.2018
		if (isParameterUsed(ui_background_image)) {
			copyImageSrc(jnlpReader, ui_background_image);
		}
		if (isParameterUsed(ui_icon)) {
			copyImageSrc(jnlpReader, ui_icon);
		}
	}

	private void copyImageSrc(JnlpReader jnlpReader, String image) throws MojoExecutionException, IOException {
		// TODO TEST 14.11.2018
		File srcFile = new File(jnlpReader.getJnlpFile().getParentFile(), image);
		File destFile = new File(appdir, image);
		String destDir = destFile.getParent();
		makeDirectoryIfNecessary(new File(destDir));
		getLog().debug("copying srcFile:" + srcFile + "  --->  " + "destFile:" + destFile);
		FileUtils.copyFile(srcFile, destFile);
	}

	private void copyGetdownClient() throws MojoExecutionException, IOException {
		// TODO TEST 14.11.2018
		Artifact getdown = (Artifact) plugin.getArtifactMap().get("com.threerings.getdown:getdown-launcher");
		File srcFile = getdown.getFile();
		File destFile = new File(appdir, "getdown.jar");
		getLog().debug("copying srcFile:" + srcFile + "  --->  " + "destFile:" + destFile);
		FileUtils.copyFile(srcFile, destFile);
	}

	private void writeApplicationJarFiles(PrintWriter writer, Vector<String> requestedJars) {
		// TODO TEST 14.11.2018
		for (String jar : requestedJars) {
			writer.println("code = " + jar);
		}
	}

	private void makeDirectoryIfNecessary(File dir) throws MojoExecutionException {
		// TODO TEST 14.11.2018
		if (!dir.exists() && !dir.mkdirs()) {
			throw new MojoExecutionException("Failed to create directory: " + dir);
		}
	}

	private void checkRequiredProperites() throws MojoExecutionException {
		// TODO TEST 14.11.2018
		if (null == jnlpfile)
			throw new MojoExecutionException("'jnlpfile' missing");
		if (null == appbase)
			throw new MojoExecutionException("'appbase' missing");
	}
}
