package gg.bayes.challenge.db.events;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class HeroDamageEvent {

    @Id
    @GeneratedValue
    private long id;

    @NonNull
    private final long matchId;

    @NonNull
    private final String hero;

    @NonNull
    private final String targetHero;

    @NonNull
    private final int damage;


}
