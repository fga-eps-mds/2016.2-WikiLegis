package gppmds.wikilegis.model;

import gppmds.wikilegis.exception.SegmentTypesException;

public class SegmentTypes {

    public static final String NAME_CANT_BE_EMPTY = "Nome não pode ser carregado.";
    public static final String ID_CANT_BE_EMPTY = "Id não pode ser carregado.";

    private Integer id;
    private String name;

    public SegmentTypes(final Integer id, final String name) throws SegmentTypesException {
        setId(id);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) throws SegmentTypesException {
        if (!idIsNull(id)) {
                this.id = id;
        } else {
            throw new SegmentTypesException(ID_CANT_BE_EMPTY);
        }
    }

    public void setName(final String name) throws SegmentTypesException {
        if (!nameIsNull(name)) {
            this.name = name;
        } else {
            throw new SegmentTypesException(NAME_CANT_BE_EMPTY);
        }
    }

    private boolean idIsNull(final Integer id) {
        return id == null ? true : false;
    }

    private boolean nameIsNull(final String name) {
        return (name == null || name.isEmpty()) ? true : false;
    }
}
