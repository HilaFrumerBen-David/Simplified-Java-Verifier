package oop.ex6.parser;

import oop.ex6.main.fileException;
import oop.ex6.variable.Variable;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This department is responsible for handling the file, from start to finish
 */
public class fileHandler {

    /**
     * This function is responsible for handling the file, from start to finish
     * @param bufferedReader  buffered Reader
     * @throws IOException errors from IO
     * @throws fileException The error that is thrown if the file handler is incorrect
     */
    public static void handleFile(BufferedReader bufferedReader) throws IOException, fileException {
            firstFilter firstFiltered = new firstFilter(bufferedReader);
            HashMap<String, ArrayList<Variable>> methodsHashMap = new HashMap<String, ArrayList<Variable>>();
            ArrayList<String> fileValidLines = new ArrayList<String>();
            firstFiltered.clearFile(fileValidLines, methodsHashMap);
            mainFilter mainFiltered = new mainFilter(methodsHashMap, fileValidLines);
            mainFiltered.runMainFilter();
        }


}
