package gppmds.wikilegis.model;

/**
 * Created by marcelo on 9/9/16.
 */
public class SegmentTypes {

    public static final String NAME_CANT_BE_EMPTY = "Nome não pode ser carregado.";
    public static final String ID_CANT_BE_EMPTY = "Id não pode ser carregado.";
    public static final String ID_CANT_BE_LESS_THAN_1 = "Id não pode ser carregado.";

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
        if(idIsValid(id) || !idIsNull(id)) {
            this.id=id;
        } else {
            //fazer a exceção
        }
    }

    public void setName(final String name) {
        if(!nameIsNull(name)) {
            this.name=name;
        } else {
            //fazer a exceção
        }
    }

    private boolean idIsNull(final Integer id) {
        return id == null ? true : false;
    }

    private boolean idIsValid(final Integer id) {
        return id < 1 ? false : true;
    }

    private boolean nameIsNull(final String name) {
        return (name.isEmpty() || name == null) ? true : false;
    }

}
