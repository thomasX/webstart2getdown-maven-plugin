package info.margreiter.getdown.maven.plugin.uitl;

import java.util.Vector;

public class ExtendedStringtokenizer {
	//TODO Test 15.11.2018
	  private Vector<String> tokens;
	  
	  /***
	   * 
	   * @param string
	   * @param delimiter delimitter is a hole word
	   */
	  public ExtendedStringtokenizer (String src, String wordDelimiter)
	  //TODO Test 15.11.2018
	  {
	    tokens  = new Vector<String>();
	    String remaining = src;
	    while ((null!=remaining) && (remaining.length()>0)) {
	    	int position=remaining.indexOf(wordDelimiter);
	    	if (position>-1) {
	    		if (position > 0) {
	    			String prefix=remaining.substring(0,position);
	    			tokens.add(prefix);
	    		}
	    		remaining=remaining.substring(position + wordDelimiter.length());
	    	} else {
	    		tokens.add(remaining);
	    		remaining=null;
	    	}
		}
	  }


	  public int countTokens()
	  {
	    return tokens.size();
	  }

	  
	  public Vector<String> getAllTokens() {
			// TODO TEST 15.11.2018
			return tokens;
	  }

	}