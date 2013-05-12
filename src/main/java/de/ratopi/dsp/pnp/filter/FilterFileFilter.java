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
// FilterFileFilter.java
//
// 10.9.01 rtp.

package de.ratopi.dsp.pnp.filter;

import java.io.File;
import javax.swing.filechooser.FileFilter;

import de.ratopi.dsp.pnp.tools.Tools;

public class FilterFileFilter extends FileFilter
{
	private static final String FILTER_FILE_EXTENSION = "filter";

	public boolean accept( File file )
	{
		if ( file.isDirectory() )
		{
			return true;
		}

		final String extension = Tools.getExtension( file );

		return extension != null && extension.equals( FILTER_FILE_EXTENSION );
	}


	public String getDescription()
	{
		return "PNP/Digitalfilter";
	}
}

