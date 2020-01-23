package me.tigermouthbear.twoboredtwowait;

import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * @author Tigermouthbear
 * 1/22/20
 */

public class GetRequestEvent
{
	public static class Start extends Event
	{
	}

	public static class Stop extends Event
	{
	}

	public static class Update extends Event
	{
		private String data;

		public String getData()
		{
			return data;
		}

		public void setData(String data)
		{
			this.data = data;
		}
	}
}
