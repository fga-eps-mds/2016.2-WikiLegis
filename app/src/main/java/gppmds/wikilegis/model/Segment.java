package gppmds.wikilegis.model;

import gppmds.wikilegis.exception.SegmentException;

/**
 * Created by augusto on 10/09/16.
 */
public class Segment {

    private static final String CONTENT_CANT_BE_EMPTY = "Conteudo não pode ser carregado";
    private static final String ID_CANT_BE_NULL = "Id não pode ser carregado";
    private static final String ORDER_CANT_BE_NULL = "Order não pode ser carregado";
    private static final String BILL_CANT_BE_NULL = "Bill não pode ser carregado";
    private static final String REPLACED_CANT_BE_NULL = "Replaced não pode ser carregado";
    private static final String PARENT_CANT_BE_NULL = "Parent não pode ser carregado";
    private static final String TYPE_CANT_BE_NULL = "Type não pode ser carregado";
    private static final String NUMBER_CANT_BE_NULL = "Number não pode ser carregado";
    private static final String IDAUTHOR_CANT_BE_NULL = "Author id não pode ser carregado";
    private static final String IDVOTE_CANT_BE_NULL = "Vote id não pode ser carregado";
    private static final String IDCOMMENT_CANT_BE_NULL = "Commend id não pode ser carregado";


    private Integer id;
    private Integer order;
    private Integer bill;
    private boolean original;
    private Integer replaced;
    private Integer parent;
    private Integer type;
    private Integer number;
    private String content;
    private Integer idAuthor;
    private Integer idVote;
    private Integer idComment;


    //Rever a parte dos Id's (Do comentário pode ser só o Id, voto tem que ser um array de id's, comentário também!)

    public Segment(Integer id, Integer order, Integer bill, boolean original, Integer replaced,
                    Integer parent, Integer type, Integer number, String content, Integer idAuthor,
                    Integer idVote, Integer idComment) throws SegmentException{
        setId(id);
        setOrder(order);
        setBill(bill);
        setOriginal(original);
        setReplaced(replaced);
        setParent(parent);
        setType(type);
        setNumber(number);
        setContent(content);
        setIdComment(idComment);
        setIdAuthor(idAuthor);
        setIdVote(idVote);

    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id)throws SegmentException {
        if(validateIntegerNull(id)) {
            this.id = id;
        } else{
            throw  new SegmentException(ID_CANT_BE_NULL);
        }
    }

    public Integer getOrder() {
        return order;
    }

    private void setOrder(Integer order)throws SegmentException {
        if(validateIntegerNull(order)) {
            this.order = order;
        } else{
            throw  new SegmentException(ORDER_CANT_BE_NULL);
        }

    }

    public Integer getBill() {
        return bill;
    }

    private void setBill(Integer bill) throws SegmentException {
        if(validateIntegerNull(bill)) {
            this.bill = bill;
        } else{
            throw  new SegmentException(BILL_CANT_BE_NULL);
        }

    }

    public boolean isOriginal() {
        return original;
    }

    private void setOriginal(boolean original) {
        this.original = original;
    }

    public Integer getReplaced() {
        return replaced;
    }

    private void setReplaced(Integer replaced) throws SegmentException{
        if(validateIntegerNull(replaced)) {
            this.replaced = replaced;
        } else {
            throw new SegmentException(REPLACED_CANT_BE_NULL);
        }
    }

    public Integer getParent() {
        return parent;
    }

    private void setParent(Integer parent) throws SegmentException{
        if(validateIntegerNull(parent)) {
            this.parent = parent;
        } else {
            throw new SegmentException(PARENT_CANT_BE_NULL);
        }

    }

    public Integer getType() {
        return type;
    }

    private void setType(Integer type) throws SegmentException{
        if(validateIntegerNull(type)) {
            this.type = type;
        } else {
            throw new SegmentException(TYPE_CANT_BE_NULL);
        }
    }

    public Integer getNumber() {

        return number;
    }

    private void setNumber(Integer number) throws SegmentException {
        if(validateIntegerNull(number)) {
            this.number = number;
        } else {
            throw new SegmentException(NUMBER_CANT_BE_NULL);
        }

    }

    public String getContent() {
        return content;
    }

    private void setContent(String content)throws SegmentException {
        if(validateStringEmpty(content)) {
            this.content = content;
        } else{
            throw new SegmentException(CONTENT_CANT_BE_EMPTY);
        }
    }

    public Integer getIdAuthor() {
        return idAuthor;
    }

    private void setIdAuthor(Integer idAuthor)throws SegmentException {
        if(validateIntegerNull(idAuthor)) {
            this.idAuthor = idAuthor;
        } else {
            throw new SegmentException(IDAUTHOR_CANT_BE_NULL);
        }

    }

    public Integer getIdVote() {
        return idVote;
    }

    private void setIdVote(Integer idVote) throws SegmentException{
        if(validateIntegerNull(idVote)) {
            this.idVote = idVote;
        } else {
            throw new SegmentException(IDVOTE_CANT_BE_NULL);
        }

    }

    public Integer getIdComment() {
        return idComment;
    }

    private void setIdComment(Integer idComment)throws SegmentException {
        if(validateIntegerNull(idComment)) {
            this.idComment = idComment;
        } else {
            throw new SegmentException(IDCOMMENT_CANT_BE_NULL);
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

    private boolean validateStringEmpty(final String string) {
        if (string == null) {
            return false;
        } else {
            return true;
        }
    }
}