package com.github.h1pstr.fairyringfavourites;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class FairyRingFavouritesPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(FairyRingFavouritesPlugin.class);
		RuneLite.main(args);
	}
}
