package oop.ex6.variable;


import oop.ex6.main.fileException;

/**
 * This class is responsible for creating variables, it is a factory class
 */
public class variablesFactory {

    private static final String INVALID_TYPE_ERROR_MSG = "invalid sjava type of oop.ex6.variable";

    private static final String STRING = "String";
    private static final String BOOLEAN = "boolean";
    private static final String INT = "int";
    private static final String CHAR = "char";
    private static final String DOUBLE = "double";



     /** This function creates a variable according to the type appearing in the line
     * @param name name of variable
     * @param isFinal if the variable is final
     * @param isInitialized if the variable is initialized
     * @param type type of variable
     * @return Variable
     * @throws fileException The error that is thrown if the variable is incorrect
     */
    public static Variable createVariable(boolean isFinal, String name, String type, boolean isInitialized)
            throws fileException {
        switch (type) {
            case DOUBLE:
                doubleVariable doubleVar = new doubleVariable(name, isFinal, isInitialized, type);
                doubleVar.validVar();
                return doubleVar;
            case STRING:
                stringVariable stringVar = new stringVariable(name, isFinal, isInitialized, type);
                stringVar.validVar();
                return stringVar;
            case INT:
                intVariable intVar = new intVariable(name, isFinal, isInitialized, type);
                intVar.validVar();
                return intVar;
            case BOOLEAN:
                booleanVariable booleanVar = new booleanVariable(name, isFinal, isInitialized, type);
                booleanVar.validVar();
                return booleanVar;
            case CHAR:
                charVariable charVar = new charVariable(name, isFinal, isInitialized, type);
                charVar.validVar();
                return charVar;
        }
        throw new fileException(INVALID_TYPE_ERROR_MSG);
    }
}
