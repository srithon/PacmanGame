package thoniyil.sridaran.pacmangame.game;

import javafx.beans.property.SimpleIntegerProperty;

public class ScoreTracker
{
	private SimpleIntegerProperty[] score;
	
	public ScoreTracker(int totalRounds)
	{
		score = new SimpleIntegerProperty[totalRounds];
		for (int i = 0; i < totalRounds; i++)
			score[i] = new SimpleIntegerProperty(0);
	}
	
	public void increment(int points)
	{
		SimpleIntegerProperty p = score[GameController.getCurrentRound()];
		p.setValue(p.get() + points);
		System.out.println(score[GameController.getCurrentRound()].get());
	}
	
	public void decrement(int points)
	{
		score[GameController.getCurrentRound()].subtract(points);
	}
	
	public int getScore(int round)
	{
		try
		{
			return score[round].getValue();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
			return Integer.MIN_VALUE;
		}
	}
	
	public int getScore()
	{
		int t = 0;
		
		for (SimpleIntegerProperty i : score)
		{
			try
			{
				t += i.getValue();
			}
			catch (NullPointerException e)
			{
				e.printStackTrace();
				return t;
			}
		}
		
		return t;
	}
	
	public SimpleIntegerProperty getCurrentObservableScore()
	{
		return score[GameController.getCurrentRound()];
	}
}
