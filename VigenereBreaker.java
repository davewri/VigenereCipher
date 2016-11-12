import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder s = new StringBuilder();
        for ( int i = whichSlice; i< message.length() ; i+= totalSlices) {
            s.append(message.charAt(i));
        }
        return s.toString();
    }


    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker cc = new CaesarCracker(mostCommon);
        for (int i = 0; i < klength ; i++) {
            String s = sliceString(encrypted, i, klength);
            int dkey = cc.getKey(s);
            key[i] = dkey;
        }
        return key;
    }

    public String calculateKey(int [] key) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder keyValue = new StringBuilder();
        
        for (int i : key) {
            keyValue.append(alphabet.charAt(i));
        }
        
        return keyValue.toString();
    }

    public void breakVigenere () {
        FileResource fr = new FileResource("TestData/secretmessage.txt");
        String message = fr.asString();
        int [] key = tryKeyLength(message, 4, 'e');
        VigenereCipher vc = new VigenereCipher(key);
        String decrypt = vc.decrypt(message);
        System.out.println(decrypt);
        System.out.println(Arrays.toString(key));
        System.out.println(calculateKey(key));
    }

    public HashSet<String> readDictionary(FileResource fr){
        HashSet<String> dict = new HashSet<String>();
        for(String word : fr.lines()){
            dict.add(word.toLowerCase());
        }
        return dict;
    }
    
    public int countWords(String message, HashSet<String> dictionary){
        int count = 0;
        String[] arr = message.split("\\W+");
        for(int i = 0; i < arr.length; i++) {
            if(dictionary.contains(arr[i].toLowerCase())) {
                count++;
            }
        }
        return count;
    }

    public String breakForLanguage(String encrypted, HashSet<String> dictionary){
        String decrypted = "";
        int max = 0;
        int[] decryptKey = {};
        for(int i = 0; i < 100; i++){
            int[] key = tryKeyLength(encrypted, i + 4, 'e');
            VigenereCipher vc = new VigenereCipher(key);
            String current = vc.decrypt(encrypted);
            if(countWords(current, dictionary) > max){
                decrypted = current;
                max = countWords(current, dictionary);
                decryptKey = key;
            }
        }
        System.out.println(Arrays.toString(decryptKey));
        System.out.println(calculateKey(decryptKey));
        System.out.println(max);
        return decrypted;
    }

    public void breakVigenereNew () {
        FileResource fr = new FileResource("TestData/secretmessages2.txt");
        String encrypted = fr.asString();        
        FileResource dictionaryResource = new FileResource("dictionaries/English");
        HashSet<String> dictionary = readDictionary(dictionaryResource);
        String decrypted = breakForLanguage(encrypted, dictionary);
        System.out.println(decrypted);
    }
    
}
