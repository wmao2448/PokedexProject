// --== CS400 File Header Information ==--
// Name: William Mao
// Email: wmao24@wisc.edu
// Notes to Grader: <optional extra notes>

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class models a Hash table with an array and a linked list
 */
public class HashTableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

  private LinkedList<Pair>[] hashTable; // Array storing LinkedLists
  private int size; // Tracks size
  protected LinkedList<KeyType> allKeys; // Tracks all Key values

  /**
   * This private helper class stores the key-value pairs
   */
  protected class Pair {
    private KeyType key;
    private ValueType value;

    /**
     * Constructs a key-value pair
     * 
     * @param key   the key of the pair
     * @param value the information of the pair
     */
    public Pair(KeyType key, ValueType value) {
      this.key = key;
      this.value = value;
    }

    /**
     * Gets key of this pair
     * 
     * @return key of this pair
     */
    public KeyType getKey() {
      return this.key;
    }

    /**
     * Gets value of this pair
     * 
     * @return value of this pair
     */
    public ValueType getValue() {
      return this.value;
    }
  }

  /**
   * Constructs a hash table with given capacity
   * 
   * @param capacity the given initial capacity of the hash table array
   */
  @SuppressWarnings("unchecked")
  public HashTableMap(int capacity) {
    this.hashTable = new LinkedList[capacity];
    this.size = 0;
    allKeys = new LinkedList<>();
  }

  /**
   * Constructs a hash table with initial capacity of 10
   */
  @SuppressWarnings("unchecked")
  public HashTableMap() {
    this.hashTable = new LinkedList[10];
    this.size = 0;
    allKeys = new LinkedList<>();
  }

  /**
   * Private helper method that resizes the hash table array by doubling and rehashing all its
   * elements
   */
  private void resize() {
    @SuppressWarnings("unchecked")
    // Creates a new hashTable (Array) with capacity twice the original
    LinkedList<Pair>[] newTable = new LinkedList[2 * this.hashTable.length];
    // Loops through each index of the array
    for (int i = 0; i < this.hashTable.length; i++) {
      // Skips if the index has no LinkedList
      if (this.hashTable[i] == null) {
        continue;
      }
      // Accesses the index and reassigns every element to
      for (int j = 0; j < this.hashTable[i].size(); j++) {
        // Stores the Pair at that particular LinkedList in a temporary variable
        Pair tempPair = this.hashTable[i].get(j);

        // Calculates the new index in the new hashTable and places it there, creates new LinkedList
        // if index is empty
        int newIndex = Math.abs(tempPair.getKey().hashCode()) % newTable.length;
        if (newTable[newIndex] == null) {
          newTable[newIndex] = new LinkedList<Pair>();
        }
        newTable[newIndex].add(tempPair);
      }
    }
    // Reassigns the hashTable to new hash table
    this.hashTable = newTable;
  }

  /**
   * Adds an element to the hash table
   * 
   * @param key   the key of the entry
   * @param value the value of the entry
   * @return true if the new key-value pair was successfully added, false otherwise
   */
  public boolean put(KeyType key, ValueType value) {
    // Checks if the key given is null or in the table
    if (key == null || this.containsKey(key)) {
      return false;
    }
    // Calculates the index in the hashTable and places it there, also creates new LinkedList
    // if index is initially empty
    int index = Math.abs(key.hashCode()) % this.hashTable.length;
    if (this.hashTable[index] == null) {
      this.hashTable[index] = new LinkedList<Pair>();
    }
    this.hashTable[index].add(new Pair(key, value));

    // Calls private helper method if the load capacity has exceeded the set threshold
    this.size++;
    if (((double) this.size / (double) this.hashTable.length) >= 0.85) {
      this.resize();
    }

    // New bit
    allKeys.add(key);
    return true;
  }

  /**
   * Retrieves the value given its key
   * 
   * @param key the key of the entry
   * @return the value of the key-value pair
   * @throws NoSuchElementException if the key-value pair does not exist
   */
  public ValueType get(KeyType key) throws NoSuchElementException {
    // Calculates appropriate index given the key
    int index = Math.abs(key.hashCode()) % this.hashTable.length;

    // If key's appropriate index points to an empty index, then the key-pair does not exist and a
    // NoSuchElementException is thrown
    if (this.hashTable[index] == null) {
      throw new NoSuchElementException();
    }
    ValueType found = null;
    // Goes into the index, and loops through linked list until key is matched or end of the linked
    // list is reached
    for (int i = 0; i < this.hashTable[index].size(); i++) {
      if (this.hashTable[index].get(i).getKey().equals(key)) {
        found = this.hashTable[index].get(i).getValue();
        break;
      }
    }

    // If exact key was not found then found remains null and a NoSuchElementException is thrown
    if (found == null) {
      throw new NoSuchElementException();
    }
    return found;
  }

  /**
   * Gives the number of entries in the hash table
   * 
   * @return the number of entries in the hash table
   */
  public int size() {
    return this.size;
  }

  /**
   * Checks if the hash table has an entry given a key
   * 
   * @return true if there is an entry with that key, false otherwise
   */
  public boolean containsKey(KeyType key) {
    // Calculates appropriate index given the key
    int index = Math.abs(key.hashCode()) % this.hashTable.length;
    // If element at that index is non-empty, traverse the Linked List and return true if an entry
    // with that key exists
    if (this.hashTable[index] != null) {
      for (int i = 0; i < this.hashTable[index].size(); i++) {
        if (this.hashTable[index].get(i).getKey().equals(key)) {
          return true;
        }
      }
    }
    // If element at that index is empty or no element with such exact key exists, returns false
    return false;
  }

  /**
   * Removes an entry given its key
   * 
   * @return the value of the removed key
   */
  public ValueType remove(KeyType key) {
    // Calculating appropriate index of the key
    int index = Math.abs(key.hashCode()) % this.hashTable.length;
    ValueType popped = null;
    // Traverses the Linked List at the index to search for the pair with key of interest
    for (int i = 0; i < this.hashTable[index].size(); i++) {
      // If found, assign pair to popped and remove the entry and break
      if (this.hashTable[index].get(i).getKey().equals(key)) {
        popped = this.hashTable[index].get(i).getValue();
        this.hashTable[index].remove(i);
        break;
      }
    }

    // If popped returned null, then return null without decrementing size
    if (popped == null) {
      return null;
    }
    size--;


    // Remove Key from allKeys
    for (int i = 0; i < allKeys.size(); i++) {
      if (allKeys.get(i).equals(key)) {
        allKeys.remove(i);
        break;
      }
    }

    return popped;
  }

  /**
   * Clears the hash table
   */
  public void clear() {
    @SuppressWarnings("unchecked")
    // Creates a new empty hash table of current length and reassigns hashTable to it, resets size
    // field as well
    LinkedList<Pair>[] newTable = new LinkedList[this.hashTable.length];
    this.size = 0;
    this.hashTable = newTable;
  }
}
