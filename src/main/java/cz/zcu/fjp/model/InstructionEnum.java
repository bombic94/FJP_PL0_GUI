package cz.zcu.fjp.model;


/**
 * Enum represents list of instructions possible to execute
 */
public enum InstructionEnum {
	EMPTY(""),
	LIT("LIT"), 
	OPR("OPR"), 
	LOD("LOD"), 
	STO("STO"), 
	CAL("CAL"), 
	INT("INT"), 
	JMP("JMP"), 
	JMC("JMC"), 
	RET("RET");

	final private String instructionE;
	
	InstructionEnum(String instructionE) {
		this.instructionE = instructionE;
	}

	@Override
	public String toString() {
		return instructionE;
	}
}
