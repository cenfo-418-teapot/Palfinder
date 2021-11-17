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

/** This is an auto generated class representing the Friend type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Friends", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.DELETE, ModelOperation.UPDATE })
})
@Index(name = "byEmail", fields = {"email"})
@Index(name = "byUsername", fields = {"username"})
@Index(name = "byFriends", fields = {"friendID","username"})
public final class Friend implements Model {
  public static final QueryField ID = field("Friend", "id");
  public static final QueryField EMAIL = field("Friend", "email");
  public static final QueryField USERNAME = field("Friend", "username");
  public static final QueryField FRIEND = field("Friend", "friendID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String email;
  private final @ModelField(targetType="String", isRequired = true) String username;
  private final @ModelField(targetType="User") @BelongsTo(targetName = "friendID", type = User.class) User friend;
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
  
  public User getFriend() {
      return friend;
  }
  
  public Temporal.DateTime getCreatedOn() {
      return createdOn;
  }
  
  public Temporal.DateTime getUpdatedOn() {
      return updatedOn;
  }
  
  private Friend(String id, String email, String username, User friend) {
    this.id = id;
    this.email = email;
    this.username = username;
    this.friend = friend;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Friend friend = (Friend) obj;
      return ObjectsCompat.equals(getId(), friend.getId()) &&
              ObjectsCompat.equals(getEmail(), friend.getEmail()) &&
              ObjectsCompat.equals(getUsername(), friend.getUsername()) &&
              ObjectsCompat.equals(getFriend(), friend.getFriend()) &&
              ObjectsCompat.equals(getCreatedOn(), friend.getCreatedOn()) &&
              ObjectsCompat.equals(getUpdatedOn(), friend.getUpdatedOn());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getEmail())
      .append(getUsername())
      .append(getFriend())
      .append(getCreatedOn())
      .append(getUpdatedOn())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Friend {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("email=" + String.valueOf(getEmail()) + ", ")
      .append("username=" + String.valueOf(getUsername()) + ", ")
      .append("friend=" + String.valueOf(getFriend()) + ", ")
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
  public static Friend justId(String id) {
    return new Friend(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      email,
      username,
      friend);
  }
  public interface EmailStep {
    UsernameStep email(String email);
  }
  

  public interface UsernameStep {
    BuildStep username(String username);
  }
  

  public interface BuildStep {
    Friend build();
    BuildStep id(String id);
    BuildStep friend(User friend);
  }
  

  public static class Builder implements EmailStep, UsernameStep, BuildStep {
    private String id;
    private String email;
    private String username;
    private User friend;
    @Override
     public Friend build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Friend(
          id,
          email,
          username,
          friend);
    }
    
    @Override
     public UsernameStep email(String email) {
        Objects.requireNonNull(email);
        this.email = email;
        return this;
    }
    
    @Override
     public BuildStep username(String username) {
        Objects.requireNonNull(username);
        this.username = username;
        return this;
    }
    
    @Override
     public BuildStep friend(User friend) {
        this.friend = friend;
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
    private CopyOfBuilder(String id, String email, String username, User friend) {
      super.id(id);
      super.email(email)
        .username(username)
        .friend(friend);
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
     public CopyOfBuilder friend(User friend) {
      return (CopyOfBuilder) super.friend(friend);
    }
  }
  
}
