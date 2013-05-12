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
 * PNPDesktop.java
 *
 *
 * Created: Tue Sep 18 11:48:30 2001
 *
 * @author
 * @version
 */

package de.ratopi.dsp.pnp.gui;

import java.util.ResourceBundle;

import de.ratopi.dsp.pnp.filter.FilterInfo;

public interface PNPDesktop
{
	public void loadFilter();

	public void loadFilter( FilterInfo filterInfo );

	public void quit();

	public void newFilterWindow();

	public void showHelpAbout();

	public void showUnitCircle( FilterInfo filterInfo );

	public void showAmplitudeLin( FilterInfo filterInfo );

	public void showAmplitudeLog( FilterInfo filterInfo );

	public void showPhase( FilterInfo filterInfo );

	public void showGroupdelay( FilterInfo filterInfo );

	public void showImpulseresponse( FilterInfo filterInfo );

	public ResourceBundle getBundle();
}
