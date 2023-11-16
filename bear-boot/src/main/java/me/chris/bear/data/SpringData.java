package me.chris.bear.data;

import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.domain.Persistable;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @Author Christopher
 * @Since 2023/11/16 2:33 PM
 */
public interface SpringData {
    static <T> T getLazyIdentifier(Persistable<T> persistable) {
        if (persistable instanceof HibernateProxy) {
            return (T) ((HibernateProxy) persistable).getHibernateLazyInitializer().getIdentifier();
        }
        return persistable.getId();
    }

    /** 在事务提交后执行 */
    static void committed(Runnable action) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronizationAdapter() {
                        @Override
                        public void afterCommit() {
                            action.run();
                        }
                    });
        } else {
            // 如果未开启事务, 则直接执行, 很少会发生
            action.run();
        }
    }
}
