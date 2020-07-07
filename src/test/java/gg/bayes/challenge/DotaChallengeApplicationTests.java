package gg.bayes.challenge;

import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItems;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import gg.bayes.challenge.service.MatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
class DotaChallengeApplicationTests {

    @Autowired
    MatchService matchService;

    @Test
    void contextLoads(){
    }

    @Test
    void serviceTest() {
        String log = "[00:11:17.489] npc_dota_hero_snapfire is killed by npc_dota_hero_mars\n" +
                "[00:12:17.474] npc_dota_hero_dragon_knight buys item item_circlet\n" +
                "[00:12:26.805] npc_dota_hero_bloodseeker casts ability bloodseeker_bloodrage (lvl 1) on npc_dota_hero_bloodseeker\n" +
                "[00:12:35.070] npc_dota_hero_pangolier hits npc_dota_hero_rubick with dota_unknown for 47 damage (473->426)\n";
        matchService.ingestMatch(log);

        List<HeroKills> heroKills = matchService.getMatch(1L);
        Assert.isTrue(heroKills.get(0).getHero().equals("mars"), "kill repo hero mismatch");
        Assert.isTrue(heroKills.get(0).getKills() == 1, "kill repo value mismatch");

        List<HeroItems> heroItems = matchService.getItems(1L, "dragon_knight");
        Assert.isTrue(heroItems.size() == 1, "item repo size mismatch");
        Assert.isTrue(heroItems.get(0).getItem().equals("circlet"), "item repo value mismatch");
        Assert.isTrue(heroItems.get(0).getTimestamp() == 737474, "item repo timestamp mismatch");

        List<HeroSpells> heroSpells = matchService.getSpells(1L, "bloodseeker");
        Assert.isTrue(heroSpells.size() == 1, "spell repo size mismatch");
        Assert.isTrue(heroSpells.get(0).getSpell().equals("bloodseeker_bloodrage"), "spell repo value mismatch");
        Assert.isTrue(heroSpells.get(0).getCasts() == 1, "spell repo count mismatch");

        List<HeroDamage> heroDamages = matchService.getDamage(1L, "pangolier");
        Assert.isTrue(heroDamages.size() == 1, "damage repo size mismatch");
        Assert.isTrue(heroDamages.get(0).getTarget().equals("rubick"), "damage repo target mismatch");
        Assert.isTrue(heroDamages.get(0).getDamageInstances() == 1, "damage repo count mismatch");
        Assert.isTrue(heroDamages.get(0).getTotalDamage() == 47, "damage repo value mismatch");
    }

}
