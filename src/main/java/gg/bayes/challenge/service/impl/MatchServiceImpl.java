package gg.bayes.challenge.service.impl;

import gg.bayes.challenge.db.events.HeroDamageEvent;
import gg.bayes.challenge.db.events.HeroKillEvent;
import gg.bayes.challenge.db.events.ItemPurchaseEvent;
import gg.bayes.challenge.db.events.SpellCastEvent;
import gg.bayes.challenge.db.jpa.HeroDamageRepo;
import gg.bayes.challenge.db.jpa.HeroKillRepo;
import gg.bayes.challenge.db.jpa.ItemPurchaseRepo;
import gg.bayes.challenge.db.jpa.SpellCastRepo;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItems;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import gg.bayes.challenge.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static gg.bayes.challenge.config.Patterns.*;

@Slf4j
@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    HeroKillRepo heroKillRepo;
    @Autowired
    ItemPurchaseRepo itemPurchaseRepo;
    @Autowired
    SpellCastRepo spellCastRepo;
    @Autowired
    HeroDamageRepo heroDamageRepo;

    @Autowired
    public MatchServiceImpl() {
    }

    private static long matchCounter = 1;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    @Override
    public Long ingestMatch(String payload) {
        log.info("Start processing payload");
        long skippedLines = 0;
        String[] lines = payload.split("\\r?\\n");
        for (String line : lines) {
            if(!processLine(line)) {
                skippedLines++;
            }
        }
        log.info("Processing payload finished. Skipped lines: " + skippedLines);
        return matchCounter++;
    }

    @Override
    public List<HeroKills> getMatch(Long matchId) {
        List<HeroKills> heroKills = new ArrayList<>();
        for (HeroKillRepo.HeroKillsProjection proj : heroKillRepo.getHeroKills(matchId)) {
            HeroKills hk = new HeroKills();
            BeanUtils.copyProperties(proj, hk);
            heroKills.add(hk);
        }
        return heroKills;
    }

    @Override
    public List<HeroItems> getItems(Long matchId, String heroName) {
        List<HeroItems> heroItems = new ArrayList<>();
        for (ItemPurchaseRepo.HeroItemsProjection proj : itemPurchaseRepo.getHeroItems(matchId, heroName)) {
            HeroItems hi = new HeroItems();
            BeanUtils.copyProperties(proj, hi);
            heroItems.add(hi);
        }
        return heroItems;
    }

    @Override
    public List<HeroSpells> getSpells(Long matchId, String heroName) {
        List<HeroSpells> heroSpells = new ArrayList<>();
        for (SpellCastRepo.HeroSpellsProjection proj : spellCastRepo.getHeroSpells(matchId, heroName)) {
            HeroSpells hs = new HeroSpells();
            BeanUtils.copyProperties(proj, hs);
            heroSpells.add(hs);
        }
        return heroSpells;
    }

    @Override
    public List<HeroDamage> getDamage(Long matchId, String heroName) {
        List<HeroDamage> heroDamages = new ArrayList<>();
        for (HeroDamageRepo.HeroDamageProjection proj : heroDamageRepo.getHeroDamage(matchId, heroName)) {
            HeroDamage hd = new HeroDamage();
            BeanUtils.copyProperties(proj, hd);
            heroDamages.add(hd);
        }
        return heroDamages;
    }

    private boolean processLine(String line) {
        Matcher killMatcher = HERO_KILL_PATTERN.matcher(line);
        if (killMatcher.matches()) {
            heroKillRepo.save(new HeroKillEvent(
                    matchCounter,
                    killMatcher.group("hero")
            ));
            return true;
        }
        Matcher itemMatcher = ITEM_PURCHASE_PATTERN.matcher(line);
        if (itemMatcher.matches()) {
            LocalTime timestamp = LocalTime.parse(itemMatcher.group("timestamp"), formatter);
            itemPurchaseRepo.save(new ItemPurchaseEvent(
                    matchCounter,
                    itemMatcher.group("hero"),
                    timestamp.toNanoOfDay() / 1000000,
                    itemMatcher.group("item")
            ));
            return true;
        }
        Matcher spellMatcher = SPELL_CAST_PATTERN.matcher(line);
        if (spellMatcher.matches()) {
            spellCastRepo.save(new SpellCastEvent(
                    matchCounter,
                    spellMatcher.group("hero"),
                    spellMatcher.group("spell")
            ));
            return true;
        }
        Matcher damageMatcher = HERO_DAMAGE_PATTERN.matcher(line);
        if (damageMatcher.matches()) {
            heroDamageRepo.save(new HeroDamageEvent(
                    matchCounter,
                    damageMatcher.group("hero"),
                    damageMatcher.group("target"),
                    Integer.parseInt(damageMatcher.group("damage"))
            ));
            return true;
        }
        if (log.isDebugEnabled()) {
            log.debug("Unprocessed line: " + line);
        }
        return false;
    }
}
