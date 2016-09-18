package gppmds.wikilegis.model;

/**
 * Created by marcelo on 9/16/16.
 */
public class SegmentsOfBill {

    private Integer idSegment;
    private Integer idBill;
    private Integer position;

    public SegmentsOfBill(Integer idBill, Integer idSegment, Integer position) {
        setIdBill(idBill);
        setIdSegment(idSegment);
        setPosition(position);
    }

    public Integer getIdBill() {
        return idBill;
    }

    private void setIdBill(Integer idBill) {

        this.idBill=idBill;
    }

    public Integer getIdSegment() {
        return idSegment;
    }

    private void setIdSegment(Integer idSegment) {
        this.idSegment=idSegment;
    }

    public Integer getPosition() {
        return position;
    }

    private void setPosition(Integer position) {
        this.position=position;
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
