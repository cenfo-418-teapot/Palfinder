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

/** This is an auto generated class representing the User type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Users", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byEmail", fields = {"email"})
@Index(name = "byUsername", fields = {"username"})
public final class User implements Model {
  public static final QueryField ID = field("User", "id");
  public static final QueryField EMAIL = field("User", "email");
  public static final QueryField USERNAME = field("User", "username");
  public static final QueryField DESCRIPTION = field("User", "description");
  public static final QueryField NAME = field("User", "name");
  public static final QueryField LAST_NAME = field("User", "lastName");
  public static final QueryField PHONE_NUMBER = field("User", "phoneNumber");
  public static final QueryField PHOTO = field("User", "photo");
  public static final QueryField STATUS = field("User", "status");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String email;
  private final @ModelField(targetType="String", isRequired = true) String username;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String", isRequired = true) String lastName;
  private final @ModelField(targetType="String") String phoneNumber;
  private final @ModelField(targetType="String") String photo;
  private final @ModelField(targetType="UserStatus", isRequired = true) UserStatus status;
  private final @ModelField(targetType="Friend") @HasMany(associatedWith = "userFriendsId", type = Friend.class) List<Friend> friends = null;
  private final @ModelField(targetType="GroupMembers") @HasMany(associatedWith = "userGroupsId", type = GroupMembers.class) List<GroupMembers> groups = null;
  private final @ModelField(targetType="EventMembers") @HasMany(associatedWith = "userEventsId", type = EventMembers.class) List<EventMembers> events = null;
  private final @ModelField(targetType="TagUser") @HasMany(associatedWith = "userTagsId", type = TagUser.class) List<TagUser> tags = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdOn;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedOn;
  public String getId() {
      return id;
  }
  
  public String getEmail() {
      return email;
  }
  
  public String getUsername() {
      return username;
  }
  
  public String getDescription() {
      return description;
  }
  
  public String getName() {
      return name;
  }
  
  public String getLastName() {
      return lastName;
  }
  
  public String getPhoneNumber() {
      return phoneNumber;
  }
  
  public String getPhoto() {
      return photo;
  }
  
  public UserStatus getStatus() {
      return status;
  }
  
  public List<Friend> getFriends() {
      return friends;
  }
  
  public List<GroupMembers> getGroups() {
      return groups;
  }
  
  public List<EventMembers> getEvents() {
      return events;
  }
  
  public List<TagUser> getTags() {
      return tags;
  }
  
  public Temporal.DateTime getCreatedOn() {
      return createdOn;
  }
  
  public Temporal.DateTime getUpdatedOn() {
      return updatedOn;
  }
  
  private User(String id, String email, String username, String description, String name, String lastName, String phoneNumber, String photo, UserStatus status) {
    this.id = id;
    this.email = email;
    this.username = username;
    this.description = description;
    this.name = name;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.photo = photo;
    this.status = status;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      User user = (User) obj;
      return ObjectsCompat.equals(getId(), user.getId()) &&
              ObjectsCompat.equals(getEmail(), user.getEmail()) &&
              ObjectsCompat.equals(getUsername(), user.getUsername()) &&
              ObjectsCompat.equals(getDescription(), user.getDescription()) &&
              ObjectsCompat.equals(getName(), user.getName()) &&
              ObjectsCompat.equals(getLastName(), user.getLastName()) &&
              ObjectsCompat.equals(getPhoneNumber(), user.getPhoneNumber()) &&
              ObjectsCompat.equals(getPhoto(), user.getPhoto()) &&
              ObjectsCompat.equals(getStatus(), user.getStatus()) &&
              ObjectsCompat.equals(getCreatedOn(), user.getCreatedOn()) &&
              ObjectsCompat.equals(getUpdatedOn(), user.getUpdatedOn());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getEmail())
      .append(getUsername())
      .append(getDescription())
      .append(getName())
      .append(getLastName())
      .append(getPhoneNumber())
      .append(getPhoto())
      .append(getStatus())
      .append(getCreatedOn())
      .append(getUpdatedOn())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("User {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("email=" + String.valueOf(getEmail()) + ", ")
      .append("username=" + String.valueOf(getUsername()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("lastName=" + String.valueOf(getLastName()) + ", ")
      .append("phoneNumber=" + String.valueOf(getPhoneNumber()) + ", ")
      .append("photo=" + String.valueOf(getPhoto()) + ", ")
      .append("status=" + String.valueOf(getStatus()) + ", ")
      .append("createdOn=" + String.valueOf(getCreatedOn()) + ", ")
      .append("updatedOn=" + String.valueOf(getUpdatedOn()))
      .append("}")
      .toString();
  }
  
  public static EmailStep builder() {
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
  public static User justId(String id) {
    return new User(
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
      email,
      username,
      description,
      name,
      lastName,
      phoneNumber,
      photo,
      status);
  }
  public interface EmailStep {
    UsernameStep email(String email);
  }
  

  public interface UsernameStep {
    NameStep username(String username);
  }
  

  public interface NameStep {
    LastNameStep name(String name);
  }
  

  public interface LastNameStep {
    StatusStep lastName(String lastName);
  }
  

  public interface StatusStep {
    BuildStep status(UserStatus status);
  }
  

  public interface BuildStep {
    User build();
    BuildStep id(String id);
    BuildStep description(String description);
    BuildStep phoneNumber(String phoneNumber);
    BuildStep photo(String photo);
  }
  

  public static class Builder implements EmailStep, UsernameStep, NameStep, LastNameStep, StatusStep, BuildStep {
    private String id;
    private String email;
    private String username;
    private String name;
    private String lastName;
    private UserStatus status;
    private String description;
    private String phoneNumber;
    private String photo;
    @Override
     public User build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new User(
          id,
          email,
          username,
          description,
          name,
          lastName,
          phoneNumber,
          photo,
          status);
    }
    
    @Override
     public UsernameStep email(String email) {
        Objects.requireNonNull(email);
        this.email = email;
        return this;
    }
    
    @Override
     public NameStep username(String username) {
        Objects.requireNonNull(username);
        this.username = username;
        return this;
    }
    
    @Override
     public LastNameStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public StatusStep lastName(String lastName) {
        Objects.requireNonNull(lastName);
        this.lastName = lastName;
        return this;
    }
    
    @Override
     public BuildStep status(UserStatus status) {
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
     public BuildStep phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
    
    @Override
     public BuildStep photo(String photo) {
        this.photo = photo;
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
    private CopyOfBuilder(String id, String email, String username, String description, String name, String lastName, String phoneNumber, String photo, UserStatus status) {
      super.id(id);
      super.email(email)
        .username(username)
        .name(name)
        .lastName(lastName)
        .status(status)
        .description(description)
        .phoneNumber(phoneNumber)
        .photo(photo);
    }
    
    @Override
     public CopyOfBuilder email(String email) {
      return (CopyOfBuilder) super.email(email);
    }
    
    @Override
     public CopyOfBuilder username(String username) {
      return (CopyOfBuilder) super.username(username);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder lastName(String lastName) {
      return (CopyOfBuilder) super.lastName(lastName);
    }
    
    @Override
     public CopyOfBuilder status(UserStatus status) {
      return (CopyOfBuilder) super.status(status);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder phoneNumber(String phoneNumber) {
      return (CopyOfBuilder) super.phoneNumber(phoneNumber);
    }
    
    @Override
     public CopyOfBuilder photo(String photo) {
      return (CopyOfBuilder) super.photo(photo);
    }
  }
  
}
