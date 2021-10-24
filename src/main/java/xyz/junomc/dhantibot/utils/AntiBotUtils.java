package xyz.junomc.dhantibot.utils;

import co.aikar.taskchain.TaskChain;
import net.md_5.bungee.api.ChatColor;
import xyz.junomc.dhantibot.DHAntiBot;

public class AntiBotUtils {
    public DHAntiBot getInstance() {
        return DHAntiBot.getInstance();
    }

    public String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public FileDataUtils getFileDataUtils() {
        return getInstance().getFileDataUtils();
    }

    public String getLang() {
        return getInstance().getLang();
    }

    public int getJoinCount() {
        return getInstance().getJoinCount();
    }

    public long getLastJoin() {
        return getInstance().getLastJoin();
    }

    public int setJoinCount(int i) {
        return getInstance().setJoinCount(i);
    }

    public int addJoinCount(int i) {
        return getInstance().setJoinCount(getJoinCount() + i);
    }

    public long setLastJoin(long i) {
        return getInstance().setLastJoin(i);
    }

    public boolean isAnti() {
        return getInstance().isAnti();
    }

    public boolean setActiveAnti(boolean bool) {
        return getInstance().setActiveAnti(bool);
    }

    public long getTimeActive() {
        return getInstance().getTimeActive();
    }

    public long setTimeActive(long i) {
        return getInstance().setTimeActive(i);
    }

    public <T> TaskChain<T> newChain() {
        return getInstance().newChain();
    }

    public String w(Integer... a) {
        return getInstance().w(a);
    }
}