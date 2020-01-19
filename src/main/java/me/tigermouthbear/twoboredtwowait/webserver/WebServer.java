package me.tigermouthbear.twoboredtwowait.webserver;

import com.sun.net.httpserver.HttpServer;
import me.tigermouthbear.twoboredtwowait.TwoBoredTwoWait;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author Tigermouthbear
 * 1/16/19
 */

public class WebServer
{
	private static final int PORT = Integer.valueOf(TwoBoredTwoWait.CONFIG.getProperty("port"));

	public WebServer()
	{
		try
		{
			HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
			server.createContext("/", new IndexHandler());
			server.createContext("/GET", new GetHandler());
			server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
			server.start();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
