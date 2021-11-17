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

/** This is an auto generated class representing the UserProfile type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "UserProfiles", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.DELETE, ModelOperation.UPDATE })
})
@Index(name = "byEmail", fields = {"email"})
public final class UserProfile implements Model {
  public static final QueryField ID = field("UserProfile", "id");
  public static final QueryField EMAIL = field("UserProfile", "email");
  public static final QueryField USERNAME = field("UserProfile", "username");
  public static final QueryField NAME = field("UserProfile", "name");
  public static final QueryField LAST_NAME = field("UserProfile", "lastName");
  public static final QueryField PHONE_NUMBER = field("UserProfile", "phoneNumber");
  public static final QueryField PHOTO = field("UserProfile", "photo");
  public static final QueryField POSTAL_CODE = field("UserProfile", "postalCode");
  public static final QueryField STATE = field("UserProfile", "state");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String email;
  private final @ModelField(targetType="String", isRequired = true) String username;
  private final @ModelField(targetType="String") String name;
  private final @ModelField(targetType="String") String lastName;
  private final @ModelField(targetType="String", isRequired = true) String phoneNumber;
  private final @ModelField(targetType="String") String photo;
  private final @ModelField(targetType="String") String postalCode;
  private final @ModelField(targetType="State", isRequired = true) State state;
  private final @ModelField(targetType="GroupMembers") @HasMany(associatedWith = "userProfile", type = GroupMembers.class) List<GroupMembers> groups = null;
  private final @ModelField(targetType="EventMembers") @HasMany(associatedWith = "userProfile", type = EventMembers.class) List<EventMembers> events = null;
  private final @ModelField(targetType="TagUserProfile") @HasMany(associatedWith = "userProfile", type = TagUserProfile.class) List<TagUserProfile> tags = null;
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
  
  public String getPostalCode() {
      return postalCode;
  }
  
  public State getState() {
      return state;
  }
  
  public List<GroupMembers> getGroups() {
      return groups;
  }
  
  public List<EventMembers> getEvents() {
      return events;
  }
  
  public List<TagUserProfile> getTags() {
      return tags;
  }
  
  public Temporal.DateTime getCreatedOn() {
      return createdOn;
  }
  
  public Temporal.DateTime getUpdatedOn() {
      return updatedOn;
  }
  
  private UserProfile(String id, String email, String username, String name, String lastName, String phoneNumber, String photo, String postalCode, State state) {
    this.id = id;
    this.email = email;
    this.username = username;
    this.name = name;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.photo = photo;
    this.postalCode = postalCode;
    this.state = state;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      UserProfile userProfile = (UserProfile) obj;
      return ObjectsCompat.equals(getId(), userProfile.getId()) &&
              ObjectsCompat.equals(getEmail(), userProfile.getEmail()) &&
              ObjectsCompat.equals(getUsername(), userProfile.getUsername()) &&
              ObjectsCompat.equals(getName(), userProfile.getName()) &&
              ObjectsCompat.equals(getLastName(), userProfile.getLastName()) &&
              ObjectsCompat.equals(getPhoneNumber(), userProfile.getPhoneNumber()) &&
              ObjectsCompat.equals(getPhoto(), userProfile.getPhoto()) &&
              ObjectsCompat.equals(getPostalCode(), userProfile.getPostalCode()) &&
              ObjectsCompat.equals(getState(), userProfile.getState()) &&
              ObjectsCompat.equals(getCreatedOn(), userProfile.getCreatedOn()) &&
              ObjectsCompat.equals(getUpdatedOn(), userProfile.getUpdatedOn());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getEmail())
      .append(getUsername())
      .append(getName())
      .append(getLastName())
      .append(getPhoneNumber())
      .append(getPhoto())
      .append(getPostalCode())
      .append(getState())
      .append(getCreatedOn())
      .append(getUpdatedOn())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("UserProfile {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("email=" + String.valueOf(getEmail()) + ", ")
      .append("username=" + String.valueOf(getUsername()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("lastName=" + String.valueOf(getLastName()) + ", ")
      .append("phoneNumber=" + String.valueOf(getPhoneNumber()) + ", ")
      .append("photo=" + String.valueOf(getPhoto()) + ", ")
      .append("postalCode=" + String.valueOf(getPostalCode()) + ", ")
      .append("state=" + String.valueOf(getState()) + ", ")
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
  public static UserProfile justId(String id) {
    return new UserProfile(
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
      name,
      lastName,
      phoneNumber,
      photo,
      postalCode,
      state);
  }
  public interface EmailStep {
    UsernameStep email(String email);
  }
  

  public interface UsernameStep {
    PhoneNumberStep username(String username);
  }
  

  public interface PhoneNumberStep {
    StateStep phoneNumber(String phoneNumber);
  }
  

  public interface StateStep {
    BuildStep state(State state);
  }
  

  public interface BuildStep {
    UserProfile build();
    BuildStep id(String id);
    BuildStep name(String name);
    BuildStep lastName(String lastName);
    BuildStep photo(String photo);
    BuildStep postalCode(String postalCode);
  }
  

  public static class Builder implements EmailStep, UsernameStep, PhoneNumberStep, StateStep, BuildStep {
    private String id;
    private String email;
    private String username;
    private String phoneNumber;
    private State state;
    private String name;
    private String lastName;
    private String photo;
    private String postalCode;
    @Override
     public UserProfile build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new UserProfile(
          id,
          email,
          username,
          name,
          lastName,
          phoneNumber,
          photo,
          postalCode,
          state);
    }
    
    @Override
     public UsernameStep email(String email) {
        Objects.requireNonNull(email);
        this.email = email;
        return this;
    }
    
    @Override
     public PhoneNumberStep username(String username) {
        Objects.requireNonNull(username);
        this.username = username;
        return this;
    }
    
    @Override
     public StateStep phoneNumber(String phoneNumber) {
        Objects.requireNonNull(phoneNumber);
        this.phoneNumber = phoneNumber;
        return this;
    }
    
    @Override
     public BuildStep state(State state) {
        Objects.requireNonNull(state);
        this.state = state;
        return this;
    }
    
    @Override
     public BuildStep name(String name) {
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
    
    @Override
     public BuildStep photo(String photo) {
        this.photo = photo;
        return this;
    }
    
    @Override
     public BuildStep postalCode(String postalCode) {
        this.postalCode = postalCode;
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
    private CopyOfBuilder(String id, String email, String username, String name, String lastName, String phoneNumber, String photo, String postalCode, State state) {
      super.id(id);
      super.email(email)
        .username(username)
        .phoneNumber(phoneNumber)
        .state(state)
        .name(name)
        .lastName(lastName)
        .photo(photo)
        .postalCode(postalCode);
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
     public CopyOfBuilder phoneNumber(String phoneNumber) {
      return (CopyOfBuilder) super.phoneNumber(phoneNumber);
    }
    
    @Override
     public CopyOfBuilder state(State state) {
      return (CopyOfBuilder) super.state(state);
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
     public CopyOfBuilder photo(String photo) {
      return (CopyOfBuilder) super.photo(photo);
    }
    
    @Override
     public CopyOfBuilder postalCode(String postalCode) {
      return (CopyOfBuilder) super.postalCode(postalCode);
    }
  }
  
}
