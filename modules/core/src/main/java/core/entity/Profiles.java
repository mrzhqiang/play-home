package core.entity;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.time.LocalDate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Embeddable;

/**
 * 用户资料。
 *
 * TODO: address telephone city etc...
 *
 * @author qiang.zhang
 */
@Embeddable
public final class Profiles {
  @Nullable
  public String name;
  @Nullable
  public Sex sex;
  @Nullable
  public LocalDate birthday;

  @Nonnull
  @CanIgnoreReturnValue
  public static Profiles of(@Nullable String name, @Nullable Sex sex,
      @Nullable LocalDate birthday) {
    Profiles profiles = new Profiles();
    profiles.name = name;
    profiles.sex = sex;
    profiles.birthday = birthday;
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
