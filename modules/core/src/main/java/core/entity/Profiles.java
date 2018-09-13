package core.entity;

import com.google.common.base.Verify;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.time.LocalDate;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Embeddable;

/**
 * 用户资料。
 *
 * @author qiang.zhang
 */
@Embeddable
public final class Profiles {
  /**
   * 名字。
   */
  private String name;
  /**
   * 性别。
   */
  private Sex sex;
  /**
   * 生日。
   */
  private LocalDate birthday;

  @CheckForNull
  public String getName() {
    return name;
  }

  public void setName(@Nonnull String name) {
    this.name = name;
  }

  @CheckForNull
  public Sex getSex() {
    return sex;
  }

  public void setSex(@Nonnull Sex sex) {
    this.sex = sex;
  }

  @CheckForNull
  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(@Nonnull LocalDate birthday) {
    Verify.verify(LocalDate.now().isAfter(birthday),
        "invalid birthday: %s", birthday);
    this.birthday = birthday;
  }

  @Nonnull
  @CanIgnoreReturnValue
  public static Profiles of(@Nullable String name, @Nullable Sex sex,
      @Nullable LocalDate birthday) {
    Profiles profiles = new Profiles();
    if (name != null) {
      profiles.setName(name);
    }
    if (sex != null) {
      profiles.setSex(sex);
    }
    if (birthday != null) {
      profiles.setBirthday(birthday);
    }
    return profiles;
  }

  public enum Sex {
    MALE("男"),
    FEMALE("女"),
    UNKNOWN("未知"),;

    final String value;

    Sex(String value) {
      this.value = value;
    }

    public static Sex of(String value) {
      for (Sex sex : Sex.values()) {
        if (sex.value.equals(value)) {
          return sex;
        }
      }
      return Sex.valueOf(value);
    }

    @Override public String toString() {
      return value;
    }
  }
}
