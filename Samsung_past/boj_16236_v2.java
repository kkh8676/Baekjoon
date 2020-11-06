import java.io.*;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;

class boj_16236_v2{
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	static int N;


	static int[][] board;
	static int[][] distance;

	// shark 0 row, 1 col, 2 size, 3 eaten
	static int[] shark;


	// first index fish num
	// second index 0 row, 1 col, 2 size, 3 dead or not
	static int[][] fish;
	static int total;

	static Queue<Integer> fishQ;

	static int TIME;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());
		total = 0;
		//fishQ = new LinkedList<>();
		board = new int[N][N];
		distance = new int[N][N];
		shark = new int[4];
		fish = new int[399][4];

		for (int r=0; r<N; r++) {
			String[] curRow = br.readLine().split(" ");
			for (int c=0; c<N; c++) {
				board[r][c] = Integer.parseInt(curRow[c]);

				//상어라면 정보 저장!!
				if(board[r][c] == 9){
					shark[0] = r;
					shark[1] = c;
					shark[2] = 2;
					shark[3] = 0;
				}
				// 상어도 아니고 빈칸도 아니면 물고기야 
				if(board[r][c] !=9 && board[r][c] != 0){
					fish[total][0] = r;
					fish[total][1] = c;
					fish[total][2] = board[r][c];
					fish[total][3] = 0;
					total++;
					}
			}
		}// end of getting map!



		TIME = 0; // 시작하기 전에 시간은 0이다.


		// 먹을 수 있는 물고기가 있는 지 확인하고 있다면 while 안으로 들어가고 그렇지 않으면 끝
		while(check()){
			int[] next = bfs();
			if(next[0] == -1) break;


			move(next);
			
			
			//printdist();

			
		}

		System.out.println(TIME);
		br.close();

	} // end of main method

	public static void move(int[] next){
		// next array contains 
		// 0 다음 물고기 index
		// 1 다음 물고기까지의 distance

		// 이 method에 들어온다는 것은 다음 물고기를 먹을 수 있따는 말이 된다.

		// 다음 물고기 정보
		int index = next[0];
		int d = next[1];

		int fr = fish[index][0];
		int fc = fish[index][1];
		int fs = fish[index][2];

		

		// 현재 상어 정보
		int sr = shark[0];
		int sc = shark[1];
		int ss = shark[2];

		// 가면서 시간이 변화한다.
		TIME = TIME + d;

		// 현재 상어가 있던 위치는 빈칸이 된다.
		board[sr][sc] = 0;

		// 현재 물고기가 들어있던 위치는 상어가 들어가고 상어의 위치가 변한다.
		board[fr][fc] = 9;
		shark[0] = fr;
		shark[1] = fc;
		
		// 상어의 먹은 물고기 수가 변한다.
		shark[3]++;
		// 만일 먹은 물고기 숫자가 현재 상어의 크기와 같아진다면 
		if(ss == shark[3]){
			shark[2]++;
			shark[3] = 0;
		}

		// 먹힌 물고기의 정보 수정
		fish[index][0] = -1; // row
		fish[index][1] = -1; // col
		fish[index][3] = 1; // 먹혔다!

	} // end of move method

	public static boolean check(){
		// 먹을 수 있는 물고기가 있는 지 없는 지 확인한다.
		for (int r=0; r<N; r++) {
			for (int c=0; c<N; c++) {
				// 지금 현재 상어의 사이즈보다 작은 물고기가 있다면 
				if(board[r][c] != 0 && board[r][c] < shark[2]) return true;
			}// end of column for loop
		}// end of row loop


		// 여기까지 없다는 소리는 먹을 물고기가 없다는 이야기.
		return false;
	}// end of check method

	public static int[] bfs(){
		// 현재 상어의 위치로 부터 먹을 수 있는 물고기까지의 거리를 계산하고 
		// 가장 우선순위의 물고기 index와 그 거리를 return 하는 method

		// distance를 짜서 거리를 계산해야한다.
		// distance array 초기화
		for (int r=0; r<N; r++) {
			Arrays.fill(distance[r], Integer.MAX_VALUE);
		}

		Queue<Integer> q = new LinkedList<>();

		// 현재 상어 위치를 큐에 삽입.
		q.add(shark[0]);
		q.add(shark[1]);
		q.add(0); // 현재 위치의 거리

		while(!q.isEmpty()){
			int nr = q.remove();
			int nc = q.remove();
			int nd = q.remove();

			// 4방향 살핀다.
			for (int w=0; w<4; w++) {
				int nnr = nr + dr[w];
				int nnc = nc + dc[w];

				// 범위를 벗어나면 큐에 삽입 ㄴㄴ
				if(nnr<0 || nnr>= N || nnc<0 || nnc>=N) continue;

				// 해당 위치를 방문한 적이 없고, 해당 위치의 물고기가 지날 수 있는 크기라면 큐에 삽입!!
				if(distance[nnr][nnc] == Integer.MAX_VALUE && board[nnr][nnc] <= shark[2]){
					distance[nnr][nnc] = nd+1;
					q.add(nnr);
					q.add(nnc);
					q.add(nd+1);
				}
			}

		} // 갈 수 있는 곳 최단거리 구하는 거 끝!!
		
		//우선순위에 따라 물고기 index와 그 거리를 내뱉으면 된다.!!
		// initialization
		int ti = -1;
		int td = Integer.MAX_VALUE; 

		// 모든 물고기에 대해서 어떤 놈인지 찾아야해!
		for (int f=0; f<total; f++) {
			// 해당 물고기 나보다 크거나 같다면 그냥 지나가
			if(fish[f][2] >= shark[2]) continue;

			// 물고기 죽었다면 
			if(fish[f][3] == 1) continue;



			// 여기 오면 크기가 나보다 작다는 말.
			// 거리가 현재 target distance보다 멀다면
			int candD = distance[ fish[f][0] ][ fish[f][1] ];
			if(candD == Integer.MAX_VALUE) continue;
			if(candD > td) continue;

			// 거리가 같거나 작은 부분이야

			if(candD < td){
				td = candD;
				ti = f;
			} else if(candD == td){
				// 거리가 같다면
				// 먼저 row를 비교
				if(fish[f][0] < fish[ti][0]){
					ti = f;
				} else if(fish[f][0] == fish[ti][0]){
					// row 가 같다면
					// column 비교
					if(fish[f][1] < fish[ti][1]){
						// column 작으면 수정
						ti = f;
					}
				}

			} // end of 거리가 같은 경우

		} // end of checking all fishes
		int[] next = new int[2];

		next[0] = ti;
		next[1] = td;

		return next;
	}// end of bfs method

	public static void printBoard(){
		for (int r=0; r<N; r++) {
			for (int c=0; c<N; c++) {
				System.out.print(board[r][c]+" ");
			}
			System.out.println();
		}
	}

	public static void printdist(){
		for (int r=0; r<N; r++) {
			for (int c=0; c<N; c++) {
				System.out.print(distance[r][c]+" ");
			}
			System.out.println();
		}
	}
}// end of class 