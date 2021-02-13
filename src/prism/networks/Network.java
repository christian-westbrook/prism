package prism.networks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import prism.interfacing.FileSystemInterface;

public class Network
{
	private int n;
	private int edges;
	protected int[][] graph;
	protected HashMap<Integer, Machine> identityMap;
	protected HashMap<Integer, Boolean> curedMap;
	protected HashMap<Integer, Boolean> infectedMap;
	private String title;

	public Network(int n)
	{
		this.n = n;
	}

	public Network(File file)
	{
		try
		{
			this.title = file.getName().substring(0, file.getName().length() - 5);

			identityMap = new HashMap<Integer, Machine>();
			infectedMap = new HashMap<Integer, Boolean>();
			curedMap = new HashMap<Integer, Boolean>();

			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			n = 1;
			graph = new int[1][1];
			edges = 0;

			String line;
			while((line = br.readLine()) != null)
			{
				String[] tokens = line.split(",");

				try
				{
					int left = Integer.parseInt(tokens[0]);
					int right = Integer.parseInt(tokens[1]);

					if(left >= n || right >= n)
						graph = expand(left, right);


					graph[left][right] = 1;
					edges++;

					if(!identityMap.containsKey(left))
					{
						identityMap.put(left, new Machine(left));
					}
					if(!identityMap.containsKey(right))
					{
						identityMap.put(right, new Machine(right));
					}
				}
				catch(NumberFormatException ex)
				{
					ex.printStackTrace();
				}

			}
		}
		catch(FileNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}

	public boolean writeGraph(FileSystemInterface fsi, String title)
	{
		return fsi.writeNetwork(graph, n, title);
	}

	public int[][] expand(int left, int right)
	{
		int[][] newGraph = new int[Math.max(left, right) + 1][Math.max(left, right) + 1];

		for(int i = 0; i < n; i++)
		{
			for(int j = 0; j < n; j++)
			{
				newGraph[i][j] = graph[i][j];
			}
		}

		n = Math.max(left, right) + 1;
		return newGraph;
	}

	public void reset()
	{
		infectedMap = new HashMap<Integer, Boolean>();
		curedMap = new HashMap<Integer, Boolean>();

		for(Integer machineID : identityMap.keySet())
		{
			identityMap.get(machineID).setInfected(false);
			identityMap.get(machineID).setCured(false);
		}
	}

	public int[][] getGraph()
	{
		return graph;
	}

	public HashMap<Integer, Machine> getIdentityMap()
	{
		return identityMap;
	}

	public HashMap<Integer, Boolean> getCuredMap()
	{
		return curedMap;
	}

	public HashMap<Integer, Boolean> getInfectedMap()
	{
		return infectedMap;
	}

	public int getN()
	{
		return n;
	}

	public int getEdges()
	{
		return edges / 2;
	}

	public String getTitle()
	{
		return title;
	}
}
