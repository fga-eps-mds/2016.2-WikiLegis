package gppmds.wikilegis.model;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;

public class Bill {

    private static final String ID_CANT_BE_EMPTY = "O id não pode ser carregado";
    private static final String TITLE_CANT_BE_EMPTY = "Título não pode ser carregado";
    private static final String EPIGRAPH_CANT_BE_EMPTY = "Epigrafe não pode ser carregado";
    private static final String STATUS_CANT_BE_EMPTY = "Status não pode ser carregado";
    private static final String THEME_CANT_BE_EMPTY = "Tema não pode ser carregado";
    private static final String DESCRIPTION_CANT_BE_EMPTY = "Descrição não pode ser carregado";
    private static final String NUMBEROFPROPOSALS_CANT_BE_EMPTY = "Number of proposals não "
            + "pode ser carregado";
    private static final String DATE_CANT_BE_EMPTY = "Data não pode ser carregado";

    private Integer id;
    private String title;
    private String epigraph;
    private String status;
    private String description;
    private String theme;
    private List<Integer> segments;
    private Integer numberOfPrposals;
    private Integer date;

    public Bill(final Integer id, final String title, final String epigraph,
                final String status, final String description, final String theme,
                final Integer numberOfPrposals, final Integer date) throws BillException {
        setId(id);
        setTitle(title);
        setEpigraph(epigraph);
        setDescription(description);
        setStatus(status);
        setTheme(theme);
        this.segments = new ArrayList<>();
        setNumberOfPrposals(numberOfPrposals);
        setDate(date);
    }

    public boolean equals(Bill bill) {

        boolean isEverythingEqual = ( this.id.equals(bill.getId()) && this.title.equals(bill.getTitle())
                && this.epigraph.equals(bill.getTitle()) && this.status.equals(bill.getStatus())
                && this.description.equals(bill.getDescription()) && this.theme.equals(bill.getTheme())
                && this.numberOfPrposals.equals(bill.getNumberOfPrposals()) && this.date.equals(bill.getDate())
        );

        return isEverythingEqual;
    }

    public Integer getId() {
        return id;
    }

    private void setId(final Integer id) throws BillException {
        if (validateIntegerNull(id)) {
            this.id = id;
        } else {
            throw new BillException(ID_CANT_BE_EMPTY);
        }
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(final String title) throws  BillException {
        if (validateStringEmpty(title)) {
            this.title = title;
        } else {
            throw  new BillException(TITLE_CANT_BE_EMPTY);
        }

    }

    public String getDescription() {return description; }

    private void setDescription(final String description) throws BillException {
        if (validateStringEmpty(description)) {
            this.description = description;
        } else {
            throw  new BillException(DESCRIPTION_CANT_BE_EMPTY);
        }
    }

    public String getEpigraph() {
        return epigraph;
    }

    private void setEpigraph(final String epigraph) throws  BillException {
        if (validateStringEmpty(epigraph)) {
            this.epigraph = epigraph;
        } else {
            throw  new BillException(EPIGRAPH_CANT_BE_EMPTY);
        }
    }

    public String getStatus() {
        return status;
    }

    private void setStatus(final String status) throws  BillException {
        if (validateStringEmpty(status)) {
            this.status = status;
        } else {
            throw  new BillException(STATUS_CANT_BE_EMPTY);
        }
    }

    public String getTheme() {
        return theme;
    }

    private void setTheme(final String theme) throws  BillException {
        if (validateStringEmpty(theme)) {
            this.theme = theme;
        } else {
            throw  new BillException(THEME_CANT_BE_EMPTY);
        }
    }

    public List<Integer> getSegments() {
        return segments;
    }

    public void setSegments(final Integer segment) {
        assert (segment >= 0);

        this.segments.add(segment);

    }
    public int getNumberOfPrposals() {
        return  numberOfPrposals;
    }

    private void setNumberOfPrposals(final Integer numberOfPrposals) throws BillException {
        if (validateIntegerNull(numberOfPrposals)) {
            this.numberOfPrposals = numberOfPrposals;
        } else {
            throw new BillException(NUMBEROFPROPOSALS_CANT_BE_EMPTY);
        }
    }
    public int getDate() {
        return  date;
    }
    private void setDate(final Integer date) throws BillException {
        if (validateIntegerNull(date)) {
            this.date = date;
        } else {
            throw new BillException(DATE_CANT_BE_EMPTY);
        }
    }

    //Methods of validation

    private boolean validateIntegerNull(final Integer integer) {
        if (integer == null) {
            return false;
        }
        return true;
    }

    private boolean validateStringEmpty(final String string) {
        if (string == null) {
            return false;
        }
        return true;
    }
}
