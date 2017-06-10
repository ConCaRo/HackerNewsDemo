package concaro.hackernews.app.data.rest.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import io.realm.RealmList;
import concaro.hackernews.app.presentation.model.realmobject.IntegerRealm;

/**
 * Created by Concaro on 5/19/2017.
 */

public class IntegerRealmConverter implements JsonDeserializer<RealmList<IntegerRealm>> {

    @Override
    public RealmList<IntegerRealm> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        RealmList<IntegerRealm> realmStrings = new RealmList<>();
        JsonArray ja = json.getAsJsonArray();
        for (JsonElement je : ja) {
            realmStrings.add((IntegerRealm) context.deserialize(je, IntegerRealm.class));
        }

        return realmStrings;
    }
}
