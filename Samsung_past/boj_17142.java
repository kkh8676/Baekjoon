// Took 4 Hr.......
// Ha , Not this time maybe....

import java.io.*;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;

class boj_17142{
	static int N;
	static int M;

	static int[][] map;
	static int[][] isV;
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	// we have to fill this empty cell with virus
	static int EMPTY;

	static int TOTAL_VIRUS;

	static int ANSWER;

	static int[][] virus;

	static int[] selected;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] NM = br.readLine().split(" ");
		N = Integer.parseInt(NM[0]);
		M = Integer.parseInt(NM[1]); // activating virus number 

		map = new int[N+1][N+1];
		isV = new int[N+1][N+1];
		EMPTY = 0;
		TOTAL_VIRUS = 0;
		ANSWER = Integer.MAX_VALUE;

		Queue<Integer> vq = new LinkedList<>();

		for (int row=1; row<=N; row++) {
			String[] curRow = br.readLine().split(" ");
			for (int col=1; col<=N; col++) {
				map[row][col] = Integer.parseInt(curRow[col-1]);
				if(map[row][col] == 0) EMPTY++;
				if(map[row][col] == 1) isV[row][col] = 1;
				if(map[row][col] == 2){
					TOTAL_VIRUS++;
					vq.add(row);
					vq.add(col);
				}
			}
		} // end of getting map

		virus = new int[TOTAL_VIRUS+1][2];
		for (int num=1; num<=TOTAL_VIRUS; num++) {
			virus[num][0] = vq.remove();
			virus[num][1] = vq.remove();
		} // We got the total virus

		// how can i get the combination number??

		// BFS for all permutation

		selected = new int[M+1];
		

		for (int i=1; i<=TOTAL_VIRUS; i++) {
			DFS(i,1);
		}


		if(ANSWER == Integer.MAX_VALUE) ANSWER = -1;
		System.out.println(ANSWER);




	}// end of main method 

	public static void BFS(int[] selected){
		// get the combination num of combination first virus
		// in the selected 
		// M virus index in it........
		// Initialize that virus and Queue
		Queue<Integer> q = new LinkedList<>();
		int[][] cMap = new int[N+1][N+1];
		int[][] cV = new int[N+1][N+1];
		int copiedEmpty = EMPTY;
		int[][] time = new int[N+1][N+1];
		int max = 0;

		for (int n=1; n<=N; n++) {
			Arrays.fill(time[n], -1);
		}

		copyMap(map,cMap,isV,cV); // current map 0 empty, 1 wall ,2 non activated virus, 3 activated virus

		for (int index=1; index<=M; index++) {
			// selected[index] means virus index 
			int vIndex = selected[index];
			q.add(virus[vIndex][0]);
			q.add(virus[vIndex][1]);
			q.add(EMPTY);
			q.add(0);
			time[ virus[vIndex][0] ][ virus[vIndex][1] ] = 0;
			
			cV[ virus[vIndex][0] ][ virus[vIndex][1] ] = 1; // activated virus locations are visited! 

		} 

		while(!q.isEmpty()){
			int cVR = q.remove();
			int cVC = q.remove();
			int cE = q.remove();
			int cT = q.remove();
			
			
			if(cE == 0){
				ANSWER = ANSWER > cT ? cT : ANSWER;
			}
			// checking adjacent vertex in 4 way
			for (int d=0; d<4; d++) {
				int nR = cVR + dr[d];
				int nC = cVC + dc[d];

				if(nR < 1 || nR > N || nC < 1 || nC > N) continue; // out of bounds
				if(cV[nR][nC] != 1){
					

					q.add(nR);
					q.add(nC);
					if(cMap[nR][nC] == 0) copiedEmpty --;
					q.add(copiedEmpty);
					q.add(cT+1);
					cV[nR][nC] = 1;
					cMap[nR][nC] = 2;
					time[nR][nC] = cT+1;
					
					//printMap(time);

				}
			}


		} // end of while 
		
		// come down here, ANSWER is not modified.......

	}// end of BFS method

	public static void copyMap(int[][] oMap, int[][] cMap, int[][] oV, int[][] cV){
		for (int row=1; row<=N; row++) {
			for (int col=1; col<=N; col++) {
				cMap[row][col] = map[row][col];
				cV[row][col] = oV[row][col];
			}
		}// enf of outer row for loop

		// now just the map 


	}// end of copyMap method

	public static void printMap(int[][] cMap){
		for (int row=1; row<=N; row++) {
			for (int col=1; col<=N; col++) {
				System.out.print(cMap[row][col]+" ");
			}
			System.out.println();
		}
	} // end of printMAp method



	public static void DFS(int index, int round){
		
		selected[round] = index;

		if(round == M){
			// for (int s=1; s<=M; s++) {
			// 	System.out.print(selected[s]+" ");
			// }
			// System.out.println();
			BFS(selected);

			return;
		}

		for (int i=index+1; i<=TOTAL_VIRUS; i++) {
			if(TOTAL_VIRUS-i+1>=M-round){
				//System.out.println("DFS at i "+i+" at round "+(round+1));
				DFS(i,round+1);
			}
		}
	}// end of DFS method


}// end of class 