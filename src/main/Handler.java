package main;

public class Handler {
	PipeLineRegister IFID;
	PipeLineRegister IDEX;
	PipeLineRegister EXMEM;
	PipeLineRegister MEMWB;
	ALU alu;
	ControlUnit cu;
	int clk = 0;
	InstructionMemory instructionMemory;
	RFile file;
	DataMemory dataMemory;
	String[] program;
	int pc = 0;
	int count1;
	int count2;
	int count3;
	int count4;

	public Handler(String[] program) {
		IFID = new PipeLineRegister("IFID");
		IDEX = new PipeLineRegister("IDEX");
		EXMEM = new PipeLineRegister("EXMEM");
		MEMWB = new PipeLineRegister("MEMWB");
		alu = new ALU();
		cu = new ControlUnit();
		instructionMemory = new InstructionMemory(program);
		file = new RFile();
		dataMemory = new DataMemory();
		this.program = program;

	}

	public static void main(String[] args) {
		String[] ins =new String[3];
		
		ins[0]="1011010011100011";
		ins[1]="1011010111100011";
		ins[2]="0011000000010001";
//		ins[1]="1000000100000011";
//		ins[2]="1011000100000111";


		Handler simulation=new Handler(ins);
		simulation.file.setRegWrite(true);
		simulation.file.setWriteReg(4);
		simulation.file.setWriteData(227);
		simulation.file.setWriteReg(5);
		simulation.file.setWriteData(225);
		simulation.dataMemory.setWriteToMemory(true);
		simulation.dataMemory.setAddress((short) 1);
		simulation.dataMemory.setToBeWritten((short) 12);
		simulation.dataMemory.writeToMemory();
		boolean flag;
		int count=0;
		while(true) {
			if(count==4) {break;}
			System.out.println("Now In Cycle:"+ simulation.clk);
//			if(!(boolean)simulation.MEMWB.getFromReg("Empty")) {
				simulation.writeBack();
//			}
//			if(!(boolean)simulation.EXMEM.getFromReg("Empty")) {
				simulation.memory();
//			}
//			if(!(boolean)simulation.IDEX.getFromReg("Empty")) {
			simulation.execute();
//			}
			
//			if((boolean)simulation.IFID.getFromReg("Empty")==false) {
				simulation.decode();
//			}
			 flag=simulation.fetch();
			 if(!flag) {count++;}else {count=0;}
//			 if(!(boolean)simulation.EXMEM.getFromReg("Empty")) {
//				 simulation.MEMWB.addToReg("Empty",false);
//			 }
//			 if(!(boolean)simulation.IDEX.getFromReg("Empty")) {
//				 simulation.EXMEM.addToReg("Empty",false);
//			 }
//			 if(!(boolean)simulation.IFID.getFromReg("Empty")) {
//					simulation.IDEX.addToReg("Empty",false);
//
//			 }
//			simulation.IFID.addToReg("Empty",false);
			simulation.clk++;
			
		}
		simulation.dataMemory.print();
	simulation.file.printRegFile();

	}

	public boolean fetch() {
//		if (count1==pc) {
//			return false;
//		}
		IFID.addToReg("Empty", true);
		String currentInstruction = instructionMemory.getAtPC(pc);
		if (currentInstruction == null) {
			return false;
		}
		System.out.println("Fetching"+pc);
		IFID.addToReg("instruction", currentInstruction);
		pc++;
		int c=pc;
//		count1=pc;
		IFID.addToReg("PC", c);
		IFID.addToReg("Empty",false);
		IFID.printReg();
		return true;

	}

	public void decode() {
//		if (count2==Integer.parseInt(IFID.getFromReg("PC")+"")-1) {
//			return ;
//		}
		IDEX.addToReg("Empty", true);
		if((boolean)IFID.getFromReg("Empty")) {
			return;
		}		System.out.println("Decode"+(Integer.parseInt(IFID.getFromReg("PC")+"")-1));
//		IDEX.addToReg("Empty",false);
		String currentInstruction = IFID.getFromReg("instruction") + "";
		cu.handleOpCode(currentInstruction.substring(0, 4));
		String offset = currentInstruction.substring(12);
		String source1 = currentInstruction.substring(4, 8);
		String source2 = currentInstruction.substring(8, 12);
		String destination = currentInstruction.substring(12);
		String address = currentInstruction.substring(4);
		String immediate = currentInstruction.substring(8);
		String destReg = currentInstruction.substring(4, 8);
		// simmulate MUXES in Decode now
		if (cu.isRegDst()) {
			if (cu.isLink()) {
				file.setWriteReg(15);
				if(cu.isImmediate()){
					file.setWriteReg(Integer.parseInt(MEMWB.getFromReg("source1")+"", 2));

				}

			} else {
				
				file.setWriteReg(Integer.parseInt(destination, 2));
				if(cu.isImmediate()){
					file.setWriteReg(Integer.parseInt(MEMWB.getFromReg("source1")+"", 2));

				}

			}

		} else {
			if (cu.isLink()) {
				file.setWriteReg(15);
				if(cu.isImmediate()){
					file.setWriteReg(Integer.parseInt(source1+"", 2));

				}

			} else {
				file.setWriteReg(Integer.parseInt(source2, 2));
				if(cu.isImmediate()){
					file.setWriteReg(Integer.parseInt(source1+"", 2));

				}

			}
		}
		file.setReadReg1(Integer.parseInt(source1, 2));
		file.setReadReg2(Integer.parseInt(source2, 2));
		IDEX.addToReg("PC", IFID.getFromReg("PC"));
		IDEX.addToReg("ReadData1", file.getReadData1());
		IDEX.addToReg("ReadData2", file.getReadData2());
		IDEX.addToReg("OPCODE", currentInstruction.substring(0, 4));
		IDEX.addToReg("BRANCH", cu.isBRANCH());
		IDEX.addToReg("MemRead", cu.isMemRead());
		IDEX.addToReg("Immediate", cu.isImmediate());
		IDEX.addToReg("Offset", offset);
		IDEX.addToReg("RegWrite",cu.isRegWrite());
		IDEX.addToReg("ALUSrc",cu.isALUSrc());
		IDEX.addToReg("MemWrite",cu.isMemWrite());
		IDEX.addToReg("jp", cu.isJp());
		IDEX.addToReg("MoveR",cu.isMoveR());
		IDEX.addToReg("address",address);
		IDEX.addToReg("destination",destination);
		IDEX.addToReg("source2",source2);
		IDEX.addToReg("source1",source1);
		IDEX.addToReg("RegDst",cu.isRegDst());
		IDEX.addToReg("ImValue",immediate);
		IDEX.addToReg("MemtoReg",cu.isMemtoReg());
		IDEX.addToReg("Link",cu.isLink());
		IDEX.addToReg("Empty", false);
		IDEX.printReg();



//		count2=Integer.parseInt(IFID.getFromReg("PC")+"")-1;
	}

	public void execute() {
//		if (count3==Integer.parseInt(IDEX.getFromReg("PC")+"")-1) {
//			return ;
//		}
		EXMEM.addToReg("Empty", true);
		if((boolean)IDEX.getFromReg("Empty")) {
			return;
		}
		System.out.println("Executing"+(Integer.parseInt(IDEX.getFromReg("PC")+"")-1));
		alu.setData1(Integer.parseInt(IDEX.getFromReg("ReadData1")+""));
		if((boolean)IDEX.getFromReg("ALUSrc")){
			alu.setData2(Integer.parseInt(IDEX.getFromReg("Offset")+"",2));


		}
		else {
			alu.setData2(Integer.parseInt(IDEX.getFromReg("ReadData2")+""));

		}
		
		alu.setControl(Integer.parseInt(IDEX.getFromReg("OPCODE")+""));
		int jumpingAddress=(Integer.parseInt(IDEX.getFromReg("Offset")+""))+(Integer.parseInt(IDEX.getFromReg("PC")+""));
		int temp;
		if((boolean)IDEX.getFromReg("BRANCH")&& alu.isZero()) {
			temp=jumpingAddress;
		}
		else {temp=(int) IDEX.getFromReg("PC");
			
		}
		if((boolean)IDEX.getFromReg("jp")) {
			EXMEM.addToReg("address",Integer.parseInt(IDEX.getFromReg("address")+"", 2));
			

		}
		else {			
			EXMEM.addToReg("address",temp);

			
		}
		EXMEM.addToReg("ReadData2",Integer.parseInt(IDEX.getFromReg("ReadData2")+""));
		EXMEM.addToReg("ReadData1",Integer.parseInt(IDEX.getFromReg("ReadData1")+""));
		EXMEM.addToReg("source2",IDEX.getFromReg("source2"));
		EXMEM.addToReg("source1",IDEX.getFromReg("source1"));
		EXMEM.addToReg("destination",IDEX.getFromReg("destination"));
		EXMEM.addToReg("Immediate",(boolean)IDEX.getFromReg("Immediate"));
		EXMEM.addToReg("MemRead",(boolean)IDEX.getFromReg("MemRead"));
		EXMEM.addToReg("MoveR",(boolean)IDEX.getFromReg("MoveR"));
		EXMEM.addToReg("MemWrite",(boolean)IDEX.getFromReg("MemWrite"));
		EXMEM.addToReg("Link",(boolean)IDEX.getFromReg("Link"));
		EXMEM.addToReg("MemtoReg",(boolean)IDEX.getFromReg("MemtoReg"));
		EXMEM.addToReg("RegWrite",(boolean)IDEX.getFromReg("RegWrite"));
		EXMEM.addToReg("RegDst",(boolean)IDEX.getFromReg("RegDst"));
		EXMEM.addToReg("ALUResult",alu.getALUResult());
		EXMEM.addToReg("ImValue",IDEX.getFromReg("ImValue"));
		EXMEM.addToReg("Zero",alu.isZero());
		EXMEM.addToReg("PC",Integer.parseInt(IDEX.getFromReg("PC")+""));
		EXMEM.addToReg("Empty", false);
		EXMEM.printReg();
//		IDEX.addToReg("Empty", true);
//		count3=Integer.parseInt(IDEX.getFromReg("PC")+"")-1;


	}

	public void memory() {
//		if (count4==Integer.parseInt(EXMEM.getFromReg("PC")+"")-1) {
//			return ;
//		}
		MEMWB.addToReg("Empty", true);
		if((boolean)EXMEM.getFromReg("Empty")) {
			return;
		}
		System.out.println("Memory"+(Integer.parseInt(EXMEM.getFromReg("PC")+"")-1));
		dataMemory.setAddress((short) Integer.parseInt(EXMEM.getFromReg("ALUResult")+""));
		dataMemory.setToBeWritten((short) (Integer.parseInt(EXMEM.getFromReg("ReadData2")+"")));
		dataMemory.setReadFromMemory((boolean)EXMEM.getFromReg("MemRead"));
		dataMemory.setWriteToMemory(((boolean)EXMEM.getFromReg("MemWrite")));
		dataMemory.writeToMemory();
		dataMemory.readFromMemory();
		MEMWB.addToReg("ALUResult",Integer.parseInt(EXMEM.getFromReg("ALUResult")+""));
		MEMWB.addToReg("PC",Integer.parseInt(EXMEM.getFromReg("PC")+""));
		MEMWB.addToReg("ReadData",dataMemory.getReturnedData());
		MEMWB.addToReg("MemWrite",(boolean)EXMEM.getFromReg("MemWrite"));
		MEMWB.addToReg("MemtoReg",(boolean)EXMEM.getFromReg("MemtoReg"));
		MEMWB.addToReg("RegWrite",(boolean)EXMEM.getFromReg("RegWrite"));
		MEMWB.addToReg("ImValue",EXMEM.getFromReg("ImValue"));
		MEMWB.addToReg("MoveR",(boolean)EXMEM.getFromReg("MoveR"));
		MEMWB.addToReg("ReadData1",Integer.parseInt(EXMEM.getFromReg("ReadData1")+""));
		MEMWB.addToReg("Link",(boolean)EXMEM.getFromReg("Link"));
		MEMWB.addToReg("Immediate",(boolean)EXMEM.getFromReg("Immediate"));
		MEMWB.addToReg("RegDst",(boolean)EXMEM.getFromReg("RegDst"));
		MEMWB.addToReg("source2",EXMEM.getFromReg("source2"));
		MEMWB.addToReg("source1",EXMEM.getFromReg("source1"));
		MEMWB.addToReg("destination",EXMEM.getFromReg("destination"));
		MEMWB.addToReg("address",EXMEM.getFromReg("address"));
		MEMWB.addToReg("Empty", false);
		MEMWB.printReg();

//		count4=Integer.parseInt(EXMEM.getFromReg("PC")+"")-1;


	}

	public void writeBack() {
		if((boolean)MEMWB.getFromReg("Empty")) {
			return;
		}
		System.out.println("WriteBack"+(Integer.parseInt(MEMWB.getFromReg("PC")+"")-1));
		if ((boolean)MEMWB.getFromReg("RegDst")) {
			if ((boolean)MEMWB.getFromReg("Link")) {
				if((boolean)MEMWB.getFromReg("Immediate")){
					file.setWriteReg(15);

					file.setWriteReg(Integer.parseInt(MEMWB.getFromReg("source1")+"", 2));

				}
				

			} else {
				file.setWriteReg(Integer.parseInt(MEMWB.getFromReg("destination")+"", 2));
				if((boolean)MEMWB.getFromReg("Immediate")){
					file.setWriteReg(Integer.parseInt(MEMWB.getFromReg("source1")+"", 2));

				}


			}

		} else {
			if ((boolean)MEMWB.getFromReg("Link")) {
				file.setWriteReg(15);
				if((boolean)MEMWB.getFromReg("Immediate")){
					file.setWriteReg(Integer.parseInt(MEMWB.getFromReg("source1")+"", 2));

				}

			} else {
				file.setWriteReg(Integer.parseInt(MEMWB.getFromReg("source2")+"", 2));
				if((boolean)MEMWB.getFromReg("Immediate")){
					file.setWriteReg(Integer.parseInt(MEMWB.getFromReg("source1")+"", 2));

				}

			}
		}
		file.setRegWrite((boolean)MEMWB.getFromReg("RegWrite"));
		int temp1=(!(boolean)MEMWB.getFromReg("RegDst"))?Integer.parseInt(MEMWB.getFromReg("ReadData")+""):Integer.parseInt(MEMWB.getFromReg("ALUResult")+"");
		System.out.println(temp1);
		int temp2=!((boolean)MEMWB.getFromReg("Immediate"))?temp1:Integer.parseInt(MEMWB.getFromReg("ImValue")+"", 2);
		System.out.println(temp2);
		int temp3=((boolean)MEMWB.getFromReg("Link"))?Integer.parseInt(MEMWB.getFromReg("PC")+""):temp2;
		System.out.println(temp3);
		file.setWriteData(temp3);
		int temp5=((boolean)MEMWB.getFromReg("MoveR"))?Integer.parseInt(MEMWB.getFromReg("ReadData1")+""):Integer.parseInt(MEMWB.getFromReg("address")+"");
//		pc=temp5;
	}
}
