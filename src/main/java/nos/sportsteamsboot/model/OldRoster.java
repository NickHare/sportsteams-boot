package nos.sportsteamsboot.model;

import com.fasterxml.jackson.annotation.JsonView;
import nos.sportsteamsboot.view.PublicView;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;

public class OldRoster {
    @JsonView(PublicView.class) @Id private Long id;
    @JsonView(PublicView.class) private Player player;
    @JsonView(PublicView.class) private Team team;
    @JsonView(PublicView.class) @CreatedDate private Timestamp createdTimestamp;
    @JsonView(PublicView.class) @LastModifiedDate private Timestamp modifiedTimestamp;

    public OldRoster() {}
    public OldRoster(Player player, Team team){
        this.player = player;
        this.team = team;
    }
    public OldRoster(Long id, Player player, Team team, Timestamp createdTimestamp, Timestamp modifiedTimestamp){
        this.id = id;
        this.player = player;
        this.team = team;
        this.createdTimestamp = createdTimestamp;
        this.modifiedTimestamp = modifiedTimestamp;
    }

    public Long getId() {
        return id;
    }
    public Player getPlayer() {
        return player;
    }
    public Team getTeam() {
        return team;
    }
    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }
    public Timestamp getModifiedTimestamp() {
        return modifiedTimestamp;
    }

    public OldRoster withId(Long id) {
        return new OldRoster(id, this.player, this.team, this.createdTimestamp, this.modifiedTimestamp);
    }
    public OldRoster withPlayer(Player player) {
        return new OldRoster(this.id, player, this.team, this.createdTimestamp, this.modifiedTimestamp);
    }
    public OldRoster withTeam(Team team) {
        return new OldRoster(this.id, this.player, team, this.createdTimestamp, this.modifiedTimestamp);
    }
    public OldRoster withCreatedTimestamp(Timestamp createdTimestamp) {
        return new OldRoster(this.id, this.player, this.team, createdTimestamp, this.modifiedTimestamp);
    }
    public OldRoster withModifiedTimestamp(Timestamp modifiedTimestamp) {
        return new OldRoster(this.id, this.player, this.team, this.createdTimestamp, modifiedTimestamp);
    }
    public OldRoster withIdAndTimestamps(Long id, Timestamp createdTimestamp, Timestamp modifiedTimestamp) {
        return new OldRoster(id, this.player, this.team, createdTimestamp, modifiedTimestamp);
    }

    public OldRoster withoutId() {
        return new OldRoster(null, this.player, this.team, this.createdTimestamp, this.modifiedTimestamp);
    }
    public OldRoster withoutPlayer() {
        return new OldRoster(this.id, null, this.team, this.createdTimestamp, this.modifiedTimestamp);
    }
    public OldRoster withoutTeam() {
        return new OldRoster(this.id, this.player, null, this.createdTimestamp, this.modifiedTimestamp);
    }
    public OldRoster withoutCreatedTimestamp() {
        return new OldRoster(this.id, this.player, this.team, null, this.modifiedTimestamp);
    }
    public OldRoster withoutModifiedTimestamp() {
        return new OldRoster(this.id, this.player, this.team, this.createdTimestamp, null);
    }
    public OldRoster withoutIdAndTimestamps() {
        return new OldRoster(null, this.player, this.team, null, null);
    }

    public String toString(){
        return "{id: " + this.id + ", player: " + this.player + ", team: " + this.team + "}";
    }
}
