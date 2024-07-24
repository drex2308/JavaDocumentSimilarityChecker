import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/**
 * @author Dhanush Venkataramu
 */
public class Similarity {
    /**
     * Instance variable HashMap to implement base data structure.
     *
     * I chose HashMap data structure from Java Collection framework,
     * collection interfaces under java.util.Map and are not true collections.
     * However, these interfaces contain collection-view operations,
     * which enable them to be manipulated as collections.
     *
     * I used this data structure because it provides fast search and insert operation,
     * O(1), and is best suited for our application where we insert large number of words,
     * often access these words and its frequency in <key, value> pair.
     */
    private Map<String, BigInteger> myMap = new HashMap<String, BigInteger>();
    /**
     * Instance variable to store the number of lines in given argument.
     */
    private int numOfLines;
    /**
     * Instance variable to store number of words, using BigInteger object to accommodate
     * for large duplicate words in processing large files.
     */
    private BigInteger numOfWords = BigInteger.valueOf(0);
    /**
     * Instance variable to store number of non-duplicate words.
     */
    private int numOfUniqueWords = 0;
    /**
     * Constructor to build data structures with string arguments.
     * @param string specifies the spring argument from user.
     */
    public Similarity(String string) {
        if (string == null || string.equals("")) {
            numOfLines = 0;
            return;
        }
        numOfLines = 1;
        for (String word: string.toLowerCase().split("\\W")) {
            if (!isWord(word)) {
                continue;
            }
            BigInteger freq = myMap.get(word);
            if (freq == null) {
                numOfUniqueWords++;
                numOfWords = numOfWords.add(BigInteger.ONE);
                freq = BigInteger.ONE;
            } else {
                numOfWords = numOfWords.add(BigInteger.ONE);
                freq = freq.add(BigInteger.ONE);
            }
            myMap.put(word, freq);
        }
    }
    /**
     * Constructor to build data structures with file arguments.
     * @param file string specifies the file argument from user.
     */
    public Similarity(File file) {
        numOfLines = 0;
        Scanner scanner = null;
        if (file == null) {
            return;
        }
        try {
            scanner = new Scanner(file, "latin1");
            while (scanner.hasNextLine()) {
                numOfLines++;
                String line = scanner.nextLine();
                for (String word : line.toLowerCase().split("\\W")) {
                    if (!isWord(word)) {
                        continue;
                    }
                    BigInteger freq = myMap.get(word);
                    if (freq == null) {
                        numOfUniqueWords++;
                        numOfWords = numOfWords.add(BigInteger.ONE);
                        freq = BigInteger.ONE;
                    } else {
                        numOfWords = numOfWords.add(BigInteger.ONE);
                        freq = freq.add(BigInteger.ONE);
                    }
                    myMap.put(word, freq);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot find the file"); //part of this code from @terrylee
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
    /**
     * Instance Method to return the number of words inserted.
     * @return a BigInteger object storing number of words.
     */
    public BigInteger numOfWords() {
        return numOfWords;
    }
    /**
     * Instance Method to return the number of lines from String/file argument.
     * @return the number of lines recorded.
     */
    public int numOfLines() {
        return numOfLines;
    }
    /**
     * Instance Method to return the number of non-duplicate words,
     * from Sting/file argument.
     *
     * @return the number of non-duplicate words recorded.
     */
    public int numOfWordsNoDups() {
        return numOfUniqueWords;
    }
    /**
     * Instance Method to calculate the euclidean norm,
     * utilizing BigDecimal Object to calculate squareRoot to client requirements.
     *
     * @return the euclidean norm of the frequencies stored.
     */
    public double euclideanNorm() {
        BigInteger norm = BigInteger.ZERO;
        for (String key: myMap.keySet()) {
            BigInteger freq = myMap.get(key);
            norm = norm.add(freq.multiply(freq));
        }
        BigDecimal normD = new BigDecimal(norm);
        return normD.sqrt(MathContext.DECIMAL128).doubleValue();
    }
    /**
     * Instance Method to calculate the dot product between 2 documents.
     *
     * Check for null arguments implemented.
     *
     * The calculations is implemented by accessing the key values set from
     * this instance map data structure and accessing same keys in argument map
     * data structure, multiplying these frequencies and total sum to get the dot product.
     *
     * The implementation has 2n access, n additions and n multiplications,
     * where n is the number of keys in this instance map data structure,
     * hence our average run time is 4n, worst case linear time complexity O(n).
     * My method through this implementation avoids quadratic running time on average.
     *
     * @param map specifies the map instance for document to be compared.
     * @return the dot product of two document frequencies.
     */
    public double dotProduct(Map<String, BigInteger> map) {
        if (map == null) {
            return 0;
        }
        BigInteger prod = BigInteger.ZERO;
        for (String key: myMap.keySet()) {
            BigInteger yFreq = map.get(key);
            if (yFreq == null) {
                continue;
            }
            prod = prod.add(myMap.get(key).multiply(yFreq));
        }
        return prod.doubleValue();
    }
    /**
     * Instance Method to calculate the distance/similarity between 2 documents.
     * Check for null arguments implemented, corner cases like
     * equal strings, empty strings and empty files covered, avoiding divide-by-zero.
     *
     * If two strings or documents are identical, we avoid NaN output from acos() function
     * for this case by checking if dotProduct and euclidean norms product are equal.
     *
     * @param map specifies the other document used to compare with this document.
     * @return 0 if two document are identical, pi/2 if they don't share any
     *         common words (same case if any/both string/document is/are empty).
     */
    public double distance(Map<String, BigInteger> map) {
        if (map == null) {
            return Math.acos(0);
        }
        if (numOfLines == 0 || map.size() == 0) {
            return Math.acos(0);
        }
        double dotProd = dotProduct(map);
        double normCombined = euclideanNorm() * euclideanNorm(map);
        if (dotProd == Math.ceil(normCombined)) {
            return 0;
        }
        return Math.acos(dotProd / normCombined);
    }
    /**
     * Instance Method to get the hash map data structure of any instance.
     * @return a deep-copy of the private hash map variable.
     */
    public Map<String, BigInteger> getMap() {
        Map<String, BigInteger> res = new HashMap<String, BigInteger>();
        for (String word: myMap.keySet()) {
            BigInteger resFreq = new BigInteger(myMap.get(word).toString());
            res.put(word, resFreq);
        }
        return res;
    }
    /**
     * Private helper method to calculate euclidean norm for
     * any given hash map with frequencies.
     *
     * @param map specifies the document's hash map with <key, value> pairs.
     * @return the euclidean norm of frequencies in hash map provided.
     */
    private double euclideanNorm(Map<String, BigInteger> map) {
        BigInteger norm = BigInteger.ZERO;
        for (String key: map.keySet()) {
            BigInteger freq = map.get(key);
            norm = norm.add(freq.multiply(freq));
        }
        BigDecimal normD = new BigDecimal(norm);
        return normD.sqrt(MathContext.DECIMAL128).doubleValue();
    }
    /**
     * Private helper method to check for validate words.
     * @param text specifies the word to be validated.
     * @return true if valid word or false otherwise.
     */
    private boolean isWord(String text) {
        if (text == null) {
            return false;
        }
        return text.matches("^[a-zA-Z]+$");
    }
}
