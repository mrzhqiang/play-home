package com.github.mrzhqiang.core.dao;

import com.github.mrzhqiang.core.entity.Treasure;
import com.google.inject.ImplementedBy;
import java.util.List;
import java.util.Optional;

@ImplementedBy(CassandraTreasure.class)
public interface TreasureDAO extends Entity<Treasure> {
  Optional<Treasure> findByName(String name);

  List<Treasure> findALL();
}
