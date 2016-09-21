package gppmds.wikilegis.model;

import gppmds.wikilegis.exception.CommentsException;

/**
 * Created by augusto on 11/09/16.
 */
public class Comments {

    private static final String ID_CANT_BE_NULL = "Id não pode ser carregado";
    private static final String IDUSER_CANT_BE_NULL = "User ID nao pode ser carregado";
    private static final String DATE_CANT_BE_NULL = "Date nao pode ser carregado";
    private static final String CONTENT_CANT_BE_NULL = "Content nao pode ser carregado";
    private static final String OBJECT_CANT_BE_NULL = "Object nao pode ser carregado";
    private static final String COMMENT_CANT_BE_NULL = "Comment nao pode ser carregado";
    private Integer id;
    private Integer idUser;
    private String date;
    private String contentType;
    private Integer objectPk;
    private String comment;

    public Comments(final Integer id, final Integer idUser, final String date,
                    final String contentType, final Integer objectPk, final String comment)
            throws CommentsException {
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

    private void setId(final Integer id) throws CommentsException {
        if (validateIntegerNull(id)) {
            this.id = id;
        } else {
            throw  new CommentsException("ID_CANT_BE_NULL");
        }
    }

    public Integer getIdUser() {
        return idUser;
    }

    private void setIdUser(final Integer idUser) throws CommentsException {
        if (validateIntegerNull(idUser)) {
            this.idUser = idUser;
        } else {
            throw  new CommentsException("IDUSER_CANT_BE_NULL");
        }
    }

    public String getDate() {
        return date;
    }

    private void setDate(final String date) throws CommentsException {
        if (validateStringEmpty(date)) {
            this.date = date;
        } else {
            throw  new CommentsException("DATE_CANT_BE_NULL");
        }
    }

    public String getContentType() {
        return contentType;
    }

    private void setContentType(final String contentType) throws CommentsException {
        if (validateStringEmpty(contentType)) {
            this.contentType = contentType;
        } else {
            throw  new CommentsException("CONTENT_CANT_BE_NULL");
        }
    }

    public Integer getObjectPk() {
        return objectPk;
    }

    private void setObjectPk(final Integer objectPk) throws CommentsException {
        if (validateIntegerNull(objectPk)) {
            this.objectPk = objectPk;
        } else {
            throw  new CommentsException("OBJECT_CANT_BE_NULL");
        }
    }

    public String getComment() {
        return comment;
    }

    private void setComment(final String comment) throws CommentsException {
        if (validateStringEmpty(comment)) {
            this.comment = comment;
        } else {
            throw  new CommentsException("COMMENT_CANT_BE_NULL");
        }
    }

    //Validation methods

    private boolean validateIntegerNull(final Integer integer) {
        if (integer == null) {
            return false;
        }
        return true;
    }

    private boolean validateStringEmpty(final String string) {
        if (string == null || string.trim().isEmpty()) {
            return false;
        }
        return true;
    }
}
