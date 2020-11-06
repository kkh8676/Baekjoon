import java.io.*;
import java.util.Arrays;

class boj_15654{
	static int N;
	static int M;

	static int[] nums;
	static int[] isV;
	static int[] seq;
	static BufferedWriter bw;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));

		String[] NM = br.readLine().split(" ");
		N = Integer.parseInt(NM[0]);
		M = Integer.parseInt(NM[1]);

		nums = new int[N+1];
		isV = new int[N+1];
		seq = new int[M+1];

		String[] numArrays = br.readLine().split(" ");

		for (int n=1; n<=N; n++) {
			nums[n] = Integer.parseInt(numArrays[n-1]);
		} // end of getting nums

		Arrays.sort(nums);

		for (int i=1; i<=N; i++) {
			permutation(i, 1);

			isV[i] = 0;
		}

		bw.flush();
		bw.close();

	}// end of main method

	public static void permutation(int curIndex, int round) throws IOException {
		isV[curIndex] = 1;
		seq[round] = nums[curIndex];

		if(round == M){
			for (int m=1; m<=M; m++) {
				bw.write(seq[m] + " ");
			}
			bw.write("\n");
			return;
		} // end of ending condition

		for (int i=1; i<=N; i++) {
			if(isV[i] != 1){
				permutation(i, round+1);

				isV[i] = 0;
			}
		}
	}// end of permutation
}// end of class 