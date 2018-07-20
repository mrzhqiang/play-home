package core.common;

import core.entity.Treasure;
import java.util.Date;
import java.util.Optional;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;
import static core.common.CassandraConstant.*;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

/**
 * @author mrzhqiang
 */
public final class Treasures {
  private Treasures() {
  }

  public static Treasure check(Treasure entity) {
    requireNonNull(entity, TABLE_TREASURE);
    checkArgument(entity.id != null && entity.id > 0, TREASURE_ID);
    checkArgument(!isNullOrEmpty(entity.name), COMMON_COLUMN_NAME);
    checkArgument(!isNullOrEmpty(entity.description), COMMON_COLUMN_DESCRIPTION);
    checkArgument(!isNullOrEmpty(entity.link), COMMON_COLUMN_LINK);
    checkArgument(!isNull(entity.created), COMMON_COLUMN_CREATED);
    checkArgument(!isNull(entity.updated), COMMON_COLUMN_UPDATED);
    return entity;
  }

  @Nonnull
  @CheckReturnValue
  public static Treasure merge(@Nonnull Treasure oldTreasure, @Nonnull Treasure newTreasure) {
    oldTreasure.name = Optional.ofNullable(oldTreasure.name).orElse(newTreasure.name);
    oldTreasure.description =
        Optional.ofNullable(oldTreasure.description).orElse(newTreasure.description);
    oldTreasure.link = Optional.ofNullable(oldTreasure.link).orElse(newTreasure.link);
    oldTreasure.updated = new Date();
    return oldTreasure;
  }
}
