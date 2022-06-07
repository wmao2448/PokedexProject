// --== CS400 File Header Information ==--
// Name: William Mao
// Email: wmao24@wisc.edu
// Notes to Grader: <optional extra notes>

// This class models a Pokedex/Pokebank Entry where each Key (Pokemon name) could retrive this
// information
public class Pokemon {

  // Baseline properties of this Pokemon
  final public int hp;
  final public int atk;
  final public int def;
  final public int spAtk;
  final public int spDef;
  final public int speed;
  final public int sum;
  final public String type1;
  final public String type2;

  /**
   * Constructs a Pokemon object with the given statistics
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
  public Pokemon(int hp, int atk, int def, int spAtk, int spDef, int speed, int sum, String type1,
      String type2) {
    this.hp = hp;
    this.atk = atk;
    this.def = def;
    this.spAtk = spAtk;
    this.spDef = spDef;
    this.speed = speed;
    this.sum = sum;
    this.type1 = type1;
    this.type2 = type2;
  }

  /**
   * Returns the appropriate statistic of this Pokemon
   * 
   * @param stat the statistic's name
   * @returns the integer value of the appropriate statistic
   */
  public int getStat(String stat) {
    // Returns based on the name of stat
    // Throws an error if stat doesn't match any of the possible cases
    stat = stat.toLowerCase();
    switch (stat) {
      case "hp":
        return this.hp;
      case "attack":
        return this.atk;
      case "defense":
        return this.def;
      case "spatk":
        return this.spAtk;
      case "spdef":
        return this.spDef;
      case "speed":
        return this.speed;
      case "sum":
        return this.sum;
      default:
        throw new IllegalArgumentException("Passed in stat is invalid");
    }
  }

  /**
   * Overrides toString method, returns a String representation of this Pokemon
   * 
   * @return String with all the statistics of this Pokemon
   */
  @Override
  public String toString() {
    return "HP: " + hp + "\n" + "Attack: " + atk + "\n" + "Defense: " + def + "\n" + "Sp. Attack: "
        + spAtk + "\n" + "Sp. Defense: " + spDef + "\n" + "Speed: " + speed + "\n" + "Sum: " + sum
        + "\n" + "Type One: " + type1 + "\n" + "Type Two: " + type2 + "\n";
  }
}
