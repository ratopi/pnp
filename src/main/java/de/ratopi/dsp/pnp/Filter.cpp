// filter.cpp
//
// 9.10.97 rtp.
//
// Version 1.0.0
//
// ToDo:
// - load mu˜ auch alte Filter lesen k÷nnen.

#include "filter.h"

#include <math.h>
#include <stdio.h>
#include <string.h>

#define CORRZ(a) (imag(a)<0 ? conj(a) : a)

Filter::Filter () :
	numOfPoles (0),
	numOfZeros (0),
	filename (0),
	savedFlag (1)
	{
	}


Filter::Filter (Filter &f) :
	filename (0),
	savedFlag (0)
	{
	int i;

	numOfPoles = f.numOfPoles;
	numOfZeros = f.numOfZeros;

	for (i=0; i<numOfPoles; i++)
		poles[i] = f.poles[i];

	for (i=0; i<numOfZeros; i++)
		zeros[i] = f.zeros[i];
	}


Filter::~Filter ()
	{
	clear ();
	}


void Filter::clear ()
	{
	numOfPoles = 0;
	numOfZeros = 0;
	savedFlag = 1;
	if (filename)
		{
		delete filename;
		filename = 0;
		}
	}


char *Filter::getFilename ()
	{
	return filename;
	}


int Filter::isSaved ()
	{
	return savedFlag;
	}


int Filter::addPole (complex &newPole)
	{
	if (numOfPoles >= 100)
		return -1;

	if (abs(newPole) >= 1)
		return -2;

	if (imag(newPole) < 0)
		poles[numOfPoles] = conj(newPole);
	else
		poles[numOfPoles] = newPole;

	numOfPoles++;
	savedFlag = 0;

	return 0;
	}


int Filter::addZero (complex &newZero)
	{
	if (numOfZeros >= 100)
		return -1;

	if (imag(newZero) < 0)
		zeros[numOfZeros] = conj(newZero);
	else
		zeros[numOfZeros] = newZero;

	numOfZeros++;
	savedFlag = 0;

	return 0;
	}


int Filter::deletePole (complex &z)
	{
	int i, j;
	complex c;

	c = CORRZ(z);

	for (i=0; i<numOfPoles; i++)
		{
		if (poles[i] == c)
			{
			for (j=i+1; j<numOfPoles; j++)
				poles[j-1] = poles[j];
			numOfPoles--;
			savedFlag = 0;
			return 0;
			}
		}

	return -1;
	}


int Filter::deleteZero (complex &z)
	{
	int i, j;
	complex c;

	c = CORRZ(z);

	for (i=0; i<numOfZeros; i++)
		{
		if (zeros[i] == c)
			{
			for (j=i+1; j<numOfZeros; j++)
				zeros[j-1] = zeros[j];
			numOfZeros--;
			savedFlag = 0;
			return 0;
			}
		}

	return -1;
	}


int Filter::getFirstPole (complex &z)
	{
	topicPole = 0;
	return getNextPole (z);
	}


int Filter::getFirstZero (complex &z)
	{
	topicZero = 0;
	return getNextZero(z);
	}


int Filter::getNextPole (complex &z)
	{
	if (topicPole >= numOfPoles)
		return -1;

	z = poles[topicPole];
	topicPole++;

	return 0;
	}


int Filter::getNextZero (complex &z)
	{
	if (topicZero >= numOfZeros)
		return -1;

	z = zeros[topicZero];
	topicZero++;

	return 0;
	}


int Filter::findPoleEpsilon (complex &z, float epsilon, complex &best)
	{
	int i, bestN;
	float d, bestDistance;
	complex pole;

	pole = CORRZ(z);
	bestN = -1;
	bestDistance = 2.*epsilon;

	for (i=0; i<numOfPoles; i++)
		{
		if ((d=abs(poles[i]-pole)) < epsilon)
			{
			if (d < bestDistance)
				{
				bestN = i;
				bestDistance = d;
				}
			}
		}

	if (bestN == -1) return -1;

	best = poles[bestN];

	return 0;
	}


int Filter::findZeroEpsilon (complex &z, float epsilon, complex &best)
	{
	int i, bestN;
	float d, bestDistance;
	complex zero;
	
	zero = CORRZ(z);
	if (imag(z) < 0)
		zero = conj(z);
	else
		zero = z;

	bestN = -1;
	bestDistance = 2.*epsilon;

	for (i=0; i<numOfZeros; i++)
		{
		if ((d=abs(zeros[i]-zero)) < epsilon)
			{
			if (d < bestDistance)
				{
				bestN = i;
				bestDistance = d;
				}
			}
		}

	if (bestN == -1) return -1;

	best = zeros[bestN];

	return 0;
	}


int Filter::movePole (complex &a, complex &b)
	{
	int i;
	complex pole, to;

	if (abs(b) >= 1) return -1;

	pole = CORRZ(a);
	to = CORRZ(b);

	for (i=0; i<numOfPoles; i++)
		{
		if (pole == poles[i])
			{
			poles[i] = to;
			savedFlag = 0;
			return 0;
			}
		}

	return -2;
	}


int Filter::moveZero (complex &a, complex &b)
	{
	int i;
	complex zero, to;

	zero = CORRZ(a);
	to = CORRZ(b);

	for (i=0; i<numOfZeros; i++)
		{
		if (zero == zeros[i])
			{
			zeros[i] = to;
			savedFlag = 0;
			return 0;
			}
		}

	return -1;
	}


int Filter::load (const char fn[])
	{
	int rtn;
	FILE *f;
	char str[200];
	double real, imag;

	rtn = 0;
	clear ();

	f = fopen (fn, "rt");
	if (!f) return -1;

	fgets (str, 200, f);
	if (strncmp (str, "#PNP", 4) == 0)
		{
		while (fgets (str, 200, f))
			{
			sscanf (str, "%*c %lf %lf", &real, &imag);
			switch (str[0])
				{
				case 'p':
				case 'P':
					addPole (complex (real, imag));
					break;
				case 'z':
				case 'Z':
					addZero (complex (real, imag));
					break;
				}
			}
		}
	else
		{
		fseek (f, 0, SEEK_SET);
		rtn = loadOldFormat (f);
		}

	fclose (f);
	savedFlag = 1;
	setFilename (fn);

	return rtn;
	}


int Filter::save (const char fn[])
	{
	FILE *f;
	complex c;

	if (fn) f = fopen (fn, "wt");
	else f = fopen (getFilename(), "wt");

	if (!f) return -1;

	fprintf (f, "#PNP\n");
//	fprintf (f, "n %s\n", getName());

	if (getFirstPole(c) == 0)
		do
			fprintf (f, "p %f %f\n", real(c), imag(c));
		while (getNextPole(c) == 0);

	if (getFirstZero(c) == 0)
		do
			fprintf (f, "z %f %f\n", real(c), imag(c));
		while (getNextZero(c) == 0);

	fclose (f);
	savedFlag = 1;
	if (fn) setFilename (fn);

	return 0;
	}


int Filter::amplitudeLin (int len, float buf[])
	{
	long double a, b;
	complex e;
	int i, j;

	for (i=0; i<len; i++)
		{
		e = exp (complex(0,1)*((M_PI*i)/len));

		a = 1;
		for (j=0; j<numOfZeros; j++)
			{
			a *= abs(e-zeros[j]);
			if (imag(zeros[j]) != 0.)
				a *= abs(e-conj(zeros[j]));
			}

		b = 1;
		for (j=0; j<numOfPoles; j++)
			{
			b *= abs(e-poles[j]);
			if (imag(poles[j]) != 0.)
				b *= abs(e-conj(poles[j]));
			}

		buf[i] = a / b;
		}

	return 0;
	}


int Filter::amplitudeLog (int len, float buf[])
	{
	int i;

	amplitudeLin (len, buf);

	for (i=0; i<len; i++)
		if (buf[i] > 0.)
			buf[i] = 20 * log10(buf[i]);

	return 0;
	}


int Filter::phase (int len, float buf[])
	{
	long double a, b;
	double d, g;
	complex e;
	int i, j;

	for (i=0; i<len; i++)
		{
		e = exp (complex(0,1)*((M_PI*i)/len));

		a = 0;
		for (j=0; j<numOfZeros; j++)
			{
			a += arg(e-zeros[j]);
			if (imag(zeros[j]) != 0.)
				a += arg(e-conj(zeros[j]));
			}

		b = 0;
		for (j=0; j<numOfPoles; j++)
			{
			b += arg(e-poles[j]);
			if (imag(poles[j]) != 0.)
				b += arg(e-conj(poles[j]));
			}

		buf[i] = b - a;
		}

// Korrektur bzw. Gl„ttung der Kurve:
	for (i=1; i<len; i++)
		{
		d = buf[i] - buf[i-1];
		modf (d/(2*M_PI)*1.1,&g);
		if (g != 0.)
			for (j=i; j<len; j++)
				buf[j] -= g*(2*M_PI);
		}

	return 0;
	}


int Filter::groupdelay (int len, float buf[])
	{
	int i, j;
	long double a, b;
	long double r, phi;
	long double c;

	for (i=0; i<len; i++)
		{
		phi = (M_PI*i) / len;
		a = 0;
		for (j=0; j<numOfZeros; j++)
			{
			r = abs(zeros[j]);
			c = cos(phi - arg(zeros[j]));
			a += (1-r*c) / (1+r*r-2*r*c);
			}
		b = 0;
		for (j=0; j<numOfPoles; j++)
			{
			r = abs(poles[j]);
			c = cos(phi - arg(poles[j]));
			b += (1-r*c) / (1+r*r-2*r*c);
			}
		buf[i] = b - a;
		}

	return 0;
	}

/*
int Filter::impulseresponse (int len, float buf[])
	{
	int i, j;
	double a, b;

	for (j=0; j<len; j++)
		buf[j] = 0.;

	buf[0] = 1.;

	for (i=0; i<numOfPoles; i++)
		{
		if (imag(poles[i]) == 0)
			{
			a = real (poles[i]);
			for (j=1; j<len; j++)
				buf[j] = buf[j] + a * buf[j-1];
			}
		else
			{
			a = abs (poles[i]) * abs(poles[i]);
			b = 2 * cos(arg(poles[i]));
			buf[1] += b*buf[0];
			for (j=2; j<len; j++)
				buf[j] += b*buf[j-1] - a*buf[j-2];
//			@@@ Wo ist hier der Fehler?
			}
		}

	for (i=0; i<numOfZeros; i++)
		{
		if (imag(zeros[i]) == 0)
			{
			a = real (zeros[i]);
			for (j=1; j<len; j++)
				buf[j-1] = buf[j] - a*buf[j-1];
			buf[len-1] = -a*buf[len-1];
			}
		else
			{
			a = abs(zeros[i]) * abs(zeros[i]);
			b = 2 * cos(arg(zeros[i]));
			for (j=2; j<len; j++)
				buf[j-2] = buf[j] - b*buf[j-1] + a*buf[j-2];
			buf[len-2] = - b*buf[len-1] + a*buf[len-2];
			buf[len-1] = a*buf[j-2];
			}
		}

	return 0;
	}
*/
int Filter::impulseresponse (int len, float buf[])
	{
	int i, j;
	complex *cbuf;
	complex z;

	cbuf = new complex [len];
	if (!cbuf) return -1;

	for (j=0; j<len; j++) cbuf[j] = 0;
	cbuf[0] = 1;

	for (i=0; i<numOfZeros; i++)
		{
		z = zeros[i];
		for (j=len-1; j>0; j--)
			cbuf[j] += z * cbuf[j-1];
		if (imag(z) != 0)
			{
			z = conj(z);
			for (j=len-1; j>0; j--)
				cbuf[j] += z * cbuf[j-1];
			}
		}

	for (i=0; i<numOfPoles; i++)
		{
		z = poles[i];
		for (j=0; j<len; j++)
			cbuf[j] += z * cbuf[j-1];
		if (imag(z) != 0)
			{
			z = conj(z);
			for (j=1; j<len; j++)
				cbuf[j] += z * cbuf[j-1];
			}
		}

	for (j=0; j<len; j++)
		buf[j] = real(cbuf[j]);

	delete cbuf;

	return 0;
	}


void Filter::print ()
	{
	int i;

	printf ("Filter::print():\n");

	for (i=0; i<numOfPoles; i++)
		printf ("p %e %e\n", real(poles[i]), imag(poles[i]));

	for (i=0; i<numOfZeros; i++)
		printf ("z %e %e\n", real(zeros[i]), imag(zeros[i]));
	}


void Filter::setFilename (const char fn[])
	{
	if (filename)
		delete filename;

	filename = 0;

	if (fn)
		{
		filename = new char[strlen(fn)+1];
		strcpy (filename, fn);
		}
	}


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

