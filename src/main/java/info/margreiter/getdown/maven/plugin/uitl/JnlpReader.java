package info.margreiter.getdown.maven.plugin.uitl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.print.DocFlavor.STRING;

import org.apache.commons.lang3.Validate;

public class JnlpReader {
	//TODO Test 15.11.2018
	private File jnlpFile;
	private Vector<String> requestedJars=new Vector<>();
	private String initialHeapSize="";
	private String maxHeapSize="";
	private String javaVersion="";
	private String mainClass="";
	private String uiName="";
	private String iconPath="";
	private String bgImagePath="";
	
	
	public JnlpReader(File jnlpFile) throws IOException {
		super();
		this.jnlpFile=jnlpFile;
		this.uiName=jnlpFile.getName();
		init();
	}
	
	private void init() throws IOException {
		//TODO Test 15.11.2018
		BufferedReader br = new BufferedReader(new FileReader(jnlpFile));
		StringBuffer sbuffer = new StringBuffer();
		String nextLine = "";
		while((nextLine = br.readLine()) != null){
			sbuffer.append(nextLine);
		}
		String jnlp = new String(sbuffer);
		jnlp=StringUtilities.removeCRuLF(jnlp);
		String resources = extractXMLvalue(jnlp, "resources");        
        Vector<String> resourceTokens = new ExtendedStringtokenizer(resources, "/>").getAllTokens();
        extractResourceProperties(resourceTokens);
        
        String information = extractXMLvalue(jnlp, "information");
        extractMainClass(jnlp);
        extractUiName(information);
        HashMap<String, String> iconTags = extractIconTags(information);
        extractBgImagePath(iconTags);
        extractIconPath(iconTags);
        
	}
 
	private void extractBgImagePath(HashMap<String, String> iconTags) {
		// TODO TEST 20.11.2018
		String kind="default";
		if (iconTags.containsKey(kind)) bgImagePath=iconTags.get(kind);
	}

	/**
	 * 
	 * @param information
	 * @return Hashmap<kind,path>
	 */
	private HashMap<String,String> extractIconTags(String information) {
		// TODO TEST 20.11.2018
		HashMap<String, String> result= new HashMap();
		String searchstring=information;
		int position=0;
		while (position > -1) {
			position=searchstring.indexOf("<icon");
			if (position>-1) {
				String arrtibutes=extractValue(searchstring,"<icon" , "/>");
				String kind = extractXMLAttribute(arrtibutes, "kind");
				String path = extractXMLAttribute(arrtibutes, "href");
				result.put(kind, path);
				searchstring=searchstring.substring(position +1);
			}
		}
		return result;
	}

	private void extractIconPath(HashMap<String, String> iconTags) {
		// TODO TEST 20.11.2018
		String kind="shortcut";
		if (iconTags.containsKey(kind)) bgImagePath=iconTags.get(kind);
		
	}

	private void extractUiName(String information) {
		String result=extractXMLvalue(information,"title");
		if ((null!=result) && (result.trim().length()>0)) uiName=result;
	}
	
	private void extractMainClass(String jnlp) {
		String appdescr=extractAppDesciption(jnlp);
		String trimmedAppdescr = StringUtilities.extendedTrim(appdescr);
		mainClass=extractValue(trimmedAppdescr, "main-class=\"", "\"");
	}
	
	private String extractAppDesciption(String jnlp) {
		// TODO TEST 16.11.2018
		return extractValue(jnlp, "<application-desc","</application-desc>");
	}

	private void extractResourceProperties(Vector<String> resourceTokens) {
		for (String resource : resourceTokens) {			
        	String trimmedResourceTag=StringUtilities.extendedTrim(resource);
			if (trimmedResourceTag.startsWith("<jar href")) extactHrefValues(trimmedResourceTag);				
			if (trimmedResourceTag.startsWith("<j2se ")) extactJ2seValues(trimmedResourceTag);				
			
		}
	}

	
	
	private void extactJ2seValues(String value) {
		// TODO TEST 16.11.2018
		String src=value;
		String validationString=StringUtilities.removeInvalidCharacter(src, '\t');
		validationString=StringUtilities.removeInvalidCharacter(src, ' ');
		
		javaVersion=extractXMLAttribute(validationString,"version");
		initialHeapSize=extractXMLAttribute(validationString,"initial-heap-size");
		maxHeapSize=extractXMLAttribute(validationString,"max-heap-size");
	}

	private String extractXMLAttribute(String validationString, String attribute) {
		// TODO TEST 16.11.2018
		return extractValue(validationString,attribute+"=\"","\"");
	}

	private void extactHrefValues(String trimmedResourceTag) {
		boolean mainJar=isMainJar(trimmedResourceTag);
		String referencedJar=extractJar(trimmedResourceTag);				
		if ((null!=referencedJar) && (referencedJar.trim().length()>0)){
			if (mainJar) requestedJars.insertElementAt(referencedJar, 0);
			if (!mainJar) requestedJars.addElement(referencedJar);
		}
	}
	
	private boolean isMainJar(String trimmedResourceTag) {
		// TODO TEST 16.11.2018
		String validationString = StringUtilities.removeInvalidCharacter(trimmedResourceTag,'\t');
		validationString = StringUtilities.removeInvalidCharacter(trimmedResourceTag,' ');
		return validationString.contains("main=\"true\"");
	}
	private boolean isMainDefined(String trimmedResourceTag) {
		// TODO TEST 16.11.2018
		String validationString = StringUtilities.removeInvalidCharacter(trimmedResourceTag,'\t');
		validationString = StringUtilities.removeInvalidCharacter(trimmedResourceTag,' ');
		return validationString.contains("main=\"true\"") || (validationString.contains("main=\"false\""));
	}

	private String extractJar(String value) {
		// TODO TEST 15.11.2018
		String result=null;
		String trimmedHrefTag=value;		
		int firstQuote=trimmedHrefTag.indexOf("\"");
		if (firstQuote>0) trimmedHrefTag=trimmedHrefTag.substring(firstQuote +1);		
		if (isMainDefined(trimmedHrefTag)) {
			trimmedHrefTag=trimmedHrefTag.substring(0, trimmedHrefTag.lastIndexOf("main"));
		}
		trimmedHrefTag=trimmedHrefTag.substring(0, trimmedHrefTag.lastIndexOf("\""));
		if (null!=trimmedHrefTag && trimmedHrefTag.trim().length()>0) result=trimmedHrefTag;
		return result;
	}

	private String extractXMLvalue(String xmlString, String tagName) {
		String starttag="<"+tagName+">";
		String stopTag="</"+tagName+">";
		return extractValue(xmlString, starttag, stopTag);
	}

	private String extractValue(int offset,String xmlString, String starttag, String stopTag) {
		String result="";
		int startPoint = xmlString.indexOf(starttag);
		int endPoint = xmlString.indexOf(stopTag,startPoint+starttag.length());
		if ((startPoint >-1) &&(endPoint > -1)) result=xmlString.substring(startPoint + starttag.length(), endPoint).trim();
		return result;
	}
	
	private String extractValue(String xmlString, String starttag, String stopTag) {
		return extractValue(0, xmlString, starttag, stopTag);
	}

	private void extractConfig(String globalResources) {
		String[] globalResourcesCol = globalResources.split("\"");
        for (int i = 0, n = globalResourcesCol.length; i < n; i++) {
        	String resource = globalResourcesCol[i].replace(" ", "");
        	if(resource.equals("j2seversion=")) javaVersion = globalResourcesCol[i+1];
        	if(resource.equals("initial-heap-size=")) initialHeapSize = globalResourcesCol[i+1];
        	if(resource.equals("max-heap-size=")) maxHeapSize = globalResourcesCol[i+1];
        }
	}

	public  File getJnlpFile() {
		// TODO TEST 15.11.2018
		return jnlpFile;
	}

	public  Vector<String> getRequestedJars() {
		// TODO TEST 15.11.2018
		return requestedJars;
	}

	public  String getInitialHeapSize() {
		// TODO TEST 15.11.2018
		return initialHeapSize;
	}

	public  String getMaxHeapSize() {
		// TODO TEST 15.11.2018
		return maxHeapSize;
	}

	public  String getJavaVersion() {
		// TODO TEST 15.11.2018
		return javaVersion;
	}

	public  String getMainClass() {
		// TODO TEST 15.11.2018
		return mainClass;
	}

	public  String getUiName() {
		// TODO TEST 16.11.2018
		return uiName;
	}
	
	public boolean hasValidHeapSizeArguments() {
		boolean result = false;
		result=((null!=getInitialHeapSize()) &&
				(getInitialHeapSize().trim().length()>0) &&
				(null!=getMaxHeapSize()) && 
				(getMaxHeapSize().trim().length()>0)
			   );
		return result;
	}

	public  String getIconPath() {
		// TODO TEST 20.11.2018
		return iconPath;
	}

	public String getBgImagePath() {
		// TODO TEST 20.11.2018
		return bgImagePath;
	}

}
