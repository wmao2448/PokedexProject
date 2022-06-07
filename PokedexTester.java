// --== CS400 File Header Information ==--
// Name: William Mao
// Email: wmao24@wisc.edu
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

// This class tests the functions of the application with JUnit
public class PokedexTester {

  protected Pokedex _dex = null; // For testing just the Pokedex
  protected PokedexDriver _driverDex = null; // For testing the dex with dataset loaded in

  /**
   * Creates a new _dex and _driverDex before each test
   */
  @BeforeEach
  public void createInstance() {
    _dex = new Pokedex();
    PokedexDriver.initiate();
  }

  /**
   * Tests the search functionality, inserts 2 Pokemon into _dex and tests if information is printed
   * correctly
   */
  @Test
  public void testSearchPokemon() {
    _dex.addPokemon("Grookey", 50, 65, 50, 40, 40, 65, 310, "Grass", "None");
    _dex.addPokemon("Bewear", 120, 125, 80, 55, 60, 60, 500, "Normal", "Fighting");

    String found = _dex.search("Grookey");
    String expected = "\nGrookey\nHP: 50\nAttack: 65\nDefense: 50\n"
        + "Sp. Attack: 40\nSp. Defense: 40\nSpeed: 65\nSum: 310\n"
        + "Type One: Grass\nType Two: None\n";
    assertEquals(expected, found);

    found = _dex.search("Bewear");
    expected = "\nBewear\nHP: 120\nAttack: 125\nDefense: 80\n"
        + "Sp. Attack: 55\nSp. Defense: 60\nSpeed: 60\nSum: 500\n"
        + "Type One: Normal\nType Two: Fighting\n";
    assertEquals(expected, found);
  }

  /**
   * Tests the Add/Remove functionality
   */
  @Test
  public void testAddRemove() {
    // Add 3 Pokemon
    _dex.addPokemon("Grookey", 50, 65, 50, 40, 40, 65, 310, "Grass", "None");
    _dex.addPokemon("Bewear", 120, 125, 80, 55, 60, 60, 500, "normal", "Fighting");
    _dex.addPokemon("Ditto", 48, 48, 48, 48, 48, 48, 288, "Normal", "None");

    // Tests if they were added succesffully
    assertEquals(true, _dex.pokedex.containsKey("Grookey"));
    assertEquals(true, _dex.pokedex.containsKey("Ditto"));
    assertEquals(true, _dex.pokedex.containsKey("Bewear"));

    // Removes 2 Pokemong
    _dex.removePokemon("Grookey");
    _dex.removePokemon("Ditto");
    // Tests if the 2 removed are no longer there and if the unremoved one is still there
    assertEquals(false, _dex.pokedex.containsKey("Grookey"));
    assertEquals(false, _dex.pokedex.containsKey("Ditto"));
    assertEquals(true, _dex.pokedex.containsKey("Bewear"));
  }

  /**
   * Tests the top function by passing in arguments into the dataset dex and see if it returns the
   * expected String information
   */
  @Test
  public void testTop() {
    // Passing in sum
    String result = PokedexDriver.dex.findTop("sum");
    String expected = "\nZacian\nHP: 92\nAttack: 170\nDefense: 115\n"
        + "Sp. Attack: 80\nSp. Defense: 115\nSpeed: 148\nSum: 720\n"
        + "Type One: Fairy\nType Two: Steel\n\nZamazenta\nHP: 92\nAttack: 130\n"
        + "Defense: 145\nSp. Attack: 80\nSp. Defense: 145\nSpeed: 128\n"
        + "Sum: 720\nType One: Fighting\nType Two: Steel\n\nEternatus\nHP: 140\n"
        + "Attack: 85\nDefense: 95\nSp. Attack: 145\nSp. Defense: 95\n"
        + "Speed: 130\nSum: 690\nType One: Poison\nType Two: Dragon\n";
    assertEquals(expected, result);

    // Passing in speed
    result = PokedexDriver.dex.findTop("speed");
    expected = "\nNinjask\nHP: 61\nAttack: 90\nDefense: 45\nSp. Attack: 50\n"
        + "Sp. Defense: 50\nSpeed: 160\nSum: 456\nType One: Bug\n"
        + "Type Two: Flying\n\nZacian\nHP: 92\nAttack: 170\nDefense: 115\n"
        + "Sp. Attack: 80\nSp. Defense: 115\nSpeed: 148\nSum: 720\n"
        + "Type One: Fairy\nType Two: Steel\n\nAccelgor\nHP: 80\n"
        + "Attack: 70\nDefense: 40\nSp. Attack: 100\nSp. Defense: 60\n"
        + "Speed: 145\nSum: 495\nType One: Bug\nType Two: None\n";
    assertEquals(expected, result);
  }

  /**
   * Tests the range function by passing in arguments into the dataset dex and see if it returns the
   * expected String information
   */
  @Test
  public void testRange() {

    // Passing in 10 to 15 for defense
    String result = PokedexDriver.dex.range("defense", 10, 15);
    String expected = "\nPichu\nHP: 20\nAttack: 40\nDefense: 15\n"
        + "Sp. Attack: 35\nSp. Defense: 35\nSpeed: 60\nSum: 205\n"
        + "Type One: Electric\nType Two: None\n";
    assertEquals(expected, result);

    // Passing in 140 to 145 for spatk
    result = PokedexDriver.dex.range("spatk", 140, 145);
    expected = "\nVikavolt\nHP: 77\nAttack: 70\nDefense: 90\n"
        + "Sp. Attack: 145\nSp. Defense: 75\nSpeed: 43\nSum: 500\n"
        + "Type One: Bug\nType Two: Electric\n\nGalarian Cursola\n"
        + "HP: 60\nAttack: 95\nDefense: 50\nSp. Attack: 145\n"
        + "Sp. Defense: 130\nSpeed: 30\nSum: 510\nType One: Ghost\n"
        + "Type Two: None\n\nChandelure\nHP: 60\nAttack: 55\n"
        + "Defense: 90\nSp. Attack: 145\nSp. Defense: 90\nSpeed: 80\n"
        + "Sum: 520\nType One: Ghost\nType Two: Fire\n\nEternatus\n"
        + "HP: 140\nAttack: 85\nDefense: 95\nSp. Attack: 145\n"
        + "Sp. Defense: 95\nSpeed: 130\nSum: 690\nType One: Poison\nType Two: Dragon\n";
    assertEquals(expected, result);
  }

  /**
   * Tests the filter function by passing in arguments into the dex and see if it returns the
   * expected String information
   */
  @Test
  public void testFilter() {
    // Filters for just Grass and Dragon Pokemon
    String result = PokedexDriver.dex.filter("Grass", "Dragon");
    String expected = "\nApplin\nHP: 40\nAttack: 40\nDefense: 80\n"
        + "Sp. Attack: 40\nSp. Defense: 40\nSpeed: 20\nSum: 260\n"
        + "Type One: Grass\nType Two: Dragon\n\nFlapple\nHP: 70\n"
        + "Attack: 110\nDefense: 80\nSp. Attack: 95\nSp. Defense: 60\n"
        + "Speed: 70\nSum: 485\nType One: Grass\nType Two: Dragon\n\n"
        + "Appletun\nHP: 110\nAttack: 85\nDefense: 80\n"
        + "Sp. Attack: 100\nSp. Defense: 80\nSpeed: 30\nSum: 485\n"
        + "Type One: Grass\nType Two: Dragon\n";
    assertEquals(expected, result);

    // Filters for just Steel and Fighting Pokemon
    result = PokedexDriver.dex.filter("Steel", "Fighting");
    expected = "\nLucario\nHP: 70\nAttack: 110\nDefense: 70\n"
        + "Sp. Attack: 115\nSp. Defense: 70\nSpeed: 90\nSum: 525\n"
        + "Type One: Fighting\nType Two: Steel\n\nZamazenta\nHP: 92\n"
        + "Attack: 130\nDefense: 145\nSp. Attack: 80\nSp. Defense: 145\n"
        + "Speed: 128\nSum: 720\nType One: Fighting\nType Two: Steel\n";
    assertEquals(expected, result);
  }
}
