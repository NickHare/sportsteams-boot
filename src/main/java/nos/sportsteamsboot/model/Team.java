package nos.sportsteamsboot.model;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import nos.sportsteamsboot.view.PublicView;
import nos.sportsteamsboot.view.DetailedView;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class Team extends BaseModel{
    public static final Team EmptyTeam = new Team(null, null, "", "", null, null);

    @JsonView(PublicView.class)
    private String name;

    @JsonView(PublicView.class)
    private String abbreviation;

    public Team() {}
    public Team(Long id, String externalId, String name, String abbreviation, Timestamp createdTimestamp, Timestamp modifiedTimestamp){
        super(id, externalId, createdTimestamp, modifiedTimestamp);
        this.name = name;
        this.abbreviation = abbreviation;
    }
    public Team(Team team){
        super(team);
        if (team == null) throw new IllegalArgumentException("Parameter team for Team constructor cannot be null");
        this.name = team.name;
    }


    public String getName() {
        return this.name;
    }
    public String getAbbreviation() {
        return this.abbreviation;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String toString(){
        return "{id: " + this.id + ", externalId: " + this.externalId + ", name: " + this.name + ", abbreviation: " + this.abbreviation + "}";
    }
}
