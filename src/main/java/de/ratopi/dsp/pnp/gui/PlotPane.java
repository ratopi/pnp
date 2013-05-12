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
// PlotPane.java
//
// 18.03.01 rtp.

package de.ratopi.dsp.pnp.gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

import de.ratopi.dsp.pnp.filter.Filter;
import de.ratopi.dsp.pnp.filter.FilterListener;

public abstract class PlotPane
	extends JPanel
	implements FilterListener
{
	//public static final String[] plotNames = {"Betragsgang (linear)", "Betragsgang (dB)", "Phasengang", "Gruppenlaufzeit", "Impulsantwort"};

	/* public static final int
				 AMPLITUDE_LIN = 0,
				 AMPLITUDE_LOG = 1,
				 PHASE = 2,
				 GROUPDELAY = 3,
				 IMPULSERESPONSE = 4;
			*/
	private Filter filter;
	private double[] buffer;
	private double max = 0, min = 0;
	private int oldWidth = -1;


	public PlotPane( Filter f )
	{
		super();

		if ( f == null )
		{
			System.err.println( "filter is null in PlotPane()" );
			System.exit( 1 );
		}

		connect( f );
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
			System.err.println( "PlotPane.disconnect()" );
		}
	}


	public abstract String getTitle();

	protected abstract double[] getValues( Filter filter, int width );


	private void calc()
	{
		int i, w;

		w = getWidth();
		buffer = getValues( filter, w );
		min = 0;
		max = buffer[ 0 ];
		for ( i = 1; i < w; i++ )
		{
			if ( buffer[ i ] < min )
			{
				min = buffer[ i ];
			}
			else if ( buffer[ i ] > max )
			{
				max = buffer[ i ];
			}
		}

		if ( max < 1 )
		{
			max = 1;
		}
	}


	public void paint( Graphics g )
	{
		int w, h, i;
		int xp[], yp[];
		double a, b;

		w = getWidth();
		h = getHeight();

		g.setColor( Color.white );
		g.fillRect( 0, 0, w - 1, h - 1 );

		g.setColor( Color.black );
		for ( i = 1; i < 4; i++ )
		{
			g.drawLine( (w * i) / 4, 0, (w * i) / 4, h - 1 );
		}

		if ( w != oldWidth )
		{
			calc();
		}

		a = (h - 1) / (min - max);
		b = -a * max;

		g.drawLine( 0, (int) b, w - 1, (int) b );

		xp = new int[w];
		yp = new int[w];

		for ( i = 0; i < w; i++ )
		{
			xp[ i ] = i;
			yp[ i ] = (int) (buffer[ i ] * a + b);
		}

		g.drawPolyline( xp, yp, w );

		oldWidth = w;
	}

	/*
	protected void finish ()
			{
		filter.removeFilterListener (this);
			}
			*/

// Schnittstelle: FilterListener

	public void filterChanged()
	{
		calc();
		repaint();
	}


	protected void finalize()
		throws Throwable
	{
		disconnect();
		System.out.println( "PlotPane.finalize() : " + this );
		super.finalize();
	}
}

