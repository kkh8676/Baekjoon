import java.util.StringTokenizer;
import java.io.*;
import java.util.Queue;
import java.util.LinkedList;
import java.lang.Math;

class boj_15685_210219{
	static int N,M, home_num, chicken_num;
	static int[][] board;
	static int[][] chicken;
	static int[][] home;
	static int[] seq;

	static int ANSWER;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		home = new int[100][2];
		chicken = new int[13][2];

		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		seq = new int[M];

		
		board = new int[N][N];
		for (int r=0; r<N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c=0; c<N; c++) {
				board[r][c] = Integer.parseInt(st.nextToken());
				if(board[r][c] == 1){
					home[home_num][0] = r;
					home[home_num][1] = c;
					home_num++;

				}else if(board[r][c] == 2){
					chicken[chicken_num][0] = r;
					chicken[chicken_num][1] = c;
					chicken_num++;
				}
			}
		}// end of getting information

		ANSWER = Integer.MAX_VALUE;
		combi(M,0,0);

		System.out.println(ANSWER);
		br.close();
	}// end of main method

	public static void combi(int capa, int round, int start){
		if(capa == round){
			// checking the distance process
			int total_dist = 0;
			for (int h=0; h<home_num; h++) {
				int min_dist = Integer.MAX_VALUE;
				int hr = home[h][0]; int hc = home[h][1];

				for (int m=0; m<M; m++) {
					int idx = seq[m];
					int chickenR = chicken[idx][0]; int chickenC = chicken[idx][1];

					int dist = Math.abs(hr - chickenR) + Math.abs(hc - chickenC);
					min_dist = min_dist > dist? dist : min_dist;
				}

				total_dist += min_dist;

			}

			ANSWER = ANSWER > total_dist ? total_dist : ANSWER;

			// printing sequence Good
			// for (int s=0; s<M; s++) {
			// 	System.out.print(seq[s]+" ");
			// }
			// System.out.println();
			return;
		}

		for (int n=start; n<chicken_num; n++) {
			seq[round] = n;
			combi(capa, round+1, n+1);
		}
	}// end of combi method
}// end of class 

