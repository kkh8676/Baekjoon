import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

class boj_17825_v3{
	static int[] dice;

	static int[] map = {0,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40,0, // 0 to 21
						13,16,19,25,30,35,40,0, // 8 element 22 to 29
						22,24,25,30,35,40,0, // 7 element 30 to 36 
						28,27,26,25,30,35,40,0, // 8 element 37 to 44 
						};

	static int[] full;

	static int[] p;
	static int[] a;

	static int SCORE;
	static int ANSWER;
	static int final_ROUND;

	static Stack<Integer> stack ;
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);

		dice = new int[11];

		for (int i=1; i<=10; i++) {
			dice[i] = scan.nextInt();
		} // end of getting dice information

		full = new int[45];
		p = new int[5];
		a = new int[5];

		Arrays.fill(full,0);
		Arrays.fill(p,0);
		Arrays.fill(a,0);

		SCORE = 0;
		ANSWER = Integer.MIN_VALUE;
		stack = new Stack<>();

		DFS(1,1);

		// for (int index=0; index<45; index++) {
		// 	System.out.println("current before location is "+index);
		// 	for (int i=1; i<6; i++) {
		// 		System.out.println("With dice number "+i+" after location is "+move_(index,i));
		// 	}
		// }
		System.out.println(ANSWER);
		
		
	} // end of main method

	public static void DFS(int pNum, int round){
		int prev = p[pNum];
		int next = move(pNum,round);

		SCORE = SCORE + map[next];
		p[pNum] = next;
		full[next] = pNum;
		full[prev] = 0;

		if(next == 21 || next == 29 || next == 36 || next == 44) a[pNum] = 1;

		// ending condition
		if(round == 10){
			// update ANSWER and return
			final_ROUND = 10;
			
			if(SCORE > ANSWER){
				ANSWER = SCORE;
				//printAry(full);
				//System.out.println(SCORE);
			} else{
				ANSWER = ANSWER;
			}
			return;
		}

		// which player to move for loop and next round is round + 1
		for (int nextPlayer = 1; nextPlayer <=4 ; nextPlayer++) {
			// check nextPlayer's next Position is possible or not 
			boolean feas = check(nextPlayer, round+1);

			// if not feasible then continue;
			// if possible go to DFS
			if(feas){


				// Should I use the stack for backtracking process???
				int beforeDFSscore = SCORE;
				int beforeDFSplayerIndex = p[nextPlayer];
				int afterDFSplayerIndex = move(nextPlayer, round+1);
				int currentPrevState = full[beforeDFSplayerIndex];
				int currentNextState = full[afterDFSplayerIndex];
				// stack.push(beforeDFSscore);
				// stack.push(beforeDFSplayerIndex);
				// stack.push(afterDFSplayerIndex);
				// stack.push(currentPrevState);
				// stack.push(currentNextState);

				// feas means that player is not arrived yet

				//System.out.println("Moving player "+ nextPlayer + " round "+ (round+1) + " before Loc " + beforeDFSplayerIndex+ " to Loc "+afterDFSplayerIndex);
				DFS(nextPlayer, round+1);

				// int prevNextState = stack.pop();
				// int prevPrevState = stack.pop();
				// int prevAfterDFSPlayerIndex = stack.pop();
				// int prevBeforeDFSPlayerIndex = stack.pop();
				// int prevDFSscore = stack.pop();

				SCORE = beforeDFSscore;
				p[nextPlayer] = beforeDFSplayerIndex;
				full[beforeDFSplayerIndex] = currentPrevState;
				full[afterDFSplayerIndex] = currentNextState;
				a[nextPlayer] = 0;

			}
		}

	} // end of DFS method

	// check if player pNum move with dice[round], there isn't player 
	public static boolean check(int pNum, int round){
		
		if(a[pNum] == 1) return false; // pNum is already arrived, you can't move the player

		int nexti = move(pNum, round);
		if(nexti == 21 || nexti == 29 || nexti == 36 || nexti == 44) return true;

		if(nexti == 25 || nexti == 32 || nexti == 40 ){
			if(full[25] != 0 || full[32] != 0 || full[40] != 0) return false;
		}
		if(nexti == 26 || nexti == 33 || nexti == 41){
			if(full[26] != 0 || full[33] != 0 || full[41] != 0) return false;
		}
		if(nexti == 27 || nexti == 34 || nexti == 42){
			if(full[27] != 0 || full[34] != 0 || full[42] != 0) return false;
		}
		if(nexti == 20 || nexti == 28 || nexti == 35 || nexti == 43){
			if(full[20] != 0 || full[28] != 0 || full[35] != 0 || full[43] != 0) return false;
		}

		if(full[nexti] != 0) return false;

		return true;

	} // end of check method

	// return index number of moved pNum's index with dice[round]
	public static int move(int pNum, int round){

		// current index of player pNum
		int cI = p[pNum];
		// moving how many
		int moving = dice[round];
		int mI;
		if(cI == 21 || cI == 29 || cI == 36 || cI == 44) return cI;

		if(cI == 5){
			// jump to index 22
			mI = 21 + moving;
			
		} else if(cI== 10) {
			// jump to index 30
			mI = 29 + moving;
			
		} else if(cI == 15){
			// jump to index 37
			mI = 36 + moving;
			
		} else{
			// move simply
			mI = cI + moving;
			
			// ver 1 0 to 21
			// ver 2 22 to 29
			// ver 3 30 to 36
			// ver 4 37 to 44
			if(cI < 21 && mI > 21)  mI = 21;
			if(cI >= 22 && cI < 29 && mI >= 29) mI = 29;
			if(cI >= 30 && cI < 36 && mI >= 36)  mI = 36;
			if(cI >= 37 && cI < 44 && mI >= 44) mI = 44;
		} // end of getting mI

		return mI;

	} // end of move method

		public static int move_(int cI, int moving){

		// current index of player pNum
		
		// moving how many
		
		int mI;
		if(cI == 21 || cI == 29 || cI == 36 || cI == 44) return cI;

		if(cI == 5){
			// jump to index 22
			mI = 21 + moving;
			
		} else if(cI== 10) {
			// jump to index 30
			mI = 29 + moving;
			
		} else if(cI == 15){
			// jump to index 37
			mI = 36 + moving;
			
		} else{
			// move simply
			mI = cI + moving;
			
			// ver 1 0 to 21
			// ver 2 22 to 29
			// ver 3 30 to 36
			// ver 4 37 to 44
			if(cI < 21 && mI > 21)  mI = 21;
			if(cI >= 22 && cI < 29 && mI >= 29) mI = 29;
			if(cI >= 30 && cI < 36 && mI >= 36)  mI = 36;
			if(cI >= 37 && cI < 44 && mI >= 44) mI = 44;
		} // end of getting mI

		return mI;

	} // end of move method

	public static void printAry(int[] ary){
		for (int i=0; i<ary.length; i++) {
			if(ary[i] != 0){
				System.out.print(" Index " + i + " player "+ ary[i]);
			}
		}
		System.out.println();
	}
}// end of class 