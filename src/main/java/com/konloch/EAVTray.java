package com.konloch;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static com.konloch.ExperimentalAntiVirus.*;

/**
 * @author Konloch
 * @since 6/20/2024
 */
public class EAVTray
{
	public SystemTray tray;
	public TrayIcon trayIcon;
	public PopupMenu trayPopup;
	public MenuItem toggleButton;
	
	public EAVTray() throws AWTException, IOException
	{
		tray = SystemTray.getSystemTray();
		trayIcon = new TrayIcon(ImageIO.read(EAVTray.class.getResourceAsStream("/icon.png")), "Experimental Antivirus: Disabled");
		trayPopup = new PopupMenu();
		
		toggleButton = new MenuItem("Enable");
		toggleButton.addActionListener(e ->
		{
			try
			{
				if (EAV.enabled)
					EAV.disable();
				else
					EAV.enable();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		});
		trayPopup.add(toggleButton);
		
		MenuItem exitButton = new MenuItem("Exit");
		exitButton.addActionListener(e -> System.exit(0));
		trayPopup.add(exitButton);
		
		trayIcon.setPopupMenu(trayPopup);
		
		tray.add(trayIcon);
	}
}
