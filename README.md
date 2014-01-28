# Sphero Java SDK

#####*I don’t know where my ideas come from. I will admit, however, that one key ingredient is caffeine. I get a couple cups of coffee into me and weird things just start to happen.* ~ Gary Larson

#####*You play with your balls a lot* ~ John Candy, Plains, Trains and Automobiles

A few weeks ago I picked up a Sphero specifically so that I could start writing some code for it.  After getting Fred all trained up (yeah, my kids named it Fred - I have no idea why.  We don't know any Freds.), it was time to grab a cup of joe and start coding.

Being a predominantly Java guys these days, that was obviously going to be my foundation.  I was kind of surprised that there wasn't an easy to use Java library out there for the Sphero API.  Android, yep; iOS, of course; Windows 8, check; and a slew of others.  But no straightfoward set of Java classes to connect to the ball and get it rolling.  My end goal is to write a Blockly (https://code.google.com/p/blockly/) translator so that my kids can code it directly.  But baby steps first...

The library should be buildable simply by opening the project in Eclipse (with a Maven plugin) and building.  There are no test cases or sample programs in the project at the moment and it's still very sparse.  I just reached a point where I wanted it under source countrol!

I've got a separate Eclipse project that I'm using for testing, but the test class is really simple.  Just change the address to your Sphero's bluetooth address and fire it up.  You'll need your OS's implementation of bluecove available at runtime.  The base bluecove library is in the Maven config in the SDK project but you'll need the implementation classes (for instance, on my Debian box I use bluecove-gpl) at runtime.

```java
package com.liquidchaos.sphero;

import java.io.IOException;

public class SpheroTester {
	Sphero sphero;

	public SpheroTester() throws IOException {
		sphero = new Sphero("6886E7014B22");
	}
	
	public void run() throws IOException {
		pulse(2, true, false, false);
		pulse(2, false, true, false);
		pulse(2, false, false, true);
	}
	
	private void pulse(int step, boolean pulseRed, boolean pulseGreen, boolean pulseBlue) throws IOException {
		for (int i=0; i<256; i=i+step) {
			byte fade = (byte)(255-i);
			byte color = (byte)255;
			
			byte red = pulseRed ? color : fade;
			byte green = pulseGreen ? color : fade;
			byte blue = pulseBlue ? color : fade;
			sphero.setLEDColor(red, green, blue, false);
		}
		
		for (int i=0; i<256; i=i+step) {
			byte fade = (byte)(i);
			byte color = (byte)255;
			
			byte red = pulseRed ? color : fade;
			byte green = pulseGreen ? color : fade;
			byte blue = pulseBlue ? color : fade;
			sphero.setLEDColor(red, green, blue, false);
		}
	}

	public static void main(String[] args) throws Exception {
		
		try {
			SpheroTester tester = new SpheroTester();
			tester.run();
		} catch (IOException ioe) {
			System.err.println("Could not connect to Sphero.");
		}
	}
}
```
