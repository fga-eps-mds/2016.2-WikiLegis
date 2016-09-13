package gppmds.wikilegis.model;

import gppmds.wikilegis.exception.VotesException;

/**
 * Created by augusto on 13/09/16.
 */
public class Votes {

    private static final String USERID_CANT_BE_NULL = "Id não pode ser carregado";
    private static final String CONTENT_CANT_BE_NULL = "Id não pode ser carregado";
    private static final String OBJECTID_CANT_BE_NULL = "Id não pode ser carregado";


    private Integer userId;
    private Integer contentType;
    private Integer objectId;
    private boolean vote;

    public Votes(Integer userId, Integer contentType, Integer objectId, boolean vote) throws VotesException {

        setContentType(contentType);
        setUserId(userId);
        setObjectId(objectId);
        setVote(vote);
    }

    public Integer getUserId() {
        return userId;
    }

    private void setUserId(Integer userId) throws VotesException {
        if(validateIntegerNull(userId)) {
            this.userId = userId;
        } else{
            throw  new VotesException("USERID_CANT_BE_NULL");
        }
    }

    public Integer getContentType() {
        return contentType;
    }

    private void setContentType(Integer contentType) throws VotesException {
        if(validateIntegerNull(contentType)) {
            this.contentType = contentType;
        } else{
            throw  new VotesException("CONTENT_CANT_BE_NULL");
        }
    }

    public Integer getObjectId() {
        return objectId;
    }

    private void setObjectId(Integer objectId) throws VotesException {
        if(validateIntegerNull(objectId)) {
            this.objectId = objectId;
        } else{
            throw  new VotesException("OBJECTID_CANT_BE_NULL");
        }
    }

    public boolean isVote() {
        return vote;
    }

    private void setVote(boolean vote) {
        this.vote = vote;
    }

    //Validation methods

    private boolean validateIntegerNull(final Integer integer){
        if(integer == null){
            return false;
        } else {
            return true;
        }
    }


}
