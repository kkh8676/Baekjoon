import java.util.Scanner;
import java.util.Arrays;

class boj_9663_201014{
	static int N;

	static int[] c;
	static int[] uD;
	static int[] dD;

	static int ANSWER;

	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);

		N = scan.nextInt();

		c = new int[N+1];
		uD = new int[N+1];
		dD = new int[N+1];

		ANSWER = 0;

		Arrays.fill(c,Integer.MAX_VALUE);
		Arrays.fill(uD,Integer.MAX_VALUE);
		Arrays.fill(dD,Integer.MAX_VALUE);

		for (int i=1; i<=N; i++) {
			DFS(1,i);
		}

		System.out.println(ANSWER);
	}// end of main method

	public static void DFS(int r, int cc){
		// 일단 들어오면 해당 index check 
		// static variable 건드리면 backtracking stack 이용해서 해야해?

		// ending condition
		if(r==N){
			ANSWER++;
			return;
		}

		//System.out.println("DFS at R "+ r+" C "+cc);
		c[r] = cc;
		uD[r] = r+cc;
		dD[r] = r-cc;

		//System.out.println("Came here?1");

		// next row gogo
		for (int col=1; col<=N; col++) {
			// if previous row column upper diagonal and lower diagonal is occupied then continue;
			//print(c);
			//print(uD);
			//print(dD);

			if(check(r+1,col)) {
				//System.out.println("Came here?2");
				DFS(r+1,col);
			} // end of if condition

		}// end of checking all column for loop

		//System.out.println("Came here?3");

	}// end of DFS method 

	public static boolean check(int r, int cc){
		//System.out.print("Check at R "+r+" C "+cc);
		// check 1 to r-1 row 
		for (int row=1; row<=r-1; row++) {
			// check that row's column diagonals
			if(c[row] == cc) return false;
			if(uD[row] == r+cc) return false;
			if(dD[row] == r-cc) return false;
		} // end of checking all for loop

		// if reach here...that means none of row are ocuupied in int c, so return true
		return true;
	}// end of check method


	public static void print(int[] ary){
		for (int i=1; i<ary.length; i++) {
			System.out.print(ary[i]+" ");
		}
		System.out.println();
	}
}// end of class 