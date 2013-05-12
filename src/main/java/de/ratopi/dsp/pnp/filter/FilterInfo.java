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

/**
 * FilterInfo.java
 *
 *
 * Created: Sun Sep 16 14:37:44 2001
 *
 * @author rtp.
 * @version
 */

package de.ratopi.dsp.pnp.filter;

import java.io.File;

public class FilterInfo
	implements FilterListener
{
	private Filter filter;
	private File file;

	public FilterInfo()
	{
		this( new Filter() );
	}

	public FilterInfo( Filter f )
	{
		filter = f;
		filter.addFilterListener( this );
	}

	public FilterInfo( Filter f, File n )
	{
		this( f );
		setFile( n );
	}

	public Filter getFilter()
	{
		return filter;
	}

	public File getFile()
	{
		return file;
	}

	public void setFile( File n )
	{
		file = n;
		filter.setName( file.toString() );
	}


	// Schnittstelle: FilterListener
	public void filterChanged()
	{
		// saved = false; // beisst sich mit filter.setName in Methode setFile!!!
	}

} // FilterInfo
