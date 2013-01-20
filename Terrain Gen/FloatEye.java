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


/**
 * 
 * Represents the camera through which the world is viewed.
 * @author Dylan Craine
 *
 */

public class FloatEye {
	double x;
	double y;
	double z;
	
	/**
	 * 
	 * @param _x The eye's x location
	 * @param _y The eye's y location
	 * @param _z The eye's z location
	 */
	
	public FloatEye(double _x, double _y, double _z){
		x = _x;
		y = _y;
		z = _z;
		
	}
	
	public Mote floatMote(){
		return new Mote(x, y, z);
	}
}
