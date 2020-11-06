// Took 2 hr.
// Using DEQUE
// Deque<Integer> dq = new LinkedList<>(); 이렇게 initialize 할 수 있다.
// Head 		Tail
// addFirst     addLast
// removeFirst  removeLast
// getFirst     getLast

// Queue의 add는 Deque의 addLast와 동일.



import java.io.*;
import java.util.Scanner;
import java.util.LinkedList;

class boj_3190{
	static int N;
	static int K;
	static int L;

	static int[] change;
	static int[][] board;

	static final int APPLE = -1;
	static final int SNAKE = 1;

	static final int LEFT = -1 ;
	static final int RIGHT = 1;

	// starting point
	static int[] head = {1,1};
	static int[] tail = {1,1};

	static int[] dr = {-1,0,1,0};
	static int[] dc = {0,1,0,-1};

	static int dir = 1;
	static int len = 1;

	static int TIME;

	static LinkedList<Integer> snake;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		

		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());

		board = new int[N+1][N+1];
		for (int k=0; k<K; k++) {
			String[] rc = br.readLine().split(" ");
			board[Integer.parseInt(rc[0])][Integer.parseInt(rc[1])] = APPLE;
		}
		board[1][1] = SNAKE; // body part checking!!
		snake = new LinkedList<>();
		

		L = Integer.parseInt(br.readLine());
		change = new int[10001];

		for (int l=1; l<=L; l++) {
			String[] secDir = br.readLine().split(" ");
			change[Integer.parseInt(secDir[0])] = secDir[1].equals("L")? LEFT : RIGHT;
		}
		// printBoard();
		// System.out.println();
		while(true){ // frame o.k

			rotate();
			if(!check()){
				TIME++;
				break;
			}

			move();			

			TIME++;
		}// end of moving while
		
		System.out.println(TIME); // o.k printing ANSWER

	}// end of main method

	public static void rotate(){ // o.k
		// current Time
		if(change[TIME] == 0){
			return;
		}

		if(change[TIME]==LEFT){
			dir = (dir+3)%4;
			return;
		}

		if(change[TIME]==RIGHT){
			dir = (dir+1)%4;
			return;
		}
	}// end of rotate method

	public static void move(){
		// apple is here or not
		// change the head
		int nr = head[0] + dr[dir];
		int nc = head[1] + dc[dir];

		int pr = head[0];
		int pc = head[1];

		head[0] = nr;
		head[1] = nc;
		

		if(len<2){
			// length 1
			if(board[nr][nc] == APPLE){
				len++;
				board[nr][nc] = SNAKE;

			} else if(board[nr][nc] != APPLE){
				board[ tail[0] ][ tail[1] ] = 0;
				tail[0] = nr;
				tail[1] = nc;
				board[nr][nc] = SNAKE;
			}
		}
		else if(len==2){
			if(board[nr][nc] == APPLE){
				len++;
				snake.addFirst(tail[1]);
				snake.addFirst(tail[0]);
				snake.addFirst(pc);
				snake.addFirst(pr);
				snake.addFirst(head[1]);
				snake.addFirst(head[0]);
				board[nr][nc] = SNAKE;

			} else if(board[nr][nc] != APPLE){
				board[ tail[0] ][ tail[1] ] = 0;
				tail[0] = pr;
				tail[1] = pc;
				board[nr][nc] = SNAKE;
			}

		} else{
			if(board[nr][nc] == APPLE){
				snake.addFirst(nc);
				snake.addFirst(nr);
				len++;
				board[nr][nc] = SNAKE;
			} else if(board[nr][nc] != APPLE){
				int ptc = snake.removeLast();
				int ptr = snake.removeLast();
				board[ptr][ptc] = 0;
				tail[1] = snake.removeLast();
				tail[0] = snake.removeLast();
				snake.addLast(tail[0]);
				snake.addLast(tail[1]);
				snake.addFirst(head[1]);
				snake.addFirst(head[0]);
				board[nr][nc] = SNAKE;
			}
		}
		
	}// end of move method

	public static boolean check(){
		int nr = head[0] + dr[dir];
		int nc = head[1] + dc[dir];

		if(nr < 1 || nr > N || nc < 1 || nc > N ) return false;
		if(board[nr][nc] == SNAKE) return false;

		return true;
	}// end of check method

	public static void printBoard(){
		for (int r=1; r<=N; r++) {
			for (int c=1; c<=N; c++) {
				System.out.print(board[r][c] + " ");
			}
			System.out.println();
		}
	}// end of printBoard

	public static void printSnake(){
		while(!snake.isEmpty()){

		}
	}
}// end of class 