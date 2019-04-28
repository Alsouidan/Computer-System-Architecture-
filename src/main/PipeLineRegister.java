package main;

import java.util.HashMap;

public class PipeLineRegister {
	HashMap<String, Object> contents;
	String stage;
	public PipeLineRegister(String stage) {
		this.stage=stage;
		 contents= new HashMap<String, Object>();
		 contents.put("Empty", true);
	}
	public void addToReg(String name,Object value) {
		contents.put(name, value);
	}
	public Object getFromReg(String name) {
		return contents.get(name);
	}
	public void printReg() {
		System.out.println(contents.toString());
	}
}
