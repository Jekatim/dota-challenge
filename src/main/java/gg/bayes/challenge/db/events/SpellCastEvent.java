package gg.bayes.challenge.db.events;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class SpellCastEvent {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private final long matchId;

    @NotNull
    private final String hero;

    @NotNull
    private final String spell;
}
