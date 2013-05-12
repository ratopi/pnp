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
// UnitCircleMenuWindow.java
//
// 4.3.01 rtp.
// 10.9.01 rtp.

package de.ratopi.dsp.pnp.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;

import de.ratopi.dsp.pnp.filter.FilterInfo;

public class UnitCircleMenuWindow
	extends AbstractMenu
{
	public UnitCircleMenuWindow( final PNPDesktop pnp, final FilterInfo filterInfo )
	{
		super( pnp.getBundle().getString( "menu.window" ) );

		addMenuItem( new JMenuItem( pnp.getBundle().getString( "menu.window.unitCircle" ) ),
			new AbstractAction()
			{
				public void actionPerformed( ActionEvent e )
				{
					pnp.showUnitCircle( filterInfo );
				}
			}
		);

		addMenuItem( new JMenuItem( pnp.getBundle().getString( "menu.window.amplitudeLin" ) ),
			new AbstractAction()
			{
				public void actionPerformed( ActionEvent e )
				{
					pnp.showAmplitudeLin( filterInfo );
				}
			}
		);

		addMenuItem( new JMenuItem( pnp.getBundle().getString( "menu.window.amplitudeLog" ) ),
			new AbstractAction()
			{
				public void actionPerformed( ActionEvent e )
				{
					pnp.showAmplitudeLog( filterInfo );
				}
			}
		);

		addMenuItem( new JMenuItem( pnp.getBundle().getString( "menu.window.phase" ) ),
			new AbstractAction()
			{
				public void actionPerformed( ActionEvent e )
				{
					pnp.showPhase( filterInfo );
				}
			}
		);

		addMenuItem( new JMenuItem( pnp.getBundle().getString( "menu.window.groupdelay" ) ),
			new AbstractAction()
			{
				public void actionPerformed( ActionEvent e )
				{
					pnp.showGroupdelay( filterInfo );
				}
			}
		);

		addMenuItem( new JMenuItem( pnp.getBundle().getString( "menu.window.impulseresponse" ) ),
			new AbstractAction()
			{
				public void actionPerformed( ActionEvent e )
				{
					pnp.showImpulseresponse( filterInfo );
				}
			}
		);

		addSeparator();

		addMenuItem( new JMenuItem( pnp.getBundle().getString( "menu.preferences" ) ),
			new AbstractAction()
			{
				public void actionPerformed( ActionEvent e )
				{
				}
			}
		);
	}

}

