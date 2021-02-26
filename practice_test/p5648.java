// 50 min failed......
// 58 min Time Overflow....... ??? Shit!
// 1hr 5min Runtime error?
// 1hr 30 min Time Overflow...

import java.util.StringTokenizer;
import java.util.Queue;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Arrays;


class p5648{
	static int T,N;
	static int ANSWER;

	static int[] dr = {1,-1,0,0};
	static int[] dc = {0,0,-1,1};

	static HashMap<Integer, HashMap> map;
	

	static Atom[] atoms;

	static Atom[][] board;

	static Atom[][] arr;

	static int TOTAL_DEAD;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder answer = new StringBuilder();
		StringTokenizer st;
		StringBuilder key;

		T = Integer.parseInt(br.readLine());
		atoms = new Atom[1000];
		//map = new HashMap<>();
		arr = new Atom[4001][4001];


		for (int t=1; t<=T; t++) {
			N = Integer.parseInt(br.readLine());
			TOTAL_DEAD = 0;

			Arrays.fill(atoms, null);
			initialize();
			//board = new Atom[4001][4001];

			for (int n=0; n<N; n++) {
				st = new StringTokenizer(br.readLine());

				int x = (Integer.parseInt(st.nextToken()) + 1000 )*2; // column
				int y = (Integer.parseInt(st.nextToken()) + 1000 )*2; // row
				int dir = Integer.parseInt(st.nextToken());
				int energy = Integer.parseInt(st.nextToken());

				atoms[n] = new Atom(y,x,energy,dir);

				//System.out.println("R"+y+" C"+x+" initialized");
			}// end of getting information

			int round = 0;
			
			while(round < 4001){
				// atom moving!!
				if(N - TOTAL_DEAD < 2) break;
				//map.clear();

				//initialize();
				//qArr = new Queue[4001][4001];
				// moving algorithm by one atom
				for (int i=0; i<N; i++) {
					// if atom is already dead, do nothing
					Atom atom = atoms[i];

					if(atom.dead == 1){
						arr[atom.r][atom.c] = null;
						continue;
					}

					// came here because current atom is not dead
					int cr = atom.r; int cc = atom.c; int cd = atom.DIR; int ce = atom.ENERGY;
					if(cr < 0 || cr > 4000 || cc < 0 || cc > 4000) continue;

					int nr = cr + dr[cd];
					int nc = cc + dc[cd];

					atom.r = nr;
					atom.c = nc;

					arr[cr][cc] = null;

					if(nr < 0 || nr > 4000 || nc < 0 || nc > 4000) continue;

					//key = new StringBuilder();
					//key.append(atom.r).append(" ").append(atom.c);

					// if there is no queue, make the queue and adding this atom in it.
					//Queue<Atom> q = map.get(key.toString());
					// HashMap<Integer, Queue> inMap = map.getOrDefault(nr, null);
					// Queue<Atom> q = null;
					// if(inMap != null) q = inMap.get(nc);
					// else{
					// 	inMap = new HashMap<Integer, Queue>();
					// }
					//Queue<Atom> q = qArr[atom.r][atom.c];

					Atom prev = arr[nr][nc];

					if(prev == null){
						arr[nr][nc] = atom;
						//q = new LinkedList<>();
						//q.add(atom);
						//map.put(key.toString(), q);
						// inMap.put(nc, q);
						// map.put(nr, inMap);
					}else{
						// already queue there
						// Atom prev = q.peek();
						// if(prev.dead != 1){
						// 	prev.dead = 1;
						// 	TOTAL_DEAD++;
						// }
						// atom.dead = 1;
						// TOTAL_DEAD++;
						// q.add(prev);
						if(prev.dead != 1){
							prev.dead = 1;
							//TOTAL_DEAD++;
						}
						arr[nr][nc] = atom;
						atom.dead = 1;
						//TOTAL_DEAD++;
					}

				}// all the atom is moved....
				//System.out.println(round);
				// last process	
				round++;
			}// end of while loop

			ANSWER = 0;
			for (int i=0; i<N; i++) {
				Atom atom = atoms[i];
				if(atom.dead == 1) ANSWER += atom.ENERGY;
				//System.out.println("Current R" + atom.r+" C"+atom.c+ "with ENERGY"+atom.ENERGY);
			}

			answer.append("#").append(t).append(" ").append(ANSWER).append("\n");
		}// end of test case 


		System.out.println(answer.toString());
		br.close();
	}// end of main method

	public static void initialize(){
		for (int i=0; i<4001; i++) {
			Arrays.fill(arr[i], null);
		}
	}// end of initialize method
}// end of class

class Atom{
	int r,c,ENERGY,DIR,dead;
	String key;
	public Atom(int r, int c, int energy, int dir){
		this.r = r;
		this.c = c;
		this.ENERGY = energy;
		this.DIR = dir;
		this.dead = 0;

	}// end of constructor
}// end of Atom class 

/*

2
4
-1000 0 3 5
1000 0 2 3
0 1000 1 7
0 -1000 0 9
4
-1 1 3 3
0 1 1 1
0 0 2 2
-1 0 0 9
*/
