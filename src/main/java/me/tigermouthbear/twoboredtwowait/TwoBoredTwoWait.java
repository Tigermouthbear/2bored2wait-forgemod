package me.tigermouthbear.twoboredtwowait;

import me.tigermouthbear.twoboredtwowait.webserver.WebServer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = TwoBoredTwoWait.MODID, name = TwoBoredTwoWait.NAME, version = TwoBoredTwoWait.VERSION)
public class TwoBoredTwoWait
{
    //TODO: Fix disconnecting and connecting in webserver/GetHandler
    public static final String MODID = "2bored2wait";
    public static final String NAME = "2bored2wait Mod";
    public static final String VERSION = "1.0";

    public static String placeInQueue = "None";
    //TODO: Add eta to the json in webserver/GetHandler
    //Change these to use the tab list maybe?
    public static String eta = "0";

    public interface Globals
    {
        Minecraft MC = Minecraft.getMinecraft();
    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
        new WebServer();
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event)
    {
        if(event.getMessage().getUnformattedText().startsWith("Position in queue: "))
        {
            placeInQueue = event.getMessage().getUnformattedText().split("Position in queue: ")[1];
        }
    }
}
