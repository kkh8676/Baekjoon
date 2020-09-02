// trying to optimizae boj_9663 code
// DFS + backtracking

// can be optimized using boolean[]

// we don't have to count the combination of queen's location
// DFS(r,c) only changes row r, so we don't have to save and load information of previous row 
// calling DFS(N,c) means there is N queens in the map, so end the DFS method

// backtracking means previous information have to be saved!
// that doesn't mean you actually save the information!

import java.util.Scanner;

class boj_9663{
	static int N;
	static int[] col;
	static int[] uDiag;
	static int[] dDiag;

	static int ANSWER;

	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);

		N = scan.nextInt();

		col = new int[N+1];
		uDiag = new int[N+1];
		dDiag = new int[N+1];


		ANSWER = 0;
		for(int c=1; c<=N; c++){
			DFS(1,c);
		}


		System.out.println(ANSWER);
	}// end of main method

	public static void DFS(int r, int c){
		//System.out.println("DFS at "+r+" "+c);
		col[r] = c;
		uDiag[r] = r+c;
		dDiag[r] = r-c;

		// end condition
		if(r==N){
			ANSWER ++ ;
			return;
		}

		for (int nextCol=1; nextCol<=N; nextCol++) {
			if(check(r+1,nextCol)){
				DFS(r+1,nextCol);
			}
		}
	} // end of DFS method

	public static boolean check(int r, int c){
		// row number is same with Queen number 
		for (int pR=1; pR<r; pR++) {
			if(col[pR] == c) return false;
			if(uDiag[pR] == r+c) return false;
			if(dDiag[pR] == r-c) return false;
		}

		return true;

	} // end of check method
}
