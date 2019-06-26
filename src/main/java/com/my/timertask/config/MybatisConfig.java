package com.my.timertask.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.github.pagehelper.PageInterceptor;
/** <blockquote>
* Mybatis配置
* @author [liangpr]
* @date [2019-01-23 11:15:57]
*/
@MapperScan("com.my.timertask.dao")
@Configuration
public class MybatisConfig {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String SQL_SESSION_FACTORY_NAME = "sqlSessionFactory";
    // mapper 接口参数配置
    @Value("${mybatis.mapper-locations}")
    private String xmllocations;
    // 分页插件参数配置
    @Value("${mybatis.page-helper.properties.offsetAsPageNum}")
    private String offsetAsPageNum;
    @Value("${mybatis.page-helper.properties.rowBoundsWithCount}")
    private String rowBoundsWithCount;
    @Value("${mybatis.page-helper.properties.reasonable}")
    private String reasonable;
    @Value("${mybatis.page-helper.properties.dialect}")
    private String dialect;
    @Autowired
    private DataSource dataSource;

    @Bean(value=SQL_SESSION_FACTORY_NAME)
    @Scope("singleton")
    public SqlSessionFactoryBean initSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = null;
        try {
            sqlSessionFactoryBean = new SqlSessionFactoryBean();
            
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
            // 设置数据源
            sqlSessionFactoryBean.setDataSource(dataSource);
            
            // 设置mapper xml所在的包
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources(xmllocations));
            // 设置MyBatis分页插件
            sqlSessionFactoryBean.setPlugins(new Interceptor[]{initPageHelper()});
            // 设置启用驼峰命名转化
            configuration.setMapUnderscoreToCamelCase(true);
            // 允许jdbc自动生成主键
            configuration.setUseGeneratedKeys(true);
            sqlSessionFactoryBean.setConfiguration(configuration);
            logger.info("=========================================sqlSessionFactoryBean初始化完成=========================================");
        } catch (Exception e) {
            logger.error("=========================================sqlSessionFactoryBean初始化失败=========================================", e);
        }
        return sqlSessionFactoryBean;
    }
    /**
    <blockquote>
    <h1>配置mybatis的分页插件pageHelper</h1>
    * @return
    */
    public PageInterceptor initPageHelper(){
        PageInterceptor pageHelper = new PageInterceptor();
        Properties properties = new Properties();
        //设置为true时，会将RowBounds第一个参数offset当成pageNum页码使用
        properties.setProperty("offsetAsPageNum",offsetAsPageNum);
        //设置为true时，使用RowBounds分页会进行count查询
        properties.setProperty("rowBoundsWithCount",rowBoundsWithCount);
        //启用合理化时，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页
        properties.setProperty("reasonable",reasonable);
        //配置mysql数据库的方言
        // properties.setProperty("dialect",dialect);
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
