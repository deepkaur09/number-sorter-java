 /**
 * This program reads numbers from a file ("numbers.csv") and stores them in an ArrayList.
 * It then sorts the numbers using recursive Selection Sort and outputs the sorted list to "sorted_num.csv".
 * It also counts how many times each number appears and outputs that to "num_frequency.csv".
 * @authors: Dapinderdeep Kaur, Frankie Gonzalez, Oluwaferanmi Morohunfola
 * @version 1.0
 */
 
import java.util.*;
import java.io.*;

public class SortNums
{
    /**
	 * A method that recursively reads every line of the file and processes it.
	 * @param list: The Arraylist storing all numebers.
	 * @param fileScanner: Scanner object to read the data from input file.
	*/
    public static void readData(ArrayList<Integer> list, Scanner fileScanner){
        //Base case
        if(!(fileScanner.hasNextLine())){
            return;
        }
        
        String line = fileScanner.nextLine();
        
        //Handling the commas of input file.
        line = line.replace(',', ' ');
        Scanner lineScanner = new Scanner(line);
        
        //Extract integers from this line
        readLine(lineScanner, list);
        
        //Recursive call
        readData(list, fileScanner);
    }
    
    /**
     * Recursively extracts integers from a line of text.
     * @param lineScanner: Scanner scanning a single line.
     * @param list: ArrayList to store integers.
    */
    public static void readLine(Scanner lineScanner, ArrayList<Integer> list) {
        //Base case
        if(!lineScanner.hasNext()){
            return;
        }
        
        //Add number to the list
        if(lineScanner.hasNextInt()){
            list.add(lineScanner.nextInt());
        }
        //handling non-integer input values
        else{
            String invalid = lineScanner.next();
            System.out.println("Invalid input found: " + invalid);
        }
        
        //Recursive call
        readLine(lineScanner, list);
    }
    
    /**
     * Recursively performs selection sort.
     * @param list: The list being sorted.
     * @param num: Current position in the list.
    */
    public static void sortArray(ArrayList<Integer> list, int num){
        //Base case
        if(num >= list.size() - 1){
            return;
        }
        int minPos = minimumPosition(list, num);
        swap(list, minPos, num);
        
        //Recursive call
        sortArray(list, num + 1);
    }
    
    /**
     * Recursively finds position of the smallest value starting from 'start'.
     * @param list: The list of integers being sorted.
     * @param start: The index from which to start searching for the minimum value.
     * @return The index of the smallest element in the portion of the list beginning at 'start'.
    */
    public static int minimumPosition(ArrayList<Integer> list, int start){
        //Base case
        if(start >= list.size() - 1){
            return start;
        }
        // Recursively find the minimum position in the remainder of the list
        int minimumPos = minimumPosition(list, start + 1);
        
        // Compare current element with the smallest found in recursive call
        if(list.get(start) < list.get(minimumPos)){
            return start;
        }
        else{
            return minimumPos;
        }
    }
    
    /**
     * Swaps the values at two given indices in the list.
     * @param list: The list in which elements will be swapped.
     * @param a: The index of the first value to swap.
     * @param i: The index of the second value to swap.
    */
    public static void swap(ArrayList<Integer> list, int a, int i){
        int temp = list.get(a);
        list.set(a, list.get(i));
        list.set(i, temp);
    }
    
    /**
     * Recursively writes each value of the list into the "sorted_num.csv" file.
     * @param list: The sorted list of integers to display.
     * @param given: The current index in the list.
     * @param writer: The PrintWriter used to write values to the output file.
    */
    public static void displayList(ArrayList<Integer> list, int given, PrintWriter writer){
        //Base case
        if(given >= list.size()){
            return;
        }
        writer.print(list.get(given) + " ");
        
        //Recursive call
        displayList(list, given + 1, writer);
    }

    /**
     * Recursively counts how many times each unique number appears in the sorted list
     *  and writes the result to the "num_frequency.csv" file.
     * @param list: The sorted list of integers.
     * @param index: The current index being checked in the list.
     * @param currentNum: The number currently being counted.
     * @param count: How many times currentNum has appeared so far.
     * @param frequencyWriter: The PrintWriter used to write frequency output.
    */
    public static void countFrequency(ArrayList<Integer> list, int index, int currentNum, int count, PrintWriter frequencyWriter) 
    {
        // Base case: reached end of list, print the last number's count.
        if (index >= list.size()){
            frequencyWriter.printf("%5d %7d times\n", currentNum, count);
            return;
        }
        int nextNum = list.get(index);

        // If same number repeats, increase the count.
        if (nextNum == currentNum){
            //Recursive call- 1
            countFrequency(list, index + 1, currentNum, count + 1, frequencyWriter);
        } 
        
        //Otherwise, print result for current number and start a new count.
        else{
            frequencyWriter.printf("%5d %7d times\n", currentNum, count);
            //Recurive call- 2
            countFrequency(list, index + 1, nextNum, 1, frequencyWriter);
        }
    }
    
    /**
     * Main method: reads numbers from the file, sorts them, writes the sorted
     *  list to one output file, and writes the frequency of each number to another.
     * @param args: command line arguments
	*/
	public static void main(String[] args) {
	    PrintWriter writer = null;
	    PrintWriter frequencyWriter = null;
	    Scanner fileScanner = null;
	    try{
	        File inputFile = new File("numbers.csv");
	        writer = new PrintWriter("sorted_num.csv");
	        frequencyWriter = new PrintWriter("num_frequency.csv");
	        fileScanner = new Scanner(inputFile);
	        ArrayList<Integer> list = new ArrayList<>();
	        readData(list, fileScanner);      // read numbers
	        
	        // handle exception when file is empty
	        if (list.size() == 0) {  
	            writer.println("The file has no interger values.");
                frequencyWriter.println("The file has no interger values.");
                return;
	        }
	        sortArray(list, 0);               // sort numbers
	        System.out.printf("See file \"sorted_num.csv\" and \"num_frequency.csv\" for output data.\n");
	        displayList(list, 0, writer);     // write sorted list
	        frequencyWriter.printf("Number     Frequency\n");
	        countFrequency(list, 1, list.get(0), 1, frequencyWriter);  // write frequency
	    }
	    
	    catch (FileNotFoundException e){
	        System.out.println("No such file exists.");
	        writer.println("Cannot open file.");
	        frequencyWriter.println("Cannot open file.");
	    }
	    
	    //Finally block to close the PrintWriters and Scanner
	    finally{
	        if(writer != null){
	            writer.close();
	        }
	        if(fileScanner != null){
	            fileScanner.close();
	        }
	        if(frequencyWriter != null)
	           frequencyWriter.close();
	    }
	}
}
