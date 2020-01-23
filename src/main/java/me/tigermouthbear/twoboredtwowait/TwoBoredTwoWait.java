package me.tigermouthbear.twoboredtwowait;

import me.tigermouthbear.twoboredtwowait.webserver.WebServer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = TwoBoredTwoWait.MODID, name = TwoBoredTwoWait.NAME, version = TwoBoredTwoWait.VERSION)
public class TwoBoredTwoWait
{
    public static final String MODID = "2bored2wait";
    public static final String NAME = "2bored2wait Mod";
    public static final String VERSION = "1.0";

    public static final Config CONFIG = new Config();
    public static final Minecraft MC = Minecraft.getMinecraft();

    private static int placeInQueue = -1;
    private static long startTime = -1;
    private static int startPosition = -1;

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
        new WebServer();
    }

    private static String getEta()
    {
        if(!MC.getCurrentServerData().serverIP.contains("2b2t.org")) return "None";

        if((startPosition - placeInQueue) == 0) return "Calculating...";

        long rate = ((System.nanoTime() - startTime)/1000000)/(startPosition - placeInQueue);
        long milliseconds = (int) (rate*placeInQueue);
        int seconds = (int) (milliseconds / 1000) % 60 ;
        int minutes = (int) ((milliseconds / (1000*60)) % 60);
        int hours   = (int) ((milliseconds / (1000*60*60)) % 24);

        String time = "";

        if(hours > 0)
        {
            time += hours + "h ";
        }

        if(minutes > 0)
        {
            time += minutes + "m ";
        }

        return (time);
    }

    private static String getPosition()
    {
        if(!MC.getCurrentServerData().serverIP.contains("2b2t.org")) return "None";
        return String.valueOf(placeInQueue);
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
                startTime = System.nanoTime();
            }
        }
    }

    @SubscribeEvent
    public void onStart(GetRequestEvent.Start event)
    {
        MC.addScheduledTask(() -> MC.displayGuiScreen(new GuiConnecting(new GuiMainMenu(), MC, new ServerData("2b2t", "2b2t.org", false))));
    }

    @SubscribeEvent
    public void onStop(GetRequestEvent.Stop event)
    {
        if(MC.world == null) return;
        MC.getConnection().handleDisconnect(new SPacketDisconnect(new TextComponentString("Logged out by the 2bored2wait mod")));

        //reset data
        placeInQueue = -1;
        startTime = -1;
        startPosition = -1;
    }

    @SubscribeEvent
    public void onUpdate(GetRequestEvent.Update event)
    {
        boolean isInQueue;

        if(MC.getCurrentServerData() != null) isInQueue = MC.getCurrentServerData().serverIP.contains("2b2t.org");
        else isInQueue = false;

        event.setData("{\"username\": \""+ MC.player.getDisplayNameString() +"\",\"place\": \""+ getPosition() +"\",\"ETA\": \""+ getEta() +"\", \"inQueue\": " + isInQueue +"}");
    }
}
