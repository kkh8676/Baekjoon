// Took 37 min
// just using Stack and one exception
// using 'String'
// 문자열 문제

import java.io.*;
import java.util.Stack;

class boj_3568{
	static String[] var_name;
	static String[] var_type;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		String[] assigned = br.readLine().split(" ");

		String common = assigned[0];
		int L = assigned.length-1;
		var_name = new String[L+1];
		var_type = new String[L+1];

		Stack<String> stack = new Stack<>();

		for (int l=1; l<=L; l++) {
			String[] vars = assigned[l].split("");
			int oneL = vars.length;
			String name = "";

			for (int ol=0; ol<oneL; ol++) {
				if(!vars[ol].equals("&") && !vars[ol].equals("*") && !vars[ol].equals("[") && !vars[ol].equals("]") && !vars[ol].equals(",") && !vars[ol].equals(";")){
					name = name + vars[ol];
					continue;
				}

				stack.push(vars[ol]);

			}// end of one var adding type to the stack
			//System.out.println("at first stack size is "+stack.size());
			var_name[l] = name;
			var_type[l] = common;
			while(!stack.isEmpty()){
				//System.out.println(stack.size());
				String adding = stack.pop();
				if(adding.equals("]")){
					String adding2 = stack.pop();
					var_type[l] = var_type[l] + adding2 + adding;
					continue;
				}
				//System.out.println(adding + " ");
				if(!adding.equals(";") && !adding.equals(",")) var_type[l] = var_type[l] + adding;
			}
		}// end of getting all variables

		for (int l=1; l<=L; l++) {
			//System.out.println(var_type[l] + " " + var_name+";");
			bw.write((var_type[l] + " " + var_name[l]+";\n"));
		}

		br.close();
		bw.flush();
		bw.close();
	}// end of main method
}// end of class 