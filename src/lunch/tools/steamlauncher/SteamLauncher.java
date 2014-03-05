package lunch.tools.steamlauncher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class SteamLauncher {
	
	final Properties config;
	private static final String Steam_exe = "Steam.exe";
	private static final String ClientRegistry_blob = "ClientRegistry.blob";
	String steamDir;
	
	public SteamLauncher() throws FileNotFoundException, IOException
	{
		config = new Properties();
		config.load(ClassLoader.getSystemResourceAsStream("resources/config.properties"));
		steamDir = config.getProperty("steamdir");
		
		deleteClientRegistryBlob();
		
		//if (!checkSteamAlreadyRunning())
			launchSteam();
	}

	private void launchSteam() throws IOException {
		Runtime.getRuntime().exec("cmd /c ".concat(steamDir).concat(Steam_exe));
		System.exit(0);
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		new SteamLauncher();

	}
	
	private boolean deleteClientRegistryBlob()
	{
		File clientRegistryBlob = new File(steamDir.concat(ClientRegistry_blob));
		
		return clientRegistryBlob.delete();
	}
	
	private boolean checkSteamAlreadyRunning()
	{
		 try {
		        String line;
		        Process p = Runtime.getRuntime().exec("ps -e");
		        BufferedReader input =
		                new BufferedReader(new InputStreamReader(p.getInputStream()));
		        while ((line = input.readLine()) != null) {
		           if (line.contains("Steam"))
		        	   return true;
		        }
		        input.close();
		    } catch (Exception err) {
		        err.printStackTrace();
		    }
		 
	        return false;
	}

}
