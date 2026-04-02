package com.github.h1pstr.fairyringfavourites;

import com.google.inject.Provides;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Menu;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.MenuOpened;
import net.runelite.api.events.PostMenuSort;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.Text;

@Slf4j
@PluginDescriptor(
	name = "Fairy Ring Favourites",
	description = "Labels favourite fairy ring teleports with destination names instead of codes",
	tags = {"fairy", "ring", "favourites", "favorites", "teleport", "label", "rename"}
)
public class FairyRingFavouritesPlugin extends Plugin
{
	private static final Pattern FAIRY_CODE_PATTERN = Pattern.compile("^([A-D][A-Z][A-Z])$");
	private static final Pattern LAST_DEST_PATTERN = Pattern.compile("Last-destination \\(([A-D][A-Z][A-Z])\\)");

	@Inject
	private Client client;

	@Inject
	private FairyRingFavouritesConfig config;

	private Map<String, String> customLabels = Map.of();

	@Provides
	FairyRingFavouritesConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(FairyRingFavouritesConfig.class);
	}

	@Override
	protected void startUp()
	{
		customLabels = parseCustomLabels();
	}

	@Override
	protected void shutDown()
	{
		customLabels = Map.of();
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (FairyRingFavouritesConfig.GROUP.equals(event.getGroup()))
		{
			customLabels = parseCustomLabels();
		}
	}

	@Subscribe
	public void onPostMenuSort(PostMenuSort event)
	{
		if (client.isMenuOpen())
		{
			return;
		}

		processMenuEntries();
	}

	@Subscribe(priority = -1) // run after MenuEntrySwapperPlugin creates swap submenus
	public void onMenuOpened(MenuOpened event)
	{
		processMenuEntries();
	}

	private void processMenuEntries()
	{
		Menu root = client.getMenu();
		MenuEntry[] entries = root.getMenuEntries();

		for (MenuEntry entry : entries)
		{
			String target = Text.removeTags(entry.getTarget());
			if (!target.toLowerCase().contains("fairy ring"))
			{
				continue;
			}

			// Check last-destination before relabelEntry could modify the option
			if (config.labelLastDestination())
			{
				String option = entry.getOption();
				Matcher m = LAST_DEST_PATTERN.matcher(option);
				if (m.matches())
				{
					String code = m.group(1);
					String label = getLabelForCode(code);
					if (label != null)
					{
						if (config.showCode())
						{
							entry.setOption("Last: " + label + " (" + code + ")");
						}
						else
						{
							entry.setOption("Last: " + label);
						}
					}
				}
			}

			// Relabel top-level entries with bare fairy ring codes (e.g. swapped left-click)
			relabelEntry(entry);

			// Relabel all submenu entries (favourites, swap left-click, swap shift-click)
			Menu sub = entry.getSubMenu();
			if (sub != null)
			{
				for (MenuEntry subEntry : sub.getMenuEntries())
				{
					relabelEntry(subEntry);
				}
			}
		}
	}

	private void relabelEntry(MenuEntry entry)
	{
		String option = entry.getOption();
		Matcher m = FAIRY_CODE_PATTERN.matcher(option);
		if (!m.matches())
		{
			return;
		}

		String code = m.group(1);
		String label = getLabelForCode(code);
		if (label == null)
		{
			return;
		}

		if (config.showCode())
		{
			entry.setOption(label + " (" + code + ")");
		}
		else
		{
			entry.setOption(label);
		}
	}

	private String getLabelForCode(String code)
	{
		String customLabel = customLabels.get(code.toUpperCase());
		if (customLabel != null)
		{
			return customLabel;
		}

		FairyRingDestination dest = FairyRingDestination.forCode(code);
		return dest != null ? dest.getDestination() : null;
	}

	private Map<String, String> parseCustomLabels()
	{
		Map<String, String> labels = new HashMap<>();
		String raw = config.customLabels();
		if (raw == null || raw.isEmpty())
		{
			return labels;
		}

		for (String line : raw.split("\n"))
		{
			line = line.trim();
			int comma = line.indexOf(',');
			if (comma > 0 && comma < line.length() - 1)
			{
				String code = line.substring(0, comma).trim().toUpperCase();
				String label = line.substring(comma + 1).trim();
				if (!label.isEmpty())
				{
					labels.put(code, label);
				}
			}
		}

		return labels;
	}
}
