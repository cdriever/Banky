package com.ulvbror.banky.data;

import com.ulvbror.banky.settings.DataManager;
import com.ulvbror.banky.settings.SettingsManager;
import org.bukkit.entity.EntityType;

import java.util.*;

public class DropData {
    EntityType type;
    boolean canDrop;
    int drop;
    boolean canVary;
    int variance;

    public static final List<String> TYPE_LIST = Collections.unmodifiableList( new ArrayList<String>() {{
                add("blaze");
                add("creeper");
                add("drowned");
                add("elder_guardian");
                add("ender_dragon");
                add("endermite");
                add("evoker");
                add("ghast");
                add("guardian");
                add("hoglin");
                add("husk");
                add("illusioner");
                add("magma_cube");
                add("phantom");
                add("piglin");
                add("piglin_brute");
                add("pillager");
                add("ravager");
                add("shulker");
                add("silverfish");
                add("skeleton");
                add("slime");
                add("stray");
                add("vex");
                add("vindicator");
                add("witch");
                add("wither");
                add("wither_skeleton");
                add("zoglin");
                add("zombie");
                add("zombie_villager");
                add("bee");
                add("cave_spider");
                add("dolphin");
                add("enderman");
                add("iron_golem");
                add("llama");
                add("polar_bear");
                add("pufferfish");
                add("spider");
                add("trader_llama");
                add("wandering_trader");
                add("wolf");
                add("zombified_piglin");
                add("bat");
                add("cat");
                add("chicken");
                add("cod");
                add("cow");
                add("donkey");
                add("fox");
                add("horse");
                add("mushroom_cow");
                add("mule");
                add("ocelot");
                add("panda");
                add("parrot");
                add("pig");
                add("rabbit");
                add("salmon");
                add("sheep");
                add("skeleton_horse");
                add("snowman");
                add("squid");
                add("strider");
                add("tropical_fish");
                add("turtle");
                add("villager");
                add("zombie_horse");
            }} );

    SettingsManager settings = SettingsManager.getInstance();

    public DropData(EntityType t) {
        type = t;
        canDrop = canVary = false;
        drop = variance = 0;
    }

    public DropData(EntityType t, int amt) {
        type = t;
        canDrop = true;
        drop = amt;
        canVary = false;
        variance = 0;
    }

    public void addVariance(int v) {
        if(v > 0) {
            this.canVary = true;
            this.variance = Math.min(v, this.drop);
        }
    }

    public void makeStatic() {
        this.canVary = false;
        this.variance = 0;
    }

    public void reset() {
        this.canDrop = this.canVary = false;
        this.drop = this.variance = 0;
    }

    public void setDropAmount(int amount) {
        if(amount == 0) {
            this.drop = 0;
            this.canDrop = false;
        } else if(amount > 0) {
            this.drop = amount;
            this.canDrop = true;
        }
    }

    public int dropped() {
        if(canVary) {
            Random rand = new Random();
            if(rand.nextBoolean()) {
                return this.drop + rand.nextInt(this.variance);
            } else {
                return this.drop - rand.nextInt(this.variance);
            }
        } else {
            return this.drop;
        }
    }

    private int boolToInt(boolean b) {
        return b ? 1 : 0;
    }

    public void save() {
        String name = this.type.toString().toLowerCase();
        settings.getData().set("banky.drops."+name+".canDrop", boolToInt(this.canDrop));
        settings.getData().set("banky.drops."+name+".drop", this.drop);
        settings.getData().set("banky.drops."+name+".canVary", boolToInt(this.canVary));
        settings.getData().set("banky.drops."+name+".variance", this.variance);
        settings.saveData();
    }

    public static EntityType findType(String entity) {
        try {
            return EntityType.valueOf(entity.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
