package thoniyil.sridaran.pacmangame.test;

public class InvalidMethodParamsExceptionTest
{
	public static void main(String[] args)
	{
		T t = new T() {};
		//ABTest(t);
	}
	
	public static <J extends A & B> void ABTest(J j)
	{
		System.out.println(j.getClass());
	}
	
	private interface T
	{

	}

	private interface A extends T
	{
		
	}

	private interface B extends T
	{
		
	}

	private interface C extends T
	{
		
	}
}