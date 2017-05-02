package com.icodejava.research.nlp.stemmer;

import java.util.ArrayList;
import java.util.List;

import com.icodejava.research.nlp.domain.CompoundWordEnding;
import com.icodejava.research.nlp.domain.Grammar;
import com.icodejava.research.nlp.utils.TextUtils;

/**
 * 
 * @author Kushal Paudyal www.sanjaal.com | www.inepal.org | www.icodejava.com
 * 
 *         Stemmer Class for Nepali Words
 * 
 *         Stemming is a process of finding the root word from a compound word.
 *         e.g. "स्थानलगायत" is stemmed to "स्थान"
 *
 */
public class NepaliStemmer {
	
	public static void main(String [] args) {
		getNepaliRootWord("समयभन्दा");
	}

	/**
	 * This method finds a root word for a given compound Nepali Word. 
	 * For example: "स्थानलगायत" is stemmed to "स्थान"
	 * 
	 * @param compoundWord
	 * @return root word
	 */
	public static String getNepaliRootWord(String compoundWord) {
		for (CompoundWordEnding dir : CompoundWordEnding.values()) {
			String cwe = dir.getNepaliWordEnding();

			if (compoundWord.endsWith(cwe) && isNotTheSameWord(compoundWord, cwe)
					&& isAllowedLength(compoundWord, cwe)) {
				compoundWord = TextUtils.replaceLast(compoundWord, cwe, "");
			}

		}

		compoundWord = compoundWord.trim();

		return compoundWord;
	}

    /**
     * Checks if two words are same. For example, we want to no more apply the
     * stemming rule if the word is लगायत although लगायत is a compound word
     * ending and would have been removed from other compound words
     * 
     * @param compoundWord
     * @param cwe
     * @return
     */
    private static boolean isNotTheSameWord(String compoundWord, String cwe) {
        return compoundWord.length() > cwe.length();
    }

	private static boolean isAllowedLength(String compoundWord, String cwe) {
		
		boolean allowed = true;
		
		String cweMatraReplaced = replaceMatras(cwe);
		
		if(cweMatraReplaced.length() == 1) {
			String matraReplacedWord = replaceMatras(TextUtils.replaceLast(compoundWord, cwe, "")); //TODO: Document this
			allowed = matraReplacedWord.length() > 2; //to prvent words like थुम्का being split as थुम् + का
			
		}
		
		return allowed;
	}

	/**
	 * This method takes a Nepali Word, repalces all the matras and returns the
	 * transformed word. e.g. ट्रान्सफर --> टरनसफर
	 * 
	 * @param word
	 * @return
	 */
	public static String replaceMatras(String word) {

		for (Character c : Grammar.SET_OF_MATRAS.toCharArray()) {
			word = word.replace(c + "", "");
		}

		return word;
	}
	
	/**
	 * 
	 * @param word
	 * @return
	 */
	public static List<String> getVerbVariations(String wordEndingInNU) {
	    List<String> verbVariations = new ArrayList<String>();
	    
	    if(wordEndingInNU == null) {
	        return verbVariations;
	    }
	    
	    String root = getVerbRoot(wordEndingInNU); //NU 
	    
	    //[, रमानोस, रमानुस, रमानुहोस, रमानेछु, रमानुहुनेछ, रमानेछन, रमान्छन, रमान्छिन, रमानुभयो, , , रमानेछौ, , , रमानेछिन, रमानेछ, रमानुहुनेछ, रमान्छु, रमान्छे, रमान्छ]
	    
	    verbVariations.add(root); //खा, रमा
	    verbVariations.add(wordEndingInNU); //खानु,रमाउनु
	    verbVariations.add(root + "एँ"); //खाएँ, रमाएँ
	    verbVariations.add(root + "यो"); //खायो, रमायो
	    verbVariations.add(root + "एको");  //खाएको, रमाएको
	    verbVariations.add(root + "एछ"); //खाएछ, रमाएछ
	    verbVariations.add(root + "ए");//खाए, रमाए
	    verbVariations.add(root + "ईन");//खाईन, रमाईन
	    verbVariations.add(root + "ईस"); //खाईस, रमाईस
	    verbVariations.add(wordEndingInNU + "भयो"); //खानुभयो, रमाउनुभयो
	    verbVariations.add(root + "ई"); // खाई, रमाई
	    verbVariations.add(root + "ऊ");//खाऊ, रमाऊ
	    verbVariations.add(root + "यौ"); //खायौ, रमायौ
	    verbVariations.add(root + "इछे"); //खाइछे, रमाइछे
	    verbVariations.add(root + "एछ"); //खाएछ, रमाएछ
	    
	    verbVariations.add(root + "नोस"); //खानोस
	    verbVariations.add(root + "नुस"); //खानुस
	    verbVariations.add(root + "नुहोस"); //खानुहोस
	    verbVariations.add(root + "नेछु"); //खानेछु
	    verbVariations.add(root + "नुहुनेछ");//खानुहुनेछ
	    verbVariations.add(root + "नेछन");//खानेछन
	    verbVariations.add(root + "न्छन");//खान्छन
	    verbVariations.add(root + "न्छिन");//खान्छिन
	    verbVariations.add(root + "नुभयो");//खानुभयो
	    verbVariations.add(root + "नेछौ"); //खानेछौ
	    verbVariations.add(root + "नेछिन"); //खानेछिन
	    verbVariations.add(root + "नेछ"); //खानेछ
	    verbVariations.add(root + "नुहुनेछ"); //खानुहुनेछ
	    verbVariations.add(root + "न्छु"); //खान्छु
	    verbVariations.add(root + "न्छे"); //खान्छे
	    verbVariations.add(root + "न्छ"); //खान्छ
	    
	    System.out.println(verbVariations);
	    return verbVariations;
	    
	}

    public static String getVerbRoot(String wordEndingInNU) {
        if(wordEndingInNU == null || !wordEndingInNU.endsWith("नु") || wordEndingInNU.indexOf(" ") >0) {
            System.out.println("Invalid Word or Word Ending " + wordEndingInNU + " Verb must end in: नु");
            return wordEndingInNU;
        }
        
        String wordWithoutNU ="";
        if(wordEndingInNU.endsWith("उनु")) {
            wordWithoutNU = TextUtils.replaceLast(wordEndingInNU, "उनु", "");
        } else if (wordEndingInNU.endsWith("नु")) {
            wordWithoutNU = TextUtils.replaceLast(wordEndingInNU, "नु", "");
        }
        
        System.out.println(wordWithoutNU);
        
        return wordWithoutNU;
    }

}
