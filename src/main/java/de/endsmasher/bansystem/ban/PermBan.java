package de.endsmasher.bansystem.ban;

import de.endsmasher.bansystem.Ocelot;
import de.endsmasher.bansystem.utils.BanScreenStrings;
import de.endsmasher.bansystem.utils.ConfigHolder;
import de.endsmasher.bansystem.utils.PlayerBan;
import de.endsmasher.bansystem.utils.PlayerLogall;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Date;

public class PermBan implements CommandExecutor {

    private Ocelot plugin;


    public PermBan(Ocelot plugin) {
        this.plugin = plugin;
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        DriveService service = plugin.getBanService();
        DriveService servicelog = plugin.getLogService();
        DriveService servicel = plugin.getTeamLogService();

        String prefix = "§7[§6Ocelot§7] ";


        if (!sender.hasPermission("Ocelot.Team") || !sender.hasPermission(ConfigHolder.Configs.CONFIG.getConfig().getString("permissions.PermBan"))) {

            sender.sendMessage(prefix + "You don't have enough permissions");

            return true;
        }
        if (args.length == 2) {


            Query queryname = new Query()
                    .addEq()
                    .setField("name")
                    .setValue(args[0])
                    .close()
                    .build();

            PlayerLogall playerLogallname = servicelog.getReader().readObject(queryname, PlayerLogall.class);



            if (!servicelog.getReader().containsObject(queryname)) {

                sender.sendMessage(prefix + "Unknown Player " + args[0]);

                return true;

            }

            Query query = new Query()
                    .addEq()
                    .setField("id")
                    .setValue(playerLogallname.getId())
                    .close()
                    .build();

            PlayerLogall playerLogall = servicelog.getReader().readObject(query, PlayerLogall.class);

            if (servicel.getReader().containsObject(query)) {

                sender.sendMessage(prefix + "You are not permitted to ban " + args[0]);

                return true;

            }

            if (service.getReader().containsObject(query)) {

                sender.sendMessage(prefix + "The Player " + args[0] + " is already banned");

                return true;

            }


                service.getWriter().write(new PlayerBan(playerLogall.getId()
                        ,sender.getName()
                        , args[1]
                        , "-1"
                        , -1,
                        new Date().getTime()));

            sender.sendMessage(prefix + "Successful banned " + args[0] + " for " + args[1]);


            if (ConfigHolder.Configs.CONFIG.getConfig().getBoolean("settings.BroadcastBan/UnbanMessages") == true) {

                Bukkit.broadcastMessage(prefix + sender.getName() + " banned " + args[0] + " for " + args[1]);

            }




                PlayerBan playerBan = service.getReader().readObject(query, PlayerBan.class);

                if (!Bukkit.getOnlinePlayers().contains("Endsmasher")) {
                    return true;
            }
                Bukkit.getPlayer(playerLogall.getId()).kickPlayer(BanScreenStrings.PBanline1

                        + BanScreenStrings.PBanline2
                        .replace("{REASON}", playerBan.getReason())
                        .replace("{BANDATE}", playerBan.getPrettyBanDate())
                        .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                        + BanScreenStrings.PBanline3
                        .replace("{REASON}", playerBan.getReason())
                        .replace("{BANDATE}", playerBan.getPrettyBanDate())
                        .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                        + BanScreenStrings.PBanline4
                        .replace("{REASON}", playerBan.getReason())
                        .replace("{BANDATE}", playerBan.getPrettyBanDate())
                        .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                        + BanScreenStrings.PBanline5
                        .replace("{REASON}", playerBan.getReason())
                        .replace("{BANDATE}", playerBan.getPrettyBanDate())
                        .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                        + BanScreenStrings.PBanline6
                        .replace("{REASON}", playerBan.getReason())
                        .replace("{BANDATE}", playerBan.getPrettyBanDate())
                        .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                        + BanScreenStrings.PBanline7
                        .replace("{REASON}", playerBan.getReason())
                        .replace("{BANDATE}", playerBan.getPrettyBanDate())
                        .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                        + BanScreenStrings.PBanline8
                        .replace("{REASON}", playerBan.getReason())
                        .replace("{BANDATE}", playerBan.getPrettyBanDate())
                        .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                        + BanScreenStrings.PBanline9
                        .replace("{REASON}", playerBan.getReason())
                        .replace("{BANDATE}", playerBan.getPrettyBanDate())
                        .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                        + BanScreenStrings.PBanline10
                        .replace("{REASON}", playerBan.getReason())
                        .replace("{BANDATE}", playerBan.getPrettyBanDate())
                        .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                        + BanScreenStrings.PBanline11
                        .replace("{REASON}", playerBan.getReason())
                        .replace("{BANDATE}", playerBan.getPrettyBanDate())
                        .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                        + BanScreenStrings.PBanline12
                        .replace("{REASON}", playerBan.getReason())
                        .replace("{BANDATE}", playerBan.getPrettyBanDate())
                        .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                        + BanScreenStrings.PBanline13
                        .replace("{REASON}", playerBan.getReason())
                        .replace("{BANDATE}", playerBan.getPrettyBanDate())
                        .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                        + BanScreenStrings.PBanline14
                        .replace("{REASON}", playerBan.getReason())
                        .replace("{BANDATE}", playerBan.getPrettyBanDate())
                        .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                        + BanScreenStrings.PBanline15
                        .replace("{REASON}", playerBan.getReason())
                        .replace("{BANDATE}", playerBan.getPrettyBanDate())
                        .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate()));


        } else sender.sendMessage(prefix + "Please use /ban <Player> <Reason> ");

        return false;
    }
}
