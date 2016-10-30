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
import static junit.framework.Assert.assertFalse;
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
    public void deleteAllBillsTest(){
        BillDAO billDAO = BillDAO.getInstance(context);
        billDAO.deleteAllBills();

        List<Bill> billList = new ArrayList<>();

        Bill bill1 = null;
        Bill bill2 = null;
        Bill bill3 = null;
        Bill bill4 = null;
        Bill bill5 = null;

        try {
            bill1 = new Bill(1, "Teste", "teste", "closed", "description", "Meio Ambiente", 666, 13);
            bill2 = new Bill(2, "Teste", "teste", "closed", "description", "Meio Ambiente", 666, 13);
            bill3 = new Bill(3, "Teste", "teste", "closed", "description", "Meio Ambiente", 666, 13);
            bill4 = new Bill(4, "Teste", "teste", "closed", "description", "Meio Ambiente", 666, 13);
            bill5 = new Bill(5, "Teste", "teste", "closed", "description", "Meio Ambiente", 666, 13);
        } catch (BillException e) {
            e.printStackTrace();
        }

        billList.add(bill1);
        billList.add(bill2);
        billList.add(bill3);
        billList.add(bill4);
        billList.add(bill5);

        billDAO.insertAllBills(billList);

        assertTrue(billDAO.deleteAllBills() == 5);
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

    @Test
    public void getAllBillsTest(){
        BillDAO billDAO = BillDAO.getInstance(context);
        List<Bill> billList = new ArrayList<>();
        List<Bill> billList2 = new ArrayList<>();


        Bill bill1 = null;
        Bill bill2 = null;
        Bill bill3 = null;
        Bill bill4 = null;
        Bill bill5 = null;
        try {
            bill1 = new Bill(1, "Teste1", "teste", "closed", "description", "Meio Ambiente", 666, 13);
            bill2 = new Bill(2, "Teste2", "teste", "closed", "description", "Meio Ambiente", 666, 13);
            bill3 = new Bill(3, "Teste3", "teste", "closed", "description", "Meio Ambiente", 666, 13);
            bill4 = new Bill(4, "Teste4", "teste", "closed", "description", "Meio Ambiente", 666, 13);
            bill5 = new Bill(5, "Teste5", "teste", "closed", "description", "Meio Ambiente", 666, 13);
        } catch (BillException e) {
            e.printStackTrace();
        }

        billList.add(bill1);
        billList.add(bill2);
        billList.add(bill3);
        billList.add(bill4);
        billList.add(bill5);

        billDAO.insertAllBills(billList);

        try {
            billList2 = billDAO.getAllBills();
        } catch (BillException e) {
            e.printStackTrace();
        }

        assertEquals(billList2.get(0).getTheme(), "Meio Ambiente");
        assertEquals(billList2.get(1).getTheme(), "Meio Ambiente");
        assertEquals(billList2.get(2).getTheme(), "Meio Ambiente");
        assertEquals(billList2.get(3).getTheme(), "Meio Ambiente");
        assertEquals(billList2.get(4).getTheme(), "Meio Ambiente");
    }

    @Test
    public void getBillByIdTest(){
        BillDAO billDAO = BillDAO.getInstance(context);
        Bill bill = null;
        Bill bill2 = null;
        try {
            bill = new Bill(1, "Teste", "teste", "closed", "description", "Meio Ambiente", 666, 13);
        } catch (BillException e) {
            e.printStackTrace();
        }
        billDAO.insertBill(bill);
        try {
            bill2 = billDAO.getBillById(1);
        } catch (BillException e) {
            e.printStackTrace();
        }

        assertTrue(bill.getId() == 1);
    }

    @Test
    public void isDatabaseEmptyTest(){
        BillDAO billDAO = BillDAO.getInstance(context);

        assertFalse(billDAO.isDatabaseEmpty());
    }
}
