package com.github.h1pstr.fairyringfavourites;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import lombok.Getter;

@Getter
public enum FairyRingDestination
{
	AIQ("Mudskipper Point"),
	AIR("South-east of Ardougne"),
	AIS("Auburn Valley"),
	AJP("Avium Savannah"),
	AJQ("Cave south of Dorgesh-Kaan"),
	AJR("Slayer cave"),
	AJS("Penguins near Miscellania"),
	AKP("Necropolis"),
	AKQ("Piscatoris Hunter area"),
	AKR("Hosidius Vinery"),
	AKS("Feldip Hunter area"),
	ALP("Lighthouse"),
	ALQ("Haunted Woods"),
	ALR("Abyssal Area"),
	ALS("McGrubor's Wood"),

	BIP("South-west of Mort Myre"),
	BIQ("Kalphite Hive"),
	BIS("Ardougne Zoo"),
	BJP("Isle of Souls"),
	BJR("Fisher King Realm"),
	BJS("Near Zul-Andra"),
	BKP("South of Castle Wars"),
	BKQ("Enchanted Valley"),
	BKR("Mort Myre Swamp"),
	BKS("Zanaris"),
	BLP("TzHaar area"),
	BLQ("Yu'biusk"),
	BLR("Legends' Guild"),
	BLS("Mount Quidamortem"),

	CIP("Miscellania"),
	CIQ("North-west of Yanille"),
	CIR("Farming Guild"),
	CIS("Arceuus Library"),
	CJQ("The Great Conch"),
	CJR("Sinclair Mansion"),
	CKP("Cosmic entity's plane"),
	CKQ("Aldarin"),
	CKR("Tai Bwo Wannai"),
	CKS("Canifis"),
	CLP("South of Draynor Village"),
	CLR("Ape Atoll"),
	CLS("Hazelmere's home"),

	DIP("Abyssal Nexus"),
	DIQ("Player-owned house"),
	DIR("Gorak's Plane"),
	DIS("Wizards' Tower"),
	DJP("Tower of Life"),
	DJR("Chasm of Fire"),
	DKP("South of Musa Point"),
	DKR("Edgeville / GE"),
	DKS("Polar Hunter area"),
	DLP("Grimstone Dungeon"),
	DLQ("North of Nardah"),
	DLR("Poison Waste"),
	DLS("The Hollows");

	private static final Map<String, FairyRingDestination> BY_CODE = Arrays.stream(values())
		.collect(Collectors.toUnmodifiableMap(Enum::name, v -> v));

	private final String destination;

	FairyRingDestination(String destination)
	{
		this.destination = destination;
	}

	@Nullable
	public static FairyRingDestination forCode(String code)
	{
		return BY_CODE.get(code.toUpperCase());
	}
}
