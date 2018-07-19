package com.github.mrzhqiang.third;

import com.google.inject.ImplementedBy;

/**
 * The repairman interface usually used by database.
 *
 * @author mrzhqiang
 */
@ImplementedBy(SimpleRepairman.class)
public interface Repairman {
  /**
   * The repair method is check database status.
   */
  void repair();
}
