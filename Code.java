package application;

public class Code implements Comparable<Code> {

	char ch;       // Represents a character
	int rep;         // Represents the frequency or repetition count of the character
	int length;        // Represents the length of the code associated with the character
	String code;       // Represents the code associated with the character

	
	
	
	public Code() {

	}

	public Code(char ch, String code, int rep) {
		this.ch = ch;
		this.code = code;
		this.rep = rep;
		length = this.code.length();
	}


	public char getCh() {
		return ch;
	}

	public void setCh(char ch) {
		this.ch = ch;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getRep() {
		return rep;
	}

	public void setRep(int rep) {
		this.rep = rep;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}


	public int compareTo(Code c) {
		if (c.ch > this.ch)
			return -1;
		else if (c.ch < this.ch)
			return 1;
		else
			return 0;
	}
}
