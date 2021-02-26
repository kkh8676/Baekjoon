import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class boj_1182{
	static int N,S;
	static int[] num, seq;

	static int TOTAL_SUM;

	static int ANSWER;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());

		num = new int[N];
		st = new StringTokenizer(br.readLine());
		seq = new int[N];

		TOTAL_SUM = 0;
		for (int n=0; n<N; n++) {
			num[n] = Integer.parseInt(st.nextToken());
			TOTAL_SUM += num[n];
		}// end of getting information

		ANSWER = 0;

		


		DFS(N,0,0);

		System.out.println(ANSWER);

	}// end of main method

	public static void DFS(int capa, int round, int curIndex){
		if(capa == round){
			return;
		}

		for (int i=curIndex; i<N; i++) {
			seq[round] = i;

			int sum = 0;
			for (int r=0; r<=round; r++) {
				sum += num[ seq[r] ];
			}

			if(sum == S) ANSWER++;
			

			DFS(capa, round+1, i+1);
		}
	}// end of DFS method

}// end of class