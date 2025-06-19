package org.happy.framework.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.happy.framework.config.mybatis.EnumTypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Set;

/**
 * Mybatis支持*匹配扫描包
 *
 * @author happy
 */
@org.springframework.context.annotation.Configuration
@Slf4j
public class MyBatisConfig {
    @Autowired
    private MybatisProperties props;

//    static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
//
//    public static String setTypeAliasesPackage(String typeAliasesPackage) {
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
//        List<String> allResult = new ArrayList<>();
//        try {
//            for (String aliasesPackage : typeAliasesPackage.split(",")) {
//                List<String> result = new ArrayList<>();
//                aliasesPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
//                                 + ClassUtils.convertClassNameToResourcePath(aliasesPackage.trim()) + "/" + DEFAULT_RESOURCE_PATTERN;
//                Resource[] resources = resolver.getResources(aliasesPackage);
//                if (resources != null && resources.length > 0) {
//                    MetadataReader metadataReader = null;
//                    for (Resource resource : resources) {
//                        if (resource.isReadable()) {
//                            metadataReader = metadataReaderFactory.getMetadataReader(resource);
//                            try {
//                                result.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName());
//                            } catch (ClassNotFoundException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//                if (!result.isEmpty()) {
//                    HashSet<String> hashResult = new HashSet<>(result);
//                    allResult.addAll(hashResult);
//                }
//            }
//            if (!allResult.isEmpty()) {
//                typeAliasesPackage = String.join(",", allResult);
//            } else {
//                throw new RuntimeException("mybatis typeAliasesPackage 路径扫描错误,参数typeAliasesPackage:" + typeAliasesPackage + "未找到任何包");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return typeAliasesPackage;
//    }
//
//    public Resource[] resolveMapperLocations(String[] mapperLocations) {
//        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
//        List<Resource> resources = new ArrayList<>();
//        if (mapperLocations != null) {
//            for (String mapperLocation : mapperLocations) {
//                try {
//                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
//                    resources.addAll(Arrays.asList(mappers));
//                } catch (IOException e) {
//                    // ignore
//                }
//            }
//        }
//        return resources.toArray(new Resource[0]);
//    }
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//        var typeAliasesPackage = props.getTypeAliasesPackage();
//        var mapperLocations = props.getMapperLocations();
//        var configLocation = props.getConfigLocation();
//        typeAliasesPackage = setTypeAliasesPackage(typeAliasesPackage);
//        VFS.addImplClass(SpringBootVFS.class);
//
//        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource);
//        sessionFactory.setTypeAliasesPackage(typeAliasesPackage);
//        sessionFactory.setMapperLocations(resolveMapperLocations(mapperLocations));
//        sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
//        return sessionFactory.getObject();
//    }


    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        var factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);

        // 设置别名包路径
        factory.setTypeAliasesPackage(props.getTypeAliasesPackage());

        // 设置 Mapper XML 路径
        var resolver = new PathMatchingResourcePatternResolver();
        var mapperLocations = resolver.getResources(props.getMapperLocations()[0]);
        factory.setMapperLocations(mapperLocations);

        // Java 配置替代 configLocation
        var configuration = new Configuration();
        // configuration.setMapUnderscoreToCamelCase(true);
        configuration.setUseGeneratedKeys(true);
        configuration.setCacheEnabled(true);
        configuration.setDefaultExecutorType(ExecutorType.SIMPLE);
        configuration.setLogImpl(org.apache.ibatis.logging.slf4j.Slf4jImpl.class);

        // 动态注册枚举 TypeHandler
        registerEnumTypeHandlers(configuration.getTypeHandlerRegistry(), "org.happy.common.enums.entity");

        factory.setConfiguration(configuration);
        return factory.getObject();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void registerEnumTypeHandlers(TypeHandlerRegistry registry, String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<? extends Enum>> enums = reflections.getSubTypesOf(Enum.class);
        for (Class<? extends Enum> enumClass : enums) {
            try {
                log.debug("Registering EnumTypeHandler for {}", enumClass.getName());
                registry.register(enumClass, new EnumTypeHandler(enumClass));
            } catch (Exception e) {
                log.warn("Failed to register EnumTypeHandler for {}: {}", enumClass.getName(), e.getMessage());
            }
        }
    }
}