package com.github.h1pstr.fairyringfavourites;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(FairyRingFavouritesConfig.GROUP)
public interface FairyRingFavouritesConfig extends Config
{
	String GROUP = "fairyringfavourites";

	@ConfigItem(
		keyName = "showCode",
		name = "Show code",
		description = "Append the fairy ring code after the name, e.g. TzHaar area (BLP)",
		position = 1
	)
	default boolean showCode()
	{
		return true;
	}

	@ConfigItem(
		keyName = "labelLastDestination",
		name = "Label last destination",
		description = "Also label the Last-destination menu entry",
		position = 2
	)
	default boolean labelLastDestination()
	{
		return true;
	}

	@ConfigItem(
		keyName = "customLabels",
		name = "Custom labels",
		description = "Override labels for specific codes, one per line: CODE,Label (e.g. BLP,Tzhaar)",
		position = 3
	)
	default String customLabels()
	{
		return "";
	}
}
