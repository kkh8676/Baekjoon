// Not DFS problem......
// binary Search?

import java.io.*;
import java.util.StringTokenizer;

class boj_1208{
	static int N,S;
	static int[] num;
	static int[] seq;
	static int TOTAL_SUM;

	static int ANSWER;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine());
		num = new int[N];
		seq = new int[N];

		TOTAL_SUM = 0;

		for (int n=0; n<N; n++) {
			num[n] = Integer.parseInt(st.nextToken());
			TOTAL_SUM += num[n];
		}

		// System.out.println(Integer.MAX_VALUE);
		// System.out.println(Integer.MIN_VALUE);



		DFS(N/2,0,0);

		System.out.println(ANSWER);

	}// end of main method

	public static void DFS(int capa, int round, int curIndex){
		if(capa == round){
			return;
		}

		for (int i=curIndex; i<N; i++) {

			seq[round] = i;

			int sum = 0;
			for (int n=0; n<=round; n++) {
				sum += num[ seq[n] ];
				//System.out.print(num[seq[n]]+" ");
			}
			//System.out.print("SUM is "+sum);
			//System.out.print(" REMAIN is "+(TOTAL_SUM - sum));
			//System.out.println();

			if(sum == S) ANSWER++;
			if(TOTAL_SUM - sum == S) ANSWER++;

			DFS(capa, round+1, i+1);
		}// end of for loop
	}// end of DFS method
}// end of class 