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
 * PolesList.java
 *
 *
 * Created: Sun Oct 28 12:32:56 2001
 *
 * @author
 * @version
 */

package de.ratopi.dsp.pnp.filter;

import de.ratopi.math.Complex;

public class PolesList
	extends ComplexList
{
	public PolesList()
	{
		super();
	}


	public boolean add( Complex c )
	{
		if ( c.abs2() >= 1 )
		{
			return false;
		}

		Complex n = c;

		if ( n.imag() < 0 )
		{
			n = c.conj();
		}

		return super.add( n );
	}

} // PolesList
