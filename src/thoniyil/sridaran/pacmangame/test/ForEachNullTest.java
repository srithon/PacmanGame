package thoniyil.sridaran.pacmangame.test;

public class ForEachNullTest {
	public static void main(String[] args)
	{
		A[] arr = new A[10];
		
		for (int i = 0; i < arr.length; i++)
		{
			switch (i % 3)
			{
				case 0: arr[i] = new B(); break;
				case 1: arr[i] = new C(); break;
			}
		}
		
		int cnt = 0;
		
		//for (B b : arr) ERROR
		for (A a : arr)
		{
			if (a instanceof B)
			{
				System.out.println("B");
			}
			else if (a instanceof C)
			{
				System.out.println("C");
			}
			else if (a == null)
			{
				System.out.println("is null");
			}
			else
			{
				System.out.println("???");
			}
			
			cnt++;
		}
		
		System.out.println(cnt);
	}
}

class A
{

}

class B extends A
{

}

class C extends A
{

}
