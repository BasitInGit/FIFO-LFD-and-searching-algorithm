// Coded by Prudence Wong 2021-12-29
// Updated 2023-02-26
// Updated 2023-03-03
//
// NOTE: You are allowed to add additional methods if you need.
//
// Name: Basit Adedeji
// Student ID: 201764845
//
// Time Complexity and explanation: You can use the following variables for easier reference.
// n denotes the number of requests, p denotes the size of the cache
// n and p can be different and there is no assumption which one is larger
//
// evictFIFO(): time complexity is O(p to the power of n) - in the worst case scenario the while loop would run through all the elments of the 
//cache each time to find a matching value in the request and this would be repeated for every elemnt in the request array
// evictLFD():
//The method initialpos has a O(n to the power of p) in the worst scenario due to the while loop of request elements repeating for every cache elemnt
//
//in the worst case scenario the elemPosUpdate method is called O(n) times because the cache has no value in the request array and so each new added value
//has to be updated once alternatively the request array could be a complete match with the cache in which case the time complexity would still be O(n),
//
//in the worst case scenario the latest method is called O(n) times because the cache has no values matching the request elemnts and the latest has to be checked
//each time there's a miss
// The time complexity of evict LFD itslef would be O(p to the n) due to the nested loop of p under n
// making the total time complexity O(n to the p  +  p to the n + 2n)
class COMP108Paging {



	// evictFIFO
	// Aim: 
	// if a request is not in cache, evict the page present in cache for longest time
	// count number of hit and number of miss, and find the hit-miss pattern; return an object COMP108PagingOutput
	// Input:
	// cArray is an array containing the cache with cSize entries
	// rArray is an array containing the requeset sequence with rSize entries
	static COMP108PagingOutput evictFIFO(int[] cArray, int cSize, int[] rArray, int rSize) {
		COMP108PagingOutput output = new COMP108PagingOutput();
		int count = 0;                 //stores the position of the oldest value in cache, starts with 0 for position 1
		for (int i= 0; i < rSize; i++){   //loops through request values;
			int j = 0;
			boolean found = false;
			while ((j < cSize )&&(found == false)){ //loops through cache values until it matches current request value
				if (rArray[i] == cArray[j]){   
					found = true;
					output.hitCount++;          //updates hitcount each time request matches cache
					output.hitPattern +="h";     //updates hitpattern each time request matches cache
				}
				j++;
			}
			if (found == false){
				output.missCount++;          
				output.hitPattern +="m";
				cArray[count]=rArray[i];            //replaces the cache at the oldest position with the not found request value
				count ++;                           //updates new oldest value
				count = count % cSize;               // ensures the oldest value position stays within cache range
			}
		}
		output.cache = arrayToString(cArray, cSize);
		return output;
	}
	// evictLFD
	// Aim:
	// if a request is not in cache, evict the number whose next request is the latest
	// count number of hit and number of miss, and find the hit-miss pattern; return an object COMP108PagingOutput
	// Input:
	// cArray is an array containing the cache with cSize entries
	// rArray is an array containing the requeset sequence with rSize entries
	static COMP108PagingOutput evictLFD(int[] cArray, int cSize, int[] rArray, int rSize) {
		COMP108PagingOutput output = new COMP108PagingOutput();
		int [] elem = COMP108Paging.initialPos(cArray,cSize,rArray,rSize);          //array storing intial request postion of cache values
		for (int i = 0; i<rSize; i++){                          //loops for every request value
			int j = 0;
			boolean found = false;
			while ((j<cSize)&& found ==false){                   //loops for every cache value until it matches current request value
				if (cArray[j]== rArray[i]){
					found = true;
					output.hitCount++;
					output.hitPattern +="h";
					int []newElem1 = COMP108Paging.elemPosUpdate(i,rArray, rSize, elem, j);  //when match is found, the next request sequence of the value is updated in the position array
					elem = newElem1;
				}else{
					j++;
				}
			}
			if (found == false){
				output.missCount++;
				output.hitPattern +="m";
				int latest_elem_pos = COMP108Paging.latest(elem,cSize);  //calls method to find element with the latest request sequence and register it's position in cache
				cArray[latest_elem_pos]= rArray[i];                    //swaps the element with the latest next sequence to the unfound request value
				int []newElem = COMP108Paging.elemPosUpdate(i,rArray, rSize, elem, latest_elem_pos);  //assigns the next sequence position of the newly added value
				elem = newElem;
			}
		}
		output.cache = arrayToString(cArray, cSize);
		return output;
	}

	public static int [] initialPos (int[] cArray, int cSize, int[] rArray, int rSize){    //method to create an array of cache value's intial positions in request sequence
		int [] elem = new int[cSize];   
		for (int i =0; i<cSize; i++){         //loops for every value in the cache
			boolean found = false;
			int j = 0;
			while ((j <rSize)&& found == false){           //loops for every request value until the current cache value matches with the request value
				if (rArray[j] == cArray[i]){
					found = true;
					elem[i] = j;                            //stores the position of the request value when it matches the cache value
				}else{
					j++;
				}}
			if (found == false){
				elem[i] = 1000;                              //infinity represented as 1000
			}}
		return elem;

	}
	public static int latest(int[]elem, int elemSize){ //method to find the latest sequence in the cache aswell as it's cache position which lines up with the position array elem
		int max = elem[0];   // starting maximum value is next sequence of cache index(0)
		int pos = 0;
		for(int i = 1; i <elemSize; i++){
			if (elem[i]>max){   //compares max with the other cache elements next sequence values
				max = elem[i];     //updates max when a larger next sequence value is found
				pos = i;             //takes the position of the new larger value
			}
		}
		return pos;
	}
	public static int [] elemPosUpdate (int i,int [] rArray, int rSize, int [] elem, int new_elem_pos){ //method to update the next request position when there's a hit
		if (i< rSize-1){                          //checks if the value found before call to method is the last request value
			int k = i+1;                            // search begins from the position next to the found values current position
			boolean seen = false;
			while ((k<rSize)&& seen == false){          // loops through request cache until the found value is seen again
				if (rArray[k]== rArray[i]){
					seen = true;
					elem[new_elem_pos] = k;                //when value is found, the new position replaces the old;
				}else{
					k++;
				}
			}
			if (seen == false){                               //if value is not found again// position is replaced with infinity (1000)
				elem[new_elem_pos] =1000;
			}
		}else {
			elem[new_elem_pos] =1000;                  
		}
		return elem;
	}

	// DO NOT change this method
	// this will turn the cache into a String
	// Only to be used for output, do not use it to manipulate the cache
	static String arrayToString(int[] array, int size) {
		String outString="";
		
		for (int i=0; i<size; i++) {
			outString += array[i];
			outString += ",";
		}
		return outString;
	}

}

