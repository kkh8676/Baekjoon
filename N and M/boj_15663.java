// 중복이 있는 순열 문제
// 중복이 있을 경우에 이전 것을 기억하여 이전 것이 들어온 적이 있다면 넣지 않는 형식으로 처리.
// 첫번 째 경우에 for 문이 없이 진행될 수 있도록 DFS 구성할 수 있나?


import java.io.*;
import java.util.Arrays;

class boj_15663{
	static int N;
	static int M;

	static int[] seq;
	static int[] num;
	static int[] isV;
	static int[][] total;

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
		isV = new int[N+1];
		total = new int[M+1][10001];

		String[] nums = br.readLine().split(" ");
		for (int n=1; n<=N; n++) {
			num[n] = Integer.parseInt(nums[n-1]);
		}// end of for 

		Arrays.sort(num);
		int prev = -1;
		for (int i=1; i<=N; i++) {
			
			if(prev != num[i]){
				prev = num[i];
				permutation(i,1);

				isV[i] = 0;
				}
		}
		
		bw.flush();
		bw.close();
		br.close();
			

	}// end of main method

	public static void permutation(int curIndex, int round) throws IOException{
		seq[round] = num[curIndex];
		isV[curIndex] = 1;
		//total[round][ num[curIndex] ] = 1;

		// ending condition
		if(round == M){
			for (int m=1; m<=M; m++) {
				bw.write(seq[m]+" ");
			}
			bw.write("\n");
			return;
		}// end of ending condition
		int prev = -1;
		for (int i=1; i<=N; i++) {
			
			if(isV[i] != 1 && prev != num[i]){
				prev = num[i];
				permutation(i, round+1);

				isV[i] = 0;
				
				
			}
			
		}
	}// end of permutation method
}// end of class 