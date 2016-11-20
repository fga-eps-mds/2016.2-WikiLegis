package gppmds.wikilegis.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import gppmds.wikilegis.dao.database.BillDAO;
import gppmds.wikilegis.dao.database.SegmentDAO;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.VotesException;

import static junit.framework.Assert.assertEquals;

/**
 * Created by marcelo on 11/20/16.
 */
public class WifiNeededTest {
    Context context;
    SegmentDAO segmentDAO;
    DataDownloadController dataDownloadController;
    SegmentController segmentController;
    BillDAO billDAO;
    BillController billController;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
        segmentDAO = SegmentDAO.getInstance(context);
        dataDownloadController = DataDownloadController.getInstance(context);
        segmentController = SegmentController.getInstance(context);

        billDAO = BillDAO.getInstance(context);
        billController = BillController.getInstance(context);

        billDAO.deleteAllBills();

        segmentDAO.clearSegmentsTable();

        WifiManager wifiManager = (WifiManager)this.context.getSystemService(Context.WIFI_SERVICE);

        final boolean STATUS = true;

        wifiManager.setWifiEnabled(STATUS);

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        WifiManager wifiManager = (WifiManager)this.context.getSystemService(Context.WIFI_SERVICE);

        final boolean STATUS = true;

        wifiManager.setWifiEnabled(STATUS);

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateDataWithWifiDisabled() {
        WifiManager wifiManager = (WifiManager)this.context.getSystemService(Context.WIFI_SERVICE);

        final boolean STATUS = false;

        wifiManager.setWifiEnabled(STATUS);

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = session.edit();

        final String keyConnection = "NetworkSettings";
        editor.putInt(keyConnection, 1);

        final String keyDate = "date";
        editor.putString(keyDate, "2010-01-01");

        editor.commit();

        try {
            dataDownloadController.updateData();
        } catch (SegmentException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (BillException e) {
            e.printStackTrace();
        } catch (VotesException e) {
            e.printStackTrace();
        }

        assertEquals(session.getString(keyDate, "2010-01-01"),
                "2010-01-01");
    }

    @Test
    public void testUpdateDataWithConnectionPreferenceToOnlyWifiAndWifiEnabled() {
        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = session.edit();

        final String keyConnection = "NetworkSettings";
        editor.putInt(keyConnection, 0);

        final String keyDate = "date";
        editor.putString(keyDate, "2010-01-01");

        editor.commit();

        try {
            dataDownloadController.updateData();
        } catch (SegmentException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (BillException e) {
            e.printStackTrace();
        } catch (VotesException e) {
            e.printStackTrace();
        }

        assertEquals(session.getString(keyDate, "2010-01-01"),
                DataDownloadController.getLocalTime());
    }

    @Test
    public void testConnectionTypeWithWifiDisconnected() {
        final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final Class conmanClass;
        try {
            conmanClass = Class.forName(conman.getClass().getName());
            final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
            iConnectivityManagerField.setAccessible(true);
            final Object iConnectivityManager = iConnectivityManagerField.get(conman);
            final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
            final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        WifiManager wifiManager = (WifiManager)this.context.getSystemService(Context.WIFI_SERVICE);

        final boolean STATUS = false;

        wifiManager.setWifiEnabled(STATUS);

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final int CONNECTION_TYPE = dataDownloadController.connectionType();
        final int EXPECTED_CONNECTION_TYPE = 2;

        assertEquals(CONNECTION_TYPE, EXPECTED_CONNECTION_TYPE);
    }

    @Test
    public void testConnectionTypeWithWifiConnected() {
        final int CONNECTION_TYPE = dataDownloadController.connectionType();
        final int EXPECTED_CONNECTION_TYPE = 0;

        Log.d(CONNECTION_TYPE + "", "testConnectionTypeWithWifiConnected ");

        assertEquals(CONNECTION_TYPE, EXPECTED_CONNECTION_TYPE);
    }

    @Test
    public void testUpdateDataWithConnectionPreferenceToWifiAndMobileDataAndWifiEnabled() {
        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = session.edit();

        final String keyConnection = "NetworkSettings";
        editor.putInt(keyConnection, 1);

        final String keyDate = "date";
        editor.putString(keyDate, "2010-01-01");

        editor.commit();

        try {
            dataDownloadController.updateData();
        } catch (SegmentException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (BillException e) {
            e.printStackTrace();
        } catch (VotesException e) {
            e.printStackTrace();
        }

        assertEquals(session.getString(keyDate, "2010-01-01"),
                DataDownloadController.getLocalTime());
    }

}
