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
// PNPMenuHelp.java
//
// 4.3.01 rtp.
// 10.9.01 rtp.

package de.ratopi.dsp.pnp.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;

public class PNPMenuHelp
	extends AbstractMenu
{
	public PNPMenuHelp( final PNPDesktop pnp )
	{
		super( pnp.getBundle().getString( "menu.help" ) );

		addMenuItem(
			new JMenuItem( pnp.getBundle().getString( "menu.help.about" ) ),
			new AbstractAction()
			{
				public void actionPerformed( ActionEvent e )
				{
					pnp.showHelpAbout();
				}
			}
		);
	}

}
