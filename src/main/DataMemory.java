package main;

public class DataMemory {
	private short [] data = new short[4096];
	private short address;
	private boolean writeToMemory;
	private boolean readFromMemory;
	private short toBeWritten;
	private short returnedData;
	
	public short[] getData() {
		return data;
	}
	public void setData(short[] data) {
		this.data = data;
	}
	public int getAddress() {
		return address;
	}
	public void setAddress(short address) {
		this.address = address;
	}
	public boolean isWriteToMemory() {
		return writeToMemory;
	}
	public void setWriteToMemory(boolean writeToMemory) {
		this.writeToMemory = writeToMemory;
	}
	public boolean isReadFromMemory() {
		return readFromMemory;
	}
	public void setReadFromMemory(boolean readFromMemory) {
		this.readFromMemory = readFromMemory;
	}
	public int getToBeWritten() {
		return toBeWritten;
	}
	public void setToBeWritten(short toBeWritten) {
		this.toBeWritten = toBeWritten;
	}
	public int getReturnedData() {
		return returnedData;
	}
	public void setReturnedData(short returnedData) {
		this.returnedData = returnedData;
	}
	public int readFromMemory() {
		if(isReadFromMemory()) {
		returnedData=data[address];}
		return returnedData;
				}
	public void writeToMemory() {
	if(isWriteToMemory()) {
		data[address]=toBeWritten;
	}
	
	}
	public void print(){
		System.out.println("PRINTING DATA MEMORY");
		for(int i=0;i<10;i++){
			System.out.println(i+":"+data[i]);

		}
	}
}
