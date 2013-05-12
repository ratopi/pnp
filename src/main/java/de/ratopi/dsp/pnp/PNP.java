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
// PNP.java
//
// 4.3.01 rtp.

package de.ratopi.dsp.pnp;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

import de.ratopi.dsp.pnp.gui.PNPPanel;


/**
 * This is the main class for the PNP standalone application.
 */
public class PNP
	extends JFrame
	implements WindowListener
{
	public static final String Version = "build 2001-10-29";

	public static void main( String[] args )
	{
		final PNP frame = new PNP();
		frame.setVisible( true );
	}


	final private PNPPanel pnp;

	public PNP()
	{
		super( "PNP" );

		pnp = new PNPPanel();

		final int inset = 50;
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		setBounds( inset, inset, screenSize.width - 2 * inset, screenSize.height - 2 * inset );

		addWindowListener( this );

		setContentPane( pnp );

		setTitle( pnp.getBundle().getString( "title.pnp" ) + " - " + Version );
	}


	// Schnittstelle: WindowAdapter

	public void windowActivated( WindowEvent e )
	{
	}

	public void windowClosed( WindowEvent e )
	{
	}

	public void windowClosing( WindowEvent e )
	{
		pnp.quit();
	}

	public void windowDeactivated( WindowEvent e )
	{
	}

	public void windowDeiconified( WindowEvent e )
	{
	}

	public void windowIconified( WindowEvent e )
	{
	}

	public void windowOpened( WindowEvent e )
	{
	}

}
