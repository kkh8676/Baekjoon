import java.util.Scanner;
import java.lang.Math;

class BOJ_19236{
	static int[][] oSea;
	static int[] oFishR;
	static int[] oFishC;
	static int[] oFishD;
	static int[] oIsEaten;

	static int[][] sea;
	static int[] fishR;
	static int[] fishC;
	static int[] fishD;
	static int[] isEaten;

	static int[] dr = {0,-1,-1,0,1,1,1,0,-1};
	static int[] dc = {0,0,-1,-1,-1,0,1,1,1};

	static int totalEat = Integer.MAX_VALUE;
	static int eat;

	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);

		oSea = new int[5][5];
		oFishR = new int[17];
		oFishC = new int[17];
		oFishD = new int[17];
		oIsEaten = new int[17];
		for (int r=1; r<5; r++) {
			for (int c=1; c<5; c++) {
				fishnum = scan.nextInt(); // getting fish number
				dir = scan.nextInt();

				oSea[r][c] = fishnum;
				oFishR[fishnum] = r;
				oFishC[fishnum] = c;
				oFishD[fishnum] = dir;

			}
		} // getting information process is done;


		// for (int r=1; r<5; r++) {
		// 	for (int c=1; c<5; c++) {
		// 		initialize(r,c);

		// 		while(true){
		// 			if(!sharkGo()) {
		// 				// if there is no place to go for shart
		// 				// update the totalEat
		// 				totalEat = totalEat > eat? eat : totalEat;

		// 				// and break the while loop
		// 				break
		// 			}// end of if 

		// 			// if there is place to go for shark
		// 			// update the fish location
		// 			fishGo();
		// 		}// end of while loop



		// 	} // end of inner for loop
		// }// end of outer for loop

		initialize(0,0);
		DFS(0,0);

		System.out.println(totalEat);


	}// end of main method

	public static void initialize(int r, int c){
		sea = new int[5][5];
		fishR = new int[17];fishR = oFishR[n];
		fishC = new int[17];
		fishD = new int[17];
		isEaten = new int[17];

		for (int r=1; r<5; r++) {
			for (int c=1; c<5; c++) {
				sea[r][c] = oSea[r][c];
			}
		}

		for(int n=1; n<17; n++){
			fishR = oFishR[n];
			fishC = oFishC[n];
			fishD = oFishD[n];
			isEaten = oIsEaten[n];
		}

		
		fishR[0] = r;
		fishC[0] = c;
		fishD[0] = oFishD[ map[r][c] ];

		eat = map[r][c];

	}// end of copy method


	public static void fishGo(){

	}

	public static int sharkGo(){
		// should get the direction of shark!

		int result = 0;
		int targetFishNum = Integer.MAX_VALUE;
		// finally, if shark can go, then add the fish num on the eat
		// and change the shark direction, row, column, sea, isEaten.
		curSharkR = fishR[0];
		curSharkC = fishC[0];
		curSharkD = fishD[0];

		for (int p=1; p<4; p++) {
			// out of range continue
			if(curSharkD + dr[curSharkD]*p <1 || curSharkD + dr[curSharkD]*p >4 || curSharkC + dc[curSharkD]*p < 1 || curSharkC + dc[curSharkD]*p > 4) continue;

			
		}


	} // end of sharkGo method


	public static void DFS(int r, int c){
		
		for(int p=1; p<4; p++){

		}

	}

}// end of class 