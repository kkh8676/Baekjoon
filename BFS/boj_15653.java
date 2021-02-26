// 48min Success!!
// 12280 KB, 80 ms

import java.io.*;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;

class boj_15653{
	static int N,M;
	static String[][] board;
	static int[][][][] isV;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	

	static int srr, src, sbr, sbc, HR, HC;
	static int ANSWER;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		board = new String[N][M];
		isV = new int[N][M][N][M];

		for (int n=0; n<N; n++) {
			String[] str = br.readLine().split("");
			for (int m=0; m<M; m++) {
				board[n][m] = str[m];
				if(board[n][m].equals("O")){
					HR = n; HC = m;
				}

				if(board[n][m].equals("B")){
					sbr = n; sbc = m;
					board[n][m] = ".";
				}

				if(board[n][m].equals("R")){
					srr = n; src = m;
					board[n][m] = ".";
				}

			}
		}// end of getting information

		ANSWER = -1;

		BFS();

		System.out.println(ANSWER);
	}// end of main method

	public static int[][] move(int rr, int rc, int br, int bc, int t){
		int[][] result = new int[2][2];

		// red ball moving
		int nrr = rr;
		int nrc = rc;

		while(true){
			nrr = nrr + dr[t];
			nrc = nrc + dc[t];

			if(board[nrr][nrc].equals("#")){
				nrr = nrr - dr[t];
				nrc = nrc - dc[t];
				break;
			} else if(board[nrr][nrc].equals("O")) break;
			else{
				continue;
			}
		}

		// blue ball moving
		int nbr = br;
		int nbc = bc;

		while(true){
			nbr = nbr + dr[t];
			nbc = nbc + dc[t];

			if(board[nbr][nbc].equals("#")){
				nbr = nbr - dr[t];
				nbc = nbc - dc[t];
				break;
			} else if(board[nbr][nbc].equals("O")) break;
			else{
				continue;
			}
		}

		// if red ball or blue ball reach the hole, return that locations......
		if((nrr == HR && nrc == HC) || (nbr == HR && nbc == HC)){
			result[0][0] = nrr; result[0][1] = nrc;
			result[1][0] = nbr; result[1][1] = nbc;
			return result;

		}

		// if two balls are not in the hole, but location of two is same
		if(nrr == nbr && nrc == nbc){
			if(t == 0){
				// upper way
				if(rr > br) nrr +=1;
				else nbr += 1;

			} else if(t == 1){
				if(rr > br) nbr -= 1;
				else nrr -= 1;

			} else if(t == 2){
				if(rc < bc) nbc += 1;
				else nrc += 1;

			} else{
				if(rc < bc) nrc -= 1;
				else nbc -= 1;
			}
		}
		// correct it......

		result[0][0] = nrr; result[0][1] = nrc; 
		result[1][0] = nbr; result[1][1] = nbc; 

		return result;
	}// end of move method

	public static void BFS(){
		Queue<Integer> q = new LinkedList<>();

		q.add(srr); q.add(src); q.add(sbr); q.add(sbc); q.add(0);// current time
		isV[srr][src][sbr][sbc] = 1;

		while(!q.isEmpty()){
			int crr = q.remove(); int crc = q.remove();
			int cbr = q.remove(); int cbc = q.remove();
			int time = q.remove();

			// ending condition
			if(crr == HR && crc == HC){
				ANSWER = time;
				break;
			}

			for (int i=0; i<4; i++) {

				int[][] nextLoc = move(crr, crc, cbr, cbc, i);
				int nrr = nextLoc[0][0]; int nrc = nextLoc[0][1];
				int nbr = nextLoc[1][0]; int nbc = nextLoc[1][1];

				if(nrr < 1 || nrr >= N-1 || nrc < 1 || nrc >= M-1) continue;
				if(nbr < 1 || nbr >= N-1 || nbc < 1 || nbc >= M-1) continue;
				if(isV[nrr][nrc][nbr][nbc] == 1) continue;

				// Branch here.........%%%%%%%%%%%%%%%%%
				if(nbr == HR && nbc == HC) continue;
				// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

				q.add(nrr); q.add(nrc); q.add(nbr); q.add(nbc); q.add(time+1);
				isV[nrr][nrc][nbr][nbc] = 1;
			}
		}// end of while loop
	}// end of BFS method
}// end of class 