package gppmds.wikilegis.controller;

import org.json.JSONException;
import org.json.JSONObject;

import gppmds.wikilegis.exception.SegmentTypesException;
import gppmds.wikilegis.model.SegmentTypes;

public class SegmentTypeController {

    public static SegmentTypes getSegmentTypes(final JSONObject jsonObject)
            throws SegmentTypesException, JSONException {

        return new SegmentTypes(jsonObject.getInt("id"),
                jsonObject.getString("name"));
    }
}
