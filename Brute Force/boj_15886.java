// Took 1hr 5min
// BFS for getting distance
// DFS + Backtracking for getting all the combination of chicken houses

// 만약에 LinkedList와 Queue로 구현해서 array로 옮기게 되면 한번 더 연산하는 과정을 거침.
// 그냥 ArrayList를 통해서 가져오면 그 과정을 생략할 수 있다.
// ArrayList를 통해서 구현하는 방법을 익혀야한다.

// 심지어 BFS로 구현할 필요도 없음.
// distance를 구하는 방법이 그냥 | r1 - r2 | + | c1 - c2 | 이기 때문

// 최적화 version 구현할 필요가 있다.


import java.io.*;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;


class boj_15686{
	static int R,C,M;
	static int[][] map;

	static int chick_num;
	static int home_num;
	static int[][][] dists;
	static int[][] home;
	static int[][] chicken;

	static int[][] isV;

	static int[] isv;

	static int[] seq;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	static int ANSWER;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] rc = br.readLine().split(" ");
		R = Integer.parseInt(rc[0]);
		C = Integer.parseInt(rc[0]);
		M = Integer.parseInt(rc[1]);
		map = new int[R+1][C+1];

		Queue<Integer> hq = new LinkedList<>();
		Queue<Integer> cq = new LinkedList<>();

		for (int r=1; r<=R; r++) {
			String[] curRow = br.readLine().split(" ");
			for (int c=1; c<=C; c++) {
				map[r][c] = Integer.parseInt(curRow[c-1]);
				if(map[r][c] == 1){
					hq.add(r);
					hq.add(c);
				} else if(map[r][c] == 2){
					cq.add(r);
					cq.add(c);
				}

			}// end of column map information
		}// end of getting map information

		home_num = hq.size()/2;
		chick_num = cq.size()/2;

		home = new int[home_num+1][2];
		chicken = new int[chick_num+1][2];

		int hindex = 1;
		while(!hq.isEmpty()){
			int chr = hq.remove();
			int chc = hq.remove();

			home[hindex][0] = chr;
			home[hindex][1] = chc;

			hindex++;
		}

		int cindex = 1;
		while(!cq.isEmpty()){
			int ccr = cq.remove();
			int ccc = cq.remove();

			chicken[cindex][0] = ccr;
			chicken[cindex][1] = ccc;

			cindex++;
		} // end of getting chicken and home information

		dists = new int[chick_num+1][R+1][C+1];

		for (int n=1; n<=chick_num; n++) {
			BFS(n);
			// in this method dists[n] are assigned
		}

		isv = new int[chick_num+1];
		seq = new int[M+1];
		ANSWER = Integer.MAX_VALUE;

		for (int i=1; i<=chick_num; i++) {
		 	combi(i,1);
		}

		System.out.println(ANSWER);

	}// end of main method

	public static void BFS(int n){
		// calculate distance from n th chicken home
		

		isV = new int[R+1][C+1];

		int r = chicken[n][0];
		int c = chicken[n][1];
		int d = 0;
		dists[n][r][c] = 0;

		isV[r][c] = 1;

		Queue<Integer> dq = new LinkedList<>();
		dq.add(r);
		dq.add(c);
		dq.add(d);

		while(!dq.isEmpty()){
			int cr = dq.remove();
			int cc = dq.remove();
			int cd = dq.remove();

			for (int i=0; i<4; i++) {
				int nr = cr + dr[i];
				int nc = cc + dc[i];

				if(nr < 1 || nr > R || nc < 1 || nc > C) continue;

				if(isV[nr][nc] == 0){
					isV[nr][nc] = 1;
					dq.add(nr);
					dq.add(nc);
					dq.add(cd+1);
					dists[n][nr][nc] = cd + 1;
				}
			}
		}



	}// end of BFS method

	public static void combi(int index, int round){
		seq[round] = index;
		isv[index] = 1;

		if(round==M){
			// for checking
			// for (int m=1; m<=M; m++) {
			// 	System.out.print(seq[m] + " ");
			// }
			// System.out.println();
			int cur_dist = cal_dist(seq);
			ANSWER = ANSWER > cur_dist ? cur_dist : ANSWER;
			
			return;
		}// end of ending condition

		// next visiting vertex
		for (int i = index+1; i<=chick_num; i++) {
			if(isv[i] == 0){
				combi(i, round+1);

				isv[i] = 0;
			}
		}

		
		return;
	}// end of combi method using DFS

	public static int cal_dist(int[] seq){
		// in seq array, there is selected chiken house index

		int[] chicken_dist = new int[home_num+1];
		Arrays.fill(chicken_dist, Integer.MAX_VALUE);

		for (int h=1; h<=home_num; h++) {
			for (int m=1; m<=M; m++) {
				chicken_dist[h] = chicken_dist[h] > dists[ seq[m] ][ home[h][0] ][ home[h][1] ] ? dists[ seq[m] ][ home[h][0] ][ home[h][1] ] : chicken_dist[h];
			}
		}

		int sum = 0;

		for (int h=1; h<=home_num; h++) {
			sum += chicken_dist[h];
			
		}

		return sum;


	}// end of cal_dist method

	public static void print_dist(int n){
		for (int r=1; r<=R; r++) {
			for (int c=1; c<=C; c++) {
				System.out.print(dists[n][r][c] + " ");
			}
			System.out.println();
		}
	}// end of print_dist method


}// end of class 
