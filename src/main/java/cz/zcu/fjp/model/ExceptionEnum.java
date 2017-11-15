package cz.zcu.fjp.model;

public enum ExceptionEnum {
	UNKNOWN_INST("This instruction does not exist or is not implemented for given parameters"),
	WRONG_ARG("Argument for given operation cannot be proccessed"),
	NEGATIVE("Argument for given operation cannot be negative number"),
	OVERFLOW("Argument for given operation points to position which is not allocated");

	final private String excText;

	ExceptionEnum(String excText) {
		this.excText = excText;
	}

	@Override
	public String toString() {
		return excText;
	}
}
