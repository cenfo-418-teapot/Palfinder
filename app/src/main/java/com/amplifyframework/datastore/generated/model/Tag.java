package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasMany;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Tag type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Tags", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE })
})
public final class Tag implements Model {
  public static final QueryField ID = field("Tag", "id");
  public static final QueryField NAME = field("Tag", "name");
  public static final QueryField USES = field("Tag", "uses");
  public static final QueryField STATUS = field("Tag", "status");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="Int") Integer uses;
  private final @ModelField(targetType="TagStatus", isRequired = true) TagStatus status;
  private final @ModelField(targetType="TagUser") @HasMany(associatedWith = "tag", type = TagUser.class) List<TagUser> users = null;
  private final @ModelField(targetType="TagGroup") @HasMany(associatedWith = "tag", type = TagGroup.class) List<TagGroup> groups = null;
  private final @ModelField(targetType="TagEvent") @HasMany(associatedWith = "tag", type = TagEvent.class) List<TagEvent> events = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdOn;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedOn;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public Integer getUses() {
      return uses;
  }
  
  public TagStatus getStatus() {
      return status;
  }
  
  public List<TagUser> getUsers() {
      return users;
  }
  
  public List<TagGroup> getGroups() {
      return groups;
  }
  
  public List<TagEvent> getEvents() {
      return events;
  }
  
  public Temporal.DateTime getCreatedOn() {
      return createdOn;
  }
  
  public Temporal.DateTime getUpdatedOn() {
      return updatedOn;
  }
  
  private Tag(String id, String name, Integer uses, TagStatus status) {
    this.id = id;
    this.name = name;
    this.uses = uses;
    this.status = status;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Tag tag = (Tag) obj;
      return ObjectsCompat.equals(getId(), tag.getId()) &&
              ObjectsCompat.equals(getName(), tag.getName()) &&
              ObjectsCompat.equals(getUses(), tag.getUses()) &&
              ObjectsCompat.equals(getStatus(), tag.getStatus()) &&
              ObjectsCompat.equals(getCreatedOn(), tag.getCreatedOn()) &&
              ObjectsCompat.equals(getUpdatedOn(), tag.getUpdatedOn());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getUses())
      .append(getStatus())
      .append(getCreatedOn())
      .append(getUpdatedOn())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Tag {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("uses=" + String.valueOf(getUses()) + ", ")
      .append("status=" + String.valueOf(getStatus()) + ", ")
      .append("createdOn=" + String.valueOf(getCreatedOn()) + ", ")
      .append("updatedOn=" + String.valueOf(getUpdatedOn()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Tag justId(String id) {
    return new Tag(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      uses,
      status);
  }
  public interface NameStep {
    StatusStep name(String name);
  }
  

  public interface StatusStep {
    BuildStep status(TagStatus status);
  }
  

  public interface BuildStep {
    Tag build();
    BuildStep id(String id);
    BuildStep uses(Integer uses);
  }
  

  public static class Builder implements NameStep, StatusStep, BuildStep {
    private String id;
    private String name;
    private TagStatus status;
    private Integer uses;
    @Override
     public Tag build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Tag(
          id,
          name,
          uses,
          status);
    }
    
    @Override
     public StatusStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep status(TagStatus status) {
        Objects.requireNonNull(status);
        this.status = status;
        return this;
    }
    
    @Override
     public BuildStep uses(Integer uses) {
        this.uses = uses;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, Integer uses, TagStatus status) {
      super.id(id);
      super.name(name)
        .status(status)
        .uses(uses);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder status(TagStatus status) {
      return (CopyOfBuilder) super.status(status);
    }
    
    @Override
     public CopyOfBuilder uses(Integer uses) {
      return (CopyOfBuilder) super.uses(uses);
    }
  }
  
}
