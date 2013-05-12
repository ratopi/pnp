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
// PlotPaneFrame.java
//
// 19.03.01 rtp.

package de.ratopi.dsp.pnp.gui;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

public class PlotPaneFrame
	extends JInternalFrame
	implements InternalFrameListener
{
	private PlotPane plotPane;

	public PlotPaneFrame( PlotPane plotPane )
	{
		super( plotPane.getTitle(), true, true, true, true );

		this.plotPane = plotPane;
		setContentPane( plotPane );

		setLocation( 10, 10 );
		setSize( 300, 200 );
		setVisible( true );
		moveToFront();
		setDefaultCloseOperation( JInternalFrame.DISPOSE_ON_CLOSE );
		addInternalFrameListener( this );
	}


	// Schnittstelle: InternalFrameListener

	public void internalFrameActivated( InternalFrameEvent e )
	{
	}

	public void internalFrameClosed( InternalFrameEvent e )
	{
		plotPane.disconnect();
	}

	public void internalFrameClosing( InternalFrameEvent e )
	{
	}

	public void internalFrameDeactivated( InternalFrameEvent e )
	{
	}

	public void internalFrameDeiconified( InternalFrameEvent e )
	{
	}

	public void internalFrameIconified( InternalFrameEvent e )
	{
	}

	public void internalFrameOpened( InternalFrameEvent e )
	{
	}

}
