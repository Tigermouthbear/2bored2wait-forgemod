package me.tigermouthbear.twoboredtwowait;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

/**
 * @author Tigermouthbear
 * 1/18/20
 */

public class Config
{
	private Properties properties = new Properties();

	public Config()
	{
		try
		{
			File configFile = new File("2bored2wait.properties");
			if(!configFile.exists())
			{
				configFile.createNewFile();
				BufferedWriter b = new BufferedWriter(new FileWriter(configFile));
				b.write("port:80\npassword:");
				b.close();
			}

			properties.load(new FileReader(configFile));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getProperty(String name)
	{
		return properties.getProperty(name);
	}
}
