package bitcamp.util;

import org.springframework.transaction.TransactionStatus;

// 트랜잭션으로 처리할 작업을 갖고 있는 객체 사용법
public interface TransactionCallback<T> {
  T doInTransaction(TransactionStatus status);
}
