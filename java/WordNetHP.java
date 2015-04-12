/*********************************************************************************
 plural version
  **********************************************************************************/
import java.util.Random;

public class WordNetHP {
    //holds all WordNetHP nouns as keys and their synset id(s)
    private RedBlackBST<String, Bag<Integer>> nounIds;
    //keys are unique synset ids, and values are the synonym sets (synsets)
    private RedBlackBST<Integer, String> idNouns;
    private Digraph di; //will be directed graph of id's
    private Digraph reverseDi;
    private SAP info; //will be used for distance and sap methods
    
    // constructor takes the name of the two input files
    public WordNetHP(String synsets, String hypernyms) {
        //will use for nouns and isNoun methods 
        nounIds = new RedBlackBST<String, Bag<Integer>>();
        //will use to help create digraph and will use in sap method
        idNouns = new RedBlackBST<Integer, String>();
        In inSynsets = new In(synsets); //for reading in synsets text file
        
        while (!inSynsets.isEmpty()) {
            //each line contains: id, synset, and dictionary definition
            //(comma-separated)
            String input = inSynsets.readLine();
            String[] parts = input.split("\\,"); //split at commas (array is size 3)
            int uniqueId = Integer.parseInt(parts[0]); //store unique id
            idNouns.put(uniqueId, parts[1]); //add key: unique id, value: synonym set
            
            //split synset at commas to get all nouns in the synset
            String[] subNouns = parts[1].split("\\ ");
            for (int i = 0; i < subNouns.length; i++) {
                //enter if statment if this noun is not already in the tree
                if (!nounIds.contains(subNouns[i])) {
                    //queue to store ids of synsets containing the noun
                    Bag<Integer> ids = new Bag<Integer>();
                    //add key: noun, value: queue of ids
                    nounIds.put(subNouns[i], ids);
                    ids.add(uniqueId); //enqueue id to queue
                }
                //if queue was already created (ie, noun was already added before),
                //then just enqueue id
                else {
                    //enqueue id to existing queue
                    nounIds.get(subNouns[i]).add(uniqueId);
                }
            }
        }
        
        //create digraph with size of idNouns
        di = new Digraph(idNouns.size());
        In inHypernyms = new In(hypernyms); //for reading in hypernyms text file
        while (!inHypernyms.isEmpty()) {
            //each line contains synset id and ids of synset's hypernyms
            //(comma-separated)
            String input = inHypernyms.readLine();
            //split at commas (size of array depends on number of hypernyms)
            String[] parts = input.split("\\,");
            //store first item in array (synset id)
            int baseVertex = Integer.parseInt(parts[0]);
            //add all other ids in that line to the digraph (pointing to baseVertex)
            for (int i = 1; i < parts.length; i++) {
                di.addEdge(baseVertex, Integer.parseInt(parts[i]));
            }
        }
        reverseDi = di.reverse();
        
        
    }
    
    // returns all WordNetHP nouns
    public Iterable<String> nouns() {
        return nounIds.keys();
    }
    
    // is the word a WordNetHP noun?
    public boolean isNoun(String word) {
        return nounIds.contains(word);
    }
    
    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!this.isNoun(nounA)) throw new
            IllegalArgumentException("nounA not a WordNetHP noun.");
        if (!this.isNoun(nounB)) throw new
            IllegalArgumentException("nounB not a WordNetHP noun.");
        //length between two vertices (ids) in digraph (using SAP)
        return info.length(nounIds.get(nounA), nounIds.get(nounB));
    }
    
    
    public String traverse(String nounA, int degree) {
      
        // handle plurals
        boolean pluralS = false;

        if (!this.isNoun(nounA)) {
          
          // if plural change to singular version and add 's' later
          if (nounA.length() > 1 && nounA.charAt(nounA.length()-1) == 's') {
            String nounS = nounA.substring(0, nounA.length()-1);
            if (this.isNoun(nounS))
              pluralS = true;
          }
          // if not plural return the noun
          if (pluralS == false)
            return nounA;
        }
       
       if (pluralS == true)
          nounA = nounA.substring(0, nounA.length()-1);
      
        //if (!this.isNoun(nounA))
          //return nounA;
        
        
        // get id's of the noun
        Bag<Integer> ids = (Bag<Integer>) nounIds.get(nounA);
        int id = 0;
        
        // pick a random id
        int rand = StdRandom.uniform(ids.size() + 1);
        if (!ids.isEmpty()) {
          int i = 0;
          for (int bagId : ids) {
            id = bagId;
            i++;
            if (i == rand)
              break;
          }
        }
        else {
          return nounA;
        }
        
        // iterate through word net, can be either parent or child
        int nextVert = id;
        for (int i = 0; i < degree; i++) {
          int parentOrChild = StdRandom.uniform(2);
          if (parentOrChild == 1) {
            Bag<Integer> vertices = (Bag<Integer>) di.adj(nextVert);
            rand = StdRandom.uniform(vertices.size() + 1);
            int j = 0;
            for (int v : vertices) {
              j++;
              nextVert = v;
              if (j == rand)
                break;
            }
          }
          
         else {
           Bag<Integer> reVertices = (Bag<Integer>) reverseDi.adj(nextVert);
           rand = StdRandom.uniform(reVertices.size() + 1);
           int k = 0;
           for (int v : reVertices) {
             k++;
             nextVert = v;
             if (k == rand)
               break;
           }
         }
          
        }
        
        // choose a random string in the returned id
        String newNoun = idNouns.get(nextVert);
        String[] arrNoun = newNoun.split(" ");
        
        int index = new Random().nextInt(arrNoun.length);
        String random = (arrNoun[index]);
        
        // add back 's' if word was plural
        if (pluralS == true) {
          if (random.charAt(random.length()-1) != 's')
            random = random + "s";
          
        }
        
        return random;
        
    }
    
    // do unit testing of this class
    public static void main(String[] args) {
       
    }
   
}