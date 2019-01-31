package com.my.timertask.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;

/**
<blockquote>
<h1>Druid连接池的配置文件</h1>
* @author [lpr]
* @date [2018年10月17日 下午4:57:42]
*/
@Configuration
public class DataSourceConfig {
    private static Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
    public static final String DATA_SOURCE_BEAN_NAME = "dataSource";
    //数据源属性
    @Value("${datasource.driver-class-name}")
    private String driverClass;
    @Value("${datasource.url}")
    private String url;
    @Value("${datasource.username}")
    private String username;
    @Value("${datasource.password}")
    private String password;
    //连接池属性
    @Value("${datasource.initialSize}")
    private int initialSize;
    @Value("${datasource.minIdle}")
    private int minIdle;
    @Value("${datasource.maxActive}")
    private int maxActive;
    @Value("${datasource.maxWait}")
    private long maxWait;
    @Value("${datasource.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis;
    @Value("${datasource.minEvictableIdleTimeMillis}")
    private long minEvictableIdleTimeMillis;
    @Value("${datasource.testWhileIdle}")
    private Boolean testWhileIdle;
    @Value("${datasource.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${datasource.testOnReturn}")
    private boolean testOnReturn;
    @Value("${datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    @Value("${datasource.filters}")
    private String filters;
    @Value("${datasource.connectionProperties}")
    private String connectionProperties;
    @Value("${datasource.druidServlet.loginuser}")
    private String loginuser;
    @Value("${datasource.druidServlet.loginpass}")
    private String loginpass;
    @Value("${datasource.druidServlet.loginfilter}")
    private String loginfilter;
    /**
    <blockquote>
    <h1>配置DruidDataSource</h1>
    * @return
    * @throws Exception
     */
    @Bean(value=DATA_SOURCE_BEAN_NAME)
    @Scope("singleton")
    //默认为主数据源
    @Primary
    public DataSource initDataSource() throws Exception{
        DruidDataSource druidDataSource = null;
        try {
            // 实例化DataSource并赋值属性
            druidDataSource = new DruidDataSource();
            druidDataSource.setDriverClassName(driverClass);
            druidDataSource.setUrl(url);
            druidDataSource.setUsername(username);
            druidDataSource.setPassword(password);
            druidDataSource.setInitialSize(initialSize);
            druidDataSource.setMinIdle(minIdle);
            druidDataSource.setMaxActive(maxActive);
            druidDataSource.setMaxWait(maxWait);
            druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
            druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
            druidDataSource.setTestWhileIdle(testWhileIdle);
            druidDataSource.setTestOnBorrow(testOnBorrow);
            druidDataSource.setTestOnReturn(testOnReturn);
            druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
            druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
            druidDataSource.setFilters(filters);
            druidDataSource.setConnectionProperties(connectionProperties);
            // 获取Druid的过滤器判断是否存在WallFilter
            List<Filter> filters = druidDataSource.getProxyFilters();
            Boolean isWallFilter = false;
            for(Filter ff : filters) {
                if(ff instanceof WallFilter) {
                    WallFilter wallFilter = (WallFilter)ff;
                    wallFilter.setConfig(wallConfig());
                    isWallFilter = true;
                }
            }
            if(!isWallFilter) {
                filters = new ArrayList<>();
                filters.add(wallFilter(wallConfig()));
                druidDataSource.setProxyFilters(filters);
            }
            logger.info("=========================================DataSource初始化完成=========================================");
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("=========================================DataSource初始化失败", e);
        }
        return druidDataSource;
    }
    /**
    <blockquote>
    <h1>配置Druid管理页面</h1>
    * @return
     */
    @Bean
    public ServletRegistrationBean<?> druidServlet() {
        logger.info("init Druid Servlet Configuration ");
        ServletRegistrationBean<?> servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), loginfilter);
        //控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", loginuser);
        servletRegistrationBean.addInitParameter("loginPassword", loginpass);
        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }
    @Bean
    public FilterRegistrationBean<?> filterRegistrationBean(){
        FilterRegistrationBean<?> filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,"+loginfilter);
        return filterRegistrationBean;
    }
    @Bean(name = "wallConfig")
    public WallConfig wallConfig(){
        WallConfig wallConfig = new WallConfig();
        wallConfig.setMultiStatementAllow(true);//允许一次执行多条语句
        wallConfig.setNoneBaseStatementAllow(true);//允许一次执行多条语句
        return wallConfig;
    }
    @Bean(name = "wallFilter")
    @DependsOn("wallConfig")
    public WallFilter wallFilter(WallConfig wallConfig){
        WallFilter wfilter = new WallFilter ();
        wfilter.setConfig(wallConfig);
        return wfilter;
    }
}
