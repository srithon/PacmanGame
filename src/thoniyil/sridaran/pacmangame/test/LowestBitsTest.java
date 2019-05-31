package thoniyil.sridaran.pacmangame.test;

public class LowestBitsTest
{
	public static void main(String[] args)
	{
		int x = 0b000100100100100100100100_10011101;
		System.out.println(x);
		System.out.println(0b11111111);
		System.out.println(0b10011101);
		System.out.println((0b11111111 & x));
	}
}
