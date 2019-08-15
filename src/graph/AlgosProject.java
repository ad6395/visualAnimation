/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author Abhishek Dongare
 */
public class AlgosProject {

    	private static int Target;
	private static int noOfInputs;
	private static int noOfParticles;
	private static int maxVelocity =10; // Maximum velocity change allowed.
	private static int upperLimit;
	private static int lowerLimit;
	private static int noOfIterations;
	private static int goldP;
	private static int diamondP;
	private static int mutualfundsP;
	private static int gPeriod;
	private static int dPeriod;
	private static int mPeriod;
	private static int bPeriod;
    public static  int epoch = 0;
    public static int gBest = 0;
    
	public AlgosProject(int target, int noOfInputs, int noOfParticles, int upperLimit, int lowerLimit, int noOfIterations,
			int goldP, int diamondP, int mutualfundsP, int gPeriod, int dPeriod, int mPeriod, int bPeriod) {
		this.Target = target;
		this.noOfInputs = noOfInputs;
		this.noOfParticles = noOfParticles;
		this.upperLimit = upperLimit;
		this.lowerLimit = lowerLimit;
		this.noOfIterations = noOfIterations;
		this.goldP = goldP;
		this.diamondP = diamondP;
		this.mutualfundsP = mutualfundsP;
		this.gPeriod = gPeriod;
		this.dPeriod = dPeriod;
		this.mPeriod = mPeriod;
		this.bPeriod = bPeriod;
	}
    

    public static ArrayList<Particle> particles = new ArrayList<Particle>();
        private static  ArrayList<Integer> particles2 = new ArrayList<>();
    private static ArrayList<Integer> particles3 = new ArrayList<>();
    static MainJFrame mj = new MainJFrame();
    public static TreeMap<Integer, Integer> particleTreeMap = new TreeMap<>();
    
    private static void initialize() {
       for (int i = 0; i < noOfParticles; i++) {
			Particle newParticle = new Particle();// create multiple with respect to the maximum allowed particles
			int total = 0;
			for (int j = 0; j < noOfInputs; j++) {
				newParticle.data(j, getRandomNumber(lowerLimit, upperLimit));// add random numbers equal to
																				// noOfInputs in the data array of the
																				// particle.
				total += newParticle.data(j);// get the random number allocated at every index in the above step and
												// add it to total which is set to 0.
				// Keep on adding the random numbers until all numbers of the array of one
				// particle are added. Thus total represents the total of all numbers of the
				// data array.
			} // j
			newParticle.pBest(total);// Initially for every particle we set the pBest as the total of all the random
										// numbers in the data array.

			particles.add(newParticle);// we add the particle to the list
		} // i
		return;
    }

    private static void PSOAlgorithm() {
        
        int gBestTest = 0;
        Particle aParticle = null;
        int dataSum=0;
        boolean done = false;

        initialize();

        while (!done) {
            // Two conditions can end this loop:
            //    if the maximum number of epochs allowed has been reached, or,
            //    if the Target value has been found.
            if (epoch < noOfIterations) {

               for (int i = 0; i < noOfParticles; i++) {
					System.out.println("Addition of these values  ");
					aParticle = particles.get(i);// its is used to get the properties of each particle
					for (int j = 0; j < noOfInputs; j++) {

						if (j < noOfInputs - 1) {
							dataSum += aParticle.data(j);

							System.out.print(aParticle.data(j) + " + ");
						} else {
							dataSum += aParticle.data(j);// storing sum of 3 inputs
//							particleMap.put(sum(i, goldP, diamondP, mutualfundsP, gPeriod, dPeriod, mPeriod, bPeriod),
//									dataSum);
							particleTreeMap.put(
									sum(i, goldP, diamondP, mutualfundsP, gPeriod, dPeriod, mPeriod, bPeriod), dataSum);
							System.out.print(aParticle.data(j) + " = " + dataSum
									+ " after making the investment as mentioned, will give amount $: ");
							dataSum = 0;

						}
					}
					particles2.add(sum(i, goldP, diamondP, mutualfundsP, gPeriod, dPeriod, mPeriod, bPeriod));
					System.out.print(sum(i, goldP, diamondP, mutualfundsP, gPeriod, dPeriod, mPeriod, bPeriod) + "\n");
					if (sum(i, goldP, diamondP, mutualfundsP, gPeriod, dPeriod, mPeriod, bPeriod) == Target) {
						done = true;
					}
				} // i

                gBestTest = Best();
                aParticle = particles.get(gBest);
                // if(any particle's pBest value is better than the gBest value, make it the new gBest value.
                if (Math.abs(Target - sum( gBestTest,  goldP,  diamondP,  mutualfundsP,  gPeriod,  dPeriod,  mPeriod,
			 bPeriod)) < Math.abs(Target - sum( gBest,  goldP,  diamondP,  mutualfundsP,  gPeriod,  dPeriod,  mPeriod,
			 bPeriod))) {
                    gBest = gBestTest;
                }
                displayParticlesJFreeChart();
                getVelocity(gBest);

                updateparticles(gBest);

                System.out.println("epoch number: " + epoch);

                epoch += 1;

            } else {
                done = true;
            }
        }
        return;
    }

    private static void getVelocity(int gBestindex) {
        //  from Kennedy & Eberhart(1995).
        //    vx[][] = vx[][] + 2 * rand() * (pbestx[][] - presentx[][]) + 
        //                      2 * rand() * (pbestx[][gbest] - presentx[][])

        int testResults = 0;
        int bestResults = 0;
        double vValue = 0.0;
        Particle aParticle = null;

        bestResults = sum( gBestindex,  goldP,  diamondP,  mutualfundsP,  gPeriod,  dPeriod,  mPeriod,
			 bPeriod);

        for (int i = 0; i < noOfParticles; i++) {
            testResults = sum( i,  goldP,  diamondP,  mutualfundsP,  gPeriod,  dPeriod,  mPeriod,
			 bPeriod);
            aParticle = particles.get(i);
            vValue = aParticle.velocity() + 2 * new Random().nextDouble() * (aParticle.pBest() - testResults) + 2 * new Random().nextDouble() * (bestResults - testResults);

            if (vValue > maxVelocity) {
                aParticle.velocity(maxVelocity);
            } else if (vValue < -maxVelocity) {
                aParticle.velocity(-maxVelocity);
            } else {
                aParticle.velocity(vValue);
            }
        }
        return;
    }

    private static void updateparticles(int gBestindex) {
        Particle gBParticle = particles.get(gBestindex);

        for (int i = 0; i < noOfParticles; i++) {
            for (int j = 0; j < noOfInputs; j++) {
                if (particles.get(i).data(j) != gBParticle.data(j)) {
                    particles.get(i).data(j, particles.get(i).data(j) + (int) Math.round(particles.get(i).velocity()));
                }
            } // j

            // Check pBest value.
            int total = sum( i,  goldP,  diamondP,  mutualfundsP,  gPeriod,  dPeriod,  mPeriod,
			 bPeriod);
            if (Math.abs(Target - total) < particles.get(i).pBest()) {
                particles.get(i).pBest(total);
            }

        } // i
        return;
    }

    private static int sum(int index, int goldP, int diamondP, int mutualfundsP, int gPeriod, int dPeriod, int mPeriod,
			int bPeriod) {
        int total = 0;
        int diamond = 0;
		int diamond1 = 0;
		int gold = 0;
		int gold1 = 0;
		int mutualFunds = 0;
		int mutualFunds1 = 0;
		int sum = 0;
		int sum1 = 0;
		int total1 = 0;
		int tax = 0;
		
		int bankInterest = 0;
		int rem = 0;
		double interest = 0;
        Particle aParticle = null;

        aParticle = particles.get(index);

        for (int i = 0; i < noOfInputs; i++) {
			sum += aParticle.data(i);// get the random numbers allocated to every index of the data array of the
		}
		// particle and add it to total. Do it continously until all numbers are added.
		// Calculation for the target
		gold = (sum * goldP) / 100;// gold investment
		gold1 = gold + (((gold * 20) / 100) * gPeriod);// gold investment
		diamond = (sum * diamondP) / 100;// diamond investment
		diamond1 = diamond + (((diamond * 30) / 100) * dPeriod);// diamond investment
		mutualFunds = (sum * mutualfundsP) / 100;
		mutualFunds1 = mutualFunds + (((mutualFunds * 14) / 100) * mPeriod);
		// sum = sum + ((sum * 11) / 100);

		// total = (sum + gold + diamond);

		// total = sum - ((sum * 20) / 100);
		rem = sum - (gold1 + diamond1 + mutualFunds1);
		bankInterest = (((rem * 11) / 100) * bPeriod);
		total1 = sum + gold1 + diamond1 + mutualFunds1 + bankInterest;
		tax = (total1 * 20) / 100;
		total = total1 - tax;
        return total;
    }

    private static void printSolution() {
        // Find solution particle.
        int i=0;
        Particle particle = null;
        for (i = 0; i < particles.size(); i++) {
            particle = particles.get(i);
            if (sum( i,  goldP,  diamondP,  mutualfundsP,  gPeriod,  dPeriod,  mPeriod,
			 bPeriod) == Target) {
                break;
            }
        }
            // Print it.
            System.out.println("Particle " + i + " has achieved Target.");
 System.out.println("PERT " + particle.mpBest);
            
               
                    System.out.print("Optimistic time: " +particle.data(0)+ " ");
               System.out.print("Most likely time: "+particle.data(1) + " ");
                    System.out.println("Pessimistic time: " +particle.data(2) + " ");
                
             // j
            System.out.print("\n");
            return;
        
    }

    private static int getRandomNumber(int low, int high) {
        int randomInteger = 0;
        Random random = new Random();

        randomInteger = random.nextInt((high - low) + 1) + low;

        // System.out.println("Random Integer in Java: " + randomInteger);

        return randomInteger;

        // return (int) ((high - low) * new Random().nextDouble() + low);
    }
    private static int Best() {
        // Returns an array index.
        int winner = 0;
        boolean foundNewWinner = false;
        boolean done = false;

        while (!done) {
            foundNewWinner = false;
            for (int i = 0; i < noOfParticles; i++) {
                if (i != winner) {             // Avoid self-comparison.
                    // The Best has to be in relation to the Target.
                    if (Math.abs(Target -sum( i,  goldP,  diamondP,  mutualfundsP,  gPeriod,  dPeriod,  mPeriod,
			 bPeriod)) < Math.abs(Target -sum( winner,  goldP,  diamondP,  mutualfundsP,  gPeriod,  dPeriod,  mPeriod,
			 bPeriod))) {
                        if (particles.get(i).data(0) <= particles.get(i).data(1) && particles.get(i).data(1) <= particles.get(i).data(2)
                                && particles.get(i).data(0) <= particles.get(i).data(2)
                                ) {
                            winner = i;
                            foundNewWinner = true;
                            //System.out.println("Winner is " +winner);
                        }
                    }

                }
            }

            if (foundNewWinner == false) {
                done = true;
            }
        }
            
        return winner;
    }

  

    public static void main(String[] args) {
        
        
        Scanner sc = new Scanner(System.in);
		//System.out.println("\t\t\t\t\t\t! PARTICLE SWARM OPTIMISATION !");
		//System.out.println("Enter the number of particles:");
		int noOfParticles = 100;
		//System.out.println("Enter the number of iterations:");
		int noOfIterations = 200;
		//System.out.println("Enter the lowerlimit of amount to be invested in $:");
		int lowerlimit = 1000;
		//System.out.println("Enter the upperlimit of amount to be invested in $::");
		int upperlimit = 2000;
		//System.out.println("Enter the target in $:");
		int target = 2302;
		//System.out.println("Enter the number of inputs:");

		int noOfInputs = 3;
		//System.out.println("Enter the percentage of amount to be invested in Gold:");
		int goldP = 10;
		//System.out.println("Enter the percentage of amount to be invested in  Diamond:");
		int diamondP = 10;
		//System.out.println("Enter the percentage of amount to be invested in  Mutual Funds:");
		int mutualfundsP = 10;
		//System.out.println("Enter the duration of time in MONTHS for which you want to invest in Gold:");
		int gPeriod = 5;

		//System.out.println("Enter the duration of time in MONTHS for which you want to invest in Diamond:");
		int dPeriod = 5;
		//System.out.println("Enter the duration of time in MONTHS for which you want to invest in Mutual Funds:");
		int mPeriod = 5;
		//System.out.println("Enter the duration of time in MONTHS for which you want to invest in Bank:");
		int bPeriod = 5;
              AlgosProject AG = new AlgosProject(target, noOfInputs, noOfParticles, upperlimit, lowerlimit, noOfIterations, goldP, diamondP,
				mutualfundsP, gPeriod, dPeriod, mPeriod, bPeriod);
             AG.PSOAlgorithm();
        AG.printSolution();
        return;
    }
    
   public static void displayParticlesJFreeChart() {
        
        try {
            SwingUtilities.invokeAndWait(
                    
                    new ParticleThread()
                    
            );        
        } catch (InterruptedException ex) {
            Logger.getLogger(AlgosProject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(AlgosProject.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        long start = System.currentTimeMillis();
        long end = 0;
        while(true){
            end = System.currentTimeMillis();
            if((end-start)>1000)break;
        }
        
    }

}
