/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
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
public class Algorithm {

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
    
	public Algorithm(int target, int noOfInputs, int noOfParticles, int upperLimit, int lowerLimit, int noOfIterations,
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

    private static void PSOAlgorithm() {// main algorithm
		int gBest = 0;
		int gBestTest = 0;
		Particle par = null;
		int Iteration = 0;
		boolean done = false;
		int dataSum = 0;
		long x = System.nanoTime();
		initialize();

		while (!done) {
			// Two conditions can end this loop:
			// if the maximum number of Iterations allowed has been reached, or,
			// if the Target value has been found.

			if (Iteration < noOfIterations) {
//this loop is used just to represent the data in the console in a format

				for (int i = 0; i < noOfParticles; i++) {
					System.out.println("Addition of these values  ");
					par = particles.get(i);// its is used to get the properties of each particle
					for (int j = 0; j < noOfInputs; j++) {

						if (j < noOfInputs - 1) {
							dataSum += par.data(j);

							System.out.print(par.data(j) + " + ");
						} else {
							dataSum += par.data(j);// storing sum of 3 inputs
							//particleMap.put(sum(i, goldP, diamondP, mutualfundsP, gPeriod, dPeriod, mPeriod, bPeriod),
									//dataSum);
							particleTreeMap.put(
									sum(i, goldP, diamondP, mutualfundsP, gPeriod, dPeriod, mPeriod, bPeriod), dataSum);
							System.out.print(par.data(j) + " = " + dataSum
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

				gBestTest = Best();// we store the result of Best function in this variable. It is basically the
									// index of the particle.
				par = particles.get(gBest);// at the beginning it is 0.
				// if(any particle's pBest value is better than the gBest value, make it the new
				// gBest value.
				if (Math.abs(Target
						- sum(gBestTest, goldP, diamondP, mutualfundsP, gPeriod, dPeriod, mPeriod, bPeriod)) < Math
								.abs(Target - sum(gBest, goldP, diamondP, mutualfundsP, gPeriod, dPeriod, mPeriod,
										bPeriod))) {// we compare the target-sum of
					// the gBestTestth index
					// particle and gBesth index
					// particle which is 0 for the
					// first iteration.Incase the If
					// condition is satisfied we
					// replace gBest with gBestTest
					// and this process continues
					// until particle at every
					// indexes are compared. At the
					// end we get the best gBest
					// which is the index of the
					// best particle.
					gBest = gBestTest;
				}
 displayParticlesJFreeChart();
				getVelocity(gBest);// to get the velocity of gBestth index particle

				updateparticles(gBest);// to update the gBestth index particle

				System.out.println("Iteration number: " + Iteration);

				Iteration += 1;

			} else {
				done = true;
			}

			long y = System.nanoTime();
			long z = y - x;
			System.out.println("Time Taken in Nano Seconds:" + z);

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
      int diamond = 0;
		int diamond1 = 0;
		int gold = 0;
		int gold1 = 0;
		int gold2 = 0;
		int mutualFunds = 0;
		int mutualFunds1 = 0;
		int sum = 0;
		int sum1 = 0;
		int total1 = 0;
		int tax = 0;
		int total = 0;
		int bankInterest = 0;
		int rem = 0;
		double interest = 0;
		Particle par = null;// create a new instance of particle and set to null
		Random randomGenerator = new Random();
		par = particles.get(index);// null is now replaced with the particle object and its properties.

		for (int i = 0; i < noOfInputs; i++) {
			sum += par.data(i);// get the random numbers allocated to every index of the data array of the
		} // particle and add it to total. Do it continously until all numbers are added.

		// get the random numbers allocated to every index of the data array of the
		// particle and add it to total. Do it continously until all numbers are added.
		// Calculation for the target
		gold = (sum * goldP) / 100;// gold investment
		for (int i = 0; i < gPeriod; i++) {
			int randomInt = randomGenerator.nextInt(50) + 1;
			// System.out.println("GOLD ROI for month " + i + " is " + randomInt);
			gold1 = gold + (((gold * 10) / 100));
			gold += gold1;
		}
		diamond = (sum * diamondP) / 100;// gold investment
		for (int i = 0; i < dPeriod; i++) {
			int randomInt = randomGenerator.nextInt(50) + 1;
			// System.out.println("Diamond ROI for month " + i + " is " + randomInt);
			diamond1 = diamond + (((diamond * 10) / 100));
			diamond += diamond1;
		}
		mutualFunds = (sum * mutualfundsP) / 100;// gold investment
		for (int i = 0; i < dPeriod; i++) {
			int randomInt = randomGenerator.nextInt(50) + 1;
			// System.out.println("MF ROI for month " + i + " is " + randomInt);
			mutualFunds1 = mutualFunds + (((mutualFunds * 10) / 100));
			mutualFunds += mutualFunds1;
		}

		total1 = sum + gold + diamond + mutualFunds;
		tax = (total1 * 20) / 100;
		total = total1 - tax;

		return total;
    }

    private static void printSolution() {
        // Find solution particle.
       int sum1 = 0;
		// Find solution particle.
		int i = 0;
		for (; i < particles.size(); i++) {
			// System.out.println("I" + i);
			// System.out.println("TEST:" + particles.get(i).getData(0));
			if (sum(i, goldP, diamondP, mutualfundsP, gPeriod, dPeriod, mPeriod, bPeriod) == Target) {
				break;
			}
		}
		// Print it.
		// System.out.println("size:" + particles.size());
		System.out.println("\nParticle Number:" + i);
		for (int j = 0; j < noOfInputs; j++) {
			try {
				sum1 += particles.get(i).data(j);
			} catch (Exception e) {

			}
			if (j < noOfInputs - 1) {
				// System.out.println("J" + j);
				try {
					// Block of code to try

					System.out.print(particles.get(i).data(j) + " + ");
				} catch (Exception e) {

				}
			} else {
				System.out.print(particles.get(i).data(j) + " = " + sum1);
			}

		} // j
		System.out.println("\nThe Target is:" + Target);
		System.out.println("\nOptimal Solution which is closest to the target after investing in GOLD: " + goldP
				+ "% for a period of " + gPeriod + " months, in DIAMOND:" + diamondP + "% for a period of " + dPeriod
				+ " months, in MUTUALFUNDS:" + mutualfundsP + "% for a period of " + mutualfundsP + " months, is $:"
				+ sum1);
		System.out.println("\nParticle " + i + " has achieved Target.");
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
        
        int A=0;
        int B=0;
        Scanner sc = new Scanner(System.in);
		System.out.println("\t\t\t\t\t\t! PARTICLE SWARM OPTIMISATION !");
		System.out.println("Enter the number of particles:");
		int noOfParticles = sc.nextInt();
		System.out.println("Enter the number of iterations:");
		int noOfIterations = sc.nextInt();
		System.out.println("Enter the lowerlimit of amount to be invested in $:");
		int lowerlimit = sc.nextInt();
		System.out.println("Enter the upperlimit of amount to be invested in $::");
		int upperlimit = sc.nextInt();
		System.out.println("Enter the target in $:");
		int target = sc.nextInt();
		System.out.println("Enter the number of inputs:");

		int noOfInputs = sc.nextInt();
		System.out.println("Enter the percentage of amount to be invested in Gold:");
		int goldP = sc.nextInt();
		System.out.println("Enter the percentage of amount to be invested in  Diamond:");
		int diamondP = sc.nextInt();
		System.out.println("Enter the percentage of amount to be invested in  Mutual Funds:");
		int mutualfundsP = sc.nextInt();
		System.out.println("Enter the duration of time in MONTHS for which you want to invest in Gold:");
		int gPeriod = sc.nextInt();

		System.out.println("Enter the duration of time in MONTHS for which you want to invest in Diamond:");
		int dPeriod = sc.nextInt();
		System.out.println("Enter the duration of time in MONTHS for which you want to invest in Mutual Funds:");
		int mPeriod = sc.nextInt();
		System.out.println("Enter the duration of time in MONTHS for which you want to invest in Bank:");
		int bPeriod = sc.nextInt();
              Algorithm AG = new Algorithm(target, noOfInputs, noOfParticles, upperlimit, lowerlimit, noOfIterations, goldP, diamondP,
				mutualfundsP, gPeriod, dPeriod, mPeriod, bPeriod);
             AG.PSOAlgorithm();
      try {
			AG.printSolution();
		} catch (Exception e) {

			// logic to get the next closest value
			int[] store = new int[particles2.size()];

			for (int i = 0; i < particles2.size(); i++) {
				store[i] = particles2.get(i);
			}
			Arrays.sort(store);
			System.out.println("Store 1:" + Arrays.toString(store));
			int x = target + ((target*5)/100);
			int y = target - ((target*5)/100);
			for (int i = 0; i < store.length; i++) {

				if (y < store[i] && store[i] < x) {
					particles3.add(store[i]);
				}
			}
			System.out.println("LIST 3" + particles3);
			int[] store2 = new int[particles3.size()];
			for (int i = 0; i < particles3.size(); i++) {
				store2[i] = particles3.get(i);
			}
			Arrays.sort(store2);
			for (int i = 0; i < store2.length; i++) {
				int l = store2.length - 1;
				A = store2[l];
			}
			for (Map.Entry<Integer, Integer> entry : particleTreeMap.entrySet()) {
				int key = entry.getKey();
				int value = entry.getValue();
				if (A == key) {
					B = value;
				}
				 System.out.println(key + " => " + value);
			}
			// System.out.println("Store 2" + Arrays.toString(store2));
			System.out.println("\nSolution cannot be found!Please enter different value!");
			System.out.println("\nInstead of " + target + " you can set target as:" + A + " and invest $:" + B);
			// System.out.println("HashMap:" + particleMap);

		}
		// System.out.println("HashMap:" + particleMap);
//		for (Map.Entry<Integer, Integer> entry : particleTreeMap.entrySet()) {
//			int key = entry.getKey();
//			int value = entry.getValue();
//
//			System.out.println(key + " => " + value);
//		}

    }
    
   public static void displayParticlesJFreeChart() {
        
        try {
            SwingUtilities.invokeAndWait(
                    
                    new ParticleThread()
                    
            );        
        } catch (InterruptedException ex) {
            Logger.getLogger(Algorithm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Algorithm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        long start = System.currentTimeMillis();
        long end = 0;
        while(true){
            end = System.currentTimeMillis();
            if((end-start)>1000)break;
        }
        
    }

}
