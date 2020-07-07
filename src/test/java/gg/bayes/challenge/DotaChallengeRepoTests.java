package gg.bayes.challenge;

import gg.bayes.challenge.db.events.HeroDamageEvent;
import gg.bayes.challenge.db.events.HeroKillEvent;
import gg.bayes.challenge.db.events.ItemPurchaseEvent;
import gg.bayes.challenge.db.events.SpellCastEvent;
import gg.bayes.challenge.db.jpa.HeroDamageRepo;
import gg.bayes.challenge.db.jpa.HeroKillRepo;
import gg.bayes.challenge.db.jpa.ItemPurchaseRepo;
import gg.bayes.challenge.db.jpa.SpellCastRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
class DotaChallengeRepoTests {

    @Autowired
    HeroKillRepo heroKillRepo;
    @Autowired
    ItemPurchaseRepo itemPurchaseRepo;
    @Autowired
    SpellCastRepo spellCastRepo;
    @Autowired
    HeroDamageRepo heroDamageRepo;

    private static final String TEST_HERO_1 = "test_hero_1";
    private static final String TEST_HERO_2 = "test_hero_2";
    private static final String TEST_ITEM = "test_item";
    private static final String TEST_SPELL = "test_spell";

    @Test
    void contextLoads(){
    }

    @Test
    void heroKillRepoTest() {
        HeroKillEvent heroKillEvent = new HeroKillEvent(1, TEST_HERO_1);
        heroKillRepo.save(heroKillEvent);

        List<HeroKillRepo.HeroKillsProjection> result = heroKillRepo.getHeroKills(1);
        Assert.isTrue(result.size() == 1, "hero repo size mismatch");
        Assert.isTrue(result.get(0).getHero().equals(TEST_HERO_1), "hero value mismatch");
        Assert.isTrue(result.get(0).getKills() == 1, "hero kills mismatch");

        heroKillEvent = new HeroKillEvent(1, TEST_HERO_1);
        heroKillRepo.save(heroKillEvent);
        result = heroKillRepo.getHeroKills(1);
        Assert.isTrue(result.get(0).getKills() == 2, "hero kills mismatch");

        heroKillEvent = new HeroKillEvent(2, TEST_HERO_1);
        heroKillRepo.save(heroKillEvent);

        result = heroKillRepo.getHeroKills(1);
        Assert.isTrue(result.size() == 1, "hero repo size mismatch");
        result = heroKillRepo.getHeroKills(2);
        Assert.isTrue(result.size() == 1, "hero repo size mismatch");
        result = heroKillRepo.getHeroKills(3);
        Assert.isTrue(result.size() == 0, "hero repo size mismatch");
    }

    @Test
    void itemPurchaseRepoTest() {
        ItemPurchaseEvent itemPurchaseEvent = new ItemPurchaseEvent(1, TEST_HERO_1, 123456, TEST_ITEM);
        itemPurchaseRepo.save(itemPurchaseEvent);

        List<ItemPurchaseRepo.HeroItemsProjection> result = itemPurchaseRepo.getHeroItems(1, TEST_HERO_1);
        Assert.isTrue(result.size() == 1, "item repo size mismatch");
        Assert.isTrue(result.get(0).getItem().equals(TEST_ITEM), "item value mismatch");
        Assert.isTrue(result.get(0).getTimestamp() == 123456, "item timestamp mismatch");

        result = itemPurchaseRepo.getHeroItems(1, TEST_HERO_2);
        Assert.isTrue(result.size() == 0, "item repo size mismatch");

        result = itemPurchaseRepo.getHeroItems(2, TEST_HERO_1);
        Assert.isTrue(result.size() == 0, "item repo size mismatch");
    }

    @Test
    void spellCastRepoTest() {
        SpellCastEvent spellCastEvent = new SpellCastEvent(1, TEST_HERO_1, TEST_SPELL);
        spellCastRepo.save(spellCastEvent);

        List<SpellCastRepo.HeroSpellsProjection> result = spellCastRepo.getHeroSpells(1, TEST_HERO_1);
        Assert.isTrue(result.size() == 1, "spell repo size mismatch");
        Assert.isTrue(result.get(0).getSpell().equals(TEST_SPELL), "spell value mismatch");
        Assert.isTrue(result.get(0).getCasts() == 1, "spell count mismatch");

        spellCastEvent = new SpellCastEvent(1, TEST_HERO_1, TEST_SPELL);
        spellCastRepo.save(spellCastEvent);
        result = spellCastRepo.getHeroSpells(1, TEST_HERO_1);
        Assert.isTrue(result.get(0).getCasts() == 2, "spell count mismatch");

        result = spellCastRepo.getHeroSpells(2, TEST_HERO_1);
        Assert.isTrue(result.size() == 0, "spell repo size mismatch");

        result = spellCastRepo.getHeroSpells(1, TEST_HERO_2);
        Assert.isTrue(result.size() == 0, "spell repo size mismatch");
    }

    @Test
    void heroDamageRepoTest() {
        HeroDamageEvent heroDamageEvent = new HeroDamageEvent(1, TEST_HERO_1, TEST_HERO_2, 10);
        heroDamageRepo.save(heroDamageEvent);

        List<HeroDamageRepo.HeroDamageProjection> result = heroDamageRepo.getHeroDamage(1, TEST_HERO_1);
        Assert.isTrue(result.size() == 1, "damage repo size mismatch");
        Assert.isTrue(result.get(0).getTarget().equals(TEST_HERO_2), "damege target mismatch");
        Assert.isTrue(result.get(0).getDamageInstances() == 1, "damage count mismatch");
        Assert.isTrue(result.get(0).getTotalDamage() == 10, "damage value mismatch");

        heroDamageEvent = new HeroDamageEvent(1, TEST_HERO_1, TEST_HERO_2, 20);
        heroDamageRepo.save(heroDamageEvent);

        result = heroDamageRepo.getHeroDamage(1, TEST_HERO_1);
        Assert.isTrue(result.get(0).getDamageInstances() == 2, "damage count mismatch");
        Assert.isTrue(result.get(0).getTotalDamage() == 30, "damage value mismatch");

        result = heroDamageRepo.getHeroDamage(2, TEST_HERO_1);
        Assert.isTrue(result.size() == 0, "spell repo size mismatch");

        result = heroDamageRepo.getHeroDamage(1, TEST_HERO_2);
        Assert.isTrue(result.size() == 0, "spell repo size mismatch");
    }

}
