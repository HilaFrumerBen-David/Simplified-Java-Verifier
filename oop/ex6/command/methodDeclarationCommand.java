package oop.ex6.command;

import oop.ex6.Scope;
import oop.ex6.main.fileException;

/**
 * This class handles the declaration of a method, we note that at this stage this declaration is always
 * valid
 */
public class methodDeclarationCommand extends Command {

    /**
     * constructor
     * @param line The line that is handled
     * @param scope The scope we are in
     * @param type method declaration
     */
    public methodDeclarationCommand(String line, Scope scope, String type) {
        super(line, scope);
        this.setTypeCommand(type);
    }

    /**
     * this stage this declaration is always valid
     * @throws fileException no need it here
     */
    @Override
    public void checkValidCommand() throws fileException {}
}
