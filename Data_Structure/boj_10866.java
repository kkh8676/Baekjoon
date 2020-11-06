// Deque basic 
// addFirst, addLast
// removeFirst, removeLast 구분해서 사용
// 그냥 add는 addLast로 생각하면 된다.


import java.util.Deque;
import java.util.LinkedList;
import java.io.*;

class boj_10866{
	static int N;
	static Deque<Integer> dq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());
		dq = new LinkedList<>();

		for (int n=0; n<N; n++) {
			String[] action = br.readLine().split(" ");
			if(action[0].equals("push_back")){
				dq.addLast(Integer.parseInt(action[1]));
				//System.out.println(Integer.parseInt(action[1]));

			} else if(action[0].equals("push_front")){
				dq.addFirst(Integer.parseInt(action[1]));
				//System.out.println(Integer.parseInt(action[1]));

			} else if(action[0].equals("pop_front")){
				if(dq.isEmpty()){
					System.out.println(-1);
				} else{
					System.out.println(dq.removeFirst());
				}
			} else if(action[0].equals("pop_back")){
				if(dq.isEmpty()){
					System.out.println(-1);
				} else{
					System.out.println(dq.removeLast());
				}
			} else if(action[0].equals("size")){
				System.out.println(dq.size());

			} else if(action[0].equals("empty")){
				if(dq.isEmpty()) System.out.println(1);
				else System.out.println(0);
			} else if(action[0].equals("front")){
				if(dq.isEmpty()) System.out.println(-1);
				else System.out.println(dq.peekFirst());
			} else if(action[0].equals("back")){
				if(dq.isEmpty()) System.out.println(-1);
				else System.out.println(dq.peekLast());
			}
		}// end of N action 

	}// end of main method


}// end of class 
