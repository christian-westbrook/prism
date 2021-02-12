package prism.interfacing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import prism.networks.Network;
import prism.networks.ErdosRenyiNetwork;
import prism.networks.BarabasiAlbertNetwork;
import prism.networks.WattsStrogatzNetwork;

public class UserInterface
{
	final int WINDOW_WIDTH = 380; // Window width in pixels
	final int WINDOW_HEIGHT = 350; // Window height in pixels

	private FileSystemInterface fsi;

	private GridBagConstraints c;
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
	private JFrame frame;

	// ---------------------------------------------------------------------------
	// Constructor
	// ---------------------------------------------------------------------------
	public UserInterface(FileSystemInterface fsi)
	{
		this.fsi = fsi;

		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;

		buildNetworkGenerationPanel();
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
}
