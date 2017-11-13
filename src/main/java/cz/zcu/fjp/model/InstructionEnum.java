package cz.zcu.fjp.model;


/**
 * Enum represents list of instructions possible to execute
 */
public enum InstructionEnum {
	EMPTY(""),
	//basic functions
	LIT("LIT"), 
	OPR("OPR"), 
	LOD("LOD"), 
	STO("STO"), 
	CAL("CAL"), 
	INT("INT"), 
	JMP("JMP"), 
	JMC("JMC"), 
	RET("RET"),
	//extended functions
	REA("REA"),
	WRI("WRI"),
	OPF("OPF"),
	RTI("RTI"),
	ITR("ITR"),
	NEW("NEW"),
	DEL("DEL"),
	LDA("LDA"),
	STA("STA"),
	PLD("PLD"),
	PST("PST");

	final private String instructionE;
	
	InstructionEnum(String instructionE) {
		this.instructionE = instructionE;
	}

	@Override
	public String toString() {
		return instructionE;
	}
}
