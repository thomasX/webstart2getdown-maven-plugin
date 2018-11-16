package info.margreiter.getdown.maven.plugin.uitl;


public class StringUtilities {
	//TODO Test 16.11.2018
	/**
	 * 
	 * @param source
	 * @param validChars
	 * @param replacment
	 * @param maxLength
	 * @return
	 */
	public static String removeInvalidCharacter(String source,char invalidChar) {
		String result = "";
		char[] allchars = source.toCharArray();
		for (int i = 0, n = allchars.length; i < n; i++) {
			if (allchars[i] != invalidChar) result +=allchars[i]; 	        
		}
		return result;  
	}
	
	public static String removeCRuLF(String source) {	    
		String result=removeInvalidCharacter(source, '\r');
		result=removeInvalidCharacter(result, '\n');
		return result;
	}
	public static String extendedTrim(String src) {
		String result=removeInvalidStartingCharacters(src, "\t ");
		result=removeInvalidEndingCharacters(result, "\t ");
		return result;
	}
	
	/**
	 * does remove all beginning chararacters['\t',' ']
	 * @return
	 */
	public static String removeInvalidStartingCharacters(String source,String invalidCharacters) {
		String result = source;
		char[] allchars = source.toCharArray();
		boolean validChar=false;		
		for (int i = 0, n = allchars.length; i < n ; i++) {
			String  curChar=new String(new char[]{allchars[i]});			
			if (! invalidCharacters.contains(curChar)) break;
			if (invalidCharacters.contains(curChar)) result=result.substring(1);
		}
		return result;  
	}
	/**
	 * does remove all beginning chararacters['\t',' ']
	 * @return
	 */
	public static String removeInvalidEndingCharacters(String source,String invalidCharacters) {
		String result = source;
		char[] allchars = source.toCharArray();
		boolean validChar=false;		
		for (int i = allchars.length-1,n=0; i > n; i--) {
			String  curChar=new String(new char[]{allchars[i]});			
			if (! invalidCharacters.contains(curChar)) break;
			if (invalidCharacters.contains(curChar)) result=result.substring(0,result.length()-1);
		}
		return result;  
	}

}
