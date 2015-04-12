/*********************************************************
  version that has word net check if word is in word net
  version 1
  
  *********************************************************/

public class NounReplacer {
    /*********************************************************/
    /* Run DFA, replace nouns from WordNet                   */
    /*********************************************************/
    public static void main(String[] args)
    {
      
        // Construct WordNet
        String synsetFilename = args[0];
        String hypernymFilename = args[1];
        WordNetHP wordnet = new WordNetHP(synsetFilename, hypernymFilename);
        
        // Initiate text input
        //In in = new In(args[2]);
        String in = args[2]; //
        
        // Initialize output file
        String output = "";
        boolean inWord = false;
        String word = "";
        
        // Read from input and run DFA
        //while (!in.isEmpty())
        for (int i = 0; i < in.length(); i++) 
        {
            char c = in.charAt(i); 
            //char c = in.readChar();
            
            if (!inWord)
            {
                if (Character.isLetter(c))
                {
                    inWord = true;
                    word = "" + c;
                }
                else
                {
                    StdOut.print(c);
                    word = "";
                }
            }
            
            else
            {
                if (Character.isLetter(c))
                    word = word + c;
//                else if (c == 'Õ' || c == '\'')
//                {
//                    StdOut.print(word);
//                    StdOut.print(c);
//                    word = "";
//                }
                else 
                {
                    if (isValid(word)) 
                        word = wordnet.traverse(word, 1);
                    word = removeUnderscore(word);
                    StdOut.print(word);
                    StdOut.print(c);
                    inWord = false;
                    word = "";
                }
            }
        }
        
        if (!word.equals(""))
        {
            if (isValid(word)) 
                word = wordnet.traverse(word, 3);
            word = removeUnderscore(word);
            StdOut.print(word);
        }
            
       StdOut.println();
    }
    
    /*********************************************************/
    /* Remove underscore from words                          */
    /*********************************************************/
    private static String removeUnderscore(String replacer)
    {
        if (replacer.contains("_")) {
            String[] parts = replacer.split("_");
            String part1 = parts[0];
            String part2 = parts[1];
            replacer = parts[0] + " " + parts[1];
        }
        return replacer;
    }
    
    /*********************************************************/
    /* Verify word is valid to replace                       */
    /*********************************************************/
    private static boolean isValid(String word)
    {
        // No capitals
        // if (Character.isUpperCase(word.charAt(0)))
        //     return false;
        
        // No one letter words
        if (word.length() == 1)
            return false;
        
        String lower = word.toLowerCase();
        
        // Subject Pronouns
        if (lower.equals("he") || lower.equals("she") || lower.equals("it") 
              || lower.equals("we") || lower.equals("you") || lower.equals("they"))
            return false;
        
        // Object Pronouns
        if (lower.equals("him") || lower.equals("her") 
              || lower.equals("us") || lower.equals("them"))
            return false;
        
        // Possessive Pronouns
        if (lower.equals("his") || lower.equals("her") || lower.equals("your") 
            || lower.equals("our") || lower.equals("its") || lower.equals("their")
            || lower.equals("is") || lower.equals("are"))
            return false;
        
        // Contractions 
        if (lower.equals("can") || lower.equals("don") || lower.equals("won") 
                || lower.equals("might") || lower.equals("must") || lower.equals("haven"))
            return false;
        
        // Other Invalid Words
        if (lower.equals("in") || lower.equals("had") || lower.equals("have")  
            || lower.equals("yes") || lower.equals("no") || lower.equals("maybe")
            || lower.equals("at") || lower.equals("over") || lower.equals("thus")
            || lower.equals("may") || lower.equals("so") || lower.equals("read")
            || lower.equals("real") ||  lower.equals("even") || lower.equals("times")
            || lower.equals("enough") ||  lower.equals("much") || lower.equals("lot")
            || lower.equals("odd") ||  lower.equals("one") || lower.equals("last")
            || lower.equals("like") ||  lower.equals("do") || lower.equals("left")
            || lower.equals("right") ||  lower.equals("thus") || lower.equals("re")
            || lower.equals("as") ||  lower.equals("will") || lower.equals("is")
            || lower.equals("are") ||  lower.equals("were") || lower.equals("nothing")
            || lower.equals("then") ||  lower.equals("being") || lower.equals("go")
            || lower.equals("re")// ||  lower.equals("being") || lower.equals("go")
           )
           
            return false;
        
        return true;
    }
}