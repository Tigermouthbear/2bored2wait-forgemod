package me.tigermouthbear.twoboredtwowait.webserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import me.tigermouthbear.twoboredtwowait.TwoBoredTwoWait;
import net.minecraft.client.multiplayer.ServerData;

import java.io.IOException;

/**
 * @author Tigermouthbear
 * 1/16/20
 */

public class GetHandler implements HttpHandler
{
	private static final String password = TwoBoredTwoWait.CONFIG.getProperty("password");
	private static final ServerData TWOBEEWTOTEA = new ServerData("2b2t", "2b2t.org", false);

	@Override
	public void handle(HttpExchange he) throws IOException
	{
		String uri = he.getRequestURI().toString();

		if(uri.contains("update"))
		{
			he.sendResponseHeaders(200, 0);
			he.getResponseBody().write(TwoBoredTwoWait.getUpdate().getBytes());
			he.close();
			return;
		}

		if(he.getRequestHeaders().get("Xpassword").get(0).equals(password))
		{
			if(uri.contains("start"))
			{
				he.sendResponseHeaders(200, 0);
				he.close();
				TwoBoredTwoWait.connect();
			}
			else if(uri.contains("stop"))
			{
				he.sendResponseHeaders(200, 0);
				he.close();
				TwoBoredTwoWait.disconnect();
			}
		}
		else
		{
			he.sendResponseHeaders(404, 0);
			he.close();
		}
	}
}