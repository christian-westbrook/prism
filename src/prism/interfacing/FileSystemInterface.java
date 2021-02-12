package prism.interfacing;

import java.io.File;

public class FileSystemInterface
{
	private File networksDirectory;

	public FileSystemInterface()
	{
		networksDirectory = new File("./networks");

		if(!networksDirectory.exists())
			networksDirectory.mkdirs();
	}

	public File getNetworksDirectory()
	{
		return networksDirectory;
	}
}
