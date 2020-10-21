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

@Entity
public class Player extends BaseModel {
    public static final Player EmptyPlayer = new Player(null, null, "", null, null);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(PublicView.class)
    protected Long id;

    @NotNull
    @NotBlank
    @JsonView(PublicView.class)
    private String name;

    public Player() {}
    public Player(Long id, String externalId, String name, Timestamp createdTimestamp, Timestamp modifiedTimestamp){
        this.id = id;
        this.externalId = externalId;
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

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return "{id: " + this.id + ", name: " + this.name + "}";
    }
}
