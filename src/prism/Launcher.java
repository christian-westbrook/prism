package prism;

import prism.interfacing.FileSystemInterface;
import prism.interfacing.UserInterface;

public class Launcher
{
	public static void main(String[] args)
	{
		FileSystemInterface fsi = new FileSystemInterface();
		UserInterface ui = new UserInterface();
	}
}
