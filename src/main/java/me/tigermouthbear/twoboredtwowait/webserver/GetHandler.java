package me.tigermouthbear.twoboredtwowait.webserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import me.tigermouthbear.twoboredtwowait.GetRequestEvent;
import me.tigermouthbear.twoboredtwowait.TwoBoredTwoWait;
import net.minecraftforge.common.MinecraftForge;

import java.io.IOException;

/**
 * @author Tigermouthbear
 * 1/16/20
 */

public class GetHandler implements HttpHandler
{
	private static final String password = TwoBoredTwoWait.CONFIG.getProperty("password");

	@Override
	public void handle(HttpExchange he) throws IOException
	{
		String uri = he.getRequestURI().toString();

		if(uri.contains("update"))
		{
			he.sendResponseHeaders(200, 0);
			GetRequestEvent.Update event = new GetRequestEvent.Update();
			MinecraftForge.EVENT_BUS.post(event);
			he.getResponseBody().write(event.getData().getBytes());
			he.close();
			return;
		}

		if(he.getRequestHeaders().get("Xpassword").get(0).equals(password))
		{
			if(uri.contains("start"))
			{
				he.sendResponseHeaders(200, 0);
				he.close();
				GetRequestEvent.Start event = new GetRequestEvent.Start();
				MinecraftForge.EVENT_BUS.post(event);
			}
			else if(uri.contains("stop"))
			{
				he.sendResponseHeaders(200, 0);
				he.close();
				GetRequestEvent.Stop event = new GetRequestEvent.Stop();
				MinecraftForge.EVENT_BUS.post(event);
			}
		}
		else
		{
			he.sendResponseHeaders(404, 0);
			he.close();
		}
	}
}