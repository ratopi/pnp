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
// PNPMenuFilter.java
//
// 4.3.01 rtp.
// 10.9.01 rtp.

package de.ratopi.dsp.pnp.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;

public class PNPMenuFilter
	extends AbstractMenu
{
	private PNPDesktop pnp;

	/**
	 * Sets the layout and creates the desktop and menu (by calling <code>createMenuBar()</code>
	 *
	 * @param pnpDesktop
	 */
	public PNPMenuFilter( PNPDesktop pnpDesktop )
	{
		super( pnpDesktop.getBundle().getString( "menu.filter" ) );

		pnp = pnpDesktop;

		addMenuItem(
			new JMenuItem( pnp.getBundle().getString( "menu.filter.new" ) ),
			new AbstractAction()
			{
				public void actionPerformed( ActionEvent e )
				{
					pnp.newFilterWindow();
				}
			}
		);

		addMenuItem(
			new JMenuItem( pnp.getBundle().getString( "menu.filter.load" ) ),
			new AbstractAction()
			{
				public void actionPerformed( ActionEvent e )
				{
					pnp.loadFilter();
				}
			}
		);

		addSeparator();

		addMenuItem(
			new JMenuItem( pnp.getBundle().getString( "menu.filter.quit" ) ),
			new AbstractAction()
			{
				public void actionPerformed( ActionEvent e )
				{
					pnp.quit();
				}
			}
		);
	}

}
