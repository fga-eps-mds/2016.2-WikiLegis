package gppmds.wikilegis.model;

import gppmds.wikilegis.exception.VotesException;

public class Votes {

    private static final String USERID_CANT_BE_NULL = "Id não pode ser carregado";
    private static final String CONTENT_CANT_BE_NULL = "Conteudo não pode ser carregado";
    private static final String OBJECTID_CANT_BE_NULL = "Id do objeto não pode ser carregado";

    private Integer userId;
    private Integer contentType;
    private Integer objectId;
    private boolean vote;

    public Votes(final Integer userId, final Integer contentType,
                 final Integer objectId, final boolean vote) throws VotesException {
        setContentType(contentType);
        setUserId(userId);
        setObjectId(objectId);
        setVote(vote);
    }

    public boolean equals(Votes votes){
        return this.contentType == votes.getContentType() && this.userId == votes.getUserId() &&
                this.objectId  == votes.getObjectId() && this.vote == votes.getVote();
    }

    public Integer getUserId() {
        return userId;
    }

    private void setUserId(final Integer userId) throws VotesException {
        if (validateIntegerNull(userId)) {
            this.userId = userId;
        } else{
            throw  new VotesException("USERID_CANT_BE_NULL");
        }
    }

    public Integer getContentType() {
        return contentType;
    }

    private void setContentType(final Integer contentType) throws VotesException {
        if (validateIntegerNull(contentType)) {
            this.contentType = contentType;
        } else{
            throw  new VotesException("CONTENT_CANT_BE_NULL");
        }
    }

    public Integer getObjectId() {
        return objectId;
    }

    private void setObjectId(final Integer objectId) throws VotesException {
        if (validateIntegerNull(objectId)) {
            this.objectId = objectId;
        } else{
            throw  new VotesException("OBJECTID_CANT_BE_NULL");
        }
    }

    public boolean isVote() {
        return vote;
    }

    private void setVote(final boolean vote) {
        this.vote = vote;
    }

    public boolean getVote () {
        return this.vote;
    }

    //Validation methods

    private boolean validateIntegerNull(final Integer integer){
        if (integer == null) {
            return false;
        }
        return true;
    }


}
