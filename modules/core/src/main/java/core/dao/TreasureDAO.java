package core.dao;

import core.entity.Treasure;
import com.google.inject.ImplementedBy;
import java.util.List;
import javax.annotation.Nonnull;

/**
 * Treasure DAO.
 *
 * @author mrzhqiang
 */
@ImplementedBy(TreasureDAOImpl.class)
public interface TreasureDAO extends DAO<Treasure> {
  @Nonnull List<Treasure> findByName(String name);

  @Nonnull List<Treasure> findAll();
}
