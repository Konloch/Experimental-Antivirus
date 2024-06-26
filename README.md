# Experimental-Antivirus
Just a tiny experiment, for the real application you should use https://github.com/NavyTitanium/Fake-Sandbox-Artifacts

## About
Assuming the malware you could be exposed to is checking the active processes - for reverse engineering tools and then safely shuts itself down if found - we can abuse that by pretending to be those tools.

![Screenshot-2](.github/screen-2.png "Screenshot-2")
![Screenshot-1](.github/screen-1.png "Screenshot-1")
![Screenshot-3](.github/screen-3.png "Screenshot-3")

## Theory
Generally malware will contain a sandbox check. This involves identifying if the current machine contains any form of virtualization used to reverse engineer the malware. A common method of handling this situation is to close and no longer attempt the infection. But this is of-course entirely up to the malware itself. Ideally this concept would be part of some free antivirus package, as this is just one small component. This is licensed MIT to help push it towards that future.

## Links
https://github.com/NavyTitanium/Fake-Sandbox-Artifacts

## Requires
Java 1.8
Windows 10 (Earlier versions would probably work fine)

## Notes
May have issues on 32bit operating systems - just recompile the BlankProcess.exe to solve this.