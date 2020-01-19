package me.tigermouthbear.twoboredtwowait.webserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import me.tigermouthbear.twoboredtwowait.TwoBoredTwoWait;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLLog;

import java.io.IOException;

/**
 * @author Tigermouthbear
 * 1/16/20
 */

public class GetHandler implements HttpHandler, TwoBoredTwoWait.Globals
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
			he.getResponseBody().write(getUpdate().getBytes());
			he.close();
			return;
		}

		if(he.getRequestHeaders().get("Xpassword").get(0).equals(password))
		{
			if(uri.contains("start"))
			{
				he.sendResponseHeaders(200, 0);
				he.close();
				connect();
			}
			else if(uri.contains("stop"))
			{
				he.sendResponseHeaders(200, 0);
				he.close();
				disconnect();
				TwoBoredTwoWait.resetData();
			}
		}
		else
		{
			he.sendResponseHeaders(404, 0);
			he.close();
		}
	}

	private void connect()
	{
		//Make sure that player isn't connected to a world
		disconnect();

		//TODO: Fix this!!
		MC.player.closeScreen();
		FMLLog.log.info("connecting...");
		MC.displayGuiScreen(new GuiConnecting(new GuiMainMenu(), MC, "2b2t.org", 25565));
	}

	private void disconnect()
	{
		MC.getConnection().handleDisconnect(new SPacketDisconnect(new TextComponentString("Logged out by the 2bored2wait mod")));
	}

	private String getUpdate()
	{
		boolean isInQueue;

		if(MC.getCurrentServerData() != null) isInQueue = MC.getCurrentServerData().serverIP.contains("2b2t.org");
		else isInQueue = false;

		return "{\"username\": \""+ MC.player.getDisplayNameString() +"\",\"place\": \""+ TwoBoredTwoWait.getPosition() +"\",\"ETA\": \""+ TwoBoredTwoWait.getEta() +"\", \"inQueue\": " + isInQueue +"}";
	}
}