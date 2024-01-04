package be.helha.assurapp.authentication.utils;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomStringGenerator {


   public String generateRandomString(){
       StringBuilder stringBuilder = new StringBuilder();

       for (int i=0; i<16; i++){
           stringBuilder.append(generateRandomChar());
           if ((i + 1) % 4 == 0 && i!=15){
               stringBuilder.append("-");
           }
       }

       return stringBuilder.toString();
   }

   private char generateRandomChar(){
       char randomChar;
       Random random = new Random();
       int randomValue = random.nextInt(36);

       if (randomValue < 10){
           randomChar = (char) ('0' + randomValue);
       } else {
           randomChar = (char) ('A' + randomValue - 10);
       }

        return randomChar;
    }
}
