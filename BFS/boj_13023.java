// 40 min
// 33640 KB, 284ms.....

import java.util.List;
import java.util.LinkedList;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.Iterator;
import java.util.ArrayList;

class boj_13023{
	static int N,M;
	static List<Integer>[] arr;
	static int[] isV;

	static int length;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		arr = new ArrayList[N];

		for (int m=0; m<M; m++) {
			st = new StringTokenizer(br.readLine());
			int v1 = Integer.parseInt(st.nextToken());
			int v2 = Integer.parseInt(st.nextToken());

			if(arr[v1] == null) arr[v1] = new ArrayList<Integer>();
			if(arr[v2] == null) arr[v2] = new ArrayList<Integer>();

			arr[v1].add(v2);
			arr[v2].add(v1);
		}// end of getting information

		boolean result = false;
		for (int n=0; n<N; n++) {
			isV = new int[N];
			isV[n] = 1;
			length = 0;
			DFS(n,0);
			
			if(length == 5){
				System.out.println(1);
				return;
			}
		}

		System.out.println(0);
		
	}// end of main method

	public static void DFS(int cur, int round){
		
		if(round == 4){
			
			length = 5;
			return;
		}

		List<Integer> adj = arr[cur];

		if(adj != null){
			// Using iterator
			// Iterator iter = adj.iterator();
			// //System.out.println("NOT NULL");
			// while(iter.hasNext()){
			// 	int next = (Integer) iter.next();
			// 	//System.out.println("Adjacent next"+next);
			// 	if(isV[next]==0){
					
			// 		isV[next] = 1;
			// 		//System.out.println("Visiting "+next);
			// 		DFS(next,round+1);
			// 		isV[next] = 0;
			// 	}
			// }

			for (Integer integer : adj) {
				int next = integer;
				if(isV[next] ==0){
					isV[next] = 1;
					DFS(next,round+1);
					isV[next] = 0;
				}
			}
		}

	}// end of BFS method
}// end of class