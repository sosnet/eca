
import java.io.*;

import org.apache.bsf.*;
import org.apache.bsf.util.*;

public class PiApp {
	BSFManager mgr = new BSFManager();

	public PiApp (String fileName) {
		try {
			String language = BSFManager.getLangFromFilename(fileName);
			FileReader in = new FileReader(fileName);
			String script = IOUtils.getStringFromReader(in);

			mgr.exec(language, fileName, 1, 1, script);
		} catch (BSFException e) {
			System.err.println ("Ouch: " + e.getMessage ());
			e.printStackTrace ();
		} catch (IOException e) {
			System.err.println ("Ouch: " + e.getMessage ());
			e.printStackTrace ();
		}

		mgr.terminate();
	}

	public static void main (String[] args) throws Exception {
		if (args.length != 1) {
			System.err.println ("Usage: java pi.PiApp filename");
			System.err.println ("       where filename is the name of the script");
			System.exit (1);
		}
		new PiApp(args[0]);
	}
}
