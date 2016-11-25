package gppmds.wikilegis.model;

import gppmds.wikilegis.exception.VotesException;

public class Vote {

    private static final String USERID_CANT_BE_NULL = "Id do usuário não pode ser nulo";
    private static final String CONTENT_CANT_BE_NULL = "Conteudo não pode ser nulo";
    private static final String OBJECTID_CANT_BE_NULL = "Id do segmento não pode ser nulo";
    private static final String ID_CANT_BE_NULL = "Id não pode ser nulo";

    private Integer id;
    private Integer userId;
    private Integer contentType;
    private Integer objectId;
    private boolean vote;

    public Vote(int id, final Integer userId, final Integer contentType,
                final Integer objectId, final boolean vote) throws VotesException {
        setContentType(contentType);
        setUserId(userId);
        setObjectId(objectId);
        setVote(vote);
        setId(id);
    }

    public Vote (int objectId , boolean vote) throws VotesException {
        setObjectId(objectId);
        setVote(vote);
    }
    public boolean equals(Vote votes){
        return this.id == votes.getId() && this.contentType == votes.getContentType() && this.userId == votes.getUserId() &&
                this.objectId  == votes.getObjectId() && this.vote == votes.getVote();
    }

    public Integer getUserId() {
        return userId;
    }

    private void setUserId(final Integer userId) throws VotesException {
        if (validateIntegerisNotNull(userId)) {
            this.userId = userId;
        } else{
            throw  new VotesException(USERID_CANT_BE_NULL);
        }
    }

    public Integer getContentType() {
        return contentType;
    }

    private void setContentType(final Integer contentType) throws VotesException {
        if (validateIntegerisNotNull(contentType)) {
            this.contentType = contentType;
        } else{
            throw  new VotesException(CONTENT_CANT_BE_NULL);
        }
    }

    public Integer getObjectId() {
        return objectId;
    }

    private void setObjectId(final Integer objectId) throws VotesException {
        if (validateIntegerisNotNull(objectId)) {
            this.objectId = objectId;
        } else{
            throw  new VotesException(OBJECTID_CANT_BE_NULL);
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

    private void setId(Integer id) throws VotesException{
        if(validateIntegerisNotNull(id)){
            this.id = id;
        }else{
            throw new VotesException(ID_CANT_BE_NULL);
        }

    }

    public Integer getId() {
        return id;
    }

    private boolean validateIntegerisNotNull(final Integer integer){
        if (integer == null) {
            return false;
        }
        return true;
    }


}
