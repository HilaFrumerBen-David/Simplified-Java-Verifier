package oop.ex6.main;

import oop.ex6.parser.fileHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *  the main method of the program
 */
public class Sjavac {

    public static final String INVALID_FILE = "invalid file to program";
    public static final String NUM_OF_ARGS_NOT_VALID = "num of args not valid";
    public static final int ARGS_NUMBER = 1;
    public static final int SUCCESS_PROGRAM = 0;
    public static final int FILE_ERROR = 1;
    public static final int IO_ERROR = 2;

    /**
     * main function to start program
     * @param args arguments
     */
    public static void main(String[] args) {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]))){
            if (args.length != ARGS_NUMBER){
                throw new fileException(NUM_OF_ARGS_NOT_VALID);
            }
            fileHandler.handleFile(bufferedReader);
            System.out.println(SUCCESS_PROGRAM);
        }
        catch (fileException e){
            System.out.println(FILE_ERROR);
            System.err.println(e.getMessage());
        }
        catch (IOException io){
            System.out.println(IO_ERROR);
            System.err.println(INVALID_FILE);
        }
    }
}

