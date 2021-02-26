// Took 31 min ......
// Reviewing!! 
// Thinking.....

import java.util.Queue;
import java.util.LinkedList;
import java.io.*;
import java.util.StringTokenizer;

class boj_3055_review{
	static int R,C;
	static String[][] map;
	static int[][] d;
	static int[][] isV;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	static int tr, sr;
	static int tc, sc;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		map = new String[R+1][C+1];
		d = new int[R+1][C+1];
		isV = new int[R+1][C+1];

		tr = -1;
		tc = -1;

		for (int r=1; r<=R; r++) {
			String[] curStr = br.readLine().split("");
			for (int c=1; c<=C; c++) {
				map[r][c] = curStr[c-1];
				if(map[r][c].equals("D")){
					//System.out.println("Hello!");
					tr = r;
					tc = c;
					d[r][c] = Integer.MAX_VALUE;
				}
				if(map[r][c].equals("S")){
					sr = r;
					sc = c;
				}
			}
		}// end of getting information

		BFS();

		//System.out.println("TR"+tr+" TC"+tc);
		if(d[tr][tc] == Integer.MAX_VALUE){
			System.out.println("KAKTUS");
		} else{
			System.out.println(d[tr][tc]);
		}

		br.close();

	}// end of main method

	public static void BFS(){
		Queue<Integer> q = new LinkedList<>();

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
		}// end of pushing water location in the queue

		q.add(sr);
		q.add(sc);
		q.add(-1);
		q.add(0);
		d[sr][sc] = 0;
		isV[sr][sc] = 1;

		while(!q.isEmpty()){
			int cr = q.remove();
			int cc = q.remove();
			int ct = q.remove();
			int cd = q.remove();

			int nd = cd == Integer.MAX_VALUE? Integer.MAX_VALUE : cd +1 ;

			for (int i=0; i<4; i++) {
				int nr = cr + dr[i];
				int nc = cc + dc[i];

				if(nr < 1 || nr > R || nc < 1 || nc > C) continue;

				if(isV[nr][nc] == 1) continue;

				if(ct == 1 && !map[nr][nc].equals("D") && !map[nr][nc].equals("X")){
					// Water diffusing
					q.add(nr);
					q.add(nc);
					q.add(ct);
					q.add(nd);
					d[nr][nc] = nd;
					isV[nr][nc] = 1;

				} else if( ct == -1 && !map[nr][nc].equals("X")){
					q.add(nr);
					q.add(nc);
					q.add(ct);
					q.add(nd);
					d[nr][nc] = nd;
					isV[nr][nc] = 1;
				}
			}
		}// end of while
	}// end of BFS method

	public static void printD(){
		for (int r=1; r<=R; r++) {
			for (int c=1; c<=C; c++) {
				if(d[r][c] == Integer.MAX_VALUE){
					System.out.print("X ");
				}
				else{
					System.out.print(d[r][c]+" ");
				}
			}
			System.out.println();
		}
	}


}// end of class 