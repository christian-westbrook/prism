package prism.networks;

import prism.interfacing.FileSystemInterface;

public abstract class Network
{
	private int n;
	protected int[][] graph;

	public Network(int n)
	{
		this.n = n;
	}

	public boolean writeGraph(FileSystemInterface fsi, String title)
	{
		return fsi.create(graph, n, title);
	}

	public int[][] getGraph()
	{
		return graph;
	}

	public int getN()
	{
		return n;
	}
}
