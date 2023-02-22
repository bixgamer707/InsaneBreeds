package me.bixgamer707.breeds.breed.user.data;

import me.bixgamer707.breeds.user.Data;
import org.bukkit.entity.Player;

public class Stats implements Data<Integer> {

        private Integer stats;

        public Stats(Integer stats){
            this.stats = stats;
        }

        @Override
        public Integer get() {
            return stats;
        }

        @Override
        public void set(Integer value) {
            this.stats = value;
        }

        @Override
        public void add(Integer value) {
            this.stats += value;
        }

        @Override
        public void reset() {
            this.stats = 0;
        }

        @Override
        public void remove(Integer value) {
            this.stats -= value;
        }

}
