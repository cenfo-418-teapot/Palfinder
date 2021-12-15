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

/** This is an auto generated class representing the EventMembers type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "EventMembers", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class EventMembers implements Model {
  public static final QueryField ID = field("EventMembers", "id");
  public static final QueryField ROLE = field("EventMembers", "role");
  public static final QueryField USER = field("EventMembers", "userEventsId");
  public static final QueryField EVENT = field("EventMembers", "eventMembersId");
  public static final QueryField USER_EVENTS_ID = field("EventMembers", "userEventsId");
  public static final QueryField EVENT_MEMBERS_ID = field("EventMembers", "eventMembersId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="EventRoles", isRequired = true) EventRoles role;
  private final @ModelField(targetType="User", isRequired = true) @BelongsTo(targetName = "userEventsId", type = User.class) User user;
  private final @ModelField(targetType="Event", isRequired = true) @BelongsTo(targetName = "eventMembersId", type = Event.class) Event event;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="ID") String userEventsId;
  private final @ModelField(targetType="ID") String eventMembersId;
  public String getId() {
      return id;
  }
  
  public EventRoles getRole() {
      return role;
  }
  
  public User getUser() {
      return user;
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
  
  public String getUserEventsId() {
      return userEventsId;
  }
  
  public String getEventMembersId() {
      return eventMembersId;
  }
  
  private EventMembers(String id, EventRoles role, User user, Event event, String userEventsId, String eventMembersId) {
    this.id = id;
    this.role = role;
    this.user = user;
    this.event = event;
    this.userEventsId = userEventsId;
    this.eventMembersId = eventMembersId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      EventMembers eventMembers = (EventMembers) obj;
      return ObjectsCompat.equals(getId(), eventMembers.getId()) &&
              ObjectsCompat.equals(getRole(), eventMembers.getRole()) &&
              ObjectsCompat.equals(getUser(), eventMembers.getUser()) &&
              ObjectsCompat.equals(getEvent(), eventMembers.getEvent()) &&
              ObjectsCompat.equals(getCreatedAt(), eventMembers.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), eventMembers.getUpdatedAt()) &&
              ObjectsCompat.equals(getUserEventsId(), eventMembers.getUserEventsId()) &&
              ObjectsCompat.equals(getEventMembersId(), eventMembers.getEventMembersId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getRole())
      .append(getUser())
      .append(getEvent())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getUserEventsId())
      .append(getEventMembersId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("EventMembers {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("role=" + String.valueOf(getRole()) + ", ")
      .append("user=" + String.valueOf(getUser()) + ", ")
      .append("event=" + String.valueOf(getEvent()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("userEventsId=" + String.valueOf(getUserEventsId()) + ", ")
      .append("eventMembersId=" + String.valueOf(getEventMembersId()))
      .append("}")
      .toString();
  }
  
  public static RoleStep builder() {
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
  public static EventMembers justId(String id) {
    return new EventMembers(
      id,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      role,
      user,
      event,
      userEventsId,
      eventMembersId);
  }
  public interface RoleStep {
    UserStep role(EventRoles role);
  }
  

  public interface UserStep {
    EventStep user(User user);
  }
  

  public interface EventStep {
    BuildStep event(Event event);
  }
  

  public interface BuildStep {
    EventMembers build();
    BuildStep id(String id);
    BuildStep userEventsId(String userEventsId);
    BuildStep eventMembersId(String eventMembersId);
  }
  

  public static class Builder implements RoleStep, UserStep, EventStep, BuildStep {
    private String id;
    private EventRoles role;
    private User user;
    private Event event;
    private String userEventsId;
    private String eventMembersId;
    @Override
     public EventMembers build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new EventMembers(
          id,
          role,
          user,
          event,
          userEventsId,
          eventMembersId);
    }
    
    @Override
     public UserStep role(EventRoles role) {
        Objects.requireNonNull(role);
        this.role = role;
        return this;
    }
    
    @Override
     public EventStep user(User user) {
        Objects.requireNonNull(user);
        this.user = user;
        return this;
    }
    
    @Override
     public BuildStep event(Event event) {
        Objects.requireNonNull(event);
        this.event = event;
        return this;
    }
    
    @Override
     public BuildStep userEventsId(String userEventsId) {
        this.userEventsId = userEventsId;
        return this;
    }
    
    @Override
     public BuildStep eventMembersId(String eventMembersId) {
        this.eventMembersId = eventMembersId;
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
    private CopyOfBuilder(String id, EventRoles role, User user, Event event, String userEventsId, String eventMembersId) {
      super.id(id);
      super.role(role)
        .user(user)
        .event(event)
        .userEventsId(userEventsId)
        .eventMembersId(eventMembersId);
    }
    
    @Override
     public CopyOfBuilder role(EventRoles role) {
      return (CopyOfBuilder) super.role(role);
    }
    
    @Override
     public CopyOfBuilder user(User user) {
      return (CopyOfBuilder) super.user(user);
    }
    
    @Override
     public CopyOfBuilder event(Event event) {
      return (CopyOfBuilder) super.event(event);
    }
    
    @Override
     public CopyOfBuilder userEventsId(String userEventsId) {
      return (CopyOfBuilder) super.userEventsId(userEventsId);
    }
    
    @Override
     public CopyOfBuilder eventMembersId(String eventMembersId) {
      return (CopyOfBuilder) super.eventMembersId(eventMembersId);
    }
  }
  
}
