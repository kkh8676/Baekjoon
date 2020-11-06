import java.util.Arrays;
import java.io.*;

class boj_15656_v2{
	static int N;
	static int M;

	static BufferedReader br;
	static BufferedWriter bw;

	static int[] seq;
	static int[] num;
	static int[][] isV;

	public static void main(String[] args) throws IOException{
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));

		String[] NM = br.readLine().split(" ");
		N = Integer.parseInt(NM[0]);
		M = Integer.parseInt(NM[1]);

		seq = new int[M+1];
		num = new int[N+1];
		isV = new int[M+1][N+1];

		String[] nums = br.readLine().split(" ");
		for (int n=1; n<=N; n++) {
			num[n] = Integer.parseInt(nums[n-1]);
		}// end of getting number

		Arrays.sort(num);

		for (int n=1; n<=N; n++) {
			permu(n,1);

			isV[1][n] = 0;
		}

		bw.flush();
		bw.close();
		br.close();
	}// end of main method



	public static void permu(int index, int round) throws IOException{
		seq[round] = num[index];
		isV[round][index] = 1;

		if(round == M){
			for (int m=1; m<=M; m++) {
				bw.write(seq[m]+" ");
			}
			bw.write("\n");
			return;
		}

		for (int n=1; n<=N; n++) {
			if(isV[round+1][n] != 1){
				permu(n,round+1);

				isV[round+1][n] = 0;
			}
		}
	}// end of permu method
}// end of class 