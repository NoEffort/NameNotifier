package me.noeffort.namenotifier.actionbar;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Actionbar {

    public static String nmsver;
    public static boolean works = true;
    static boolean useOldMethods = false;
    public static Plugin plugin;

    public static void sendActionbar(Player player, String message) {
        if (!player.isOnline()) {
            return;
        }
        ActionbarMessageEvent actionBarMessageEvent = new ActionbarMessageEvent(player, message);
        Bukkit.getPluginManager().callEvent(actionBarMessageEvent);
        if (actionBarMessageEvent.isCancelled())
            return;

        if (nmsver.startsWith("v1_12_")) {
            sendActionbar112(player, message);
        }
    }

    private static void sendActionbar112(Player player, String message) {
        if (!player.isOnline()) {
            return;
        }

        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
            Object craftPlayer = craftPlayerClass.cast(player);
            Object ppoc;
            Class<?> classChat = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
            Class<?> classPacket = Class.forName("net.minecraft.server." + nmsver + ".Packet");
            Class<?> classText = Class.forName("net.minecraft.server." + nmsver + ".ChatComponentText");
            Class<?> classBase = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
            Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + nmsver + ".ChatMessageType");
            Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
            Object chatMessageType = null;
            for (Object obj : chatMessageTypes) {
                if (obj.toString().equals("GAME_INFO")) {
                    chatMessageType = obj;
                }
            }
            Object o = classText.getConstructor(new Class<?>[]{String.class}).newInstance(message);
            ppoc = classChat.getConstructor(new Class<?>[]{classBase, chatMessageTypeClass}).newInstance(o, chatMessageType);
            Method m1 = craftPlayerClass.getDeclaredMethod("getHandle");
            Object h = m1.invoke(craftPlayer);
            Field f1 = h.getClass().getDeclaredField("playerConnection");
            Object pc = f1.get(h);
            Method m5 = pc.getClass().getDeclaredMethod("sendPacket", classPacket);
            m5.invoke(pc, ppoc);
        } catch (Exception ex) {
            ex.printStackTrace();
            works = false;
        }
    }

    public static void sendActionbar(final Player player, final String message, int duration) {
        sendActionbar(player, message);

        if (duration >= 0) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendActionbar(player, "");
                }
            }.runTaskLater(plugin, duration + 1);
        }

        while (duration > 40) {
            duration -= 40;
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendActionbar(player, message);
                }
            }.runTaskLater(plugin, (long) duration);
        }
    }

    public static void sendActionbarToAllPlayers(String message) {
        sendActionbarToAllPlayers(message, -1);
    }

    public static void sendActionbarToAllPlayers(String message, int duration) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            sendActionbar(p, message, duration);
        }
    }

}