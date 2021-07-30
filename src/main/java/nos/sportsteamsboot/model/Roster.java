package nos.sportsteamsboot.model;

import com.fasterxml.jackson.annotation.JsonView;

import nos.sportsteamsboot.view.PublicView;

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
        if (roster == null) throw new IllegalArgumentException("Parameter roster for Roster constructor cannot be null");
        this.player = roster.player;
        this.team = roster.team;
        this.active = roster.active;
    }

    public Player getPlayer() {
        return this.player;
    }
    public Team getTeam() {
        return this.team;
    }
    public Boolean getActive(){
        return this.active;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setTeam(Team team) {
        this.team = team;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }

    public String toString(){
        return "{id: " + this.id + ", externalId: " + this.externalId + ", player:" + this.player + ", team: " + this.team + ", activeRoster: " + this.active + "}";
    }
}
