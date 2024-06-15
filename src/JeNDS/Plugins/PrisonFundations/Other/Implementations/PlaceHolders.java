package JeNDS.Plugins.PrisonFundations.Other.Implementations;


import JeNDS.Plugins.PrisonFundations.Mines.MineObjects.Mine;
import JeNDS.Plugins.PrisonFundations.PlayerData.PFPlayer;
import JeNDS.Plugins.PrisonFundations.Ranks.Rank;
import JeNDS.Plugins.PrisonFundations.Shops.Shop;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PlaceHolders extends PlaceholderExpansion {


    public static void LoadPlaceHolders() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceHolders().register();
        } else {
            System.out.println("Could not find PlaceholderAPI unloading placeholders");
        }
    }

    @Override
    public @NotNull String getIdentifier() {
        return "pf";
    }

    @Override
    public @NotNull String getAuthor() {
        return "JPlugins";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (player != null) {
            if (currentRank(player) != null) {
                if (params.equalsIgnoreCase("current_rank")) {
                    return currentRank(player).getRankName();
                }
                if (params.equalsIgnoreCase("current_rank_prefix")) {
                    return currentRank(player).getPrefix();
                }
                if (params.equalsIgnoreCase("next_rank")) {
                    return nextRank(player).getRankName();
                }
                if (params.equalsIgnoreCase("next_rank_prefix")) {
                    return nextRank(player).getPrefix();
                }
            }
            if (currentShop(player) != null)
                if (params.equalsIgnoreCase("current_shop_name")) {
                    return currentShop(player).getShopName();
                }
            if (params.equalsIgnoreCase("current_multiplier")) {
                if (PFPlayer.GetPFPlayer(player.getUniqueId()) != null) {
                    return Objects.requireNonNull(PFPlayer.GetPFPlayer(player.getUniqueId())).getFinalMultiplier() + "";
                } else {
                    return "1.0";
                }
            }
            Mine mine = closetMine(player);
            if (mine != null) {
                // 10 block radius
                if (params.equalsIgnoreCase("closet_mine_name")) {
                    return mine.getName();
                }
                if (params.equalsIgnoreCase("closet_mine_time_until_reset")) {
                    return mine.getTimeBeforeReset() + "M";
                }
                if (params.equalsIgnoreCase("closet_mine_percentage")) {
                    return mine.getMinePercentage() + "%";
                }
            }
        }

        return null;
    }

    private Rank currentRank(OfflinePlayer offlinePlayer) {
        Player player = offlinePlayer.getPlayer();
        return Rank.GetPlayerRank(player);
    }

    private Rank nextRank(OfflinePlayer offlinePlayer) {
        Player player = offlinePlayer.getPlayer();
        return Rank.GetPlayerNextRank(player);
    }

    private Shop currentShop(OfflinePlayer offlinePlayer) {
        Player player = offlinePlayer.getPlayer();
        return Shop.GetPlayerShop(player);
    }

    private Mine closetMine(OfflinePlayer offlinePlayer) {
        Player player = offlinePlayer.getPlayer();
        return Mine.ClosesMineToPlayer(player);
    }
}
