package gppmds.wikilegis.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import gppmds.wikilegis.exception.BillException;

/**
 * Created by thiago on 9/6/16.
 */
public class Bill implements Comparable<Bill>{

    private static final String TITLE_CANT_BE_EMPTY = "Título não pode ser carregado";
    private static final String EPIGRAPH_CANT_BE_EMPTY = "Epigrafe não pode ser carregado";
    private static final String STATUS_CANT_BE_EMPTY = "Status não pode ser carregado";
    private static final String THEME_CANT_BE_EMPTY = "Tema não pode ser carregado";
    private static final String DESCRIPTION_CANT_BE_EMPTY = "Descrição não pode ser carregado";

    private Integer id;
    private String title;
    private String epigraph;
    private String status;
    private String description;
    private String theme;
    private List<Integer> segments;
    private Integer numberOfPrposals;

    public Bill(final Integer id, final String title, final String epigraph,
                final String status, final String description, final String theme,final Integer numberOfPrposals) throws BillException {
        setId(id);
        setTitle(title);
        setEpigraph(epigraph);
        setDescription(description);
        setStatus(status);
        setDescription(description);
        setTheme(theme);
        this.segments = new ArrayList<>();
        setNumberOfPrposals(numberOfPrposals);
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) throws  BillException {
        if (validateStringEmpty(title)) {
            this.title = title;
        } else {
            throw  new BillException(TITLE_CANT_BE_EMPTY);
        }

    }

    public String getDescription() { return description; }

    private void setDescription(String description) throws BillException {
        if(validateStringEmpty(description)){
            this.description = description;
        } else {
            throw  new BillException(DESCRIPTION_CANT_BE_EMPTY);
        }
    }

    public String getEpigraph() {
        return epigraph;
    }

    private void setEpigraph(String epigraph) throws  BillException {
        if (validateStringEmpty(epigraph)) {
            this.epigraph = epigraph;
        } else {
            throw  new BillException(EPIGRAPH_CANT_BE_EMPTY);
        }
    }

    public String getStatus() {
        return status;
    }

    private void setStatus(String status) throws  BillException {
        if (validateStringEmpty(status)) {
            this.status = status;
        } else {
            throw  new BillException(STATUS_CANT_BE_EMPTY);
        }
    }

    public String getTheme() {
        return theme;
    }

    private void setTheme(String theme) throws  BillException {
        if (validateStringEmpty(theme)) {
            this.theme = theme;
        } else {
            throw  new BillException(THEME_CANT_BE_EMPTY);
        }
    }

    public List<Integer> getSegments() {
        return segments;
    }

    public void setSegments(Integer segment) throws  BillException {
            this.segments.add(segment);
    }
    public int getNumberOfPrposals(){return  numberOfPrposals;}

    private void setNumberOfPrposals(Integer numberOfPrposals){

        this.numberOfPrposals = numberOfPrposals;
    }
    //Methods of validation

    private boolean validateStringEmpty(final String string) {
        if (string == null || string.trim().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    private int number;
    @Override
    public int compareTo(Bill bill) {
        if(this.numberOfPrposals>bill.getNumberOfPrposals()){
            return -1;
        }
        if(this.numberOfPrposals<bill.getNumberOfPrposals()){
            return 1;
        }
        return 0;
    }

}
