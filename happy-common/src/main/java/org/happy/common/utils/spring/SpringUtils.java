package org.happy.common.utils.spring;

import org.happy.common.utils.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Spring utils for static access to Spring beans and context.
 *
 * @author happy
 */
@Component
public final class SpringUtils implements BeanFactoryPostProcessor, ApplicationContextAware {
    private static ConfigurableListableBeanFactory beanFactory;
    private static ApplicationContext ac;

    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtils.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext ac) throws BeansException {
        SpringUtils.ac = ac;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) beanFactory.getBean(name);
    }

    public static <T> T getBean(Class<T> clz) throws BeansException {
        return beanFactory.getBean(clz);
    }

    public static boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.isSingleton(name);
    }

    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getType(name);
    }

    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getAliases(name);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getAopProxy(T invoker) {
        Object proxy = AopContext.currentProxy();
        if (((Advised) proxy).getTargetSource().getTargetClass() == invoker.getClass()) {
            return (T) proxy;
        }
        return invoker;
    }

    public static String[] getActiveProfiles() {
        return ac.getEnvironment().getActiveProfiles();
    }

    public static String getActiveProfile() {
        final String[] activeProfiles = getActiveProfiles();
        return StringUtils.isNotEmpty(activeProfiles) ? activeProfiles[0] : null;
    }

    public static Environment getEnvironment() {
        return ac.getEnvironment();
    }

    public static String getRequiredProperty(String key) {
        return getEnvironment().getRequiredProperty(key);
    }
}
