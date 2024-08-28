//main brain of the app

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.logging.Handler;

public class LLA
{
    public static void main(String[] args) throws FileNotFoundException
    {   

        viewLLA viewLLA = new viewLLA();//creating a new object view
        modelLLA modelLLA = new modelLLA();//creating new object model

        //initialize the high scores at the beginning 
        modelLLA.highScoreCurrent();// this splits the list but does not select one to be displayed

        //print greeting
        viewLLA.printToUser("Hello and welcome to Learning Language App. English -> Spanish is only supported at the moment. Feel free to practice vocabulary with" +
        "us\n **Written and Planned by Eder Martinez for Miss Ella**\nSelect from the following topics\n***********************************************");
        //get list of menu items
        String menuDisplay = modelLLA.displayMenu();
        //display the menus
        viewLLA.printToUser(menuDisplay);


        ///we probably need to loop this!!!!

        //time to ask the user what they want to study
        viewLLA.printToUser("What would you like to study today?");

        //get reply from user and validate it
        int choiceByUser = modelLLA.setMenuChoice();
        while(choiceByUser < 0 || choiceByUser > 24)
        {
            viewLLA.printToUser("Invalid choice, please give a valid digit");
            choiceByUser = modelLLA.setMenuChoice();
        }
        //we got a choice, now we grab the menu vocab
        HashMap<String, String> setToStudy = modelLLA.getSetToStudy(choiceByUser);//creates a hashmap depending on the input of the user*************hashmap!!!1
        //lets send it over to be displayed 
        viewLLA.printToUser(setToStudy);
        viewLLA.printToUser("\n\nCurrent High Score is: " + modelLLA.getCurrentHighScore());
        ////////LOGIC AND LOOP TO RUN THE GAME/////////////
        int[] seed = modelLLA.makeSeed();
        int totalScore = 0;

        for(int i = 0; i<seed.length;i++)//lets use the length of the list 
        {
            ArrayList<String> keyList = modelLLA.getListKeys();
            viewLLA.printToUser("Ready to guess? your word is the following!");
            viewLLA.printToUser(keyList.get(seed[i]));
            viewLLA.printToUser("Enter your guess now: \b");
            String userGuess = modelLLA.userGuessOnWord();
            viewLLA.printToUser("your guess was: " + userGuess);
            boolean correct = false;
            String correctAnswer = setToStudy.get(keyList.get(seed[i]));
            //System.out.println(correctAnswer); for testing
            if(userGuess.equalsIgnoreCase(correctAnswer))
            {
                viewLLA.printToUser("Correct adding points");
                correct = true;
                totalScore++;
            }
            if(correct == false)
            {
                viewLLA.printToUser("Incorrect answer, no points added");
            }
            viewLLA.printToUser("\n");

        }
        viewLLA.printToUser("Total Points" + totalScore + "good job miss Ella pie:)");
        //we send it over to update the score if we need to
        modelLLA.updateScores(totalScore);
    }
}