package nos.sportsteamsboot.model;

import com.fasterxml.jackson.annotation.JsonView;
import nos.sportsteamsboot.view.DetailedView;
import nos.sportsteamsboot.view.PublicView;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(PublicView.class)
    private Long id;

    @JsonView(PublicView.class)
    private Long homeTeamId;

    @JsonView(PublicView.class)
    private Long awayTeamId;

    @JsonView(PublicView.class)
    private Integer homeScore;

    @JsonView(PublicView.class)
    private Integer awayScore;

    @NotNull
    @NotBlank
    @JsonView(PublicView.class)
    private String status;

    @NotNull
    @JsonView(PublicView.class)
    private Timestamp startTimestamp;

    @CreatedDate
    @JsonView(DetailedView.class)
    private Timestamp createdTimestamp;

    @LastModifiedDate
    @JsonView(DetailedView.class)
    private Timestamp modifiedTimestamp;

    public Game() {}
    public Game(Long homeTeamId, Long awayTeamId, Integer homeScore, Integer awayScore, String status, Timestamp startTimestamp){
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.status = status;
        this.startTimestamp = startTimestamp;
    }
    public Game(Long id, Long homeTeamId, Long awayTeamId, Integer homeScore, Integer awayScore, String status, Timestamp startTimestamp, Timestamp createdTimestamp, Timestamp modifiedTimestamp){
        this.id = id;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.status = status;
        this.startTimestamp = startTimestamp;
        this.createdTimestamp = createdTimestamp;
        this.modifiedTimestamp = modifiedTimestamp;
    }

    public Long getId() {
        return id;
    }
    public Long getHomeTeamId() {
        return homeTeamId;
    }
    public Long getAwayTeamId() {
        return awayTeamId;
    }
    public Integer getHomeScore() {
        return homeScore;
    }
    public Integer getAwayScore() {
        return awayScore;
    }
    public String getStatus() {
        return status;
    }
    public Timestamp getStartTimestamp() {
        return startTimestamp;
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
    public void setHomeTeamId(Long homeTeamId) {
        this.homeTeamId = homeTeamId;
    }
    public void setAwayTeamId(Long awayTeamId) {
        this.awayTeamId = awayTeamId;
    }
    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }
    public void setAwayScore(Integer awayScore) {
        this.awayScore = awayScore;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setStartTimestamp(Timestamp startTimestamp) {
        this.startTimestamp = startTimestamp;
    }
    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }
    public void setModifiedTimestamp(Timestamp modifiedTimestamp) {
        this.modifiedTimestamp = modifiedTimestamp;
    }

    public String toString(){
        return "{id: " + this.id + ", homeTeamId: " + this.homeTeamId + ", awayTeamId: " + this.awayTeamId + ", homeScore: " + this.homeScore + ", awayScore: " + this.awayScore + ", status: " + this.status + ", startTimestamp: " + this.startTimestamp + "}";
    }
}
