package prism.interfacing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileSystemInterface
{
	private File networksDirectory;
	private File simulationsDirectory;

	private File simulationFile;
	private FileWriter simulationFileWriter;
	private BufferedWriter simulationBufferedWriter;
	private PrintWriter simulationPrintWriter;

	public FileSystemInterface()
	{
		networksDirectory = new File("./networks");
		simulationsDirectory = new File("./simulations");

		if(!networksDirectory.exists())
			networksDirectory.mkdirs();

		if(!simulationsDirectory.exists())
			simulationsDirectory.mkdirs();
	}

	public boolean writeNetwork(int[][] graph, int n, String title)
	{
		try
		{
			File newFile = new File(networksDirectory.getPath() + "\\" + title + ".data");

			if(newFile.exists())
				newFile.delete();

			newFile.createNewFile();

			FileWriter fw = new FileWriter(newFile);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);

			for(int i = 0; i < n; i++)
			{
				for(int j = 0; j < n; j++)
				{
					if(graph[i][j] == 1)
						pw.println(i + "," + j);
				}
			}

			pw.close();
			return true;
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	public boolean writeLog(String title, ArrayList<String> log)
	{
		try
		{
			File outFile = new File("./simulations/" + title + ".log");

			if(outFile.exists())
				outFile.delete();

			FileWriter fw = new FileWriter(outFile);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);

			for(int i = 0; i < log.size(); i++)
			{
				String line = log.get(i);
				pw.println(line);
			}

			pw.close();
			return true;
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	public boolean writeTrace(String title, ArrayList<String> trace)
	{
		try
		{
			File outFile = new File("./simulations/" + title + ".csv");

			if(outFile.exists())
				outFile.delete();

			FileWriter fw = new FileWriter(outFile);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);

			for(int i = 0; i < trace.size(); i++)
			{
				String line = trace.get(i);
				pw.println(line);
			}

			pw.close();
			return true;
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	public File getNetworksDirectory()
	{
		return networksDirectory;
	}

	public File getSimulationsDirectory()
	{
		return simulationsDirectory;
	}
}
