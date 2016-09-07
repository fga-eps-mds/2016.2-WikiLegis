package gppmds.wikilegis.model;

/**
 * Created by thiago on 9/6/16.
 */
public class Bill {
    private Integer id;
    private String title;
    private String name;
    private String epigraph;
    private String status;
    private String theme;
    private String segments;
    private String numberOfLike;
    private String numberOfDislike;

    public Bill(final Integer id, final String title, final String name, final String epigraph, final String status, final String theme, final String segments, final String numberOfLike, final String numberOfDislike) {
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

    private void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getEpigraph() {
        return epigraph;
    }

    private void setEpigraph(String epigraph) {
        this.epigraph = epigraph;
    }

    public String getStatus() {
        return status;
    }

    private void setStatus(String status) {
        this.status = status;
    }

    public String getTheme() {
        return theme;
    }

    private void setTheme(String theme) {
        this.theme = theme;
    }

    public String getSegments() {
        return segments;
    }

    private void setSegments(String segments) {
        this.segments = segments;
    }

    public String getNumberOfLike() {
        return numberOfLike;
    }

    private void setNumberOfLike(String numberOfLike) {
        this.numberOfLike = numberOfLike;
    }

    public String getNumberOfDislike() {
        return numberOfDislike;
    }

    private void setNumberOfDislike(String numberOfDislike) {
        this.numberOfDislike = numberOfDislike;
    }

    }
