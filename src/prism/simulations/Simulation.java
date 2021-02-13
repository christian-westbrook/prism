package prism.simulations;

import java.util.ArrayList;
import java.util.Random;

import prism.interfacing.FileSystemInterface;
import prism.networks.Machine;
import prism.networks.Network;

public class Simulation
{
	private Network network;
	private int cures;
	private int worms;
	private double cureRate;
	private double infectionRate;

	private Random r;

	public Simulation(Network network, int cures, int worms, double cureRate, double infectionRate)
	{
		this.network = network;
		this.cures = cures;
		this.worms = worms;
		this.cureRate = cureRate;
		this.infectionRate = infectionRate;

		r = new Random();
	}

	public boolean simulate(FileSystemInterface fsi)
	{
		Machine genTarget;
		int genTargetID;

		int timestep = 0;
		int infected = 0;
		int cured = 0;
		int stranded = 0;

		ArrayList<Integer> candidateInfectionMachines;
		ArrayList<Integer> candidateCureMachines;
		ArrayList<Integer> candidateWormTargets;
		ArrayList<Integer> candidateCureTargets;

		int infectingMachineID;
		int curingMachineID;
		int wormTargetID;
		int cureTargetID;
		Machine wormTarget;
		Machine cureTarget;

		boolean cureVictory = false;
		boolean wormVictory = false;

		ArrayList<String> log = new ArrayList<String>();
		ArrayList<String> trace = new ArrayList<String>();

		// Compute stranded nodes
		boolean connected;
		for(Integer machineID : network.getIdentityMap().keySet())
		{
			connected = false;
			for(int j = 0; j < network.getN(); j++)
			{
				if(network.getGraph()[machineID][j] == 1)
				{
					connected = true;
					break;
				}
			}

			if(!connected)
				stranded++;
		}

		log.add("[" + timestep + "] " + "There are " + stranded + " stranded machines.");

		// Randomly generate infections
		for(int i = 0; i < worms; i++)
		{
			genTarget = null;
			genTargetID = Integer.MIN_VALUE;
			boolean validTarget = false;
			while(!validTarget)
			{
				genTargetID = r.nextInt(network.getN());

				if(network.getIdentityMap().containsKey(genTargetID))
					genTarget = network.getIdentityMap().get(genTargetID);

				if(genTarget != null && !genTarget.getCured() && !genTarget.getInfected())
					validTarget = true;
			}

			genTarget.infect();
			network.getInfectedMap().put(genTarget.getID(), true);
			log.add("[" + timestep + "] " + "Initial infection occurring at machine " + genTarget.getID());
			infected++;
			log.add("[" + timestep + "] " + "New infection count is " + infected);
		}

		// Randomly generate cures
		for(int i = 0; i < cures; i++)
		{
			genTarget = null;
			genTargetID = Integer.MIN_VALUE;
			boolean validTarget = false;
			while(!validTarget)
			{
				genTargetID = r.nextInt(network.getN());

				if(network.getIdentityMap().containsKey(genTargetID))
					genTarget = network.getIdentityMap().get(genTargetID);

				if(genTarget != null && !genTarget.getCured() && !genTarget.getInfected())
					validTarget = true;
			}

			genTarget.cure();
			network.getCuredMap().put(genTarget.getID(), true);
			log.add("[" + timestep + "] " + "Initial inoculation occurring at machine " + genTarget.getID());
			cured++;
			log.add("[" + timestep + "] " + "New inoculated count is " + cured);
		}

		// Run simulation
		log.add("[" + timestep + "] " + "Initializing simulation");
		boolean simulating = true;
		boolean wormsIsolated;
		boolean curesIsolated;

		if(worms > 0)
			wormsIsolated = false;
		else
			wormsIsolated = true;

		if(cures > 0)
			curesIsolated = false;
		else
			curesIsolated = true;

		trace.add("Timestep,Stranded,Infected,Cured");
		trace.add(timestep + "," + stranded + "," + infected +  "," + cured);

		while(simulating)
		{
			// Check if either cures or worms have won
			if(infected >= network.getN() - stranded)
				wormVictory = true;
			if(cured >= network.getN() - stranded)
				cureVictory = true;

			if(wormVictory)
				log.add("[" + timestep + "] " + "Worm victory");
			if(cureVictory)
				log.add("[" + timestep + "] " + "Cure victory");

			if(wormVictory || cureVictory)
			{
				log.add("Initial infected nodes: " + worms);
				log.add("Initial cured nodes: " + cures);
				log.add("Infection rate: " + infectionRate);
				log.add("Cure rate: " + cureRate);
				log.add("Total nodes: " + network.getN());
				log.add("Total stranded nodes: " + stranded);
				log.add("Total infected nodes: " + infected);
				log.add("Total cured nodes: " + cured);

				simulating = false;
				break;
			}

			// Check if both are stuck
			if(curesIsolated && wormsIsolated)
			{
				log.add("[" + timestep + "] " + "Both cures and worms isolated, neither can make progress");
				log.add("[" + timestep + "] " + "Game is a draw");
				log.add("Initial infected nodes: " + worms);
				log.add("Initial cured nodes: " + cures);
				log.add("Infection rate: " + infectionRate);
				log.add("Cure rate: " + cureRate);
				log.add("Total nodes: " + network.getN());
				log.add("Total infected nodes: " + infected);
				log.add("Total cured nodes: " + cured);
				trace.add(timestep + "," + stranded + "," + infected +  "," + cured);
				simulating = false;
			}

			// If no victory has occurred, increment the timestep
			timestep++;
			log.add("[" + timestep + "] " + "Timestep incremented");

			// Compute worm movement at this timestep
			if(!wormsIsolated)
			{
				for(int i = 0; i < worms; i++)
				{
					// Get all currently infected machines that could spread
					candidateInfectionMachines = new ArrayList<Integer>();
					for(Integer machineID : network.getInfectedMap().keySet())
					{
						if(network.getInfectedMap().get(machineID) == true)
						{
							for(int j = 0; j < network.getN(); j++)
							{
								if(network.getGraph()[machineID][j] == 1 && !network.getIdentityMap().get(j).getCured()
								&& !network.getIdentityMap().get(j).getInfected() && !candidateInfectionMachines.contains(machineID))
									candidateInfectionMachines.add(machineID);
							}
						}
					}

					if(candidateInfectionMachines.size() == 0)
					{
						log.add("[" + timestep + "] " + "No possible moves for worms. Worms have been isolated.");
						wormsIsolated = true;
					}
					else
					{
						// Pick one from all of the infected machines
						infectingMachineID = candidateInfectionMachines.get(r.nextInt(candidateInfectionMachines.size()));
						log.add("[" + timestep + "] " + "Worm " + i + " infecting from machine " + infectingMachineID);

						// For the infected machine, get all connected machines
						candidateWormTargets = new ArrayList<Integer>();
						for(int j = 0; j < network.getN(); j++)
						{
							if(network.getGraph()[infectingMachineID][j] ==  1)
								candidateWormTargets.add(j);
						}

						// Pick one to attempt to infect
						wormTargetID = candidateWormTargets.get(r.nextInt(candidateWormTargets.size()));
						log.add("[" + timestep + "] " + "Attempting to infect machine " + wormTargetID);

						// Roll for infection
						log.add("[" + timestep + "] " + "Infection threshold: " + (1 - infectionRate));
						double roll = r.nextDouble();
						log.add("[" + timestep + "] " + "Roll: " + roll);

						// Process result
						if(roll > 1 - infectionRate)
						{
							log.add("[" + timestep + "] " + "Hit!");
							wormTarget = network.getIdentityMap().get(wormTargetID);

							if(wormTarget.getInfected())
								log.add("[" + timestep + "] " + "Machine " + wormTargetID + " was already infected.");
							if(wormTarget.getCured())
								log.add("[" + timestep + "] " + "Machine " + wormTargetID + " was already inoculated.");
							if(!wormTarget.getInfected() && !wormTarget.getCured())
							{
								wormTarget.infect();
								network.getInfectedMap().put(wormTargetID, true);
								log.add("[" + timestep + "] " + "Machine " + wormTargetID + " was infected.");
								infected++;
								log.add("[" + timestep + "] " + "New infection count is " + infected);
							}
						}
						else
						{
							log.add("[" + timestep + "] " + "Miss!");
							log.add("[" + timestep + "] " + "Machine " + wormTargetID + " was not infected.");
						}
					}
				}
			}

			// Compute cure movement at this timestep
			if(!curesIsolated)
			{
				for(int i = 0; i < cures; i++)
				{
					// Get all currently cured machines that could inoculate neighbors
					candidateCureMachines = new ArrayList<Integer>();
					for(Integer machineID : network.getCuredMap().keySet())
					{
						if(network.getCuredMap().get(machineID) == true)
						{
							for(int j = 0; j < network.getN(); j++)
							{
								if(network.getGraph()[machineID][j] == 1 && !network.getIdentityMap().get(j).getCured() && !candidateCureMachines.contains(machineID))
									candidateCureMachines.add(machineID);
							}
						}
					}

					if(candidateCureMachines.size() == 0)
					{
						log.add("[" + timestep + "] " + "No possible moves for cures. Cures have been isolated.");
						curesIsolated = true;
					}
					else
					{
						// Pick one from all of the cured machines
						curingMachineID = candidateCureMachines.get(r.nextInt(candidateCureMachines.size()));
						log.add("[" + timestep + "] " + "Cure " + i + " inoculating from machine " + curingMachineID);

						// For the curing machine, get all connected machines
						candidateCureTargets = new ArrayList<Integer>();
						for(int j = 0; j < network.getN(); j++)
						{
							if(network.getGraph()[curingMachineID][j] ==  1)
								candidateCureTargets.add(j);
						}

						// Pick one to attempt to inoculate
						cureTargetID = candidateCureTargets.get(r.nextInt(candidateCureTargets.size()));
						log.add("[" + timestep + "] " + "Attempting to inoculate machine " + cureTargetID);

						// Roll for inoculation
						log.add("[" + timestep + "] " + "Inoculation threshold: " + (1 - cureRate));
						double roll = r.nextDouble();
						log.add("[" + timestep + "] " + "Roll: " + roll);

						// Process result
						if(roll > 1 - cureRate)
						{
							log.add("[" + timestep + "] " + "Hit!");
							cureTarget = network.getIdentityMap().get(cureTargetID);

							if(cureTarget.getInfected())
							{
								log.add("[" + timestep + "] " + "Machine " + cureTargetID + " was cured.");
								infected--;
								log.add("[" + timestep + "] " + "New infection count is " + infected);
							}
							if(cureTarget.getCured())
								log.add("[" + timestep + "] " + "Machine " + cureTargetID + " was already inoculated.");
							if(!cureTarget.getCured())
							{
								cureTarget.cure();
								network.getInfectedMap().put(cureTargetID, false);
								network.getCuredMap().put(cureTargetID, true);
								log.add("[" + timestep + "] " + "Machine " + cureTargetID + " was inoculated.");
								cured++;
								log.add("[" + timestep + "] " + "New inoculated count is " + cured);
							}
						}
						else
						{
							log.add("[" + timestep + "] " + "Miss!");
							log.add("[" + timestep + "] " + "Machine " + cureTargetID + " was not inoculated.");
						}
					}
				}
			}

			trace.add(timestep + "," + stranded + "," + infected +  "," + cured);
			System.out.println(timestep + "," + stranded + "," + infected +  "," + cured);
		}

		if(fsi.writeLog(network.getTitle(), log) && fsi.writeTrace(network.getTitle(), trace))
			return true;
		else
			return false;
	}
}
