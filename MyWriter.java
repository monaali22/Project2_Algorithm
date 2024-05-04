package application;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class MyWriter {
	BufferedOutputStream out;

	public MyWriter(BufferedOutputStream out) {
		this.out = out;
	}

	public void write4Bytes(byte[] b) throws IOException {
		out.write(b);
	}

	void writeString(String s) throws IOException {
		for (int i = 0; i < s.length(); i++)
			out.write((byte) s.charAt(i));
	}

	void writeLong(long l) throws IOException {
		for (int i = 0; i < 8; i++) {
			out.write((byte) l);
			l >>= 8;
		}
	}

	void writeInt(int num) throws IOException {
		for (int i = 0; i < 4; i++) {
			out.write((byte) num);
			num >>= 8;
		}
	}

	void writeByte(byte b) throws IOException {
		out.write(b);
	}

	int n = 0,  //Keeps track of the number of bits accumulated in the buffer.
	buff = 0;  // Keeps track of the number of bits accumulated in the buffer.

	
	void writeBit(boolean bit) throws IOException {
		buff <<= 1; // left
		n++;
		if (bit)
			buff |= 1;

		if (n == 8) {
			out.write((byte) buff);
			buff = 0;
			n = 0;
		}
	}
}
