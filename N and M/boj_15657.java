import java.io.*;
import java.util.Arrays;

class boj_15657{
	static int N;
	static int M;

	static int[] seq;
	static int[] num;
	static int[][] isV;

	static BufferedReader br;
	static BufferedWriter bw;


	public static void main(String[] args) throws IOException {
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		br = new BufferedReader(new InputStreamReader(System.in));

		String[] NM = br.readLine().split(" ");
		N = Integer.parseInt(NM[0]);
		M = Integer.parseInt(NM[1]);

		seq = new int[M+1];
		num = new int[N+1];
		isV = new int[M+1][N+1];

		String[] nums = br.readLine().split(" ");
		for (int n=1; n<=N; n++) {
			num[n] = Integer.parseInt(nums[n-1]);
		}// end of for 

		Arrays.sort(num);
		
		for (int i=1; i<=N; i++) {
			permutation(i,1);

			isV[1][i] = 0;
		}

		bw.flush();
		bw.close();
		br.close();

	}// end of main method

	public static void permutation(int curIndex, int round) throws IOException {
		isV[round][curIndex] = 1;
		seq[round] = num[curIndex];

		if(round == M){
			for (int m=1; m<=M; m++) {
				bw.write(seq[m] + " ");
			}
			bw.write("\n");
			return;
		}// end of ending condition

		for (int i=curIndex; i<=N; i++) {
			if(isV[round+1][i] != 1){
				permutation(i,round+1);

				isV[round+1][i] = 0;
			}
		}
	}// end of permutation method
}// end of class 