// 57 min, Success!!
// 19368kb , 120ms

import java.util.StringTokenizer;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;

class p1949_2102016{
	static int T,N,K;
	static int largest;
	static int[][] board, isV;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	static Queue<Integer> q;

	static int ANSWER;

	

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();

		board = new int[8][8];
		isV = new int[8][8];
		q = new LinkedList<>();

		T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());

			largest = Integer.MIN_VALUE;
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());

			// initialize the board arr
			for (int r=0; r<8; r++) {
				Arrays.fill(board[r],0);
			}
			

			for (int r=0; r<N; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<N; c++) {
					board[r][c] = Integer.parseInt(st.nextToken());
					if(largest < board[r][c]) largest = board[r][c];
				}
			}// end of getting information

			ANSWER = Integer.MIN_VALUE;

			for (int r=0; r<N; r++) {
				for (int c=0; c<N; c++) {
					if(board[r][c] == largest){
						// initializing the isV arr
						for (int rr=0; rr<8; rr++) {
							Arrays.fill(isV[rr],0);
						}
						isV[r][c] = 1;
						DFS(2,0,1,r,c);
					}
				}
			}
			//DFS(2,0,1,2,1);

			answer.append("#").append(t).append(" ").append(ANSWER).append("\n");
		}// end of test case

		System.out.println(answer.toString());
		br.close();
	}// end of main method

	public static void DFS(int capa, int round, int length, int cr, int cc){
		if(capa == round){
			//System.out.println("DFS ends with score"+ length);
			ANSWER = ANSWER < length ? length : ANSWER;
			return;
		}

		// next location visiting process
		// there's 4 way to visit
		for (int d=0; d<4; d++) {
			int nr = cr + dr[d];
			int nc = cc + dc[d];

			if(nr < 0 || nr >= N || nc < 0 || nc >= N || isV[nr][nc] == 1) continue;
			//System.out.println("Current R"+cr+" C"+cc);
			// 지금보다 그냥 낮은 지역이라면 이동 가능 ㄱㄱ
			if(board[nr][nc] < board[cr][cc]){
				isV[nr][nc] = 1;

				//System.out.println("move to R"+nr+" C"+nc+" which has lower height");
				DFS(capa, round, length+1, nr, nc);
				isV[nr][nc] = 0;
			} else{
				// 다음 위치의 높이가 지금과 같거나 높다.
				// 깎을 수 있는 기회가 존재하지 않는다.
				if(round == 1){
					isV[nr][nc] = 1;
					//System.out.println("move to R"+nr+" C"+nc+ " to end DFS");
					DFS(capa,round+1,length,nr,nc);
					isV[nr][nc] = 0;
					
				}
				// 만일 내가 깎을 수 있는 기회를 이미 가지고 있고 깎을 수 있는 범위 내에서 앞으로 나아갈 수 있다면 ㄱㄱ
				else if(round == 0 && board[nr][nc] - K < board[cr][cc]){
					for (int k=1; k<=K; k++) {
						if(board[nr][nc] - k >= board[cr][cc]) continue;
						isV[nr][nc] = 1;
						board[nr][nc] = board[nr][nc] - k;
						//System.out.println("move to R"+nr+" C"+nc+ " with using K");
						DFS(capa, round+1, length+1, nr,nc);
						board[nr][nc] = board[nr][nc] + k;
						isV[nr][nc] = 0;
					}
				}			

			}

		}

		// ANSWER updating process
		ANSWER = ANSWER < length? length : ANSWER;
	}// end of DFS method

	public static void BFS(int sr, int sc){

		// initialize Q and isV arr
		q.clear();

		for (int r=0; r<N; r++) {
			Arrays.fill(isV[r],0);
		}

		q.add(sr); q.add(sc);
		q.add(1); // length of road
		q.add(0); // using K score
		isV[sr][sc] = 1;

		int largest_depth = 1;
		while(!q.isEmpty()){
			int cr = q.remove(); int cc = q.remove(); int cl = q.remove(); int cs = q.remove();

			for (int d=0; d<4; d++) {
				int nr = cr + dr[d];
				int nc = cc + dc[d];

				// if out of bound, just continue or visited
				if(nr < 0 || nr >= N || nc < 0 || nc >= N || isV[nr][nc] == 1)  continue;


			}
		}// end of while loop

		// ANSWER updating process using largest_depth
		ANSWER = ANSWER < largest_depth? largest_depth : ANSWER;

	}// end of BFS method
}// end of class

/*
10
5 1
9 3 2 3 2
6 3 1 7 5
3 4 8 9 9
2 3 7 7 7
7 6 5 5 8
3 2
1 2 1
2 1 2
1 2 1
5 2
9 3 2 3 2
6 3 1 7 5
3 4 8 9 9
2 3 7 7 7
7 6 5 5 8
4 4
8 3 9 5
4 6 8 5
8 1 5 1
4 9 5 5
4 1
6 6 1 7
3 6 6 1
2 4 2 4
7 1 3 4
5 5
18 18 1 8 18
17 7 2 7 2
17 8 7 4 3
17 9 6 5 16
18 10 17 13 18
6 4
12 3 12 10 2 2
13 7 13 3 11 6
2 2 6 5 13 9
1 12 5 4 10 5
11 10 12 8 2 6
13 13 7 4 11 7
7 3
16 10 14 14 15 14 14
15 7 12 2 6 4 9
10 4 11 4 6 1 1
16 4 1 1 13 9 14
3 12 16 14 8 13 9
3 4 17 15 12 15 1
6 6 13 6 6 17 12
8 5
2 3 4 5 4 3 2 1
3 4 5 6 5 4 3 2
4 5 6 7 6 5 4 3
5 6 7 8 7 6 5 4
6 7 8 9 8 7 6 5
5 6 7 8 7 6 5 4
4 5 6 7 6 5 4 3
3 4 5 6 5 4 3 2
8 2
5 20 15 11 1 17 10 14
1 1 11 16 1 14 7 5
17 2 3 4 5 13 19 20
6 18 5 16 6 7 8 5
10 4 5 4 9 2 10 16
2 7 16 5 8 9 10 11
12 19 18 8 7 11 15 12
1 20 18 17 16 15 14 13
*/