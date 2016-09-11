package gppmds.wikilegis.model;

/**
 * Created by augusto on 11/09/16.
 */
public class Comments {

    private Integer id;
    private Integer idUser;
    private String date;
    private String contentType;
    private Integer objectPk;
    private String comment;

    public Comments(Integer id, Integer idUser, String date, String contentType, Integer objectPk, String comment) {
        setId(id);
        setIdUser(idUser);
        setDate(date);
        setContentType(contentType);
        setObjectPk(objectPk);
        setComment(comment);

    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    private void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getDate() {
        return date;
    }

    private void setDate(String date) {
        this.date = date;
    }

    public String getContentType() {
        return contentType;
    }

    private void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getObjectPk() {
        return objectPk;
    }

    private void setObjectPk(Integer objectPk) {
        this.objectPk = objectPk;
    }

    public String getComment() {
        return comment;
    }

    private void setComment(String comment) {
        this.comment = comment;
    }

    //Validation methods

    private boolean validateIntegerNull(final Integer integer){
        if(integer == null){
            return false;
        } else {
            return true;
        }
    }

    private boolean validateStringEmpty(final String string) {
        if (string == null || string.trim().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
