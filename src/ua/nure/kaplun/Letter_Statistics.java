package ua.nure.kaplun;

import java.io.*;
import java.util.*;

/**
 * Created by Anton on 12.02.2017.
 */
public class Letter_Statistics {
    public static HashMap<Character,Integer> getCountStatistics(String file_path){
        HashMap<Character,Integer> result = new HashMap<>();
        try(InputStreamReader reader = new InputStreamReader(new FileInputStream(file_path), "UTF-8"))
        {
            int c;
            while((c=reader.read())!=-1){
                if(Character.isLetter((char)c)) {
                    char currentChar = Character.toLowerCase((char) c);
                    if(result.get(currentChar)!=null) {
                        result.put(currentChar, result.get(currentChar) + 1);
                    }
                    else{
                        result.put(currentChar, 1);
                    }
                }
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        return result;
    }

    public static LinkedHashMap<Character, Double> getPercentStatistics(String file_path){
        HashMap<Character,Integer> countHash = getCountStatistics(file_path);
        LinkedHashMap<Character, Integer> sortedHash = sort(countHash);
        LinkedHashMap<Character, Double> percentHash=new LinkedHashMap<Character, Double>();
        Integer letterSum=getLetterSum(sortedHash);
        for(Map.Entry<Character,Integer>entry : sortedHash.entrySet()){
            Character key = entry.getKey();
            Double letterPercent = (double)(entry.getValue())*100/letterSum;
            percentHash.put(key, letterPercent);
        }
        return percentHash;
    }

    public static int getLetterSum(HashMap<Character,Integer> countHash){
        int sum=0;
        for(Map.Entry<Character,Integer> entry : countHash.entrySet()){
            sum += entry.getValue();
        }
        return sum;
    }

    public static void printCountHashMap(HashMap<Character,Integer> hash){
        for(Character ch : hash.keySet()){
            int value = hash.get(ch);
            System.out.println(ch + " => " + value);
        }
    }

    public static void printPercentHashMap(HashMap<Character,Double> hash){
        for(Character ch : hash.keySet()){
            double value = hash.get(ch);
            System.out.println(ch + " => " + value + " %");
        }
    }
    private static LinkedHashMap<Character, Integer> sort(HashMap<Character, Integer> hash){
        List<Map.Entry<Character, Integer>> list = new ArrayList(hash.entrySet());
        LinkedHashMap<Character, Integer> sortedHash=new LinkedHashMap<Character, Integer>();
        Collections.sort(list, new Comparator<Map.Entry<Character, Integer>>() {
            @Override
            public int compare(Map.Entry<Character, Integer> a, Map.Entry<Character, Integer> b) {
                return b.getValue()-a.getValue();
            }
        });
        for(Map.Entry<Character, Integer> o: list){
            sortedHash.put(o.getKey(), o.getValue());
        }
        return sortedHash;
    }


    public static void main(String[] args){
        System.out.println("Count Statistics");
        HashMap<Character,Integer> h = getCountStatistics("./FilesForAnalaszing/Простой текст для проверки статистики.txt");
        printCountHashMap(sort(h));

        System.out.println("Percent Statistics");
        HashMap<Character,Double> percentHash=getPercentStatistics("./FilesForAnalaszing/Простой текст для проверки статистики.txt");
        printPercentHashMap(percentHash);
    }
}
