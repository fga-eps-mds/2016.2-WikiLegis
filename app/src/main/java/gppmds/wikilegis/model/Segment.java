package gppmds.wikilegis.model;

/**
 * Created by augusto on 10/09/16.
 */
public class Segment {

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


    public Segment(Integer id, Integer order, Integer bill, boolean original, Integer replaced,
                    Integer parent, Integer type, Integer number, String content, Integer idAuthor,
                    Integer idVote, Integer idComment) {
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

    private void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    private void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getBill() {
        return bill;
    }

    private void setBill(Integer bill) {
        this.bill = bill;
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

    private void setReplaced(Integer replaced) {
        this.replaced = replaced;
    }

    public Integer getParent() {
        return parent;
    }

    private void setParent(Integer parent) {
        this.parent = parent;
    }

    public Integer getType() {
        return type;
    }

    private void setType(Integer type) {
        this.type = type;
    }

    public Integer getNumber() {
        return number;
    }

    private void setNumber(Integer number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    private void setContent(String content) {
        this.content = content;
    }

    public Integer getIdAuthor() {
        return idAuthor;
    }

    private void setIdAuthor(Integer idAuthor) {
        this.idAuthor = idAuthor;
    }

    public Integer getIdVote() {
        return idVote;
    }

    private void setIdVote(Integer idVote) {
        this.idVote = idVote;
    }

    public Integer getIdComment() {
        return idComment;
    }

    private void setIdComment(Integer idComment) {
        this.idComment = idComment;
    }
}