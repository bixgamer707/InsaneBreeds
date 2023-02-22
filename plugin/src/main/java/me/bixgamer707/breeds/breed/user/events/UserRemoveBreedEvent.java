package me.bixgamer707.breeds.breed.user.events;

import me.bixgamer707.breeds.breed.Breed;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class UserRemoveBreedEvent extends PlayerEvent implements Cancellable {


    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private final Breed breed;
    private boolean cancelled;

    public UserRemoveBreedEvent(Player player, Breed breed){
        super(player);
        this.breed = breed;
    }

    public Breed getBreed() {
        return breed;
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

