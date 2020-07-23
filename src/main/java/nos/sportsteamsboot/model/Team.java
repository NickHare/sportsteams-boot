package nos.sportsteamsboot.model;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import nos.sportsteamsboot.view.PublicView;
import nos.sportsteamsboot.view.DetailedView;

import java.sql.Timestamp;
import java.util.Set;

public class Team {
    @JsonView(PublicView.class) @Id private Long id;
    @JsonView(PublicView.class) private String name;
    @JsonView(PublicView.class) Set<Roster> rosters;
    @JsonView(DetailedView.class) @CreatedDate private Timestamp createdTimestamp;
    @JsonView(DetailedView.class) @LastModifiedDate private Timestamp modifiedTimestamp;

    public Team() {}
    public Team(String name, Set<Roster> rosters){
        this.name = name;
        this.rosters = rosters;
    }
    public Team(Long id, String name, Set<Roster> rosters, Timestamp createdTimestamp, Timestamp modifiedTimestamp){
        this.id = id;
        this.name = name;
        this.rosters = rosters;
        this.createdTimestamp = createdTimestamp;
        this.modifiedTimestamp = modifiedTimestamp;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Set<Roster> getRosters() {
        return rosters;
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
    public void setName(String name) {
        this.name = name;
    }
    public void setRosters(Set<Roster> rosters) {
        this.rosters = rosters;
    }
    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }
    public void setModifiedTimestamp(Timestamp modifiedTimestamp) {
        this.modifiedTimestamp = modifiedTimestamp;
    }

    public String toString(){
        return "{id: " + this.id + ", name: " + this.name + "}";
    }
}
