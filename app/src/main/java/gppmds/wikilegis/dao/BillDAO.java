package gppmds.wikilegis.dao;

import android.content.Context;

/**
 * Created by marcelo on 9/13/16.
 */
public class BillDAO extends DaoUtilities{

    private static String tableColumns[] = {"id", "title", "epigraph", "description", "theme",
    "amountParticipants", "amountProposals", "status"};

    private static BillDAO instance;

    private static String tableName = "Bill";

    private BillDAO(Context context) {
        BillDAO.database = new DatabaseHelper(context);
    }

    public static BillDAO getInstance(Context context) {
        if (BillDAO.instance != null) {
            //nothing to do
        } else {
            BillDAO.instance = new BillDAO(context);
        }
        return BillDAO.instance;
    }

}
