// 1hr 5min failed....
// 1hr 28min failed....

// Map key setting is bottleNeck for this problem to me......
// "Row Num" + "Col Num" => row 1, col 23 // row 12, col 3 key is same "123"
// So, should append " " in the middle of each coordinate value like, "row num"+" "+"col num"
// so, row 1, col 12 => "1 12" // row 11, col 2 => "11 2"
// with sign - it can be done..... row -1, col 33 =? "-1 33" // row -13, col 3 =? "-13 3"

 import java.util.ArrayList;
 import java.util.LinkedList;
 import java.util.Queue;
 import java.io.*;
 import java.util.StringTokenizer;
 import java.util.HashMap;


 class p5653{
 	static int T,N,M,K;
 	static int[][] board;
 	static StringBuilder sb;
 	static int ANSWER;

 	static int[] dr = {-1,1,0,0};
 	static int[] dc = {0,0,-1,1};

 	static Queue<Cell> q;
 	static HashMap<String, Cell> map;
 	static Queue<Cell> aq;

 	public static void main(String[] args) throws IOException {
 		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 		sb = new StringBuilder();

 		StringTokenizer st = new StringTokenizer(br.readLine());

 		T = Integer.parseInt(st.nextToken());

 		for (int t=1; t<=T ; t++) {
 			// algorithm here!!
 			ANSWER = 0;

 			st = new StringTokenizer(br.readLine());
 			N = Integer.parseInt(st.nextToken());
 			M = Integer.parseInt(st.nextToken());
 			K = Integer.parseInt(st.nextToken());

 			board = new int[N][M];

 			map = new HashMap<>();
 			q = new LinkedList<>();
 			aq = new LinkedList<>();

 			for (int n=0; n<N; n++) {
 				st = new StringTokenizer(br.readLine())	;
 				for (int m=0; m<M; m++) {
 					board[n][m] = Integer.parseInt(st.nextToken());
 					if(board[n][m] != 0){
 						Cell newCell = new Cell(n,m,board[n][m],0);
 						StringBuilder str = new StringBuilder();
 						str.append(n).append(" ").append(m);
 						map.put(str.toString(), newCell);
 						q.add(newCell);
 					}
 				}
 			}// end of getting information

 			BFS();

 			

 			// while(!aq.isEmpty()){
 			// 	Cell removed = aq.remove();
 			// 	System.out.println(removed.r+" "+removed.c);
 			// }

 			sb.append("#").append(t).append(" ").append(ANSWER).append("\n");
 		}// end of test case

 		

 		
 		
 		System.out.println(sb.toString());
 		br.close();
 	}// end of main method

 	public static void BFS(){
 		// q is already initialized.......

 		while(!q.isEmpty()){
 			Cell cur = q.remove();
 			StringBuilder str = new StringBuilder();
 			str.append(cur.r).append(cur.c);

 			int curTime = cur.time;
 			// if removed cell is already dead continue.......
 			// remove that cell from the map
 			if(cur.hp == 0){
 				//map.remove(str.toString());
 				continue;
 			}

 			// if time is upper than K, then add it to aq
 			if(curTime >= K){
 				//aq.add(cur);
 				ANSWER++;
 				continue;
 			}


 			// if current cell is activated reproduct 
 			if(cur.activated == 1){
 				// System.out.println("Current cell "+cur.r+" "+cur.c+" is activated in time"+cur.time);
 				for (int n=0; n<4; n++) {
 					int nr = cur.r + dr[n];
 					int nc = cur.c + dc[n];
 					str = new StringBuilder();
 					str.append(nr).append(" ").append(nc);

 					// if there is no occupied cell
 					Cell exist = map.get(str.toString());
 					if(exist == null){
 						//System.out.println("NR"+nr+" NC"+nc+" is empty, so reproduct!");
 						Cell newCell = new Cell(nr,nc,cur.LIFE,curTime+1);
 						map.put(str.toString(), newCell);
 						q.add(newCell);

 					} else{
 						if(exist.start == curTime+1){
 							if(exist.LIFE < cur.LIFE){
 								//System.out.println("NR"+nr+" NC"+nc+" is exist, But curren LIFE is bigger so came here");

 								//Cell newCell = new Cell(nr,nc,cur.LIFE,curTime+1);
 								exist.LIFE = cur.LIFE;
 								exist.hp = exist.LIFE*2;
 								
 							}
 						}else{
 							//System.out.println("NR"+nr+" NC"+nc+" is already exist");
 						}
 					}
 				}// end of 4 way looking
 			} else{
 				//System.out.println("Current cell " +cur.r+" "+cur.c+" is not activated in time"+cur.time);
 			}
 			cur.activated = 0;
 			// minus 1 from current cell hp
 			// if hp is same or below LIFE then activate the cell
 			cur.hp --;
 			if(cur.hp == cur.LIFE){
 				//System.out.println("Activate the cell"+" "+cur.r+" "+cur.c);
 				cur.activated = 1;	
 			} 
 			cur.time++;

			q.add(cur);


 		}// end of while loop
 	}// end of BFS method
 }// end of class 

 class Cell{
 	int r;
 	int c;
 	int hp;
 	int LIFE;
 	int activated;
 	int time;
 	int start;

 	public Cell(int r, int c, int life, int time){
 		this.r = r;
 		this.c = c;
 		this.hp = life *2;
 		this.LIFE = life;
 		this.activated = 0;
 		this.time = time;
 		this.start = time;
 	}// end of constructor
 }// end of class cell


/*
5
2 2 10
1 1
0 2
5 5 19
3 2 0 3 0 
0 3 0 0 0 
0 0 0 0 0 
0 0 1 0 0 
0 0 0 0 2
9 10 37
0 0 0 0 0 0 0 0 3 0 
0 0 0 0 0 0 0 0 5 3 
0 0 2 0 0 0 0 4 0 0 
3 0 0 0 0 0 4 0 0 0 
0 0 0 0 0 3 5 0 0 2 
0 0 0 0 0 0 0 0 0 5 
0 0 0 0 0 0 0 0 2 3 
0 0 0 0 0 0 0 0 0 0 
0 0 2 2 0 0 0 0 0 0 
20 18 83
0 0 0 0 0 0 0 0 0 0 0 2 0 0 6 0 0 0 
0 0 0 0 0 0 0 3 0 0 0 0 0 0 0 0 0 0 
0 0 0 6 0 0 0 0 0 0 0 0 0 0 2 0 3 0 
4 0 2 0 0 0 0 0 0 0 0 0 5 0 0 0 0 3 
0 0 0 0 0 5 4 4 6 0 0 0 0 0 0 0 0 0 
5 0 0 0 0 0 2 0 2 6 0 0 0 0 0 4 0 0 
4 0 0 3 0 0 0 0 0 0 0 3 0 0 0 5 0 0 
0 0 0 0 0 0 0 2 2 0 0 0 0 3 0 0 0 0 
0 0 0 0 5 0 0 0 3 0 3 0 0 4 0 0 0 0 
0 0 0 0 6 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 0 6 0 2 0 0 0 0 0 3 0 0 0 3 0 
0 5 2 0 0 0 0 4 0 0 0 0 0 0 0 0 0 0 
3 0 0 0 0 0 0 0 6 0 2 0 5 0 0 0 0 0 
5 0 0 0 4 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 6 0 5 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 4 0 0 0 0 0 0 0 0 0 0 2 0 0 0 
0 0 3 4 5 0 0 0 0 0 0 0 0 0 0 6 0 0 
2 0 0 0 0 3 0 0 0 0 0 0 0 0 0 5 0 0 
0 0 0 0 0 0 0 0 0 3 6 2 0 0 0 0 0 0 
49 43 283
0 6 0 0 0 10 0 0 0 0 0 0 9 0 0 0 0 0 0 0 0 0 0 4 0 8 0 0 0 0 0 0 0 0 0 0 0 0 0 0 4 0 0 
0 5 0 0 0 2 0 0 0 0 0 0 8 0 0 8 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 3 7 0 0 0 0 0 0 9 0 1 0 5 0 0 1 0 0 0 0 0 0 0 0 0 0 0 9 0 7 0 0 0 0 0 0 0 0 1 0 0 
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 7 0 8 0 0 0 0 0 0 0 0 0 3 0 0 0 6 0 0 0 0 6 0 0 0 0 0 0 
7 0 0 0 5 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 7 0 0 0 0 8 0 0 0 0 0 0 0 0 1 0 0 
0 9 0 0 0 0 0 0 0 0 9 6 0 2 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 9 
0 0 0 0 1 0 0 0 0 0 0 3 0 0 0 0 0 0 5 0 10 0 0 0 0 0 0 0 0 0 9 4 0 0 0 0 0 0 9 0 9 0 8 
0 0 0 0 0 0 0 0 0 7 0 0 0 0 9 0 0 0 0 0 0 0 0 0 0 8 0 0 0 0 0 0 0 0 0 0 0 0 0 3 2 0 1 
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 3 3 0 0 4 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 7 0 0 0 2 0 0 0 0 0 0 8 0 0 0 0 10 0 0 1 7 0 8 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 2 0 0 9 0 0 0 0 0 8 0 0 0 0 0 4 0 6 0 0 0 0 0 0 6 0 0 0 0 0 0 0 0 0 0 0 
1 0 0 0 0 0 0 6 0 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 6 0 5 0 0 0 0 0 0 7 0 0 0 
8 0 0 0 0 0 0 0 0 0 0 0 0 0 0 10 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 9 0 0 0 0 0 0 5 8 
0 0 0 10 0 9 0 8 0 0 0 0 0 0 2 9 0 0 0 7 2 7 0 7 0 0 0 0 2 0 4 3 0 0 0 0 0 0 0 0 0 2 0 
1 0 0 0 0 0 0 4 9 1 0 0 0 0 0 0 0 0 0 5 0 0 0 0 6 0 0 5 0 0 0 0 0 0 0 0 0 0 0 3 3 0 0 
0 0 0 0 0 0 0 0 0 0 0 0 1 0 3 1 10 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 5 0 1 0 0 0 9 0 0 
0 0 0 0 0 0 0 10 0 0 0 0 0 0 9 6 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 7 0 0 1 3 0 0 
0 0 0 0 0 0 6 0 0 0 1 0 0 2 0 0 0 0 9 0 0 0 0 0 0 0 3 0 0 0 0 0 0 0 0 0 0 0 0 7 7 0 0 
0 0 0 0 0 2 0 0 0 0 0 0 0 0 0 0 0 5 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 10 
0 0 0 0 9 0 8 0 0 0 0 0 0 4 0 0 0 10 8 0 0 0 0 0 0 10 0 0 0 5 0 0 0 0 0 0 0 1 0 0 10 4 7 
0 0 0 0 0 0 0 0 0 0 0 6 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 8 0 0 0 4 0 7 0 0 0 0 0 3 0 
0 0 0 0 5 0 3 0 0 0 0 0 0 0 8 1 0 0 7 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 9 0 1 0 0 0 0 10 7 0 0 0 0 0 2 0 0 7 0 0 0 0 0 0 0 7 0 0 4 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 8 2 0 4 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 3 0 0 8 0 0 7 0 2 0 0 0 0 
0 8 0 0 0 0 0 0 0 0 3 0 0 1 0 5 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 8 0 0 5 0 9 0 0 0 0 0 
0 0 0 0 0 0 0 0 3 5 0 0 1 0 4 0 0 0 0 9 0 0 0 0 0 0 0 0 0 0 5 0 0 4 0 0 0 0 10 8 0 0 0 
0 0 0 0 0 0 0 0 4 0 0 7 10 0 10 0 0 0 0 0 0 0 0 0 0 9 0 0 0 0 0 0 0 8 3 9 6 7 0 0 0 0 2 
0 0 0 0 0 0 0 0 0 0 0 0 0 5 0 0 0 8 7 10 0 0 0 0 0 0 6 0 0 0 5 0 0 0 0 0 0 0 0 0 0 10 0 
7 0 0 0 8 0 0 0 8 9 0 0 0 0 0 0 9 0 0 0 0 0 0 0 0 6 0 0 5 0 0 0 0 0 0 0 0 0 0 3 0 0 0 
0 0 0 0 0 0 0 0 0 0 0 6 0 6 0 0 0 0 3 0 0 5 3 0 0 0 0 1 9 0 6 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 7 2 0 0 0 0 0 0 0 0 0 0 5 0 0 0 0 8 0 0 0 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 7 6 
0 9 0 0 0 0 0 0 0 0 0 3 0 9 2 0 0 0 4 0 2 9 2 0 6 0 0 0 0 0 0 0 0 0 0 0 0 0 8 0 0 0 0 
0 0 0 3 0 1 0 0 8 0 0 0 0 0 0 0 0 0 0 0 0 3 0 0 0 0 0 7 0 6 0 0 0 0 0 7 0 0 0 0 4 7 10 
1 0 0 0 5 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 5 0 8 0 0 0 0 0 0 0 0 3 9 2 
5 0 0 0 0 0 0 0 0 0 1 0 0 1 0 0 0 0 6 0 0 0 0 0 0 0 0 9 0 8 0 0 0 0 0 0 0 3 0 0 0 0 0 
0 0 0 0 7 0 10 0 0 0 6 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 3 0 0 0 0 0 0 0 0 0 0 0 8 2 3 0 0 
0 0 0 0 0 5 0 0 6 0 0 3 0 0 0 0 0 8 0 0 6 0 0 0 8 0 0 5 0 0 0 0 8 0 0 0 0 0 0 0 5 0 1 
7 0 9 0 7 0 0 9 0 0 0 0 4 0 0 0 0 0 0 8 1 0 4 0 0 0 0 0 0 0 0 0 4 7 0 0 8 0 0 0 0 0 0 
0 0 0 1 0 0 0 0 0 0 0 0 6 0 0 0 0 0 0 0 4 0 0 0 0 0 0 0 0 0 2 3 1 0 0 4 0 3 10 0 0 0 5 
0 0 4 0 0 0 0 0 0 4 4 0 0 0 8 0 4 0 2 0 8 0 0 0 0 0 0 0 9 0 0 0 0 5 0 0 0 0 0 0 0 0 0 
0 0 8 0 7 0 0 0 0 0 0 0 0 0 0 4 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 6 0 0 0 0 1 0 0 0 0 4 3 
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 10 3 0 0 0 0 0 3 0 
0 0 2 0 0 0 0 0 8 5 0 0 0 0 0 0 0 0 0 0 0 0 4 8 0 0 0 0 0 1 0 5 0 0 0 0 2 3 9 0 0 0 0 
0 5 8 9 0 0 0 0 0 4 0 0 0 10 0 0 0 1 0 0 0 0 0 10 0 7 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
6 0 0 0 0 0 10 0 5 0 0 0 0 0 0 0 0 0 0 0 6 0 0 0 0 0 0 0 0 10 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 9 0 0 0 0 0 0 2 0 0 0 4 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 3 0 0 0 0 0 2 4 0 
0 3 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 0 0 0 0 0 0 0 0 1 0 0 0 
0 0 0 9 0 0 0 0 4 0 0 0 0 0 2 0 0 0 0 0 0 0 0 0 9 0 0 0 0 0 0 0 0 0 0 0 5 0 0 0 9 2 0 
0 0 0 0 0 2 0 0 0 0 0 0 10 0 0 0 0 0 2 0 0 0 8 0 0 0 0 0 0 10 0 0 0 0 0 0 7 0 0 0 0 0 0 
*/
