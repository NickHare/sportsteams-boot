package nos.sportsteamsboot.model;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import nos.sportsteamsboot.view.PublicView;
import nos.sportsteamsboot.view.DetailedView;

import javax.persistence.*;

import java.sql.Timestamp;

@Entity
public class Roster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(PublicView.class)
    private Long id;

    @JsonView(PublicView.class)
    private Long playerId;

    @JsonView(PublicView.class)
    private Long teamId;

    @CreatedDate
    @JsonView(DetailedView.class)
    private Timestamp createdTimestamp;

    @LastModifiedDate
    @JsonView(DetailedView.class)
    private Timestamp modifiedTimestamp;

    public Roster() {}
    public Roster(Long playerId, Long teamId){
        this.playerId = playerId;
        this.teamId = teamId;
    }
    public Roster(Long id, Long playerId, Long teamId, Timestamp createdTimestamp, Timestamp modifiedTimestamp){
        this.id = id;
        this.playerId = playerId;
        this.teamId = teamId;
        this.createdTimestamp = createdTimestamp;
        this.modifiedTimestamp = modifiedTimestamp;
    }

    public Long getId() {
        return id;
    }
    public Long getPlayer() {
        return playerId;
    }
    public Long getTeam() {
        return teamId;
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
    public void setPlayer(Long playerId) {
        this.playerId = playerId;
    }
    public void setTeam(Long teamId) {
        this.teamId = teamId;
    }
    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }
    public void setModifiedTimestamp(Timestamp modifiedTimestamp) {
        this.modifiedTimestamp = modifiedTimestamp;
    }

    public String toString(){
        return "{id: " + this.id + ", player: " + this.playerId + ", team: " + this.teamId + "}";
    }
}
