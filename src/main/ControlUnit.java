package main;

public class ControlUnit {
	boolean Link;
	boolean BRANCH;
	boolean RegDst;
	boolean MemRead;
	boolean MemtoReg;
	boolean MemWrite;
	boolean ALUSrc;
	boolean RegWrite;
	boolean Immediate;
	boolean MoveR;
	boolean jp;
	public void handleOpCode(String opCode) {
		reset();
		switch (opCode) {
		// BIE
		case "0000":
			BRANCH = true;
			break;
		// JR
		case "0001":
			MoveR = true;
			break;
		// BUN
		case "0010":
			jp = true;
			
			break;
		// LD
		case "0011":
			MemRead = true;
			MemtoReg = true;
			ALUSrc = true;
			RegWrite = true;
			break;
		//ST
		case "0100":
			MemWrite = true;
			ALUSrc = true;
			break;
		//AND
		case "0101":
			RegDst = true;
			RegWrite = true;
			break;
		//OR
		case "0110":
			RegDst = true;
			RegWrite = true;
			break;
		//ADD
		case "0111":
			RegDst = true;
			RegWrite = true;
			break;
		//SUB
		case "1000":
			RegDst = true;
			RegWrite = true;
			break;
		//SLT
		case "1001":
			RegDst = true;
			RegWrite = true;
			break;
		//NOR
		case "1010":
			RegDst = true;
			RegWrite = true;
			break;
		//MI
		case "1011":
			Immediate = true;
			RegWrite = true;
			ALUSrc=true;
			break;
		//SL
		case "1100":
			RegDst = true;
			RegWrite = true;
			break;
		//SR
		case "1101":
			RegDst = true;
			RegWrite = true;
			break;
		//XOR
		case "1110":
			RegDst = true;
			RegWrite = true;
			break;
		//CAL
		case "1111":
			Link = true;
			jp = true;
			break;
		}

	}
	public boolean isJp() {
		return jp;
	}
	public void setJp(boolean jp) {
		this.jp = jp;
	}
	public void reset() {
	 Link=false;
	 BRANCH=false;
	 RegDst=false;
	 MemRead=false;
	 MemtoReg=false;
	 MemWrite=false;
	 ALUSrc=false;
	 RegWrite=false;
	 Immediate=false;
	 MoveR=false;
	}
	public boolean isLink() {
		return Link;
	}
	public void setLink(boolean link) {
		Link = link;
	}
	public boolean isBRANCH() {
		return BRANCH;
	}
	public void setBRANCH(boolean bRANCH) {
		BRANCH = bRANCH;
	}
	public boolean isRegDst() {
		return RegDst;
	}
	public void setRegDst(boolean regDst) {
		RegDst = regDst;
	}
	public boolean isMemRead() {
		return MemRead;
	}
	public void setMemRead(boolean memRead) {
		MemRead = memRead;
	}
	public boolean isMemtoReg() {
		return MemtoReg;
	}
	public void setMemtoReg(boolean memtoReg) {
		MemtoReg = memtoReg;
	}
	public boolean isMemWrite() {
		return MemWrite;
	}
	public void setMemWrite(boolean memWrite) {
		MemWrite = memWrite;
	}
	public boolean isALUSrc() {
		return ALUSrc;
	}
	public void setALUSrc(boolean aLUSrc) {
		ALUSrc = aLUSrc;
	}
	public boolean isRegWrite() {
		return RegWrite;
	}
	public void setRegWrite(boolean regWrite) {
		RegWrite = regWrite;
	}
	public boolean isImmediate() {
		return Immediate;
	}
	public void setImmediate(boolean immediate) {
		Immediate = immediate;
	}
	public boolean isMoveR() {
		return MoveR;
	}
	public void setMoveR(boolean moveR) {
		MoveR = moveR;
	}
}
