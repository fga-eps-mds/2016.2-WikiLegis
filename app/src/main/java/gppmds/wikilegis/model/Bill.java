package gppmds.wikilegis.model;

import gppmds.wikilegis.exception.BillException;

/**
 * Created by thiago on 9/6/16.
 */
public class Bill {

    private static final String TITLE_CANT_BE_EMPTY = "Título não pode ser carregado";
    private static final String NAME_CANT_BE_EMPTY = "Nome não pode ser carregado";
    private static final String EPIGRAPH_CANT_BE_EMPTY = "Epigrafe não pode ser carregado";
    private static final String STATUS_CANT_BE_EMPTY = "Status não pode ser carregado";
    private static final String THEME_CANT_BE_EMPTY = "Tema não pode ser carregado";
    private static final String SEGMENTS_CANT_BE_EMPTY = "Segmentos não podem ser carregados";
    private static final String NUMBEROFLIKE_CANT_BE_EMPTY = "Numero de likes não pode ser carregado";
    private static final String NUMBEROFDISFLIKE_CANT_BE_EMPTY = "Numero de deslikes não pode ser carregado";

    private Integer id;
    private String title;
    private String name;
    private String epigraph;
    private String status;
    private String theme;
    private String segments;
    private String numberOfLike;
    private String numberOfDislike;

    public Bill(final Integer id, final String title, final String name, final String epigraph,
                final String status, final String theme, final String segments,
                final String numberOfLike, final String numberOfDislike) throws BillException {
        setId(id);
        setTitle(title);
        setName(name);
        setEpigraph(epigraph);
        setStatus(status);
        setTheme(theme);
        setSegments(segments);
        setNumberOfLike(numberOfLike);
        setNumberOfDislike(numberOfDislike);
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

    public String getName() {
        return name;
    }

    private void setName(String name) throws  BillException {
        if (validateStringEmpty(name)) {
            this.name = name;
        } else {
            throw  new BillException(NAME_CANT_BE_EMPTY);
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

    public String getSegments() {
        return segments;
    }

    private void setSegments(String segments) throws  BillException {
        if (validateStringEmpty(segments)) {
            this.segments = segments;
        } else {
            throw  new BillException(SEGMENTS_CANT_BE_EMPTY);
        }
    }

    public String getNumberOfLike() {
        return numberOfLike;
    }

    private void setNumberOfLike(String numberOfLike) throws  BillException {
        if (validateStringEmpty(numberOfLike)) {
            this.numberOfLike = numberOfLike;
        } else {
            throw  new BillException(NUMBEROFLIKE_CANT_BE_EMPTY);
        }
    }

    public String getNumberOfDislike() {
        return numberOfDislike;
    }

    private void setNumberOfDislike(String numberOfDislike) throws  BillException {
        if (validateStringEmpty(numberOfDislike)) {
            this.numberOfDislike = numberOfDislike;
        } else {
            throw  new BillException(NUMBEROFDISFLIKE_CANT_BE_EMPTY);
        }
    }

    //Methods of validation

    private boolean validateStringEmpty(final String string) {
        if (string == null || string.trim().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
