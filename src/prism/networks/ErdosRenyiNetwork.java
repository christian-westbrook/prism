package prism.networks;

import java.util.Random;

public class ErdosRenyiNetwork extends Network
{
	private double p;

	public ErdosRenyiNetwork(int n, double p)
	{
		super(n);
		this.p = p;
		graph = generate(n, p);
	}

	private int[][] generate(int n, double p)
	{
		int[][] graph = new int[n][n];

		Random r = new Random();
		for(int i = 0; i < n; i++)
		{
			for(int j = 0; j < n; j++)
			{
				if(i != j)
				{
					double roll = r.nextDouble();
					if(roll > 1 - p)
					{
						graph[i][j] = 1;
						graph[j][i] = 1;
					}
				}
			}
		}

		return graph;
	}

	public double getP()
	{
		return p;
	}
}
