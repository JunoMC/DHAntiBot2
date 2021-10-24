package xyz.junomc.dhantibot;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import com.amihaiemil.eoyaml.YamlMapping;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.junomc.dhantibot.filters.EngineInterface;
import xyz.junomc.dhantibot.filters.NewEngine;
import xyz.junomc.dhantibot.filters.OldEngine;
import xyz.junomc.dhantibot.listeners.AsyncPlayerPreLogin;
import xyz.junomc.dhantibot.utils.AntiBotUtils;
import xyz.junomc.dhantibot.utils.DiHoaUtils;
import xyz.junomc.dhantibot.utils.FileDataUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public final class DHAntiBot extends JavaPlugin {
    private static DHAntiBot instance;

    public Logger log;
    public boolean is19Server;
    public boolean is13Server;
    public boolean oldEngine;

    private EngineInterface eng;

    private int joinCount = 0;
    private long lastJoin = 0;

    private boolean activeAnti = false;
    private long timeActive = 0;

    private boolean isInActive = false;

    public static DHAntiBot getInstance() {
        return instance;
    }

    public String getLang() {
        YamlMapping mapping = getFileDataUtils().read(w(99, 111, 110, 102, 105, 103, 46, 121, 109, 108));
        return mapping.string(w(76, 97, 110, 103, 117, 97, 103, 101));
    }

    public int getJoinCount() {
        return joinCount;
    }

    public long getLastJoin() {
        return lastJoin;
    }

    public int setJoinCount(int i) {
        return joinCount = i;
    }

    public long setLastJoin(long i) {
        return lastJoin = i;
    }

    public long getTimeActive() {
        return timeActive;
    }

    public long setTimeActive(long i) {
        return timeActive = i;
    }

    public boolean isAnti() {
        return activeAnti;
    }

    public boolean setActiveAnti(boolean bool) {
        return activeAnti = bool;
    }

    private TaskChainFactory taskChainFactory;

    public <T> TaskChain<T> newChain() {
        return taskChainFactory.newChain();
    }

    enum ConsoleLevel {
        DEFAULT(getInstance().w(38, 114)),
        INFO(getInstance().w(38, 98)),
        WARNING(getInstance().w(38, 101)),
        SUCCESS(getInstance().w(38, 97)),
        DANGER(getInstance().w(38, 99));

        private String lvl;

        ConsoleLevel(String lv) {
            this.lvl = lv;
        }

        String getColor() {
            return this.lvl;
        }
    }

    enum YamlFile {
        CONFIG(1), LANGUAGE(2);

        private List<String> yml;

        private int code;

        YamlFile(int code) {
            this.code = code;
        }

        List<String> getYml() {
            this.yml = Collections.synchronizedList(new ArrayList<>());
            switch (this.code) {
                case 1:
                    yml.addAll(Arrays.asList(
                            getInstance().w(80, 114, 101, 102, 105, 120, 58, 32, 39, 38, 102, 91, 38, 99, 38, 108, 68, 72, 45, 65, 110, 116, 105, 66, 111, 116, 38, 102, 93, 32, 39),
                            getInstance().w(),
                            getInstance().w(35, 84, 111, 32, 103, 101, 116, 32, 65, 80, 73, 75, 101, 121, 32, 45, 62, 32, 104, 116, 116, 112, 115, 58, 47, 47, 100, 105, 104, 111, 97, 115, 116, 111, 114, 101, 46, 99, 111, 109, 47),
                            getInstance().w(76, 105, 99, 101, 110, 115, 101, 58, 32, 39, 73, 110, 112, 117, 116, 32, 121, 111, 117, 114, 32, 65, 80, 73, 75, 101, 121, 32, 104, 101, 114, 101, 39),
                            getInstance().w(68, 111, 109, 97, 105, 110, 58, 32, 39, 73, 110, 112, 117, 116, 32, 121, 111, 117, 114, 32, 115, 101, 114, 118, 101, 114, 32, 100, 111, 109, 97, 105, 110, 39),
                            getInstance().w(),
                            getInstance().w(76, 97, 110, 103, 117, 97, 103, 101, 58, 32, 39, 118, 105, 45, 86, 78, 39)
                    ));
                    break;
            }

            return yml;
        }
    }

    public FileDataUtils getFileDataUtils() {
        return new FileDataUtils();
    }

    public String getPrefix() {
        YamlMapping mapping = getFileDataUtils().read(w(99, 111, 110, 102, 105, 103, 46, 121, 109, 108));
        return mapping.string(w(80, 114, 101, 102, 105, 120));
    }

    public EngineInterface getEngine() {
        return eng;
    }

    private void hookAutoSaveWorld() {
        if (!(Bukkit.getServer().getPluginManager().getPlugin(w(65, 117, 116, 111, 83, 97, 118, 101, 87, 111, 114, 108, 100)) == null)) {
            File aswf = new File(Bukkit.getPluginManager().getPlugin(w(65, 117, 116, 111, 83, 97, 118, 101, 87, 111, 114, 108, 100)).getDataFolder().getParentFile(), w(92, 65, 117, 116, 111, 83, 97, 118, 101, 87, 111, 114, 108, 100, 92, 99, 111, 110, 102, 105, 103, 46, 121, 109, 108));

            if (aswf.exists()) {
                YamlConfiguration yamlConfiguration = new YamlConfiguration();

                try {
                    yamlConfiguration.load(aswf);
                } catch (Exception exception) { }

                if (yamlConfiguration.getBoolean(w(110, 101, 116, 119, 111, 114, 107, 119, 97, 116, 99, 104, 101, 114, 46, 109, 97, 105, 110, 116, 104, 114, 101, 97, 100, 110, 101, 116, 97, 99, 99, 101, 115, 115, 46, 119, 97, 114, 110))) {
                    yamlConfiguration.set(w(110, 101, 116, 119, 111, 114, 107, 119, 97, 116, 99, 104, 101, 114, 46, 109, 97, 105, 110, 116, 104, 114, 101, 97, 100, 110, 101, 116, 97, 99, 99, 101, 115, 115, 46, 119, 97, 114, 110), Boolean.valueOf(false));
                    try {
                        yamlConfiguration.save(aswf);
                    } catch (Exception exception) {}
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), w(97, 115, 119, 32, 114, 101, 108, 111, 97, 100));
                }
            }
        }
    }

    private List<String> brand() {
        return Arrays.asList(
                w(),
                w(38, 102, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45),
                w(32, 32, 32, 32, 95, 32, 32, 32, 32, 32, 32, 32, 95, 32, 32, 32, 95, 32, 95, 95, 95, 32, 32, 32, 32, 32, 32, 95),
                w(32, 32, 32, 47, 95, 92, 32, 32, 95, 32, 95, 124, 32, 124, 95, 40, 95, 41, 32, 95, 32, 41, 32, 95, 95, 95, 124, 32, 124, 95, 32),
                w(32, 32, 47, 32, 95, 32, 92, 124, 32, 39, 32, 92, 32, 32, 95, 124, 32, 124, 32, 95, 32, 92, 47, 32, 95, 32, 92, 32, 32, 95, 124),
                w(32, 47, 95, 47, 32, 92, 95, 92, 95, 124, 124, 95, 92, 95, 95, 124, 95, 124, 95, 95, 95, 47, 92, 95, 95, 95, 47, 92, 95, 95, 124),
                w(),
                w(32, 38, 102, 86, 101, 114, 115, 105, 111, 110, 58, 32, 38, 101, 118) + version() + w(38, 102, 32, 45, 32, 99, 111, 100, 101, 100, 32, 98, 121, 32, 74, 117, 110, 111, 77, 67),
                w(32, 38, 102, 83, 101, 114, 118, 101, 114, 32, 118, 101, 114, 115, 105, 111, 110, 58, 32, 38, 101) + getMcVersion(),
                w(32, 38, 102, 73, 80, 118, 52, 58, 32, 38, 101) + new DiHoaUtils().getServerIP(),
                w(32, 38, 102, 87, 101, 98, 115, 105, 116, 101, 58, 32, 38, 101, 119, 119, 119, 46, 100, 105, 104, 111, 97, 115, 116, 111, 114, 101, 46, 99, 111, 109),
                w(38, 102, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45),
                w()
        );
    }

    private String version() {
        return instance.getDescription().getVersion();
    }

    private void messages(ConsoleLevel level, String prefix, String... msgs) {
        AntiBotUtils abutils = new AntiBotUtils();
        for (String msg : msgs) {
            Bukkit.getConsoleSender().sendMessage(abutils.color(prefix + level.getColor() + msg));
        }
    }

    private void messages(ConsoleLevel level, String prefix, List<String> msgs) {
        AntiBotUtils abutils = new AntiBotUtils();
        for (String msg : msgs) {
            Bukkit.getConsoleSender().sendMessage(abutils.color(prefix + level.getColor() + msg));
        }
    }

    private void registerEvents(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, getInstance());
        }
    }

    private String getMcVersion() {
        final String[] serverVersion = Bukkit.getBukkitVersion().split(w(45));
        final String version = serverVersion[0];

        if (version.matches(w(40, 49, 46, 55, 46, 49, 48, 124, 49, 46, 55, 46, 57, 124, 49, 46, 55, 46, 53, 124, 49, 46, 55, 46, 50, 124, 49, 46, 56, 46, 56, 124, 49, 46, 56, 46, 51, 124, 49, 46, 56, 46, 52, 124, 49, 46, 56, 41))) {
            is19Server = false;
            is13Server = false;
            oldEngine = true;
        } else if (version.matches(w(40, 49, 46, 57, 124, 49, 46, 57, 46, 49, 124, 49, 46, 57, 46, 51, 124, 49, 46, 57, 46, 52, 124, 49, 46, 49, 48, 124, 49, 46, 49, 48, 46, 49, 124, 49, 46, 49, 48, 46, 50, 124, 49, 46, 49, 49, 124, 49, 46, 49, 49, 46, 49, 124, 49, 46, 49, 49, 46, 50, 41))) {
            oldEngine = true;
            is19Server = true;
            is13Server = false;
        } else {
            is13Server = true;
            is19Server = true;
            oldEngine = false;
        }

        if (oldEngine) {
            eng = new OldEngine();
        } else {
            eng = new NewEngine();
        }

        getEngine().hideConsoleMessages();

        return version;
    }

    @Override
    public void onEnable() {
        instance = this;
        taskChainFactory = BukkitTaskChainFactory.create(this);

        Bukkit.getScheduler().runTaskLater(instance, () -> {
            log = this.getLogger();

            is19Server = true;
            is13Server = false;
            oldEngine = true;

            hookAutoSaveWorld();

            DiHoaUtils diHoaUtils = new DiHoaUtils();

            getFileDataUtils().createFile(w(99, 111, 110, 102, 105, 103, 46, 121, 109, 108), YamlFile.CONFIG.getYml());
            getFileDataUtils().createFile(w(119, 104, 105, 116, 101, 108, 105, 115, 116, 46, 121, 109, 108));

            getFileDataUtils().createFile(w(108, 97, 110, 103, 117, 97, 103, 101, 115, 47) + getLang() + w(46, 121, 109, 108),
                    w(118, 101, 114, 105, 102, 121, 45, 107, 105, 99, 107, 45, 109, 101, 115, 115, 97, 103, 101, 58),
                    w(32, 32, 45, 32, 39, 38, 99, 38, 108, 77, 73, 78, 69, 67, 82, 65, 70, 84, 32, 83, 69, 82, 86, 69, 82, 39),
                    w(32, 32, 45, 32, 39, 38, 55, 80, 108, 101, 97, 115, 101, 32, 118, 101, 114, 105, 102, 121, 32, 121, 111, 117, 114, 32, 97, 99, 99, 111, 117, 110, 116, 32, 97, 116, 58, 32, 38, 102, 119, 119, 119, 46, 100, 105, 104, 111, 97, 115, 116, 111, 114, 101, 46, 110, 101, 116, 39)
            );

            YamlMapping mapping = getFileDataUtils().read(w(99, 111, 110, 102, 105, 103, 46, 121, 109, 108));
            String license = mapping.string(w(76, 105, 99, 101, 110, 115, 101));

            messages(ConsoleLevel.INFO, w(), brand());
            diHoaUtils.isIPv4(license);

            JsonObject checkLicense = diHoaUtils.checkLicense(license);

            if (checkLicense.get(w(115, 116, 97, 116, 117, 115)).getAsString().equals(w(101, 114, 114, 111, 114))) {
                messages(ConsoleLevel.INFO, w(),
                        w(32, 38, 99) + checkLicense.get(w(109, 101, 115, 115, 97, 103, 101)).getAsString());
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }

            if (checkLicense.get(w(105, 115, 95, 117, 115, 105, 110, 103)).getAsBoolean()) {
                messages(ConsoleLevel.INFO, w(),
                        w(32, 38, 99, 89, 111, 117, 114, 32, 108, 105, 99, 101, 110, 115, 101, 32, 107, 101, 121, 32, 105, 115, 32, 97, 108, 114, 101, 97, 100, 121, 32, 105, 110, 32, 117, 115, 101, 33));
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }

            if (!checkLicense.get(w(115, 101, 114, 118, 101, 114)).getAsJsonObject().get(w(105, 112, 118, 52)).getAsString().equals(diHoaUtils.getServerIP())) {
                messages(ConsoleLevel.INFO, w(),
                        w(32, 38, 99, 89, 111, 117, 114, 32, 115, 101, 114, 118, 101, 114, 32, 100, 111, 101, 115, 32, 110, 111, 116, 32, 104, 97, 118, 101, 32, 112, 101, 114, 109, 105, 115, 115, 105, 111, 110, 32, 116, 111, 32, 117, 115, 101, 32, 68, 72, 45, 65, 110, 116, 105, 66, 111, 116, 33));
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }

            JsonObject activeLicense = diHoaUtils.activeLicense(license);

            if (activeLicense.get(w(115, 116, 97, 116, 117, 115)).getAsString().equals(w(101, 114, 114, 111, 114))) {
                messages(ConsoleLevel.INFO, w(),
                        w(32, 38, 99) + checkLicense.get(w(109, 101, 115, 115, 97, 103, 101)).getAsString());
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }

            isInActive = true;
            registerEvents(new AsyncPlayerPreLogin());
        }, 5);
    }

    @Override
    public void onDisable() {
        if (isInActive) {
            YamlMapping mapping = getFileDataUtils().read(w(99, 111, 110, 102, 105, 103, 46, 121, 109, 108));
            String license = mapping.string(w(76, 105, 99, 101, 110, 115, 101));
            DiHoaUtils diHoaUtils = new DiHoaUtils();
            diHoaUtils.inactiveLicense(license);
        }
    }

    public String w(Integer... a) {
        List<Integer> args = Arrays.asList(a);
        byte[] c = new byte[args.size()];
        for (int i = 0; i < args.size(); i++) {
            c[i] = Byte.valueOf(String.valueOf(a[i]));
        }
        return new String(c);
    }
}
