package prism.interfacing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileSystemInterface
{
	private File networksDirectory;

	public FileSystemInterface()
	{
		networksDirectory = new File("./networks");

		if(!networksDirectory.exists())
			networksDirectory.mkdirs();
	}

	public boolean create(int[][] graph, int n, String title)
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

	public File getNetworksDirectory()
	{
		return networksDirectory;
	}
}
