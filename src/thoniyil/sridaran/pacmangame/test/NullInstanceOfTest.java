package thoniyil.sridaran.pacmangame.test;

public class NullInstanceOfTest
{
	public static void main(String[] args)
	{
		F a = null;
		G a_ = a;
		
		System.out.println(a_ instanceof F);
		System.out.println(a_ instanceof G);
		
		System.out.println(a instanceof F);
		System.out.println(a instanceof G);
	}
}

class G
{
	
}

class F extends G
{
	
}