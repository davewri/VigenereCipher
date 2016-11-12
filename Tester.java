
/**
 * Write a description of Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class Tester {
    
    public String logFile = "TestData/secretmessages2.txt";
    
    public void testSliceString() {
        String message = "abcdefghijklm";
        int whichSlice = 1;
        int totalSlices = 3;
        
        VigenereBreaker vb = new VigenereBreaker();
        
        String slice = vb.sliceString(message, whichSlice, totalSlices);
        System.out.println("sliced string result is : " + slice);
    }
    
    public void testTryKeyLength() {
        FileResource fr = new FileResource(logFile);
        String message = fr.asString();
        int[] key;
        
        VigenereBreaker vb = new VigenereBreaker();
        key = vb.tryKeyLength(message, 58, 'e');
        System.out.println(Arrays.toString(key));
        System.out.println(vb.calculateKey(key));
    }

}
