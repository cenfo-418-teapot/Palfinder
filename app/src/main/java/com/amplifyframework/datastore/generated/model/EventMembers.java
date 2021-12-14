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
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE })
})
@Index(name = "byUserEvent", fields = {"userID","eventID"})
@Index(name = "byEventUser", fields = {"eventID","userID"})
public final class EventMembers implements Model {
  public static final QueryField ID = field("EventMembers", "id");
  public static final QueryField USER_ID = field("EventMembers", "userID");
  public static final QueryField EVENT_ID = field("EventMembers", "eventID");
  public static final QueryField ROLE = field("EventMembers", "role");
  public static final QueryField USER = field("EventMembers", "userID");
  public static final QueryField EVENT = field("EventMembers", "eventID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String userID;
  private final @ModelField(targetType="ID", isRequired = true) String eventID;
  private final @ModelField(targetType="EventRoles", isRequired = true) EventRoles role;
  private final @ModelField(targetType="User", isRequired = true) @BelongsTo(targetName = "userID", type = User.class) User user;
  private final @ModelField(targetType="Event", isRequired = true) @BelongsTo(targetName = "eventID", type = Event.class) Event event;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getUserId() {
      return userID;
  }
  
  public String getEventId() {
      return eventID;
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
  
  private EventMembers(String id, String userID, String eventID, EventRoles role, User user, Event event) {
    this.id = id;
    this.userID = userID;
    this.eventID = eventID;
    this.role = role;
    this.user = user;
    this.event = event;
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
              ObjectsCompat.equals(getUserId(), eventMembers.getUserId()) &&
              ObjectsCompat.equals(getEventId(), eventMembers.getEventId()) &&
              ObjectsCompat.equals(getRole(), eventMembers.getRole()) &&
              ObjectsCompat.equals(getUser(), eventMembers.getUser()) &&
              ObjectsCompat.equals(getEvent(), eventMembers.getEvent()) &&
              ObjectsCompat.equals(getCreatedAt(), eventMembers.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), eventMembers.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUserId())
      .append(getEventId())
      .append(getRole())
      .append(getUser())
      .append(getEvent())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("EventMembers {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("userID=" + String.valueOf(getUserId()) + ", ")
      .append("eventID=" + String.valueOf(getEventId()) + ", ")
      .append("role=" + String.valueOf(getRole()) + ", ")
      .append("user=" + String.valueOf(getUser()) + ", ")
      .append("event=" + String.valueOf(getEvent()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static UserIdStep builder() {
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
      userID,
      eventID,
      role,
      user,
      event);
  }
  public interface UserIdStep {
    EventIdStep userId(String userId);
  }
  

  public interface EventIdStep {
    RoleStep eventId(String eventId);
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
  }
  

  public static class Builder implements UserIdStep, EventIdStep, RoleStep, UserStep, EventStep, BuildStep {
    private String id;
    private String userID;
    private String eventID;
    private EventRoles role;
    private User user;
    private Event event;
    @Override
     public EventMembers build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new EventMembers(
          id,
          userID,
          eventID,
          role,
          user,
          event);
    }
    
    @Override
     public EventIdStep userId(String userId) {
        Objects.requireNonNull(userId);
        this.userID = userId;
        return this;
    }
    
    @Override
     public RoleStep eventId(String eventId) {
        Objects.requireNonNull(eventId);
        this.eventID = eventId;
        return this;
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
    private CopyOfBuilder(String id, String userId, String eventId, EventRoles role, User user, Event event) {
      super.id(id);
      super.userId(userId)
        .eventId(eventId)
        .role(role)
        .user(user)
        .event(event);
    }
    
    @Override
     public CopyOfBuilder userId(String userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
    
    @Override
     public CopyOfBuilder eventId(String eventId) {
      return (CopyOfBuilder) super.eventId(eventId);
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
  }
  
}
