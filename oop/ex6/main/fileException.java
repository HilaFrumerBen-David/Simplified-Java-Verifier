package oop.ex6.main;

/**
 * This class handles errors that happen to us in the code, it extends the Exception class
 */
public class fileException extends Exception{

    /**
     * constructor
     * @param errorMessage Error message thrown during the program
     */
    public fileException(String errorMessage) {
        super(errorMessage);
    }
}
