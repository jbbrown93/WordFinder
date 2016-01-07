/*
 * WordFinder.java
 * 
 * Version 7
 * 
 * Copyright Jordan Brown
 * 
 * Course: CSC 172 FALL 2015
 * 
 * Project 3
 * 
 * Last Revised: November 21, 2015
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;



public class WordFinder {
	
	public static MyQueue<String> wordQueue = new MyQueue<String>();
	
	/*
	 * Generate all combinations of a word inside the puzzle, place into queue
	 */
	public static void Holder(String holder){
		
		String temp[];
		String tempR[];
		
		String s1 = "";
		String s2 = "";
		
		temp = holder.split("");
		tempR = holder.split("");
		
		String re = "";
		
		for(int l = tempR.length-1; l >= 0; l--){
			re = re + temp[l];
		}
			
		
		tempR = re.split("");
		
		holder = "";
		
		for(int i = 0; i < temp.length ; i++){
		
			
			for(int k = i; k < temp.length; k++){
				
				s1 = s1 + temp[k];
				s2 = s2 + tempR[k];
				
				wordQueue.enqueue(s1);
				wordQueue.enqueue(s2);
				
				
			}
			
			
			s1 = "";
			s2 = "";
			

			
		}
		
	}
	
	
	//start of main method
	public static void main(String[] args) {
		
		HashTable dictionary = new HashTable( 479829 * 2 );
	
        String wordList = args[0]; // The name of word list file
        
        String inputPuzzle = args[1];  // name of the file to take in the puzzle
        
        String outputFileName = args[2];  // name of the file to write the found words to

        String line = null;  // This will reference one paragraph at a time     
        
        String[] foundWordsArr; //final array with the found words in sorted order
        
        LinkedList<String> foundWords = new LinkedList<String>(); //linked list holding the words found in queue
        
        int count = 0; //total count of words from word list file
        
        int nSize = 0;  //the size of the puzzle 
        
        MyQueue<String> puzzleQueue = new MyQueue<String>(); //holds the characters of the puzzle
        
        String[][] puzzle; //2D array representing the puzzle
        
        int maxWordLength = 0; //maximum word length of all the words from word list file

        
        try( FileReader fileReader = new FileReader(wordList); 
        		BufferedReader bufferedReader = new BufferedReader(fileReader);
        		FileReader fileReader2 = new FileReader(inputPuzzle);
        		BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
        		FileWriter fileWriter = new FileWriter(outputFileName);
        		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        		){
        	
        	//Read in word list and store in our dictionary hash table
        	
        	 while((line = bufferedReader.readLine()) != null) {
                 
        		dictionary.insert(line);
        		
        		if(line.length() > maxWordLength)
        			maxWordLength = line.length();
             }
        	 
        	//Read in the puzzle and detect the maximum length
        	 
        	 while((line = bufferedReader2.readLine()) != null){
        		
        		nSize = line.length();
        		
        		puzzleQueue.enqueue(line);
        	 }
        	 
        	//Initialize puzzle given our n x n size
        	puzzle = new String[nSize][nSize];
        	 
        	String[] temp; 
        	
        	//Fill the puzzle with the words inputted 
        	while( puzzleQueue.isEmpty() == false ){
        		
        		for(int i = 0; i < nSize; i++){
        			
        			temp = puzzleQueue.dequeue().split("");
        			
        			for(int j = 0; j < nSize; j++){
        				
        				puzzle[i][j] = temp[j];
        			}
        		}
        		
        	}
        	
        	
        	//Search and extract the words in the puzzle from left to right orientation and vice versa
        	
        	String holder = "";
        	
        	for(int row = 0; row < puzzle.length; row++ ){
    			
    			for(int cols = 0; cols< puzzle.length; cols++){
    				
    				holder =  holder + puzzle[row][cols];
    			}
    			
    			Holder(holder);
    		
    			holder = "";
    		}
        
        	//Search and extract the words in the puzzle from top to bottom orientation and vice versa
        	holder = "";
    		for(int cols = 0; cols < puzzle.length; cols++){
    			
    			for(int row = 0; row < puzzle.length; row++ ){
    				holder =  holder + puzzle[row][cols];
    			}
    			
    			Holder(holder);
    			holder = "";
    		}
        	
    		//Search and extract the words in the puzzle from diagonal orientations in both directions
    		
    		holder = "";
    		int col = 0;
    		int i;
    		
    		for(int row = 0; row < puzzle.length; row++){
    			
    			i = row;
    			
    			while( col < puzzle.length && i < puzzle.length )
    				holder = holder + puzzle[i++][col++];
    			
    			col = 0;
    			Holder(holder);
    			holder = "";
    		}
    		
    		
    		holder = "";
    		for(int row = 0, j = puzzle.length-1; row < puzzle.length - 1; row++, j--){
    			
    			i = 0;
    			int k = j;
    			
    			while( i <= row ){
    				
    				holder = holder + puzzle[i++][k++];
    				
    			}
    			
    			Holder(holder);
    			holder = "";
    		}
    		
    		
    		
    		holder = "";
    		for(int j = puzzle.length - 1; j >= 0; j--){
    		
    		i = puzzle.length - 1; 
    		int k = j;
    		
    		while( k <= puzzle.length - 1){
    			
    			holder = holder + puzzle[i--][k++];
    		}
    		Holder(holder);
    		holder="";
    	}
    	
    	
    	holder = "";
    	for(int row = 0; row < puzzle.length - 1; row++){
    		
    		col = 0;
    		i = row;
    		
    		while( i >= 0){
    			
    			holder = holder + puzzle[i--][col++];
    		}
    		
    		Holder(holder);
    		holder = "";
    	}
        	
        	
    	//The wordQueue now holds all the word combinations from the puzzle 
    	
    	
        	String tempWord;
        	
        	while( wordQueue.isEmpty() == false ){
        		
        		tempWord = wordQueue.dequeue();
        		
        		//Verify the the current word is legal and that it is not above the max word length
        		if( dictionary.find( tempWord ) == true && tempWord.length() <= maxWordLength )
        			
        			//If it is not a duplicate, store in our foundWords list
        			if( foundWords.contains(tempWord) == false ){
        				foundWords.add(tempWord);
            			count++;
        			}
        			
        				
        	}
        	
        	//Fill an array with our unique found words and sort them 
        	
        	foundWordsArr = new String[count];
        	
        	int index = 0;
        	
        	while( foundWords.isEmpty() == false ){
        		
        		
        		foundWordsArr[index] = foundWords.pop();
        		
        		index++;
        	}
        	
        	Arrays.sort(foundWordsArr);
        
        	
        	//write the final elements of the array into the output file
        	for(String current: foundWordsArr){
        		
        		bufferedWriter.write(current);
        		
        		bufferedWriter.newLine(); //separate by a new line
        	}
        	


        	
        	
        }//end of try-block
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + inputPuzzle + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '"+ inputPuzzle + "'");                  
           
        }
	}//end of main method

}//end of class WordFinder
