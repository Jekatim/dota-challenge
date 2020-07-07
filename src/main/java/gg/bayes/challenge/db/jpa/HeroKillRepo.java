package gg.bayes.challenge.db.jpa;

import gg.bayes.challenge.db.events.HeroKillEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroKillRepo extends CrudRepository<HeroKillEvent, Long> {

    @Query(value = "SELECT HERO as hero, COUNT(ID) as kills FROM HERO_KILL_EVENT " +
            "WHERE MATCH_ID = ?1 " +
            "GROUP BY HERO",
            nativeQuery = true)
    List<HeroKillsProjection> getHeroKills(long matchId);

    interface HeroKillsProjection {
        String getHero();
        Integer getKills();
    }
}
