package cart.dao;

import cart.domain.Product;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public Long insert(final Product product) {
        final SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(product);

        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
    }

    public Product findById(final Long id) {
        validateExistData(id);

        final String sql = "select id, name, price, image_url from Product where id = ?";

        return jdbcTemplate.queryForObject(sql, productRowMapper(), id);
    }

    public List<Product> findAll() {
        final String sql = "select id, name, price, image_url from Product";

        return jdbcTemplate.query(sql, productRowMapper());
    }

    public void update(final Product newProduct) {
        final String sql = "update Product set name = ?, price = ?, image_url = ? where id = ?";

        jdbcTemplate.update(sql,
                newProduct.getName(),
                newProduct.getPrice(),
                newProduct.getImage_url(),
                newProduct.getId());
    }

    public void delete(final Long id) {
        validateExistData(id);
        final String sql = "delete Product where id = ?";

        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Product> productRowMapper() {
        return (resultSet, rowNum) -> new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image_url")
        );
    }

    private void validateExistData(final Long id) {
        final String sql = "SELECT count(*) FROM Product WHERE id = ?";

        if (jdbcTemplate.queryForObject(sql, Integer.class, id) == 0) {
            throw new IllegalArgumentException("존재하지 않는 id입니다.");
        }
    }
}