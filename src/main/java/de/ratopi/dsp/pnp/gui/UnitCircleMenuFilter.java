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
// UnitCircleMenuFilter.java
//
// 15.3.01 rtp.
// 10.9.01 rtp.

package de.ratopi.dsp.pnp.gui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import de.ratopi.dsp.pnp.filter.FilterFileFilter;
import de.ratopi.dsp.pnp.filter.FilterInfo;

public class UnitCircleMenuFilter
	extends AbstractMenu
{
	private FilterInfo filterInfo;

	public UnitCircleMenuFilter( PNPDesktop pnpDesktop, FilterInfo fi )
	{
		super( pnpDesktop.getBundle().getString( "menu.filter" ) );

		filterInfo = fi;

		addMenuItem(
			new JMenuItem( pnpDesktop.getBundle().getString( "menu.filter.add" ) ),
			new AbstractAction()
			{
				public void actionPerformed( ActionEvent e )
				{
				}
			}
		);

		addMenuItem(
			new JMenuItem( pnpDesktop.getBundle().getString( "menu.filter.save" ) ),
			new AbstractAction()
			{
				public void actionPerformed( ActionEvent e )
				{
					filterSave( false );
				}
			}
		);

		addMenuItem(
			new JMenuItem( pnpDesktop.getBundle().getString( "menu.filter.saveAs" ) ),
			new AbstractAction()
			{
				public void actionPerformed( ActionEvent e )
				{
					filterSave( true );
				}
			}
		);

		addMenuItem(
			new JMenuItem( pnpDesktop.getBundle().getString( "menu.filter.remove" ) ),
			new AbstractAction()
			{
				public void actionPerformed( ActionEvent e )
				{
				}
			}
		);
	}


	/**
	 * Menu point Filter:Save
	 * @param saveAs
	 */
	private void filterSave( boolean saveAs )
	{
		File file = filterInfo.getFile();

		if ( saveAs || file == null )
		{
			final JFileChooser fc = new JFileChooser();
			fc.addChoosableFileFilter( new FilterFileFilter() );
			final int rtn = fc.showSaveDialog( this );
			if ( rtn == JFileChooser.APPROVE_OPTION )
			{
				file = fc.getSelectedFile();
			}
			else
			{
				return;
			}
		}

		Writer w = null;
		try
		{
			w = new FileWriter( file );
			filterInfo.getFilter().write( w );
			filterInfo.setFile( file );
		}
		catch ( java.io.IOException e )
		{
			System.err.println( "Can not save file" );
			e.printStackTrace();
			// TODO: Show dialog here! (2010-04-16, rtp.)
		}
		finally
		{
			if ( w != null )
			{
				try
				{
					w.close();
				}
				catch ( IOException e )
				{
					// ignore this ...
				}
			}
		}
	}

}
