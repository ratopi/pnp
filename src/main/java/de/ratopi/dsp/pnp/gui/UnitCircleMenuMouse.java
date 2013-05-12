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
// UnitCircleMenuMouse.java
//
// 15.3.01 rtp.
// 10.9.01 rtp.

package de.ratopi.dsp.pnp.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButtonMenuItem;


public class UnitCircleMenuMouse
	extends AbstractMenu
{
	public UnitCircleMenuMouse( final PNPDesktop pnp, final UnitCircle unitCircle )
	{
		super( pnp.getBundle().getString( "menu.mouse" ) );

		final JRadioButtonMenuItem menuMove = new JRadioButtonMenuItem( pnp.getBundle().getString( "menu.mouse.move" ), unitCircle.getMouseMode() == UnitCircle.MOVE );
		final JRadioButtonMenuItem menuSetPole = new JRadioButtonMenuItem( pnp.getBundle().getString( "menu.mouse.setPole" ), unitCircle.getMouseMode() == UnitCircle.SET_POLE );
		final JRadioButtonMenuItem menuSetZero = new JRadioButtonMenuItem( pnp.getBundle().getString( "menu.mouse.setZero" ), unitCircle.getMouseMode() == UnitCircle.SET_ZERO );
		final JRadioButtonMenuItem menuDelete = new JRadioButtonMenuItem( pnp.getBundle().getString( "menu.mouse.delete" ), unitCircle.getMouseMode() == UnitCircle.DELETE );

		final ButtonGroup bg = new ButtonGroup();

		bg.add( menuMove );
		bg.add( menuSetPole );
		bg.add( menuSetZero );
		bg.add( menuDelete );

		addMenuItem(
			menuMove,
			new AbstractAction()
			{
				public void actionPerformed( ActionEvent e )
				{
					unitCircle.setMouseMode( UnitCircle.MOVE );
				}
			}
		);

		addMenuItem(
			menuSetPole,
			new AbstractAction()
			{
				public void actionPerformed( ActionEvent e )
				{
					unitCircle.setMouseMode( UnitCircle.SET_POLE );
				}
			}
		);

		addMenuItem(
			menuSetZero,
			new AbstractAction()
			{
				public void actionPerformed( ActionEvent e )
				{
					unitCircle.setMouseMode( UnitCircle.SET_ZERO );
				}
			}
		);

		addMenuItem(
			menuDelete,
			new AbstractAction()
			{
				public void actionPerformed( ActionEvent e )
				{
					unitCircle.setMouseMode( UnitCircle.DELETE );
				}
			}
		);
	}

}
