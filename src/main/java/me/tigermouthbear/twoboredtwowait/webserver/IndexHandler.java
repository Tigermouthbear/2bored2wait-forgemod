package me.tigermouthbear.twoboredtwowait.webserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import me.tigermouthbear.twoboredtwowait.TwoBoredTwoWait;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author Tigermouthbear
 * 1/16/20
 */

public class IndexHandler implements HttpHandler, TwoBoredTwoWait.Globals
{
	static final ResourceLocation indexLocation = new ResourceLocation("2bored2wait", "index.html");
	private File index = File.createTempFile("index-temp", "");

	IndexHandler() throws IOException
	{
		FileUtils.copyInputStreamToFile(MC.getResourceManager().getResource(indexLocation).getInputStream(), index);
	}

	@Override
	public void handle(HttpExchange he) throws IOException
	{
		he.sendResponseHeaders(200, index.length());
		Files.copy(index.toPath(), he.getResponseBody());
		he.close();
	}
}
