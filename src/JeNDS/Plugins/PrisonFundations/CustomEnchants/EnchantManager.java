package JeNDS.Plugins.PrisonFundations.CustomEnchants;

import JeNDS.Plugins.JeNDSAPI.Enchants.CustomEnchantmentLoader;
import JeNDS.Plugins.PrisonFundations.CustomEnchants.Enchantments.AutoSell;
import JeNDS.Plugins.PrisonFundations.CustomEnchants.Enchantments.Explosion;

public class EnchantManager {

    public static void LoadEnchantments() {
        new AutoSell();
        new Explosion();
        CustomEnchantmentLoader.LoadAllEnchantments();
    }

}
