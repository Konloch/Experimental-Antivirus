package com.konloch;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static com.konloch.ExperimentalAntiVirus.*;

/**
 * @author Konloch
 * @since 6/20/2024
 */
public class AEVTray
{
	public SystemTray tray;
	public TrayIcon trayIcon;
	public PopupMenu trayPopup;
	public MenuItem toggleButton;
	
	public AEVTray() throws AWTException, IOException
	{
		tray = SystemTray.getSystemTray();
		trayIcon = new TrayIcon(ImageIO.read(AEVTray.class.getResourceAsStream("/icon.png")), "Experimental Antivirus: Disabled");
		trayPopup = new PopupMenu();
		
		toggleButton = new MenuItem("Enable");
		toggleButton.addActionListener(e ->
		{
			try
			{
				if (AEV.enabled)
					AEV.disable();
				else
					AEV.enable();
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
