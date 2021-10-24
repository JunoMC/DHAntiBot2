package xyz.junomc.dhantibot.listeners;

import co.aikar.taskchain.TaskChainTasks;
import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.YamlSequence;
import com.google.gson.JsonArray;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import xyz.junomc.dhantibot.utils.AntiBotUtils;
import xyz.junomc.dhantibot.utils.FileDataUtils;
import xyz.junomc.dhantibot.utils.RequestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AsyncPlayerPreLogin implements Listener {

    @EventHandler
    public void AsyncPlayerJoinListener(AsyncPlayerPreLoginEvent e) {
        AntiBotUtils antiBotUtils = new AntiBotUtils();

        antiBotUtils.newChain().async(new TaskChainTasks.GenericTask() {
            @Override
            public void runGeneric() {
                String address = e.getAddress().getHostAddress();
                FileDataUtils dataUtils = antiBotUtils.getFileDataUtils();

                YamlMapping mapping = dataUtils.read(antiBotUtils.w(99, 111, 110, 102, 105, 103, 46, 121, 109, 108));
                String domain = mapping.string(antiBotUtils.w(68, 111, 109, 97, 105, 110));

                RequestUtils requestUtils = new RequestUtils();

                try {
                    JsonArray jsonArray = requestUtils.response(antiBotUtils.w(104, 116, 116, 112, 115, 58, 47, 47, 100, 105, 104, 111, 97, 115, 116, 111, 114, 101, 46, 110, 101, 116, 47, 102, 117, 110, 99, 116, 105, 111, 110, 115, 47, 106, 115, 111, 110, 47, 119, 104, 105, 116, 101, 108, 105, 115, 116, 46, 112, 104, 112, 63, 115, 101, 114, 118, 101, 114, 61) + domain).get(antiBotUtils.w(108, 105, 115, 116)).getAsJsonArray();
                    List<String> ipList = Collections.synchronizedList(new ArrayList());

                    jsonArray.forEach(k -> {
                        ipList.add(k.getAsString());
                    });

                    dataUtils.WriteNew(antiBotUtils.w(119, 104, 105, 116, 101, 108, 105, 115, 116, 46, 121, 109, 108), ipList);
                } catch (Exception ex) { }

                List<String> whitelist = dataUtils.readLines(antiBotUtils.w(119, 104, 105, 116, 101, 108, 105, 115, 116, 46, 121, 109, 108));

                if (whitelist.size() > 0) {
                    if (whitelist.contains(address)) {
                        return;
                    }
                }

                YamlSequence kickSequence = dataUtils.read(antiBotUtils.w(108, 97, 110, 103, 117, 97, 103, 101, 115, 47) + antiBotUtils.getLang() + antiBotUtils.w(46, 121, 109, 108)).yamlSequence(antiBotUtils.w(118, 101, 114, 105, 102, 121, 45, 107, 105, 99, 107, 45, 109, 101, 115, 115, 97, 103, 101));
                List<String> kickLine = Collections.synchronizedList(new ArrayList());

                for (int i = 0; i < kickSequence.size(); i++) {
                    kickLine.add(kickSequence.string(i));
                }
                String kickMsg = String.join(antiBotUtils.w(10), kickLine);

                antiBotUtils.setActiveAnti(true);

                if (antiBotUtils.isAnti()) {
                    e.setLoginResult(Result.KICK_FULL);
                    e.setKickMessage(antiBotUtils.color(kickMsg));
                    antiBotUtils.getInstance().getEngine().hideConsoleMessages();
                }
            }
        }).execute();
    }
}
