package oop.ex6.command;
import oop.ex6.Scope;
import oop.ex6.main.fileException;

public class ReturnCommand extends Command {

    /**
     * constructor
     * @param line The line that is handled
     * @param scope The scope we are in
     * @param type return
     */
    public ReturnCommand(String line, Scope scope, String type){
        super(line, scope);
        this.setTypeCommand(type);
    }

    /**
     * this stage this return is always valid
     * @throws fileException no need it here
     */
    @Override
    public void checkValidCommand() throws fileException {}

}
