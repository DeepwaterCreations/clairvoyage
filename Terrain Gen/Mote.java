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

/**
 * Represents a point in 3D space associated with a color.
 * 
 * @author Dylan Craine
 *
 */

public class Mote {

	double x;
	double y;
	double z;

	Color hue;
	
	public Mote(double _x, double _y, double _z, Color c){
		x = _x;
		y = _y;
		z = _z;

		hue = c;
		
	}
	
	public Mote(double _x, double _y, double _z){
		x = _x;
		y = _y;
		z = _z;
		
		hue = new Color(0, 0, 0);
	}
	
	public void rotate(double radx, double rady, double radz){
		double dist;
		double newx;
		double newy;
		double newz;
		newy = Math.cos(radx)*(y) - Math.sin(radx)*(z);
		newz = Math.sin(radx)*(y) + Math.cos(radx)*(z);
		y = newy;
		z = newz;
		newz = Math.cos(rady)*(z) - Math.sin(rady)*(x);
		newx = Math.sin(rady)*(z) + Math.cos(rady)*(x);
		z = newz;
		x = newx;
		newx = Math.cos(radz)*(x) - Math.sin(radz)*(y);
		newy = Math.sin(radz)*(x) + Math.cos(radz)*(y);
		x = newx;
		y = newy;
		/*
		//X axis
		dist = Math.sqrt(Math.pow(y, 2) + Math.pow(z, 2));
		newy = Math.cos(radx + Math.atan2(z, y)) * dist;
		newz = Math.sin(radx + Math.atan2(z, y)) * dist;
		y = newy;
		z = newz;
		//Y axis
		dist = Math.sqrt(Math.pow(z, 2) + Math.pow(x, 2));
		newz = Math.cos(rady + Math.atan2(x, z)) * dist;
		newx = Math.sin(rady + Math.atan2(x, z)) * dist;
		z = newz;
		x = newx;
		//Z axis
		dist = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		newx = Math.cos(radz + Math.atan2(y, x)) * dist;
		newy = Math.sin(radz + Math.atan2(y, x)) * dist;
		x = newx;
		y = newy;
		*/
	
	}
	
	/**
	 * Rotates the mote around a given origin.
	 * @param ox rotation origin x coordinate.
	 * @param oy rotation origin y coordinate.
	 * @param oz rotation origin z coordinate.
	 * @param radx radians around x axis. 
	 * @param rady radians around y axis.
	 * @param radz radians around z axis.
	 */
	public void rotate(double ox, double oy, double oz, double radx, double rady, double radz){
		double dist;
		double newx;
		double newy;
		double newz;
		newy = Math.cos(radx)*(y-oy) - Math.sin(radx)*(z-oz);
		newz = Math.sin(radx)*(y-oy) + Math.cos(radx)*(z-oz);
		y = newy;
		z = newz;
		newz = Math.cos(rady)*(z-oz) - Math.sin(rady)*(x-ox);
		newx = Math.sin(rady)*(z-oz) + Math.cos(rady)*(x-ox);
		z = newz;
		x = newx;
		newx = Math.cos(radz)*(x-ox) - Math.sin(radz)*(y-oy);
		newy = Math.sin(radz)*(x-ox) + Math.cos(radz)*(y-oy);
		x = newx;
		y = newy;
		/*
		//X axis
		dist = Math.sqrt(Math.pow(y - oy, 2) + Math.pow(z - oz, 2));
		newy = (Math.cos(radx + Math.atan2(z - oz, y - oy)) * dist) + oy;
		newz = (Math.sin(radx + Math.atan2(z - oz, y - oy)) * dist) + oz;
		y = newy;
		z = newz;
		//Y axis
		dist = Math.sqrt(Math.pow(z - oz, 2) + Math.pow(x - ox, 2));
		newz = (Math.cos(rady + Math.atan2(x - ox, z - oz)) * dist) + oz;
		newx = (Math.sin(rady + Math.atan2(x - ox, z - oz)) * dist) + ox;
		z = newz;
		x = newx;
		//Z axis
		dist = Math.sqrt(Math.pow(x - ox, 2) + Math.pow(y - oy, 2));
		newx = (Math.cos(radz + Math.atan2(y - oy, x - ox)) * dist) + ox;
		newy = (Math.sin(radz + Math.atan2(y - oy, x - ox)) * dist) + oy;
		x = newx;
		y = newy;
		*/
		
	}
	
	public Mote normalize(){
		double dist = Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2));
		Mote norm = new Mote(x/dist, y/dist, z/dist, hue);
		return norm;
	}
	
	//Static methods for vector arithmetic.
	//Not much point to the color portions of them, but whatever. Maybe it will turn out to be useful.
	public static Mote subtract(Mote m1, Mote m2){
		Color diffColor = new Color(Math.abs(m1.hue.getRed() - m2.hue.getRed()), Math.abs(m1.hue.getGreen() - m2.hue.getGreen()), Math.abs(m1.hue.getBlue() - m2.hue.getBlue()));
		Mote m3 = new Mote(m1.x + -m2.x, m1.y + -m2.y, m1.z + -m2.z, diffColor);
		return m3;
	}
	
	public static Mote add(Mote m1, Mote m2){
		Color sumColor = new Color(Math.min(m1.hue.getRed() + m2.hue.getRed(), 256), Math.min(m1.hue.getGreen() - m2.hue.getGreen(), 256), Math.min(m1.hue.getBlue() - m2.hue.getBlue(), 256));
		Mote m3 = new Mote(m1.x + m2.x, m1.y + m2.y, m1.z + m2.z, sumColor);
		return m3;
	}
	
	public static Mote cross(Mote m1, Mote m2){
		Mote m3 = new Mote((m1.y * m2.z) - (m1.z * m2.y), (m1.z * m2.x) - (m1.x * m2.z), (m1.x * m2.y) - (m1.y * m2.x));
		return m3;
	}
	
	public static double dot(Mote m1, Mote m2){
		double scalar = (m1.x * m2.x) + (m1.y * m2.y) + (m1.z * m2.z);
		return scalar;
	}
	
	public static Mote scalarMult(double s, Mote m){
		Color multColor = new Color((int) (m.hue.getRed() * s) % 256, (int) (m.hue.getGreen() * s) % 256, (int) (m.hue.getBlue() * s) % 256);
		Mote newMote = new Mote(m.x * s, m.y * s, m.z * s);
		return newMote; 
	}
	
	
	
}
