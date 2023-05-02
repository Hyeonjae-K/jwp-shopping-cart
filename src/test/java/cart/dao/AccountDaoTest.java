package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.account.Account;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql("classpath:schema.sql")
class AccountDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private AccountDao accountDao;

    @BeforeEach
    void setup() {
        this.accountDao = new AccountDao(jdbcTemplate);
    }

    @Test
    void 사용자_목록_반환() {
        final List<Account> accounts = accountDao.fetchAll();

        assertThat(accounts.size()).isEqualTo(2);
    }

    @Test
    void 사용자_존재_여부_확인() {
        final boolean isExist = accountDao.isMember(new Account("user1@email.com", "password1"));

        assertThat(isExist).isTrue();
    }
}