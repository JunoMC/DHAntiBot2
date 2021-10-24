package xyz.junomc.dhantibot.filters;

import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.Filter;
import xyz.junomc.dhantibot.DHAntiBot;
import xyz.junomc.dhantibot.utils.AntiBotUtils;
import xyz.junomc.dhantibot.utils.DiHoaUtils;

import java.util.Arrays;
import java.util.List;

public class LogFilter implements Filter {
    
    public Filter.Result checkMessage(final String message) {
        List<String> hidden = Arrays.asList("com.mojang.authlib.GameProfile", "Disconnecting com.mojang.authlib.GameProfile", "lost connection");

        for (final String s : hidden) {
            if (message.contains(s)) {
                new AntiBotUtils().getInstance().getEngine().addHiddenMsg();
                return Filter.Result.DENY;
            }
        }
        return Filter.Result.NEUTRAL;
    }
    
    public LifeCycle.State getState() {
        try {
            return LifeCycle.State.STARTED;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public void initialize() {
    }
    
    public boolean isStarted() {
        return true;
    }
    
    public boolean isStopped() {
        return false;
    }
    
    public void start() {
    }
    
    public void stop() {
    }
    
    public Filter.Result filter(final LogEvent event) {
        return this.checkMessage(event.getMessage().getFormattedMessage());
    }
    
    public Filter.Result filter(final Logger arg0, final Level arg1, final Marker arg2, final String message, final Object... arg4) {
        return this.checkMessage(message);
    }
    
    public Filter.Result filter(final Logger arg0, final Level arg1, final Marker arg2, final String message, final Object arg4) {
        return this.checkMessage(message);
    }
    
    public Filter.Result filter(final Logger arg0, final Level arg1, final Marker arg2, final Object message, final Throwable arg4) {
        return this.checkMessage(message.toString());
    }
    
    public Filter.Result filter(final Logger arg0, final Level arg1, final Marker arg2, final Message message, final Throwable arg4) {
        return this.checkMessage(message.getFormattedMessage());
    }
    
    public Filter.Result filter(final Logger arg0, final Level arg1, final Marker arg2, final String message, final Object arg4, final Object arg5) {
        return this.checkMessage(message);
    }
    
    public Filter.Result filter(final Logger arg0, final Level arg1, final Marker arg2, final String message, final Object arg4, final Object arg5, final Object arg6) {
        return this.checkMessage(message);
    }
    
    public Filter.Result filter(final Logger arg0, final Level arg1, final Marker arg2, final String message, final Object arg4, final Object arg5, final Object arg6, final Object arg7) {
        return this.checkMessage(message);
    }
    
    public Filter.Result filter(final Logger arg0, final Level arg1, final Marker arg2, final String message, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8) {
        return this.checkMessage(message);
    }
    
    public Filter.Result filter(final Logger arg0, final Level arg1, final Marker arg2, final String message, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9) {
        return this.checkMessage(message);
    }
    
    public Filter.Result filter(final Logger arg0, final Level arg1, final Marker arg2, final String message, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9, final Object arg10) {
        return this.checkMessage(message);
    }
    
    public Filter.Result filter(final Logger arg0, final Level arg1, final Marker arg2, final String message, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9, final Object arg10, final Object arg11) {
        return this.checkMessage(message);
    }
    
    public Filter.Result filter(final Logger arg0, final Level arg1, final Marker arg2, final String message, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9, final Object arg10, final Object arg11, final Object arg12) {
        return this.checkMessage(message);
    }
    
    public Filter.Result filter(final Logger arg0, final Level arg1, final Marker arg2, final String message, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9, final Object arg10, final Object arg11, final Object arg12, final Object arg13) {
        return this.checkMessage(message);
    }
    
    public Filter.Result getOnMatch() {
        return Filter.Result.NEUTRAL;
    }
    
    public Filter.Result getOnMismatch() {
        return Filter.Result.NEUTRAL;
    }
}