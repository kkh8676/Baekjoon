import java.io.*;
import java.util.Arrays;

class boj_15655{
	static int N;
	static int M;
	static BufferedReader br;
	static BufferedWriter bw;

	static int[] seq;
	static int[] isV;
	static int[] num;

	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));

		String[] NM = br.readLine().split(" ");
		N = Integer.parseInt(NM[0]);
		M = Integer.parseInt(NM[1]);

		seq = new int[M+1];
		isV = new int[N+1];
		num = new int[N+1];

		String[] nums = br.readLine().split(" ");
		for (int n=1; n<=N; n++) {
			num[n]	 = Integer.parseInt(nums[n-1]);
		}// end of getting numbers

		Arrays.sort(num);

		for (int i=1; i<=N; i++) {
			permutation(i,1);

			isV[i] = 0;
		}

		bw.flush();
		br.close();
		bw.close();

	}// end of main method

	public static void permutation(int curIndex, int round)  throws IOException {
		isV[curIndex] = 1;
		seq[round] = num[curIndex];

		// ending condition
		if(round == M){
			for (int m=1; m<=M; m++) {
				bw.write(seq[m] + " ");
			}
			bw.write("\n");
			return;
		}// end of ending condition

		for (int i=curIndex+1; i<=N; i++) {
			if(isV[i] != 1){
				permutation(i, round+1);

				isV[i] = 0;
			}
		}
	}// end of permutation
}// end of class 