// How can i notice this is the Dynamic Programming Problem??
// 
// At first, thought Weight Limit K is from Weight Limit K-1 
// Half correct, half incorrect.
// We should CHECK the product is in or out at Weight Limit K.
// So, Establish the Ignition checking current product is in or out.

// DP[N][k] = max( DP[N-1][k-W[N]] + V[N] , DP[N-1][k] )
// 
// 

import java.util.Scanner;
import java.lang.Math;
class boj_12865{
	static int N;
	static int K;

	static int[] W;
	static int[] V;

	static int[][] dp;
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		N = scan.nextInt();
		K = scan.nextInt();

		W = new int[N+1];
		V = new int[N+1];

		dp = new int[N+1][K+1];

		for (int n=1; n<N+1; n++) {	
			W[n] = scan.nextInt();
			V[n] = scan.nextInt();
		} // end of for loop

		// now we have W and V

		// Ignition

		// DP[N][k] = max (DP[N-1][k-W[N]] + V[N] , DP[N-1][k] )
		// initializing all row
		for (int row=0; row<N+1; row++) {
			for (int col=0; col<K+1; col++) {
				dp[row][col] = 0;
			}
		}

		// initializing the first row
		for (int col=W[1]; col<K+1; col++) {
			dp[1][col] = V[1];
		}

		for (int row=2; row<N+1; row++) {
			for(int col=1; col<K+1; col++){
				int included = col-W[row] < 0? 0 : dp[row-1][col-W[row]] + V[row];
				int excluded = dp[row-1][col];
				dp[row][col] = included > excluded? included : excluded;
			}
		}

		System.out.println(dp[N][K]);

	} // end of main method
} // end of class 