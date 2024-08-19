package pl.szczurowsky.loottableparser.util.paper;

import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Method;
import java.util.Optional;

public class EnchantmentUtil {
    
    private EnchantmentUtil() {
        throw new IllegalStateException("Utility class");
    }


    public static Optional<Enchantment> getEnchantment(String enchantment) {
        try {
            if (enchantment.contains("minecraft:")) enchantment = enchantment.replace("minecraft:", "");
            Method method = Enchantment.class.getDeclaredMethod("getEnchantment", String.class);
            method.setAccessible(true);
            Enchantment foundEnchantment = (Enchantment) method.invoke(null, enchantment);
            return Optional.ofNullable(foundEnchantment);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
