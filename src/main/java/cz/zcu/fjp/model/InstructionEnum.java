package cz.zcu.fjp.model;

public enum InstructionEnum {
	LIT("LIT"),
	OPR("OPR"),
	LOD("LOD"),
	STO("STO"),
	CAL("CAL"),
	INT("INT"),
	JMP("JMP"),
	JMC("JMC"),
	RET("RET");
	
	InstructionEnum(String instructionE) {
		this.instructionE = instructionE;
	}
	
	private String instructionE;
	
	public String getInstructionE() {
		return instructionE;
	}
}
