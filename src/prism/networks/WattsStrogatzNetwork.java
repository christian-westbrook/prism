package prism.networks;

import java.util.Random;

public class WattsStrogatzNetwork extends Network
{
	private int k;
	private double p;

	public WattsStrogatzNetwork(int n, int k, double p)
	{
		super(n);
		this.p = p;
		this.k = k;
		graph = generate(n, k, p);
	}

	private int[][] generate(int n, int k, double p)
	{
		graph = new int[n][n];

		int left = k / 2;
		int right = k - left;

		for(int i = 0; i < n; i++)
		{
			for(int j = i - left; j < i + right + 1; j++)
			{
				int actual = Math.floorMod(j, n);
				if(j != i) {
					graph[i][actual] = 1;
					graph[actual][i] = 1;
				}
			}
		}

		Random r = new Random();
		for(int i = 0; i < n; i++)
		{
			for(int j = i + 1; j < i + right + 1; j++)
			{
				int actual = Math.floorMod(j, n);
				if(j != i)
				{
					double roll = r.nextDouble();
					if(roll > 1 - p)
					{
						graph[i][actual] = 0;
						graph[actual][i] = 0;

						int targetValue = 1;
						int target = -1;
						while(targetValue == 1 || target == i)
						{
							target = r.nextInt(n);
							targetValue = graph[i][target];
						}

						graph[i][target] = 1;
						graph[target][i] = 1;
					}
				}
			}
		}

		return graph;
	}
}
