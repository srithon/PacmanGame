package thoniyil.sridaran.pacmangame.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GetTotalLineCount
{
	public static void main(String[] args)
	{
		File mainDir = new File("C:\\Development\\Java\\PacmanGame\\src\\thoniyil\\sridaran\\pacmangame");
		System.out.println(getLines(mainDir) + " total lines");
	}

	private static int getLines(File directory)
	{
		File[] files = directory.listFiles();
		int lines = 0;
		for (File f : files)
		{
			if (f.isDirectory())
				lines += getLines(f);
			else
				lines += countLines(f);
		}
		return lines;
	}

	private static int countLines(File file)
	{
		try (BufferedReader r = new BufferedReader(new FileReader(file)))
		{
			int lines = 0;
			while (r.readLine() != null) lines++;
			System.out.println(file.getName() + ": " + lines + " lines");
			return lines;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return Integer.MIN_VALUE;
		}
	}
}
