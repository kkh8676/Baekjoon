import java.util.StringTokenizer;
import java.io.*;
import java.util.Queue;
import java.util.LinkedList;

class boj_5373_210220{
	static int T,N;
	static int[][] plane;
	static String[] color;

	static int[][] around_4_plane_idx = {
		{44,41,38,33,34,35,45,48,51,20,19,18}, // rotating U O.K
		{53,50,47,29,28,27,36,39,42,24,25,26}, // rotating D o.k
		{42,43,44,6,7,8,51,52,53,15,16,17}, // rotating F 
		{38,37,36,11,10,9,47,46,45,2,1,0}, // rotating B
		{17,14,11,27,30,33,0,3,6,18,21,24}, // rotating L
		{8,5,2,35,32,29,9,12,15,26,23,20}, // rotating R
	};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();

		plane = new int[6][9]; // NOT CHANGE
		color = new String[54];


		T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			N = Integer.parseInt(br.readLine());

			// initializing plane and color arr
			initialize();

			st = new StringTokenizer(br.readLine());
			for (int n=0; n<N; n++) {
				String[] curStr = st.nextToken().split("");
				int plane;
				if(curStr[0].equals("U")) plane = 0;
				else if(curStr[0].equals("D")) plane = 1;
				else if(curStr[0].equals("F")) plane = 2;
				else if(curStr[0].equals("B")) plane = 3;
				else if(curStr[0].equals("L")) plane = 4;
				else plane = 5;

				int cnt;
				if(curStr[1].equals("+")) cnt = 1;
				else cnt = 3;

				rotate(plane, cnt);
			}

			// printing upper plane process
			for (int r=0; r<3; r++) {
				for (int c=0; c<3; c++) {
					answer.append(color[r*3+c]);
				}
				answer.append("\n");
			}


		}// end of test case

		System.out.println(answer.toString());
		br.close();
	}// end of main method

	public static void rotate(int p, int cnt){
		// System.out.println("Rotating plane "+p+" "+cnt+" times!");

		for (int cc=0; cc<cnt; cc++) {

			// around 4 colors rotating process
			int[] around_4 = around_4_plane_idx[p];

			// copying into before rotate array
			String[] before_rotate = new String[12];
			for (int idx=0; idx<12; idx++) {
				
				before_rotate[idx] = color[around_4[idx]];
				// System.out.print(before_rotate[idx]+" ");

			}
			// System.out.println();

			// before rotate to color
			for (int idx=0; idx<12; idx++) {

				color[around_4[(idx+3)%12]] = before_rotate[idx];
				// System.out.print(before_rotate[(idx+3)%12]+" ");
			}
			// System.out.println();



			// rotate current plane into clockwise dir
			String[] temp = new String[9];

			// copying current plane into temp
			for (int idx=0; idx<9; idx++) {
				temp[idx] = color[ plane[p][idx] ];
			}

			// copy copied temp into color!
			for (int idx=0; idx<9; idx++) {
				int prev_r = idx/3;
				int prev_c = idx%3;

				color[plane[p][0] + prev_c*3 + (2-prev_r)] = temp[idx];
			}

			
		}// end of rotating cnt for loop

		// System.out.println();
			
		// 	System.out.println("U");
		// 	print_plane(0);

		// 	System.out.println("D");
		// 	print_plane(1);

		// 	System.out.println("F");
		// 	print_plane(2);

		// 	System.out.println("B");
		// 	print_plane(3);

		// 	System.out.println("L");
		// 	print_plane(4);

		// 	System.out.println("R");
		// 	print_plane(5);

	}// end of rotate method

	public static void initialize(){
		// initialize plane 
		for (int p=0; p<6; p++) {
			for (int idx=0; idx<9; idx++) {
				plane[p][idx] = p*9+idx;
				String ccolor;
				if(p==0) ccolor = "w";
				else if(p==1) ccolor = "y";
				else if(p==2) ccolor = "r";
				else if(p==3) ccolor = "o";
				else if(p==4) ccolor = "g";
				else ccolor="b";
				color[p*9+idx] = ccolor;
			}
		}
		
	}// end of initialize method

	public static void print_plane(int p){
		for (int r=0; r<3; r++) {
			for (int c=0; c<3; c++) {
				System.out.print(color[ plane[p][0] + r*3 + c]+" ");
			}
			System.out.println();
		}
	}// end of print_plane method


}// end of class

/*

1
8
U+ B- R- F- D+ L- B+ U-

정답
rgo
bwo
wby

1
5
L- F+ L+ D+ B+

정답
bbr
www
wgg


*/