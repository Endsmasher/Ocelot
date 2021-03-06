package de.endsmasher.bansystem;

import de.endsmasher.bansystem.ban.*;
import de.endsmasher.bansystem.mute.Mute;
import de.endsmasher.bansystem.mute.MuteListener;
import de.endsmasher.bansystem.mute.UnMute;
import de.endsmasher.bansystem.register.ListLogged;
import de.endsmasher.bansystem.register.ListTeam;
import de.endsmasher.bansystem.register.Register;
import de.endsmasher.bansystem.register.Remove;
import de.endsmasher.bansystem.utils.*;
import de.endsmasher.bansystem.warn.UnWarnPlayer;
import de.endsmasher.bansystem.warn.WarnPlayer;
import net.endrealm.realmdrive.factory.DriveServiceFactory;
import net.endrealm.realmdrive.interfaces.ConversionHandler;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.utils.DriveSettings;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ocelot extends JavaPlugin {

    private DriveService banService;
    public DriveService getBanService() {
        return banService;
    }

    private DriveService warnService;
    public DriveService getWarnService() { return warnService;}

    private DriveService muteService;
    public DriveService getMuteService() {return muteService;}

    public DriveService TeamLogService;
    public DriveService getTeamLogService() {return TeamLogService;}

    public DriveService LogService;
    public DriveService getLogService() {return LogService;}

    public DriveService WarncountService;
    public DriveService getWarncountService() {return WarncountService;}

    public static Ocelot instance;

    private ConfigHolder configHolder;

    public static Ocelot getInstance() {
        return instance;
    }

    @Override

    public void onEnable() {

        instance = this;

        configHolder = new ConfigHolder(this);

        BanScreenStrings.init();

        String hostUrl = ConfigHolder.Configs.CONFIG.getConfig().getString("settings.hosturl");
        String password = ConfigHolder.Configs.CONFIG.getConfig().getString("settings.password");
        String username = ConfigHolder.Configs.CONFIG.getConfig().getString("settings.username");

        // To save the Bans
        DriveSettings settings = DriveSettings.builder()
                .type(DriveSettings.BackendType.MONGO_DB)
                .hostURL(hostUrl)
                .username(username)
                .password(password)
                .database("Ocelot")
                .table("general")
                .build();
        banService = new DriveServiceFactory().getDriveService(settings);
        ConversionHandler conversion = banService.getConversionHandler();
        conversion.registerClasses(PlayerBan.class);



        // To save the Warns
        DriveSettings settings1 = DriveSettings.builder()
                .type(DriveSettings.BackendType.MONGO_DB)
                .hostURL(hostUrl)
                .username(username)
                .password(password)
                .database("Ocelot")
                .table("warn")
                .build();
        warnService = new DriveServiceFactory().getDriveService(settings1);
        ConversionHandler conversionWarn = warnService.getConversionHandler();
            WarncountService = new DriveServiceFactory().getDriveService(settings1);
            ConversionHandler conversionWarnCount = WarncountService.getConversionHandler();
        conversionWarn.registerClasses(PlayerWarn.class);
        conversionWarnCount.registerClasses(PlayerWarnCount.class);



        // To save the Mutes
        DriveSettings settingsmute = DriveSettings.builder()
                .type(DriveSettings.BackendType.MONGO_DB)
                .hostURL(hostUrl)
                .username(username)
                .password(password)
                .database("Ocelot")
                .table("mute")
                .build();

        muteService = new DriveServiceFactory().getDriveService(settingsmute);
        ConversionHandler conversionmute = muteService.getConversionHandler();
        conversionmute.registerClasses(PlayerMute.class);



        //To save the logged players
        DriveSettings settingslog = DriveSettings.builder()
                .type(DriveSettings.BackendType.MONGO_DB)
                .hostURL(hostUrl)
                .username(username)
                .password(password)
                .database("Ocelot")
                .table("log")
                .build();
         TeamLogService = new DriveServiceFactory().getDriveService(settingslog);
         ConversionHandler conversionlogteam = TeamLogService.getConversionHandler();
         conversionlogteam.registerClasses(PlayerLog.class);



        //To register new players
        DriveSettings settingsl = DriveSettings.builder()
                .type(DriveSettings.BackendType.MONGO_DB)
                .hostURL(hostUrl)
                .username(username)
                .password(password)
                .database("Ocelot")
                .table("logNew")
                .build();
        LogService = new DriveServiceFactory().getDriveService(settingsl);
        ConversionHandler conversionlog = LogService.getConversionHandler();
        conversionlog.registerClasses(PlayerLogall.class);



        // Register the Commands

        getCommand("banip").setExecutor(new BanIp(this));
        getCommand("ban").setExecutor(new PermBan(this));
        getCommand("tempban").setExecutor(new TempBan(this));
        getCommand("unban").setExecutor(new Unban(this));

        getCommand("warn").setExecutor(new WarnPlayer(this));
        getCommand("unwarn").setExecutor(new UnWarnPlayer(this));
        getCommand("history").setExecutor(new History(this));

        getCommand("mute").setExecutor(new Mute(this));
        getCommand("unmute").setExecutor(new UnMute(this));

        getCommand("teamcheck").setExecutor(new ListLogged(this));
        getCommand("register").setExecutor(new Register(this));
        getCommand("remove").setExecutor(new Remove(this));
        getCommand("teamlist").setExecutor(new ListTeam(this));


        // Register the Events

        Bukkit.getPluginManager().registerEvents(new CommandListener(), this);
        Bukkit.getPluginManager().registerEvents(new LoginListener(this), this);
        Bukkit.getPluginManager().registerEvents(new MuteListener(this), this);
        Bukkit.getPluginManager().registerEvents(new de.endsmasher.bansystem.lognew.LoginListener(this), this);

    }

    @Override
    public void onDisable() {

    }

    public ConfigHolder getConfigHolder() {
        return configHolder;
    }
}
