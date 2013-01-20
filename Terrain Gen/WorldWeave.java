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

import java.awt.Color;
import java.util.ArrayList;


/**
 * Creates a set of LandShards based on z values associated with a grid
 * of x,y coordinates. 
 * @author Dylan Craine
 *
 */

public class WorldWeave {

	double x;
	double y;
	double z;
	double height;
	double width;
	double depth;
	//double xrot;
	
	ArrayList<LandShard> triangles; 
	
	public WorldWeave(double _x, double _y, double _z, double w, double d, double h/*, double xr*/){
		//These specify the size and origin of the overall surface. 
		x = _x;
		y = _y;
		z = _z;
		width = w;
		depth = d;
		height = h;
		//xrot = xr;
		
		triangles = new ArrayList<LandShard>();
	}
	
	public ArrayList<LandShard> getTriangles(){
		return triangles;
	}
	
	/**
	 * Takes a 2d array of values and turns them into a set of triangles (LandShard objects)
	 * whose vertices are positioned at grid coordinates corresponding to the array elements, with z values
	 * set to the array element value at those coordinates.  
	 * @param lattice a 2d array of height values.
	 */
	public void loomWeave(double[][] lattice){
		boolean colorflip = true;
		Color blue = new Color(0, 128, 255);
		Color red = new Color(255, 128, 0);
		Color green = new Color(0, 255, 128);
		Color yellow = new Color(255, 255, 0);
		Color c;
		Mote v0;
		Mote v1;
		Mote v2;
		Mote[][] windingFrame = raiseFrame(lattice);
		//System.out.println("framesize: " + windingFrame.length + " " + windingFrame[0].length);
		
		//Warp is y axis, weft is x.
		//Don't ask me why I don't just CALL them x and y.
		//I mean, it's an interesting metaphor, but it's not exactly easy to read.
		for(int warp = 0; warp < windingFrame.length; warp++){
			for(int weft = 0; weft < windingFrame[0].length; weft++){
				//For each point on the grid, I create four triangles in the shape of a diamond
				//between the point in question and its horizontal and vertical neighbors, if
				//it has them:
				//..o..O..o.
				//.../A|C\..
				//..O__*__O.
				//...\B|D/..
				//..o..O..o.
				//where A, B, C, D are the triangles generated (in that order) when looking at point *
				//O are horizontal and vertical neighbors, and o are diagonal neighbors (which aren't looked at
				//for this stage of the process.)
				//Vertices are added to the triangle counterclockwise from the top. 
				
				//This alternates colors assigned to the landshards.
				//Only for testing purposes.
				/*
				if(colorflip)
					c = blue;
				else
					c = red;
				colorflip = !colorflip;
				*/
				
				//This divides the whole surface into quarters and gives each a different color.
				//Only for testing purposes.
				if(warp < windingFrame.length/2){
					if(weft < windingFrame[0].length/2)
						c = red;
					else 
						c = blue;
				}
				else{
					if(weft < windingFrame[0].length/2)
						c = green;
					else 
						c = yellow;
				}
				
				
				if(weft > 0){
					if(warp > 0){
						v0 = windingFrame[weft-1][warp-1];
						v1 = windingFrame[weft-1][warp]; 
						v2 = windingFrame[weft][warp];
//						v0.rotate(x + (width/2), y + (depth/2), z + (height/2), xrot, 0, 0);
//						v1.rotate(x + (width/2), y + (depth/2), z + (height/2), xrot, 0, 0);
//						v2.rotate(x + (width/2), y + (depth/2), z + (height/2), xrot, 0, 0);
						triangles.add(new LandShard(v0, v1, v2,	c));
						//System.out.println(weft + " " + warp + " A");
					}
					if(warp < windingFrame.length - 1){
						v0 = windingFrame[weft-1][warp]; 
						v1 = windingFrame[weft][warp+1]; 
						v2 = windingFrame[weft][warp];
//						v0.rotate(x + (width/2), y + (depth/2), z + (height/2), xrot, 0, 0);
//						v1.rotate(x + (width/2), y + (depth/2), z + (height/2), xrot, 0, 0);
//						v2.rotate(x + (width/2), y + (depth/2), z + (height/2), xrot, 0, 0);
						triangles.add(new LandShard(v0, v1, v2,	c));
						//System.out.println(weft + " " + warp + " B");
					}
				}
				if(weft < windingFrame[0].length - 1){
					if(warp > 0){
						v0 = windingFrame[weft][warp-1]; 
						v1 = windingFrame[weft+1][warp]; 
						v2 = windingFrame[weft][warp];
//						v0.rotate(x + (width/2), y + (depth/2), z + (height/2), xrot, 0, 0);
//						v1.rotate(x + (width/2), y + (depth/2), z + (height/2), xrot, 0, 0);
//						v2.rotate(x + (width/2), y + (depth/2), z + (height/2), xrot, 0, 0);
						triangles.add(new LandShard(v0, v1, v2,	c));
						//System.out.println(weft + " " + warp + " C");
					}
					if(warp < windingFrame.length - 1){
						v0 = windingFrame[weft][warp]; 
						v1 = windingFrame[weft][warp+1]; 
						v2 = windingFrame[weft+1][warp];
//						v0.rotate(x + (width/2), y + (depth/2), z + (height/2), xrot, 0, 0);
//						v1.rotate(x + (width/2), y + (depth/2), z + (height/2), xrot, 0, 0);
//						v2.rotate(x + (width/2), y + (depth/2), z + (height/2), xrot, 0, 0);
						triangles.add(new LandShard(v0, v1, v2,	c));
						//System.out.println(weft + " " + warp + " D");
					}
				}
				
			}
		}
	}
	
	/**
	 * Takes a set of values in a 2d array, turns them into a set of motes with z axis position
	 * based on the value in the corresponding array slot and x and y values from array indices but 
	 * normalized to WorldWeave's dimensions.   
	 * @param lattice a 2d array of values to become the heights of corresponding points.
	 * @return a 2d array of Motes based on the input array.
	 */
	private Mote[][] raiseFrame(double[][] lattice){
		Mote[][] windingFrame = new Mote[lattice.length][lattice[0].length];
		for(int warp = 0; warp < lattice.length; warp++){
			for(int weft = 0; weft < lattice[0].length; weft++){
				windingFrame[weft][warp] = new Mote((width/windingFrame.length * weft) + x, 
						(depth/windingFrame[0].length * warp) + y,
						(lattice[weft][warp]) + z); 	
			}	
		}
		return windingFrame;
	}
			
}
