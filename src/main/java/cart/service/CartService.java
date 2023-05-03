package cart.service;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.domain.cart.Item;
import cart.domain.cart.ItemEntity;
import cart.domain.product.ProductEntity;
import cart.dto.application.ItemDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final CartDao cartDao;
    private final ProductDao productDao;

    public CartService(final ProductDao productDao, final CartDao cartDao) {
        this.productDao = productDao;
        this.cartDao = cartDao;
    }

    public List<ProductEntity> findAll(final long userId) {
        final List<ItemEntity> items = cartDao.findAll(userId);

        return items.stream()
                .map(item -> productDao.find(item.getProductId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemEntity insert(final ItemDto itemDto) {
        final Item item = new Item(itemDto.getUserId(), itemDto.getProductId());

        final long id = cartDao.insert(item);

        return new ItemEntity(id, item);
    }

    @Transactional
    public void delete(final long id) {
        cartDao.delete(id);
    }
}
