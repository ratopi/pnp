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
// Filter.java
//
// Version 1.0.0
//
// 9.10.97 rtp. -- C++ Version
// 4.3.01 rtp. -- Java Version
//
// rtp. : Ralf Thomas Pietsch
//
// ToDo:
// - load muss auch alte Filter lesen koennen.
//
// 31.8.01 rtp. -- read() & write ()

package de.ratopi.dsp.pnp.filter;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import de.ratopi.math.Complex;

public class Filter
{
	// ==== private attributes ====

	private String name;
	// * vorgesehen fuer eine Bemerkung in der Filterdatei,
	// * die den Filter genauer beschreibt.

	// ==== private members ====

	private final ComplexList poles = new PolesList();
	private final ComplexList zeros = new ZerosList();
	private final Collection<FilterListener> filterListeners = new HashSet<FilterListener>();

	// ==== simple setters and getters ====

	public String getName()
	{
		return name;
	}

	// ==== complex setters and getters ====

	public void setName( String name )
	{
		this.name = name;
		filterIsChanged();
	}

	// ==== business logic ====

	public Iterator<Complex> polesEnumeration()
	{
		return poles.getComplexIterator();
	}


	public Iterator<Complex> zerosEnumeration()
	{
		return zeros.getComplexIterator();
	}


	public boolean addPole( Complex newPole )
	{
		final boolean rtn = poles.add( newPole );
		filterIsChanged();

		return rtn;
	}


	public boolean addZero( Complex newZero )
	{
		final boolean rtn = zeros.add( newZero );
		filterIsChanged();

		return rtn;
	}


	public boolean deletePole( Complex z )
	{
		final boolean rtn = poles.remove( z );
		filterIsChanged();

		return rtn;
	}


	public boolean deleteZero( Complex z )
	{
		final boolean rtn = zeros.remove( z );
		filterIsChanged();

		return rtn;
	}


	public boolean movePole( Complex a, Complex b )
	{
		if ( poles.move( a, b ) )
		{
			filterIsChanged();
			return true;
		}

		return false;
	}


	public boolean moveZero( Complex a, Complex b )
	{
		if ( zeros.move( a, b ) )
		{
			filterIsChanged();
			return true;
		}

		return false;
	}


	public Complex findNearestPole( Complex z )
	{
		return poles.findNearest( z );
	}


	public Complex findNearestZero( Complex z )
	{
		return zeros.findNearest( z );
	}


	// Ein- und Ausgabe:

	public void read( Reader r )
		throws IOException
	{
		int n;
		char cbuf[] = new char[1024];
		StringBuffer str = new StringBuffer();

		while ( (n = r.read( cbuf )) >= 0 )
		{
			str.append( cbuf, 0, n );
		}

		valueOf( str.toString() );
	}


	public void write( Writer w )
		throws IOException
	{
		w.write( toString() );
	}


	public String toString()
	{
		final StringBuilder stringBuilder = new StringBuilder( "# PNP 1\n" );

		final Iterator<Complex> polesIterator = poles.getComplexIterator();
		while ( polesIterator.hasNext() )
		{
			stringBuilder.append( "p " ).append( polesIterator.next() ).append( "\n" );
		}

		final Iterator<Complex> zerosIterator = zeros.getComplexIterator();
		while ( zerosIterator.hasNext() )
		{
			stringBuilder.append( "z " ).append( zerosIterator.next() ).append( "\n" );
		}

		return stringBuilder.toString();
	}


	public void valueOf( String str )
	{
		final StringTokenizer stl = new StringTokenizer( str, "\n" );

		while ( stl.hasMoreTokens() )
		{
			final String line = stl.nextToken();
			if ( ! line.startsWith( "#" ) )
			{
				final StringTokenizer stx = new StringTokenizer( line, " " );
				try
				{
					final String pz = stx.nextToken();
					final Complex c = Complex.valueOf( stx.nextToken() );
					if ( pz.equals( "p" ) )
					{
						addPole( c );
					}
					else if ( pz.equals( "z" ) )
					{
						addZero( c );
					}
				}
				catch ( NoSuchElementException excp )
				{
					System.err.println( "Error when parsing line '" + line + "'" );
					// throw new FilterException ("Error when parsing line '"+line+"'");
				}
			}
		}
	}


	// Die DSP-Routinen:

	public double[] amplitudeLin( int len )
	{
		final double buf[] = new double[len];

		for ( int i = 0; i < len; i++ )
		{
			final Complex e = Complex.exp( Complex.mult( Complex.I, (Math.PI * i) / len ) );

			double a = 1;
			final Iterator<Complex> zerosIterator = zeros.getComplexIterator();
			while ( zerosIterator.hasNext() )
			{
				final Complex z = zerosIterator.next();
				a *= Complex.abs( Complex.sub( e, z ) );
				if ( z.imag() != 0. )
				{
					a *= Complex.abs( Complex.sub( e, z.conj() ) );
				}
			}

			double b = 1;
			final Iterator<Complex> polesIterator = poles.getComplexIterator();
			while ( polesIterator.hasNext() )
			{
				final Complex p = polesIterator.next();
				b *= Complex.abs( Complex.sub( e, p ) );
				if ( p.imag() != 0. )
				{
					b *= Complex.abs( Complex.sub( e, p.conj() ) );
				}
			}

			buf[ i ] = a / b;
		}

		return buf;
	}


	public double[] amplitudeLog( int len )
	{
		final double log10 = 1. / Math.log( 10 );

		final double[] buf = amplitudeLin( len );

		for ( int i = 0; i < len; i++ )
		{
			if ( buf[ i ] > 0. )
			{
				buf[ i ] = 20. * Math.log( buf[ i ] ) * log10;
			}
		}

		return buf;
	}


	public double[] phase( int len )
	{
		final double buf[] = new double[len];

		for ( int i = 0; i < len; i++ )
		{
			final Complex e = Complex.exp( Complex.mult( Complex.I, (Math.PI * i) / len ) );

			double a = 0;
			final Iterator<Complex> zerosIterator = zeros.getComplexIterator();
			while ( zerosIterator.hasNext() )
			{
				final Complex x = zerosIterator.next();
				a += Complex.arg( Complex.sub( e, x ) );
				if ( x.imag() != 0. )
				{
					a += Complex.arg( Complex.sub( e, x.conj() ) );
				}
			}

			double b = 0;
			final Iterator<Complex> polesIterator = poles.getComplexIterator();
			while ( polesIterator.hasNext() )
			{
				final Complex x = polesIterator.next();
				b += Complex.arg( Complex.sub( e, x ) );
				if ( x.imag() != 0. )
				{
					b += Complex.arg( Complex.sub( e, x.conj() ) );
				}
			}

			buf[ i ] = b - a;
		}

		// Korrektur bzw. Glaettung der Kurve:

		final double addit = 2 * Math.PI;
		final double dist = addit * .8;

		double add = 0;
		for ( int i = 1; i < len; i++ )
		{
			buf[ i ] += add;
			double d = buf[ i ] - buf[ i - 1 ];
			if ( d > dist )
			{
				add -= addit;
				buf[ i ] -= addit;
			}
			if ( d < -dist )
			{
				add += addit;
				buf[ i ] += addit;
			}
		}

		/*
		 for (i=1; i<len; i++)
		 {
		 d = buf[i] - buf[i-1];
		 modf (d/(2*M_PI)*1.1,&g); @@@ Funktion modf in Java unbekannt!!!
		 if (g != 0.)
		 for (j=i; j<len; j++)
		 buf[j] -= g*(2*M_PI);
		 }
	 */

		return buf;
	}


	public double[] groupdelay( int len )
	{
		final double buf[] = new double[len];

		for ( int i = 0; i < len; i++ )
		{
			final double phi = (Math.PI * i) / len;
			double a = 0;
			final Iterator<Complex> zerosIterator = zeros.getComplexIterator();
			while ( zerosIterator.hasNext() )
			{
				final Complex x = zerosIterator.next();
				final double r = x.abs();
				final double c = Math.cos( phi - x.arg() );
				a += (1 - r * c) / (1 + r * r - 2 * r * c);
			}

			double b = 0;
			final Iterator<Complex> polesIterator = poles.getComplexIterator();
			while ( polesIterator.hasNext() )
			{
				final Complex x = polesIterator.next();
				final double r = x.abs();
				final double c = Math.cos( phi - x.arg() );
				b += (1 - r * c) / (1 + r * r - 2 * r * c);
			}
			buf[ i ] = b - a;
		}

		return buf;
	}


	public double[] impulseresponse( int len )
	{
		final double buf[] = new double[len];
		final Complex cbuf[] = new Complex[len];

		cbuf[ 0 ] = new Complex( 1, 0 );
		for ( int i = 1; i < cbuf.length; i++ )
		{
			cbuf[ i ] = new Complex( 0, 0 );
		}

		final Iterator<Complex> zerosIterator = zeros.getComplexIterator();
		while ( zerosIterator.hasNext() )
		{
			Complex z = zerosIterator.next();

			for ( int j = cbuf.length - 1; j > 0; j-- )
			{
				cbuf[ j ] = cbuf[ j ].add( Complex.mult( z, cbuf[ j - 1 ] ) );
			}

			if ( z.imag() != 0 )
			{
				z = z.conj();
				for ( int j = len - 1; j > 0; j-- )
				{
					cbuf[ j ] = cbuf[ j ].add( Complex.mult( z, cbuf[ j - 1 ] ) );
				}
			}
		}

		final Iterator<Complex> polesIterator = poles.getComplexIterator();
		while ( polesIterator.hasNext() )
		{
			Complex z = polesIterator.next();

			for ( int j = 1; j < cbuf.length; j++ )
			{
				cbuf[ j ] = cbuf[ j ].add( Complex.mult( z, cbuf[ j - 1 ] ) );
			}

			if ( z.imag() != 0 )
			{
				z = z.conj();
				for ( int j = 1; j < len; j++ )
				{
					cbuf[ j ] = cbuf[ j ].add( Complex.mult( z, cbuf[ j - 1 ] ) );
				}
			}
		}

		for ( int j = 0; j < len; j++ )
		{
			buf[ j ] = cbuf[ j ].real();
		}

		return buf;
	}


/*
int Filter::loadOldFormat (FILE *f)
	{
	int n;
	struct Gadget
		{
		short x, y, dx, dy;
		short type;	// 5: Pol, 6: Nullstelle
		float b, w;
		short num;
		Gadget *nextgadget;
		void *ptr;
		} a;

	while (!feof(f))
		{
		n = fread (&a, sizeof(Gadget), 1, f);
		if (n == 1 && (a.w <= M_PI || a.ptr == NULL))
			{
			if (a.type == 5)
				{
				addPole(complex(a.b*cos(a.w),a.b*sin(a.w)));
				if (a.ptr) addPole(complex(a.b*cos(a.w),-a.b*sin(a.w)));
				}
			else if (a.type == 6)
				{
				addZero(complex(a.b*cos(a.w),a.b*sin(a.w)));
				if (a.ptr) addZero(complex(a.b*cos(a.w),-a.b*sin(a.w)));
				}
			}
		}

	return 0;
	}
*/


	// FilterListener verwalten und aufrufen

	public boolean addFilterListener( FilterListener f )
	{
		return filterListeners.add( f );
	}


	public boolean removeFilterListener( FilterListener f )
	{
		return filterListeners.remove( f );
	}


	private void filterIsChanged()
	{
		for ( FilterListener aFilterListener : filterListeners )
		{
			aFilterListener.filterChanged();
		}
	}

}

