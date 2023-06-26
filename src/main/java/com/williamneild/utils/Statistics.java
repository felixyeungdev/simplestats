package com.williamneild.utils;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class Statistics {
    public static ArrayList<GeneralStatistic> getGeneralStatistics(StatHandler statHandler) {
        //create a new list
        ArrayList<GeneralStatistic> result = Lists.newArrayList();

        // Get all general stats (Custom) and add to list
        ObjectArrayList<Stat<Identifier>> statList = new ObjectArrayList<>(Stats.CUSTOM.iterator());
        for (Stat<Identifier> stat : statList) {
            result.add(new GeneralStatistic(statHandler, stat));
        }

        return result;
    }

    public static abstract class BaseStatistic {
        public final StatHandler statHandler;
        public final Text label;

        public BaseStatistic(StatHandler statHandler, Text label) {
            this.statHandler = statHandler;
            this.label = label;
        }
    }

    public static class GeneralStatistic extends BaseStatistic {
        public final Stat<Identifier> stat;
        public final int value;

        public GeneralStatistic(StatHandler statHandler, Stat<Identifier> generalStat) {
            super(statHandler, Text.literal(generalStat.getValue().toString()));

            this.stat = generalStat;
            this.value = statHandler.getStat(generalStat);
        }
    }
}