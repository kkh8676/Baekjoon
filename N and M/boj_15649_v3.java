import java.util.Scanner;

class boj_15649{
	static int N;
	static int M;

	static int[] seq;
	static int[] isV;

	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);

		N = scan.nextInt();
		M = scan.nextInt();
		isV = new int[N+1];
		seq = new int[M+1];

		// We have 1,2,3,......,N integers
		// We should get the sequence which has the length M

		for (int i=1; i<=N; i++) {
			permutation(i,1);

			seq[1] = 0;
			isV[i] = 0;
		}
	}// end of main method

	public static void permutation(int curNum, int round){
		// using DFS
		// ending condition
		seq[round] = curNum;
		isV[curNum] = 1;

		if(round == M){
			for (int m=1; m<=M; m++) {
				System.out.print(seq[m] + " ");
			}
			System.out.println();
			return;
		}// end of ending condition

		// visit the adjacent vertex
		for (int i=1; i<=N; i++) {
			if(isV[i] != 1){
				permutation(i,round+1);

				// Backtracking process
				seq[round+1] = 0;
				isV[i] = 0;
			}
		}
	}// end of permutation method
}// end of class
