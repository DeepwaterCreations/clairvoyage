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

import javax.swing.*;


	/**
	 * Draws 3D terrain.
	 * @author Dylan Craine
	 *
	 */

public class CrystalBall {

	public static void main(String[] args) {
		
		final int WINDOW_WIDTH = 700;
		final int WINDOW_HEIGHT = 700;
		double visdepth = -200; //Camera y axis distance from origin
		
		GeniusLoci placespirit = new GeniusLoci(42, 5, 500);
		//GeniusCaeli skyspirit = new GeniusCaeli(123456, 3, 4, 6, 3);
		//System.out.println(placespirit);
		//System.out.println(skyspirit);
		
		WorldWeave testland = new WorldWeave(-512, 200, -100, 1024, 1024, 200);
		testland.loomWeave(placespirit.lattice);
		
		JFrame frame = new JFrame("Does this world exist?");
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FloatEye eye = new FloatEye(0, visdepth, 0);
		SeerPanel panel = new SeerPanel(WINDOW_WIDTH, WINDOW_HEIGHT, eye, testland);
		frame.add(panel);
		panel.addMouseMotionListener(panel);
		//LandShard[] landscape = new LandShard[2];
		//landscape[0] = new LandShard(new Mote(-256, 512, -100), new Mote(-256, 0, -100), new Mote(256, 0, -100));
		//landscape[1] = new LandShard(new Mote(-256, 512, 100), new Mote(256, 0, 100), new Mote(256, 512, 100)); 
		panel.repaint();
		frame.setVisible(true);
		
		while(true){
			panel.repaint();
		}

	}

}
