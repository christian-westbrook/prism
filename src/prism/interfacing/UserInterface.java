package prism.interfacing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

import prism.networks.Network;
import prism.networks.ErdosRenyiNetwork;
import prism.networks.BarabasiAlbertNetwork;
import prism.networks.WattsStrogatzNetwork;
import prism.simulations.Simulation;

public class UserInterface
{
	final int WINDOW_WIDTH = 760; // Window width in pixels
	final int WINDOW_HEIGHT = 350; // Window height in pixels

	private FileSystemInterface fsi;
	private Network chosenNetwork;

	private GridBagConstraints c;
	private Border blackline;
	private JLabel prismLabel;
	private JLabel networkGenerationLabel;
	private JLabel networkTypeLabel;
	private JRadioButton erdosRenyiButton;
	private JRadioButton barabasiAlbertButton;
	private JRadioButton wattsStrogatzButton;
	private ButtonGroup networkTypeButtonGroup;
	private JPanel networkTypePanel;
	private JLabel computerCountLabel;
	private JTextField computerCountField;
	private JLabel erdosRenyiProbabilityLabel;
	private JTextField erdosRenyiProbabilityField;
	private JLabel wattsStrogatzMeanDegreeLabel;
	private JTextField wattsStrogatzMeanDegreeField;
	private JLabel wattsStrogatzRewireProbabilityLabel;
	private JTextField wattsStrogatzRewireProbabilityField;
	private JLabel networkNameLabel;
	private JTextField networkNameField;
	private JButton generateNetworkButton;
	private JPanel networkGenerationPanel;
	private JLabel simulationLabel;
	private JLabel networkLabel;
	private JButton browseButton;
	private JFileChooser networkChooser;
	private JLabel chosenNetworkLabel;
	private JLabel chosenNetworkComputerCountLabel;
	private JLabel chosenNetworkEdgeCountLabel;
	private JLabel cureCountLabel;
	private JTextField cureCountField;
	private JLabel cureRateLabel;
	private JTextField cureRateField;
	private JLabel wormCountLabel;
	private JTextField wormCountField;
	private JLabel infectionRateLabel;
	private JTextField infectionRateField;
	private JButton simulateButton;
	private JPanel simulationPanel;
	private JFrame frame;

	// ---------------------------------------------------------------------------
	// Constructor
	// ---------------------------------------------------------------------------
	public UserInterface(FileSystemInterface fsi)
	{
		this.fsi = fsi;

		c = new GridBagConstraints();
		blackline = BorderFactory.createLineBorder(Color.black);

		buildNetworkGenerationPanel();
		buildSimulationPanel();
		buildFrame();
	}

	// -------------------------- Interface --------------------------------------
	private void buildNetworkTypePanel()
	{
		erdosRenyiButton = new JRadioButton("Erdos-Renyi", true);
		erdosRenyiButton.addActionListener(new ErdosRenyiRadioButtonListener());
		barabasiAlbertButton = new JRadioButton("Barabasi-Albert");
		barabasiAlbertButton.addActionListener(new BarabasiAlbertRadioButtonListener());
		wattsStrogatzButton = new JRadioButton("Watts-Strogatz");
		wattsStrogatzButton.addActionListener(new WattsStrogatzRadioButtonListener());

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
		erdosRenyiProbabilityField.setEnabled(true);

		wattsStrogatzMeanDegreeLabel = new JLabel("Watts-Strogatz Mean Degree");
		wattsStrogatzMeanDegreeField = new JTextField(4);
		wattsStrogatzMeanDegreeField.setEnabled(false);

		wattsStrogatzRewireProbabilityLabel = new JLabel("Watts-Strogatz Rewire Probability");
		wattsStrogatzRewireProbabilityField = new JTextField(4);
		wattsStrogatzRewireProbabilityField.setEnabled(false);

		networkNameLabel = new JLabel("Network Title");
		networkNameField = new JTextField(12);

		generateNetworkButton = new JButton("Generate Network");
		generateNetworkButton.addActionListener(new GenerateNetworkButtonListener());

		networkGenerationPanel = new JPanel();
		networkGenerationPanel.setLayout(new GridBagLayout());
		networkGenerationPanel.setBorder(blackline);

		c.anchor = GridBagConstraints.WEST;

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
		networkGenerationPanel.add(networkNameLabel, c);

		c.gridx = 0;
		c.gridy = 13;
		networkGenerationPanel.add(networkNameField, c);

		c.gridx = 0;
		c.gridy = 14;
		networkGenerationPanel.add(generateNetworkButton, c);
	}

	private void buildSimulationPanel()
	{
		simulationLabel = new JLabel("Simulation Configuration Panel");
		networkChooser = new JFileChooser("./networks");
		networkLabel = new JLabel("Simulation Network");
		browseButton = new JButton("Browse");
		browseButton.addActionListener(new BrowseButtonListener());
		chosenNetworkLabel = new JLabel("File:       ");
		chosenNetworkComputerCountLabel = new JLabel("Computer Count: ");
		chosenNetworkEdgeCountLabel = new JLabel("Edge Count: ");
		cureCountLabel = new JLabel("Cure Count");
		cureCountField = new JTextField(4);
		cureRateLabel = new JLabel("Cure Rate");
		cureRateField = new JTextField(4);
		wormCountLabel = new JLabel("Worm Count");
		wormCountField = new JTextField(4);
		infectionRateLabel = new JLabel("Infection Rate");
		infectionRateField = new JTextField(4);
		simulateButton = new JButton("Simulate");
		simulateButton.addActionListener(new SimulateButtonListener());

		simulationPanel = new JPanel();
		simulationPanel.setLayout(new GridBagLayout());
		simulationPanel.setBorder(blackline);

		c.anchor = GridBagConstraints.WEST;

		c.gridx = 0;
		c.gridy = 0;
		simulationPanel.add(simulationLabel, c);

		c.gridx = 0;
		c.gridy = 1;
		simulationPanel.add(networkLabel, c);

		c.gridx = 0;
		c.gridy = 2;
		simulationPanel.add(browseButton, c);

		c.gridx = 0;
		c.gridy = 3;
		simulationPanel.add(chosenNetworkLabel, c);

		c.gridx = 0;
		c.gridy = 4;
		simulationPanel.add(chosenNetworkComputerCountLabel, c);

		c.gridx = 0;
		c.gridy = 5;
		simulationPanel.add(chosenNetworkEdgeCountLabel, c);

		c.gridx = 0;
		c.gridy = 6;
		simulationPanel.add(cureCountLabel, c);

		c.gridx = 0;
		c.gridy = 7;
		simulationPanel.add(cureCountField, c);

		c.gridx = 0;
		c.gridy = 8;
		simulationPanel.add(cureRateLabel, c);

		c.gridx = 0;
		c.gridy = 9;
		simulationPanel.add(cureRateField, c);

		c.gridx = 0;
		c.gridy = 10;
		simulationPanel.add(wormCountLabel, c);

		c.gridx = 0;
		c.gridy = 11;
		simulationPanel.add(wormCountField, c);

		c.gridx = 0;
		c.gridy = 12;
		simulationPanel.add(infectionRateLabel, c);

		c.gridx = 0;
		c.gridy = 13;
		simulationPanel.add(infectionRateField, c);

		c.gridx = 0;
		c.gridy = 14;
		simulationPanel.add(simulateButton, c);
	}

	private void buildFrame()
	{
		JFrame frame = new JFrame();
		frame.setLayout(new GridLayout(1, 2));

		frame.add(networkGenerationPanel);
		frame.add(simulationPanel);

		frame.setTitle("Prism");
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	// -------------------------- Action Listeners -------------------------------
	private class ErdosRenyiRadioButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			erdosRenyiProbabilityField.setEnabled(true);
			wattsStrogatzMeanDegreeField.setEnabled(false);
			wattsStrogatzRewireProbabilityField.setEnabled(false);
			erdosRenyiProbabilityField.setText("");
			wattsStrogatzMeanDegreeField.setText("");
			wattsStrogatzRewireProbabilityField.setText("");
		}
	}

	private class BarabasiAlbertRadioButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			erdosRenyiProbabilityField.setEnabled(false);
			wattsStrogatzMeanDegreeField.setEnabled(false);
			wattsStrogatzRewireProbabilityField.setEnabled(false);
			erdosRenyiProbabilityField.setText("");
			wattsStrogatzMeanDegreeField.setText("");
			wattsStrogatzRewireProbabilityField.setText("");
		}
	}

	private class WattsStrogatzRadioButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			erdosRenyiProbabilityField.setEnabled(false);
			wattsStrogatzMeanDegreeField.setEnabled(true);
			wattsStrogatzRewireProbabilityField.setEnabled(true);
			erdosRenyiProbabilityField.setText("");
			wattsStrogatzMeanDegreeField.setText("");
			wattsStrogatzRewireProbabilityField.setText("");
		}
	}

	private class GenerateNetworkButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(!validateComputerCount())
				return;

			int n = Integer.parseInt(computerCountField.getText());

			boolean success = false;

			if(erdosRenyiButton.isSelected())
			{
				if(!validateErdosRenyiProbability())
					return;

				double p = Double.parseDouble(erdosRenyiProbabilityField.getText());

				ErdosRenyiNetwork ern = new ErdosRenyiNetwork(n, p);
				success = ern.writeGraph(fsi, networkNameField.getText());
			}
			else if(barabasiAlbertButton.isSelected())
			{
				BarabasiAlbertNetwork ban = new BarabasiAlbertNetwork(n);
				success = ban.writeGraph(fsi, networkNameField.getText());
			}
			else if(wattsStrogatzButton.isSelected())
			{
				if(!validateWattsStrogatzMeanDegree(n))
					return;
				if(!validateWattsStrogatzRewireProbability())
					return;

				int k = Integer.parseInt(wattsStrogatzMeanDegreeField.getText());
				double p = Double.parseDouble(wattsStrogatzRewireProbabilityField.getText());

				WattsStrogatzNetwork wsn = new WattsStrogatzNetwork(n, k, p);
				success = wsn.writeGraph(fsi, networkNameField.getText());
			}

			if(success)
				JOptionPane.showMessageDialog(null, "The network " + networkNameField.getText() + " was successfully written to disk.");
			else
				JOptionPane.showMessageDialog(null, "The network " + networkNameField.getText() + " failed to be written to disk.");
		}
	}

	private class BrowseButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int returnVal = networkChooser.showOpenDialog(simulationPanel);

			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				chosenNetwork = new Network(networkChooser.getSelectedFile());
				chosenNetworkLabel.setText("File: " + networkChooser.getSelectedFile().getName());
				chosenNetworkLabel.paintImmediately(chosenNetworkLabel.getVisibleRect());
				chosenNetworkComputerCountLabel.setText("Computer Count: " + chosenNetwork.getN());
				chosenNetworkComputerCountLabel.paintImmediately(chosenNetworkComputerCountLabel.getVisibleRect());
				chosenNetworkEdgeCountLabel.setText("Edge Count: " + chosenNetwork.getEdges());
				chosenNetworkEdgeCountLabel.paintImmediately(chosenNetworkEdgeCountLabel.getVisibleRect());

			}
			else
			{
				chosenNetworkLabel.setText("File:       ");
				chosenNetworkLabel.paintImmediately(chosenNetworkLabel.getVisibleRect());
			}
		}
	}

	private class SimulateButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(!validateChosenNetwork())
				return;

			int n = chosenNetwork.getN();

			if(!validateCureCount(n))
				return;
			if(!validateWormCount(n))
				return;
			if(Integer.parseInt(cureCountField.getText()) > 0 && !validateCureRate())
				return;
			if(Integer.parseInt(wormCountField.getText()) > 0 && !validateInfectionRate())
				return;

			int cureCount = Integer.parseInt(cureCountField.getText());
			int wormCount = Integer.parseInt(wormCountField.getText());

			double cureRate;
			double infectionRate;
			if(cureCount > 0)
				cureRate = Double.parseDouble(cureRateField.getText());
			else
				cureRate = 0.0;

			if(wormCount > 0)
				infectionRate = Double.parseDouble(infectionRateField.getText());
			else
				infectionRate = 0.0;

			chosenNetwork.reset();
			Simulation s = new Simulation(chosenNetwork, cureCount, wormCount, cureRate, infectionRate);
			if(s.simulate(fsi))
				JOptionPane.showMessageDialog(null, "Simulation successfully written to disk");
			else
				JOptionPane.showMessageDialog(null, "Simulation failed to be written to disk");
		}
	}

	// -------------------------- Validation -------------------------------------
	private boolean validateComputerCount()
	{
		try
		{
			int computerCount = Integer.parseInt(computerCountField.getText());

			if(computerCount < 1)
			{
				JOptionPane.showMessageDialog(null, "Number of connected computers must be a positive integer.");
				return false;
			}
			else
				return true;
		}
		catch(NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(null, "Number of connected computers must be a positive integer.");
			return false;
		}
	}

	private boolean validateErdosRenyiProbability()
	{
		try
		{
			double p = Double.parseDouble(erdosRenyiProbabilityField.getText());

			if(p < 0.0 || p > 1.0)
			{
				JOptionPane.showMessageDialog(null, "Erdos-Renyi connection probability must be a floating-point value between 0 and 1.");
				return false;
			}
			else
				return true;
		}
		catch(NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(null, "Erdos-Renyi connection probability must be a floating-point value between 0 and 1.");
			return false;
		}
	}

	private boolean validateWattsStrogatzMeanDegree(int computerCount)
	{
		try
		{
			Integer degree = Integer.parseInt(wattsStrogatzMeanDegreeField.getText());

			if(degree < 1 || degree > computerCount)
			{
				JOptionPane.showMessageDialog(null, "Watts-Strogatz mean degree must be a positive integer between 1 and the computer count.");
				return false;
			}
			else
				return true;
		}
		catch(NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(null, "Watts-Strogatz mean degree must be a positive integer between 1 and the computer count.");
			return false;
		}
	}

	private boolean validateWattsStrogatzRewireProbability()
	{
		try
		{
			double p = Double.parseDouble(wattsStrogatzRewireProbabilityField.getText());

			if(p < 0.0 || p > 1.0)
			{
				JOptionPane.showMessageDialog(null, "Watts-Strogatz rewire probability must be a floating-point value between 0 and 1.");
				return false;
			}
			else
				return true;
		}
		catch(NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(null, "Watts-Strogatz rewire probability must be a floating-point value between 0 and 1.");
			return false;
		}
	}

	private boolean validateChosenNetwork()
	{
		if(chosenNetwork == null)
		{
			JOptionPane.showMessageDialog(null, "You must upload a network file before running a simulation.");
			return false;
		}
		else
			return true;
	}

	private boolean validateCureCount(int computerCount)
	{
		try
		{
			int cureCount = Integer.parseInt(cureCountField.getText());

			if(cureCount < 0 || cureCount > computerCount)
			{
				JOptionPane.showMessageDialog(null, "Cure count must be an integer between 0 and the computer count.");
				return false;
			}
			else
				return true;
		}
		catch(NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(null, "Cure count must be an integer between 0 and the computer count.");
			return false;
		}
	}

	private boolean validateWormCount(int computerCount)
	{
		try
		{
			int wormCount = Integer.parseInt(wormCountField.getText());

			if(wormCount < 0 || wormCount > computerCount)
			{
				JOptionPane.showMessageDialog(null, "Worm count must be an integer between 0 and the computer count.");
				return false;
			}
			else
				return true;
		}
		catch(NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(null, "Worm count must be an integer between 0 and the computer count.");
			return false;
		}
	}

	private boolean validateCureRate()
	{
		try
		{
			double cureRate = Double.parseDouble(cureRateField.getText());

			if(cureRate < 0.0 || cureRate > 1.0)
			{
				JOptionPane.showMessageDialog(null, "Cure rate must be a floating-point value between 0 and 1.");
				return false;
			}
			else
				return true;
		}
		catch(NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(null, "Cure rate must be a floating-point value between 0 and 1.");
			return false;
		}
	}

	private boolean validateInfectionRate()
	{
		try
		{
			double infectionRate = Double.parseDouble(infectionRateField.getText());

			if(infectionRate < 0.0 || infectionRate > 1.0)
			{
				JOptionPane.showMessageDialog(null, "Infection rate must be a floating-point value between 0 and 1.");
				return false;
			}
			else
				return true;
		}
		catch(NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(null, "Infection rate must be a floating-point value between 0 and 1.");
			return false;
		}
	}
}
