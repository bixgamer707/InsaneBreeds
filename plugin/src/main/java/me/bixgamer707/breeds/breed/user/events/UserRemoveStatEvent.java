package me.bixgamer707.breeds.breed.user.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class UserRemoveStatEvent extends PlayerEvent implements Cancellable {


    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private final String stat;
    private Integer value;
    private boolean cancelled;

    public UserRemoveStatEvent(Player player, String stat, Integer value){
        super(player);
        this.stat = stat;
        this.value = value;
    }

    public String getStat() {
        return stat;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
}

