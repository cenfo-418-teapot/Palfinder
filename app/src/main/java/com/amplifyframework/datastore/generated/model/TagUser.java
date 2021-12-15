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
public final class TagUser implements Model {
  public static final QueryField ID = field("TagUser", "id");
  public static final QueryField TAG = field("TagUser", "tagUsersId");
  public static final QueryField USER = field("TagUser", "userTagsId");
  public static final QueryField USER_TAGS_ID = field("TagUser", "userTagsId");
  public static final QueryField TAG_USERS_ID = field("TagUser", "tagUsersId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Tag", isRequired = true) @BelongsTo(targetName = "tagUsersId", type = Tag.class) Tag tag;
  private final @ModelField(targetType="User", isRequired = true) @BelongsTo(targetName = "userTagsId", type = User.class) User user;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="ID") String userTagsId;
  private final @ModelField(targetType="ID") String tagUsersId;
  public String getId() {
      return id;
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
  
  public String getUserTagsId() {
      return userTagsId;
  }
  
  public String getTagUsersId() {
      return tagUsersId;
  }
  
  private TagUser(String id, Tag tag, User user, String userTagsId, String tagUsersId) {
    this.id = id;
    this.tag = tag;
    this.user = user;
    this.userTagsId = userTagsId;
    this.tagUsersId = tagUsersId;
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
              ObjectsCompat.equals(getTag(), tagUser.getTag()) &&
              ObjectsCompat.equals(getUser(), tagUser.getUser()) &&
              ObjectsCompat.equals(getCreatedAt(), tagUser.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), tagUser.getUpdatedAt()) &&
              ObjectsCompat.equals(getUserTagsId(), tagUser.getUserTagsId()) &&
              ObjectsCompat.equals(getTagUsersId(), tagUser.getTagUsersId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTag())
      .append(getUser())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getUserTagsId())
      .append(getTagUsersId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("TagUser {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("tag=" + String.valueOf(getTag()) + ", ")
      .append("user=" + String.valueOf(getUser()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("userTagsId=" + String.valueOf(getUserTagsId()) + ", ")
      .append("tagUsersId=" + String.valueOf(getTagUsersId()))
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
      tag,
      user,
      userTagsId,
      tagUsersId);
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
    BuildStep userTagsId(String userTagsId);
    BuildStep tagUsersId(String tagUsersId);
  }
  

  public static class Builder implements TagStep, UserStep, BuildStep {
    private String id;
    private Tag tag;
    private User user;
    private String userTagsId;
    private String tagUsersId;
    @Override
     public TagUser build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new TagUser(
          id,
          tag,
          user,
          userTagsId,
          tagUsersId);
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
    
    @Override
     public BuildStep userTagsId(String userTagsId) {
        this.userTagsId = userTagsId;
        return this;
    }
    
    @Override
     public BuildStep tagUsersId(String tagUsersId) {
        this.tagUsersId = tagUsersId;
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
    private CopyOfBuilder(String id, Tag tag, User user, String userTagsId, String tagUsersId) {
      super.id(id);
      super.tag(tag)
        .user(user)
        .userTagsId(userTagsId)
        .tagUsersId(tagUsersId);
    }
    
    @Override
     public CopyOfBuilder tag(Tag tag) {
      return (CopyOfBuilder) super.tag(tag);
    }
    
    @Override
     public CopyOfBuilder user(User user) {
      return (CopyOfBuilder) super.user(user);
    }
    
    @Override
     public CopyOfBuilder userTagsId(String userTagsId) {
      return (CopyOfBuilder) super.userTagsId(userTagsId);
    }
    
    @Override
     public CopyOfBuilder tagUsersId(String tagUsersId) {
      return (CopyOfBuilder) super.tagUsersId(tagUsersId);
    }
  }
  
}
