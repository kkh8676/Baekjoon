// Took 1 hr
// DFS + backTracking
// State: calculated result so far, calculated number count, remained operator number
// Check the state vertex, so don't enter that vertex again.......
// isV : N-1 (total operator number) * 4 (+, -, *, /)

// At first, while loop which is not needed should be coded, i think.....
// that causes inifinite loop

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Stack;
import java.util.LinkedList;


class boj_14888{

	static int N;
	static int[] A;
	static int[] ops;
	static int[][] isV;

	static final int PLUS = 0;
	static final int MINUS = 1;
	static final int MULTI = 2;
	static final int DEVIS = 3;

	static int ANSWER_MIN;
	static int ANSWER_MAX;

	static Stack<Integer> stack;

	public static void main(String[] args) throws IOException{
		BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter BW = new BufferedWriter(new OutputStreamWriter(System.out));

		N = Integer.parseInt(BR.readLine());
		A = new int[N+1];
		isV = new int[N][4];
		ops = new int[4];

		stack = new Stack<>();
		

		ANSWER_MIN = Integer.MAX_VALUE;
		ANSWER_MAX = Integer.MIN_VALUE;

		String as = BR.readLine();
		String[] asSplit = as.split(" ");

		for (int n=1; n<=N; n++) {
			A[n] = Integer.parseInt(asSplit[n-1]);
		} // we got As.......

		String operStr = BR.readLine();
		String[] operStrSplit = operStr.split(" ");

		for (int n=0; n<4; n++) {
			ops[n] = Integer.parseInt(operStrSplit[n]);
		}// we got operators 

		stack.push(A[1]); // result
		stack.push(1); // caculated

		DFS();

		System.out.println(ANSWER_MAX);
		System.out.println(ANSWER_MIN);
		
		
	}// end of main method


	public static void DFS(){
		// end condition
		int calculated = stack.pop();
		int result = stack.pop();

		if(calculated==N){
			//System.out.println("Came here??");
			ANSWER_MIN = result < ANSWER_MIN? result : ANSWER_MIN;
			ANSWER_MAX = result > ANSWER_MAX? result : ANSWER_MAX;
			return;
		}

		
			for (int i=0; i<4; i++) {
				int newResult;

				if(ops[i] > 0){
					// if we have additional operator 
					
					// plus
					if(i==0 && isV[calculated][i] != 1) newResult = result + A[calculated+1];

					// minus
					else if(i==1 && isV[calculated][i] != 1 ) newResult = result - A[calculated+1];

					// multiply
					else if(i==2 && isV[calculated][i] != 1 ) newResult = result * A[calculated+1];

					// division
					else if(i==3 && isV[calculated][i] != 1) newResult = result / A[calculated+1];
					else continue;

					ops[i]--;
					stack.push(newResult);
					stack.push(calculated+1);
					isV[calculated][i] = 1;
					DFS();
					isV[calculated][i] = 0;
					ops[i]++;
				} // end of additional operator
			}// end of for loop, adjacent vertex DFS
		
	} // end of DFS method



	
}// end of class 
