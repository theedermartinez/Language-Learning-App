import java.util.HashMap;

public class viewLLA
{
    //overloaded funtion
    public void printToUser(String printThis)
    {
        System.out.println(printThis);
    }
    //overloaded function
    public void printToUser(HashMap <String,String>hm)//Must specify type to avoid object issue
    {
        System.out.println("Please memorize the following");
        for(String word: hm.keySet())
        {
            String value = hm.get(word);
            System.out.println("WORD: "+ word+" MEANING: "+value );
        }
    }



}