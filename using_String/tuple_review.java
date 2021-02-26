import java.util.Set;
import java.util.HashSet;
import java.io.*;
import java.util.Queue;
import java.util.LinkedList;

class tuple_review{
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String str = br.readLine();

		String regexStr = str.replaceAll("[{}]"," ");
		String[] strArr = regexStr.split(" , ");
		//System.out.println(regexStr);

		Set<Integer> set = new HashSet<>();
		Queue<Integer> q = new LinkedList<>();
		for (String curStr : strArr) {
			String trimmed = curStr.trim();

			String[] splited = trimmed.split(",");
			for(String num : splited){
				int curNum = Integer.parseInt(num);
				//System.out.println(curNum);
				if(set.contains(curNum)) continue;
				else{
					//System.out.println("ADDED!");
					set.add(curNum);
					q.add(curNum);
				}
			}
		}

		int[] answer = new int[q.size()];
		int index = 0;
		while(!q.isEmpty()){
			int removed = q.remove();
			answer[index] = removed;
			index++;
		}

		for (int i=0; i<answer.length; i++) {
			System.out.print(answer[i]+" ");
		}

		


	}// end of main method
}// end of class