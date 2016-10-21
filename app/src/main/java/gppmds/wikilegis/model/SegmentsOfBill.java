package gppmds.wikilegis.model;

import gppmds.wikilegis.exception.SegmentsOfBillException;

public class SegmentsOfBill {

    private static final String IDBILL_CANT_BE_NULL = "idBill não pode ser carregado";
    private static final String IDSEGMENT_CANT_BE_NULL = "idBill não pode ser carregado";

    private Integer idSegment;
    private Integer idBill;

    public SegmentsOfBill(final Integer idBill, final Integer idSegment)
            throws SegmentsOfBillException {
        setIdBill(idBill);
        setIdSegment(idSegment);
    }

    public boolean equals (SegmentsOfBill segmentsOfBill){
        return (this.idBill.equals(segmentsOfBill.getIdBill()) &&
                this.idSegment.equals(segmentsOfBill.getIdSegment()) );
    }

    public Integer getIdBill() {
        return idBill;
    }

    private void setIdBill(final Integer idBill) throws SegmentsOfBillException {

        if (validateIntegerNull(idBill)) {
            this.idBill = idBill;
        } else {
            throw new SegmentsOfBillException(IDBILL_CANT_BE_NULL);
        }
    }

    public Integer getIdSegment() {
        return idSegment;
    }

    private void setIdSegment(final Integer idSegment) throws SegmentsOfBillException {

        if (validateIntegerNull(idSegment)) {
            this.idSegment = idSegment;
        } else {
            throw new SegmentsOfBillException(IDSEGMENT_CANT_BE_NULL);
        }
    }

    private boolean validateIntegerNull(final Integer integer){
        if (integer == null){
            return false;
        }
        return true;
    }
}
