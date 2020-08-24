// Basic Dynamic Programming
// 
// length N stair number ends with 0,1,2,....,8 is from length N-1 stair number ends with 1,2,...,9
// length N stair number ends with 1,2,3,....,9 is from length N-1 stair number ends with 0,1,...,8
// 
// 

import java.util.Scanner;

class boj_10844{
	static int[][] memo;
	static final int DELI = 1000000000;
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);

		int N = scan.nextInt();
		memo = new int[N+1][10];
		memo[1][0] = 0;

		for (int p=1; p<=9; p++) {
			memo[1][p] = 1;
		}

		for (int row=2; row<=N; row++) {
			for (int col=0; col<=9; col++) {
				int lower = col-1<0 ? 0 : memo[row-1][col-1] ;
				int upper = col+1>9 ? 0 : memo[row-1][col+1];

				int result = (lower + upper)%DELI;
				memo[row][col] = result;
			}
		}

		int ANSWER = 0;
		for (int p=0; p<=9; p++) {
			ANSWER = (ANSWER + memo[N][p])%DELI;
		}

		System.out.println(ANSWER);

	}
}