package gg.bayes.challenge.config;

import java.util.regex.Pattern;

public class Patterns {

    public static final Pattern ITEM_PURCHASE_PATTERN = Pattern.compile("^\\[(?<timestamp>[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3})\\] npc_dota_hero_(?<hero>[a-zA-Z_]+) buys item item_(?<item>[a-zA-Z_]+)$");
    public static final Pattern HERO_KILL_PATTERN = Pattern.compile("^\\[[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}\\] npc_dota_hero_[a-zA-Z_]+ is killed by npc_dota_hero_(?<hero>[a-zA-Z_]+)$");
    public static final Pattern SPELL_CAST_PATTERN = Pattern.compile("^\\[[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}\\] npc_dota_hero_(?<hero>[a-zA-Z_]+) casts ability (?<spell>[a-zA-Z_]+) \\(lvl [1-4]\\) on [a-zA-Z_]+$");
    public static final Pattern HERO_DAMAGE_PATTERN = Pattern.compile("^\\[[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}\\] npc_dota_hero_(?<hero>[a-zA-Z_]+) hits npc_dota_hero_(?<target>[a-zA-Z_]+) with [a-zA-Z_]+ for (?<damage>[0-9]+) damage(?: \\([0-9]+->[0-9]+\\))?$");
}
