package oop.ex6.variable;

import oop.ex6.main.fileException;
import oop.ex6.regex;

import java.util.regex.Pattern;

/**
 * This class bundles all the variables
 */
public abstract class Variable {
    private final String name;
    private final boolean isFinal;
    private boolean isInitialized;
    private String type;

    public static final String INVALID_NAME_VAR_ERROR_MSG = "name of variable is not valid";
    public static final String INVALID_FINAL_VAR_ERROR_MSG = "final variable is not initialized";
    public static final Pattern namePattern = Pattern.compile(regex.VAR_NAME);
    public static String EMPTY_STR = "";
    public static String MINUS = "-";
    public static String PLUS = "+";
    public static String DOT = ".";
    public static final String DOUBLE = "double";
    public static final String INT = "int";


    /**
     * constructor
     * @param name name of variable
     * @param isFinal if the variable is final
     * @param isInitialized if the variable is initialized
     */

    public Variable(String name, boolean isFinal, boolean isInitialized) {
        this.name = name;
        this.isFinal = isFinal;
        this.isInitialized = isInitialized;
        this.type = null;
    }

    /**
     * set the type of variable
     * @param type type
     */
    protected void setType(String type)
    {
        this.type = type;
    }

    /**
     * getter for name of var
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return if the variable is final
     */
    public boolean getIsFinal() {
        return this.isFinal;
    }

    /**
     *
     * @return if the variable Is Initialized
     */
    public boolean getIsInitialized() {
        return this.isInitialized;
    }

    /**
     * set the variable initialized to be true or false
     * @param isInitialized true or false
     */
    public void setIsInitialized(boolean isInitialized) {
        this.isInitialized = isInitialized;
    }


    /**
     * This function checks whether the value we want to put inside a variable is valid
     * @param optionalVal the value to assignment
     * @return true if valid, else false
     */
    public abstract boolean isValidValAssignment(String optionalVal);

    /**
     * This function checks whether the var we want to put inside a variable is valid
     * @param optionalVar the value to assignment
     * @return true if valid, else false
     */
    public abstract boolean isValidVarAssignment(Variable optionalVar);

    /**
     *
     * @return the type of variable
     */
    public String getType() { return this.type; }

    /**
     * This function copies the member, because of the use of scopes
     * @return copy of variable
     */
    public abstract Variable copy();

    /**
     * check the validity variable
     * @throws fileException The error that is thrown if the variable is incorrect
     */
    public abstract void validVar() throws fileException;
}
