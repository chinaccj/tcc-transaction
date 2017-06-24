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
    public void insert(PreAccount preAccount) {
        try {

            //data check
            if (preAccount.getOperation() != Operation.PAY.getValue()) {
                throw new IllegalArgumentException("prePay operation should be 0");
            }

            sqlSession.insert("pre_account.insert", preAccount);
        } catch (DuplicateKeyException ex) {//幂等性校验 该预处理已经提交过.忽略这种异常
            logger.warn("pre account had done before " + ex.getMessage(), ex);
        }
    }


    @Override
    public PreAccount selectPreAccountByXid(String xid) {
        return  sqlSession.selectOne("pre_account.selectByXid", xid);
    }

    @Override
    public PreAccount selectPreAccountByXidForUpdate(String xid) {
        return  sqlSession.selectOne("pre_account.selectByXidForUpdate", xid);
    }

    @Override
    public Account selectAccountById(String accountId) {
        Account param = new Account();
        param.setAccountId(accountId);
        return sqlSession.selectOne("account.selectById", param);
    }

    @Override
    public Account selectAccountByIdForUpdate(String accountId) {
        Account param = new Account();
        param.setAccountId(accountId);
        return sqlSession.selectOne("account.selectByIdForUpdate", param);
    }


    @Override
    public int balanceMinusByDelta(Account account) {
        return sqlSession.update("account.pay", account);
    }

    @Override
    public int preBalanceMinusByDelta(Account account) {
        return sqlSession.update("account.prePay", account);
    }

    @Override
    public int preBalanceAddByDelta(Account account) {
        return sqlSession.update("account.prePayRollback", account);
    }

    @Override
    public int deletePreAccountByXid(String xid) {
        return sqlSession.delete("pre_account.deleteByXid",xid);
    }



    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
}
