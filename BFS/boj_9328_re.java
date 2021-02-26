// 57 min Time OverFlow.......
// 1hr 30 min with Help in Question Board....
// 124940 KB, 768 ms....

import java.io.*;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.HashSet;



class boj_9328_re{
	static int T,H,W;
	static String[][] board;
	static int[][] got_doc_arr;
	static int[][] isV;
	static Queue<Integer> q;
	static Queue<Integer> closedDoor;
	static Queue<Integer> temp;

	static String lower = "[a-z]";
	static String upper = "[A-Z]";

	//static HashSet<String> cand_key;
	static HashSet<String> got_key;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	static int ANSWER;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();

		T = Integer.parseInt(st.nextToken());

		for (int t=0; t<T; t++) {
			st = new StringTokenizer(br.readLine());
			H = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());

			board = new String[H][W];
			isV = new int[H][W];
			got_doc_arr = new int[H][W];

			//cand_key = new HashSet<>();
			got_key = new HashSet<>();

			closedDoor = new LinkedList<>();
			temp = new LinkedList<>();

			for (int r=0; r<H; r++) {
				String[] str = br.readLine().split("");
				for (int c=0; c<W; c++) {
					board[r][c] = str[c];
				}
			}// end of getting board information

			String[] gotKeys = br.readLine().split("");
			for (int i=0; i<gotKeys.length; i++) {
				got_key.add(gotKeys[i]);
			}// end of getting pre had key information

			ANSWER = 0;

			while(true){
				boolean gotNewKey = false;

				BFS(0,0);
				BFS(0,W-1);
				BFS(H-1,0);
				BFS(H-1,W-1);

				for (int r=1; r<H-1; r++) {
					BFS(r,0);
					BFS(r,W-1);
				}

				for (int c=1; c<W-1; c++) {
					BFS(0,c);
					BFS(H-1,c);
				}

				if(!gotNewKey) break;
			}// end of while loop

			sb.append(ANSWER).append("\n");



		}// end of all of the test case

		System.out.println(sb.toString());

	}// end of main method

	public static boolean BFS(int sr, int sc){
		// Starting point 5 way
		boolean result = false;

		if(isV[sr][sc] == 1) return result;

		if(board[sr][sc].equals(".")){

		} else if(board[sr][sc].equals("*")){
			return false;

		} else if(board[sr][sc].matches(lower)){
			if(got_key.add(board[sr][sc])){
				// can go but un opened door adding the queue!
				while(!closedDoor.isEmpty()){
					int cdr = closedDoor.remove();
					int cdc = closedDoor.remove();

					if(got_key.contains(board[cdr][cdc].toLowerCase())){
						q.add(cdr);
						q.add(cdc);
					} else{
						temp.add(cdr);
						temp.add(cdc);
					}
				}

				while(!temp.isEmpty()){
					closedDoor.add(temp.remove());
					closedDoor.add(temp.remove());
				}

				result = true;
			} 

		} else if(board[sr][sc].matches(upper)){
			if(!got_key.contains(board[sr][sc].toLowerCase())){
				closedDoor.add(sr);
				closedDoor.add(sc);
				return false;	
			} 

		}else{
			// document
			if(got_doc_arr[sr][sc] == 0) {
				got_doc_arr[sr][sc] = 1;
				ANSWER++;
			}
		}

		// Make new queue instance
		// and add starting point
		q = new LinkedList<>();

		q.add(sr); q.add(sc);
		isV[sr][sc] = 1;

		while(!q.isEmpty()){
			int cr = q.remove();
			int cc = q.remove();

			// Here, we got 4 cases.
			// "." 
			// Upper case alphabet : came here because we got the keys for that door
			// lower case alphabet : added this case with dealing gotKey.....
			// "$"                 : added this case with dealing got_key_arr and ANSWER.....

			for (int i=0; i<4; i++) {
				int nr = cr + dr[i];
				int nc = cc + dc[i];

				// out of bound 
				if(nr < 0 || nr >= H || nc < 0 || nc >= W) continue;

				// already been there
				if(isV[nr][nc] == 1) continue;

				if(board[nr][nc].equals(".")){
					// new location is empty 
				} else if(board[nr][nc].equals("$")){
					// new location is document
					if(got_doc_arr[nr][nc] == 0){
						got_doc_arr[nr][nc] = 1;
						ANSWER++;
					}
				} else if(board[nr][nc].matches(lower)){
					//new location is key
					if(got_key.add(board[nr][nc])){
						while(!closedDoor.isEmpty()){
							int cdr = closedDoor.remove();
							int cdc = closedDoor.remove();

							if(got_key.contains(board[cdr][cdc].toLowerCase())){
								q.add(cdr);
								q.add(cdc);
							} else{
								temp.add(cdr);
								temp.add(cdc);
							}
						}

						while(!temp.isEmpty()){
							closedDoor.add(temp.remove());
							closedDoor.add(temp.remove());
						}	
						result = true;
					} 

				} else if(board[nr][nc].matches(upper)){
					///new location is Door
					if(!got_key.contains(board[nr][nc].toLowerCase())){
						closedDoor.add(nr); closedDoor.add(nc); 
						continue;
					}

				} else{
					// new location is wall
					continue;
				}

				q.add(nr);
				q.add(nc);
				isV[nr][nc] = 1;
			}// end of 4 way moving for loop
		}// while q is not empty while loop

		return result;
	}// end of BFS method

	public static void initialize(int[][] arr){
		for (int r=0; r<arr.length; r++) {
			Arrays.fill(arr[r], 0);
		}
	}// end of initialize method


}// end of class