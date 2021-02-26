// Totally Simulation!
// 1hr 18 min......Success!!!!!!!!
// 30672 kb, 160ms

import java.util.StringTokenizer;
import java.io.*;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Iterator;

class p2477_210213{
	static int T,N,M,A,B,K;
	
	static int[] reception;
	static int[] repair;
	static Client[] busy_reception;
	static Client[] busy_repair;
	static Client[] clients;
	static Queue<Client> receptionQ;
	static Queue<Client> repairQ;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder answer = new StringBuilder();
		StringTokenizer st;
		reception = new int[20]; // N
		repair = new int[20]; // M
		busy_repair = new Client[20];
		busy_reception = new Client[20];
		clients = new Client[1000];
		receptionQ = new LinkedList<>();
		repairQ = new LinkedList<>();

		T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());

			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			A = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());

			// initializing reception and repair arr
			Arrays.fill(repair,0);
			Arrays.fill(reception,0);
			Arrays.fill(clients, null);
			Arrays.fill(busy_reception, null);
			Arrays.fill(busy_repair, null);
			receptionQ.clear();
			repairQ.clear();

			// getting information process
			st = new StringTokenizer(br.readLine());
			for (int n=0; n<N; n++) {
				reception[n] = Integer.parseInt(st.nextToken());
			}// end of getting reception information

			st = new StringTokenizer(br.readLine());
			for (int m=0; m<M; m++) {
				repair[m] = Integer.parseInt(st.nextToken());
			}// ed of getting repair information

			st = new StringTokenizer(br.readLine());
			for (int k=0; k<K; k++) {
				clients[k] = new Client(Integer.parseInt(st.nextToken()), k+1);
				receptionQ.add(clients[k]);
			}

			// end of getting information process

			int time = 0;
			int last_client_idx = -1;

			while(true){
				// ending condition
				if(all_client_check()) break;

				// 완료된 reception은 repair Queue로 보내야함
				for (int i=0; i<N; i++) {
					Client curClient = busy_reception[i];
					if(curClient == null) continue;

					if(curClient.finished_time == time){
						busy_reception[i] = null;
						repairQ.add(curClient);
					}
				}

				// getting in reception process
				// 도착시간으로 주어져있음.......
				// 그 때 만약에 reception이 비어있다? 그럼 ㄱㄱ 해야지
				int empty_reception;
				while(receptionQ.peek() != null && receptionQ.peek().tk <= time && (empty_reception = check_reception()) != -1){
					Client recepting = receptionQ.remove();
					busy_reception[empty_reception] = recepting;
					recepting.reception_num = empty_reception;
					recepting.finished_time = time + reception[empty_reception];
				}


				for (int i=0; i<M; i++) {
					Client curClient = busy_repair[i];
					if(curClient == null) continue;

					if(curClient.finished_time == time){
						busy_repair[i] = null;
						curClient.finished = 1;
					}
				}


				// getting in repair process
				int empty_repair;
				while(repairQ.peek() != null &&(empty_repair = check_repair()) != -1){
					Client repairing = repairQ.remove();
					busy_repair[empty_repair] = repairing;
					repairing.repair_num = empty_repair;
					repairing.finished_time = time + repair[empty_repair];

				}

				

				// System.out.println();
				// print_state(time);

				// one time cycle is over
				time++;
			}

			// check there's same reception and repair class
			// System.out.println("Current round is "+t);
			int idx_sum = 0;
			for (int i=0; i<K; i++) {
				if(clients[i].reception_num + 1 == A && clients[i].repair_num + 1 == B){
					// System.out.println(i+1);
					idx_sum = idx_sum + (i+1);
				} 
			}
			if(idx_sum == 0) idx_sum = -1;
			answer.append("#").append(t).append(" ").append(idx_sum).append("\n");

		}// end of Test case

		System.out.println(answer.toString());
		br.close();
	}// end of main method

	public static int check_reception(){
		for (int i=0; i<N; i++) {
			if(busy_reception[i] == null ) return i;
		}

		return -1;
	}// end of check_reception method

	public static int check_repair(){
		for (int i=0; i<M; i++) {
			if(busy_repair[i] == null) return i;
		}

		return -1;
	}//end of check repair method



	public static boolean all_client_check(){
		
		for (int k=0; k<K; k++) {
			if(clients[k].finished == 0) return false;
		}

		return true;
	}// end of all client check

	public static void print_state(int time){
		System.out.println("Time is "+time);
		System.out.println("Current Reception center");
		for (int n=0; n<N; n++) {
			if(busy_reception[n] != null) System.out.print(busy_reception[n].idx+" ");
			else System.out.print("X ");
		}
		System.out.println();

		System.out.println("Current Repair center");
		for (int n=0; n<M; n++) {
			if(busy_repair[n] != null) System.out.print(busy_repair[n].idx+" ");
			else System.out.print("X ");
		}
		System.out.println();

		System.out.println("In reception Q");
		Iterator iter = receptionQ.iterator();
		while(iter.hasNext()){
			Client next = (Client)iter.next();
			System.out.print(next.idx+" ");
		}
		System.out.println();

		System.out.println("In repair Q");
		iter = repairQ.iterator();
		while(iter.hasNext()){
			Client next = (Client)iter.next();
			System.out.print(next.idx+" ");
		}
		System.out.println();

	}// end of print_state
}// end of class 

class Client{
	int tk, reception_num, repair_num, finished_time, finished, idx;

	Client(int tk,int idx){
		this.tk = tk;
		this.idx = idx;
		finished = 0;
	}
}// end of class Client

/*
10
1 1 2 1 1
5
7
7 10
2 2 6 1 2
3 2
4 2
0 0 1 2 3 4
2 1 4 2 1
2 5
1
0 1 3 10
4 1 10 3 1
4 6 4 8
1
0 3 4 4 5 6 9 9 9 10
2 2 8 2 1
10 3
2 9
0 2 3 3 4 6 6 7
3 2 10 1 2
5 5 8
3 5
0 0 4 7 8 8 9 9 10 10
4 2 30 4 2
3 2 2 10
2 6
0 2 2 4 5 6 7 9 9 15 15 16 17 18 18 19 19 22 23 24 24 24 25 25 25 26 27 27 28 29
5 2 70 5 1
6 6 6 4 5
5 6
0 0 0 1 1 5 6 8 10 12 12 14 15 15 17 17 18 18 19 19 22 24 26 26 28 30 30 31 32 32 32 33 33 33 34 35 35 35 37 38 40 40 41 42 46 46 47 48 48 51 53 54 55 56 56 57 59 60 61 61 63 63 64 65 65 66 67 67 70 70
4 3 100 1 3
9 9 5 2
8 7 8
0 3 5 6 10 12 13 14 15 15 19 19 20 20 21 22 22 23 23 26 26 26 26 30 31 33 33 35 36 39 39 39 40 40 41 41 42 43 43 45 47 48 49 50 50 51 51 51 51 51 52 54 58 58 59 60 60 60 60 61 61 62 62 63 63 66 69 69 69 70 71 72 73 73 74 75 76 76 79 81 82 82 82 85 87 87 88 91 91 91 94 94 96 96 96 98 98 99 99 100
5 3 100 1 1
9 10 3 5 3
8 8 10
0 0 0 0 1 1 3 3 4 5 7 8 8 9 9 11 11 13 14 15 16 17 17 18 19 19 22 22 23 23 25 26 26 27 27 30 30 34 34 36 36 38 41 44 44 45 45 47 47 49 50 50 51 53 53 58 61 62 62 63 64 65 67 67 69 70 72 73 75 76 77 80 80 80 81 81 83 83 83 84 87 87 89 89 89 90 90 91 92 93 93 93 93 95 95 98 99 100 100 100
*/