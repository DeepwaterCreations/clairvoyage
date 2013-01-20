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


public class LandShard implements Comparable<LandShard>{
	
	//Vertices, counterclockwise.
	Mote v0, v1, v2;
	//normal and d define a plane: The set of points that satisfy dot(point, normal) = d
	Mote normal;
	Mote center;
	double d;
	double dist;
	Color color;
	
	public LandShard(Mote _v0, Mote _v1, Mote _v2){
		v0 = _v0;
		v1 = _v1;
		v2 = _v2;
		Mote sub10 = Mote.subtract(v1, v0);
		Mote sub20 = Mote.subtract(v2, v0); 
		normal = Mote.cross(sub10, sub20);
		//System.out.println("Subtract v1, v0:  x = " + sub10.x + " y = " + sub10.y + " z = " + sub10.z);
		//System.out.println("Subtract v2, v0:  x = " + sub20.x + " y = " + sub20.y + " z = " + sub20.z);
		//System.out.println("Normal x:" + normal.x + " y:" + normal.y + " z:" + normal.z);
		d = Mote.dot(normal, v0);
		//System.out.println("d = " + d);
		
		//If unspecified, color is the average of the colors assigned to the vertices.
		color = new Color((v0.hue.getRed() + v1.hue.getRed() + v2.hue.getRed())/3,
				(v0.hue.getGreen() + v1.hue.getGreen() + v2.hue.getGreen())/3,
				(v0.hue.getBlue() + v1.hue.getBlue() + v2.hue.getBlue())/3);
		dist = 0;
		
		center = new Mote(Math.max(Math.max(v0.x, v1.x), v2.x) - Math.min(Math.min(v0.x, v1.x), v2.x), 
						  Math.max(Math.max(v0.y, v1.y), v2.y) - Math.min(Math.min(v0.y, v1.y), v2.y),
						  Math.max(Math.max(v0.z, v1.z), v2.z) - Math.min(Math.min(v0.z, v1.z), v2.z));
							
	}
	
	public LandShard(Mote _v0, Mote _v1, Mote _v2, Color c){
		v0 = _v0;
		v1 = _v1;
		v2 = _v2;
		Mote sub10 = Mote.subtract(v1, v0);
		Mote sub20 = Mote.subtract(v2, v0); 
		normal = Mote.cross(sub10, sub20);
		d = Mote.dot(normal, v0);
		color = c;
		dist = 0;
		center = new Mote(Math.max(Math.max(v0.x, v1.x), v2.x) - Math.min(Math.min(v0.x, v1.x), v2.x), 
				  Math.max(Math.max(v0.y, v1.y), v2.y) - Math.min(Math.min(v0.y, v1.y), v2.y),
				  Math.max(Math.max(v0.z, v1.z), v2.z) - Math.min(Math.min(v0.z, v1.z), v2.z));
	
	}
	
	public LandShard(Mote _v0, Mote _v1, Mote _v2, Color c, double _dist){
		v0 = _v0;
		v1 = _v1;
		v2 = _v2;
		Mote sub10 = Mote.subtract(v1, v0);
		Mote sub20 = Mote.subtract(v2, v0); 
		normal = Mote.cross(sub10, sub20);
		d = Mote.dot(normal, v0);
		color = c;
		dist = _dist;
		center = new Mote(Math.max(Math.max(v0.x, v1.x), v2.x) - Math.min(Math.min(v0.x, v1.x), v2.x), 
				  Math.max(Math.max(v0.y, v1.y), v2.y) - Math.min(Math.min(v0.y, v1.y), v2.y),
				  Math.max(Math.max(v0.z, v1.z), v2.z) - Math.min(Math.min(v0.z, v1.z), v2.z));
	
	}
	
	public Color getShaded(Mote sun){
		//System.out.println(center.x + " " + center.y + " " + center.z);
		Mote sunvect = new Mote(sun.x - center.x, sun.y - center.y, sun.z - center.z);
		sunvect = sunvect.normalize();
		Mote normnorm = normal.normalize(); //I don't even why I do this. >:C
		//double shade = Math.abs(sunvect.x * normnorm.x + sunvect.y * normnorm.y + sunvect.z * normnorm.z);
		double shade = Math.abs(Mote.dot(sunvect, normnorm));
		//System.out.println("Shade: " + shade);
		Color shadecolor = new Color((int)(color.getRed()*shade), (int)(color.getGreen()*shade), (int)(color.getRed()*shade));
		//System.out.println("RED: " + shadecolor.getRed() + " GREEN: " + shadecolor.getGreen() + " BLUE: " + shadecolor.getBlue());
		return shadecolor;
	}
	
	/**
	 * DEPRECATED. Gloriously, gloriously deprecated. I'll just leave this here for posterity's sake.
	 * Finds the point of intersection between a ray and the triangle represented by 
	 * the LandShard. 
	 * Based on concepts and psuedocode from
	 * "3-D Computer Graphics" by Samuel R. Buss. 
	 * @param source A Mote representing the source of the ray.
	 * @param direction A unit vector determining the direction of the ray.
	 * @return the point where the ray specified by source and direction intersects with the 
	 * LandShard, or null if the LandShard is never intersected.
	 */
	
	public Mote pointPierced(Mote source, Mote direction){
		Mote intersect;
		//System.out.println(d - Mote.dot(source, normal));
		double alpha = ((d - Mote.dot(source, normal))/Mote.dot(direction, normal));
		//if(alpha > 0)
			//System.out.println("Alpha: " + alpha);
		if(alpha < 0 || Mote.dot(direction, normal) == 0){ 
			//System.out.println("No planar intersection.");
			return null; //No intersection with the plane.
		}
		else
			intersect = Mote.add(source, Mote.scalarMult(alpha, direction));

		//We have an intersection with the plane. Now we have to determine if it lies in the triangle. 
		//We do this with barycentric coordinates.
		Mote e1 = Mote.subtract(v1, v0);
		Mote e2 = Mote.subtract(v2, v0);
		double a = Mote.dot(e1, e1);
		double b = Mote.dot(e1, e2);
		double c = Mote.dot(e2, e2);
		double bigD = (a * c) - Math.pow(b, 2);
		double bigA = a/bigD;
		double bigB = b/bigD;
		double bigC = c/bigD;
		Mote ub = Mote.subtract(Mote.scalarMult(bigC, e1), Mote.scalarMult(bigB, e2));
		Mote uc = Mote.subtract(Mote.scalarMult(bigA, e2), Mote.scalarMult(bigB, e1));
		
		Mote r = Mote.subtract(intersect, v0);
		double beta = Mote.dot(ub, r);
		//if(beta > 0)
			//System.out.println("Beta: " + beta);
		if(beta < 0){
			//System.out.println("No intersection beta");
			return null;
		}
		double gamma = Mote.dot(uc, r);
		//if(gamma + beta < 1)
			//System.out.println("Gamma: " + gamma);
		if(gamma < 0){
			//System.out.println("No gamma intersection");
			return null;
		}
		alpha = 1 - beta - gamma;
		if(alpha < 0){
			//System.out.println("Alpha - no intersection.");
			return null;
		}
		
		return intersect; //Give this an appropriate color when I have a color model.
	}

	//It is supposed to return a negative number, 0 or a positive number
	//depending on whether this 
	//LandShard or the other LandShard has the greatest dist.
	public int compareTo(LandShard other) {
//		double othergreatest = Math.min(other.v0.y, Math.min(other.v1.y, other.v2.y));
//		double thisgreatest = Math.min(v0.y, Math.min(v1.y, v2.y));
//		return (int) (othergreatest - thisgreatest);
		return (int) (other.dist-dist);
		
	}
	
}
