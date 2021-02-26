// 4000 Simulation No?

import java.util.StringTokenizer;
import java.util.Queue;
import java.io.*;
import java.util.LinkedList;
import java.util.Arrays;

class p5648_re{
	static int T,N;
	static int ANSWER;

	static int[] dr = {1,-1,0,0};
	static int[] dc = {0,0,-1,1};

	static int[][] board;

	static Queue<Atom> q;
	static Queue<Integer> iq;

	static Atom[] atoms;

	static int cnt;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder answer = new StringBuilder();
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());
		board = new int[4001][4001];
		for (int t=1; t<=T; t++) {

			N = Integer.parseInt(br.readLine());

			//atoms = new Atom[N];
			for (int i=0; i<board.length; i++) {
				Arrays.fill(board[i],0);
			}

			q = new LinkedList<>();
			//iq = new LinkedList<>();

			for (int n=0; n<N; n++) {
				st = new StringTokenizer(br.readLine());

				int x = (Integer.parseInt(st.nextToken()) + 1000 )*2; // column
				int y = (Integer.parseInt(st.nextToken()) + 1000 )*2; // row
				int dir = Integer.parseInt(st.nextToken());
				int energy = Integer.parseInt(st.nextToken());

				if(energy == 0) energy = 101;
				Atom newAtom = new Atom(y,x,energy,dir);

				q.add(newAtom);
				//iq.add(y); iq.add(x); iq.add(energy); iq.add(dir);
				// board[y][x] = energy;
				//System.out.println("R"+y+" C"+x+" initialized");
			}// end of getting information

			cnt = 0;
			ANSWER = 0;

			BFS();

			ANSWER = ANSWER - cnt * 101;
			answer.append("#").append(t).append(" ").append(ANSWER).append("\n");
		}// end of test case

		System.out.println(answer.toString());

	}// end of main method

	public static void BFS(){
		// first atoms are all added in the q

		while(!q.isEmpty()){
			Atom removed = q.remove();

			//if(iq.size() == 4) break;


			//int cr = iq.remove();
			int cr = removed.r;
			//int cc = iq.remove();
			int cc = removed.c;

			//int ce = iq.remove();
			int ce = removed.ENERGY;

			//int cd = iq.remove();
			int cd = removed.DIR;


			if(board[ cr ][ cc ] > ce){
				// current position atom energy is larger than current Energy means there is collision
				// how can i take the next level??
				//System.out.println("R"+removed.r+ " C"+removed.c+" has collided");
				if(ce == 101) cnt++;
				ANSWER += board[cr][cc];	
				
				board[cr][cc] = 0;
				continue;
			} 

			// came here because there's no collision
			
			board[ cr ][ cc ] = 0;

			// move!
			int nr = cr + dr[cd];
			int nc = cc + dc[cd];
			removed.r = nr; removed.c = nc;


			//System.out.println("Current R"+prevR+" C"+prevC+" moved to "+"R"+nextR+" C"+nextC);

			if(nr < 0 || nr > 4000 || nc < 0 || nc > 4000) continue;

			// no atom in nextR, nextC
			if(board[ nr ][ nc ] == 0){
				board[ nr ][ nc ] = ce;
				q.add(removed);
				//iq.add(nr);  iq.add(nc); iq.add(ce); iq.add(cd);
			} else{
				//already existed atom in there.....
				if (ce == 101) cnt++;
				board[ nr ][ nc ]  += ce;
				
			}

		}// end of while 
	}// end of BFS method
}// end of class

class Atom{
	int r,c,ENERGY,DIR,dead;

	public Atom(int r, int c, int energy, int dir){
		this.r = r;
		this.c = c;
		this.ENERGY = energy;
		this.DIR = dir;
		this.dead = 0;
	}// end of constructor
}