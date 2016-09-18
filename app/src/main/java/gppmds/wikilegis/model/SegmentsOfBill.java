package gppmds.wikilegis.model;

import gppmds.wikilegis.exception.SegmentException;

/**
 * Created by marcelo on 9/16/16.
 */
public class SegmentsOfBill {

    private static final String IDBILL_CANT_BE_NULL = "idBill não pode ser carregado";
    private static final String IDSEGMENT_CANT_BE_NULL = "idBill não pode ser carregado";
    private static final String POSITION_CANT_BE_NULL = "idBill não pode ser carregado";


    private Integer idSegment;
    private Integer idBill;
    private Integer position;

    public SegmentsOfBill(Integer idBill, Integer idSegment, Integer position) throws SegmentException {
        setIdBill(idBill);
        setIdSegment(idSegment);
        setPosition(position);
    }

    public Integer getIdBill() {
        return idBill;
    }

    private void setIdBill(Integer idBill) throws SegmentException {

        if(validateIntegerNull(idBill)) {
            this.idBill = idBill;
        } else {
            throw new SegmentException(IDBILL_CANT_BE_NULL);
        }
    }

    public Integer getIdSegment() {
        return idSegment;
    }

    private void setIdSegment(Integer idSegment) throws SegmentException {

        if(validateIntegerNull(idSegment)) {
            this.idSegment = idSegment;
        } else {
            throw new SegmentException(IDSEGMENT_CANT_BE_NULL);
        }
    }

    public Integer getPosition() {
        return position;
    }

    private void setPosition(Integer position) throws SegmentException {

        if(validateIntegerNull(position)) {
            this.position = position;
        } else {
                throw new SegmentException(POSITION_CANT_BE_NULL);
        }
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
