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

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import javax.swing.*;

/**
 * Renders a 3D landscape
 * @author Dylan Craine
 *
 */

@SuppressWarnings("serial")
public class SeerPanel extends JPanel implements MouseMotionListener{
	
	int mousex;
	int mousey;
	int mousexthreshold;
	int mouseythreshold;
	double zradPerDist;
	double xradPerDist;
	double width;
	double height;
	double rotxcenter;
	double rotycenter;
	double rotzcenter;
	FloatEye eye;
	ArrayList<LandShard> leyLines;
	ArrayList<LandShard> leyLines2;
	Robot cursorSerpent;
	boolean serpentMove;
	Mote burningSun;
	
	public SeerPanel(double w, double h, FloatEye e, WorldWeave l){
		width = w;
		height = h;
		eye = e;
		rotxcenter = l.x + (l.width/2);
		rotycenter = l.y + (l.depth/2);
		rotzcenter = l.z + (l.height/2);
		leyLines = l.getTriangles();
		leyLines2 = new ArrayList<LandShard>();
		
		mousex = 0;
		mousey = 0;
		mousexthreshold = (int) width*5;
		mouseythreshold = (int) height*5;
		zradPerDist = (2*Math.PI)/mousexthreshold;
		xradPerDist = (2*Math.PI)/mouseythreshold;
		try {
			cursorSerpent = new Robot();
		} catch (AWTException fit) {
			fit.printStackTrace();
		}
		serpentMove = false;

		burningSun = new Mote(300, 200, 500); //These are the coordinates of the light source.
	}
	
	
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(0, 0, 0));
		g2d.fillRect(0, 0, (int) width, (int) height);
				
		
				for(LandShard shard : leyLines){
					rotateShard(shard);
				}
				Collections.sort(leyLines2);
				
				for(LandShard shard : leyLines2){	
					int[] v0point = translatePoint(shard.v0);
					int[] v1point = translatePoint(shard.v1);
					int[] v2point = translatePoint(shard.v2);
					int[] xpoints = {v0point[0], v1point[0], v2point[0]};
					int[] ypoints = {v0point[1], v1point[1], v2point[1]};
					
					g2d.setColor(shard.getShaded(burningSun));
					g2d.fillPolygon(xpoints, ypoints, 3);
				}
				leyLines2.clear();

	}
	

	
	public void rotateShard(LandShard shard){
		Mote[] rotMotes = {new Mote(shard.v0.x, shard.v0.y, shard.v0.z),
						   new Mote(shard.v1.x, shard.v1.y, shard.v1.z),
						   new Mote(shard.v2.x, shard.v2.y, shard.v2.z)};
		
		double[] dists = new double[rotMotes.length];
		
		for(int i = 0; i < rotMotes.length; i++){
			rotMotes[i].rotate(eye.x, eye.y, eye.z, xradPerDist*mousey, 0, zradPerDist*mousex);
			dists[i] = Math.sqrt(Math.pow(rotMotes[i].x, 2) + Math.pow(rotMotes[i].y, 2) + Math.pow(rotMotes[i].z, 2) );
		}
		leyLines2.add(new LandShard(rotMotes[0], rotMotes[1], rotMotes[2], shard.color, Math.min(Math.min(dists[0], dists[1]), dists[2])));
		
	}
	
	public int[] translatePoint(Mote p){
		double xcoord = 0;
		double ycoord = 0;
		
		Mote rp = new Mote(p.x, p.y, p.z);

		//rp.rotate(/*rotxcenter, rotycenter, rotzcenter,*/ xradPerDist*mousey, 0, zradPerDist*mousex);
		
		//rp.rotate(rotxcenter, rotycenter, rotzcenter, Math.toRadians(45), 0, 0);
		
		ycoord = rp.z/((1 + Math.abs(rp.y))/eye.y);
		xcoord = rp.x/((1 + Math.abs(rp.y))/eye.y);
		
	
//		double vtheta = Math.atan2(rp.z - eye.z, rp.y - eye.y);
//		ycoord = Math.tan(vtheta) * eye.y;
//		double htheta = Math.atan2(rp.x - eye.x, rp.y - eye.y);
//		xcoord = Math.tan(htheta) * eye.y;
	
		
		xcoord = xcoord + width/2;
		ycoord = ycoord + height/2;
		
		int[] returnvals = {(int) xcoord, (int) ycoord};
		return returnvals;
	}

	//Records the mouse movement, then moves the cursor
	//back to the center.
	public synchronized void mouseDragged(MouseEvent event) {
		//if(serpentMove && event.getX() == (int)width/2 && event.getY() == (int)height/2)
			//serpentMove = false;
		//else{
			int movex = event.getX() - (int)(width/2);
			int movey = event.getY() - (int)(height/2);
			
			mousex = -movex; 
			System.out.println("Mousex = " + mousex);
			mousey = movey;
			System.out.println("Mousey = " + mousey);
			
			
			
			//cursorSerpent.mouseMove((int)width/2, (int)height/2);
			//serpentMove = true;
		//}
	}

	
	public void mouseMoved(MouseEvent event) {
		//Empty - just here because MouseMotionListener requires it.
	}
	

}
