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
    public static final Player NullPlayer = new Player(null, null, null, null, null);

    @JsonView(PublicView.class)
    private String name;

    public Player() {}
    public Player(Long id, String externalId, String name, Timestamp createdTimestamp, Timestamp modifiedTimestamp){
        super(id, externalId, createdTimestamp, modifiedTimestamp);
        this.name = name;
    }
    public Player(Player player){
        super(player);
        if (player == null) throw new IllegalArgumentException("Parameter player for Player constructor cannot be null");
        this.name = player.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return "{id: " + this.id + ", name: " + this.name + "}";
    }
}
