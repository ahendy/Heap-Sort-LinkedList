/* Heap.java
   CSC 225 - Summer 2015
   Programming Assignment 3 - Template for an integer heap
   
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java Heap
	
   To conveniently test the algorithm with a large input, create a 
   text file containing space-separated integer values and run the program with
	java Heap file.txt
   where file.txt is replaced by the name of the text file.

   B. Bird - 03/26/2015
*/

import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.io.File;

//Do not change the class name.
public class Heap{

	/* ************************* DEBUGGING OPTIONS ************************* */
	/*
	   These govern the behavior of the testing code in main().
	   Some tests are slow or annoying, so you may want to disable them.
	*/
	
	/* Tests for heap properties */
	/* If these are set to true, the heap properties are checked after every 
	   insertion/deletion. If the properties do not hold, an error message is
	   printed and the program halts.
	   
	   Note that testing the heap properties is a O(n) operation, so these
	   tests can dramatically increase the running time of the program.
	*/
	   
	public static final boolean TestHeapAfterEachInsertion = false;
	public static final boolean TestHeapAfterEachDeletion = false	;
	
	
	/* Threshold for printing the tree contents */
	/* If one of the tests above fails, the entire tree will be printed if its
	   size is at most this value
	*/
	public static final int PrintTreeSizeThreshold = 10;
	/* *********************** END DEBUGGING OPTIONS *********************** */
	
	
	/* *********************** METHODS TO IMPLEMENT ************************ */
	
	
	/* Constructor */
	/* (You may not require any additional code here) */
	HeapNode root;
	int size;
	public Heap(){
		/* Your code here */
		
		root = null;
		size = 0;

			}

	
	/* removeMinimum()
	   Remove and return the minimum element of the heap, restoring the heap
	   properties as necessary. This method should have O(log n) running 
	   time.
	*/
	public int removeMin() throws HeapEmptyException{
		size = getSize();
		root = getRoot();
		HeapNode point = root;
		String binaryDirections = (Integer.toBinaryString(size));
		binaryDirections = binaryDirections.substring(1);
		int minElement = root.element;
		if (getRoot() == null)
			throw new HeapEmptyException();
		
		for(int i = 0; i<binaryDirections.length();i++){
				if(binaryDirections.charAt(i)==('1')){ //go right
						//System.out.println("right");
						point = point.rightChild;
					}else{ //go left
						//System.out.println("left");
						point = point.leftChild;
						
					}
					
		}
		
		root.element = point.element; //move last element to root
		
		if(size == 1){
			root = null;
			size--;
			return minElement;

		}

		//System.out.println("the root value is " +point.element);
		if(point.parent.rightChild!= null && point.parent.rightChild.element==point.element) 
			point.parent.rightChild = null;//unlink last element
		else if (point.parent.leftChild!= null && point.parent.leftChild.element == point.element)
			point.parent.leftChild = null;
		
	point = null;
	size--;
		
		bubbleDown();
		//System.out.println(minElement);
		return minElement;
	}

	public void bubbleDown(){
		HeapNode point = getRoot();
		int lesserElementChild = 0;
		
		


		while(lesserElementChild>-1){
			lesserElementChild = getLesserComparison(point);
			
			if(lesserElementChild == -1) break;
			

			if(lesserElementChild < point.element ){ //then we must swap elements
				
				//System.out.println("time to swap "+  point.element +" and "+lesserElementChild);
				
				int temp = point.element;
				point.element = lesserElementChild;
				lesserElementChild = temp; //put lesser element in node
				
				if(point.element == point.leftChild.element){
					point.leftChild.element = lesserElementChild;
					point = point.leftChild;
					}
				else {
					point.rightChild.element = lesserElementChild; //swap complete
					point = point.rightChild;
				}

			}else{
				return;

			

			}


		}
		
		
	}

	public int getLesserComparison(HeapNode parent){

		if(parent.leftChild ==null) return -1;
		
		else if(parent.rightChild == null || parent.leftChild.element < parent.rightChild.element) return parent.leftChild.element;

		else return parent.rightChild.element;


	}
	
	/* add(e)
	   Add the provided element to the heap, restoring heap properties as
	   necessary. This method should have O(log n) running time.
	*/
	public void add(int element){
		/* Your code here */
		HeapNode point = getRoot();
		if(size == 0){
			HeapNode h = new HeapNode(element);
			root = h;
			size++;
			//System.out.println("root");
		}

		else{
			String binaryDirections = (Integer.toBinaryString(size+1));
			binaryDirections = binaryDirections.substring(1);
				for(int i = 0; i<binaryDirections.length();i++){
				
					if(binaryDirections.charAt(i)==('1')){
						
						//System.out.println("right");
						if(point.rightChild==null){
							point.rightChild = new HeapNode(element);
							point.rightChild.parent = point;
							bubbleUp(point.rightChild);
						}else
							point = point.rightChild;
						
					
					}else{
						
						//System.out.println("left");
						if(point.leftChild==null){
							point.leftChild = new HeapNode(element);
							point.leftChild.parent = point;
							
							bubbleUp(point.leftChild);
						}else
							point = point.leftChild;
						
					}
					
				}

			size++;		

		}
			
		
	}

	public void bubbleUp(HeapNode last){
		
		
		while(last!=getRoot()){
			//System.out.println("the last element: " + last.element+ "and the parent: "+last.parent.element);
			
			if(last.parent.element > last.element ){
				//System.out.print("time fo a swap mawfuka");
				int temp = last.element;
				last.element = last.parent.element;
				last.parent.element = temp;
				last = last.parent;
			}
			else{
				//System.out.print("no way hoessay");
				
				break;

			}
		}
		
	}
	
	
	/* getSize()
	   Returns the total number of nodes in the tree.
	*/
	public int getSize(){
		/* Your code here */
		return size;
	}
	
	/* getRoot()
	   Return a pointer to the root of the heap (or null if the tree is 
	   empty).
	*/
	public HeapNode getRoot(){
		/* Your code here */
		return root;
	}
	
	
	/* ******************** END OF METHODS TO IMPLEMENT ******************** */
	
	/* HeapEmptyException
	   An exception to throw when a remove operation is executed on an
	   empty heap.
	*/
	public static class HeapEmptyException extends Exception{ }
	
	
	
	
	/* printTreeRecursive(node, leftPrefix, nodePrefix, rightPrefix)
	   Takes a pointer to a tree node and strings containing the current indentation level
	   and prints an in-order traversal of the subtree rooted at the provided node.
	*/
	private void printTreeRecursive(HeapNode node, String leftPrefix, String nodePrefix, String rightPrefix){

		if (node.leftChild == null){
			System.out.println(leftPrefix + "    |-- (no left child)");
		}else{
			printTreeRecursive(node.leftChild, leftPrefix + "     ", leftPrefix + "    |--",leftPrefix + "    |" );
			System.out.println(leftPrefix  + "    |");
		}

		System.out.println(nodePrefix + node.element);
		if (node.rightChild == null){
			System.out.println(rightPrefix + "    |-- (no right child)");
		}else{
			System.out.println(rightPrefix + "    |");
			printTreeRecursive(node.rightChild, rightPrefix + "    |", rightPrefix + "    |--",rightPrefix + "     " );
		}
	}
	
	/* printTree()
	   Prints an in-order traversal of the tree.
	*/
	public void printTree(){
		System.out.println("----------");
		HeapNode root = getRoot();
		if (root == null){
			System.out.println("Tree is empty.");
		}else{
			printTreeRecursive(root,"","","");
		}
		System.out.println("----------");
	}
	
	/* HeapNode class */
	/* Do not change anything in the class definition below */
	/* If any contents of the TreeNode class are changed, your submission will
	   not be marked. */
	/* You may create a sub-class of HeapNode if you want to add functionality. */
	public static class HeapNode{
		//Integer value contained in this node.
		int element;
		//Pointer to the parent of this node (or null if this node is the root
		//of the tree).
		public HeapNode parent;
		//Pointers so the left and right children of this node (or null if the
		//child is missing).
		public HeapNode leftChild;
		public HeapNode rightChild;
		
		public HeapNode(int element){
			this.element = element;
		}
	}
	
	/* VerifyHeap(H)
	   Tests whether the heap properties hold for the provided Heap instance.
	   If the tree is a heap, VerifyHeap returns the number of nodes found.
	   If the tree is not a heap, VerifyHeap returns -1.
	*/
	public static int VerifyHeap(Heap H){
		ArrayDeque<HeapNode> SH = new ArrayDeque<HeapNode>();
		ArrayDeque<Integer> SI = new ArrayDeque<Integer>();
		int size = H.getSize();
		HeapNode root = H.getRoot();
		if (root == null)
			return 0; //An empty tree is automatically a heap
		SH.push(root);
		SI.push(1);
		long nodeSum = 0;
		int nodesFound = 0;
		while (!SH.isEmpty()){
			HeapNode node = SH.pop();
			int idx = SI.pop();
			nodeSum += idx;
			nodesFound++;
			if (node.leftChild != null){
				if (node.element > node.leftChild.element)
					return -1; //Violation of heap property
				SH.push(node.leftChild);
				SI.push(2*idx);
			}
			if (node.rightChild != null){
				if (node.element > node.rightChild.element)
					return -1; //Violation of heap property
				SH.push(node.rightChild);
				SI.push(2*idx+1);
			}
		}
		long expectedSum = ((long)nodesFound)*(((long)nodesFound)+1)/2;
		if (nodeSum != expectedSum)
			return -1; //Not a left-complete binary tree
		
		return nodesFound;
	}
	
	/* HeapError(T, format, args)
	   Prints the provided error message, possibly printing the contents of the
	   provided tree, then exits the program.
	*/
	public static void HeapError(Heap H, String format, Object... args){
		System.out.print("ERROR: ");
		System.out.printf(format,args);
		if (H.getSize() <= PrintTreeSizeThreshold){
			System.out.printf("Tree contents:\n");
			H.printTree();
		}
		System.exit(1);
	}
	
	/* main()
	   Contains code to test the Heap implementation. Nothing in this function 
	   will be marked, and the entire method will be deleted before marking
	   begins. You are free to change the provided code to test your 
	   implementation, but only the methods above will be considered during 
	   marking.
	*/
	public static void main(String[] args){
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
		}
		Vector<Integer> insertValues = new Vector<Integer>();
		
		int v;
		while(s.hasNextInt() && (v = s.nextInt()) >= 0)
			insertValues.add(v);
		
		System.out.printf("Read %d values.\n",insertValues.size());
		
		Heap H = new Heap();
		

		long startTime, endTime;
		double totalTimeSeconds;
		
		startTime = System.currentTimeMillis();
		
		for(int i = 0; i < insertValues.size(); i++){
			int insertElement = insertValues.get(i);
			H.add(insertElement);
			if (TestHeapAfterEachInsertion){
				int verifySize = VerifyHeap(H);
				int expectedSize = i+1;
				if (verifySize == -1)
					HeapError(H,"Inserting \"%d\": Heap properties do not hold after insertion.\n",insertElement);
				if (verifySize != expectedSize)
					HeapError(H,"Inserting \"%d\": Heap contains incorrect number of nodes after insertion (%d found, %d expected).\n",insertElement,verifySize,expectedSize);
				int reportedSize = H.getSize();
				if (reportedSize != expectedSize)
					HeapError(H,"Inserting \"%d\": getSize() returns incorrect value after insertion (%d returned, %d expected).\n",insertElement,reportedSize,expectedSize);
			}
		}
		
		endTime = System.currentTimeMillis();
		
		totalTimeSeconds = (endTime-startTime)/1000.0;
		
		if (VerifyHeap(H) != insertValues.size())
			HeapError(H,"Tree is not a valid heap after all insertions.\n");
		
		System.out.printf("Inserted %d elements.\n Total Time (seconds): %.2f\n\n",insertValues.size(),totalTimeSeconds);
		
		int treeSize = H.getSize();
		
		if (treeSize != insertValues.size()){
			HeapError(H,"Heap contains the wrong number of values after all insertions (%d found, %d expected).\n",treeSize,insertValues.size());
		}
		
		if (treeSize <= PrintTreeSizeThreshold){
			System.out.printf("Tree contents:\n");
			H.printTree();
		}
		System.out.printf("Tree contains %d nodes.\n",treeSize);
		
		int previous = -1;
		int[] sortedValues = new int[treeSize];
		
		startTime = System.currentTimeMillis();
		
		for(int i = 0; i < treeSize; i++){
			int next = 0;
			try{
				next = H.removeMin();
			} catch(HeapEmptyException e){
				HeapError(H,"Heap empty exception on removal %d\n",i+1);
			}
			sortedValues[i] = next;
			if (i > 0 && next < previous)
				HeapError(H,"Deleting \"%d\": Removed element is smaller than a previous minimum.\n",next);
			previous = next;
			
			if (TestHeapAfterEachDeletion){
				int verifySize = VerifyHeap(H);
				int expectedSize = treeSize - (i+1);
				if (verifySize == -1)
					HeapError(H,"Deleting \"%d\": Heap properties do not hold after deletion.\n",next);
				if (verifySize != expectedSize)
					HeapError(H,"Deleting \"%d\": Heap contains incorrect number of nodes after deletion (%d found, %d expected).\n",next,verifySize,expectedSize);
				int reportedSize = H.getSize();
				if (reportedSize != expectedSize)
					HeapError(H,"Deleting \"%d\": getSize() returns incorrect value after deletion (%d returned, %d expected).\n",next,reportedSize,expectedSize);
			}
		}
		endTime = System.currentTimeMillis();
		
		totalTimeSeconds = (endTime-startTime)/1000.0;
		
		if (H.getRoot() != null && VerifyHeap(H) != 0)
			HeapError(H,"Tree is not a valid heap after all deletions.\n");
		System.out.printf("Deleted %d elements.\n Total Time (seconds): %.2f\n\n",treeSize,totalTimeSeconds);
		
		
		if (sortedValues.length <= 100){
			System.out.println("Sorted values:");
			for (int i = 0; i < sortedValues.length; i++)
				System.out.printf("%d ",sortedValues[i]);
			System.out.println();
		}
		
	}
}
