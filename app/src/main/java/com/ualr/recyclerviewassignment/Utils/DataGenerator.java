package com.ualr.recyclerviewassignment.Utils;

import android.content.Context;

import com.ualr.recyclerviewassignment.R;
import com.ualr.recyclerviewassignment.model.Inbox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    private static Random r = new Random();

    public static int randInt(int max) {
        int min = 0;
        return r.nextInt((max - min) + 1) + min;
    }

    public static List<Inbox> getInboxData(Context ctx) {
        List<Inbox> items = new ArrayList<>();
        String name_arr[] = ctx.getResources().getStringArray(R.array.people_names);
        String date_arr[] = ctx.getResources().getStringArray(R.array.general_date);

        for (int i = 0; i < name_arr.length; i++) {
            Inbox obj = new Inbox();
            obj.setFrom(name_arr[i]);
            obj.setEmail(Tools.getEmailFromName(obj.getFrom()));
            obj.setMessage(ctx.getResources().getString(R.string.lorem_ipsum));
            obj.setDate(date_arr[randInt(date_arr.length - 1)]);
            items.add(obj);
        }
        Collections.shuffle(items);
        return items;
    }

    public static Inbox getRandomInboxItem(Context ctx) {
        List<Inbox> items = new ArrayList<>();
        String name_arr[] = ctx.getResources().getStringArray(R.array.people_names);
        String date_arr[] = ctx.getResources().getStringArray(R.array.general_date);
        int indexName = randInt(name_arr.length - 1);
        int indexDate = randInt(date_arr.length - 1);
        Inbox obj = new Inbox();
        obj.setFrom(name_arr[indexName]);
        obj.setEmail(Tools.getEmailFromName(obj.getFrom()));
        obj.setMessage(ctx.getResources().getString(R.string.lorem_ipsum));
        obj.setDate(date_arr[indexDate]);
        return obj;
    }
}
