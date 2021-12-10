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

/** This is an auto generated class representing the Group type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Groups", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.DELETE })
})
@Index(name = "byName", fields = {"id"})
public final class Group implements Model {
  public static final QueryField ID = field("Group", "id");
  public static final QueryField NAME = field("Group", "name");
  public static final QueryField DESCRIPTION = field("Group", "description");
  public static final QueryField IMAGE = field("Group", "image");
  public static final QueryField STATUS = field("Group", "status");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="String") String image;
  private final @ModelField(targetType="Status") Status status;
  private final @ModelField(targetType="Event") @HasMany(associatedWith = "group", type = Event.class) List<Event> events = null;
  private final @ModelField(targetType="GroupMembers") @HasMany(associatedWith = "group", type = GroupMembers.class) List<GroupMembers> users = null;
  private final @ModelField(targetType="TagGroup") @HasMany(associatedWith = "group", type = TagGroup.class) List<TagGroup> tags = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdOn;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedOn;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getDescription() {
      return description;
  }
  
  public String getImage() {
      return image;
  }
  
  public Status getStatus() {
      return status;
  }
  
  public List<Event> getEvents() {
      return events;
  }
  
  public List<GroupMembers> getUsers() {
      return users;
  }
  
  public List<TagGroup> getTags() {
      return tags;
  }
  
  public Temporal.DateTime getCreatedOn() {
      return createdOn;
  }
  
  public Temporal.DateTime getUpdatedOn() {
      return updatedOn;
  }
  
  private Group(String id, String name, String description, String image, Status status) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.image = image;
    this.status = status;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Group group = (Group) obj;
      return ObjectsCompat.equals(getId(), group.getId()) &&
              ObjectsCompat.equals(getName(), group.getName()) &&
              ObjectsCompat.equals(getDescription(), group.getDescription()) &&
              ObjectsCompat.equals(getImage(), group.getImage()) &&
              ObjectsCompat.equals(getStatus(), group.getStatus()) &&
              ObjectsCompat.equals(getCreatedOn(), group.getCreatedOn()) &&
              ObjectsCompat.equals(getUpdatedOn(), group.getUpdatedOn());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getDescription())
      .append(getImage())
      .append(getStatus())
      .append(getCreatedOn())
      .append(getUpdatedOn())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Group {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("image=" + String.valueOf(getImage()) + ", ")
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
  public static Group justId(String id) {
    return new Group(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      description,
      image,
      status);
  }
  public interface NameStep {
    BuildStep name(String name);
  }
  

  public interface BuildStep {
    Group build();
    BuildStep id(String id);
    BuildStep description(String description);
    BuildStep image(String image);
    BuildStep status(Status status);
  }
  

  public static class Builder implements NameStep, BuildStep {
    private String id;
    private String name;
    private String description;
    private String image;
    private Status status;
    @Override
     public Group build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Group(
          id,
          name,
          description,
          image,
          status);
    }
    
    @Override
     public BuildStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep description(String description) {
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep image(String image) {
        this.image = image;
        return this;
    }
    
    @Override
     public BuildStep status(Status status) {
        this.status = status;
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
    private CopyOfBuilder(String id, String name, String description, String image, Status status) {
      super.id(id);
      super.name(name)
        .description(description)
        .image(image)
        .status(status);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder image(String image) {
      return (CopyOfBuilder) super.image(image);
    }
    
    @Override
     public CopyOfBuilder status(Status status) {
      return (CopyOfBuilder) super.status(status);
    }
  }
  
}
