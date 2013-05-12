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
// UnitCircleFrame.java
//
// 18.3.01 rtp.

package de.ratopi.dsp.pnp.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import de.ratopi.dsp.pnp.filter.FilterInfo;
import de.ratopi.math.Complex;

public class UnitCircleFrame
	extends JInternalFrame
	implements InternalFrameListener, UnitCircleListener
{
	private UnitCircle unitCircle;
	private JLabel statusLabel;

	public UnitCircleFrame( FilterInfo filterInfo, PNPDesktop pnpDesktop )
	{
		super( "Einheitskreis", true, true, true, true );

		setSize( 300, 300 );
		setLocation( 10, 10 );
		setVisible( true );
		moveToFront();
		setDefaultCloseOperation( JInternalFrame.DISPOSE_ON_CLOSE );
		addInternalFrameListener( this );

		final Container container = getContentPane();
		container.setLayout( new BorderLayout() );

		unitCircle = new UnitCircle( filterInfo.getFilter() );
		container.add( unitCircle, BorderLayout.CENTER );

		statusLabel = new JLabel( " " );
		container.add( statusLabel, BorderLayout.SOUTH );

		unitCircle.addUnitCircleListener( this );

		setJMenuBar( new UnitCircleMenu( pnpDesktop, unitCircle, filterInfo ) );
	}


	// Schnittstelle: UnitCircleListener

	public void setCoordinates( Complex c )
	{
		if ( c != null )
		{
			statusLabel.setText( c.toString() );
		}
		else
		{
			statusLabel.setText( "???" );
		}
	}

	public void clearCoordinates()
	{
		statusLabel.setText( " " );
	}

	// Schnittstelle: InternalFrameListener

	public void internalFrameActivated( InternalFrameEvent e )
	{
		System.err.println( "UCF activated" );
	}

	public void internalFrameClosed( InternalFrameEvent e )
	{
		System.err.println( "UCF closed" );
		unitCircle.disconnect();
	}

	public void internalFrameClosing( InternalFrameEvent e )
	{
		System.err.println( "UCF closing" );
	}

	public void internalFrameDeactivated( InternalFrameEvent e )
	{
		System.err.println( "UCF deactivated" );
	}

	public void internalFrameDeiconified( InternalFrameEvent e )
	{
	}

	public void internalFrameIconified( InternalFrameEvent e )
	{
	}

	public void internalFrameOpened( InternalFrameEvent e )
	{
		System.err.println( "UCF opened" );
	}

}

