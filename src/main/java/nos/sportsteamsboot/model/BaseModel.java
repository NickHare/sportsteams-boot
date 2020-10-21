package nos.sportsteamsboot.model;

import com.fasterxml.jackson.annotation.JsonView;
import nos.sportsteamsboot.view.DetailedView;
import nos.sportsteamsboot.view.PublicView;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;

@MappedSuperclass
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String externalId;

    @CreatedDate
    protected Timestamp createdTimestamp;

    @LastModifiedDate
    protected Timestamp modifiedTimestamp;

    @JsonView(PublicView.class) public Long getId() {
        return id;
    }
    @JsonView(DetailedView.class) public String getExternalId() {
        return externalId;
    }
    @JsonView(DetailedView.class) public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }
    @JsonView(DetailedView.class) public Timestamp getModifiedTimestamp() {
        return modifiedTimestamp;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }
    public void setModifiedTimestamp(Timestamp modifiedTimestamp) {
        this.modifiedTimestamp = modifiedTimestamp;
    }
}
