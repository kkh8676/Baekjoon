import java.util.StringTokenizer;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Arrays;
import java.lang.Math;
import java.util.Deque;
import java.util.Iterator;

class p2383_210217{
	static int T, N;
	static int[][] board;

	static PriorityQueue[] pqs;
	static Deque[] stairs;
	static int num;
	static int SR1, SC1, SR2, SC2;
	static int[] sr;
	static int[] sc;
	static int[] sl;
	static Person[] people;
	static int[] toWhere;

	static int ANSWER;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();

		board = new int[10][10];
		pqs = new PriorityQueue[3];
		pqs[1] = new PriorityQueue<Person>(); // 대기열
		pqs[2] = new PriorityQueue<Person>();
		people = new Person[10];
		toWhere = new int[10];
		sr = new int[3];
		sc = new int[3];
		sl = new int[3];
		stairs = new LinkedList[3];
		stairs[1] = new LinkedList<Person>();
		stairs[2] = new LinkedList<Person>();

		T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			// initialize the board and pqs
			for (int r=0; r<10; r++) {
				Arrays.fill(board[r],0);
			}
			Arrays.fill(people,null);
			Arrays.fill(toWhere,0);

			pqs[1].clear();
			pqs[2].clear();
			stairs[1].clear();
			stairs[2].clear();

			N = Integer.parseInt(br.readLine());	
			
			int stair = 0;
			num = 0;
			for (int r=0; r<N; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<N; c++) {
					board[r][c] = Integer.parseInt(st.nextToken());
					if(board[r][c] == 1){
						people[num] = new Person(r,c,num);
						num++;
					} else if(board[r][c] >= 2 && board[r][c] <= 10){
						if(stair == 0){
							sr[1] = r;
							sc[1] = c;
							sl[1] = board[r][c];
							stair++;
						} else{
							sr[2] = r;
							sc[2] = c;
							sl[2] = board[r][c];
						}
					}

				}
			}// end of getting information

			ANSWER = Integer.MAX_VALUE;

			// solving algorithm
			DFS(num,0);
			// toWhere[0] = 1;
			// toWhere[1] = 1;
			// toWhere[2] = 1;
			// toWhere[3] = 1;

			// toWhere[4] = 2;
			// toWhere[5] = 2;

			// simulation();


			// printing process
			answer.append("#").append(t).append(" ").append(ANSWER).append("\n");


		}// end of test case

		System.out.println(answer.toString());
		br.close();

	}// end of main method

	public static void simulation(){
		pqs[1].clear();
		pqs[2].clear();
		stairs[1].clear();
		stairs[2].clear();

		// all the person get in to the waiting priorityQueue!
		for (int p=0; p<num; p++) {
			int currentDesti = toWhere[p];

			Person person = people[p];

			person.time = Math.abs(person.r - sr[currentDesti]) + Math.abs(person.c - sc[currentDesti]);
			// System.out.println("IDX"+(person.idx+1)+" R"+person.r+" C"+person.c+" D"+person.time+" with Stair"+currentDesti);
			// System.out.println("stair R"+sr[currentDesti]+ " C"+sc[currentDesti]);
			pqs[currentDesti].add(person);

		}

		// using stair time process getting in.......
		int time = 0;
		while(true){
			// stair 1 ends people get outside from deque
			while(true){
				

				Person wasInStair = (Person)stairs[1].peekFirst();
				if(wasInStair == null || wasInStair.finish_time > time) break;
				// System.out.println((wasInStair.idx+1)+ " Using stair 1 is over!");
				// came here because wasinStair is getting outside the stair
				stairs[1].removeFirst();
				wasInStair.arrived = 1;
			}

			// number 1 stair using algorithm
			while(!pqs[1].isEmpty() && stairs[1].size() < 3 && ((Person)pqs[1].peek()).time <= time){
				
				Person getInStair = (Person)pqs[1].remove();
				// System.out.println((getInStair.idx+1)+ " Adding in Stair 1 which has size "+stairs[1].size());
				getInStair.finish_time = time + sl[toWhere[getInStair.idx]];
				stairs[1].addLast(getInStair);
			}


			// number 2 stair using algorithm
			while(true){
				Person wasInStair = (Person)stairs[2].peekFirst();

				if(wasInStair == null || wasInStair.finish_time > time) break;
				// System.out.println((wasInStair.idx+1)+ " Using stair 2 is over!");
				// came here because wasinStair is getting outside the stair
				stairs[2].removeFirst();
				wasInStair.arrived = 1;
			}

			// number 1 stair using algorithm
			while(!pqs[2].isEmpty() && stairs[2].size() < 3 && ((Person)pqs[2].peek()).time <= time){
				
				Person getInStair = (Person)pqs[2].remove();
				// System.out.println((getInStair.idx+1)+ " Adding in Stair 2 which has size "+stairs[2].size());
				getInStair.finish_time = time + sl[toWhere[getInStair.idx]];
				stairs[2].addLast(getInStair);
			}


			time++;

			// System.out.println("Current time "+ time);
			// print_state();

			if(pqs[1].isEmpty() && pqs[2].isEmpty() && stairs[1].isEmpty() && stairs[2].isEmpty()) break;
		}

		ANSWER = ANSWER > time ? time : ANSWER;


	}// end of simulation method

	public static void print_state(){
		System.out.println("In Stair 1 waiting line");
		Iterator iter = pqs[1].iterator();
		while(iter.hasNext()){
			Person cur = (Person)iter.next();
			System.out.print("IDX"+(cur.idx+1)+"T"+cur.time+" ");
		}
		System.out.println();

		System.out.println("In Stair 1");
		iter = stairs[1].iterator();
		while(iter.hasNext()){
			Person cur = (Person)iter.next();
			System.out.print(cur.idx+" ");
		}
		System.out.println();

		System.out.println("In Stair 2 waiting line");
		iter = pqs[2].iterator();
		while(iter.hasNext()){
			Person cur = (Person)iter.next();
			System.out.print("IDX"+(cur.idx+1)+"T"+cur.time+" ");
		}
		System.out.println();

		System.out.println("In Stair 2");
		iter = stairs[2].iterator();
		while(iter.hasNext()){
			Person cur = (Person)iter.next();
			System.out.print(cur.idx+" ");
		}
		System.out.println();
	}// end of print_state method

	public static void DFS(int capa, int round){
		if(capa == round){
			simulation();
			return;
		}// end of ending condition

		for (int where=1; where<=2; where++) {
			toWhere[round] = where;
			DFS(capa,round+1);
		}
	}// end of DFS method

	
}// end of class

class Person implements Comparable<Person>{
	int r,c,time, arrived, finish_time, idx;

	Person(int r, int c, int idx){
		this.r = r;
		this.c = c;
		this.idx = idx;
	}

	@Override 
	public int compareTo(Person p2){
		return Integer.compare(this.time, p2.time);
	}// end of compareTo

}// end of class 
/*
10
5
0 1 1 0 0
0 0 1 0 3
0 1 0 1 0
0 0 0 0 0
1 0 5 0 0
5
0 0 1 1 0
0 0 1 0 2
0 0 0 1 0
0 1 0 0 0
1 0 5 0 0
6
0 0 0 1 0 0
0 0 0 0 0 0
0 0 0 0 0 0
0 1 0 0 0 0
2 0 1 0 0 0
0 0 2 0 0 0
6
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
1 0 0 0 0 0
0 0 0 2 0 4
7
0 0 0 0 0 0 0
0 0 0 0 0 0 4
0 0 0 0 1 0 0
1 0 0 1 0 0 0
0 0 0 0 0 0 0
0 0 0 0 0 0 0
0 2 0 0 0 0 0
7
0 0 0 0 0 0 0
7 0 0 0 0 0 0
0 0 0 0 0 0 0
0 0 0 0 0 0 0
0 0 0 0 0 0 0
2 0 0 0 0 1 0
0 0 0 0 0 0 0
8
0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 2
0 0 0 0 0 0 0 0
2 0 0 0 0 0 0 0
0 0 0 0 0 1 0 0
0 0 0 0 0 0 0 0
0 0 0 0 0 0 1 0
0 0 0 0 1 0 0 0
8
3 0 0 0 0 0 5 0
0 0 0 0 0 0 0 0
1 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0
1 0 1 1 0 0 0 0
0 0 0 0 0 0 1 0
0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0
9
0 0 0 1 0 0 0 0 0
0 1 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 8
7 0 0 0 0 1 0 0 0
0 0 0 0 0 1 1 0 0
0 0 0 0 0 0 0 0 0
1 0 0 0 0 1 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
10
0 0 0 0 0 0 0 0 0 0
0 0 0 0 1 0 0 0 0 0
0 0 1 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
3 0 1 0 1 0 0 0 0 2
1 1 0 0 1 0 1 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
*/