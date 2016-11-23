package gppmds.wikilegis.model;

import android.util.Log;

import gppmds.wikilegis.exception.SegmentException;

public class Segment {

    private static final String CONTENT_CANT_BE_EMPTY = "Conteudo não pode ser carregado";
    private static final String ID_CANT_BE_NULL = "Id não pode ser carregado";
    private static final String ORDER_CANT_BE_NULL = "Order não pode ser carregado";
    private static final String BILL_CANT_BE_NULL = "Bill não pode ser carregado";
    private static final String REPLACED_CANT_BE_NULL = "Replaced não pode ser carregado";
    private static final String PARENT_CANT_BE_NULL = "Parent não pode ser carregado";
    private static final String TYPE_CANT_BE_NULL = "Type não pode ser carregado";
    private static final String NUMBER_CANT_BE_NULL = "Number não pode ser carregado";

    private Integer id;
    private Integer order;
    private Integer bill;
    private boolean original;
    private Integer replaced;
    private Integer parent;
    private Integer type;
    private Integer number;
    private String content;
    private String date;

    public Segment(final Integer id, final Integer order, final Integer bill, final boolean original,
                   final Integer replaced, final Integer parent, final Integer type,
                   final Integer number, final String content, final String date) throws SegmentException{
        setId(id);
        setOrder(order);
        setBill(bill);
        setOriginal(original);
        setReplaced(replaced);
        setParent(parent);
        setType(type);
        setNumber(number);
        setContent(content);
        setDate(date);
    }


    public Segment(final Integer bill,final Integer replaced, final String content)
            throws SegmentException{
        setBill(bill);
        setReplaced(replaced);
        setContent(content);
    }

    public Segment (final Integer id, final Integer order, final Integer bill, final int original,
                    final Integer replaced, final Integer parent, final Integer type,
                    final Integer number, final String content, final String date) throws SegmentException{
        setId(id);
        setOrder(order);
        setBill(bill);
        setOriginal(original);
        setReplaced(replaced);
        setParent(parent);
        setType(type);
        setNumber(number);
        setContent(content);
        setDate(date);
    }

    public boolean equals(Segment segment) {

        boolean isEverythingEqual = ( this.id.equals(segment.getId()) &&
                this.order.equals(segment.getOrder()) && this.bill.equals(segment.getBill())
                && this.original == segment.isOriginal() &&
                this.replaced.equals(segment.getReplaced()) &&
                this.parent.equals(segment.getParent()) && this.type.equals(segment.getType())
                && this.number.equals(segment.getNumber())  &&
                this.content.equals(segment.getContent())  &&  this.date.equals(segment.getDate()) );

        return isEverythingEqual;
    }

    public Integer getId() {
        return id;
    }

    private void setId(final Integer id)throws SegmentException {
        if (validateIntegerNull(id)) {
            this.id = id;
        } else{
            throw  new SegmentException(ID_CANT_BE_NULL);
        }
    }

    public Integer getOrder() {
        return order;
    }

    private void setOrder(final Integer order)throws SegmentException {
        if (validateIntegerNull(order)) {
            this.order = order;
        } else{
            throw  new SegmentException(ORDER_CANT_BE_NULL);
        }

    }

    public Integer getBill() {
        return bill;
    }

    private void setBill(final Integer bill) throws SegmentException {
        if (validateIntegerNull(bill)) {
            this.bill = bill;
        } else{
            throw  new SegmentException(BILL_CANT_BE_NULL);
        }

    }

    public boolean isOriginal() {
        return original;
    }


    private void setOriginal(final boolean original) {
        this.original = original;
    }

    private void setOriginal (final int original){
        if (original == 1){
            this.original = true;
        }
        else {
            this.original = false;
        }
    }

    public Integer getReplaced() {
        return replaced;
    }

    private void setReplaced(final Integer replaced) throws SegmentException{
        if (validateIntegerNull(replaced)) {
            this.replaced = replaced;
        } else {
            throw new SegmentException(REPLACED_CANT_BE_NULL);
        }
    }

    public Integer getParent() {
        return parent;
    }

    private void setParent(final Integer parent) throws SegmentException{
        if (validateIntegerNull(parent)) {
            this.parent = parent;
        } else {
            throw new SegmentException(PARENT_CANT_BE_NULL);
        }

    }

    public Integer getType() {
        return type;
    }

    private void setType(final Integer type) throws SegmentException{
        if (validateIntegerNull(type)) {
            this.type = type;
        } else {
            throw new SegmentException(TYPE_CANT_BE_NULL);
        }
    }

    public Integer getNumber() {

        return number;
    }

    private void setNumber(final Integer number) throws SegmentException {
        if (validateIntegerNull(number)) {
            this.number = number;
        } else {
            throw new SegmentException(NUMBER_CANT_BE_NULL);
        }

    }

    public String getContent() {
        return content;
    }

    private void setContent(final String content) throws SegmentException {
        if (validateStringEmpty(content)) {
            this.content = content;
        } else{
            throw new SegmentException(CONTENT_CANT_BE_EMPTY);
        }
    }

    public String getDate()  { return date; }

    private void setDate(final String date){
        this.date = date;
    }

    private boolean validateIntegerNull(final Integer integer){
        if (integer == null) {
            return false;
        }
        return true;
    }

    private boolean validateStringEmpty(final String string) {
        if (string == null ) {
            return false;
        }
        return true;
    }

    public int booleanToInt(boolean bool) {
        if (bool == true){
            return 1;
        } else {
            return 0;
        }
    }
}