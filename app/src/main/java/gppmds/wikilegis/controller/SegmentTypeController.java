package gppmds.wikilegis.controller;

import org.json.JSONException;
import org.json.JSONObject;

import gppmds.wikilegis.exception.SegmentTypesException;
import gppmds.wikilegis.model.SegmentTypes;

/**
 * Created by josue on 9/15/16.
 */
public class SegmentTypeController {

    public static SegmentTypes getSegmentTypes(JSONObject f) throws SegmentTypesException, JSONException {

        return new SegmentTypes(f.getInt("id"),
                f.getString("name"));
    }
}
