/* Copyright (C) 2011 IsmAvatar <IsmAvatar@gmail.com>
 * Copyright (C) 2011 Josh Ventura <joshv@zoominternet.net>
 * 
 * This file is part of JoshEdit. JoshEdit is free software.
 * You can use, modify, and distribute it under the terms of
 * the GNU General Public License, version 3 or later. 
 */

package org.lateralgm.joshedit;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.prefs.Preferences;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretListener;

import org.lateralgm.joshedit.Code.CodeEvent;
import org.lateralgm.joshedit.Code.CodeListener;

public class Runner
{
	public static final ResourceBundle MESSAGES = ResourceBundle.getBundle("org.lateralgm.joshedit.translate"); //$NON-NLS-1$
	public static final Preferences PREFS = Preferences.userRoot().node("/org/lateralgm/joshedit"); //$NON-NLS-1$

	public static EditorInterface editorInterface = new EditorInterface()
	{
		public ImageIcon getIconForKey(String key)
		{
			return Runner.getIconForKey(key);
		}

		public String getString(String key)
		{
			return Runner.getString(key,null);
		}

		public String getString(String key, String def)
		{
			return Runner.getString(key,def);
		}
	};

	public static interface EditorInterface
	{
		ImageIcon getIconForKey(String key);

		String getString(String key);

		String getString(String key, String def);
	}

	public static void createAndShowGUI()
	{
		showCodeWindow(true);
		//showBindingsWindow(false);
	}

	public static class JoshTextPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;

		public JScrollPane scroller;
		public JoshText text;
		public LineNumberPanel lines;
		public QuickFind find;

		public JoshTextPanel()
		{
			this((String[]) null);
		}

		public JoshTextPanel(String code)
		{
			this(Runner.splitLines(code));
		}

		public JoshTextPanel(String[] codeLines)
		{
			super(new BorderLayout());

			text = new JoshText(codeLines);
			lines = new LineNumberPanel(text.getFont(),text.code.size());
			text.code.addCodeListener(new CodeListener()
			{
				public void codeChanged(CodeEvent e)
				{
					lines.setLines(text.code.size());
				}
			});

			//TODO: Remove text.quickFind (remove the interdependence)
			find = new QuickFind(text);
			text.finder = find;

			scroller = new JScrollPane(text);
			scroller.setRowHeaderView(lines);

			add(scroller,BorderLayout.CENTER);
			add(find,BorderLayout.SOUTH);
		}

		void addEditorButtons(JToolBar tb) //adds a bunch of buttons for interacting with the text (copy, goto line, syntax check, etc)
		{
			//TODO: IMPLEMENT
		}

		int getCaretLine()
		{
			return text.caret.row;
		}

		int getCaretColumn()
		{
			return text.caret.col;
		}

		void addCaretListener(CaretListener cl)
		{
			text.caret.addCaretListener(cl);
		}

		String getTextCompat() //convenience method that replaces newlines with \r\n for GM compatability
		{
			String res = "";
			for (int i = 0; i < text.code.size(); i++)
				res += text.code.getsb(i).toString().replaceAll("[^\r]\n","\r\n");
			return res;
		}

		boolean isChanged() //will probably need replaced. Simply used to determine if the text has changed at all (resourceChanged()) so can probably just replace with isChanged()
		{
			return text.isChanged();
		}

	}

	public static void showCodeWindow(boolean closeExit)
	{
		JFrame f = new JFrame("Title");
		JoshTextPanel p = new JoshTextPanel(getDefaultCode());
		f.setContentPane(p);
		f.pack();
		f.setLocationRelativeTo(null);
		if (closeExit) f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public static String[] getDefaultCode()
	{
		ArrayList<String> code = new ArrayList<String>();
		code.add("Hello, world");
		code.add("Mystical second line what can't' be edited");
		code.add("\tTab test line 1");
		code.add("\tTab test line 2");
		code.add("\tTab test line 3");
		code.add("End tab test line");
		code.add("derp\tCascading tab test");
		code.add("der\tCascading tab test");
		code.add("de\tCascading tab test");
		code.add("d\tCascading tab test");
		code.add("if (a = \"It's hard to think\")");
		code.add("  then b = c + 10; // That after all this time");
		code.add("else /* Everything is finally working.");
		code.add("        *wipes tear* */");
		code.add("  d = e * 20;");
		code.add("/** Okay, so there are probably");
		code.add("    some bugs to work out, but you");
		code.add("    have to admit... It looks nice. */");
		return code.toArray(new String[0]);
	}

	public static String[] splitLines(String text)
	{
		if (text == null) return null;
		LinkedList<String> list = new LinkedList<String>();
		Scanner sc = new Scanner(text);
		while (sc.hasNext())
			list.add(sc.nextLine());
		return list.toArray(new String[0]);
	}

	public static void showBindingsWindow(boolean closeExit)
	{
		JFrame f = new JFrame();
		f.getContentPane().setLayout(new BoxLayout(f.getContentPane(),BoxLayout.PAGE_AXIS));

		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("Bindings",new Bindings());
		f.add(tabs);

		JPanel repanel = new JPanel();
		repanel.setLayout(new BoxLayout(repanel,BoxLayout.LINE_AXIS));
		repanel.add(new JButton("OK"));
		repanel.add(new JButton("Cancel"));
		f.add(repanel);

		f.pack();
		f.setLocationRelativeTo(null);
		if (closeExit) f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public static void showFindWindow(boolean closeExit)
	{
		JFrame f = new JFrame();
		f.getContentPane().setLayout(new BoxLayout(f.getContentPane(),BoxLayout.PAGE_AXIS));

	}

	public static ImageIcon findIcon(String filename)
	{
		String location = "org/lateralgm/joshedit/icons/" + filename; //$NON-NLS-1$
		ImageIcon ico = new ImageIcon(location);
		if (ico.getIconWidth() == -1)
		{
			URL url = Runner.class.getClassLoader().getResource(location);
			if (url != null) ico = new ImageIcon(url);
		}
		return ico;
	}

	public static ImageIcon getIconForKey(String key)
	{
		Properties iconProps = new Properties();
		InputStream is = Runner.class.getClassLoader().getResourceAsStream(
				"org/lateralgm/joshedit/icons.properties"); //$NON-NLS-1$
		try
		{
			iconProps.load(is);
		}
		catch (IOException e)
		{
			System.err.println("Unable to read icons.properties"); //$NON-NLS-1$
		}
		String filename = iconProps.getProperty(key,""); //$NON-NLS-1$
		if (!filename.isEmpty()) return findIcon(filename);
		return null;
	}

	public static String getString(String key, String def)
	{
		String r;
		try
		{
			r = MESSAGES.getString(key);
		}
		catch (MissingResourceException e)
		{
			r = def == null ? '!' + key + '!' : def;
		}
		return PREFS.get(key,r);
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				createAndShowGUI();
			}
		});
	}
}
