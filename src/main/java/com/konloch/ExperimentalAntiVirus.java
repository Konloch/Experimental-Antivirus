package com.konloch;

import java.awt.AWTException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Konloch
 * @since 6/20/2024
 */
public class ExperimentalAntiVirus
{
	public static ExperimentalAntiVirus EAV;
	private List<Process> processPool = new ArrayList<>();
	public EAVTray tray;
	public boolean enabled = false;
	
	public static void main(String[] args) throws AWTException, IOException
	{
		EAV = new ExperimentalAntiVirus();
		EAV.tray = new EAVTray();
		
		//add shutdown hook to disable all active processes
		Runtime.getRuntime().addShutdownHook(new Thread(() -> EAV.disable()));
		
		//enable the pseudo protection
		EAV.enable();
	}
	
	//extract BlankProcess.exe to the various files, then run them
	public void enable() throws IOException
	{
		File workingDirectory = new File(System.getProperty("user.home") + File.separator + "EAV.konloch");
		
		if(!workingDirectory.exists())
			workingDirectory.mkdir();
		
		String[] processList;
		try (InputStream inputStream = ExperimentalAntiVirus.class.getResourceAsStream("/FakeProcesses.txt"))
		{
			int bytesRead;
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			while ((bytesRead = inputStream.read(buffer)) != -1)
				outputStream.write(buffer, 0, bytesRead);
			
			processList = outputStream.toString("UTF-8").split("\\r?\\n");
		}
		
		for(String fakeProcessName : processList)
		{
			if(fakeProcessName.isEmpty() || fakeProcessName.startsWith("#"))
				continue;
			
			File fakeProcessFile = new File(workingDirectory, fakeProcessName);
			
			//creates a fake process by copying BlankProcess.exe
			if(!fakeProcessFile.exists())
				createFakeProcess(fakeProcessFile);
			
			//run BlankProcess.exe under the new name
			Process process;
			try
			{
				process = new ProcessBuilder(fakeProcessFile.getAbsolutePath()).start();
				processPool.add(process);
			}
			catch (IOException e)
			{
				System.err.println("Error running process: " + e.getMessage());
			}
		}
		
		tray.trayIcon.setToolTip("Experimental Antivirus: Active");
		tray.toggleButton.setLabel("Disable");
		EAV.enabled = true;
	}
	
	//kill each process
	public void disable()
	{
		for (Process process : processPool)
			process.destroy();
		
		processPool.clear();
		
		tray.trayIcon.setToolTip("Experimental Antivirus: Disabled");
		tray.toggleButton.setLabel("Enable");
		EAV.enabled = false;
	}
	
	private void createFakeProcess(File fakeProcessFile) throws IOException
	{
		try (InputStream inputStream = ExperimentalAntiVirus.class.getResourceAsStream("/BlankProcess.exe");
		     OutputStream outputStream = new FileOutputStream(fakeProcessFile))
		{
			int bytesRead;
			byte[] buffer = new byte[1024];
			while ((bytesRead = inputStream.read(buffer))!= -1)
				outputStream.write(buffer, 0, bytesRead);
		}
	}
}