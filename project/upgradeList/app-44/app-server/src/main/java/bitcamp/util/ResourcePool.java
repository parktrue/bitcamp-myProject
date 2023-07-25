package bitcamp.util;

public interface ResourcePool<T> {
  T getResource();

  void returnResource(T resource);
}
