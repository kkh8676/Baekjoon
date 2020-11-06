/*

Exhaustive Search problem ; Depth First Search
Stack can get the Integer[] as the generic
We should mind which information is saved in Stack in DFS using Stack

fish location and Sea and eat information is saved before the DFS method.......
shark location information can be specificated in the 'sea' array


*/
import java.util.Scanner;
import java.lang.Math;
import java.util.*;

class boj_19236_v4{
	static int[][] sea;
	static int[] fishR;
	static int[] fishC;
	static int[] fishD;
	static int[] isEaten;

	static final int EATEN = 1;
	static final int ALIVE = -1;
	static final int SHARK = 0;

	static int[] dr = {0,-1,-1,0,1,1,1,0,-1};
	static int[] dc = {0,0,-1,-1,-1,0,1,1,1};

	static int ANSWER;
	static int eat;

	static Stack<Integer[]> forInfo;
	static Stack<Integer[][]> seaStack;
	static Stack<Integer[]> locStack;
	static Stack<Integer> eatStack;

	static String[] cursor = {"","\u2191","\u2196","\u2190","\u2199","\u2193","\u2198","\u2192","\u2197"};

	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);


		sea = new int[5][5];
		fishR = new int[17];
		fishC = new int[17];
		fishD = new int[17];
		isEaten = new int[17];
		
		seaStack = new Stack<>();
		locStack = new Stack<>();
		eatStack = new Stack<>();

		for (int r=1; r<5; r++) {
			for (int c=1; c<5; c++) {
				int fishNum = scan.nextInt();
				int dir = scan.nextInt();

				sea[r][c] = fishNum;
				fishR[fishNum] = r;
				fishC[fishNum] = c;
				fishD[fishNum] = dir;

				isEaten[fishNum] = ALIVE;

			}
		}// end of getting information

		eat = 0;
		ANSWER = Integer.MIN_VALUE;
		DFS(1,1);

		System.out.println(eatStack.empty());
		System.out.println(ANSWER);


	}// end of main method


	public static void fishGo(){
		// fish moves 
		for (int num=1; num<17; num++) {
			// num : moving fish number
			int cR = fishR[num];
			int cC = fishC[num];
			int cD = fishD[num];

			if(isEaten[num] == EATEN)  continue;
			for (int p=0; p<8; p++) {
				
				if(cR + dr[cD] < 1 || cR + dr[cD] > 4 ||  cC + dc[cD] < 1 || cC + dc[cD] > 4) { cD = cD%8 + 1 ; continue;}

				if(cR + dr[cD] == fishR[SHARK] && cC + dc[cD] == fishC[SHARK]) { cD = cD%8 + 1 ; continue;}

				fishSwap(cR,cC,cR + dr[cD],cC + dc[cD]);
				fishD[num] = cD;

				
				break;
			}
			//System.out.println("num " + num +" has moved!");
			//printInfo();

		}// fish num for loop over
	}// end of fishGO method

	public static void DFS(int r, int c){
		//System.out.println("eating " + sea[r][c]+"\n");
		System.out.println("DFS R"+r+" C"+c);
		int beingEaten = sea[r][c];
		//sea[r][c] = SHARK;
		int sR = fishR[SHARK] = r;
		int sC = fishC[SHARK] = c;
		int sD = fishD[SHARK] = fishD[beingEaten];
		

		//System.out.println(eat);
		

		Integer[][] copiedSea = new Integer[5][5];
		Integer[] copiedFishR = new Integer[17];
		Integer[] copiedFishC = new Integer[17];
		Integer[] copiedFishD = new Integer[17];
		Integer[] copiedIsEaten = new Integer[17];
		Integer copiedEat = eat;

		System.out.println("Before eating "+eat);
		saveState(copiedSea, copiedFishR, copiedFishC, copiedFishD, copiedIsEaten, copiedEat); // information before the DFS method......
		eat = eat + beingEaten;
		isEaten[beingEaten] = EATEN;
		ANSWER = eat > ANSWER ? eat : ANSWER;
		
		System.out.println("After eating "+eat);
		//System.out.println("before fish go");
		//printInfo();

		fishGo();
		//System.out.println("After fish go");;
		//printInfo();

		
		//System.out.println();


		for (int p=1; p<4; p++) {
			// if there is a limit continue
			if(sR + dr[sD]*p < 1 || sR + dr[sD]*p > 4 || sC + dc[sD]*p < 1 || sC + dc[sD]*p > 4) continue;

			// if there is no fish 
			if(isEaten[ sea[ sR + dr[sD]*p ][ sC + dc[sD]*p ] ] == EATEN) continue;


			//System.out.println("Before DFS method");
			//printInfo();

			DFS(sR + dr[sD]*p, sC + dc[sD]*p);
			//ANSWER = eat > ANSWER ? eat : ANSWER;
			//System.out.println("ANSWER is "+ ANSWER);
			System.out.println("load to R"+r+" C"+c);
			loadState();
			System.out.println("After load "+eat);
			
		}
		//System.out.println("ANSWER is "+ ANSWER);

		



	}// end of DFS method

	public static void fishSwap(int r, int c, int tR, int tC){

		int movingFishNum = sea[r][c];
		int movedFishNum = sea[tR][tC];

		// sea change
		sea[r][c] = movedFishNum;
		sea[tR][tC] = movingFishNum;

		// row change
		fishR[movingFishNum] = tR;
		fishC[movingFishNum] = tC;

		// column change
		fishR[movedFishNum] = r;
		fishC[movedFishNum] = c;

		// eaten information change
		// int tempEaten = isEaten[movingFishNum];
		// isEaten[movingFishNum] = isEaten[movedFishNum];
		// isEaten[movedFishNum] = tempEaten;

		// direcion information is same 

	}// end of fishSwap method

	public static void saveState(Integer[][] copiedSea, Integer[] copiedFishR, Integer[] copiedFishC, Integer[] copiedFishD, Integer[] copiedIsEaten, Integer copiedEat){
		System.out.println(copiedEat + " has pushed in the eat stack!");
		for (int r=0; r<5; r++) {
			for (int c=0; c<5; c++) {
				copiedSea[r][c] = sea[r][c];
			}// end of inner for loop
		}// end of outer for loop

		for(int num=0; num<17; num++){
			copiedFishR[num] = fishR[num];
			copiedFishC[num] = fishC[num];
			copiedFishD[num] = fishD[num];
			copiedIsEaten[num] = isEaten[num];
		}

		seaStack.push(copiedSea);
		locStack.push(copiedFishR);
		locStack.push(copiedFishC);
		locStack.push(copiedFishD);
		locStack.push(copiedIsEaten);
		eatStack.push(eat);
	}// end of save State method

	public static void loadState(){
		Integer copiedEat = eatStack.pop();
		System.out.println(copiedEat+ " has poped in the Stack!");
		Integer[] copiedIsEaten = locStack.pop();
		Integer[] copiedFishD = locStack.pop();
		Integer[] copiedFishC = locStack.pop();
		Integer[] copiedFishR = locStack.pop();
		Integer[][] copiedSea = seaStack.pop();
		for (int r=0; r<5; r++) {
			for (int c=0; c<5; c++) {
				sea[r][c] = copiedSea[r][c];
			}// end of inner for loop
		}// end of outer for loop

		for(int num=0; num<17; num++){
			fishR[num] = copiedFishR[num];
			fishC[num] = copiedFishC[num];
			fishD[num] = copiedFishD[num];
			isEaten[num] = copiedIsEaten[num];
		}

		eat = copiedEat;
	} // end of loadState method


	public static void printInfo(){
		//System.out.println();
		for (int row=1; row<5; row++) {
			for (int col=1; col<5; col++) {
				System.out.print(sea[row][col] + " " + cursor[ fishD[sea[row][col]] ]+ " ");
			}

			System.out.println();
		}
		System.out.println();
	}
}