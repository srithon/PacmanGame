package thoniyil.sridaran.pacmangame.game;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ScoreTracker
{
	private SimpleIntegerProperty[] score;
	private SimpleStringProperty currentScore;
	
	public ScoreTracker(int totalRounds)
	{
		score = new SimpleIntegerProperty[totalRounds];
		for (int i = 0; i < totalRounds; i++)
			score[i] = new SimpleIntegerProperty(0);
		currentScore = new SimpleStringProperty();
		score[0].addListener((observableValue, oldValue, newValue) ->
			currentScore.set("Score: " + newValue + " points"));
		/*
		 * NOTE
		 * When round gets incremented, remove listener from score[0]
		 * and add to score[1]
		 * etc
		 */
	}
	
	public SimpleStringProperty getObservableScore()
	{
		return currentScore;
	}
	
	public void reset()
	{
		for (SimpleIntegerProperty i : score)
		{
			i.set(0);
		}
	}
	
	public void increment(int points)
	{
		SimpleIntegerProperty p = score[GameController.getCurrentRound()];
		Platform.runLater(() -> p.setValue(p.get() + points));
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
}
