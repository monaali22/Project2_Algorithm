package application;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadFile {

	void readFile(int[] rep, File file) throws IOException {

		BufferedInputStream in1 = new BufferedInputStream(new FileInputStream(file));
		
        // Calculate the number of 4-byte chunks and the remaining bytes in the file
		int size = (int) (file.length() / 4);
		int rem = (int) (file.length() % 4);
		for (int i = 0; i < size; i++) {
			byte[] b = new byte[4];
			
			
            // Read 4 bytes into the byte array 'b'
			in1.read(b);
			for (int j = 0; j < 4; j++)
				rep[(b[j] & 0xFF)]++;
		}

		for (int i = 0; i < rem; i++) {
			byte[] b = new byte[1];
			in1.read(b);
			rep[(b[0] & 0xFF)]++;
		}
	}
}
