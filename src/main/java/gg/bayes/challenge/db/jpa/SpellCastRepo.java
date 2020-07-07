package gg.bayes.challenge.db.jpa;

import gg.bayes.challenge.db.events.SpellCastEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpellCastRepo extends CrudRepository<SpellCastEvent, Long> {

    @Query(value = "SELECT SPELL, COUNT(ID) as CASTS " +
            "from SPELL_CAST_EVENT " +
            "WHERE MATCH_ID = ?1 " +
            "AND HERO = ?2 " +
            "GROUP BY SPELL",
            nativeQuery = true)
    List<HeroSpellsProjection> getHeroSpells(long matchId, String heroName);

    interface HeroSpellsProjection {
        String getSpell();
        Integer getCasts();
    }
}
