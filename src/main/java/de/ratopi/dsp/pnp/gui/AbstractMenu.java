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
// AbstractMenu.java
//
// 10.9.01 rtp.

package de.ratopi.dsp.pnp.gui;

import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public abstract class AbstractMenu
	extends JMenu
{
	public AbstractMenu( String title )
	{
		super( title );
	}

	protected void addMenuItem( JMenuItem item )
	{
		add( item );
		// item.addActionListener (this);
	}

	protected void addMenuItem( JMenuItem item, ActionListener al )
	{
		add( item );
		item.addActionListener( al );
	}
}
