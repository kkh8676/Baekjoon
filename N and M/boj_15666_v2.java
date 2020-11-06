import java.util.Arrays;
import java.io.*;

class boj_15666_v2{
	static int N;
	static int M;

	static BufferedReader br;
	static BufferedWriter bw;

	static int[] seq;
	static int[] num;
	static int[][] isV;
	
	public static void main(String[] args) throws IOException{
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
		}

		Arrays.sort(num);

		int prev = -1;
		for (int n=1; n<=N; n++) {
			if(prev != num[n]){
				prev = num[n];
				permu(n,1);

				isV[1][n] = 0;

			}
		}

		bw.flush();
		bw.close();
		br.close();

	}// end of main method

	public static void permu(int curIndex, int round) throws IOException {
		isV[round][curIndex] = 1;
		seq[round] = num[curIndex];

		if(round == M){
			for (int m=1; m<=M; m++) {
				bw.write(seq[m] + " ");
			}
			bw.write("\n");
			return;
		}
		int prev = -1;
		for (int n=curIndex; n<=N; n++) {
			if(isV[round+1][n] != 1 && prev != num[n]){
				prev = num[n];
				permu(n, round+1);

				isV[round+1][n] = 0;
			}
		}
	}// end of permu method
}// end of class 