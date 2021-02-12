package prism.networks;

import java.util.Random;

public class BarabasiAlbertNetwork extends Network
{
	public BarabasiAlbertNetwork(int n)
	{
		super(n);
		graph = generate(n);
	}

	private int[][] generate(int n)
	{
		int[][] graph = new int[n][n];
		double totalConnections = 0;

		Random r = new Random();
		for(int i = 0; i < n; i++)
		{
			for(int j = 0; j < i; j++)
			{

				if(i != j)
				{
					double jconnections = 0.0;
					for(int k = 0; k < n; k++)
					{
						if(graph[j][k] == 1)
							jconnections++;
					}

					double p = (jconnections + 1.0) / (totalConnections + 1.0);
					double roll = r.nextDouble();

					if(roll > 1 - p) {
						graph[i][j] = 1;
						graph[j][i] = 1;
						totalConnections = totalConnections + 2;
					}
				}

			}
		}

		return graph;
	}
}
