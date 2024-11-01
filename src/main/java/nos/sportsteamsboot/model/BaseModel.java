package nos.sportsteamsboot.model;

import com.fasterxml.jackson.annotation.JsonView;
import nos.sportsteamsboot.view.DetailedView;
import nos.sportsteamsboot.view.PublicView;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String externalId;

    @CreatedDate
    protected Timestamp createdTimestamp;

    @LastModifiedDate
    protected Timestamp modifiedTimestamp;

    public BaseModel(){

    }
    public BaseModel(Long id, String externalId, Timestamp createdTimestamp, Timestamp modifiedTimestamp){
        this.id = id;
        this.externalId = externalId;
        this.createdTimestamp = createdTimestamp;
        this.modifiedTimestamp = modifiedTimestamp;
    }
    public BaseModel(BaseModel baseModel){
        if (baseModel == null) throw new IllegalArgumentException("Parameter baseModel for BaseModel constructor cannot be null");
        this.externalId = baseModel.externalId;
    }

    @JsonView(PublicView.class) public Long getId() {
        return id;
    }
    @JsonView(PublicView.class) public String getExternalId() {
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

    public boolean isIdentical(BaseModel other){
        return (this.id == other.id && this.externalId == other.externalId && this.createdTimestamp == other.createdTimestamp && this.modifiedTimestamp == other.modifiedTimestamp);
    }

//    public abstract boolean isEqual(BaseModel other);

    public boolean isSame(BaseModel other){
        return (this.id == other.id && this.externalId == other.externalId);
    }
}
