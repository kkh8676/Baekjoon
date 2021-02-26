import java.util.PriorityQueue;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;


class boj_7662{
	static int T,K;
	static PriorityQueue<Integer> pq_asc;
	static PriorityQueue<Integer> pq_desc;
	static Queue<Integer> temp;

	static HashMap<Integer, Integer> map;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		T = Integer.parseInt(br.readLine());
		for (int t=0; t<T; t++) {
			
			K = Integer.parseInt(br.readLine());
			pq_desc = new PriorityQueue<>(Collections.reverseOrder());
			pq_asc = new PriorityQueue<>();
			temp = new LinkedList<>();
			map  = new HashMap<>();

			for (int k=0; k<K; k++) {
				st = new StringTokenizer(br.readLine());
				String action = st.nextToken();
				int num = Integer.parseInt(st.nextToken());
				operation_opt(action, num);
			}

			// Printing process
			if(map.size() == 0){
				sb.append("EMPTY").append("\n");

			}else{
				int max;
				int min;

				while(true){
					max = pq_desc.remove();

					int cnt = map.getOrDefault(max,0);
					if(cnt == 0) continue;
					else if(cnt == 1) map.remove(max);
					else map.put(max, cnt-1);

					break;
				}

				if(map.size()==0) min = max;
				else{
					while(true){
						min = pq_asc.remove();

						int cnt = map.getOrDefault(min,0);
						if(cnt == 0) continue;
						else if(cnt == 1) map.remove(min);
						else map.put(min, cnt-1);

						break;
					}// end of while loop
				}

				sb.append(max).append(" ").append(min).append("\n");
			}

		}// end of test case

		System.out.println(sb.toString());
		br.close();
	}// end of main method

	public static void operation(String str, int num){
		// str is "I" adding method
		if(str.equals("I")){
			//System.out.println("Adding "+num);
			pq_desc.add(num);
			pq_asc.add(num);
			return;
		} // end of adding operation

		// str is "D" delete method......
		if(str.equals("D")){
			if(pq_asc.isEmpty() && pq_desc.isEmpty()) return;

			if(num == 1){
				// deleting max
				//System.out.println("Deleting Max");
				int del_max = pq_desc.remove();

				// finding max from min heap
				while(true){
					int removed = pq_asc.remove();

					if(removed == del_max) break;

					temp.add(removed);
				}

				while(!temp.isEmpty()){
					pq_asc.add(temp.remove());
				}

				return;

			} else{
				// deleting min
				//System.out.println("Deleting Min");
				int del_min = pq_asc.remove();

				// finding min from max heap and delete it
				while(true){
					int removed = pq_desc.remove();

					if(removed == del_min) break;

					temp.add(removed);
				}

				while(!temp.isEmpty()){
					pq_desc.add(temp.remove());
				}

				return;
			}
			
		}// end of deleting operation
	}// end of operation method

	public static void operation_opt(String str, int num){
		// str is "I" adding method
		if(str.equals("I")){
			pq_desc.add(num);
			pq_asc.add(num);

			int cnt = map.getOrDefault(num,0);
			map.put(num, cnt+1);
			return;
		}

		// str is "D" deleting method
		if(str.equals("D")){
			if(map.size() == 0) return;

			if(num == 1){
				// deleting max
				// delete max from max heap
				while(true){
					int del_max = pq_desc.remove();
					int cnt = map.getOrDefault(del_max,0);

					if(cnt == 0) continue;
					else if(cnt == 1) map.remove(del_max);
					else map.put(del_max, cnt-1);

					break;
				}// end of while loop

			} else{
				// deleting min
				while(true){
					int del_min = pq_asc.remove();
					int cnt = map.getOrDefault(del_min,0);

					if(cnt == 0) continue;
					else if(cnt == 1) map.remove(del_min);
					else map.put(del_min, cnt-1);

					break;
				}
			}
		}// end of deleting method

	}// end of operation optimized method
}// end of class 