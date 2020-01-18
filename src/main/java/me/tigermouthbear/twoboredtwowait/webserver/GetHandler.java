package me.tigermouthbear.twoboredtwowait.webserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import me.tigermouthbear.twoboredtwowait.TwoBoredTwoWait;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;
import java.net.URI;

/**
 * @author Tigermouthbear
 * 1/16/20
 */

public class GetHandler implements HttpHandler, TwoBoredTwoWait.Globals
{
	private static final ServerData TOOBEETWOTEA = new ServerData("2b2t.org", "2b2t.org", false);

	@Override
	public void handle(HttpExchange he) throws IOException
	{
		URI requestedUri = he.getRequestURI();
		String uri = requestedUri.toString();

		if(uri.contains("start"))
		{
			he.sendResponseHeaders(200, 0);
			he.close();

			//Just in case
			if(MC.world != null) disconnect();
			//Then connect to server
			MC.displayGuiScreen(new GuiConnecting(new GuiMainMenu(), MC, TOOBEETWOTEA));
		}
		else if(uri.contains("stop"))
		{
			he.sendResponseHeaders(200, 0);
			he.close();

			//Disconnect from world
			disconnect();
		}
		else if(uri.contains("update"))
		{
			he.sendResponseHeaders(200, 0);

			//Get info about if the client is connected to 2b2t
			boolean isInQueue = MC.getCurrentServerData().serverIP.contains("2b2t.org");
			String response = "{\"username\": \""+ MC.player.getDisplayNameString() +"\",\"place\": \""+ TwoBoredTwoWait.placeInQueue +"\",\"ETA\": \""+ TwoBoredTwoWait.eta +"\", \"inQueue\": " + isInQueue +"}";

			he.getResponseBody().write(response.getBytes());
			he.close();
		}
	}

	private void disconnect()
	{
		MC.getConnection().handleDisconnect(new SPacketDisconnect(new TextComponentString("Logged out by the 2bored2wait mod")));
	}
}
