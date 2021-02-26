// Failed in 58 min......
// What's wrong????????
// Success in 1hr 2min
// Memory 31492KB
// Time   1116ms

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.io.*;
import java.util.StringTokenizer;

class boj_4991{
	static int C,R;
	static String[][] board;
	static int[][] start_d;
	static ArrayList<int[][]> list;
	static ArrayList<Integer> rList;
	static ArrayList<Integer> cList;

	static int sr;
	static int sc;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	static int[] seq;
	static int[] dfs_v;

	static int ANSWER;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		while(true){
			st = new StringTokenizer(br.readLine());
			C = Integer.parseInt(st.nextToken());
			R = Integer.parseInt(st.nextToken());

			// Case ending condition
			if(C==0 && R == 0) break;

			board = new String[R][C];
			start_d = new int[R][C];
			ANSWER = Integer.MAX_VALUE;

			list = new ArrayList<>();
			rList = new ArrayList<>();
			cList = new ArrayList<>();
			// We can get array at index by list.get(index number)

			for (int r=0; r<R; r++) {
				String[] curStr = br.readLine().split("");
				for (int c=0; c<C; c++) {
					board[r][c] = curStr[c];

					if(board[r][c].equals("*")){
						list.add(new int[R][C]);
						rList.add(r);
						cList.add(c);

					}
					if(board[r][c].equals("o")){
						sr = r;
						sc = c;
					}
				}
			}// end of getting information

			BFS(-1);
			//printD(-1);

			for (int i=0; i<list.size(); i++) {
				//System.out.println();
				BFS(i);
				//printD(i);
			}
			seq = new int[list.size()+1];
			seq[0] = -1;
			dfs_v = new int[list.size()];
			DFS(list.size(), 0);

			ANSWER = ANSWER == Integer.MAX_VALUE? -1 : ANSWER;
			sb.append(""+ANSWER+"\n");

		}// end of case while loop

		System.out.println(sb.toString());
		br.close();
		//bw.close();
	}// end of main method

	public static void DFS(int capa, int round){
		// ending condition

		if(capa == round){
			int dist = 0;
			for (int l=0; l<seq.length-1; l++) {
				//System.out.print(seq[l]+" ");
				// current index seq[l]
				// next index seq[l+1]
				int[][] curDarr;
				if(seq[l] == -1) curDarr = start_d;
				else curDarr = list.get(seq[l]);

				int nextD = curDarr[ rList.get(seq[l+1]) ][ cList.get(seq[l+1]) ];
				if(nextD == Integer.MAX_VALUE || dist == Integer.MAX_VALUE){
					dist = Integer.MAX_VALUE;
					break;
				}
				else dist = dist + curDarr[ rList.get(seq[l+1]) ][ cList.get(seq[l+1]) ];
				
			}

			ANSWER = ANSWER > dist ? dist : ANSWER;
			//System.out.println();
			return;
		}

		for (int i=0; i<capa; i++) {
			if(dfs_v[i] == 0){
				dfs_v[i] = 1;
				seq[round+1] = i;
				DFS(capa, round+1);
				dfs_v[i] = 0;
				seq[round+1] = 0;
			}
		}


	}

	public static void BFS(int index){
		int[][] d;
		if(index==-1) d = start_d;
		else d = list.get(index);

		initialize_D(d);

		Queue<Integer> q = new LinkedList<>();
		int[][] isV = new int[R][C];

		// initializing the queue
		if(index == -1){
			q.add(sr);
			q.add(sc);
			q.add(0);
			isV[sr][sc] = 1;
			d[sr][sc] = 0;
		} else{
			q.add(rList.get(index));
			q.add(cList.get(index));
			q.add(0);
			isV[rList.get(index)][cList.get(index)] = 1;
			d[rList.get(index)][cList.get(index)] = 0;
		}

		while(!q.isEmpty()){
			int cr = q.remove();
			int cc = q.remove();
			int cd = q.remove();

			for (int i=0; i<4; i++) {
				int nr = cr + dr[i];
				int nc = cc + dc[i];

				if(nr < 0 || nr >= R || nc < 0 || nc >= C || isV[nr][nc] == 1) continue;

				if(!board[nr][nc].equals("x")){
					q.add(nr);
					q.add(nc);
					q.add(cd+1);

					isV[nr][nc] = 1;
					d[nr][nc] = cd+1;	
				}
			}// end of 4 way visiting
		}// end of while loop

	}// end of BFS method

	public static void printD(int index){
		int[][] arr;
		if(index==-1) arr = start_d;
		else arr = list.get(index);

		for (int r=0; r<R; r++) {
			for (int c=0; c<C; c++) {
				if(arr[r][c]== Integer.MAX_VALUE) System.out.print("X ");
				else System.out.print(arr[r][c]+" ");
				
			}
			System.out.println();
		}
	}// end of printD method

	public static void initialize_D(int[][] arr){
		for (int r=0; r<R; r++) {
			for (int c=0; c<C; c++) {
				arr[r][c] = Integer.MAX_VALUE;
			}
		}
	}
}// end of class 