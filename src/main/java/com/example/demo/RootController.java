package com.example.demo;

import java.util.ArrayList;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class RootController {

	@CrossOrigin(origins = "*")
	@RequestMapping("/main")
	String pickBest(@RequestParam("data") String islands) {
		String[] islandStr = islands.split(",");
		int[] islandsArray = new int[islandStr.length];//new Array[islandStr.length];
		int index = 0;
		for(String str: islandStr) {
			islandsArray[index++] = Integer.parseInt(str);
		}

		ArrayList<Integer> myBucket = new ArrayList<Integer>();
		ArrayList<Integer> hisBucket = new ArrayList<Integer>();
		int num = getTheBest(islandsArray, true, myBucket, hisBucket);
		
		
		
        return ""+num;
    }
	
	public int getTheBest(int[] islands, boolean myTurn, ArrayList<Integer> myBucket, ArrayList<Integer> hisBucket) {
		int index = -1;
		if(islands.length == 1){
			index = 0;
		} else {
			int myLeft = 0;
			int myRight = 0;
			int myLeftIndex = 0;
			int myRightIndex = 0;
			int hisLeftIndex = 0;
			int hisRightIndex = 0;
			int hisLeft = 0;
			int hisRight = 0;
			ArrayList<Integer> myT1, myT2, hisT1, hisT2 = new ArrayList<Integer>();
			int returnedNum = -1;
			if(myTurn) {
				if(islands.length == 2) {
					if(islands[0]> islands[1]) {
						myBucket.add(islands[0]);
						hisBucket.add(islands[1]);
						returnedNum =  islands[0];
						index = 0;
					}else {
						myBucket.add(islands[1]);
						hisBucket.add(islands[0]);
						returnedNum = islands[1];
						index = 1;
					}
				}
				//my turn with recursion
				else {
					myLeft = islands[0];
					myT1 = (ArrayList<Integer>)myBucket.clone();
					hisT1 = (ArrayList<Integer>)hisBucket.clone();
					hisLeftIndex = getTheBest(removeLeft(islands), !myTurn, myT1, hisT1);
					hisLeft = removeLeft(islands)[hisLeftIndex];
					
					int x1, y1, x2, y2=0;
					x1= myLeft+sumOfBucket(myT1);
					y1=sumOfBucket(hisT1);
					//3,4
					
					myRight = islands[islands.length-1];
					myT2 = (ArrayList<Integer>)myBucket.clone();
					hisT2 = (ArrayList<Integer>)hisBucket.clone();
					hisRightIndex = getTheBest(removeRight(islands), !myTurn, myT2, hisT2);
					hisRight = removeRight(islands)[hisRightIndex];
					
					x2= myRight+sumOfBucket(myT2);
					y2=sumOfBucket(hisT2);
					
					//5,2
					if(x1>y1 && x2>y2) {
						if(x1>x2) {
							myBucket.addAll(myT1);
							hisBucket.addAll(hisT1);
							myBucket.add(myLeft);
							index = 0;
							return index;//myLeft;//x1;
						}else {
							myBucket.addAll(myT2);
							hisBucket.addAll(hisT2);
							myBucket.add(myRight);
							index = islands.length-1;
							return index;//myRight;//x2;
						}
					}else if(x1>y1 && x2<y2) {
						myBucket.addAll(myT1);
						hisBucket.addAll(hisT1);
						myBucket.add(myLeft);
						index = 0;
						return index;//myLeft;//x1;
					}else if(x1<y1 && x2>y2) {
						myBucket.addAll(myT2);
						hisBucket.addAll(hisT2);
						myBucket.add(myRight);
						index = islands.length-1;
						return index;//myRight;//x2;
					}else if(x1<y1 && x2<y2) {
						if(x1>x2) {
							myBucket.addAll(myT1);
							hisBucket.addAll(hisT1);
							myBucket.add(myLeft);
							index = 0;
							return index;//myLeft;//x1;
						}else {
							myBucket.addAll(myT2);
							hisBucket.addAll(hisT2);
							myBucket.add(myRight);
							index = islands.length-1;
							return index;//myRight;//x2;
						}
					}else {
						myBucket.addAll(myT1);
						hisBucket.addAll(hisT1);
						myBucket.add(myLeft);
						index = 0;
						return index; //x1; //perhaps equal case
					}
				}
			}
			////////////////////// not my turn
			else {
				if(islands.length == 2) {
					if(islands[0]> islands[1]) {
						hisBucket.add(islands[0]);
						myBucket.add(islands[1]);
						returnedNum = islands[0];
						index = 0;
					}else {
						hisBucket.add(islands[1]);
						myBucket.add(islands[0]);
						returnedNum = islands[1];
						index = 1;
					}
				}
				//not my turn with recursion
				else {
					hisLeft = islands[0];
					myT1 = (ArrayList<Integer>)myBucket.clone();
					hisT1 = (ArrayList<Integer>)hisBucket.clone();
					
					myLeftIndex = getTheBest(removeLeft(islands), !myTurn, myT1, hisT1);
					myLeft = removeLeft(islands)[myLeftIndex];
							
					int x1, y1, x2, y2=0;
					x1= hisLeft+sumOfBucket(hisT1);
					y1=sumOfBucket(myT1);
					//3,4
					hisRight = islands[islands.length-1];
	
					myT2 = (ArrayList<Integer>)myBucket.clone();
					hisT2 = (ArrayList<Integer>)hisBucket.clone();
					myRightIndex = getTheBest(removeRight(islands), !myTurn, myT2, hisT2);
					myRight = removeRight(islands)[myRightIndex];
					
					x2= hisRight+sumOfBucket(hisT2);
					y2=sumOfBucket(myT2);
					
					//5,2
					if(x1>y1 && x2>y2) {
						if(x1>x2) {
							hisBucket.addAll(hisT1);
							myBucket.addAll(myT1);
							hisBucket.add(hisLeft);
							index = 0;
							return index;//hisLeft;//x1;
						}else {
							hisBucket.addAll(hisT2);
							myBucket.addAll(myT2);
							hisBucket.add(hisRight);
							index = islands.length-1;
							return index;//hisRight;//x2;
						}
					}else if(x1>y1 && x2<y2) {
						hisBucket.addAll(hisT1);
						myBucket.addAll(myT1);
						hisBucket.add(hisLeft);
						index = 0;
						return index;//hisLeft;//x1;
					}else if(x1<y1 && x2>y2) {
						hisBucket.addAll(hisT2);
						myBucket.addAll(myT2);
						hisBucket.add(hisRight);
						index = islands.length-1;
						return index;//hisRight;//x2;
					}else if(x1<y1 && x2<y2) {
						if(x1>x2) {
							hisBucket.addAll(hisT1);
							myBucket.addAll(myT1);
							hisBucket.add(hisLeft);
							index = 0;
							return index;//hisLeft;//x1;
						}else {
							hisBucket.addAll(hisT2);
							myBucket.addAll(myT2);
							hisBucket.add(hisRight);
							index = islands.length-1;
							return index;//hisRight;//x2;
						}
					}else {
						hisBucket.addAll(hisT1);
						myBucket.addAll(myT1);
						hisBucket.add(hisLeft);
						index = 0;
						return index;//x1; //perhaps equal case
					}
				}
			}
		}
		return index;
	}
	
	public int[] removeLeft(int[] islands) {
		int[] a = new int[islands.length-1];
		for(int i=0; i<islands.length-1; i++) {
			a[i] = islands[i+1];
		}
		return a;
		
	}

	public int[] removeRight(int[] islands) {
		int[] a = new int[islands.length-1];
		for(int i=0; i<islands.length-1; i++) {
			a[i] = islands[i];
		}
		return a;
		
	}


	public int sumOfBucket(ArrayList<Integer> islands) {
		int a = 0;
		for(int i: islands) {
			a += i;
		}
		return a;
		
	}

}
