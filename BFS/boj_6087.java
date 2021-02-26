// 1hr 25min failed.......
// Different Type problem of BFS.....

import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;
import java.io.*;

class boj_6087{
	static int W,H;
	static String[][] map;
	static int[][] isV;
	static int[][][] dist_count;
	static int[][] cLoc;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		W = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());

		map = new String[H][W];
		isV = new int[H][W];
		dist_count = new int[H][W][2];
		cLoc = new int[2][2];

		int cCount = 0;
		for (int h=H-1; h>=0; h--) {
			String[] curStr = br.readLine().split("");
			for (int w=0; w<W; w++) {
				map[h][w] = curStr[w];
				if(map[h][w].equals("C")){
					cLoc[cCount][0] = h;
					cLoc[cCount][1] = w;
					cCount++;
				}
			}
		}// end of getting information

		for (int r=0; r<H; r++) {
			for (int c=0; c<W; c++) {
				dist_count[r][c][1] = Integer.MAX_VALUE;
			}
		}

		BFS();

		System.out.println(dist_count[ cLoc[1][0] ][ cLoc[1][1] ][1] - 1);

		// System.out.println();
		// printCount();
	}// end of main method

	public static void BFS(){
		Queue<Integer> q = new LinkedList<>();

		q.add(cLoc[0][0]); //row
		q.add(cLoc[0][1]); // column
		q.add(-1); // direction
		q.add(0); // change Count
		isV[cLoc[0][0]][cLoc[0][1]] = 1;
		dist_count[ cLoc[0][0] ][ cLoc[0][1] ][1] = 0;

		while(!q.isEmpty()){
			int cr = q.remove();
			int cc = q.remove();
			int cd = q.remove();
			int change = q.remove();

			for (int i=0; i<4; i++) {
				if(cd != i){
					// current row of currnet column checking all 
					int nr = cr + dr[i];
					int nc = cc + dc[i];

					while(true){
						if(nr < 0 || nr >=  H || nc < 0 || nc >= W || map[nr][nc].equals("*")) break;
						if(isV[nr][nc] == 1){
							nr = nr + dr[i];
							nc = nc + dc[i];
							continue;
						} 

						q.add(nr);
						q.add(nc);
						q.add(i);
						q.add(change+1);
						isV[nr][nc] = 1;
						dist_count[nr][nc][1] = change+1;

						nr = nr + dr[i];
						nc = nc + dc[i];
					}
				}// if condition change direction
			}// end of for loop
		}// end of while loop

		
	}// end of BFS method

	public static void printCount(){
			for (int r=H-1; r>=0; r--) {
				for (int c=0; c<W; c++) {
					if(dist_count[r][c][1] == Integer.MAX_VALUE){
						System.out.print("X ");
					} else{
						System.out.print(dist_count[r][c][1]+" ");
					}
					
				}
				System.out.println();
			}
		}

}// end of class 