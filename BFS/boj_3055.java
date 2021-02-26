// Took 2hr.....
// Re Solve it!

import java.util.Queue;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.io.*;

class boj_3055{
	static int R,C;
	static String[][] map;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	static int sr;
	static int sc;

	static int ddr;
	static int ddc;

	static int[][] d;
	static int[][] isV;

	static int ANSWER;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());

		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		map = new String[R+1][C+1];
		d = new int[R+1][C+1];
		isV = new int[R+1][C+1];
		

		Queue<Integer> q = new LinkedList<Integer>();

		for (int r=1; r<=R; r++) {
			String[] curStr = br.readLine().split("");
			for (int c=1; c<=C; c++) {
				map[r][c] = curStr[c-1];
			}
		}// end of getting information

		// Checking next water position
		sr = -1;
		sc = -1;
		Queue<Integer> wq = new LinkedList<>();
		for (int r=1; r<=R; r++) {
			for (int c=1; c<=C; c++) {
				if(map[r][c].equals("S")){
					sr = r;
					sc = c;
					map[r][c] = ".";
				}
				if(map[r][c].equals("*")){
					//System.out.println("Heelo");
					wq.add(r);
					wq.add(c);
				}
				if(map[r][c].equals("D")){
					d[r][c] = -1;
					ddr = r;
					ddc = c;
				}
			}
		}

		// while(!wq.isEmpty()){
		// 	int cr = wq.remove();
		// 	int cc = wq.remove();

		// 	for (int i=0; i<4; i++) {
		// 		int nr = cr + dr[i];
		// 		int nc = cc + dc[i];

		// 		if(nr < 1 || nr > R || nc < 1 || nc > C) continue;

		// 		map[nr][nc] = "*";
		// 	}
		// }

		

		BFS(q);

		//printD();
		if(d[ddr][ddc] == -1){
			System.out.println("KAKTUS");
		}
		else{
			System.out.println(d[ddr][ddc]);
		}

	}// end of main method


	public static void printMap(){
		for (int r=1; r<=R; r++) {
			for (int c=1; c<=C; c++) {
				System.out.print(map[r][c] + " ");
			}
			System.out.println();
		}
	}// end of printMAp method


	public static void BFS(Queue<Integer> q){
		

		// first making q with water and 0
		for (int r=1; r<=R; r++) {
			for (int c=1; c<=C; c++) {
				if(map[r][c].equals("*")){
					
					q.add(r);
					q.add(c);
					q.add(1);
					q.add(Integer.MAX_VALUE);
					d[r][c] = Integer.MAX_VALUE;
					isV[r][c] = 1;
					
				}
			}
		}

		q.add(sr);
		q.add(sc);
		//System.out.println("SR"+sr+" SC"+sc);
		q.add(-1);
		q.add(0);
		d[sr][sc] = 0;
		isV[sr][sc] = 1;

		
		while(!q.isEmpty()){
			int cr = q.remove();
			int cc = q.remove();
			int ct = q.remove();
			int cd = q.remove();

			for (int i=0; i<4; i++) {
				int nr = cr + dr[i];
				int nc = cc + dc[i];
				int nd = cd == Integer.MAX_VALUE? Integer.MAX_VALUE : cd + 1;

				if(nr < 1 || nr > R || nc < 1 || nc > C) continue;

				if(ct!= -1 && map[nr][nc].equals(".") && isV[nr][nc] == 0){
					q.add(nr);
					q.add(nc);
					q.add(ct);
					q.add(nd);
					isV[nr][nc] = 1;
					d[nr][nc] = nd;
				} else if(ct == -1 && !map[nr][nc].equals("X") && isV[nr][nc] == 0){
					q.add(nr);
					q.add(nc);
					q.add(ct);
					q.add(nd);
					isV[nr][nc] = 1;
					d[nr][nc] = nd;
				}
			}
			//System.out.println("After CR"+cr+" CC"+cc+" with TYPE"+ct);
			//printD();
			//System.out.println();
		}


	}// end of DFS method

	public static void printD(){
		for (int r=1; r<=R; r++) {
			for (int c=1; c<=C; c++) {
				if(d[r][c] == Integer.MAX_VALUE){
					System.out.print("X ");	
					continue;
				}
				System.out.print(d[r][c] +" ");
			}
			System.out.println();
		}
	}
}// end of class 