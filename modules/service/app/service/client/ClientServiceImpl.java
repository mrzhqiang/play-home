package service.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.entity.Client;
import core.exception.ApplicationException;
import core.repository.ClientRepository;
import java.util.concurrent.CompletionStage;
import javax.annotation.Nonnull;
import service.DatabaseExecutionContext;

import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * @author qiang.zhang
 */
@Singleton final class ClientServiceImpl implements ClientService {
  private final ClientRepository clientRepository;
  private final DatabaseExecutionContext ec;

  @Inject ClientServiceImpl(ClientRepository clientRepository, DatabaseExecutionContext ec) {
    this.clientRepository = clientRepository;
    this.ec = ec;
  }

  @Nonnull @Override public CompletionStage<Boolean> authenticate(String basicAuth) {
    return supplyAsync(
        () -> clientRepository.authenticate(basicAuth)
            .map(Client::checkSelf)
            .orElseThrow(() -> ApplicationException.forbidden("authenticate failed.")),
        ec);
  }
}
