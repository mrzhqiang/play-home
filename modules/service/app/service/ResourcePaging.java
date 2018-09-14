package service;

import core.Paging;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 资源分页。
 *
 * @author qiang.zhang
 */
public final class ResourcePaging<T> {
  public int total;
  public int index;
  public int size;
  public List<T> resources;

  public static <T, E> ResourcePaging<T> convert(Paging<E> paging, Function<E, T> function) {
    ResourcePaging<T> resourcePaging = new ResourcePaging<>();
    resourcePaging.total = paging.total();
    resourcePaging.index = paging.index();
    resourcePaging.size = paging.size();
    resourcePaging.resources = paging.resources().stream()
        .map(function)
        .collect(Collectors.toList());
    return resourcePaging;
  }
}
