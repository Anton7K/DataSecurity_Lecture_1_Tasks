package ua.nure.kaplun;


/**
 * Created by Anton on 11.02.2017.
 */
public class Caesar_cipher {
    private static  final String RUSSIAN_ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    
    public static String encrypt(String text, int offset){
       char[] inputChars = text.toCharArray();
       StringBuilder result=new StringBuilder();
       for(char ch : inputChars){
           boolean isUpperCase = Character.isUpperCase(ch);
           char LowerCaseChar = Character.toLowerCase(ch);
           int currentCharIndex=RUSSIAN_ALPHABET.indexOf(LowerCaseChar);
           int newCharIndex = currentCharIndex + offset;

           if(currentCharIndex==-1){
               result.append(ch);
               continue;
           }

           while(newCharIndex >= RUSSIAN_ALPHABET.length()){
               newCharIndex -= RUSSIAN_ALPHABET.length();
           }
           while (newCharIndex <0){
               newCharIndex += RUSSIAN_ALPHABET.length();
           }
           char newCharacter = RUSSIAN_ALPHABET.charAt(newCharIndex);
           if(isUpperCase){
               newCharacter=Character.toUpperCase(newCharacter);
           }
           result.append(newCharacter);
       }
        return result.toString();
    }

    public static int getKey(char nonEncryptionChar, char encryptionChar){
       int key = RUSSIAN_ALPHABET.indexOf(encryptionChar)-RUSSIAN_ALPHABET.indexOf(nonEncryptionChar);
       if(key<0){
           key += RUSSIAN_ALPHABET.length();
       }
        return key;
    }

    public static String decrypt(String text, int offset){
        int decrKey=-offset;
        return encrypt(text, decrKey);
    }

    public static void main(String[] args){
        String encryptionText = encrypt("Агав",3);
        System.out.println(encryptionText);
    }
}
