package com.keeper.model.dto;

import com.keeper.model.types.UserType;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 10.05.2017
 */
public class UserSubscriptionsDTO {

    public static final UserSubscriptionsDTO EMPTY = new UserSubscriptionsDTO();

    @NotNull
    private Long userId;
    private Map<Long, LocalDateTime> subscriptions;

    private UserSubscriptionsDTO() {
        this.userId = UserType.EMPTY.getValue();
        this.subscriptions = Collections.emptyMap();
    }

    public UserSubscriptionsDTO(Long userId, Map<Long, LocalDateTime> subscriptions) {
        this.userId = userId;
        this.subscriptions = subscriptions;
    }

    public void addSubciption(Long taskId, LocalDateTime lastModificationDate) {
        this.subscriptions.put(taskId, lastModificationDate);
    }

    public void removeSubscription(Long taskId) {
        subscriptions.remove(taskId);
    }

    //<editor-fold desc="GetterAndSetter">

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Map<Long, LocalDateTime> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Map<Long, LocalDateTime> subscriptions) {
        this.subscriptions = subscriptions;
    }

    //</editor-fold>

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSubscriptionsDTO that = (UserSubscriptionsDTO) o;

        return userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}
