package ua.nure.kaplun;

/**
 * Created by Anton on 16.02.2017.
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CryptoAnalizing {

    private static final int COUNT_OF_THE_MOST_REPEATED_LETTERS = 4;
    public static char[] getMostRepeatedLetters(String pathToFile){
        char[] mostRepeatedLettersArr = new char[COUNT_OF_THE_MOST_REPEATED_LETTERS];
        HashMap<Character, Double> statistics = Letter_Statistics.getPercentStatistics(pathToFile);
        int i=0;
        for(Map.Entry<Character,Double> entry : statistics.entrySet()){
            if(i<COUNT_OF_THE_MOST_REPEATED_LETTERS){
                mostRepeatedLettersArr[i]=entry.getKey();
                i++;
            }
            else{
                break;
            }
        }
        return mostRepeatedLettersArr;
    }



    public static LinkedHashMap<Integer, Integer> getPossibleKeys(char[] encryptionLetters, char[] nonEncryptionLetters){
        LinkedHashMap<Integer, Integer> possibleKeys = new LinkedHashMap<Integer, Integer>();
        int key;
        for (char nonEncrChar : nonEncryptionLetters) {
            for (char encrChar : encryptionLetters){
                key = Caesar_cipher.getKey(nonEncrChar, encrChar);
                if(possibleKeys.get(key)!=null){
                    possibleKeys.put(key, possibleKeys.get(key)+1);
                }
                else {
                    possibleKeys.put(key, 1);
                }
            }
        }
        return sortPossibleKeys(possibleKeys);
    }
    private static LinkedHashMap<Integer, Integer> sortPossibleKeys(HashMap<Integer, Integer> hash){
        List<Map.Entry<Integer, Integer>> list = new ArrayList(hash.entrySet());
        LinkedHashMap<Integer, Integer> sortedHash=new LinkedHashMap<Integer, Integer>();
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> a, Map.Entry<Integer, Integer> b) {
                return b.getValue()-a.getValue();
            }
        });
        for(Map.Entry<Integer, Integer> o: list){
            sortedHash.put(o.getKey(), o.getValue());
        }
        return sortedHash;
    }

    public static void tryKeys(String text, LinkedHashMap<Integer, Integer> possibleKeys){
        int possibleKey;
        for(Map.Entry<Integer,Integer> entry : possibleKeys.entrySet()){
            possibleKey = entry.getKey();
            String decryptionText = Caesar_cipher.decrypt(text, possibleKey);

            System.out.println();
            System.out.println("Decryption for " + possibleKey + " key:");
            System.out.println(decryptionText);
            System.out.println("*****************************************************************");
        }
    }
    public static String readFileToString(String pathToFile){
        StringBuilder sb = new StringBuilder(512);
        try(InputStreamReader reader = new InputStreamReader(new FileInputStream(pathToFile), "UTF-8"))
        {
            int c;
            while((c=reader.read())!=-1){
                sb.append((char) c);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return sb.toString();
    }

    public static void analyzeСaesarCryptogram(String pathToEncryptionFile, String pathToFileForStatistics){
        String encrText = readFileToString(pathToEncryptionFile);
        char[] mostRepeatedEncryptionLetters = getMostRepeatedLetters(pathToEncryptionFile);
        char[] mostRepeatedNonEncryptionLetters = getMostRepeatedLetters(pathToFileForStatistics);

        LinkedHashMap<Integer, Integer> possibleKeys = getPossibleKeys(mostRepeatedEncryptionLetters, mostRepeatedNonEncryptionLetters);
        tryKeys(encrText, possibleKeys);
    }

    public static void main(String[] args){
        analyzeСaesarCryptogram("./FilesForAnalaszing/Зашифрованная Курочка Ряба.txt", "./FilesForAnalaszing/Колобок.txt");
    }
}
