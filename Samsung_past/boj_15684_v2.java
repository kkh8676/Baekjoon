// Took 1hr 13min
// DFS should be coded neatly more than this
// 0,1,2,3 hard coded DFS capacity
// DFS should be optimized!

import java.io.*;
import java.util.Arrays;

class boj_15684_v2{
	static int N,M,H;
	static int[][] map;
	static int[][] isV;
	static int[] desti;

	static int ANSWER;

	static int[] ldr = {1,0,0};
	static int[] ldc = {0,-1,1};



	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] nmh = br.readLine().split(" ");
		N = Integer.parseInt(nmh[0]);
		M = Integer.parseInt(nmh[1]);
		H = Integer.parseInt(nmh[2]);

		map = new int[H+1][N+1];
		isV = new int[H+1][N+1];
		desti = new int[N+1];

		for (int m=0; m<M; m++) {
			String[] rc = br.readLine().split(" ");
			int r = Integer.parseInt(rc[0]);
			int c = Integer.parseInt(rc[1]);

			map[r][c] = 1;
			map[r][c+1] = -1;

		}// end of getting map information

		ANSWER = -1;

		// for capa 0
		go();
		if(check()){
			ANSWER = 0;
			System.out.println(ANSWER);
			return;
		}

		// for capa 1
		for (int r=1; r<=H; r++) {
			for (int c=1; c<N; c++) {
				if(map[r][c] == 0 && map[r][c+1] == 0 && isV[r][c] == 0){
					DFS(r,c,1,1);
					map[r][c] = 0;
					map[r][c+1] = 0;
					isV[r][c] = 0;
				}
			}
		}

		if(ANSWER != -1){
			System.out.println(ANSWER);
			return;
		}

		// for capa 2
		for (int r=1; r<=H; r++) {
			for (int c=1; c<N; c++) {
				if(map[r][c] == 0 && map[r][c+1] == 0 && isV[r][c] == 0){
					DFS(r,c,2,1);
					map[r][c] = 0;
					map[r][c+1] = 0;
					isV[r][c] = 0;
				}
			}
		}

		if(ANSWER != -1){
			System.out.println(ANSWER);
			return;
		}

		// for capa 3
		for (int r=1; r<=H; r++) {
			for (int c=1; c<N; c++) {
				if(map[r][c] == 0 && map[r][c+1] == 0 && isV[r][c] == 0){
					//System.out.println("First Added R"+r+" C"+c);
					DFS(r,c,3,1);
					map[r][c] = 0;
					map[r][c+1] = 0;
					isV[r][c] = 0;
				}
			}
		}

		if(ANSWER != -1){
			System.out.println(ANSWER);
			return;
		}

		// go();
		// printDesti();
		// System.out.println(check());


		System.out.println(-1);
	}// end of main method


	public static void DFS(int r, int c, int capa, int round){
		map[r][c] = 1;
		map[r][c+1] = -1;
		isV[r][c] = 1;

		if(capa == round){
			//System.out.println("DFS ended");
			go();
			if(check()){
				ANSWER = capa;
			}
			return;
		}

		for (int row=r; row<=H; row++) {
			for (int col=1; col<N; col++) {
				//System.out.println("Looking R"+row+" C"+col);
				if(map[row][col] == 0 && map[row][col+1]==0 && isV[row][col] == 0){
					//System.out.println("Added R"+row+" C"+col);
					DFS(row,col,capa,round+1);
					map[row][col] = 0;
					map[row][col+1] = 0;
					isV[row][col] = 0;
				}
			}
		}
		//System.out.println("DFS ended without Recursion");
	}// end of DFS method

	public static boolean check(){
		boolean result = true;

		// just one element doesn't have the answer, return false
		for (int n=1; n<=N; n++) {
			if(desti[n] != n){
				result = false;
				break;
			}
		}

		return result;
	}// end of check method

	public static void go(){
		// for every vertical line.... which is numbered by 1,2,3,4...,N
		// modify the desti array

		for (int n=1; n<=N; n++) {
			int cr = 1;
			int cc = n;

			boolean ph = false;
			while(cr!=H+1){ // cr ends at H+1 line.....
				int cd;
				if(ph || map[cr][cc] == 0){
					cd = 0;
					ph = false;	
				} 
				else if(map[cr][cc] == 1){
					cd = 2;
					ph = true;	
				} 
				else{
					cd = 1;
					ph = true;	
				} 

				cr = cr + ldr[cd];
				cc = cc + ldc[cd];
				//System.out.println("Trapped Here!! with R"+cr+" C"+cc+" D"+cd);
			}

			desti[n] = cc;
		}// end of moving all the vertical line

	}// end of go method o.k


	public static void printMap(){
		for (int r=1; r<=H; r++) {
			for (int c=1; c<=N; c++) {
				System.out.print(map[r][c]+" ");
			}
			System.out.println();
		}
	}// end of printMap method

	public static void printDesti(){
		for (int n=1; n<=N; n++) {
			System.out.print(desti[n] + " ");
		}
		System.out.println();
	}

}// end of class 
