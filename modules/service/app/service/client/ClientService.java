package service.client;

import com.google.inject.ImplementedBy;
import java.util.concurrent.CompletionStage;
import javax.annotation.Nonnull;

/**
 * 客户端服务。
 *
 * @author qiang.zhang
 */
@ImplementedBy(ClientServiceImpl.class)
public interface ClientService {
  /**
   * 验证客户端权限。
   */
  @Nonnull CompletionStage<Boolean> authenticate(String basicAuth);
}
