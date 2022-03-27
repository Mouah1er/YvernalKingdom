package fr.yvernal.yvernalkingdom.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;

/**
 * Simple {@link ItemStack} builder. <br>
 * J'ai eu la flemme d'en faire un :x
 *
 * @author MrMicky
 */
public class ItemBuilder {

    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilder(Material material) {
        this(new ItemStack(material));
    }

    public ItemBuilder(Material material, byte id) {
        this(new ItemStack(material, 1, id));
    }

    public ItemBuilder(ItemStack item) {
        this.item = Objects.requireNonNull(item, "item");
        this.meta = item.getItemMeta();

        if (this.meta == null) {
            throw new IllegalArgumentException("The type " + item.getType() + " doesn't support item meta");
        }
    }

    public ItemBuilder type(Material material) {
        this.item.setType(material);
        return this;
    }

    public ItemBuilder data(int data) {
        return durability((short) data);
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder durability(short durability) {
        this.item.setDurability(durability);
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment) {
        return enchant(enchantment, 1);
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        this.meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder removeEnchant(Enchantment enchantment) {
        this.meta.removeEnchant(enchantment);
        return this;
    }

    public ItemBuilder removeEnchants() {
        this.meta.getEnchants().keySet().forEach(this.meta::removeEnchant);
        return this;
    }

    public ItemBuilder meta(Consumer<ItemMeta> metaConsumer) {
        metaConsumer.accept(this.meta);
        return this;
    }

    public <T extends ItemMeta> ItemBuilder meta(Class<T> metaClass, Consumer<T> metaConsumer) {
        if (metaClass.isInstance(this.meta)) {
            metaConsumer.accept(metaClass.cast(this.meta));
        }
        return this;
    }

    public ItemBuilder name(String name) {
        this.meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder lore(String lore) {
        return lore(Collections.singletonList(lore));
    }

    public ItemBuilder lore(String... lore) {
        return lore(Arrays.asList(lore));
    }

    public ItemBuilder lore(List<String> lore) {
        this.meta.setLore(lore);
        return this;
    }

    public ItemBuilder addLore(String line) {
        List<String> lore = this.meta.getLore();

        if (lore == null) {
            return lore(line);
        }

        lore.add(line);
        return lore(lore);
    }

    public ItemBuilder addLore(String... lines) {
        return addLore(Arrays.asList(lines));
    }

    public ItemBuilder addLore(List<String> lines) {
        List<String> lore = this.meta.getLore();

        if (lore == null) {
            return lore(lines);
        }

        lore.addAll(lines);
        return lore(lore);
    }

    public ItemBuilder flags(ItemFlag... flags) {
        this.meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder flags() {
        return flags(ItemFlag.values());
    }

    public ItemBuilder removeFlags(ItemFlag... flags) {
        this.meta.removeItemFlags(flags);
        return this;
    }

    public ItemBuilder removeFlags() {
        return removeFlags(ItemFlag.values());
    }

    public ItemBuilder armorColor(Color color) {
        return meta(LeatherArmorMeta.class, m -> m.setColor(color));
    }

    public ItemBuilder skullTexture(String base64) {
        final SkullMeta skullMeta = (SkullMeta) this.meta;

        try {
            final Method profileMethod = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            profileMethod.setAccessible(true);
            profileMethod.invoke(skullMeta, gameProfile(base64));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            try {
                final Field profileField = skullMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(skullMeta, gameProfile(base64));
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }

        item.setItemMeta(skullMeta);

        return this;
    }

    public ItemBuilder owner(OfflinePlayer offlinePlayer) {
        if (this.meta instanceof SkullMeta) {
            final SkullMeta skullMeta = (SkullMeta) this.meta;

            final CraftPlayer craftPlayer = (CraftPlayer) Bukkit.getPlayer(offlinePlayer.getUniqueId());

            final GameProfile gameProfile = craftPlayer == null ? new GameProfile(offlinePlayer.getUniqueId(),
                    offlinePlayer.getName()) : craftPlayer.getProfile();

            try {
                final Method profileMethod = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
                profileMethod.setAccessible(true);
                profileMethod.invoke(skullMeta, gameProfile);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                try {
                    final Field profileField = skullMeta.getClass().getDeclaredField("profile");
                    profileField.setAccessible(true);
                    profileField.set(skullMeta, gameProfile);
                } catch (NoSuchFieldException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }

            item.setItemMeta(skullMeta);
        }

        return this;
    }

    public ItemStack build() {
        this.item.setItemMeta(this.meta);
        return this.item;
    }

    private GameProfile gameProfile(String base64) {
        final GameProfile profile = new GameProfile(UUID.randomUUID(), "Player");

        profile.getProperties().put("textures", new Property("textures", base64));

        return profile;
    }
}
