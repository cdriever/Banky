package com.ulvbror.banky.events;

import com.ulvbror.banky.data.BankData;
import com.ulvbror.banky.settings.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.*;

public class BankEvents implements Listener {

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        BankData bd = DataManager.getInstance().joined(player);
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("wallet", "dummy",ChatColor.AQUA + "Wallet");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score = objective.getScore(ChatColor.GREEN + "$");
        score.setScore(bd.wallet());
        player.setScoreboard(board);
    }

    @EventHandler
    public static void onPlayerLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        DataManager.getInstance().left(player);
    }

    @EventHandler
    public static void onKillMoney(EntityDeathEvent e) {
        Entity dead = e.getEntity();
        Entity killer = e.getEntity().getKiller();
        if(killer instanceof Player) {
            EntityType type = dead.getType();

        }
    }

    private int dropAmount(EntityType e) {
        int drop = 0;
        switch (e) {
            //HOSTILE MOBS
            case BLAZE:
            case CREEPER:
            case DROWNED:
            case ELDER_GUARDIAN:
            case ENDER_DRAGON:
            case ENDERMITE:
            case EVOKER:
            case GHAST:
            case GUARDIAN:
            case HOGLIN:
            case HUSK:
            case ILLUSIONER:
            case MAGMA_CUBE:
            case PHANTOM:
            case PIGLIN:
            case PIGLIN_BRUTE:
            case PILLAGER:
            case RAVAGER:
            case SHULKER:
            case SILVERFISH:
            case SKELETON:
            case SLIME:
            case STRAY:
            case VEX:
            case VINDICATOR:
            case WITCH:
            case WITHER:
            case WITHER_SKELETON:
            case ZOGLIN:
            case ZOMBIE:
            case ZOMBIE_VILLAGER:

                //NEUTRAL MOBS
            case BEE:
            case CAVE_SPIDER:
            case DOLPHIN:
            case ENDERMAN:
            case IRON_GOLEM:
            case LLAMA:
            case POLAR_BEAR:
            case PUFFERFISH:
            case SPIDER:
            case TRADER_LLAMA:
            case WANDERING_TRADER:
            case WOLF:
            case ZOMBIFIED_PIGLIN:

                //PASSIVE MOBS
            case BAT:
            case CAT:
            case CHICKEN:
            case COD:
            case COW:
            case DONKEY:
            case FOX:
            case HORSE:
            case MUSHROOM_COW:
            case MULE:
            case OCELOT:
            case PANDA:
            case PARROT:
            case PIG:
            case RABBIT:
            case SALMON:
            case SHEEP:
            case SKELETON_HORSE:
            case SNOWMAN:
            case SQUID:
            case STRIDER:
            case TROPICAL_FISH:
            case TURTLE:
            case VILLAGER:
            case ZOMBIE_HORSE:
            default:
                drop = 0;
        }
        return drop;
    }
}
