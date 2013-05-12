/*
  Complex -- an immutable class for the representation of complex numbers.

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
// Complex.java
//
// 4.3.01 rtp.
// 22.9.01 rtp. - made the class imutable

package de.ratopi.math;

/**
 * This class is a representation of (imutable) complex numbers.
 */
public class Complex
	implements java.io.Serializable
{
	// ==== static members ====

	/**
	 * The imaginary unit <i>i</i>.
	 * <i>i</i> is the resolution of the equation: <i>i</i><sup>2</sup> = -1.
	 * (BTW: <i>i</i> is <b>not</b> the square root of -1!)
	 */
	public static final Complex I = new Complex( 0, 1 );

	/**
	 * Returns the absolute value of c: |c|.
	 */
	public static double abs( Complex c )
	{
		return Math.sqrt( abs2( c ) );
	}

	/**
	 * Returns the square of the absolute value of c.
	 * It is calculate by the equation: <i>real</i><sup>2</sup> + <i>imag</i><sup>2</sup>.
	 * <p/>
	 * This routine is a little faster than abs(), because of the missing
	 * square root.
	 * @param c A complex number.
	 * @return The square of the absolute value of c.
	 */
	public static double abs2( Complex c )
	{
		return c.real * c.real + c.imag * c.imag;
	}

	public static double arg( Complex c )
	{
		return Math.atan2( c.imag, c.real );
	}

	public static Complex exp( Complex c )
	{
		double r = Math.exp( c.real );

		return new Complex( r * Math.cos( c.imag ), r * Math.sin( c.imag ) );
	}

	public static Complex conj( Complex c )
	{
		return new Complex( c.real, -c.imag );
	}

	public static double real( Complex c )
	{
		return c.real;
	}

	public static double imag( Complex c )
	{
		return c.imag;
	}

	public static Complex add( Complex a, Complex b )
	{
		return new Complex( a.real + b.real, a.imag + b.imag );
	}

	public static Complex sub( Complex a, Complex b )
	{
		return new Complex( a.real - b.real, a.imag - b.imag );
	}

	public static Complex mult( Complex a, Complex b )
	{
		return new Complex( a.real * b.real - a.imag * b.imag, a.real * b.imag + a.imag * b.real );
	}

	public static Complex mult( Complex a, double d )
	{
		return new Complex( a.real * d, a.imag * d );
	}

	public static Complex mult( double d, Complex a )
	{
		return mult( a, d );
	}

	public static Complex div( Complex a, Complex b )
	{
		double x = 1 / (b.real * b.real + b.imag * b.imag);

		return new Complex( (a.real * b.real + a.imag * b.imag) * x, (a.imag * b.real - a.real * b.imag) * x );
	}

	/**
	 * Gives back the complex number which is represented by a string in the
	 * form '<i>a</i>+<i>b</i>i', whereby <i>a</i> and <i>b</i> are legal <code>double</code>
	 * representations.
	 */
	public static Complex valueOf( String str )
		throws NumberFormatException
	{
		int i = str.indexOf( "+", 1 );
		if ( i == -1 )
		{
			i = str.indexOf( "-", 1 );
		}

		final int j = str.indexOf( "i" );

		if ( i == -1 || j == -1 )
		{
			throw new NumberFormatException( str );
		}

		final double real = Double.valueOf( str.substring( 0, i ) );
		final double imag = Double.valueOf( str.substring( i, j ) );

		return new Complex( real, imag );
	}

	// ==== private attributes ====

	private double real, imag;

	// ==== constructors ====

	/**
	 * Construct a new complex number with the value 0+0i.
	 */
	public Complex()
	{
		set( 0, 0 );
	}

	/**
	 * Construct a new complex number with a real value and an imaginary part equals 0.
	 * @param d The value of the real value.
	 */
	public Complex( double d )
	{
		set( d, 0 );
	}

	/**
	 * Construct a new complex number with the value a+b<i>i</i>.
	 * @param a The real value.
	 * @param b The imaginary value.
	 */
	public Complex( double a, double b )
	{
		set( a, b );
	}

	/**
	 * Construct a new complex number with the same value as c.
	 * @param c A complex number.
	 */
	public Complex( Complex c )
	{
		set( c );
	}

	// ==== business logic ====

	/**
	 * Returns the absolute value.
	 * @return The absolute value of this complex number.
	 */
	public double abs()
	{
		return abs( this );
	}

	public double abs2()
	{
		return abs2( this );
	}

	public double arg()
	{
		return arg( this );
	}

	public Complex conj()
	{
		return conj( this );
	}

	public double imag()
	{
		return imag;
	}

	public double real()
	{
		return real;
	}

	public Complex positiveImag()
	{
		if ( imag() < 0 )
		{
			return conj( this );
		}

		return new Complex( this );
	}

	public Complex add( Complex a )
	{
		return add( this, a );
	}

	// ==== private methods ====

	/**
	 * Set the real (r) and imaginary (i) part of the complex number.
	 */
	private void set( double r, double i )
	{
		real = r;
		imag = i;
	}

	/**
	 * Set the value to the value of c.
	 */
	private void set( Complex c )
	{
		set( c.real, c.imag );
	}

	// ==== methods from java.lang.Object ====

	/**
	 * Tests if the complex number's value is equal to that of obj.
	 * </p><p>
	 * Gives back true, if this complex has the same value as that of obj.
	 * Gives back false, if obj is not of type <code>Complex</code> or it has
	 * not the same value.
	 */
	@Override
	public boolean equals( Object o )
	{
		if ( this == o ) return true;
		if ( o == null || getClass() != o.getClass() ) return false;

		Complex complex = (Complex) o;

		if ( Double.compare( complex.imag, imag ) != 0 ) return false;
		if ( Double.compare( complex.real, real ) != 0 ) return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		int result;
		long temp;

		temp = real != +0.0d ? Double.doubleToLongBits( real ) : 0L;
		result = (int) (temp ^ (temp >>> 32));
		temp = imag != +0.0d ? Double.doubleToLongBits( imag ) : 0L;
		result = 31 * result + (int) (temp ^ (temp >>> 32));

		return result;
	}

	/**
	 * Gives back a string representation of the value of the complex number
	 * in the form '<a>+<b>i'
	 */
	public String toString()
	{
		final String str;

		if ( imag < 0 )
		{
			str = Double.toString( real ) + Double.toString( imag ) + "i";
		}
		else
		{
			str = Double.toString( real ) + "+" + Double.toString( imag ) + "i";
		}

		return str;
	}

}
