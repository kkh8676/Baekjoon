// DFS + Backtracking
// how can i count the combination of queen's location??
// you don't have to count the combination of queen's location

// because in first row you can count all the state space......

// N x N map : N Queen, so you have to locate a queen at each row and column
// so there is only one queen, in the row or column

// DFS(r,c) changes after row R, so we don't have to get information of columns of 1,2,...,R-1
// calling DFS(4,c) means you have 4 queens already, that's the end condition

// don't need to 'backtracking' the information

// backtracking means previous information have to be saved!
// that doesn't mean you actually save the information!!

import java.util.Scanner;
import java.util.Stack;

class boj_9663{
	static int N;

	static int[] row;
	static int[] col;

	static Stack<Integer> stack; 
	static int QUEEN;

	static int ANSWER;

	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		N = scan.nextInt();

		row = new int[N+1];
		col = new int[N+1];

		//stack = new Stack<>();
		//QUEEN = 0;

		ANSWER = 0;

		

		for (int firstCol = 1; firstCol <= N; firstCol++) {
			
			DFS(1,firstCol);
		}

		System.out.println(ANSWER);
	}

	static void DFS(int r, int c){
		// get in the row and col vertex cause it can be
		// change the information and saveState
		//System.out.println("\nDFS at "+r+" "+c);
		row[c] = r;
		col[r] = c;
		//QUEEN++;

		//printMap();
		//System.out.println("QUEEN value is "+QUEEN);
		
		// and ANSWER

		// end condition
		if(r == N){
			ANSWER++;
			//System.out.println("QUEEN has been " + N);
			//printMap();
			return;
		}

		// if Need to go, search the feasible place......
		// r+1 can larger than N?
		// i think No, because those case is conditioned by next DFS method.........
		// it means if DFS(N, c) is called, it means it can be located in last row, so QUEEN must be N

		for (int nextRow_col = 1; nextRow_col <= N; nextRow_col++) {
			// check if there is used row, col, diagonal......
			// row infomation is not needed to check because we're going to downward through row.....
			// So check the column and diagonal
			if( checkCol(r+1, nextRow_col) && checkDiag(r+1, nextRow_col) ){
				//saveState(r);

				DFS(r+1,nextRow_col);
				//System.out.println("DFS at "+(r+1)+" "+nextRow_col + " is over");

				//loadState(r);

			}// end of feasible if condition
		}// end of for loop


	}//end of DFS method

	static void saveState(int r){
		for (int time=1; time<=r; time++) {
			stack.push(col[time]);
		}
		//System.out.println("Saving QUEEN value "+QUEEN);
		//stack.push(QUEEN);

		return;
	}// end of saveState method

	static void loadState(int r){
		//QUEEN = stack.pop();
		//System.out.println("Loading QUEEN value "+QUEEN);
		for (int rowIndex=r; rowIndex>=1; rowIndex--) {
			col[rowIndex] = stack.pop();
		}
		return;
	}// end of loadState method

	static boolean checkCol(int r, int nextRow_col){

		boolean result = true;
		for (int rowNum = 1; rowNum < r; rowNum++) {
			if(col[rowNum] == nextRow_col){
				result = false;
				break;
			}
		}
		return result;
	} // end of check Column method

	static boolean checkDiag(int r, int nextRow_col){

		int diff = r - nextRow_col;
		int sum  = r + nextRow_col;

		boolean result = true;

		for (int rowNum=1; rowNum < r; rowNum++) {
			if(col[rowNum] + rowNum == sum || rowNum - col[rowNum] == diff){
				result = false;
				break;
			}
		}

		return result;
	} // end of check diagonal method

	static void printMap(){
		String[][] map = new String[N+1][N+1];

		for (int rowNum=1; rowNum <= N; rowNum++) {
			int colNum = col[rowNum];
			map[rowNum][colNum] = "O";
		}

		for (int rowNum=1; rowNum<=N; rowNum++) {
			for (int colNum=1; colNum<=N; colNum++) {
				if(map[rowNum][colNum] == "O") System.out.print(map[rowNum][colNum] + " ");
				else                           System.out.print("X" + " ");
			}
			System.out.println();
		}
	}
}
