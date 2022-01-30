package io.github.dmkng.CoordCopy;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Set;

public class Main extends JavaPlugin implements Listener {
	private final ItemStack WAND = new ItemStack(Material.STICK);
	private boolean interacted;

	@Override
	public void onEnable() {
		final ItemMeta meta = WAND.getItemMeta();
		if(meta != null) {
			meta.setDisplayName(ChatColor.GOLD + "CoordCopy Wand");
			meta.setLore(Arrays.asList(ChatColor.GRAY + "LMB - Copy coords of something you look at", ChatColor.GRAY + "RMB - Copy your coords"));
			WAND.setItemMeta(meta);
		}

		getCommand("coordcopy").setExecutor(this);
		getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 3) {
			getConfig().set("x", Double.parseDouble(args[0]));
			getConfig().set("y", Double.parseDouble(args[1]));
			getConfig().set("z", Double.parseDouble(args[2]));
			saveConfig();
			sender.sendMessage(ChatColor.GOLD + "Coords saved to the config file!");
		} else if(sender instanceof Player) {
			final Player player = (Player)sender;
			player.getInventory().addItem(WAND);
			player.sendMessage(ChatColor.GOLD + "LMB - Copy coords of something you look at, RMB - Copy your coords");
		}
		return true;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent e) {
		if(interacted) {
			interacted = false;
		} else if(WAND.equals(e.getItem()) || WAND.equals(e.getPlayer().getItemInHand())) {
			if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				final double x = Math.round(e.getPlayer().getLocation().getX() * 1000) / 1000d;
				final double y = Math.round(e.getPlayer().getLocation().getY() * 1000) / 1000d;
				final double z = Math.round(e.getPlayer().getLocation().getZ() * 1000) / 1000d;
				e.getPlayer().spigot().sendMessage(new ComponentBuilder("Your").color(ChatColor.BLUE)
					.append(String.format(" X:%s Y:%s Z:%s", x, y, z)).color(ChatColor.GOLD)
					.append(" [Get]").event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, String.format("x: %s/y: %s/z: %s", x, y, z))).color(ChatColor.WHITE)
					.append(" [Copy]").event(new ClickEvent(ClickEvent.Action.OPEN_URL, String.format("http://x:%s/y:%s/z:%s", x, y, z))).color(ChatColor.YELLOW)
					.append(" [Save]").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/coordcopy %s %s %s", x, y, z))).color(ChatColor.AQUA)
					.append(" [Get Rounded]").event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, String.format("x: %s/y: %s/z: %s", (int)x, (int)y, (int)z))).color(ChatColor.WHITE)
					.append(" [Copy Rounded]").event(new ClickEvent(ClickEvent.Action.OPEN_URL, String.format("http://x:%s/y:%s/z:%s", (int)x, (int)y, (int)z))).color(ChatColor.YELLOW)
					.append(" [Save Rounded]").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/coordcopy %s %s %s", (int)x, (int)y, (int)z))).color(ChatColor.AQUA)
					.create());
				e.setCancelled(true);
			} else if(e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR) {
				Block block = e.getClickedBlock();
				boolean far = false;
				if(block == null) {
					block = e.getPlayer().getTargetBlock((Set<Material>)null, 100);
					far = true;
				}
				if(block.getType() != Material.AIR) {
					final int x = block.getX();
					final int y = block.getY();
					final int z = block.getZ();
					e.getPlayer().spigot().sendMessage(new ComponentBuilder(far ? "Far Block" : "Block").color(ChatColor.BLUE)
						.append(String.format(" X:%s Y:%s Z:%s", x, y, z)).color(ChatColor.GOLD)
						.append(" [Get]").event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, String.format("x: %s/y: %s/z: %s", x, y, z))).color(ChatColor.WHITE)
						.append(" [Copy]").event(new ClickEvent(ClickEvent.Action.OPEN_URL, String.format("http://x:%s/y:%s/z:%s", x, y, z))).color(ChatColor.YELLOW)
						.append(" [Save]").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/coordcopy %s %s %s", x, y, z))).color(ChatColor.AQUA)
						.create());
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInteractEntity(PlayerInteractEntityEvent e) {
		if(WAND.equals(e.getPlayer().getItemInHand())) {
			final double x = Math.round(e.getRightClicked().getLocation().getX() * 1000) / 1000d;
			final double y = Math.round(e.getRightClicked().getLocation().getY() * 1000) / 1000d;
			final double z = Math.round(e.getRightClicked().getLocation().getZ() * 1000) / 1000d;
			e.getPlayer().spigot().sendMessage(new ComponentBuilder("Entity").color(ChatColor.BLUE)
				.append(String.format(" X:%s Y:%s Z:%s", x, y, z)).color(ChatColor.GOLD)
				.append(" [Get]").event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, String.format("x: %s/y: %s/z: %s", x, y, z))).color(ChatColor.WHITE)
				.append(" [Copy]").event(new ClickEvent(ClickEvent.Action.OPEN_URL, String.format("http://x:%s/y:%s/z:%s", x, y, z))).color(ChatColor.YELLOW)
				.append(" [Save]").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/coordcopy %s %s %s", x, y, z))).color(ChatColor.AQUA)
				.append(" [Get Rounded]").event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, String.format("x: %s/y: %s/z: %s", (int)x, (int)y, (int)z))).color(ChatColor.WHITE)
				.append(" [Copy Rounded]").event(new ClickEvent(ClickEvent.Action.OPEN_URL, String.format("http://x:%s/y:%s/z:%s", (int)x, (int)y, (int)z))).color(ChatColor.YELLOW)
				.append(" [Save Rounded]").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/coordcopy %s %s %s", (int)x, (int)y, (int)z))).color(ChatColor.AQUA)
				.create());
			e.setCancelled(true);
			interacted = true;
		}
	}
}
