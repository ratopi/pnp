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
// About.java
//
// 18.03.01 rtp.

package de.ratopi.dsp.pnp.gui;

import java.awt.Color;
import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import de.ratopi.dsp.pnp.PNP;

public class About
	extends JInternalFrame
{
	public About()
	{
		super( "About PNP", true, true, true, true );

		setLocation( 10, 10 );
		setSize( 300, 200 );
		setVisible( true );

		final Container cont = getContentPane();

		cont.setBackground( Color.lightGray );
		cont.setLayout( new BoxLayout( cont, BoxLayout.Y_AXIS ) );

		final String[] text = {
			"PNP for Java " + PNP.Version,
			"--",
			"Copyright (C) 2001 by Ralf Thomas Pietsch",
			"<pietsch@iap.uni-frankfurt.de>",
			"--",
			"This program is free software; you can redistribute it and/or modify",
			"it under the terms of the GNU General Public License as published by",
			"the Free Software Foundation; either version 2 of the License, or",
			"(at your option) any later version.",
			"",
			"This program is distributed in the hope that it will be useful,",
			"but WITHOUT ANY WARRANTY; without even the implied warranty of",
			"MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the",
			"GNU General Public License for more details.",
			"",
			"You should have received a copy of the GNU General Public License",
			"along with this program; if not, write to the Free Software",
			"Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA." };

		for ( String aText : text )
		{
			cont.add( new JLabel( aText ) );
		}
	}
}

