/*
  PNP -- Pole and Zero Plot Program

  Copyright (C) 2001  Ralf Thomas Pietsch <ratopi@sourceforge.net>

  This program is free software; you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation; either version 2 of the License, or
  (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program; if not, write to the Free Software
  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
*/
// UnitCircle.java
//
// 15.3.01 rtp.

package de.ratopi.dsp.pnp.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.JPanel;

import de.ratopi.dsp.pnp.filter.Filter;
import de.ratopi.dsp.pnp.filter.FilterListener;
import de.ratopi.math.Complex;

public class UnitCircle
	extends JPanel
	implements MouseListener, MouseMotionListener, FilterListener
{
	public static final int
		MOVE = 0,
		SET_POLE = 1,
		SET_ZERO = 2,
		DELETE = 3;

	private int x0, y0;                // Bildschirmkoordinaten des Koordinatensystemursprungs
	private int e1;                    // Laenge 1 in Bildschirmkoordinaten
	private Filter filter;             // Dargestellter Filter
	private int elementSize = 5;       // Groesse der Pol-Kreuze und Nulstellen-Kreis in Pixel
	private int mouseMode = MOVE;      // Maus-Modus
	private double circleRadius = .25; // relativer Kreisradius
	private Collection<UnitCircleListener> uclistener = new ArrayList<UnitCircleListener>();

	private Complex selected, lastPosition;
	private boolean selectedIsPole;


	public UnitCircle( Filter f )
	{
		super();

		connect( f );

		addMouseListener( this );
		addMouseMotionListener( this );
	}


	public void addUnitCircleListener( UnitCircleListener l )
	{
		uclistener.add( l );
	}

	public void removeUnitCircleListener( UnitCircleListener l )
	{
		uclistener.remove( l );
	}

	private void informUnitCircleListener( Complex c )
	{
		for ( UnitCircleListener anUclistener : uclistener )
		{
			anUclistener.setCoordinates( c );
		}
	}

	private void informUnitCircleListenerExited()
	{
		for ( UnitCircleListener anUclistener : uclistener )
		{
			anUclistener.clearCoordinates();
		}
	}


	public void connect( Filter f )
	{
		disconnect();
		filter = f;
		filter.addFilterListener( this );
	}


	public void disconnect()
	{
		if ( filter != null )
		{
			filter.removeFilterListener( this );
		}
	}


	public void setMouseMode( int newMode )
	{
		if ( newMode >= MOVE && newMode <= DELETE )
		{
			mouseMode = newMode;
		}
	}


	public int getMouseMode()
	{
		return mouseMode;
	}


	public void setElementSize( int e )
	{
		if ( e > 2 )
		{
			elementSize = e;
		}
	}


	public int getElementSize()
	{
		return elementSize;
	}


	public void paint( Graphics g )
	{
		final int w = getWidth();
		final int h = getHeight();

		x0 = w / 2;
		y0 = h / 2;

		if ( h < w )
		{
			e1 = (int) (h * circleRadius);
		}
		else
		{
			e1 = (int) (w * circleRadius);
		}

		g.setColor( Color.white );
		g.fillRect( 0, 0, w - 1, h - 1 );

		g.setColor( Color.black );
		g.drawOval( x0 - e1, y0 - e1, 2 * e1, 2 * e1 );

		g.drawLine( 0, y0, w, y0 );
		g.drawLine( x0, 0, x0, h );

		g.setColor( Color.black );

		Iterator<Complex> enumeration = filter.polesEnumeration();
		while ( enumeration.hasNext() )
		{
			final Complex z = enumeration.next();
			drawPole( g, z );
			drawPole( g, z.conj() );
		}

		enumeration = filter.zerosEnumeration();
		while ( enumeration.hasNext() )
		{
			final Complex z = enumeration.next();
			drawZero( g, z );
			drawZero( g, z.conj() );
		}
	}


	private void drawPole( Graphics g, Complex pole )
	{
		final int x = getScreenX( pole.real() );
		final int y = getScreenY( pole.imag() );

		g.drawLine( x - elementSize, y - elementSize, x + elementSize, y + elementSize );
		g.drawLine( x - elementSize, y + elementSize, x + elementSize, y - elementSize );
	}

	private void drawZero( Graphics g, Complex zero )
	{
		final int x = getScreenX( zero.real() );
		final int y = getScreenY( zero.imag() );

		g.drawOval( x - elementSize, y - elementSize, 2 * elementSize, 2 * elementSize );
		g.drawLine( x, y, x, y );
	}


	// Umrechnung von "realen" Koordinaten in Bildschirmkoordinaten
	private int getScreenX( double x )
	{
		return (int) (x0 + x * e1);
	}

	private int getScreenY( double y )
	{
		return (int) (y0 - y * e1);
	}

	// Umrechnung von Bildschirmkoordinaten in "reale" Koordinate
	private double getRealX( int x )
	{
		return (x - x0) / (double) e1;
	}

	private double getRealY( int y )
	{
		return -(y - y0) / (double) e1;
	}


	private Complex getRealXY( int x, int y )
	{
		return new Complex( getRealX( x ), getRealY( y ) );
	}


	private boolean findSomethingAt( Complex c )
	{
		selected = null;

		double epsilon = getRealX( 4 ) - getRealX( 0 );
		epsilon *= epsilon;
		// entspricht 5 Pixel -- quadrieren wg. abs2()-Funktion!

		final Complex pole = filter.findNearestPole( c );
		final Complex zero = filter.findNearestZero( c );

		final double pdist;
		if ( pole != null )
		{
			pdist = Complex.sub( c, pole ).abs2();
		}
		else
		{
			pdist = Double.MAX_VALUE;
		}

		final double zdist;
		if ( zero != null )
		{
			zdist = Complex.sub( c, zero ).abs2();
		}
		else
		{
			zdist = Double.MAX_VALUE;
		}

		if ( pole != null && pdist < zdist )
		{
			if ( pdist < epsilon )
			{
				selected = pole;
				selectedIsPole = true;
			}
		}
		else if ( zero != null )
		{
			if ( zdist < epsilon )
			{
				selected = zero;
				selectedIsPole = false;
			}
		}

		return selected != null;
	}


	private void deleteSomethingAt( Complex c )
	{
		if ( findSomethingAt( c ) )
		{
			if ( selectedIsPole )
			{
				filter.deletePole( selected );
			}
			else
			{
				filter.deleteZero( selected );
			}
		}
	}


	// Schnittstelle: FilterListener

	public void filterChanged()
	{
		repaint();
	}


	// Schnittstelle: MouseListener

	public void mouseClicked( MouseEvent e )
	{
		int modifier = e.getModifiers();

		/*
		 if (modifier == InputEvent.BUTTON3_MASK  ||  modifier == InputEvent.BUTTON2_MASK)
		 {
		 // popupMenu.show (e.getComponent(), e.getX(), e.getY());
		 }
		 else
	 */
		if ( modifier == InputEvent.BUTTON1_MASK )
		{
			Complex c = getRealXY( e.getX(), e.getY() );
			if ( mouseMode == SET_POLE )
			{
				filter.addPole( c );
			}
			else if ( mouseMode == SET_ZERO )
			{
				filter.addZero( c );
			}
			else if ( mouseMode == DELETE )
			{
				deleteSomethingAt( c );
			}
		}
	}

	public void mouseEntered( MouseEvent e )
	{
	}

	public void mouseExited( MouseEvent e )
	{
		informUnitCircleListenerExited();
	}

	public void mousePressed( MouseEvent e )
	{
		if ( mouseMode == MOVE )
		{
			findSomethingAt( getRealXY( e.getX(), e.getY() ) );
		}
		else
		{
			selected = null;
		}

		lastPosition = selected;
	}

	public void mouseReleased( MouseEvent e )
	{
		if ( selected != null )
		{
			Complex c = getRealXY( e.getX(), e.getY() );

			if ( selectedIsPole )
			{
				filter.movePole( lastPosition, c );
			}
			else
			{
				filter.moveZero( lastPosition, c );
			}
		}
	}


	// Schnittstelle: MouseMotionListener

	public void mouseDragged( MouseEvent e )
	{
		informUnitCircleListener( getRealXY( e.getX(), e.getY() ) );
		if ( lastPosition != null )
		{
			Complex c = getRealXY( e.getX(), e.getY() );
			if ( selectedIsPole )
			{
				if ( filter.movePole( lastPosition, c ) )
				{
					lastPosition = c;
				}
			}
			else
			{
				filter.moveZero( lastPosition, c );
				lastPosition = c;
			}
		}
	}

	public void mouseMoved( MouseEvent e )
	{
		informUnitCircleListener( getRealXY( e.getX(), e.getY() ) );
	}

}
