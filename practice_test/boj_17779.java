import java.util.StringTokenizer;
import java.io.*;
import java.util.Arrays;

class boj_17779{
	static int N;
	static int[][] board;
	static int[][] team;
	static int[] people;

	static int ANSWER;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Integer.parseInt(br.readLine());
		board = new int[N+1][N+1];
		team = new int[N+1][N+1];
		people = new int[6];

		for (int r=1; r<=N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c=1; c<=N; c++) {
				board[r][c] = Integer.parseInt(st.nextToken());
			}
		}// end of getting information

		ANSWER = Integer.MAX_VALUE;

		for (int r=1; r<=N; r++) {
			for (int c=1; c<=N; c++) {
				for (int d1=1; d1<N; d1++) {
					for (int d2 = 1; d2 < N; d2++) {
						divide(r,c,d1,d2);
					}
				}
			}
		}

		System.out.println(ANSWER);


	}// end of main method

	public static void divide(int sr, int sc, int d1, int d2){
		// initialize team and people
		for (int r=1; r<=N; r++) {
			Arrays.fill(team[r],0);
		}
		Arrays.fill(people, 0);

		// can go condition here
		if(sr < sr + d1 + d2 && sr + d1 + d2 <= N && 1 <= sc - d1 && sc - d1 < sc && sc < sc + d2 && sc + d2 <= N){
			
			// for (int d=0; d<=d1; d++) {
			// 	int ssr = sr +d;
			// 	int ssc = sc-d;

			// 	for (int nd = 0; nd<=d2; nd++) {
			// 		if(team[ssr+nd][ssc+nd] == 0){
						
			// 			team[ssr+nd][ssc+nd] = 5;
			// 			people[5] += board[ssr+nd][ssc+nd];
			// 		}
			// 	}
			// }
			for(int r=1; r<=N; r++){
				for (int c=1; c<=N; c++) {
					if(sr+sc <= r+c && r+c <= sr+sc+2*d2 && sr-sc <= r-c && r-c <= sr-sc+2*d1){
						team[r][c] = 5;
						people[5] += board[r][c];
					}
				}
			}

			for (int r=1; r<=N; r++) {
				for (int c=1; c<=N; c++) {
					// district 1 saving
					if(1 <= r && r < sr+d1 && 1 <= c && c <= sc && team[r][c] ==0){
						team[r][c] = 1;
						people[1] += board[r][c];
					}
					// district 2 saving
					else if(1 <= r && r <= sr+d2 && sc < c && c <= N && team[r][c] ==0 ){
						team[r][c] = 2;
						people[2] += board[r][c];
					}
					// district 3 saving
					else if(sr + d1 <= r && r <= N && 1 <= c && c < sc - d1 + d2 && team[r][c] ==0){
						team[r][c] = 3;
						people[3] += board[r][c];
					}
					// district 4 saving
					else if(sr + d2 < r && r <= N && sc - d1 + d2 <= c && c <= N && team[r][c] ==0){
						team[r][c] = 4;
						people[4] += board[r][c];
					} 
				}
			}
			// System.out.println("X"+sr+" Y"+sc+" D1 is "+d1+" D2 is "+d2);
			// print_team();
			// System.out.println();
			// modifying answer process 
			int min = Integer.MAX_VALUE;
			int max = Integer.MIN_VALUE;
			for (int d=1; d<6; d++) {
				min = min > people[d]? people[d] : min;
				max = max < people[d]? people[d] : max;
			}

			ANSWER = ANSWER > max - min? max- min : ANSWER;
		} else return;
	}// end of divide method

	public static void print_team(){
		for (int r=1; r<=N; r++) {
			for (int c=1; c<=N; c++) {
				System.out.print(team[r][c]+" ");
			}
			System.out.println();
		}
	}
}// end of class 