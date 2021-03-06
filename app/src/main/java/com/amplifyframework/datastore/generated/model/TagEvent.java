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
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class TagEvent implements Model {
  public static final QueryField ID = field("TagEvent", "id");
  public static final QueryField TAG = field("TagEvent", "tagEventsId");
  public static final QueryField EVENT = field("TagEvent", "eventTagsId");
  public static final QueryField TAG_EVENTS_ID = field("TagEvent", "tagEventsId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Tag", isRequired = true) @BelongsTo(targetName = "tagEventsId", type = Tag.class) Tag tag;
  private final @ModelField(targetType="Event", isRequired = true) @BelongsTo(targetName = "eventTagsId", type = Event.class) Event event;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="ID") String tagEventsId;
  public String getId() {
      return id;
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
  
  public String getTagEventsId() {
      return tagEventsId;
  }
  
  private TagEvent(String id, Tag tag, Event event, String tagEventsId) {
    this.id = id;
    this.tag = tag;
    this.event = event;
    this.tagEventsId = tagEventsId;
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
              ObjectsCompat.equals(getTag(), tagEvent.getTag()) &&
              ObjectsCompat.equals(getEvent(), tagEvent.getEvent()) &&
              ObjectsCompat.equals(getCreatedAt(), tagEvent.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), tagEvent.getUpdatedAt()) &&
              ObjectsCompat.equals(getTagEventsId(), tagEvent.getTagEventsId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTag())
      .append(getEvent())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getTagEventsId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("TagEvent {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("tag=" + String.valueOf(getTag()) + ", ")
      .append("event=" + String.valueOf(getEvent()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("tagEventsId=" + String.valueOf(getTagEventsId()))
      .append("}")
      .toString();
  }
  
  public static TagStep builder() {
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
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      tag,
      event,
      tagEventsId);
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
    BuildStep tagEventsId(String tagEventsId);
  }
  

  public static class Builder implements TagStep, EventStep, BuildStep {
    private String id;
    private Tag tag;
    private Event event;
    private String tagEventsId;
    @Override
     public TagEvent build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new TagEvent(
          id,
          tag,
          event,
          tagEventsId);
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
    
    @Override
     public BuildStep tagEventsId(String tagEventsId) {
        this.tagEventsId = tagEventsId;
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
    private CopyOfBuilder(String id, Tag tag, Event event, String tagEventsId) {
      super.id(id);
      super.tag(tag)
        .event(event)
        .tagEventsId(tagEventsId);
    }
    
    @Override
     public CopyOfBuilder tag(Tag tag) {
      return (CopyOfBuilder) super.tag(tag);
    }
    
    @Override
     public CopyOfBuilder event(Event event) {
      return (CopyOfBuilder) super.event(event);
    }
    
    @Override
     public CopyOfBuilder tagEventsId(String tagEventsId) {
      return (CopyOfBuilder) super.tagEventsId(tagEventsId);
    }
  }
  
}
