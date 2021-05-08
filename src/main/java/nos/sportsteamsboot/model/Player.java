package nos.sportsteamsboot.model;

import com.fasterxml.jackson.annotation.JsonView;

import nos.sportsteamsboot.view.PublicView;

import javax.persistence.Entity;

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
