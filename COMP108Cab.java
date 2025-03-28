//
// Coded by Prudence Wong 2021-03-06
// Updated 2023-02-25
//
// Note: You are allowed to add additional methods if you need.
// Name:  Basit Adedeji
// Student ID: 201764845
//
// Time Complexity and explanation: 
// f denotes initial cabinet size
// n denotes the total number of requests 
// d denotes number of distinct requests
// You can use any of the above notations or define additional notation as you wish.
// 
// appendIfMiss(): in the worst case scenario the cabinet contains all of the distinct requests and and so the time complexity would be O(d to the n) but 
//the while loop would run for all d elements for each request element, alternatively the cabinet could contain more elements than the distinct
// 	request elements and so the time complexity would then be O (p to the n)
// 
// freqCount():  in the worst case scenario the cabinet contains every distinct request 
//and in the worst case scenario the element at the tail is moved to the  head for every element in the cabinet
// the first scenario has a time compelxity of (d to the n) due to the while loop running for all d eleemts for each n element
//and the second scenario has a complexity of (d to the d) due to the while loop running for all d elements each time a swap is done for each d element
// giving a total time complexity of O(d to the n + d to the d)
import java.util.ArrayList;
class COMP108Cab {

	public COMP108Node head, tail;
	
	public COMP108Cab() {
		head = null;
		tail = null;
	}

	// append to end of list when miss
	public COMP108CabOutput appendIfMiss(int rArray[], int rSize) {
		COMP108CabOutput output = new COMP108CabOutput(rSize);
		for (int i = 0; i <rSize; i++){
			COMP108Node curr = head;
			boolean found = false;
			int count = 0;
			while(curr!= null && found == false){
				count++;
				output.compare[i]++;
				if (curr.data == rArray[i]){
					found = true;
					output.hitCount++;
				}else{
					curr = curr.next;
				}
			}
			if (found == false){
				output.missCount++;
				output.compare[i]=count;
				COMP108Node burr = new COMP108Node(rArray[i]);
				insertTail(burr);
		    }
		}

		output.cabFromHead = headToTail();
		output.cabFromTail = tailToHead();
		return output;
	}

	// move the file requested so that order is by non-increasing frequency
	public COMP108CabOutput freqCount(int rArray[], int rSize) {
		COMP108CabOutput output = new COMP108CabOutput(rSize);
		for (int i = 0; i<rSize; i++){
			COMP108Node curr = head;
			boolean found = false;
			int count = 0;
			while(curr!= null && found == false){
				count++;
				output.compare[i]++;
				if (curr.data == rArray[i]){
					found = true;
					output.hitCount++;
					curr.freq++;
					while (curr!=head){
						if (curr.freq>curr.prev.freq){
							int tmp = curr.prev.data;
							int tmpF = curr.prev.freq;
							curr.prev.data = curr.data;
							curr.prev.freq = curr.freq;
							curr.data = tmp;
							curr.freq = tmpF;
							curr = curr.prev;
						}else{
						    curr=head;
						}}
				}else{
					curr = curr.next;
				}}
			if (found == false){
				output.missCount++;
				output.compare[i]=count;
				COMP108Node furr = new COMP108Node(rArray[i]);
				insertTail(furr);
			}
		}
		output.cabFromHead = headToTail();
		output.cabFromTail = tailToHead();
		output.cabFromHeadFreq = headToTailFreq();
		return output;		
	}	

	// DO NOT change this method
	// insert newNode to head of list
	public void insertHead(COMP108Node newNode) {		

		newNode.next = head;
		newNode.prev = null;
		if (head == null)
			tail = newNode;
		else
			head.prev = newNode;
		head = newNode;
	}

	// DO NOT change this method
	// insert newNode to tail of list
	public void insertTail(COMP108Node newNode) {

		newNode.next = null;
		newNode.prev = tail;
		if (tail != null)
			tail.next = newNode;
		else head = newNode;
		tail = newNode;
	}

	// DO NOT change this method
	// delete the node at the head of the linked list
	public COMP108Node deleteHead() {
		COMP108Node curr;

		curr = head;
		if (curr != null) {
			head = head.next;
			if (head == null)
				tail = null;
			else
				head.prev = null;
		}
		return curr;
	}
	
	// DO NOT change this method
	// empty the cabinet by repeatedly removing head from the list
	public void emptyCab() {
		while (head != null)
			deleteHead();
	}


	// DO NOT change this method
	// this will turn the list into a String from head to tail
	// Only to be used for output, do not use it to manipulate the list
	public String headToTail() {
		COMP108Node curr;
		String outString="";
		
		curr = head;
		while (curr != null) {
			outString += curr.data;
			outString += ",";
			curr = curr.next;
		}
		return outString;
	}

	// DO NOT change this method
	// this will turn the list into a String from tail to head
	// Only to be used for output, do not use it to manipulate the list
	public String tailToHead() {
		COMP108Node curr;
		String outString="";
		
		curr = tail;
		while (curr != null) {
			outString += curr.data;
			outString += ",";
			curr = curr.prev;
		}
		return outString;
	}

	// DO NOT change this method
	// this will turn the frequency of the list nodes into a String from head to tail
	// Only to be used for output, do not use it to manipulate the list
	public String headToTailFreq() {
		COMP108Node curr;
		String outString="";
		
		curr = head;
		while (curr != null) {
			outString += curr.freq;
			outString += ",";
			curr = curr.next;
		}
		return outString;
	}

}
