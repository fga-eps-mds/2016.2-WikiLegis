package gppmds.wikilegis.model;

/**
 * Created by marcelo on 9/9/16.
 */
public class SegmentTypes {

    private Integer id;
    private String name;

    public SegmentTypes(final Integer id, final String name) {
        setId(id);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id=id;
    }

    public void setName(final String name) {
        this.name=name;
    }
}
