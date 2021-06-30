package nos.sportsteamsboot.model;

import com.fasterxml.jackson.annotation.JsonView;

import nos.sportsteamsboot.view.PublicView;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

import java.sql.Timestamp;

@Entity
@NamedEntityGraph(name = "roster", attributeNodes = {@NamedAttributeNode("player"), @NamedAttributeNode("team")})
public class Roster extends BaseModel{
    public static final Roster EmptyRoster = new Roster(null, null, null, null, false, null, null);
    public static final Roster NullRoster = new Roster(null, null, null, null, null, null, null);

    @JsonView(PublicView.class)
    @ManyToOne
    private Player player;

    @JsonView(PublicView.class)
    @ManyToOne
    private Team team;

    @JsonView(PublicView.class)
    private Boolean active;

    public Roster() {}
    public Roster(Long id, String externalId, Player player, Team team, Boolean active, Timestamp createdTimestamp, Timestamp modifiedTimestamp){
        super(id, externalId, createdTimestamp, modifiedTimestamp);
        this.player = player;
        this.team = team;
        this.active = active;
    }
    public Roster(Roster roster){
        super(roster.id, roster.externalId, roster.createdTimestamp, roster.modifiedTimestamp);
        this.player = roster.player;
        this.team = roster.team;
        this.active = roster.active;
    }

    public Player getPlayer() {
        return player;
    }
    public Team getTeam() {
        return team;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setTeam(Team team) {
        this.team = team;
    }

    public String toString(){
        return "{id: " + this.id + ", player: " + this.player + ", team: " + this.team + ", activeRoster: " + this.active + "}";
    }
}
