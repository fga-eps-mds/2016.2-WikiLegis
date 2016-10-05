package gppmds.wikilegis.dao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.model.Bill;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by shammyz on 10/5/16.
 */
public class BillDAOTest {

    Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void insertBillTest(){
        BillDAO billDAO = BillDAO.getInstance(context);
        Bill bill = null;
        try {
            bill = new Bill(1, "Teste", "teste", "closed", "description", "Meio Ambiente", 666, 13);
        } catch (BillException e) {
            e.printStackTrace();
        }

        assertTrue(billDAO.insertBill(bill));
    }

    @Test
    public void insertAllBillsTest(){
        BillDAO billDAO = BillDAO.getInstance(context);
        List<Bill> billList = new ArrayList<>();

        Bill bill1 = null;
        Bill bill2 = null;
        Bill bill3 = null;
        Bill bill4 = null;
        Bill bill5 = null;
        try {
            bill1 = new Bill(1, "Teste", "teste", "closed", "description", "Meio Ambiente", 666, 13);
            bill2 = new Bill(1, "Teste", "teste", "closed", "description", "Meio Ambiente", 666, 13);
            bill3 = new Bill(1, "Teste", "teste", "closed", "description", "Meio Ambiente", 666, 13);
            bill4 = new Bill(1, "Teste", "teste", "closed", "description", "Meio Ambiente", 666, 13);
            bill5 = new Bill(1, "Teste", "teste", "closed", "description", "Meio Ambiente", 666, 13);
        } catch (BillException e) {
            e.printStackTrace();
        }

        billList.add(bill1);
        billList.add(bill2);
        billList.add(bill3);
        billList.add(bill4);
        billList.add(bill5);

        assertTrue(billDAO.insertAllBills(billList));
    }
}
