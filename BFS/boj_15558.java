import java.io.*;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;

class boj_15558{
	static int N,K;
	static int[][] board;
	static int[][] isV;

	static int[][] dc = {{1,-1,0},{1,-1,0}};
	static int[][] dr = {{0,0,1},{0,0,-1}};
	static String[] actions = {"FORWARD","BACKWARD","JUMP"};

	static int ANSWER;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		dc[0][2] = K; dc[1][2] = K;


		board = new int[2][N+1];
		isV = new int[2][N+1];

		for (int i=0; i<2; i++) {
			String[] cur = br.readLine().split("");
			for (int j=1; j<=N; j++) {
				board[i][j] = Integer.parseInt(cur[j-1]);
			}
		}// end of getting information

		ANSWER = 0;

		BFS();

		System.out.println(ANSWER);
		br.close();


	}// end of main method

	public static void BFS(){
		Queue<Integer> q = new LinkedList<>();

		q.add(0); q.add(1); q.add(0);
		// row         col       time
		isV[0][1] = 1;

		while(!q.isEmpty()){
			int cr = q.remove();
			int cc = q.remove();
			int ct = q.remove();

			for (int i=0; i<3; i++) {
				int nr = cr + dr[cr][i];
				int nc = cc + dc[cr][i];
				int nt = ct + 1;

				if(nc > N){
					ANSWER = 1;
					// System.out.println("Reach!");
					return;
				}

				if( nc <= nt || board[nr][nc] == 0) continue;

				if(isV[nr][nc] == 1) continue;

				// System.out.println("Current R"+cr+" C"+cc+" in TIME "+ ct);
				// System.out.println("NR"+nr+" NC"+nc+" is safe!");
				
				// System.out.println("So move to there with "+actions[i]);
				q.add(nr); q.add(nc); q.add(ct+1);
				isV[nr][nc] = 1;
			}
		}// end of while loop

	}// end of BFS method
}// end of class