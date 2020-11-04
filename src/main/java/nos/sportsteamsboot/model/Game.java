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
public class Game extends BaseModel{
    public static final Game EmptyGame = new Game(null, null, null, null, 0, 0, "", null, null, null);

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

    public Game() {}
    public Game(Long id, String externalId, Long homeTeamId, Long awayTeamId, Integer homeScore, Integer awayScore, String status, Timestamp startTimestamp, Timestamp createdTimestamp, Timestamp modifiedTimestamp){
        super(id, externalId, createdTimestamp, modifiedTimestamp);
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.status = status;
        this.startTimestamp = startTimestamp;
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

    public String toString(){
        return "{id: " + this.id + ", homeTeamId: " + this.homeTeamId + ", awayTeamId: " + this.awayTeamId + ", homeScore: " + this.homeScore + ", awayScore: " + this.awayScore + ", status: " + this.status + ", startTimestamp: " + this.startTimestamp + "}";
    }
}
