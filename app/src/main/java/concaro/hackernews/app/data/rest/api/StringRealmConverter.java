package concaro.hackernews.app.data.rest.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import io.realm.RealmList;
import concaro.hackernews.app.presentation.model.realmobject.StringRealm;

/**
 * Created by Concaro on 5/19/2017.
 */

public class StringRealmConverter implements JsonDeserializer<RealmList<StringRealm>> {

    @Override
    public RealmList<StringRealm> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        RealmList<StringRealm> realmStrings = new RealmList<>();
        JsonArray ja = json.getAsJsonArray();
        for (JsonElement je : ja) {
            realmStrings.add((StringRealm) context.deserialize(je, StringRealm.class));
        }

        return realmStrings;
    }
}
