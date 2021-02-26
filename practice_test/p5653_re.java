// Map key setting is bottleNeck for this problem to me......
// "Row Num" + "Col Num" => row 1, col 23 // row 12, col 3 key is same "123"
// So, should append " " in the middle of each coordinate value like, "row num"+" "+"col num"
// so, row 1, col 12 => "1 12" // row 11, col 2 => "11 2"
// with sign - it can be done..... row -1, col 33 =? "-1 33" // row -13, col 3 =? "-13 3"

// comparing already existed cell and new one
// should use "start" time not current time
// current time always revalued by new time, so removed.time == removed.time + 1 can be always true 


import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.io.*;

class p5653_re{
	static int T,N,M,K;
	static HashMap<String, Cell> map;
	static Queue<Cell> q;
	static Queue<Cell> aq;
	static int ANSWER;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st ;
		StringBuilder ansStr = new StringBuilder();
		T = Integer.parseInt(br.readLine());

		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());

			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());

			map = new HashMap<>();
			q = new LinkedList<>();
			aq = new LinkedList<>();

			for (int n=0; n<N; n++) {
				st = new StringTokenizer(br.readLine());
				for (int m=0; m<M; m++) {
					int life = Integer.parseInt(st.nextToken());
					if(life != 0){
						Cell newCell = new Cell(n,m,life,0);
						StringBuilder sb = new StringBuilder();
						sb.append(n).append(" ").append(m);
						map.put(sb.toString(), newCell);
						q.add(newCell);
					}
				}
			}// end of getting information
			ANSWER = 0;
			BFS();
			// System.out.println();
			// while(!aq.isEmpty()){
			// 	Cell alive = aq.remove();
			// 	System.out.println(alive.r+" "+alive.c);
			// }

			//System.out.println(map.size());
			ansStr.append("#").append(t).append(" ").append(ANSWER).append("\n");

		}// end of test case for loop

		System.out.println(ansStr.toString());
	}// end of main method

	public static void BFS(){
		// q is already initialized.......

		while(!q.isEmpty()){	
			Cell removed = q.remove();

			// if removed cell is dead, just continue......
			if(removed.hp == 0) continue;

			// came here means cell is not dead!
			if(removed.time >= K){
				//aq.add(removed);
				ANSWER++;
				continue;
			}

			// activated time! 
			// if activated, reproduct
			if(removed.hp == removed.LIFE){
				// 4way looking!
				for (int i=0; i<4; i++) {
					int nr = removed.r + dr[i];
					int nc = removed.c + dc[i];

					StringBuilder temp = new StringBuilder();
					temp.append(nr).append(" ").append(nc);

					Cell exist = map.get(temp.toString());

					// if next position has no cell, just add it
					if(exist == null){
						Cell adding = new Cell(nr,nc,removed.LIFE, removed.time+1);
						map.put(temp.toString(), adding);
						q.add(adding);
					} else{
						// if next position is already occupied...
						// check if current LIFE is bigger or not
						// if bigger, change the LIFE and hp
						if(exist.START == removed.time+1 && exist.LIFE < removed.LIFE){
							exist.LIFE = removed.LIFE;
							exist.hp = exist.LIFE*2;
						}
					}// end of next position is already occupied or not.
				}// end of 4 way looking
			}// end of if condition

			// just time and hp flows.......
			removed.hp--;
			removed.time++;
			q.add(removed);

		}// end of while loop
	}// end of BFS method

}// end of class

class Cell{
	int r,c,LIFE,hp, time, START;

	public Cell(int r, int c, int LIFE, int time){
		this.r = r;
		this.c = c;
		this.LIFE = LIFE;
		this.hp = LIFE * 2;
		this.time = time;
		this.START = time;
	}// end of constructor
}// end of class Cell