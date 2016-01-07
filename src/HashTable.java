/*
 * HashTable.java
 * 
 * Version 7
 * 
 * Copyright Jordan Brown
 * 
 * Course: CSC 172 FALL 2015
 * 
 * Assignment: PROJECT 3
 * 
 * Last Revised: November 15, 2015
 */
public class HashTable {
	
	private String[] hashTable;
	private int tableSize;
	private int uniqueItems;
	private int itemsRead;
	private double loadFactor;
	private static final double FIFTY_PERCENT = .5;
	
	public HashTable( int tableSize ){
		
		hashTable = new String[ tableSize ];
		
		this.tableSize = tableSize;
		uniqueItems = 0;
		loadFactor = 0;
		itemsRead = 0;
	}
	
	public int getItemsRead(){
		return itemsRead;
	}

	public int getTableSize(){
		return tableSize;
	}
	
	public int getUniqueItems(){
		return uniqueItems;
	}
	
	public double getLoadFactor(){
		return loadFactor;
	}
	
	/*
	 * generates a hash function
	 */
	public int hash( String key ){
		
		int hashValue = 0;
		
		for( int i = 0; i < key.length(); i++ ){ //For each character in our key
			
			hashValue = 37 * hashValue + key.charAt( i );
			
			hashValue %= tableSize; 
			
			if( hashValue < 0 ){
				hashValue += tableSize;
			}
		}
		
	
		return hashValue;
	}
	
	/*
	 * finds an element in hash table
	 */
	public boolean find( String item ){
		
		int index = hash(item);
		
		while( hashTable[index] != null ){
			
			if( hashTable[index].equals(item) ){
				return true;
			}
			
			index++;
			
			index = index % tableSize;
			
		}
		
		return false;
	}
	

	/*
	 * determines whether an element is contained in a cell
	 */
	public boolean isOccupied( int index ){
		
		if( hashTable[index] == null ){
			return false;
		}
		
		return true;
	}
	
	/*
	 * computes the load factor
	 */
	public double computeLoadFactor(){
		
		loadFactor = ((double) uniqueItems ) / ( (double) tableSize );
		
		return loadFactor;
	}
	
	/*
	 * expands the hash table when load factor > 50 percent
	 */
	public void expandHashTable(){
		
		
		 //double the size of the original table
		
		
		String[] holdItems = new String[tableSize];
		
	
		/*
		 * Since the unique hash value keys depends on the size of the hash table, we cannot simply copy the items
		 * into a larger array, one by one. We must rehash (i.e. call the hash function for each item as we insert)
		 * into the hash table so when we try and search for an item, the hash function can locate them with 
		 * accuracy.
		 */
		
		int i = 0;
		
		for(String item: hashTable){
			
			if( item != null ){
				holdItems[i] = item;
				i++;
			}
			
		}
		
	
		
		tableSize = tableSize * 2; 
		
		hashTable = new String[ tableSize ];
		
		for(String current: holdItems){
			
			if(current != null)
				insert(current);
		}
		
		
	}
	
	/*
	 * inserts a string into the hash table
	 */
	public void insert( String item ){
		
		
		
		int index; //index for our item to be inserted
		
		if( find( item ) == false ){ //Verify item to be inserted is unique
			
			//retrieve the correct index for the item using the hash function
			index = hash( item );
			
			while ( isOccupied( index ) == true ){ //loop while the current cell is occupied
				
				//check the next index (linear probe) 
				index++;
				
				//wrap around when hitting the tableSize
				index = index % tableSize;
				
			}
			
			//insert the item
			hashTable[index] = item;
			
			//increment uniqueItems
			uniqueItems++;
			
			//recalculate load factor
			loadFactor = computeLoadFactor();
			
			//check if hash table needs to be expanded 
			
			if( loadFactor > FIFTY_PERCENT ){
				
				expandHashTable();
				
			}
			
		}
		
		
		//do nothing, item is not unique and already contained in hash table
		itemsRead++;
		
	}
	
	/*
	 * prints the non null elements of hash table
	 */
	public void print(){
		
		for(String current: hashTable){
			
			if( current != null ){
				System.out.println(current + " ");
			}
		}
	}
	
	
	
}//end of class HashTable
