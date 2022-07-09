package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Main {

    public static void openJsonFile(String path) {

        JSONObject season;
        JSONArray scenes;
        JSONParser parser = new JSONParser();
        char answer;
        int manBechdel = 0;
        int womanBechdel = 0;

        try (Reader reader = new FileReader(path)) {
            season = (JSONObject) parser.parse(reader);
            scenes = (JSONArray) season.get("rows"); System.out.println("Number of scenes: " + scenes.size());
            for (int index = 0 ; index < scenes.size() ; index++) {
                answer = newBechdel(scenes.get(index).toString());
                if (answer == 'm') {
                    manBechdel++;
                }
                else if (answer == 'w') {
                    womanBechdel++;
                }
            }
            System.out.println("Bechdel-Men: " + manBechdel);
            System.out.println("Bechdel-Women: " + womanBechdel);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static char newBechdel(String currentScene) {
        String[] mensNamesWith = {"Joey:" , "Ross:" , "Chandler:"};
        String[] womensNamesWith = {"Monica:" , "phoebe:" , "Rachel:"};
        String[] mensNames = {"Joey" , "Ross" , "Chandler"};
        String[] womensNames = {"Monica" , "Phoebe" , "Rachel"};
        boolean manTalk = false;
        boolean womanTalk = false;

        for (int index = 0 ; index < mensNamesWith.length && index < womensNamesWith.length ; index++) {
            if (!manTalk && currentScene.contains(mensNamesWith[index])) {
                manTalk = true;
            }
            if (!womanTalk && currentScene.contains(womensNamesWith[index])) {
                womanTalk = true;
            }
            if (manTalk && womanTalk) {
                return 'f';
            }
        }
        if (manTalk) {
            for (int index = 0 ; index < womensNames.length ; index++) {
                if (currentScene.contains(womensNames[index])) {
                    return 'f';
                }
            }
            return 'm';
        }
        else if (womanTalk) {
            for (int index = 0 ; index < mensNames.length ; index++) {
                if (currentScene.contains(mensNames[index])) {
                    return 'f';
                }
            }
            return 'w';
        }
        return 'f';
    }



    public static void main(String[] args) throws FileNotFoundException {
        String basePath = "C:\\Users\\netad\\OneDrive\\Desktop\\TheNewBechdelFriendsTest\\s";
        String fileType = ".json";

        for (int index = 1 ; index <= 10 ; index++) {
            System.out.println("Season" + index + ":");
            openJsonFile(basePath + index + fileType);
            System.out.println("---------------------------------------------------------------------------------------------------");
        }
    }
}
