package de.endsmasher.bansystem.lognew;

import de.endsmasher.bansystem.BanSystem;
import de.endsmasher.bansystem.utils.PlayerLogall;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Date;

public class LoginListener implements Listener {

        private BanSystem plugin;

    public LoginListener(BanSystem plugin) {
        this.plugin = plugin;
    }

        @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerLoginEvent event) {
        DriveService service = plugin.getlService();
        Player player = event.getPlayer();
        Query query = new Query()
                .addEq()
                .setField("id")
                .setValue(player.getUniqueId().toString())
                .close()
                .build();

            PlayerLogall playerLogall = service.getReader().readObject(query, PlayerLogall.class);



     if (service.getReader().containsObject(query)) {
        /* service.getWriter().write(new PlayerLogall(playerLogall.getId()
                 , player.getName()
                 , playerLogall.getAddress()
                 , playerLogall.getFirstLogin()
                 , playerLogall.getConnections() + 1));
         service.getWriter().delete(new Query()
                 .addEq()
                 .setField("id")
                 .setValue(player.getUniqueId().toString())
                 .setField("connections")
                 .setValue(playerLogall.getConnections() - 1)
                 .close()
                 .build(), 1); */
        return;

     }
     service.getWriter().write(new PlayerLogall(player.getUniqueId().toString()
             , player.getName()
             , event.getAddress().getHostAddress()
             , new Date().getTime()
             , 1));

    }
}