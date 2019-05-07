/* Author: Saar Weitzman
 * ID: 204175137
 * Date: 06-05-2019 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; 


public class TreasureHunter {

	private static int howManyToAdd(char[][] carpet, int middle)
	{
		int quad1H = 0, quad2H = 0, quad3H = 0, quad4H = 0
				,quad1Emp = 0, quad2Emp = 0, quad3Emp = 0, quad4Emp = 0;
		//check how many hunters and free places there are on the carpet, by spread the carpet to quadrants
		for (int y = 0 ; y < carpet.length; y++) 
		{
			for (int x = 0; x < carpet.length; x++)
			{
				if (x < middle && y < middle)  //counting Quad1
				{
					if (carpet[y][x] == 'H')     //Hunter sits in this index
						quad1H++;
					else if (carpet[y][x] == 0)  //the index is null
						quad1Emp++;
				}

				else if ( x < middle && y >= middle)   ///checks Quad2 of the carpet
				{
					if (carpet[y][x] == 'H')    //Hunter sits in this index
						quad2H++;
					else if (carpet[y][x] == 0)   //the index is null
						quad2Emp++;
				}

				else if (x >= middle && y < middle)  //checks Quad3 of the carpet
				{
					if (carpet[y][x] == 'H')    //Hunter sits in this index
						quad3H++;
					else if (carpet[y][x] == 0)   //the index is null
						quad3Emp++;
				}

				else
				{
					if (carpet[y][x] == 'H')    //Hunter sits in this index
						quad4H++;
					else if (carpet[y][x] == 0)   //the index is null
						quad4Emp++;
				}
			}
		}

		//check how many hunters needs to be added to the carpet
		int add = 0, leftH = quad1H + quad2H, rightH = quad3H + quad4H, 
				frontH = quad1H + quad3H, backH = quad2H + quad4H;
		if (leftH > rightH) //there are more hunters in the left side
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
		else if (rightH > leftH)  //there are more hunters in the right side
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
		//leftH = rightH
		if( leftH == rightH)
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
			return -1; //I added it now!!!
		
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
		// pass the path to the file as a parameter 
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
			char[][] carpet = new char[N][N];
			B = sc.nextInt();
			H = sc.nextInt();

			i = 0;  ////initial i before 
			while (i < B && sc.hasNextInt())  //  putting the Boxes on the carpet
			{
				x = sc.nextInt()-1;
				y = sc.nextInt()-1;

				carpet[y][x] = 'B';	
				i++;
			}

			i = 0;  //initial i before 
			while (i < H && sc.hasNextInt())  //putting the Treasure Hunters on the carpet
			{
				x = sc.nextInt()-1;
				y = sc.nextInt()-1;

				carpet[y][x] = 'H';	
				i++;
			}

			System.out.println(howManyToAdd(carpet, N/2));
			test_num++;
		}
		sc.close();
	}
}