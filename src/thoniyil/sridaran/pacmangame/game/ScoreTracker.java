package thoniyil.sridaran.pacmangame.game;

public class ScoreTracker
{
	private int[] score;

	public ScoreTracker(int totalRounds)
	{
		score = new int[totalRounds];
	}
	
	public void increment(int points)
	{
		score[GameController.getCurrentRound()] += points;
	}
	
	public void decrement(int points)
	{
		score[GameController.getCurrentRound()] -= points;
	}
	
	public int getScore(int round)
	{
		return score[round];
	}
	
	public int getScore()
	{
		int t = 0;
		for (int i : score)
			t += i;
		return t;
	}
}
