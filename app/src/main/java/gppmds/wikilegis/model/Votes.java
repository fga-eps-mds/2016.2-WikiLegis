package gppmds.wikilegis.model;

/**
 * Created by augusto on 13/09/16.
 */
public class Votes {

    private Integer userId;
    private Integer contentType;
    private Integer objectId;
    private boolean vote;

    public Votes(Integer userId, Integer contentType, Integer objectId, boolean vote) {

        setContentType(contentType);
        setUserId(userId);
        setObjectId(objectId);
        setVote(vote);
    }

    public Integer getUserId() {
        return userId;
    }

    private void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getContentType() {
        return contentType;
    }

    private void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Integer getObjectId() {
        return objectId;
    }

    private void setObjectId(Integer objectId) {
        this.objectId = objectId;
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
