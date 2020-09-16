// Basic BFS problem
// memory limited, first
// In introduction to algorithm, used WHITE and GRAY and BLACK

// in this problem, we don't have to use BLACK and GRAY means that vertex is already visiited........

// 50min


import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

class boj_2178{
	static int N;
	static int M;
	static int[][] map;
	static int[][] isVisited;

	static final int WALL = 0;
	static final int ROAD = 1;

	static int ANSWER = Integer.MAX_VALUE;
	static int COUNT = 0; // ? do we need it??

	static Queue<Integer> q;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);

		N = scan.nextInt();
		M = scan.nextInt();
		scan.nextLine();
		map = new int[N][M];
		isVisited = new int[N][M];


		for (int n=0; n<N; n++) {
			String[] strAry = scan.nextLine().split("");
			
			for (int m=0; m<M; m++) {
				map[n][m] = Integer.parseInt(strAry[m]);
				isVisited[n][m] = 0;
			}
		}// end of making map

		
		q = new LinkedList<>();

		BFS(0,0);

		System.out.println(ANSWER);
	}// end of main method



	// how do i add the visited condition??
	public static void BFS(int row, int col){
		isVisited[row][col] = 1;
		q.add(row);
		q.add(col);
		q.add(1);

		while(!q.isEmpty()){
			//System.out.println("WHERE?");
			int curRow = q.remove();
			int curCol = q.remove();
			int curCount = q.remove();

			if(curRow == N-1 && curCol == M-1){
				ANSWER = curCount;
				break;
			}

			for (int i=0; i<4; i++) {
				// if adjacent vertex is beyond limit of array, continue;
				if(curRow + dr[i] <0 || curRow + dr[i] >=N || curCol + dc[i] <0 || curCol + dc[i] >= M) continue;

				if(map[ curRow + dr[i] ][ curCol + dc[i] ] == ROAD && isVisited[ curRow + dr[i] ][ curCol + dc[i] ] == 0){
					q.add( curRow + dr[i] );
					q.add( curCol + dc[i] );
					q.add( curCount + 1 );

					isVisited[ curRow + dr[i] ][ curCol + dc[i] ] = 1;

				}
			} // end of 4 time adjacent vertex getting in the queue

			
		} // end of queue is not empty while loop


	} // end of BFS method
}// end of class 