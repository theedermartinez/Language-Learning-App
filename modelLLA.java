import java.io.*;//to use file input stream
import java.util.*;//to use the scanner and random
public class modelLLA
{
    //vairables for high score tracking
    private HashMap <String,Integer> highScore = new HashMap <String, Integer>();//holds high score for all and we just get the individual one 
    private ArrayList<String> currentHighScoreARR = new ArrayList<>();//to hold all the high score keys and acces them later
    private int currentHScore = 0;//holds current high score
    
    //variables for array list passing and displaying 
    private ArrayList<String> keyString = new ArrayList<>();//holds keys of the dictionaryArea, used by private function
    private HashMap <String,String> dictionaryWord = new HashMap<String,String>(); //hashmap returned by the class
    private HashMap<String,String>madeString;//holds keys of the dictonary 
    private int lengthCurrList;

    private String currentChoice;



    int setToDo = 0;
    int menuChoice;
    //no constructors
    public String displayMenu()throws FileNotFoundException
    {
        FileInputStream menu = new FileInputStream("vocabToPracticeMenuLLA.txt");
        Scanner scnr = new Scanner(menu);
        String menuString = "";//must start it as an empty string

        while(scnr.hasNext())
        {
            menuString += scnr.nextLine() + "\n"; //we add on to it on every iteration 
        }
        return menuString;
    }
    
    //gets the set that will be practiced, also sets the high score by passing the name and retriving the correct hashmap!
    public HashMap getSetToStudy(int userChoice) throws FileNotFoundException
    {
        if (userChoice == 1) 
        {
            madeString = makeString("vocabWords/greetings.txt");
            setHighScore("greetings");
            currentChoice = "greetings";
        }
        else if(userChoice == 2)
        {
            madeString = makeString("vocabWords/days.txt");
        }
        else if(userChoice == 3)
        {
            madeString = makeString("vocabWords/months.txt");
        }
        return madeString;
    }
//Will give us a unique 10 digit 
    public int[] makeSeed()
    {
        Random rng = new Random();
        int listLenth = lengthCurrList;//change one variable to change the length of the list
        int[] seed = new int[listLenth];// with a lenght of 10
        int counter = 0;
        boolean found = false;
        while(counter <listLenth)
        {
            found = false;
            int randomNum = rng.nextInt(listLenth);
            for(int i = 0; i<counter;i++)// we iterate to counter to save resources
            {
                if(seed[i]==randomNum)
                {
                found = true;
                break;//break from the for loop
                }
            }

            if(found == false) // we have to put this on a if stateent because if we dont it will always execute
            {
                seed[counter] =randomNum;
                counter++;
            }
        }
        return seed;
    }

    public String userGuessOnWord()
    {
        Scanner s = new Scanner(System.in);
        String guess;
        try
        {
            guess = s.nextLine();
            guess.toLowerCase();
        }catch(IllegalArgumentException iae)
        {
            guess = "Error use only valid input";
        }
        return guess;
    }

    

    public void pauseToContinue()
    {
        Scanner s = new Scanner(System.in);
        s.nextLine();
    }


    public int setMenuChoice()
    {
        Scanner s = new Scanner(System.in);
        //we make a while loop to ensure we get a number;
        try
        {
            menuChoice = s.nextInt();
            //this line will only excecute if we pass the int test
        }
        catch(InputMismatchException IME)
        {
            menuChoice = -1;
        }
        return menuChoice;
    }
////////////////high scores functions//////////////////
    public void highScoreCurrent() throws FileNotFoundException
    {
        FileInputStream highScores = new FileInputStream("highScores.txt");//we open the file
        Scanner scnr = new Scanner(highScores);

        while(scnr.hasNextLine())
        {
            String currHighScore = scnr.nextLine();//scans the line
            String[] currentHighScore = currHighScore.split("-");//makes a list from the line and splits it using split method WE HAVE A VARIABLE HERE THAT IS GLOBAL BUT PRIVATE
            int lineHighScore = Integer.parseInt(currentHighScore[1]);//parses the string into an int
            Integer highScoreInt = Integer.valueOf(lineHighScore);//turns the int into an integer to be added into object
            highScore.put(currentHighScore[0],highScoreInt);//puts it into a HashMap
            currentHighScoreARR.add(currentHighScore[0]);//adds it to the global array string

        }
    }

    public void setHighScore(String name)
    {
        currentHScore = highScore.get(name);
        System.out.println(highScore.get(name)+"*************");///NEEDS MOVED *************
    }

    public void updateScores(int newScore) throws FileNotFoundException
    {
        System.out.println(newScore +"************");
        if(currentHScore < newScore)//checks if the current score and new scores 
        {
            highScore.put(currentChoice, newScore);
            writeScoresToText();
        }
    }

    public void writeScoresToText() throws FileNotFoundException
    {
        FileOutputStream fos = new FileOutputStream("highScores.txt");
        PrintWriter outFOS = new PrintWriter(fos);

        for(int w = 0; w < highScore.size();w++)
        {
            outFOS.println(currentHighScoreARR.get(w) + "-" +highScore.get(currentHighScoreARR.get(w)));
        }

        outFOS.close();
    }


/////////////get set//////
    public ArrayList getListKeys()
    {
        return keyString;
    }

    public int getCurrentHighScore()
    {
        return currentHScore;
    }


    ///////////////////game mode//////////////////////////////get logic and keep track of it
    //will return a word depending on the position of the index given and a hashmap given
    
    //public String giveWord(HashMap <String,String> st)
    //{
    //    Random newRandomNum = new Random();

    //}
    ///////////////Private to use only within////////////////////////
    // we are going to use a string maker to only write once and use different file names we return a hashmap
    private HashMap makeString(String nameOfFile) throws FileNotFoundException
    {
        File fileToUse = new File(nameOfFile);// file to open we use a path
        FileInputStream fileToOpen = new FileInputStream(fileToUse);
        Scanner scnr = new Scanner(fileToOpen);
        lengthCurrList = 0;//we use this for the list of items that are on the list
        //either we put it direcly into a hasmap or list then hashmap
        while(scnr.hasNext())
        {
            String line = scnr.nextLine();
            String[] wordAndKey = line.split("-");
            dictionaryWord.put(wordAndKey[0],wordAndKey[1]);//put key and value into the hashmap
            keyString.add(wordAndKey[0]);//we put it into an arraylist that will be used to get each values
            lengthCurrList++;
        }
        return dictionaryWord;
    }


}
