package com.elboukharielkhamlichi.khalid.guideuniversitaire;

import android.arch.persistence.room.TypeConverter;

import com.elboukharielkhamlichi.khalid.guideuniversitaire.entity.Etablissement;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Created by Khalid on 1/13/2018.
 */

public class EtablissementConverter {

    public static List<Integer> stringToIntegerList(String data) {
        Gson gson = new Gson();
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Integer>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    public static String IntegerListToString(List<Integer> Integers) {
        Gson gson = new Gson();
        return gson.toJson(Integers);
    }

}
