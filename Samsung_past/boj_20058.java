// Took 2hr Failed..........Shit!!!!
// Took 2hr 30min successed........
// Hard problem.........
// Divide and Rotate process is bottleneck....

import java.io.*;
import java.lang.Math;
import java.util.Queue;
import java.util.LinkedList;

class boj_20058{
	static int N,Q;
	static int[][] map, isV;
	static int[] L;

	static int LINK;
	static int ICE;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	static Queue<Integer> q;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] nq = br.readLine().split(" ");
		N = Integer.parseInt(nq[0]);
		Q = Integer.parseInt(nq[1]);

		map = new int[65][65];
		isV = new int[65][65];
		L = new int[1001];

		for (int r=1; r<=Math.pow(2,N); r++) {
			String[] curRow = br.readLine().split(" ");
			for (int c=1; c<=Math.pow(2,N); c++) {
				map[r][c] = Integer.parseInt(curRow[c-1]);
			}
		}// end of getting map information

		String[] Ls = br.readLine().split(" ");
		for (int q=1; q<=Q; q++) {
			L[q] = Integer.parseInt(Ls[q-1]);
		}// end of getting L information

		for (int q=1; q<=Q; q++) {
			rotate(L[q]);
			// System.out.println("Rotated!");
			// printMap();
			melt();
			// System.out.println();
			// System.out.println("Melted!");
			// printMap();
			// System.out.println();
		}

		// printMap();
		// System.out.println();

		calculate_ice();
		calculate_link();

		System.out.println(ICE);
		LINK = ICE == 0? 0 : LINK;
		System.out.println(LINK);

		// rotate(1);
		// System.out.println();
		// printMap();

	}// end of main

	public static void calculate_ice(){
		ICE = 0;

		for (int r=1; r<=Math.pow(2,N); r++) {
			for (int c=1; c<=Math.pow(2,N); c++) {
				ICE += map[r][c];
			}
		}

	} // end of calculate _ice method

	public static void calculate_link(){
		LINK = Integer.MIN_VALUE;

		q = new LinkedList<>();
		// BFS method gogo........
		for (int r=1; r<=Math.pow(2,N); r++) {
			for (int c=1; c<=Math.pow(2,N); c++) {
				if(isV[r][c] != 1 && map[r][c] > 0) BFS(r,c);
			}
		}

	}// end of calculate_link method

	public static void BFS(int r, int c){
		int temp_Link = 1;
		isV[r][c] = 1;
		q.add(r);
		q.add(c);

		while(!q.isEmpty()){
			int cr = q.remove();
			int cc = q.remove();

			for (int i=0; i<4; i++) {
				int nr = cr + dr[i];
				int nc = cc + dc[i];

				if(nr < 1 || nr > Math.pow(2,N) || nc < 1 || nc > Math.pow(2,N)) continue;
				if(isV[nr][nc] == 0 && map[nr][nc] > 0 ){
					q.add(nr);
					q.add(nc);
					isV[nr][nc] = 1;
					temp_Link++;
				}
			}
		}// end of BFS method

		LINK = LINK < temp_Link? temp_Link : LINK;

	}

	public static void rotate(int l){
		if(l==0) return;
		int[][] cMap = new int[65][65];

		for (int sr=1; sr<Math.pow(2,N); sr = sr + (int)(Math.pow(2,l)) ) {
			for (int sc=1; sc<Math.pow(2,N); sc = sc + (int)(Math.pow(2,l))) {
				// divided array
				//System.out.println("SR"+sr +" SC"+sc);
				for (int r=sr; r<sr+Math.pow(2,l); r++) {
					int d=1;
					for (int c=sc; c<sc+Math.pow(2,l); c++) {
						//System.out.println("R"+r+" C"+c+" NR"+(sr+c-sc)+" NC"+(int)(sr+sc+Math.pow(2,l)-r-1));
						cMap[sr+c-sc][(int)(sr+sc+Math.pow(2,l)-r-1)] = map[r][c];
					}
					d++;
				}
			}
		}
		map = cMap;
	}// end of rotate method

	public static void melt(){
		int[][] cMap = new int[65][65];

		for (int r=1; r<=Math.pow(2,N); r++) {
			for (int c=1; c<=Math.pow(2,N); c++) {
				int iced = 0;

				for (int i=0; i<4; i++) {
					int nr = r + dr[i];
					int nc = c + dc[i];

					if(nr < 1 || nr > Math.pow(2,N) || nc < 1 || nc > Math.pow(2,N)) continue;
					if(map[nr][nc] <= 0) continue;

					iced++;
				}

				if(iced < 3 && map[r][c] >0){
					//System.out.println("WHy not here? R"+r+" C"+c);
					cMap[r][c] = map[r][c] - 1;
				}
				else{
					cMap[r][c] = map[r][c];
				}

			}// end of outer column for loop
		}// end of outer row for loop
		map = cMap;
	}// end of melt method

	public static void printMap(){
		for (int r=1; r<=Math.pow(2,N); r++) {
			for (int c=1; c<=Math.pow(2,N); c++) {
				System.out.print(map[r][c] + " ");
			}
			System.out.println();
		}
	}

}// end of class 

// 3 1
// 1 2 3 4 5 6 7 8
// 8 7 6 5 4 3 2 1
// 1 2 3 4 5 6 7 8
// 8 7 6 5 4 3 2 1
// 1 2 3 4 5 6 7 8
// 8 7 6 5 4 3 2 1
// 1 2 3 4 5 6 7 8
// 8 7 6 5 4 3 2 1
// 3

// 3 1 
// 0 0 0 0 0 0 0 0
// 0 0 0 0 0 0 0 0
// 0 0 0 0 0 0 0 0
// 0 0 0 0 0 0 0 0
// 0 0 0 0 0 0 0 0
// 0 0 0 0 0 0 0 0
// 0 0 0 0 0 0 0 0
// 0 0 0 0 0 0 0 0
// 3
