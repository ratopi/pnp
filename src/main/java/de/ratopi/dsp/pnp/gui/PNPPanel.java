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
// PNPPanel.java
//
// 4.3.01 rtp.

package de.ratopi.dsp.pnp.gui;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ResourceBundle;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import de.ratopi.dsp.pnp.filter.Filter;
import de.ratopi.dsp.pnp.filter.FilterFileFilter;
import de.ratopi.dsp.pnp.filter.FilterInfo;

/**
 * <p>
 * <code>PNPPanel</code> is the main panel for the PNPPanel application.
 * This class is used by the <code>PNP</code> (stand alone version) and
 * <code>PNPApplet</code> (applet version) classes.
 * </p>
 */

public class PNPPanel
	extends JPanel
	implements PNPDesktop
{
	// Variablen:

	/**
	 * The desktop is where everything happen -- except the menu things.
	 */
	private JDesktopPane desktop;

	private ResourceBundle theResourceBundle;

	/**
	 * ..
	 */
	public PNPPanel()
	{
		super();

		setLayout( new BorderLayout() );

		desktop = new JDesktopPane();
		// desktop.putClientProperty ("JDesktopPane.dragMode", "outline");

		theResourceBundle = ResourceBundle.getBundle( "de.ratopi.dsp.pnp.text" );

		add( new PNPMenu( this ), BorderLayout.NORTH );
		add( desktop, BorderLayout.CENTER );

		createFrame( getBundle().getString( "title.readme" ), new Readme(), true );
	}

	// Methoden:

	public void loadFilter()
	{
		FilterInfo fi = new FilterInfo();
		loadFilter( fi );
		showUnitCircle( fi );
	}

	public void loadFilter( FilterInfo filterInfo )
	{
		JFileChooser fc = new JFileChooser();

		fc.addChoosableFileFilter( new FilterFileFilter() );

		int rtn = fc.showOpenDialog( this );

		if ( rtn == JFileChooser.APPROVE_OPTION )
		{
			File file = fc.getSelectedFile();
			Filter filter = filterInfo.getFilter();
			// Filter filter = new Filter ();
			try
			{
				Reader r = new FileReader( file );
				filter.read( r );
				r.close();
				filterInfo.setFile( file );
				// desktop.add (new UnitCircleFrame (filterInfo, desktop));
			}
			catch ( java.io.FileNotFoundException excp )
			{
				System.err.println( "PNPPanel.filterload() : " + excp );
			}
			catch ( java.io.IOException excp )
			{
				System.err.println( "PNPPanel.filterload() : " + excp );
			}
		}
	}

	public void quit()
	{
		System.exit( 0 );
	}

	public void newFilterWindow()
	{
		showUnitCircle( new FilterInfo() );
	}


	// Verschiedene Fenster-Methoden:

	public void showHelpAbout()
	{
		desktop.add( new About() );
	}

	public void showUnitCircle( FilterInfo filterInfo )
	{
		desktop.add( new UnitCircleFrame( filterInfo, this ) );
	}

	public void showAmplitudeLin( FilterInfo filterInfo )
	{
		//desktop.add (new PlotPaneFrame (filterInfo.getFilter(), PlotPane.AMPLITUDE_LIN));
		desktop.add( new PlotPaneFrame( new PlotAmplitudeLin( filterInfo.getFilter() ) ) );
	}

	public void showAmplitudeLog( FilterInfo filterInfo )
	{
		//desktop.add (new PlotPaneFrame (filterInfo.getFilter(), PlotPane.AMPLITUDE_LOG));
		desktop.add( new PlotPaneFrame( new PlotAmplitudeLog( filterInfo.getFilter() ) ) );
	}

	public void showPhase( FilterInfo filterInfo )
	{
		//desktop.add (new PlotPaneFrame (filterInfo.getFilter(), PlotPane.PHASE));
		desktop.add( new PlotPaneFrame( new PlotPhase( filterInfo.getFilter() ) ) );
	}

	public void showGroupdelay( FilterInfo filterInfo )
	{
		//desktop.add (new PlotPaneFrame (filterInfo.getFilter(), PlotPane.GROUPDELAY));
		desktop.add( new PlotPaneFrame( new PlotGroupdelay( filterInfo.getFilter() ) ) );
	}

	public void showImpulseresponse( FilterInfo filterInfo )
	{
		//desktop.add (new PlotPaneFrame (filterInfo.getFilter(), PlotPane.IMPULSERESPONSE));
		desktop.add( new PlotPaneFrame( new PlotImpulseresponse( filterInfo.getFilter() ) ) );
	}

	//

	public ResourceBundle getBundle()
	{
		return theResourceBundle;
	}

	/**
	 * Simple creation of a <code>JInternalFrame</code>.
	 */
	private void createFrame( String title, JComponent p, boolean resizable )
	{
		JInternalFrame f = new JInternalFrame( title, resizable, true, resizable, resizable );

		f.setContentPane( p );
		f.setVisible( true );
		f.setSize( 400, 200 );
		f.setLocation( 10, 10 );
		desktop.add( f );

		try
		{
			f.setSelected( true );
		}
		catch ( java.beans.PropertyVetoException e )
		{
		}

		/*
		 MyInternalFrame frame = new MyInternalFrame();
		 frame.setVisible(true); //necessary as of kestrel
		 desktop.add(frame);
		 try {
		 frame.setSelected(true);
		 } catch (java.beans.PropertyVetoException e) {}
	 */
	}

}

