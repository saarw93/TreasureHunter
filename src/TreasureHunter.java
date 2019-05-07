/* Author: Saar Weitzman
 * ID: 204175137
 * Date: 06-05-2019 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; 

public class TreasureHunter {
	
	public static class Box{
		private int x, y;
		Box(int x, int y){
			this.x = x;
			this.y = y;
		}	
	}
	
	public static class Hunter{
		private int x, y;
		Hunter(int x, int y){
			this.x = x;
			this.y = y;
		}	
	}
	
	private static int howManyToAddEven(Box[] boxes, Hunter[] hunters, int N)
	{
		int quad1H = 0, quad2H = 0, quad3H = 0, quad4H = 0, quad1B = 0, quad2B = 0,
				quad3B = 0, quad4B = 0, quad1Emp = 0, quad2Emp = 0, quad3Emp = 0,
				quad4Emp = 0, middle = N/2, all_quadrant_seats = (N*N)/4;
		//check how many hunters and free places there are on the carpet, by spread the carpet to quadrants, central column and central row
		
		int i;
		for (i = 0; i < hunters.length; i++) {
			if (hunters[i].x < middle && hunters[i].y < middle) //counts Quad1's hunters
				quad1H++;
			else if (hunters[i].x < middle && hunters[i].y >= middle) //counts Quad2's hunters
				quad2H++;
			else if (hunters[i].x >= middle && hunters[i].y < middle)  //counts Quad3's hunters
				quad3H++;
			else   //count Quad4's hunters
				quad4H++;
		}
		
		for (i = 0; i < boxes.length; i++) {
			if (boxes[i].x < middle && boxes[i].y < middle) //counting Quad1's boxes
				quad1B++;
			else if (boxes[i].x < middle && boxes[i].y >= middle) //counts Quad2's boxes
				quad2B++;
			else if (boxes[i].x >= middle && boxes[i].y < middle)  //counts Quad3's boxes
				quad3B++;
			else   //count Quad4's boxes
				quad4B++;
		}
		quad1Emp = all_quadrant_seats - (quad1H + quad1B); //number of empty seats in quad1
		quad2Emp = all_quadrant_seats - (quad2H + quad2B); //number of empty seats in quad2
		quad3Emp = all_quadrant_seats - (quad3H + quad3B); //number of empty seats in quad3
		quad4Emp = all_quadrant_seats - (quad4H + quad4B); //number of empty seats in quad4
		
		//check how many hunters needs to be added to the carpet
		int add = 0, leftH = quad1H + quad2H, rightH = quad3H + quad4H, 
				frontH = quad1H + quad3H, backH = quad2H + quad4H;
		if (leftH > rightH) //there are more hunters at the left side
		{
			while (quad1H > quad4H && quad4Emp > 0){
				quad4H++; 
				rightH++;
				backH++;
				quad4Emp--;
				add++;	
			}
			while (quad2H > quad3H && quad3Emp > 0){
				quad3H++;
				rightH++;
				frontH++;
				quad3Emp--;
				add++;
			}
		}
		else if (rightH > leftH)  //there are more hunters at the right side
		{
			while (quad4H > quad1H && quad1Emp > 0){
				quad1H++; 
				leftH++;
				frontH++;
				quad1Emp--;
				add++;	
			}
			while (quad3H > quad2H && quad2Emp > 0){
				quad2H++;
				leftH++;
				backH++;
				quad2Emp--;
				add++;
			}
		}
		else{} //left = rightH, so don't add treasure hunters
		
		if (leftH == rightH)
		{
			if (frontH > backH)  //there are more hunters in the front
			{
				while (quad1H > quad4H && quad4Emp > 0){
					quad4H++; 
					rightH++;
					backH++;
					quad4Emp--;
					add++;	
				}
				while (quad3H > quad2H && quad2Emp > 0){
					quad2H++;
					leftH++;
					backH++;
					quad2Emp--;
					add++;
				}
			}
			else if (backH > frontH)   //there are more hunters in the back
			{
				while (quad2H > quad3H && quad3Emp > 0){
					quad3H++;
					rightH++;
					frontH++;
					quad3Emp--;
					add++;
				}
				while (quad4H > quad1H && quad1Emp > 0){
					quad1H++; 
					leftH++;
					frontH++;
					quad1Emp--;
					add++;	
				}
			}
			else{} //frontH = backH, so don't add treasure hunters
		}
		else
			return -1;
		
		if (leftH == rightH && frontH == backH)  //the extra treasure hunters that can be added
		{
			while (quad1H == quad4H && quad4Emp > 0 && quad1Emp > 0){
				add+=2;
				quad1Emp--;
				quad4Emp--;
			}
			while (quad3H == quad2H && quad3Emp > 0 && quad2Emp > 0){
				add+=2;
				quad3Emp--;
				quad2Emp--;
			}
			return add;
		}
		else
			return -1;
	}

	
	private static int howManyToAddOdd(Box[] boxes, Hunter[] hunters, int N)
	{
		int quad1H = 0, quad2H = 0, quad3H = 0, quad4H = 0, quad1B = 0, quad2B = 0,
				quad3B = 0, quad4B = 0, quad1Emp = 0, quad2Emp = 0, quad3Emp = 0,
				quad4Emp = 0, middle = (int) Math.floor(N/2)
				,all_quadrant_seats = ((N*N) - (N*2-1))/4;
		Object[] centralRow = new Object[N]; //array that represents the central row
		Object[] centralCol = new Object[N]; //array that represents the central column
		
		if (N == 1)  //special case when the carpet has 1 seat
		{
			if (hunters.length == 0 && boxes.length == 0) 
				return 1;
			return 0;
		}
		//check how many hunters and free places there are on the carpet, by spread the carpet to quadrants, central column and central row
		int i;
		for (i = 0; i < hunters.length; i++) {
			if (hunters[i].x < middle && hunters[i].y < middle) //counts Quad1's hunters
				quad1H++;
			else if (hunters[i].x < middle && hunters[i].y > middle) //counts Quad2's hunters
				quad2H++;
			else if (hunters[i].x > middle && hunters[i].y < middle)  //counts Quad3's hunters
				quad3H++;
			else if (hunters[i].x > middle && hunters[i].y > middle)   //count Quad4's hunters
				quad4H++;
			else if (hunters[i].x == middle && hunters[i].y == middle)
			{
				centralCol[hunters[i].y] = hunters[i];
				centralRow[hunters[i].x] = hunters[i];
			}
			else if (hunters[i].x == middle)
				centralCol[hunters[i].y] = hunters[i];
			else  // hunters[i].y == middle)
				centralRow[hunters[i].x] = hunters[i];
		}
		
		for (i = 0; i < boxes.length; i++) {
			if (boxes[i].x < middle && boxes[i].y < middle) //counting Quad1's boxes
				quad1B++;
			else if (boxes[i].x < middle && boxes[i].y > middle) //counts Quad2's boxes
				quad2B++;
			else if (boxes[i].x > middle && boxes[i].y < middle)  //counts Quad3's boxes
				quad3B++;
			else if (boxes[i].x > middle && boxes[i].y > middle)   //count Quad4's boxes
				quad4B++;
			else if (boxes[i].x == middle && boxes[i].y == middle)
			{
				centralCol[boxes[i].y] = boxes[i];
				centralRow[boxes[i].x] = boxes[i];
			}
			else if (boxes[i].x == middle)
				centralCol[boxes[i].y] = boxes[i];
			else  // boxes[i].y == middle)
				centralRow[boxes[i].x] = boxes[i];
		}
		
		quad1Emp = all_quadrant_seats - (quad1H + quad1B); //number of empty seats in quad1
		quad2Emp = all_quadrant_seats - (quad2H + quad2B); //number of empty seats in quad2
		quad3Emp = all_quadrant_seats - (quad3H + quad3B); //number of empty seats in quad3
		quad4Emp = all_quadrant_seats - (quad4H + quad4B); //number of empty seats in quad4
		
		//check how many hunters needs to be added to the carpet
		int add = 0, leftH = quad1H + quad2H, rightH = quad3H + quad4H, 
				frontH = quad1H + quad3H, backH = quad2H + quad4H, j = 0;
		
		for (j = 0; j < centralCol.length; j++) {
			
			if (j == middle){
				if (centralCol[j] == null){
					add++;
					continue;
				}
			}
			
			if (centralCol[j] instanceof Hunter)
			{
				if (centralCol[N-1-j] instanceof Box)
						return -1; //there is reflection of B to H on the central column.
				if (centralCol[N-1-j] == null)
						add++;
			}
			if (centralCol[j] == null) //there are both empty seats on the central column, can add 2 hunters
				if (centralCol[N-1-j] == null)
					add++;	
		}
		
		for (j = 0; j < centralRow.length; j++) {
			if (j == middle)
				continue; //we already checked the middle in the centralCol array
			
			if (centralRow[j] instanceof Hunter)
			{
				if (centralRow[N-1-j] instanceof Box)
						return -1; //there is reflection of B to H on the central column.
				if (centralRow[N-1-j] == null)
						add++;
			}
			if (centralRow[j] == null) //there are both empty seats on the central row, can add 2 hunters
				if (centralRow[N-1-j] == null)
					add++;
		}
		
		if (leftH > rightH) //there are more hunters at the left side
		{
			while (quad1H > quad4H && quad4Emp > 0){
				quad4H++; 
				rightH++;
				backH++;
				quad4Emp--;
				add++;	
			}
			while (quad2H > quad3H && quad3Emp > 0){
				quad3H++;
				rightH++;
				frontH++;
				quad3Emp--;
				add++;
			}
		}
		else if (rightH > leftH)  //there are more hunters at the right side
		{
			while (quad4H > quad1H && quad1Emp > 0){
				quad1H++; 
				leftH++;
				frontH++;
				quad1Emp--;
				add++;	
			}
			while (quad3H > quad2H && quad2Emp > 0){
				quad2H++;
				leftH++;
				backH++;
				quad2Emp--;
				add++;
			}
		}
		
		else{} //left = rightH, so don't add treasure hunters
		
		if (leftH == rightH)
		{
			if (frontH > backH)  //there are more hunters in the front
			{
				while (quad1H > quad4H && quad4Emp > 0){
					quad4H++; 
					rightH++;
					backH++;
					quad4Emp--;
					add++;	
				}
				while (quad3H > quad2H && quad2Emp > 0){
					quad2H++;
					leftH++;
					backH++;
					quad2Emp--;
					add++;
				}
			}
			else if (backH > frontH)   //there are more hunters in the back
			{
				while (quad2H > quad3H && quad3Emp > 0){
					quad3H++;
					rightH++;
					frontH++;
					quad3Emp--;
					add++;
				}
				while (quad4H > quad1H && quad1Emp > 0){
					quad1H++; 
					leftH++;
					frontH++;
					quad1Emp--;
					add++;	
				}
			}
			else{} //frontH = backH, so don't add treasure hunters
		}
		else
			return -1;
		
		if (leftH == rightH && frontH == backH)  //the extra treasure hunters that can be added
		{
			while (quad1H == quad4H && quad4Emp > 0 && quad1Emp > 0){
				add+=2;
				quad1Emp--;
				quad4Emp--;
			}
			while (quad3H == quad2H && quad3Emp > 0 && quad2Emp > 0){
				add+=2;
				quad3Emp--;
				quad2Emp--;
			}
			return add;
		}
		else
			return -1;
	}

	public static void main(String[] args) throws FileNotFoundException { 
	    Scanner input = new Scanner(System.in);
	    System.out.println("Please enter the path of the test file (for example: C:\\Users\\user\\Desktop\\task-1.txt): ");
	    String filePath = input.nextLine();  // read user input
	    File file = new File(filePath);
	    input.close();
		Scanner sc = new Scanner(file); 
		int i = 0, N = 0, B = 0, H = 0, x = 0, y = 0;
		int num_of_tests = sc.nextInt(), test_num = 1;

		while(test_num <= num_of_tests)
		{
			System.out.print("Case #" + test_num + ": ");

			N = sc.nextInt();
			B = sc.nextInt();
			Box[] boxes = new Box[B];
			H = sc.nextInt();
			Hunter[] hunters = new Hunter[H];

			i = 0;  ////initial i before 
			while (i < B && sc.hasNextInt())  //  putting the Boxes on the carpet
			{
				x = sc.nextInt()-1;
				y = sc.nextInt()-1;
 
				boxes[i] = new Box(x,y);
				i++;
			}

			i = 0;  //initial i before 
			while (i < H && sc.hasNextInt())  //putting the Treasure Hunters on the carpet
			{
				x = sc.nextInt()-1;
				y = sc.nextInt()-1;

				hunters[i] = new Hunter(x, y);	
				i++;
			}
			
			if (N%2 == 0)  //N is even number
				System.out.println(howManyToAddEven(boxes, hunters, N));
			else  //N is odd number
				System.out.println(howManyToAddOdd(boxes, hunters, N));
			test_num++;
		}
		sc.close();
	}
}