// Failed.....Shit!!!!!!!!!
// Using PriorityQueue BFS

import java.util.Scanner;
import java.util.HashSet;
import java.util.Queue;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Stack;

class boj_13549{
	static int N,K;
	//static HashSet<Integer> set;
	//static HashMap<Integer,Integer> map;
	static int[] d;
	static int[] prev;

	static int ANSWER;

	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);

		N = scan.nextInt();
		K = scan.nextInt();

		d = new int[100001];
		prev = new int[100001];
		Arrays.fill(d, Integer.MAX_VALUE);
		BFS(N,K);

		System.out.println(ANSWER);

		Stack<Integer> stack = new Stack<>();
		int p = K;
		stack.push(p);
		while(p != N){
			p = prev[p];
			stack.push(p);
		}

		while(!stack.isEmpty()){
			System.out.print(stack.pop()+" ");
		}
		System.out.println();

	}// end of main method

	public static void BFS(int start, int target){
		// set = new HashSet<>();
		//map = new HashMap<>();
		//PriorityQueue<Loc> pq = new PriorityQueue<>();
		Queue<Loc> q = new LinkedList<>();
		// set.add(start);
		//map.put(start,0);
		d[start] = 0;
		Loc loc = new Loc(start,0);
		//pq.add(loc);
		q.add(loc);

		while(!q.isEmpty()){
			//Loc cur = pq.remove();
			Loc cur = q.remove();
			//System.out.println(cur.loc + " removed!");
			if(cur.loc == target){
				//System.out.println("Found it!");
				ANSWER = cur.time;
				return;
			}

			for (int i=0; i<3; i++) {
				int next = next_location(cur.loc,i);
				int nextTime;

				if(next < 0 || next >= 100001) continue;
				if(d[next] != Integer.MAX_VALUE) continue;

				if(i!=0) nextTime = cur.time+1;
				else nextTime=cur.time;
				//if(d[next] <= nextTime) continue;				
				
				
				Loc newLoc = new Loc(next, nextTime);
				

				//System.out.println("Added "+next);
				//pq.add(newLoc);
				q.add(newLoc);
				d[next] = nextTime;
				prev[next] = cur.loc;

				//System.out.println("Adding "+next+" in TIME"+(time+1));
			}
		}// end of while loop

	}// end of BFS method

	public static int next_location(int current, int type){
		if(type == 1) return current-1;
		else if(type == 2) return current+1;
		else return current*2;
	} // end of next location method
}// end of class 

class Loc implements Comparable<Loc>{
	int loc;
	int time;

	public Loc(int loc, int time){
		this.loc = loc;
		this.time = time;
	}// end of constructor

	@Override
	public int compareTo(Loc loc2){
		return Integer.compare(this.time, loc2.time);
	}
}// end of class 