package bitcamp.util;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TransactionProxyBuilder {

  TransactionTemplate txTemplate;

  public TransactionProxyBuilder(PlatformTransactionManager txManager) {
    this.txTemplate = new TransactionTemplate(txManager);
  }

  public Object build(Object originalWorker) {
    Class<?> clazz = originalWorker.getClass().getInterfaces()[0];

    return Proxy.newProxyInstance(
            this.getClass().getClassLoader(),
            new Class[]{clazz},
            new InvocationHandler() {
              @Override
              public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Method originalMethod = getOriginalMethod(originalWorker, method);

                Transactional trasactional = originalMethod.getAnnotation(Transactional.class);

                if (trasactional != null) {
                  return txTemplate.execute(status -> {
                    System.out.printf("%s() - 트랜잭션 경유!\n", originalMethod.getName());
                    try {
                      return originalMethod.invoke(originalWorker, args);
                    } catch (Exception e) {
                      throw new RuntimeException(e);
                    }
                  });
                } else {
                  System.out.printf("%s() - 직접 호출!\n", originalMethod.getName());
                  return originalMethod.invoke(originalWorker, args);
                }
              }
            });
  }

  public Method getOriginalMethod(Object originalWorker, Method method) throws Exception {
    Method[] methods = originalWorker.getClass().getDeclaredMethods();
    for (Method originalMethod : methods) {
      if (originalMethod.getName().equals(method.getName()) && originalMethod.getParameterCount() == method.getParameterCount()) {
        return originalMethod;
      }
    }
    return null;
  }
}
