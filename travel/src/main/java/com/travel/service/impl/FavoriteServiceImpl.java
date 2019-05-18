package com.travel.service.impl;

import com.travel.dao.IFavoriteDao;
import com.travel.domain.Favorite;
import com.travel.domain.User;
import com.travel.factory.BeanFactory;
import com.travel.service.IFavotiteService;
import com.travel.utils.JdbcUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class FavoriteServiceImpl implements IFavotiteService {
    private IFavoriteDao fd = (IFavoriteDao) BeanFactory.getBean("favoriteDaoImpl");

    private JdbcTemplate jt = new JdbcTemplate(JdbcUtil.getDataSource());
    private DataSourceTransactionManager dtm =  new DataSourceTransactionManager(JdbcUtil.getDataSource());
    private TransactionStatus status;

    @Override
    public Favorite findUserFavorite(String rid, User user) {
        Favorite favorite = fd.findUserFavorite(rid, user);
        return favorite;
    }

    @Override
    public Integer addFavorite(String rid, User user) {
        Integer count= null;
        try {
            startAffair();
            fd.addFavorite(jt,rid,user);
            fd.updateRouteCount(jt,rid);
            dtm.commit(status);
            count = fd.findRouteCount(rid);
        }catch (Exception e){
            dtm.rollback(status);
            throw new RuntimeException("收藏失败");
        }
        return count;

    }

    private  void startAffair(){
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        status = dtm.getTransaction(def);
    }

    public static void main(String[] args) {

    }


}

