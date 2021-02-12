package prism.interfacing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class UserInterface
{
	final int WINDOW_WIDTH = 500; // Window width in pixels
	final int WINDOW_HEIGHT = 350; // Window height in pixels

	private GridBagConstraints c;
	private JLabel networkGenerationLabel;
	private JLabel networkTypeLabel;
	private JRadioButton erdosRenyiButton;
	private JRadioButton barabasiAlbertButton;
	private JRadioButton wattsStrogatzButton;
	private ButtonGroup networkTypeButtonGroup;
	private JLabel computerCountLabel;
	private JTextField computerCountField;
	private JLabel erdosRenyiProbabilityLabel;
	private JTextField erdosRenyiProbabilityField;
	private JLabel wattsStrogatzMeanDegreeLabel;
	private JTextField wattsStrogatzMeanDegreeField;
	private JLabel wattsStrogatzRewireProbabilityLabel;
	private JTextField wattsStrogatzRewireProbabilityField;
	private JButton generateNetworkButton;
	private JPanel networkTypePanel;
	private JPanel networkGenerationPanel;
	private JFrame frame;

	public UserInterface()
	{
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;

		buildNetworkGenerationPanel();
		buildFrame();
	}

	private void buildNetworkTypePanel()
	{
		erdosRenyiButton = new JRadioButton("Erdos-Renyi", true);
		barabasiAlbertButton = new JRadioButton("Barabasi-Albert");
		wattsStrogatzButton = new JRadioButton("Watts-Strogatz");

		networkTypeButtonGroup = new ButtonGroup();
		networkTypeButtonGroup.add(erdosRenyiButton);
		networkTypeButtonGroup.add(barabasiAlbertButton);
		networkTypeButtonGroup.add(wattsStrogatzButton);

		networkTypePanel = new JPanel();
		networkTypePanel.add(erdosRenyiButton);
		networkTypePanel.add(barabasiAlbertButton);
		networkTypePanel.add(wattsStrogatzButton);
	}

	private void buildNetworkGenerationPanel()
	{
		networkGenerationLabel = new JLabel("Network Generation Panel");

		networkTypeLabel = new JLabel("Network Type");
		buildNetworkTypePanel();

		computerCountLabel = new JLabel("Number of Connected Computers");
		computerCountField = new JTextField(4);

		erdosRenyiProbabilityLabel = new JLabel("Erdos-Renyi Connection Probability");
		erdosRenyiProbabilityField = new JTextField(4);

		wattsStrogatzMeanDegreeLabel = new JLabel("Watts-Strogatz Mean Degree");
		wattsStrogatzMeanDegreeField = new JTextField(4);

		wattsStrogatzRewireProbabilityLabel = new JLabel("Watts-Strogatz Rewire Probability");
		wattsStrogatzRewireProbabilityField = new JTextField(4);

		generateNetworkButton = new JButton("Generate Network");

		networkGenerationPanel = new JPanel();
		networkGenerationPanel.setLayout(new GridBagLayout());

		c.gridx = 0;
		c.gridy = 0;
		networkGenerationPanel.add(networkGenerationLabel, c);

		c.gridx = 0;
		c.gridy = 2 ;
		networkGenerationPanel.add(networkTypeLabel, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 3;
		networkGenerationPanel.add(networkTypePanel, c);
		c.gridwidth = 0;

		c.gridx = 0;
		c.gridy = 4;
		networkGenerationPanel.add(computerCountLabel, c);

		c.gridx = 0;
		c.gridy = 5;
		networkGenerationPanel.add(computerCountField, c);

		c.gridx = 0;
		c.gridy = 6;
		networkGenerationPanel.add(erdosRenyiProbabilityLabel, c);

		c.gridx = 0;
		c.gridy = 7;
		networkGenerationPanel.add(erdosRenyiProbabilityField, c);

		c.gridx = 0;
		c.gridy = 8;
		networkGenerationPanel.add(wattsStrogatzMeanDegreeLabel, c);

		c.gridx = 0;
		c.gridy = 9;
		networkGenerationPanel.add(wattsStrogatzMeanDegreeField, c);

		c.gridx = 0;
		c.gridy = 10;
		networkGenerationPanel.add(wattsStrogatzRewireProbabilityLabel, c);

		c.gridx = 0;
		c.gridy = 11;
		networkGenerationPanel.add(wattsStrogatzRewireProbabilityField, c);

		c.gridx = 0;
		c.gridy = 12;
		networkGenerationPanel.add(generateNetworkButton, c);
	}

	private void buildFrame()
	{
		JFrame frame = new JFrame();

		frame.add(networkGenerationPanel);

		frame.setTitle("Prism");
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
