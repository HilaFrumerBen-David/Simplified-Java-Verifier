package oop.ex6.variable;

import oop.ex6.main.fileException;
import oop.ex6.regex;

import java.util.regex.Matcher;

/**
 * This class handles string variables, it extends the Variable class
 */
public class stringVariable extends Variable {
    /**
     * constructor
     * @param name name of variable
     * @param isFinal if the variable is final
     * @param isInitialized if the variable is initialized
     * @param type type of variable
     */
    public stringVariable(String name, boolean isFinal, boolean isInitialized, String type) {
        super(name, isFinal, isInitialized);
        this.setType(type);
    }

    /**
     * check the string variable
     * @throws fileException The error that is thrown if the string variable is incorrect
     */
    @Override
    public void validVar() throws fileException {
        Matcher nameMatcher = namePattern.matcher(this.getName());
        if (!nameMatcher.matches()) {
            throw new fileException(INVALID_NAME_VAR_ERROR_MSG);
        }
        if (getIsFinal() && !getIsInitialized()) {
            throw new fileException(INVALID_FINAL_VAR_ERROR_MSG);
        }
    }

    /**
     * This function checks whether the value we want to put inside a string variable is valid
     * @param optionalVal the value to assignment
     * @return true if valid, else false
     */
    @Override
    public boolean isValidValAssignment(String optionalVal) {
        return optionalVal.matches(regex.STRING);
    }

    /**
     * This function checks whether the var we want to put inside a string variable is valid
     * @param optionalVar the value to assignment
     * @return true if valid, else false
     */
    @Override
    public boolean isValidVarAssignment(Variable optionalVar) {
        return this.getType().equals(optionalVar.getType());
    }

    /**
     * This function copies the member, because of the use of scopes
     * @return copy of variable
     */
    @Override
    public Variable copy(){
        return new stringVariable(getName(), getIsFinal(), getIsInitialized(), getType());
    }
}
