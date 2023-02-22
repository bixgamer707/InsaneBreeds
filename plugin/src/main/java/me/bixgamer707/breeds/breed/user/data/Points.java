package me.bixgamer707.breeds.breed.user.data;

import me.bixgamer707.breeds.user.Data;
import org.bukkit.entity.Player;

public class Points implements Data<Integer> {

    private Integer points;

    public Points(Integer points){
        this.points = points;
    }

    @Override
    public Integer get() {
        return points;
    }

    @Override
    public void set(Integer value) {
        this.points = value;
    }

    @Override
    public void add(Integer value) {
        this.points += value;
    }

    @Override
    public void reset() {
        this.points = 0;
    }

    @Override
    public void remove(Integer value) {
        this.points -= value;
    }
}
