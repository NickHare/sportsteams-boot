package nos.sportsteamsboot.model;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import nos.sportsteamsboot.view.PublicView;
import nos.sportsteamsboot.view.DetailedView;

import java.sql.Timestamp;

public class Roster {
    @JsonView(PublicView.class) @Id private Long id;
    @JsonView(PublicView.class) private Long player;
    @JsonView(PublicView.class) private Long team;
    @JsonView(DetailedView.class) @CreatedDate private Timestamp createdTimestamp;
    @JsonView(DetailedView.class) @LastModifiedDate private Timestamp modifiedTimestamp;

    public Roster() {}
    public Roster(Long player, Long team){
        this.player = player;
        this.team = team;
    }
    public Roster(Long id, Long player, Long team, Timestamp createdTimestamp, Timestamp modifiedTimestamp){
        this.id = id;
        this.player = player;
        this.team = team;
        this.createdTimestamp = createdTimestamp;
        this.modifiedTimestamp = modifiedTimestamp;
    }

    public Long getId() {
        return id;
    }
    public Long getPlayer() {
        return player;
    }
    public Long getTeam() {
        return team;
    }
    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }
    public Timestamp getModifiedTimestamp() {
        return modifiedTimestamp;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setPlayer(Long player) {
        this.player = player;
    }
    public void setTeam(Long team) {
        this.team = team;
    }
    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }
    public void setModifiedTimestamp(Timestamp modifiedTimestamp) {
        this.modifiedTimestamp = modifiedTimestamp;
    }

    public String toString(){
        return "{id: " + this.id + ", player: " + this.player + ", team: " + this.team + "}";
    }
}
