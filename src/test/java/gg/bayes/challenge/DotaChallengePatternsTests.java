package gg.bayes.challenge;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.regex.Matcher;

import static gg.bayes.challenge.config.Patterns.*;

@SpringBootTest
class DotaChallengePatternsTests {

    @Test
    void contextLoads(){
    }

    @Test
    void killPatternMatching() {
        String killLine = "[00:11:17.489] npc_dota_hero_snapfire is killed by npc_dota_hero_mars";
        Matcher killMatcher = HERO_KILL_PATTERN.matcher(killLine);
        Assert.isTrue(killMatcher.matches(), "hero kill 1 pattern mismatch");

        killLine = "[00:12:15.108] npc_dota_neutral_harpy_scout is killed by npc_dota_hero_pangolier";
        killMatcher = HERO_KILL_PATTERN.matcher(killLine);
        Assert.isTrue(!killMatcher.matches(), "hero kill 2 pattern mismatch");
    }

    @Test
    void itemPatternMatching() {
        String itemLine = "[00:12:17.474] npc_dota_hero_dragon_knight buys item item_circlet";
        Matcher itemMatcher = ITEM_PURCHASE_PATTERN.matcher(itemLine);
        Assert.isTrue(itemMatcher.matches(), "hero item 1 pattern mismatch");
    }

    @Test
    void spellPatternMatching() {
        String spellLine = "[00:12:26.805] npc_dota_hero_bloodseeker casts ability bloodseeker_bloodrage (lvl 1) on npc_dota_hero_bloodseeker";
        Matcher spellMatcher = SPELL_CAST_PATTERN.matcher(spellLine);
        Assert.isTrue(spellMatcher.matches(), "hero spell 1 pattern mismatch");

        spellLine = "[00:12:33.670] npc_dota_hero_pangolier casts ability pangolier_swashbuckle (lvl 1) on dota_unknown";
        spellMatcher = SPELL_CAST_PATTERN.matcher(spellLine);
        Assert.isTrue(spellMatcher.matches(), "hero spell 2 pattern mismatch");
    }

    @Test
    void damagePatternMatching() {
        String damageLine = "[00:12:35.070] npc_dota_hero_pangolier hits npc_dota_hero_rubick with dota_unknown for 47 damage (473->426)";
        Matcher damageMatcher = HERO_DAMAGE_PATTERN.matcher(damageLine);
        Assert.isTrue(damageMatcher.matches(), "hero damage 1 pattern mismatch");

        damageLine = "[00:12:39.536] npc_dota_hero_abyssal_underlord hits npc_dota_hero_bloodseeker with abyssal_underlord_firestorm for 18 damage (693->675)";
        damageMatcher = HERO_DAMAGE_PATTERN.matcher(damageLine);
        Assert.isTrue(damageMatcher.matches(), "hero damage 1 pattern mismatch");
    }

}
