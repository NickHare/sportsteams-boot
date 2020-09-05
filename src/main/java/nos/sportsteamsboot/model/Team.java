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
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(PublicView.class)
    private Long id;

    @NotNull
    @NotBlank
    @JsonView(PublicView.class)
    private String name;

    @CreatedDate
    @JsonView(DetailedView.class)
    private Timestamp createdTimestamp;

    @LastModifiedDate
    @JsonView(DetailedView.class)
    private Timestamp modifiedTimestamp;

    public Team() {}
    public Team(String name){
        this.name = name;
    }
    public Team(Long id, String name, Timestamp createdTimestamp, Timestamp modifiedTimestamp){
        this.id = id;
        this.name = name;
        this.createdTimestamp = createdTimestamp;
        this.modifiedTimestamp = modifiedTimestamp;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
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
