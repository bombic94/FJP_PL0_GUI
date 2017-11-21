package cz.zcu.fjp.model;

import cz.zcu.fjp.controller.MainController;
import cz.zcu.fjp.controller.PL0Debugger;

/**
 * MyException calls controller to popup new alert window with occurred error
 */
public class MyException extends Exception {

	private PL0Debugger pl0 = PL0Debugger.getInstance();
	private MainController controller = pl0.getController();
	private static final long serialVersionUID = -8374250665378163257L;

	public MyException(ExceptionEnum error) {
		super(error.toString());
		controller.alert(error);
	}
}
