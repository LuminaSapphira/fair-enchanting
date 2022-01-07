package net.cerulan.fairenchanting;


import net.fabricmc.api.ModInitializer;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class FairEnchanting implements ModInitializer {
    
    public static final Logger logger = LogManager.getLogger();
    
    public static int getExperienceCostAtLevel(int startLevel, int levelCost) {
        if (levelCost < 0 || startLevel < 0)
            throw new CrashException(CrashReport.create(
                    new RuntimeException("Value cannot be negative"),
                    "Calculating experience using negative value"));
        int cost = 0;
        for (int i = startLevel; i > startLevel - levelCost; --i) {
            cost += getSingleLevelCost(i);
        }
        return cost;
    }
    
    private static int getSingleLevelCost(int level) {
//		2 × current_level + 7 (for levels 0–15)
//		5 × current_level – 38 (for levels 16–30)
//		9 × current_level – 158 (for levels 31+)
        level -= 1;
        if (level >= 0 && level <= 15) {
            return 2 * level + 7;
        } else if (level >= 16 && level <= 30) {
            return 5 * level - 38;
        } else if (level >= 31) {
            return 9 * level - 158;
        } else {
            throw new RuntimeException("Undefined level! [" + level + "]");
        }
    }
    
    @Override
    public void onInitialize() {
        logger.info("FairEnchanting active");
    }
    
}
