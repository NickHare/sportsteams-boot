package nos.sportsteamsboot.model;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import nos.sportsteamsboot.view.PublicView;
import nos.sportsteamsboot.view.DetailedView;

import javax.persistence.*;

import java.sql.Timestamp;

@Entity
public class Roster extends BaseModel{
    public static final Roster EmptyRoster = new Roster(null, null, null, null, null, null);

    @JsonView(PublicView.class)
    private Long playerId;

    @JsonView(PublicView.class)
    private Long teamId;

    public Roster() {}
    public Roster(Long id, String externalId, Long playerId, Long teamId, Timestamp createdTimestamp, Timestamp modifiedTimestamp){
        this.id = id;
        this.externalId = externalId;
        this.playerId = playerId;
        this.teamId = teamId;
        this.createdTimestamp = createdTimestamp;
        this.modifiedTimestamp = modifiedTimestamp;
    }

    public Long getPlayer() {
        return playerId;
    }
    public Long getTeam() {
        return teamId;
    }

    public void setPlayer(Long playerId) {
        this.playerId = playerId;
    }
    public void setTeam(Long teamId) {
        this.teamId = teamId;
    }

    public String toString(){
        return "{id: " + this.id + ", player: " + this.playerId + ", team: " + this.teamId + "}";
    }
}
