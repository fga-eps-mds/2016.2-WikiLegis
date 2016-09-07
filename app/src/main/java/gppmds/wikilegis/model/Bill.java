package gppmds.wikilegis.model;

import gppmds.wikilegis.exception.BillException;

/**
 * Created by shammyz on 9/7/16.
 */
public class Bill {
    private Integer id;
    private String title;
    private String name;
    private String epigraph;
    private String description;
    private String status;
    private String theme;
    private Integer segments;
    private Integer numberOfLike;
    private Integer numberOfDeslike;

    //Inserir métodos SET
    public Bill(Integer id, String title, String name, String epigraph, String description,
                String status, String theme, Integer segments) throws BillException {

    }
}

