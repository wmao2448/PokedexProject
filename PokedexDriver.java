// --== CS400 File Header Information ==--
// Name: William Mao
// Email: wmao24@wisc.edu
// Notes to Grader: dataset is from https://www.kaggle.com/ookawayoshimi/pokemon-data-swordshield
// Japanese name column was removed
//This Project uses the Hash table data structure

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

// This class dictates the behavior of a simple text interface and is the main driver of the application
//As such, this is intended to take care of front-end user inputs
public class PokedexDriver {

  private static final String PROMPT_MESSAGE = // Displays when asking for user input
      "Enter an expression to explore or enter H to see the help menu";
  private static final String ERROR_MESSAGE = "Not a valid expression! Enter H to see all commands";
  // General error message
  public static Pokedex dex; // Name of the region that the dataset's pokemon can all be found

  /**
   * Communicates with Pokedex by accessing appropriate methods
   * 
   * @param input the input that the user enters
   */
  private static void processCommand(String input) {
    // Places given input into an array to more easily access inputs
    String[] inputs = input.trim().split(" ");

    // Change input to lowercase in case it was entered as an uppercase letter
    switch (inputs[0].toLowerCase()) {
      // Each case cleans or edits the other inputs and prints an error message if the parameters
      // were not correctly given
      case "h": // Help Menu
        System.out.println("=== Help Menu ===\n" + "Command - Inputs \n"
            + "s - Search <Pokemon Name> - Enter a Pokemon name to search through the data\n"
            + "f - Filter <Type One> <Type Two> - Filters the data to only display Pokemon with entered types, can also enter just one type\n"
            + "t - Top <Pokemon Stat> - Finds the top 3 Pokemon with the entered stat\n"
            + "r - Range <Pokemon Stat> <Lower Bound> <Upper Bound> - Finds all Pokemon that have a stat value within range - Bounds are inclusive\n"
            + "a - Prints all Pokemon in the region\n" + "q - Quit application\n"
            + "Stats shorthand for entering: hp, atk, def, spatk, spdef, speed, sum\n"
            + "=== Advanced Commands ===\n"
            + "add <Pokemon name> <HP> <Attack> <Defense> <Sp. Atk> <Sp. Def> <Speed> <Sum> <typeOne> <typeTwo> - Adds a Pokemon to the pokedex\n"
            + "remove <Pokemon name> - Removes a Pokemon from the pokedex\n");
        break;
      case "s": // Search
        // Checks if pokemon name has more than one part
        // Handles names up to three words long, longest is Galarian Mr. Mime from the dataset
        if (inputs.length == 4) {
          // Makes sure all parts are in the correct format before passing them in
          String nameOne = inputs[1].substring(0, 1).toUpperCase() + inputs[1].substring(1);
          String nameTwo = inputs[2].substring(0, 1).toUpperCase() + inputs[2].substring(1);
          String nameThree = inputs[3].substring(0, 1).toUpperCase() + inputs[3].substring(1);
          String fullName = nameOne + " " + nameTwo + " " + nameThree;

          System.out.println(dex.search(fullName));
          break;
        }
        if (inputs.length == 3) {
          // Makes sure both parts are in the correct format before passing them in
          String nameOne = inputs[1].substring(0, 1).toUpperCase() + inputs[1].substring(1);
          String nameTwo = inputs[2].substring(0, 1).toUpperCase() + inputs[2].substring(1);
          String fullName = nameOne + " " + nameTwo;

          System.out.println(dex.search(fullName));
          break;
        }
        if (inputs.length == 2) {
          System.out.println(dex.search(inputs[1]));
        } else {
          System.out.println(ERROR_MESSAGE);
        }
        break;
      case "f": // Filter by type(s)
        // Determines if one or two types were entered and calls the appropriate method
        if (inputs.length == 2) {
          System.out.println(dex.filter(inputs[1]));
        } else if (inputs.length == 3) {
          System.out.println(dex.filter(inputs[1], inputs[2]));
        } else {
          System.out.println(ERROR_MESSAGE);
        }

        break;
      case "t": // Finds the top 3 pokemon given a stat
        if (inputs.length == 2) {
          System.out.println(dex.findTop(inputs[1].toLowerCase()));
        } else if (inputs.length == 1) {
          // If just t was entered, then it finds the top 3 based on the sum statistic
          try {
            System.out.println(dex.findTop("sum"));
          } catch (Exception e) {
            System.out.println("Invalid stat passed in");
            break;
          }
        } else {
          System.out.println(ERROR_MESSAGE);
        }
        break;
      case "r":
        int lower = 0;
        int upper = 0;

        // Checks if parsed in inputs are integers, breaks otherwise
        try {
          lower = Integer.parseInt(inputs[2]);
          upper = Integer.parseInt(inputs[3]);
        } catch (Exception e) {
          System.out.println("Invalid inputs");
          break;
        }

        if (inputs.length == 4) {
          // If lower bound is greater than upper bound, then break
          if (lower <= upper) {
            System.out.println(dex.range(inputs[1], lower, upper));
          } else {
            System.out.println("Lower bound is not less than upper bound");
          }
        } else {
          System.out.println(ERROR_MESSAGE);
          break;
        }
        break;
      case "add": // Adds a Pokemon entry to the Pokedex , can pass multi word names in
        if (inputs.length == 13) {
          // Handles names up to 3 words long
          // Parse Strings into appropriate types
          try {
            String pokeName = inputs[1] + " " + inputs[2] + " " + inputs[3];
            int hp = Integer.parseInt(inputs[4]);
            int atk = Integer.parseInt(inputs[5]);
            int def = Integer.parseInt(inputs[6]);
            int spAtk = Integer.parseInt(inputs[7]);
            int spDef = Integer.parseInt(inputs[8]);
            int speed = Integer.parseInt(inputs[9]);
            int sum = Integer.parseInt(inputs[10]);
            String type1 = inputs[11];
            String type2 = inputs[12];
            // Add to dex
            dex.addPokemon(pokeName, hp, atk, def, spAtk, spDef, speed, sum, type1, type2);
            System.out.println("Pokemon successfully added!");
            break;
          } catch (Exception e) {
            System.out.println("Pokemon was not successfully added!");
            break;
          }
        }
        // Handles names up to 3 words long
        if (inputs.length == 12) {
          // Parse Strings into appropriate types
          try {
            String pokeName = inputs[1] + " " + inputs[2];
            int hp = Integer.parseInt(inputs[3]);
            int atk = Integer.parseInt(inputs[4]);
            int def = Integer.parseInt(inputs[5]);
            int spAtk = Integer.parseInt(inputs[6]);
            int spDef = Integer.parseInt(inputs[7]);
            int speed = Integer.parseInt(inputs[8]);
            int sum = Integer.parseInt(inputs[9]);
            String type1 = inputs[10];
            String type2 = inputs[11];
            // Add to dex
            dex.addPokemon(pokeName, hp, atk, def, spAtk, spDef, speed, sum, type1, type2);
            System.out.println("Pokemon successfully added!");
            break;
          } catch (Exception e) {
            System.out.println("Pokemon was not successfully added!");
            break;
          }
        }
        if (inputs.length == 11) {
          // Parse Strings into appropriate types
          try {
            String pokeName = inputs[1];
            int hp = Integer.parseInt(inputs[2]);
            int atk = Integer.parseInt(inputs[3]);
            int def = Integer.parseInt(inputs[4]);
            int spAtk = Integer.parseInt(inputs[5]);
            int spDef = Integer.parseInt(inputs[6]);
            int speed = Integer.parseInt(inputs[7]);
            int sum = Integer.parseInt(inputs[8]);
            String type1 = inputs[9];
            String type2 = inputs[10];
            // Add to dex
            dex.addPokemon(pokeName, hp, atk, def, spAtk, spDef, speed, sum, type1, type2);
            System.out.println("Pokemon successfully added!");
            break;
          } catch (Exception e) {
            System.out.println("Pokemon was not successfully added!");
            break;
          }
        }
      case "remove": // Removes a Pokemon entry from the Pokedex, can pass multi word names in
        // Handles names up to 3 words long
        try {
          if (inputs.length == 4) {
            // Makes sure all parts are in the correct format before passing them in
            String nameOne = inputs[1].substring(0, 1).toUpperCase() + inputs[1].substring(1);
            String nameTwo = inputs[2].substring(0, 1).toUpperCase() + inputs[2].substring(1);
            String nameThree = inputs[3].substring(0, 1).toUpperCase() + inputs[3].substring(1);
            String fullName = nameOne + " " + nameTwo + " " + nameThree;

            dex.removePokemon(fullName);
            System.out.println("Pokemon removed");
            break;
          }
          if (inputs.length == 3) {
            // Makes sure both parts are in the correct format before passing them in
            String nameOne = inputs[1].substring(0, 1).toUpperCase() + inputs[1].substring(1);
            String nameTwo = inputs[2].substring(0, 1).toUpperCase() + inputs[2].substring(1);
            String fullName = nameOne + " " + nameTwo;

            dex.removePokemon(fullName);
            System.out.println("Pokemon removed");
            break;
          }
          if (inputs.length == 2) {
            dex.removePokemon(inputs[1]);
            System.out.println("Pokemon removed");
            break;
          } else {
            System.out.println(ERROR_MESSAGE);
            break;
          }
        } catch (Exception e) {
          System.out.println("Pokemon was not successfully removed!");
          break;
        }
      case "a": // Prints all in the pokedex
        for (int i = 0; i < dex.pokedex.allKeys.size(); i++) {
          System.out.println(
              dex.pokedex.allKeys.get(i) + "\n" + dex.pokedex.get(dex.pokedex.allKeys.get(i)));
        }
        break;
      default: // Defaults to an error message
        System.out.println(ERROR_MESSAGE);
    }
  }

  /**
   * Text based interface that handles user inputs
   */
  private static void userInterface() {
    // Prints Welcome message, the prompt message, and initiates a Scanner class to start accepting
    // inputs
    System.out.println("=== Welcome to the Pokemon Galar Region Pokedex! ===\n\n"
        + "This application allows the user to explore and learn about all the different Pokemon in the Sword and Shield games.\n");
    System.out.println(PROMPT_MESSAGE);
    String input = "";
    Scanner scan = new Scanner(System.in);
    input = scan.nextLine();

    // If the user enters quit, then stop acceepting inputs and close the scanner and prints a
    // farewell message
    while (!input.equalsIgnoreCase("q")) {

      processCommand(input);
      System.out.println(PROMPT_MESSAGE);
      input = scan.nextLine().trim();
    }
    scan.close();
    System.out.println("=== Thanks for using this Pokedex! See you later! ===");
  }


  /**
   * Adds all the contents of the pokemon dataset into the pokedex as distinct entries
   */
  public static void initiate() {
    dex = new Pokedex();
    String fileName = "pokemon-swsh.csv";
    File file = new File(fileName);
    Scanner input = null;
    try {
      input = new Scanner(file);
      String data = input.nextLine(); // Skips column headers
      while (input.hasNext()) { // While there are more rows, continue
        data = input.nextLine();
        String[] pokemon = data.split(","); // Splits the data by column into an array

        // Separates each piece of the statistic into a variable
        String pokeName = pokemon[0].strip();
        int hp = Integer.parseInt(pokemon[1]);
        int atk = Integer.parseInt(pokemon[2]);
        int def = Integer.parseInt(pokemon[3]);
        int spAtk = Integer.parseInt(pokemon[4]);
        int spDef = Integer.parseInt(pokemon[5]);
        int speed = Integer.parseInt(pokemon[6]);
        int sum = Integer.parseInt(pokemon[7]);
        String type1 = pokemon[8];
        String type2 = pokemon[9];

        // Passes it into addPokemon and adds to the hashtable
        dex.addPokemon(pokeName, hp, atk, def, spAtk, spDef, speed, sum, type1, type2);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (input != null) // Close scanner
        input.close();
    }
  }

  /**
   * Main method that starts the application
   * 
   * @param args input arguments if any
   */
  public static void main(String[] args) {
    initiate(); // Adds contents to the pokedex hash table
    userInterface(); // Allows exploration of the data
  }
}
