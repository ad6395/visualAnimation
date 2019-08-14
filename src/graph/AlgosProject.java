/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author vighnesh
 */
public class AlgosProject {

    	private static int Target=2400;
	private static int noOfInputs=3;
	private static int noOfParticles=10;
	private static int maxVelocity = 10; // Maximum velocity change allowed.
	private static int upperLimit=2000;
	private static int lowerLimit=1000;
	private static int noOfIterations=20;
	private static int goldP=10;
	private static int diamondP=10;
	private static int mutualfundsP=10;
	private static int gPeriod=5;
	private static int dPeriod=5;
	private static int mPeriod=5;
	private static int bPeriod=5;
    public static  int epoch = 0;
    public static int gBest = 0;
    

    public static ArrayList<Particle> particles = new ArrayList<Particle>();
    static MainJFrame mj = new MainJFrame();

    private static void initialize() {
        for (int i = 0; i < noOfParticles; i++) {
            Particle newParticle = new Particle();
            int total = 0;
            
                newParticle.data(0, getRandomNumber(lowerLimit, upperLimit));
                newParticle.data(2, getRandomNumber(newParticle.data(0), upperLimit));
                newParticle.data(1, getRandomNumber(newParticle.data(0), newParticle.data(2)));
                total = newParticle.data(0) + newParticle.data(1) + newParticle.data(2);
             // j
            newParticle.pBest(total);
            particles.add(newParticle);
        } // i
        return;
    }

    private static void PSOAlgorithm() {
        
        int gBestTest = 0;
        Particle aParticle = null;
        
        boolean done = false;

        initialize();

        while (!done) {
            // Two conditions can end this loop:
            //    if the maximum number of epochs allowed has been reached, or,
            //    if the Target value has been found.
            if (epoch < noOfIterations) {

                for (int i = 0; i < noOfParticles; i++) {
                    aParticle = particles.get(i);
                      System.out.print("Optimistic time: " +particles.get(i).data(0)+ " ");
               System.out.print("Most likely time: "+particles.get(i).data(1) + " ");
                    System.out.println("Pessimistic time: " +particles.get(i).data(2) + " ");
                System.out.print("PERT Fitness: " +testProblem(i));
             // j
            System.out.print("\n");

                    
                    if (testProblem(i) == Target) {
                        done = true;
                    }
                } // i

                gBestTest = minimum();
                aParticle = particles.get(gBest);
                // if(any particle's pBest value is better than the gBest value, make it the new gBest value.
                if (Math.abs(Target - testProblem(gBestTest)) < Math.abs(Target - testProblem(gBest))) {
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

        bestResults = testProblem(gBestindex);

        for (int i = 0; i < noOfParticles; i++) {
            testResults = testProblem(i);
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
            int total = testProblem(i);
            if (Math.abs(Target - total) < particles.get(i).pBest()) {
                particles.get(i).pBest(total);
            }

        } // i
        return;
    }

    private static int testProblem(int index) {
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
        return total=sum;
    }

    private static void printSolution() {
        // Find solution particle.
        int i=0;
        Particle particle = null;
        for (i = 0; i < particles.size(); i++) {
            particle = particles.get(i);
            if (testProblem(i) == Target) {
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
        return (int) ((high - low) * new Random().nextDouble() + low);
    }

    private static int minimum() {
        // Returns an array index.
        int winner = 0;
        boolean foundNewWinner = false;
        boolean done = false;

        while (!done) {
            foundNewWinner = false;
            for (int i = 0; i < noOfParticles; i++) {
                if (i != winner) {             // Avoid self-comparison.
                    // The minimum has to be in relation to the Target.
                    if (Math.abs(Target -testProblem(i)) < Math.abs(Target -testProblem(winner))) {
                        if (particles.get(i).data(0) <= particles.get(i).data(1) && particles.get(i).data(1) <= particles.get(i).data(2)
                                && particles.get(i).data(0) <= particles.get(i).data(2)
                                ) {
                            winner = i;
                            foundNewWinner = true;
                            System.out.println("Winner is " +winner);
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
        PSOAlgorithm();
        printSolution();
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
