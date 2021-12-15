package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.annotations.HasMany;

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

/** This is an auto generated class representing the Event type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Events", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Event implements Model {
  public static final QueryField ID = field("Event", "id");
  public static final QueryField NAME = field("Event", "name");
  public static final QueryField DESCRIPTION = field("Event", "description");
  public static final QueryField IMAGE = field("Event", "image");
  public static final QueryField LOCATION = field("Event", "location");
  public static final QueryField WHEN = field("Event", "when");
  public static final QueryField STATUS = field("Event", "status");
  public static final QueryField GROUP = field("Event", "groupEventsId");
  public static final QueryField GROUP_EVENTS_ID = field("Event", "groupEventsId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="String") String image;
  private final @ModelField(targetType="String") String location;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime when;
  private final @ModelField(targetType="EventStatus", isRequired = true) EventStatus status;
  private final @ModelField(targetType="Group") @BelongsTo(targetName = "groupEventsId", type = Group.class) Group group;
  private final @ModelField(targetType="EventMembers") @HasMany(associatedWith = "eventMembersId", type = EventMembers.class) List<EventMembers> members = null;
  private final @ModelField(targetType="TagEvent") @HasMany(associatedWith = "eventTagsId", type = TagEvent.class) List<TagEvent> tags = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdOn;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedOn;
  private final @ModelField(targetType="ID") String groupEventsId;
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
  
  public String getLocation() {
      return location;
  }
  
  public Temporal.DateTime getWhen() {
      return when;
  }
  
  public EventStatus getStatus() {
      return status;
  }
  
  public Group getGroup() {
      return group;
  }
  
  public List<EventMembers> getMembers() {
      return members;
  }
  
  public List<TagEvent> getTags() {
      return tags;
  }
  
  public Temporal.DateTime getCreatedOn() {
      return createdOn;
  }
  
  public Temporal.DateTime getUpdatedOn() {
      return updatedOn;
  }
  
  public String getGroupEventsId() {
      return groupEventsId;
  }
  
  private Event(String id, String name, String description, String image, String location, Temporal.DateTime when, EventStatus status, Group group, String groupEventsId) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.image = image;
    this.location = location;
    this.when = when;
    this.status = status;
    this.group = group;
    this.groupEventsId = groupEventsId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Event event = (Event) obj;
      return ObjectsCompat.equals(getId(), event.getId()) &&
              ObjectsCompat.equals(getName(), event.getName()) &&
              ObjectsCompat.equals(getDescription(), event.getDescription()) &&
              ObjectsCompat.equals(getImage(), event.getImage()) &&
              ObjectsCompat.equals(getLocation(), event.getLocation()) &&
              ObjectsCompat.equals(getWhen(), event.getWhen()) &&
              ObjectsCompat.equals(getStatus(), event.getStatus()) &&
              ObjectsCompat.equals(getGroup(), event.getGroup()) &&
              ObjectsCompat.equals(getCreatedOn(), event.getCreatedOn()) &&
              ObjectsCompat.equals(getUpdatedOn(), event.getUpdatedOn()) &&
              ObjectsCompat.equals(getGroupEventsId(), event.getGroupEventsId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getDescription())
      .append(getImage())
      .append(getLocation())
      .append(getWhen())
      .append(getStatus())
      .append(getGroup())
      .append(getCreatedOn())
      .append(getUpdatedOn())
      .append(getGroupEventsId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Event {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("image=" + String.valueOf(getImage()) + ", ")
      .append("location=" + String.valueOf(getLocation()) + ", ")
      .append("when=" + String.valueOf(getWhen()) + ", ")
      .append("status=" + String.valueOf(getStatus()) + ", ")
      .append("group=" + String.valueOf(getGroup()) + ", ")
      .append("createdOn=" + String.valueOf(getCreatedOn()) + ", ")
      .append("updatedOn=" + String.valueOf(getUpdatedOn()) + ", ")
      .append("groupEventsId=" + String.valueOf(getGroupEventsId()))
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
  public static Event justId(String id) {
    return new Event(
      id,
      null,
      null,
      null,
      null,
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
      location,
      when,
      status,
      group,
      groupEventsId);
  }
  public interface NameStep {
    WhenStep name(String name);
  }
  

  public interface WhenStep {
    StatusStep when(Temporal.DateTime when);
  }
  

  public interface StatusStep {
    BuildStep status(EventStatus status);
  }
  

  public interface BuildStep {
    Event build();
    BuildStep id(String id);
    BuildStep description(String description);
    BuildStep image(String image);
    BuildStep location(String location);
    BuildStep group(Group group);
    BuildStep groupEventsId(String groupEventsId);
  }
  

  public static class Builder implements NameStep, WhenStep, StatusStep, BuildStep {
    private String id;
    private String name;
    private Temporal.DateTime when;
    private EventStatus status;
    private String description;
    private String image;
    private String location;
    private Group group;
    private String groupEventsId;
    @Override
     public Event build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Event(
          id,
          name,
          description,
          image,
          location,
          when,
          status,
          group,
          groupEventsId);
    }
    
    @Override
     public WhenStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public StatusStep when(Temporal.DateTime when) {
        Objects.requireNonNull(when);
        this.when = when;
        return this;
    }
    
    @Override
     public BuildStep status(EventStatus status) {
        Objects.requireNonNull(status);
        this.status = status;
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
     public BuildStep location(String location) {
        this.location = location;
        return this;
    }
    
    @Override
     public BuildStep group(Group group) {
        this.group = group;
        return this;
    }
    
    @Override
     public BuildStep groupEventsId(String groupEventsId) {
        this.groupEventsId = groupEventsId;
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
    private CopyOfBuilder(String id, String name, String description, String image, String location, Temporal.DateTime when, EventStatus status, Group group, String groupEventsId) {
      super.id(id);
      super.name(name)
        .when(when)
        .status(status)
        .description(description)
        .image(image)
        .location(location)
        .group(group)
        .groupEventsId(groupEventsId);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder when(Temporal.DateTime when) {
      return (CopyOfBuilder) super.when(when);
    }
    
    @Override
     public CopyOfBuilder status(EventStatus status) {
      return (CopyOfBuilder) super.status(status);
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
     public CopyOfBuilder location(String location) {
      return (CopyOfBuilder) super.location(location);
    }
    
    @Override
     public CopyOfBuilder group(Group group) {
      return (CopyOfBuilder) super.group(group);
    }
    
    @Override
     public CopyOfBuilder groupEventsId(String groupEventsId) {
      return (CopyOfBuilder) super.groupEventsId(groupEventsId);
    }
  }
  
}
