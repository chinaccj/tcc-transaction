package com.touna.tcc.demo.pay.dao;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;

import com.touna.tcc.demo.pay.dao.model.Account;
import com.touna.tcc.demo.pay.dao.model.PreAccount;

/**
 * Created by chenchaojian on 17/6/11.
 */
public class AccountDaoImpl implements AccountDao {
    private static final Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);

    private SqlSession sqlSession;

    @Override
    public void prePay(PreAccount preAccount) {
        try {

            //data check
            if (preAccount.getOperation() != Operation.PAY.getValue()) {
                throw new IllegalArgumentException("prePay operation should be 0");
            }

            sqlSession.insert("pre_account.insertWithoutToAccountId", preAccount);
        } catch (DuplicateKeyException ex) {//幂等性校验 该预处理已经提交过.忽略这种异常
            logger.warn("pre account had done before " + ex.getMessage(), ex);
        }

    }

    @Override
    public void payCommit(String xid) {
        PreAccount param = new PreAccount();
        param.setXid(xid);

        PreAccount preAccount = sqlSession.selectOne("pre_account.selectByXid", param);
        if (preAccount == null) {
            //幂等性操作。
            return;
        }

        Account account = new Account();
        account.setAccountId(preAccount.getAccountId());
        account.setDelta(preAccount.getDelta());
        sqlSession.update("account.pay", account);

        sqlSession.delete("pre_account.deleteByXid",xid);

    }

    @Override
    public void payRollback(String xid) {
        sqlSession.delete("pre_account.deleteByXid",xid);
    }

    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
}
