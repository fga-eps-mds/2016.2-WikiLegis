package gppmds.wikilegis.dao;

import android.util.JsonReader;

import java.io.IOException;
import java.io.StringReader;


/**
 * Created by josue on 9/3/16.
 */
public class DaoUtilities {

    public boolean findEmail(String email) throws IOException {
        String auxiliaryEmail;

        int counter=0;
        JsonReader read = new JsonReader(new StringReader("http://wikilegis.labhackercd.net/api/users/?format=api"));

            while (read.hasNext() || counter ==0) {
                auxiliaryEmail = read.nextString();
                if (auxiliaryEmail.equals(email)) {
                counter = 1;
                }
         }

        if(counter == 1){
            return true;
        }else{
            return false;
        }
    }
}





