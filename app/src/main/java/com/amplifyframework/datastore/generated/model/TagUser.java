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

/** This is an auto generated class representing the TagUser type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "TagUsers", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE })
})
@Index(name = "byTagUser", fields = {"tagID","userID"})
@Index(name = "byUserTag", fields = {"userID","tagID"})
public final class TagUser implements Model {
  public static final QueryField ID = field("TagUser", "id");
  public static final QueryField TAG_ID = field("TagUser", "tagID");
  public static final QueryField USER_ID = field("TagUser", "userID");
  public static final QueryField TAG = field("TagUser", "tagID");
  public static final QueryField USER = field("TagUser", "userID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String tagID;
  private final @ModelField(targetType="ID", isRequired = true) String userID;
  private final @ModelField(targetType="Tag", isRequired = true) @BelongsTo(targetName = "tagID", type = Tag.class) Tag tag;
  private final @ModelField(targetType="User", isRequired = true) @BelongsTo(targetName = "userID", type = User.class) User user;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getTagId() {
      return tagID;
  }
  
  public String getUserId() {
      return userID;
  }
  
  public Tag getTag() {
      return tag;
  }
  
  public User getUser() {
      return user;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private TagUser(String id, String tagID, String userID, Tag tag, User user) {
    this.id = id;
    this.tagID = tagID;
    this.userID = userID;
    this.tag = tag;
    this.user = user;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      TagUser tagUser = (TagUser) obj;
      return ObjectsCompat.equals(getId(), tagUser.getId()) &&
              ObjectsCompat.equals(getTagId(), tagUser.getTagId()) &&
              ObjectsCompat.equals(getUserId(), tagUser.getUserId()) &&
              ObjectsCompat.equals(getTag(), tagUser.getTag()) &&
              ObjectsCompat.equals(getUser(), tagUser.getUser()) &&
              ObjectsCompat.equals(getCreatedAt(), tagUser.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), tagUser.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTagId())
      .append(getUserId())
      .append(getTag())
      .append(getUser())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("TagUser {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("tagID=" + String.valueOf(getTagId()) + ", ")
      .append("userID=" + String.valueOf(getUserId()) + ", ")
      .append("tag=" + String.valueOf(getTag()) + ", ")
      .append("user=" + String.valueOf(getUser()) + ", ")
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
  public static TagUser justId(String id) {
    return new TagUser(
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
      userID,
      tag,
      user);
  }
  public interface TagIdStep {
    UserIdStep tagId(String tagId);
  }
  

  public interface UserIdStep {
    TagStep userId(String userId);
  }
  

  public interface TagStep {
    UserStep tag(Tag tag);
  }
  

  public interface UserStep {
    BuildStep user(User user);
  }
  

  public interface BuildStep {
    TagUser build();
    BuildStep id(String id);
  }
  

  public static class Builder implements TagIdStep, UserIdStep, TagStep, UserStep, BuildStep {
    private String id;
    private String tagID;
    private String userID;
    private Tag tag;
    private User user;
    @Override
     public TagUser build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new TagUser(
          id,
          tagID,
          userID,
          tag,
          user);
    }
    
    @Override
     public UserIdStep tagId(String tagId) {
        Objects.requireNonNull(tagId);
        this.tagID = tagId;
        return this;
    }
    
    @Override
     public TagStep userId(String userId) {
        Objects.requireNonNull(userId);
        this.userID = userId;
        return this;
    }
    
    @Override
     public UserStep tag(Tag tag) {
        Objects.requireNonNull(tag);
        this.tag = tag;
        return this;
    }
    
    @Override
     public BuildStep user(User user) {
        Objects.requireNonNull(user);
        this.user = user;
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
    private CopyOfBuilder(String id, String tagId, String userId, Tag tag, User user) {
      super.id(id);
      super.tagId(tagId)
        .userId(userId)
        .tag(tag)
        .user(user);
    }
    
    @Override
     public CopyOfBuilder tagId(String tagId) {
      return (CopyOfBuilder) super.tagId(tagId);
    }
    
    @Override
     public CopyOfBuilder userId(String userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
    
    @Override
     public CopyOfBuilder tag(Tag tag) {
      return (CopyOfBuilder) super.tag(tag);
    }
    
    @Override
     public CopyOfBuilder user(User user) {
      return (CopyOfBuilder) super.user(user);
    }
  }
  
}
