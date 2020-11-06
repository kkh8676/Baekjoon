import java.util.Scanner;
import java.io.*;

class boj_15651{
	static int N;
	static int M;

	static int[] seq;
	static int[][] isV;
	static BufferedWriter bw;

	public static void main(String[] args) throws IOException{
		Scanner scan = new Scanner(System.in);
		bw = new BufferedWriter(new OutputStreamWriter(System.out));

		N = scan.nextInt();
		
		M = scan.nextInt();
		seq = new int[M+1];
		isV = new int[M+1][N+1];

		for (int n=1; n<=N; n++) {
			permutation(n,1);

			isV[1][n] = 0;
			//seq[1] = 0;
		}
		bw.flush();
		bw.close();
	}// end of main method

	public static void permutation(int curNum, int round) throws IOException {
		// ending condition
		isV[round][curNum] = 1;
		seq[round] = curNum;

		// ending condition
		if(round == M){
			for (int m=1; m<=M; m++) {
				bw.write(seq[m] + " ");
			}
			bw.write("\n");
			return;
		} // end of ending condition

		// visit the adjacent vertex!
		for (int n=1; n<=N; n++) {
			if(isV[round+1][n] != 1){
				permutation(n, round+1);

				isV[round+1][n] = 0;
				//seq[round] = 0;
			}
		}
	}// end of permutation method


}// end of class 