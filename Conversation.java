import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * A class that simulates a conversation with a Chat Bot
 */
class Conversation implements ConversationRequirements {


  // Attributes 

  private ArrayList<String> transcript;
  private HashMap<String,String> mirrorDict;
  private static ArrayList<String> cannedResponses = new ArrayList<>();

  /**
   * Constructs an instance of the Conversation class.
   */
  Conversation() {
    this.transcript = new ArrayList<>();
    this.mirrorDict = new HashMap<>();
    mirrorDict.put(" i'm ", " you're_ ");
    mirrorDict.put(" you ", " I_ "); //works
    mirrorDict.put(" me ", " you_ ");
    mirrorDict.put(" am ", " are_ ");//works
    mirrorDict.put(" are ", " am_ ");
    mirrorDict.put(" I ", " you_ ");
    mirrorDict.put(" your ", " my_ ");
    mirrorDict.put(" my ", " your_ ");
    cannedResponses.add("Oh yeah!");
    cannedResponses.add("I'm sorry");
    cannedResponses.add("Mmhmm");
    cannedResponses.add("Okay");
  }

  /**
   * Starts and runs the conversation with the user and records the transcript
   */
  public void chat() {
    
    Scanner scan = new Scanner(System.in);
    String input = "";
    String response = "";
    
    System.out.println("Hello! Please input how many rounds as an int:");
    input = scan.nextLine();
    
    System.out.println("\n\n\nHi there! What's on your mind?");
    this.transcript.add("Hi there! What's on your mind?");
      
    int rounds = Integer.parseInt(input);
    
    for(int i=1; i<=rounds; i++){
    
      input = scan.nextLine();
      this.transcript.add(input);
      response = this.respond(input);
      System.out.println(response);
      this.transcript.add(response);
      
    }

    System.out.println("See you later!");
    this.transcript.add("See you later!");

    scan.close();

  }

  /**
   * Prints transcript of conversation
   */
  public void printTranscript() {
    System.out.println("\n\n\nTRANSCRIPT");

    for(String line : this.transcript){
      System.out.println(line);
    }
  }

  /**
   * Gives appropriate response (mirrored or canned) to user input
   * @param inputString the users last line of input
   * @return mirrored or canned response to user input  
   */
  public String respond(String inputString) {
    
    // Setting up the strings
    String passingString = " " + inputString.toLowerCase() + " "; // Spaces are put there so each word gets changed
    String qualityControlString = " " + inputString.toLowerCase() + " ";

    if(passingString.contains(" i ")){
      passingString = passingString.replaceAll(" i ", " I ");
      qualityControlString = qualityControlString.replaceAll(" i ", " I ");
      passingString = passingString.replaceAll(" i ", " I ");
      qualityControlString = qualityControlString.replaceAll(" i ", " I ");
      // done twice because there was an issue if the same word was repeated over and over
    }

    int count = 0;

    // Mirroring the strings if possible
    for(String str: mirrorDict.keySet()){

      //Mirrors String, everything lowercase
      if(qualityControlString.contains(str)){
        String change = mirrorDict.get(str);
        passingString = passingString.replaceAll(str, change);
        passingString = passingString.replaceAll(str, change); // done twice because there was an issue if the same word was repeated over and over
        qualityControlString = qualityControlString.replace(str,"   ");
      }
      else{
        count++;
      }
    }

    String returnString = passingString.substring(1).replaceAll("_", ""); // Gets rid of empty space

    Random randy = new Random();
    //Canned responses
    if(count == mirrorDict.keySet().size()){
      returnString = cannedResponses.get(randy.nextInt(0, cannedResponses.size()));
    }

    return returnString; 
  }

  public static void main(String[] arguments) {

    Conversation myConversation = new Conversation();
    myConversation.chat();
    myConversation.printTranscript();

  }
}
