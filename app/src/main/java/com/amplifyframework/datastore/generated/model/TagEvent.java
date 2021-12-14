package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
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

/** This is an auto generated class representing the TagEvent type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "TagEvents", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE })
})
@Index(name = "byTagEvent", fields = {"tagID","eventID"})
@Index(name = "byEventTag", fields = {"eventID","tagID"})
public final class TagEvent implements Model {
  public static final QueryField ID = field("TagEvent", "id");
  public static final QueryField TAG_ID = field("TagEvent", "tagID");
  public static final QueryField EVENT_ID = field("TagEvent", "eventID");
  public static final QueryField TAG = field("TagEvent", "tagID");
  public static final QueryField EVENT = field("TagEvent", "eventID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String tagID;
  private final @ModelField(targetType="ID", isRequired = true) String eventID;
  private final @ModelField(targetType="Tag", isRequired = true) @BelongsTo(targetName = "tagID", type = Tag.class) Tag tag;
  private final @ModelField(targetType="Event", isRequired = true) @BelongsTo(targetName = "eventID", type = Event.class) Event event;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getTagId() {
      return tagID;
  }
  
  public String getEventId() {
      return eventID;
  }
  
  public Tag getTag() {
      return tag;
  }
  
  public Event getEvent() {
      return event;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private TagEvent(String id, String tagID, String eventID, Tag tag, Event event) {
    this.id = id;
    this.tagID = tagID;
    this.eventID = eventID;
    this.tag = tag;
    this.event = event;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      TagEvent tagEvent = (TagEvent) obj;
      return ObjectsCompat.equals(getId(), tagEvent.getId()) &&
              ObjectsCompat.equals(getTagId(), tagEvent.getTagId()) &&
              ObjectsCompat.equals(getEventId(), tagEvent.getEventId()) &&
              ObjectsCompat.equals(getTag(), tagEvent.getTag()) &&
              ObjectsCompat.equals(getEvent(), tagEvent.getEvent()) &&
              ObjectsCompat.equals(getCreatedAt(), tagEvent.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), tagEvent.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTagId())
      .append(getEventId())
      .append(getTag())
      .append(getEvent())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("TagEvent {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("tagID=" + String.valueOf(getTagId()) + ", ")
      .append("eventID=" + String.valueOf(getEventId()) + ", ")
      .append("tag=" + String.valueOf(getTag()) + ", ")
      .append("event=" + String.valueOf(getEvent()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TagIdStep builder() {
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
  public static TagEvent justId(String id) {
    return new TagEvent(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      tagID,
      eventID,
      tag,
      event);
  }
  public interface TagIdStep {
    EventIdStep tagId(String tagId);
  }
  

  public interface EventIdStep {
    TagStep eventId(String eventId);
  }
  

  public interface TagStep {
    EventStep tag(Tag tag);
  }
  

  public interface EventStep {
    BuildStep event(Event event);
  }
  

  public interface BuildStep {
    TagEvent build();
    BuildStep id(String id);
  }
  

  public static class Builder implements TagIdStep, EventIdStep, TagStep, EventStep, BuildStep {
    private String id;
    private String tagID;
    private String eventID;
    private Tag tag;
    private Event event;
    @Override
     public TagEvent build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new TagEvent(
          id,
          tagID,
          eventID,
          tag,
          event);
    }
    
    @Override
     public EventIdStep tagId(String tagId) {
        Objects.requireNonNull(tagId);
        this.tagID = tagId;
        return this;
    }
    
    @Override
     public TagStep eventId(String eventId) {
        Objects.requireNonNull(eventId);
        this.eventID = eventId;
        return this;
    }
    
    @Override
     public EventStep tag(Tag tag) {
        Objects.requireNonNull(tag);
        this.tag = tag;
        return this;
    }
    
    @Override
     public BuildStep event(Event event) {
        Objects.requireNonNull(event);
        this.event = event;
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
    private CopyOfBuilder(String id, String tagId, String eventId, Tag tag, Event event) {
      super.id(id);
      super.tagId(tagId)
        .eventId(eventId)
        .tag(tag)
        .event(event);
    }
    
    @Override
     public CopyOfBuilder tagId(String tagId) {
      return (CopyOfBuilder) super.tagId(tagId);
    }
    
    @Override
     public CopyOfBuilder eventId(String eventId) {
      return (CopyOfBuilder) super.eventId(eventId);
    }
    
    @Override
     public CopyOfBuilder tag(Tag tag) {
      return (CopyOfBuilder) super.tag(tag);
    }
    
    @Override
     public CopyOfBuilder event(Event event) {
      return (CopyOfBuilder) super.event(event);
    }
  }
  
}
