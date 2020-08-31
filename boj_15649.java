// basically same with boj_19236, backtracking problem
// how can i backtracking??
// check the smooth version of backtracking problem

// DFS + backtracking.....

// number of calling saving states should be same with number of calling loading states!

// Draw the graph, so specifying what information is saved in which way!

import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

class boj_15649{
	static int N;
	static int M;
	static int[][] map;
	static Boolean[] checked;

	static String ANSWER;
	static int answerLen;

	static Stack<String> strStack;
	static Stack<Boolean[]> checkStack;
	static Stack<Integer> lenStack;

	static Queue<Integer> answerQ;

	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);

		N = scan.nextInt();
		M = scan.nextInt();

		map = new int[N+1][N+1];
		checked = new Boolean[N+1];

		answerLen = 0;

		strStack = new Stack<>();
		checkStack = new Stack<>();
		lenStack = new Stack<>();

		

		for (int r=0; r<N+1; r++) {
			for (int c=0; c<N+1; c++) {
				if(r==c) map[r][c] = 0;
				else map[r][c] = 1;
			}
		} // end of outer for loop

		for (int idx=0; idx<checked.length; idx++) {
			checked[idx] = false;
		}

		ANSWER = new String();

		for (int n=1; n<N+1; n++) {
			saveState();
			checked[n] = true;

			DFS(n);

			loadState();
			checked[n] = false;
		}
	} // end of main method

	public static void DFS(int n){
		// came in here......
		// change the information
		checked[n] = true;
		ANSWER = new String(ANSWER);
		ANSWER = ANSWER + n + " ";
		

		// if stopping criteria
		if(answerLen == M){
			System.out.println(ANSWER);
			
			return;
		}
		

		for (int edge=1; edge<N+1; edge++) {
			if(map[n][edge]==1 && !checked[edge]){
				// saving States!!

				saveState();

				DFS(edge);

				// loading the Staes!!
				loadState();
				checked[edge] = false;
			}
		}
	} // end of DFS method

	 public static void saveState(){
	// 	// push information on the stack
	// 	// saving String
	// 	// we have to make another instance and save it on the stack
		String saveStr = new String(ANSWER);
		strStack.push(saveStr);

		//saving checked 
		// Boolean[] saveChecked = new Boolean[N+1];
		// for (int idx=0; idx<checked.length; idx++) {
		// 	saveChecked[idx] = checked[idx];
		// }
		// checkStack.push(saveChecked);

		//saving answerLen
		Integer saveLen = answerLen;
		lenStack.push(saveLen);

	 }// end of save State method

	public static void loadState(){
		// pop the information on the stack

		//global variable 
		// answerLen
		// ANSWER
		// checked

		answerLen = lenStack.pop();

		ANSWER = strStack.pop();

		//checked = checkStack.pop();

	}// end of load State method


}// end of class 
