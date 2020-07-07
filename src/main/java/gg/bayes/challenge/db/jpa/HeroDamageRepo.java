package gg.bayes.challenge.db.jpa;

import gg.bayes.challenge.db.events.HeroDamageEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroDamageRepo extends CrudRepository<HeroDamageEvent, Long> {

    @Query(value = "SELECT TARGET_HERO as target, COUNT(ID) as damageInstances, SUM(DAMAGE) as totalDamage " +
            "from HERO_DAMAGE_EVENT " +
            "WHERE MATCH_ID = ?1 " +
            "AND HERO = ?2 " +
            "GROUP BY TARGET_HERO",
            nativeQuery = true)
    List<HeroDamageProjection> getHeroDamage(long matchId, String heroName);

    interface HeroDamageProjection {
        String getTarget();
        Integer getDamageInstances();
        Integer getTotalDamage();
    }
}
