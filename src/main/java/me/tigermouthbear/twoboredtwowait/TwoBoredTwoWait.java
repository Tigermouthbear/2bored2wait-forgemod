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

    public static final Config CONFIG = new Config();

    private static long placeInQueue = -1;
    private static long startTime = -1;
    private static long startPosition = -1;

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
            placeInQueue = Integer.valueOf(event.getMessage().getUnformattedText().split("Position in queue: ")[1]);

            if(startPosition == -1 && startTime == -1)
            {
                startPosition = placeInQueue;
                startTime = System.currentTimeMillis();
            }
        }
    }

    public static String getEta()
    {
        /*if(!Globals.MC.getCurrentServerData().serverIP.contains("2b2t.org")) return "None";

        long rate = (startPosition - placeInQueue)/(System.currentTimeMillis() - startTime);
        long milliseconds = rate*placeInQueue;
        int seconds = (int) (milliseconds / 1000) % 60 ;
        int minutes = (int) ((milliseconds / (1000*60)) % 60);
        int hours   = (int) ((milliseconds / (1000*60*60)) % 24);

        String time = "error";

        if(hours > 0)
        {
            time += hours + " hrs, ";
        }

        if(minutes > 0)
        {
            time += minutes + " min, ";
        }

        if(seconds > 0)
        {
            time += seconds + " sec";
        }

        return String.valueOf(rate);*/
        return "WIP";
    }

    public static String getPosition()
    {
        if(!Globals.MC.getCurrentServerData().serverIP.contains("2b2t.org")) return "None";
        return String.valueOf((int)placeInQueue);
    }

    public static void resetData()
    {
        placeInQueue = -1;
        startTime = -1;
        startPosition = -1;
    }
}
