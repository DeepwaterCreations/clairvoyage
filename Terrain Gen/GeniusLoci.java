/*
 * Copyright 2011 Dylan Craine
 * 
    This file is part of Clairvoyage, a 3d terrain generator (and possibly explorer, someday).

    RaG TeA is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    RaG TeA is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with RaG TeA.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

import java.util.Random;


/**
 * Generates values across a 2-dimensional array based on the diamond-square algorithm.
 * @author Dylan Craine
 *
 */
public class GeniusLoci {
	
	
	//Verily, we shall pluck numbers down from the very sky itself - the numbers that are carried by the currents of the
	//aether that ties and bind all things! We shall snatch them from its wild caprices and tie them down, force them
	//to do our bidding!
	Random aethericBreeze; 
	//Because what fun is a naming convention that actually makes sense? No fun at all, I say.
	
	double[][] lattice;
	double roughness;
	
	/**
	 * 
	 * @param seed a seed number for the RNG so that the terrain is reproducible.
	 * @param size the array of height values will have side length of 2^(size) + 1, which is necessary
	 * because of the way the algorithm divides lengths in half. So, appropriate size values are probably between 5 and 10. 
	 */
	public GeniusLoci(long seed, int size, double rough){
		aethericBreeze = new Random(seed);
		lattice = new double[(int) (Math.pow(2, size) + 1)][(int) (Math.pow(2, size) + 1)]; 
		roughness = rough;
		manifestTerrain();
		
	}
	
	private void manifestTerrain(){
		int division = lattice.length - 1;
		while(division > 1){
			for(int x = 0;x < lattice.length - 1;x += division){
				for(int y = 0;y < lattice.length - 1;y += division){
					//System.out.println("X: " + x + " Y: " + y + " division: " + division);
					squareStep(y, x + division, y + division, x);
				}
			}
			for(int x = 0; x < lattice.length - 1;x += division){
				for(int y = 0;y < lattice.length - 1; y += division){
					//Calculates centers of diamonds to the top, then to the left of the center points calculated in the
					//previous loop.
					diamondStep(Math.abs(y - (division>>1)) % (lattice[0].length), x + division, (y + (division>>1)), x, division);
					diamondStep(y, (x + (division>>1)), y + division, Math.abs(x - (division>>1)) % (lattice.length), division);
				}
			}
			reduceRandom();
			division = division >> 1; //A bit shift! Maybe my first ever! So exciting!
			//System.out.println(division);
		}
	}
	
	/**
	 * For a given square, sets the center point value to the average of the corner values, plus a 
	 * psuedorandom component.  
	 * 
	 * @param top the y index of the top two points.
	 * @param right the x index of the rightmost two points.
	 * @param bottom the y index of the bottom two points.
	 * @param left the x index of the leftmost two points.
	 */
	private void squareStep(int top, int right, int bottom, int left){
		//newvalue is the average of the four corner values.
		double newvalue = (lattice[right][top] +
							lattice[left][top] +
							lattice[right][bottom] +
							lattice[left][bottom])/4; 
		newvalue += getRandom();
		lattice[((right-left)/2) + left][(bottom-top)/2 + top] = newvalue;
		//System.out.println("Top: " + top  + " Right: " + right + " Bottom: " + bottom + " Left: " + left);
		//System.out.println("Square assigning " + newvalue + " to " + (((right-left)/2) + left) + ", " + (((bottom-top)/2) + top));
	}
	
	private void diamondStep(int top, int right, int bottom, int left, int division){
		//System.out.println("Top: " + top  + " Right: " + right + " Bottom: " + bottom + " Left: " + left);
		int centerx = right - (division>>1);
		int centery = bottom - (division>>1);
		double newvalue = (lattice[centerx][top] +
							lattice[right][centery] +
							lattice[centerx][bottom] +
							lattice[left][centery])/4;
		newvalue += getRandom();
		lattice[centerx][centery] = newvalue;
		//Wrap from top to bottom edge and from left ro right edge.
		if(centerx == 0)
			lattice[lattice.length - 1][centery] = newvalue;
		if(centery == 0)
			lattice[centerx][lattice[0].length - 1] = newvalue;
		//System.out.println("Diamond assigning " + newvalue + " to " + centerx + ", " + centery);
	}
	
	/**
	 * Returns an appropriate psuedorandomly generated number to alter the height of the
	 * next point set by the diamond and square algorithms.
	 * @return  a psuedorandom double.
	 */
	private double getRandom(){
		double newnum = aethericBreeze.nextDouble();
		//gets a value between roughness and -roughness.
		newnum *= roughness;
		if(aethericBreeze.nextInt(2) == 1)
			newnum = -newnum;
		return newnum;
	}
	
	/**
	 * Reduces the range of the random number generator.
	 */
	private void reduceRandom(){
		roughness *= .5;
	}

	/**
	 * Returns a string containing lattice values arranged in a 2d grid.
	 */
	public String toString(){
		String returnString = new String("");
		for(int y = 0; y < lattice[0].length; y++){
			for(int x = 0; x < lattice.length; x++){
				returnString = returnString + " " + lattice[x][y]; 
			}
			returnString = returnString + "\n"; 
		}
		return returnString;
	}
	
}


