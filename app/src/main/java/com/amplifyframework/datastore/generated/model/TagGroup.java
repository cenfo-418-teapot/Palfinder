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

/** This is an auto generated class representing the TagGroup type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "TagGroups", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE })
})
@Index(name = "byTagGroup", fields = {"tagID","groupID"})
@Index(name = "byGroupTag", fields = {"groupID","tagID"})
public final class TagGroup implements Model {
  public static final QueryField ID = field("TagGroup", "id");
  public static final QueryField TAG_ID = field("TagGroup", "tagID");
  public static final QueryField GROUP_ID = field("TagGroup", "groupID");
  public static final QueryField TAG = field("TagGroup", "tagID");
  public static final QueryField GROUP = field("TagGroup", "groupID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String tagID;
  private final @ModelField(targetType="ID", isRequired = true) String groupID;
  private final @ModelField(targetType="Tag", isRequired = true) @BelongsTo(targetName = "tagID", type = Tag.class) Tag tag;
  private final @ModelField(targetType="Group", isRequired = true) @BelongsTo(targetName = "groupID", type = Group.class) Group group;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getTagId() {
      return tagID;
  }
  
  public String getGroupId() {
      return groupID;
  }
  
  public Tag getTag() {
      return tag;
  }
  
  public Group getGroup() {
      return group;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private TagGroup(String id, String tagID, String groupID, Tag tag, Group group) {
    this.id = id;
    this.tagID = tagID;
    this.groupID = groupID;
    this.tag = tag;
    this.group = group;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      TagGroup tagGroup = (TagGroup) obj;
      return ObjectsCompat.equals(getId(), tagGroup.getId()) &&
              ObjectsCompat.equals(getTagId(), tagGroup.getTagId()) &&
              ObjectsCompat.equals(getGroupId(), tagGroup.getGroupId()) &&
              ObjectsCompat.equals(getTag(), tagGroup.getTag()) &&
              ObjectsCompat.equals(getGroup(), tagGroup.getGroup()) &&
              ObjectsCompat.equals(getCreatedAt(), tagGroup.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), tagGroup.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTagId())
      .append(getGroupId())
      .append(getTag())
      .append(getGroup())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("TagGroup {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("tagID=" + String.valueOf(getTagId()) + ", ")
      .append("groupID=" + String.valueOf(getGroupId()) + ", ")
      .append("tag=" + String.valueOf(getTag()) + ", ")
      .append("group=" + String.valueOf(getGroup()) + ", ")
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
  public static TagGroup justId(String id) {
    return new TagGroup(
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
      groupID,
      tag,
      group);
  }
  public interface TagIdStep {
    GroupIdStep tagId(String tagId);
  }
  

  public interface GroupIdStep {
    TagStep groupId(String groupId);
  }
  

  public interface TagStep {
    GroupStep tag(Tag tag);
  }
  

  public interface GroupStep {
    BuildStep group(Group group);
  }
  

  public interface BuildStep {
    TagGroup build();
    BuildStep id(String id);
  }
  

  public static class Builder implements TagIdStep, GroupIdStep, TagStep, GroupStep, BuildStep {
    private String id;
    private String tagID;
    private String groupID;
    private Tag tag;
    private Group group;
    @Override
     public TagGroup build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new TagGroup(
          id,
          tagID,
          groupID,
          tag,
          group);
    }
    
    @Override
     public GroupIdStep tagId(String tagId) {
        Objects.requireNonNull(tagId);
        this.tagID = tagId;
        return this;
    }
    
    @Override
     public TagStep groupId(String groupId) {
        Objects.requireNonNull(groupId);
        this.groupID = groupId;
        return this;
    }
    
    @Override
     public GroupStep tag(Tag tag) {
        Objects.requireNonNull(tag);
        this.tag = tag;
        return this;
    }
    
    @Override
     public BuildStep group(Group group) {
        Objects.requireNonNull(group);
        this.group = group;
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
    private CopyOfBuilder(String id, String tagId, String groupId, Tag tag, Group group) {
      super.id(id);
      super.tagId(tagId)
        .groupId(groupId)
        .tag(tag)
        .group(group);
    }
    
    @Override
     public CopyOfBuilder tagId(String tagId) {
      return (CopyOfBuilder) super.tagId(tagId);
    }
    
    @Override
     public CopyOfBuilder groupId(String groupId) {
      return (CopyOfBuilder) super.groupId(groupId);
    }
    
    @Override
     public CopyOfBuilder tag(Tag tag) {
      return (CopyOfBuilder) super.tag(tag);
    }
    
    @Override
     public CopyOfBuilder group(Group group) {
      return (CopyOfBuilder) super.group(group);
    }
  }
  
}
