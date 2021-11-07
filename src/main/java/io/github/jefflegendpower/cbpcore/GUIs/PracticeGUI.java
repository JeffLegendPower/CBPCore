package io.github.jefflegendpower.cbpcore.GUIs;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import io.github.jefflegendpower.cbpcore.events.PracticeStartEvent;
import io.github.jefflegendpower.cbpcore.modes.Mode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class PracticeGUI implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("PracticeGui")
            .provider(new PracticeGUI())
            .size(3, 9)
            .title(ChatColor.GRAY + "Choose Gamemode")
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {

        ClickableItem fireballJump = ClickableItem.of(guiItem(new ItemStack(Material.FIREBALL),
                ChatColor.BLACK + "Fireball Jump", "To the moon!"), e ->
                callEvent(Mode.FIREBALL_JUMP, player));

        ClickableItem tntJump = ClickableItem.of(guiItem(new ItemStack(Material.TNT),
                ChatColor.RED + "TNT Jump", "How far can you go?"), e ->
                callEvent(Mode.TNT_JUMP, player));

        ClickableItem speedClutch = ClickableItem.of(guiItem(new ItemStack(Material.WOOL),
                ChatColor.WHITE + "Speed Clutch", "Clutch past all the obstacles at the speed of sound"), e ->
                callEvent(Mode.SPEED_CLUTCH, player));

        ClickableItem knockbackClutch = ClickableItem.of(guiItem(knockbackClutchItem(),
                ChatColor.WHITE + "Knockback Clutch"), e ->
                callEvent(Mode.KNOCKBACK_CLUTCH, player));

        ClickableItem blockIn = ClickableItem.of(guiItem(new ItemStack(Material.GOLD_PICKAXE),
                "Block In"), e ->
                callEvent(Mode.BLOCK_IN, player));

        contents.set(1, 2, fireballJump);
        contents.set(1, 3, tntJump);
        contents.set(1, 4, speedClutch);
        contents.set(1, 5, knockbackClutch);
        contents.set(1, 6, blockIn);
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    private void callEvent(Mode gameMode, Player player) {
        PracticeStartEvent event = new PracticeStartEvent(gameMode, player);
        Bukkit.getPluginManager().callEvent(event);
    }

    private ItemStack guiItem(ItemStack itemStack, String displayName, String... lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(Arrays.asList(lore));

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private ItemStack knockbackClutchItem() {
        ItemStack itemStack = new ItemStack(Material.STICK);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.KNOCKBACK, 1, false);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
