package gg.bayes.challenge.db.jpa;

import gg.bayes.challenge.db.events.ItemPurchaseEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPurchaseRepo extends CrudRepository<ItemPurchaseEvent, Long> {

    @Query(value = "SELECT ITEM, TIMESTAMP FROM ITEM_PURCHASE_EVENT " +
            "WHERE MATCH_ID = ?1 " +
            "AND HERO = ?2 ",
            nativeQuery = true)
    List<HeroItemsProjection> getHeroItems(long matchId, String heroName);

    interface HeroItemsProjection {
        String getItem();
        Long getTimestamp();
    }
}
