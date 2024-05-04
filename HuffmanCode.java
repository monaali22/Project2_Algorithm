package application;
import java.util.Hashtable;
import java.util.PriorityQueue;

public class HuffmanCode {

	Hashtable<Character, Code> codes = new Hashtable<>();
	PriorityQueue<Node> minHeap = new PriorityQueue<>();


	
	void generateCodes(int[] rep) {
		Node left, right, top;
		

        // Priority queue to store nodes with their frequencies (min heap based on frequency)

		for (int i = 0; i < rep.length; i++) {
			if (rep[i] > 0) {             // non-zero frequencies
				minHeap.add(new Node((char) i, rep[i], true));

			}
		}

		while (minHeap.size() != 1) {
			left = minHeap.poll();
			right = minHeap.poll();

			if (left == null)
				left = new Node();
			if (right == null)
				right = new Node();

			top = new Node('\0', left.freq + right.freq, false);
			top.left = left;
			top.right = right;

			minHeap.add(top);

		}

		getCodes(minHeap.peek(), "", rep);

	}

	int count = 0;  //number of leaf nodes
	

    // Recursively traverse the Huffman tree to generate codes for each character
	void getCodes(Node root, String str, int[] rep) {
		
		if (root == null)
			return;

        // If the node is a leaf, create a Code object and add it to the Hashtable

		if (root.isLeaf) {
			codes.put(root.ch, new Code(root.ch, str, rep[(int) root.ch]));
			count++;
		}

		//System.out.println("the counr is "+count);
		
        // Traverse the left and right subtrees with updated code strings
		getCodes(root.left, str + "0", rep);
		getCodes(root.right, str + "1", rep);
		getHeapTreePostOrder();
	}


	
	
	
	
	
	// Method to get the post-order traversal of the Huffman tree
	String getHeapTreePostOrder() {
	    StringBuilder result = new StringBuilder();
	    getHeapTreePostOrder(minHeap.peek(), result);
	    System.out.println(result.toString());
	    return result.toString();
	}

	private void getHeapTreePostOrder(Node root, StringBuilder result) {
	    if (root != null) {
	        getHeapTreePostOrder(root.left, result);
	        getHeapTreePostOrder(root.right, result);
	        if (root.isLeaf) {
	            // If the node is a leaf, print 1 and the character
	            result.append("1").append(root.ch).append(" ");
	        } else {
	            // If the node is not a leaf, print 0
	            result.append("0 ");
	        }
	    }
	    
	}
	
	
	
	/*
	// Method to get the in-order traversal of the Huffman tree
	String getHeapTreeInOrder() {
	    StringBuilder result = new StringBuilder();
	    getHeapTreeInOrder(minHeap.peek(), result);
	    System.out.println(result.toString());
	    return result.toString();
	}

	private void getHeapTreeInOrder(Node root, StringBuilder result) {
	    if (root != null) {
	        if (root.isLeaf) {
	            // If the node is a leaf, print 1 and the character
	            result.append("1").append(root.ch).append(" ");
	        } else {
	            // If the node is not a leaf, print 0
	            result.append("0 ");
	        }
	        getHeapTreeInOrder(root.left, result);
	        getHeapTreeInOrder(root.right, result);
	    }
	}

	*/
}


