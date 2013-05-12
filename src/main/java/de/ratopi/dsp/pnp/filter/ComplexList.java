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

// ComplexList.java
//
// 10.9.01 rtp.

package de.ratopi.dsp.pnp.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.ratopi.math.Complex;

public class ComplexList
{
	// ==== private members ====

	private List<Complex> list = new ArrayList<Complex>();

	// ==== constructors ====

	public ComplexList()
	{
	}

	// ==== business logic ====

	public void clear()
	{
		list.clear();
	}

	public boolean add( Complex c )
	{
		Complex n = c;

		if ( n.imag() < 0 )
		{
			n = c.conj();
		}

		return list.add( n );
	}

	public boolean remove( Complex c )
	{
		return list.remove( c );
	}

	public boolean move( Complex from, Complex to )
	{
		Complex z = from;

		if ( ! list.contains( z ) )
		{
			z = z.conj();
			if ( ! list.contains( z ) )
			{
				return false;
			}
		}

		if ( remove( z ) )
		{
			if ( ! add( to ) )
			{
				add( z );
				return false;
			}
		}

		return true;
	}

	public Complex findNearest( Complex z )
	{
		Complex top, best = null;
		double dist = Double.MAX_VALUE;
		Iterator<Complex> iterator = getComplexIterator();

		while ( iterator.hasNext() )
		{
			top = iterator.next();

			if ( Complex.abs( Complex.sub( top, z ) ) < dist )
			{
				best = top;
				dist = Complex.abs( Complex.sub( best, z ) );
			}

			top = Complex.conj( top );
			if ( Complex.abs( Complex.sub( top, z ) ) < dist )
			{
				best = top;
				dist = Complex.abs( Complex.sub( best, z ) );
			}
		}

		return best;
	}

	public Iterator<Complex> getComplexIterator()
	{
		return list.iterator();
	}

}
