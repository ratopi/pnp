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
 * PlotAmplitudeLin.java
 *
 *
 * Created: Sun Sep 16 13:45:54 2001
 *
 * @author
 * @version
 */

package de.ratopi.dsp.pnp.gui;

import de.ratopi.dsp.pnp.filter.Filter;

public class PlotAmplitudeLin
	extends PlotPane
{
	public PlotAmplitudeLin( Filter f )
	{
		super( f );
	}

	public String getTitle()
	{
		return "Betragsgang (linear)";
	}

	protected double[] getValues( Filter filter, int width )
	{
		return filter.amplitudeLin( width );
	}

} // PlotAmplitudeLin
