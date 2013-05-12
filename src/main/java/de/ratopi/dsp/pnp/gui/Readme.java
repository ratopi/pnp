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
package de.ratopi.dsp.pnp.gui;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import de.ratopi.dsp.pnp.PNP;


public class Readme extends JPanel
{
	public Readme()
	{
		super();

		setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );

		final JTextArea textArea = new JTextArea();
		textArea.setLineWrap( true );
		textArea.setWrapStyleWord( true );
		textArea.setEnabled( false );
		textArea.setFont( new Font( null, Font.PLAIN, 16 ) );

		add( textArea );

		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;

		try
		{
			inputStreamReader = new InputStreamReader( PNP.class.getResourceAsStream( "readme.en.txt" ) );
			reader = new BufferedReader( inputStreamReader );

			String line;
			while ( (line = reader.readLine()) != null )
			{
				textArea.append( line + "\n" );
			}
		}
		catch ( IOException e )
		{
			throw new RuntimeException( e );
		}
		finally
		{
			if ( reader != null )
			{
				try
				{
					reader.close();
				}
				catch ( IOException e )
				{
					// ignore this ...
				}
			}

			if ( inputStreamReader != null )
			{
				try
				{
					inputStreamReader.close();
				}
				catch ( IOException e )
				{
					// do nothing ...
				}
			}
		}
	}

}
