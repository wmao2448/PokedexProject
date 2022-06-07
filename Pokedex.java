// --== CS400 File Header Information ==--
// Name: William Mao
// Email: wmao24@wisc.edu
// Notes to Grader: <optional extra notes>

import java.util.ArrayList;

// This class models a pokedex that stores Pokemon information and is intended to take care of back end functions
public class Pokedex {
  protected HashTableMap<String, Pokemon> pokedex;

  /**
   * Constructs a new empty Pokedex
   */
  public Pokedex() {
    pokedex = new HashTableMap<String, Pokemon>();
  }

  /**
   * Fixes Strings so that they all just have the first letter capitalized
   * 
   * @param unfixed the uncorrected String
   * @return a corrected String with just the first letter capitalized
   */
  private String fixString(String unFixed) {
    String[] arr = unFixed.split(" ");
    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < arr.length; i++) {
      sb.append(Character.toUpperCase(arr[i].charAt(0))).append(arr[i].substring(1)).append(" ");
    }
    return sb.toString().trim();
  }

  /**
   * Adds Pokemon to the pokedex hash table
   * 
   * @param hp    health of Pokemon
   * @param atk   physical move damage of this Pokemon
   * @param def   resistance of this Pokemon when hit by a physical move
   * @param spAtk damage caused by this Pokemon when using a special move
   * @param spDef resistence of this Pokemon to special moves
   * @param speed swiftness of this Pokemon
   * @param sum   overall sum of previous values
   * @param type1 first type of the Pokemon
   * @param type2 second type, if a Pokemon doesn't have a second type, it will be displayed as
   *              "None"
   */
  public void addPokemon(String pokeName, int hp, int atk, int def, int spAtk, int spDef, int speed,
      int sum, String type1, String type2) {

    String name = fixString(pokeName);

    if (name.split(" ").length > 3) {
      throw new IllegalArgumentException();
    }

    Pokemon pokeInfo = new Pokemon(hp, atk, def, spAtk, spDef, speed, sum, type1, type2);
    pokedex.put(name, pokeInfo);
  }

  /**
   * Removes Pokemon from the pokedex hash table
   * 
   * @param pokeName Name of Pokemon to be removed
   */
  public void removePokemon(String pokeName) {
    String name = fixString(pokeName);
    pokedex.remove(name);
  }

  /**
   * Retrieves and returns a String with all the Pokemon that are within range for a given field
   * 
   * @param stat  user given field of the Pokemon
   * @param lower lower bound
   * @param upper upper bound
   * @return a String with all the Pokemon and their info that satisfy the criteria
   */
  public String range(String stat, int lower, int upper) {

    // ArrayList to track the keys of Pokemon that satisfy the condition
    ArrayList<String> satisfied = new ArrayList<String>();

    // Looks through each Pokemon and compares their value with the given range, adds its key to
    // satisfied if its value is within range
    for (int i = 0; i < pokedex.allKeys.size(); i++) {

      String pokeName = pokedex.allKeys.get(i);
      int compareStat = 0;

      try {
        compareStat = pokedex.get(pokeName).getStat(stat);
      } catch (Exception e) {
        return "Invalid stat field";
      }
      // If in range, then add key to ArrayList
      if (compareStat <= upper && compareStat >= lower) {
        satisfied.add(pokeName);
      }
    }

    // Creates the String with the satisfied Pokemon names and their information
    String results = "";
    for (int i = 0; i < satisfied.size(); i++) {
      results += "\n" + satisfied.get(i) + "\n" + pokedex.get(satisfied.get(i)).toString();
    }
    return results;
  }

  /**
   * Finds the top 3 Pokemon of the given field
   * 
   * @param stat user given field
   * @return a String with the resulting top 3 Pokemon and their information
   */
  public String findTop(String stat) {
    // An array to keep track of the top 3 Pokemon names (the Pokemon's key)
    String[] topPokemon = new String[3];

    // Initialize 3 values with minimum values to track current top 3 Pokemon's values as the
    // program looks through
    // the hash table
    int first = Integer.MIN_VALUE;
    int second = Integer.MIN_VALUE;
    int third = Integer.MIN_VALUE;
    // Looks through the hash table and tracks the top 3 Pokemon with the largest values in the
    // given field
    for (int i = 0; i < pokedex.allKeys.size(); i++) {
      String pokeName = pokedex.allKeys.get(i);
      int compareStat = pokedex.get(pokeName).getStat(stat);

      // Changes ordering by pushing existing top three down a slot if the current pokemon has a
      // value larger than the current largest value
      if (compareStat > first) {

        // Push Pokemon's field values down a slot
        third = second;
        second = first;
        first = compareStat;

        // Push Pokemon's names down a slot
        topPokemon[2] = topPokemon[1];
        topPokemon[1] = topPokemon[0];
        topPokemon[0] = pokeName;


      } else if (compareStat > second) {
        // If larger than the second, then do appropriate switching on the 2nd and 3rd largest
        third = second;
        second = compareStat;

        topPokemon[2] = topPokemon[1];
        topPokemon[1] = pokeName;
      } else if (compareStat > third) {
        // If only larger than the third, replace it with the new Pokemon
        third = compareStat;
        topPokemon[2] = pokeName;
      }


    }


    // Creates String with all the Pokemon names and their information
    String top = "";

    for (int i = 0; i < topPokemon.length; i++) {
      top += "\n" + topPokemon[i] + "\n" + pokedex.get(topPokemon[i]).toString();
    }
    // Rest of stuff
    return top;
  }

  /**
   * Filters out the Pokemon with the only given types - Considers both types
   * 
   * @param type1 user entered first type
   * @param type2 user entered second type
   * @return a String with all the Pokemon that have both types and their information
   */
  public String filter(String type1, String type2) {

    // Edits the types to account for slight differences in input
    String typeOne = fixString(type1);
    String typeTwo = fixString(type2);

    ArrayList<String> filteredByType = new ArrayList<>();

    // Goes through the hash table and adds pokemon with that given type to the ArrayList
    for (int i = 0; i < pokedex.allKeys.size(); i++) {
      String pokeName = pokedex.allKeys.get(i);
      Pokemon inspect = pokedex.get(pokeName);
      // Inspects both types (either order works)
      if (inspect.type1.equalsIgnoreCase(typeOne) && inspect.type2.equalsIgnoreCase(typeTwo)
          || inspect.type2.equalsIgnoreCase(typeOne) && inspect.type1.equalsIgnoreCase(typeTwo)) {
        filteredByType.add(pokeName);
      }

    }

    String filterInfo = "";

    // If no results found, then returns No Results
    if (filteredByType.isEmpty()) {
      filterInfo = "No Results";
      return filterInfo;
    }

    // Creates a String with each Pokemon's name and its information
    for (int i = 0; i < filteredByType.size(); i++) {
      filterInfo +=
          "\n" + filteredByType.get(i) + "\n" + pokedex.get(filteredByType.get(i)).toString();
    }

    return filterInfo;
  }

  /**
   * Filters out the Pokemon with the only given types - Considers only one type
   * 
   * @param type1 user entered type
   * @return a String with all the Pokemon that have that type and their information
   */
  public String filter(String type1) {


    String typeOne = fixString(type1);

    ArrayList<String> filteredByType = new ArrayList<>();

    // Compares if the type is among the Pokemon's types, if so, adds to ArrayList
    for (int i = 0; i < pokedex.allKeys.size(); i++) {
      String pokeName = pokedex.allKeys.get(i);
      Pokemon inspect = pokedex.get(pokeName);
      if (inspect.type1.equals(typeOne) || inspect.type2.equals(typeOne)) {
        filteredByType.add(pokeName);
      }

    }
    // If no results found, then returns No Results
    String filterInfo = "";
    if (filteredByType.isEmpty()) {
      filterInfo = "No Results";
      return filterInfo;
    }


    // Creates a String with each Pokemon's name and its information
    for (int i = 0; i < filteredByType.size(); i++) {
      filterInfo +=
          "\n" + filteredByType.get(i) + "\n" + pokedex.get(filteredByType.get(i)).toString();
    }

    return filterInfo;
  }

  /**
   * Searches for a Pokemon given its name (key in the hash table)
   * 
   * @param pokeName the Pokemon's name
   * @return the Pokemon's name and information
   */
  public String search(String pokeName) {

    // Modifies the given string to match with standards
    String name = fixString(pokeName);
    Pokemon found = null;

    // If key retrieval is unsuccessful, then the Pokemon is not in the hash table
    try {
      found = pokedex.get(name);

    } catch (Exception e) {
      return "Pokemon name not found";
    }

    // Returns Pokemon with its name and information
    String result = "\n" + name + "\n" + found;
    return result;
  }


}
