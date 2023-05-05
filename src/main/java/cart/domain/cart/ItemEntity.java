package cart.domain.cart;

public class ItemEntity {

    private final CartId id;
    private final Item item;

    public ItemEntity(final long id, final long memberId, final long productId) {
        this.id = new CartId(id);
        this.item = new Item(memberId, productId);
    }

    public long getId() {
        return id.getValue();
    }

    public long getMemberId() {
        return item.getMemberId();
    }

    public long getProductId() {
        return item.getProductId();
    }
}
