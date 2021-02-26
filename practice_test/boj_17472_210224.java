import java.util.StringTokenizer;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;

class boj_17472_210224{
	static int N,M, num_island;
	static int[][] board;
	static int[][] isV;

	static Island[] islands;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	static int[] linked;
	static int[][] adj;

	static int ANSWER;

	static Queue<Integer> q;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		islands = new Island[7];

		board = new int[N][M];
		isV = new int[N][M];
		q = new LinkedList<>();

		for (int r=0; r<N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c=0; c<M; c++) {
				board[r][c] = Integer.parseInt(st.nextToken());
			}
		}// end of getting information

		// making adj process
		num_island = 1;

		for (int r=0; r<N; r++) {
			for (int c=0; c<M; c++) {
				if(board[r][c] == 0) continue;
				if(isV[r][c] == 1) continue;
				// came here because that location is land
				// so get the island using BFS
				BFS(r,c);
				num_island++;
			}
		}

		adj = new int[num_island][num_island];
		linked = new int[num_island];
		num_island--;


		// System.out.println();
		// print_board();
		// getting distance from each island.......
		get_distance();
		// System.out.println();
		// print_adj(adj);

		
		// Solving process
		ANSWER = Integer.MAX_VALUE;

		DFS(num_island-1,0,0);

		if(ANSWER == Integer.MAX_VALUE) ANSWER = -1;
		System.out.println(ANSWER);
		br.close();
	}// end of main method

	public static void get_distance(){
		// last one no need to speculate...
		for (int index=1; index<=num_island-1; index++) {
			// getting distance process
			// initialize isV arr
			// for (int r=0; r<N; r++) {
			// 	Arrays.fill(isV[r],0);
			// }
			q.clear();

			Island cur_land = islands[index];
			Queue<Integer> land_q = cur_land.land_list;

			// first elements making
			int island_num = -1;
			while(!land_q.isEmpty()){
				int cr = land_q.remove(); int cc = land_q.remove();
				island_num = board[cr][cc];

				for (int d=0; d<4; d++) {
					int nr = cr + dr[d];
					int nc = cc + dc[d];

					// out of bound
					if(nr < 0 || nr >= N || nc <0 || nc >= M) continue;
					// already current land
					if(board[nr][nc] != 0) continue;

					// came here because not out of bound and not land
					q.add(nr); q.add(nc); q.add(d); q.add(1); // direction and distance saving
					// isV[nr][nc] = 1;
				}
			}

			// visiting next lands!
			while(!q.isEmpty()){
				int cr = q.remove(); int cc = q.remove(); int pd = q.remove(); int dist = q.remove();

				int nr = cr + dr[pd];
				int nc = cc + dc[pd];	

				// out of bounds then continue
				if(nr < 0 || nr >= N || nc < 0 || nc >= M) continue;

				// if next location is not land
				if(board[nr][nc] == 0){
					q.add(nr); q.add(nc); q.add(pd); q.add(dist+1);
					continue;
				}

				else if(adj[island_num][board[nr][nc]] <= 1){
					adj[island_num][board[nr][nc]] = dist;
					adj[board[nr][nc]][island_num] = dist;
					continue;
				}
			}// end of visiting next land

		}
	}// end of get distance

	public static void BFS(int sr, int sc){
		q.add(sr); q.add(sc); isV[sr][sc] = 1;
		board[sr][sc] = num_island;
		Island new_land = new Island();
		new_land.land_list.add(sr);
		new_land.land_list.add(sc);

		while(!q.isEmpty()){
			int cr = q.remove(); int cc = q.remove();

			for (int d=0; d<4; d++) {
				int nr = cr + dr[d];
				int nc = cc + dc[d];

				if(nr < 0 || nr >= N || nc < 0 || nc >= M || isV[nr][nc] == 1 || board[nr][nc] == 0) continue;

				// came here because not visited and not out of bound, not land location
				q.add(nr); q.add(nc);
				isV[nr][nc] = 1;
				board[nr][nc] = num_island;

				new_land.land_list.add(nr); new_land.land_list.add(nc);
			}
		}// end of BFS visiting vertex process

		islands[num_island] = new_land;
	}// end of BFS method

	public static boolean feasible(){
		for (int i=1; i<=num_island; i++) {
			if(linked[i] == 0) return false;
		}

		return true;
	}

	public static void DFS(int capa, int round, int length){
		if(capa == round){
			// System.out.println("ANSWER updating process!");
			// if linked are all checked
			if(feasible()) ANSWER = ANSWER > length? length : ANSWER;
			return;
		}// end of ending condition

		// visit next vertex!
		for (int i=1; i<=num_island; i++) {
			for (int j=1; j<=num_island; j++) {
				if(i==j) continue;

				// i and j are not same island
				// one of two is not linked yet, you can link it
				int prev_i = linked[i];
				int prev_j = linked[j];

				if((linked[i] == 0 || linked[j] == 0) && adj[i][j] > 1){
					linked[i] = 1;
					linked[j] = 1;
					// System.out.println("DFS!!");
					DFS(capa, round+1, length+adj[i][j]);

					// backTracking process
					linked[i] = prev_i;
					linked[j] = prev_j;
				}
			}
		}
	}// end of DFS method

	public static void print_board(){
		for (int r=0; r<N; r++) {
			for (int c=0; c<M; c++) {
				System.out.print(board[r][c]+" ");
			}
			System.out.println();
		}
	}// end of print board method

	public static void print_adj(int[][] adj){
		for (int r=1; r<=num_island; r++) {
			for (int c=1; c<=num_island; c++) {
				System.out.print(adj[r][c]+" ");
			}
			System.out.println();
		}
	}// end of print board method
}// end of class

class Island{
	Queue<Integer> land_list;

	Island(){
		this.land_list = new LinkedList<>();
	}
}// end of Island