package main;

public class RFile {
	Register[] registers = new Register[16];
	int ReadReg1;
	int ReadReg2;
	int WriteReg;
	int WriteData;
	int ReadData1;
	int ReadData2;
	boolean RegWrite;

	public RFile() {
		registers[0] = new Register("RR0",(short) 0);
		registers[1] = new Register("RR1",(short) 0);
		registers[2] = new Register("A0",(short) 0);
		registers[3] = new Register("A1",(short) 0);
		registers[4] = new Register("T0",(short) 0);
		registers[5] = new Register("T1",(short) 0);
		registers[6] = new Register("T2",(short) 0);
		registers[7] = new Register("T3",(short) 0);
		registers[8] = new Register("T4",(short) 0);
		registers[9] = new Register("S0",(short) 0);
		registers[10] = new Register("S1",(short) 0);
		registers[11] = new Register("S2",(short) 0);
		registers[12] = new Register("S3",(short) 0);
		registers[13] = new Register("S4",(short) 0);
		registers[14] = new Register("SP",(short) 0);
		registers[15] = new Register("RA",(short) 0);
	}

	public Register[] getRegisters() {
		return registers;
	}

	public void setRegisters(Register[] registers) {
		this.registers = registers;
	}

	public int getReadReg1() {
		return ReadReg1;
	}

	public void setReadReg1(int readReg1) {
		ReadReg1 = readReg1;
		ReadData1=registers[readReg1].getValue();
		System.out.println(ReadData1);
	}

	public int getReadReg2() {
		return ReadReg2;
	}

	public void setReadReg2(int readReg2) {
		ReadReg2 = readReg2;
		ReadData2=registers[readReg2].getValue();
	}

	public int getWriteReg() {
		return WriteReg;
	}

	public void setWriteReg(int writeReg) {
		WriteReg = writeReg;
	}

	public int getWriteData() {
		return WriteData;
	}

	public void setWriteData(int writeData) {
		WriteData = writeData;
		if(RegWrite) {
			registers[WriteReg].setValue((short) WriteData);
		}
		
	}

	public int getReadData1() {
		return ReadData1;
	}

	public void setReadData1(int readData1) {
		ReadData1 = readData1;
	}

	public int getReadData2() {
		return ReadData2;
	}

	public void setReadData2(int readData2) {
		ReadData2 = readData2;
	}

	public boolean isRegWrite() {
		return RegWrite;
	}

	public void setRegWrite(boolean regWrite) {
		RegWrite = regWrite;
	}
public void printRegFile() {
	System.out.println("PRINTING REGISTERS");
for(int i=0;i<registers.length;i++) {
	System.out.println(registers[i].name+":"+registers[i].value);
}
}
}

