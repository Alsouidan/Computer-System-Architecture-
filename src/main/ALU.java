package main;

public class ALU {
	boolean Zero;
	int ALUResult;
	int Data1;
	int Data2;
	int Control;
	public boolean isZero() {
		return Zero;
	} 
	public void setZero(boolean zero) {
		Zero = zero;
	}
	public int getALUResult() {
		return ALUResult;
	}
	public void setALUResult(int aLUResult) {
		ALUResult = aLUResult;
	}
	public int getData1() {
		return Data1;
	}
	public void setData1(int data1) {
		Data1 = data1;
	}
	public int getData2() {
		return Data2;
	}
	public void setData2(int data2) {
		Data2 = data2;
	}
	public int getControl() {
		return Control;
	}
	public void setControl(int control) {
		control=Integer.parseInt(control+"",2);
		Control = control;
		int result = 0;
		String bin=null;
		String bin2=null;
		switch(Control) {
			case 5:
				bin = String.format("%016d", Integer.parseInt(Integer.toBinaryString(Data1))); //AND
				bin2 = String.format("%016d", Integer.parseInt(Integer.toBinaryString(Data2)));
				result=Integer.parseInt(anding(bin,bin2), 2);
				break;
		case 6:

			bin = String.format("%016d", Integer.parseInt(Integer.toBinaryString(Data1))); //OR
			bin2 = String.format("%016d", Integer.parseInt(Integer.toBinaryString(Data2)));
			result=Integer.parseInt(oring(bin,bin2), 2);
			break;
		case 3://st
		case 4://ld
		case 7:
			result=Data1+Data2;
			break;
		case 8:
			result =Data1-Data2;
			break;
		case 9:
			result=(Data1<Data2)?1:0;
			break;			
		case 10:
			bin = String.format("%016d", Integer.parseInt(Integer.toBinaryString(Data1))); //NOR
			bin2 = String.format("%016d", Integer.parseInt(Integer.toBinaryString(Data2)));
			result=~Integer.parseInt(oring(bin,bin2), 2);
			break;
		case 11:
			result=Data2;
		case 12:
			result = Data1<<Data2;
			break;
		case 13: 
			result = Data1>>Data2;
			break;
		case 14: 
			result = Data1^Data2;
			break;
		case 0:Zero=Data1==Data2;break;
	}
		ALUResult=result;
}
	
	public static String oring(String bm2,String bm1){
		String result="";
		for(int i=0;i<bm1.length();i++){
			if(bm1.charAt(i)=='1' || bm2.charAt(i)=='1'){
				result=result+"1";
			}else
				result=result+"0";
		}
		return result;
	}
	public static String anding(String bm2,String bm1){
		String result="";
		for(int i=0;i<bm1.length();i++){
			if(bm1.charAt(i)=='0' || bm2.charAt(i)=='0'){
				result=result+"0";
			}else
				result=result+"1";
		}
		return result;
	}
	public static void main (String [] args) {
		
	}
}
