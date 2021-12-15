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

/** This is an auto generated class representing the GroupMembers type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "GroupMembers", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class GroupMembers implements Model {
  public static final QueryField ID = field("GroupMembers", "id");
  public static final QueryField ROLE = field("GroupMembers", "role");
  public static final QueryField USER = field("GroupMembers", "userGroupsId");
  public static final QueryField GROUP = field("GroupMembers", "groupUsersId");
  public static final QueryField GROUP_USERS_ID = field("GroupMembers", "groupUsersId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="GroupRoles", isRequired = true) GroupRoles role;
  private final @ModelField(targetType="User", isRequired = true) @BelongsTo(targetName = "userGroupsId", type = User.class) User user;
  private final @ModelField(targetType="Group", isRequired = true) @BelongsTo(targetName = "groupUsersId", type = Group.class) Group group;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="ID") String groupUsersId;
  public String getId() {
      return id;
  }
  
  public GroupRoles getRole() {
      return role;
  }
  
  public User getUser() {
      return user;
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
  
  public String getGroupUsersId() {
      return groupUsersId;
  }
  
  private GroupMembers(String id, GroupRoles role, User user, Group group, String groupUsersId) {
    this.id = id;
    this.role = role;
    this.user = user;
    this.group = group;
    this.groupUsersId = groupUsersId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      GroupMembers groupMembers = (GroupMembers) obj;
      return ObjectsCompat.equals(getId(), groupMembers.getId()) &&
              ObjectsCompat.equals(getRole(), groupMembers.getRole()) &&
              ObjectsCompat.equals(getUser(), groupMembers.getUser()) &&
              ObjectsCompat.equals(getGroup(), groupMembers.getGroup()) &&
              ObjectsCompat.equals(getCreatedAt(), groupMembers.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), groupMembers.getUpdatedAt()) &&
              ObjectsCompat.equals(getGroupUsersId(), groupMembers.getGroupUsersId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getRole())
      .append(getUser())
      .append(getGroup())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getGroupUsersId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("GroupMembers {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("role=" + String.valueOf(getRole()) + ", ")
      .append("user=" + String.valueOf(getUser()) + ", ")
      .append("group=" + String.valueOf(getGroup()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("groupUsersId=" + String.valueOf(getGroupUsersId()))
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
  public static GroupMembers justId(String id) {
    return new GroupMembers(
      id,
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
      group,
      groupUsersId);
  }
  public interface RoleStep {
    UserStep role(GroupRoles role);
  }
  

  public interface UserStep {
    GroupStep user(User user);
  }
  

  public interface GroupStep {
    BuildStep group(Group group);
  }
  

  public interface BuildStep {
    GroupMembers build();
    BuildStep id(String id);
    BuildStep groupUsersId(String groupUsersId);
  }
  

  public static class Builder implements RoleStep, UserStep, GroupStep, BuildStep {
    private String id;
    private GroupRoles role;
    private User user;
    private Group group;
    private String groupUsersId;
    @Override
     public GroupMembers build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new GroupMembers(
          id,
          role,
          user,
          group,
          groupUsersId);
    }
    
    @Override
     public UserStep role(GroupRoles role) {
        Objects.requireNonNull(role);
        this.role = role;
        return this;
    }
    
    @Override
     public GroupStep user(User user) {
        Objects.requireNonNull(user);
        this.user = user;
        return this;
    }
    
    @Override
     public BuildStep group(Group group) {
        Objects.requireNonNull(group);
        this.group = group;
        return this;
    }
    
    @Override
     public BuildStep groupUsersId(String groupUsersId) {
        this.groupUsersId = groupUsersId;
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
    private CopyOfBuilder(String id, GroupRoles role, User user, Group group, String groupUsersId) {
      super.id(id);
      super.role(role)
        .user(user)
        .group(group)
        .groupUsersId(groupUsersId);
    }
    
    @Override
     public CopyOfBuilder role(GroupRoles role) {
      return (CopyOfBuilder) super.role(role);
    }
    
    @Override
     public CopyOfBuilder user(User user) {
      return (CopyOfBuilder) super.user(user);
    }
    
    @Override
     public CopyOfBuilder group(Group group) {
      return (CopyOfBuilder) super.group(group);
    }
    
    @Override
     public CopyOfBuilder groupUsersId(String groupUsersId) {
      return (CopyOfBuilder) super.groupUsersId(groupUsersId);
    }
  }
  
}
