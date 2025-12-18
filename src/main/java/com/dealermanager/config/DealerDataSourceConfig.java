package com.dealermanager.config;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 代销商库配置
 * @author
 * @date
 */
@Configuration
@MapperScan(basePackages = "com.dealermanager.mapper_dealer", sqlSessionTemplateRef  = "dealerSqlSessionTemplate")
public class DealerDataSourceConfig {


    @Bean(name = "dealerDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.dealer")
    public DataSource DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dealerSqlSessionFactory")
    @Primary
    public SqlSessionFactory SqlSessionFactory(@Qualifier("dealerDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        //SqlSessionFactoryBean bean=new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //分页拦截器
        bean.setPlugins(paginationInterceptor());
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper_dealer/*.xml"));
        return bean.getObject();
    }

    //创建事务
    @Bean(name = "dealerTransactionManager")
    @Primary
    public DataSourceTransactionManager TransactionManager(@Qualifier("dealerDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    //用于注入dao
    @Bean(name = "dealerSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate SqlSessionTemplate(@Qualifier("dealerSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    //分页功能
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
