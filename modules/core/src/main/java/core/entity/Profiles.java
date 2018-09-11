package core.entity;

import com.google.common.base.Preconditions;
import core.Entity;
import java.time.LocalDate;
import javax.persistence.Embeddable;

/**
 * 用户资料。
 *
 * @author qiang.zhang
 */
@Embeddable
public final class Profiles implements Entity {
  /**
   * 名字
   */
  public String firstName;
  /**
   * 姓氏
   */
  public String lastName;
  /**
   * 生日
   */
  public LocalDate birthday;

  public String name() {
    // 暂时是中文格式
    return lastName + firstName;
  }

  @Override public boolean checkSelf() {
    if (firstName != null) {
      Preconditions.checkState(!firstName.isEmpty());
    }
    if (lastName != null) {
      Preconditions.checkState(!lastName.isEmpty());
    }
    if (birthday != null) {
      Preconditions.checkState(birthday.isBefore(LocalDate.now()));
    }
    return true;
  }
}
