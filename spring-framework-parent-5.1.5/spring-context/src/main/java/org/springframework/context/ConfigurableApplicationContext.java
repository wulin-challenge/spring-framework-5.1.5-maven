/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.context;

import java.io.Closeable;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.lang.Nullable;

/**
 * SPI interface to be implemented by most if not all application contexts.
 * Provides facilities to configure an application context in addition
 * to the application context client methods in the
 * {@link org.springframework.context.ApplicationContext} interface.
 * 
 * <p> SPI 接口由大多数(如果不是全部)应用程序上下文实现,在ApplicationContext中除了应用程序上下文客户端方法外,
 * 还提供了配置一个应用程序上下文的工具
 *
 * <p>Configuration and lifecycle methods are encapsulated here to avoid
 * making them obvious to ApplicationContext client code. The present
 * methods should only be used by startup and shutdown code.
 * 
 * <p> 在这里配置和声明周期方法被封装,为了避免使他们对ApplicationContext客户端代码变得明显,当前方法应该只能由startup和shutdown代码使用
 *
 * @author Juergen Hoeller
 * @author Chris Beams
 * @since 03.11.2003
 */
public interface ConfigurableApplicationContext extends ApplicationContext, Lifecycle, Closeable {

	/**
	 * Any number of these characters are considered delimiters between
	 * multiple context config paths in a single String value.
	 * 
	 * <p> 在一个单一的字符串值中在多个上下文配置路径之间,任意数量的这些字符被认为是分隔符
	 * 
	 * @see org.springframework.context.support.AbstractXmlApplicationContext#setConfigLocation
	 * @see org.springframework.web.context.ContextLoader#CONFIG_LOCATION_PARAM
	 * @see org.springframework.web.servlet.FrameworkServlet#setContextConfigLocation
	 */
	String CONFIG_LOCATION_DELIMITERS = ",; \t\n";

	/**
	 * Name of the ConversionService bean in the factory.
	 * If none is supplied, default conversion rules apply.
	 * 
	 * <p> 工厂中的ConversionService bean的名称,如果没有提供,则应用默认的转换规则
	 * 
	 * @since 3.0
	 * @see org.springframework.core.convert.ConversionService
	 */
	String CONVERSION_SERVICE_BEAN_NAME = "conversionService";

	/**
	 * Name of the LoadTimeWeaver bean in the factory. If such a bean is supplied,
	 * the context will use a temporary ClassLoader for type matching, in order
	 * to allow the LoadTimeWeaver to process all actual bean classes.
	 * 
	 * <p> 工厂中的LoadTimeWeaver bean的名称,如果提供了这样一个bean,为了类型匹配这个上下文将使用一个临时的ClassLoader,
	 * 为了允许LoadTimeWeaver处理所有实际bean的classes
	 * @since 2.5
	 * @see org.springframework.instrument.classloading.LoadTimeWeaver
	 */
	String LOAD_TIME_WEAVER_BEAN_NAME = "loadTimeWeaver";

	/**
	 * Name of the {@link Environment} bean in the factory.
	 * 
	 * <p> bean工厂中的Environment bean的名称
	 * 
	 * @since 3.1
	 */
	String ENVIRONMENT_BEAN_NAME = "environment";

	/**
	 * Name of the System properties bean in the factory.
	 * 
	 * spring bean工厂中的 系统属性bean的名称
	 * @see java.lang.System#getProperties()
	 */
	String SYSTEM_PROPERTIES_BEAN_NAME = "systemProperties";

	/**
	 * Name of the System environment bean in the factory.
	 * 
	 * spring bean工厂中的系统环境bean的名称
	 * 
	 * @see java.lang.System#getenv()
	 */
	String SYSTEM_ENVIRONMENT_BEAN_NAME = "systemEnvironment";


	/**
	 * Set the unique id of this application context.
	 * 
	 * 设置唯一的应用程序上下文Id
	 * 
	 * @since 3.0
	 */
	void setId(String id);

	/**
	 * Set the parent of this application context.
	 * 
	 * <p> 设置应用上下文的父亲
	 * 
	 * <p>Note that the parent shouldn't be changed: It should only be set outside
	 * a constructor if it isn't available when an object of this class is created,
	 * for example in case of WebApplicationContext setup.
	 * 
	 * <p> 注意: 不应该改变这个父亲,当这个类创建一个对象时,若果这个对象不可用,它(父亲)应该只能在一个构造器的外部被设置,
	 * 例如,在WebApplicationContext设置的情况下.
	 * 
	 * @param parent the parent context - 父亲上下文
	 * @see org.springframework.web.context.ConfigurableWebApplicationContext
	 */
	void setParent(@Nullable ApplicationContext parent);

	/**
	 * Set the {@code Environment} for this application context.
	 * 
	 * <p> 为应用程序上下文设置 Enviroment
	 * @param environment the new environment 新环境
	 * @since 3.1
	 */
	void setEnvironment(ConfigurableEnvironment environment);

	/**
	 * Return the {@code Environment} for this application context in configurable
	 * form, allowing for further customization.
	 * 
	 * <p> 以配置形式返回这个应用程序上下文的Environment
	 * 
	 * @since 3.1
	 */
	@Override
	ConfigurableEnvironment getEnvironment();

	/**
	 * Add a new BeanFactoryPostProcessor that will get applied to the internal
	 * bean factory of this application context on refresh, before any of the
	 * bean definitions get evaluated. To be invoked during context configuration.
	 * 
	 * <p> 在评估任何bean定义之前，添加一个新的BeanFactoryPostProcessor，它将在刷新时应用于此应用程序上下文的内部bean工厂。
	 *  在上下文配置期间调用。
	 * 
	 * @param postProcessor the factory processor to register - 注册工厂处理器
	 */
	void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);

	/**
	 * Add a new ApplicationListener that will be notified on context events
	 * such as context refresh and context shutdown.
	 * <p>Note that any ApplicationListener registered here will be applied
	 * on refresh if the context is not active yet, or on the fly with the
	 * current event multicaster in case of a context that is already active.
	 * @param listener the ApplicationListener to register
	 * @see org.springframework.context.event.ContextRefreshedEvent
	 * @see org.springframework.context.event.ContextClosedEvent
	 */
	void addApplicationListener(ApplicationListener<?> listener);

	/**
	 * Register the given protocol resolver with this application context,
	 * allowing for additional resource protocols to be handled.
	 * <p>Any such resolver will be invoked ahead of this context's standard
	 * resolution rules. It may therefore also override any default rules.
	 * @since 4.3
	 */
	void addProtocolResolver(ProtocolResolver resolver);

	/**
	 * Load or refresh the persistent representation of the configuration,
	 * which might an XML file, properties file, or relational database schema.
	 * <p>As this is a startup method, it should destroy already created singletons
	 * if it fails, to avoid dangling resources. In other words, after invocation
	 * of that method, either all or no singletons at all should be instantiated.
	 * @throws BeansException if the bean factory could not be initialized
	 * @throws IllegalStateException if already initialized and multiple refresh
	 * attempts are not supported
	 */
	void refresh() throws BeansException, IllegalStateException;

	/**
	 * Register a shutdown hook with the JVM runtime, closing this context
	 * on JVM shutdown unless it has already been closed at that time.
	 * <p>This method can be called multiple times. Only one shutdown hook
	 * (at max) will be registered for each context instance.
	 * @see java.lang.Runtime#addShutdownHook
	 * @see #close()
	 */
	void registerShutdownHook();

	/**
	 * Close this application context, releasing all resources and locks that the
	 * implementation might hold. This includes destroying all cached singleton beans.
	 * <p>Note: Does <i>not</i> invoke {@code close} on a parent context;
	 * parent contexts have their own, independent lifecycle.
	 * <p>This method can be called multiple times without side effects: Subsequent
	 * {@code close} calls on an already closed context will be ignored.
	 */
	@Override
	void close();

	/**
	 * Determine whether this application context is active, that is,
	 * whether it has been refreshed at least once and has not been closed yet.
	 * @return whether the context is still active
	 * @see #refresh()
	 * @see #close()
	 * @see #getBeanFactory()
	 */
	boolean isActive();

	/**
	 * Return the internal bean factory of this application context.
	 * Can be used to access specific functionality of the underlying factory.
	 * 
	 * <p> 返回此应用程序上下文的内部bean工厂。 可用于访问基础工厂的特定功能。
	 * 
	 * <p>Note: Do not use this to post-process the bean factory; singletons
	 * will already have been instantiated before. Use a BeanFactoryPostProcessor
	 * to intercept the BeanFactory setup process before beans get touched.
	 * 
	 * <p> 注意：不要使用它来后处理这个bean工厂,在这之前单例已经被初始化了,在beans得到触发之前,使用一个 BeanFactoryPostProcessor
	 * 来拦截这个BeanFactory的设置过程
	 * 
	 * <p>Generally, this internal factory will only be accessible while the context
	 * is active, that is, in-between {@link #refresh()} and {@link #close()}.
	 * The {@link #isActive()} flag can be used to check whether the context
	 * is in an appropriate state.
	 * 
	 * <p> 通常，只有在上下文处于活动状态时，即在refresh（）和close（）之间，才能访问此内部工厂。 isActive（）标志可用于检查上下文是否处于适当的状态。
	 * 
	 * @return the underlying bean factory - 基础的bean工厂
	 * 
	 * @throws IllegalStateException if the context does not hold an internal
	 * bean factory (usually if {@link #refresh()} hasn't been called yet or
	 * if {@link #close()} has already been called)
	 * 
	 * <p> 如果上下文没有持有内部bean工厂（通常如果还没有调用refresh（）或者已经调用了close（））
	 * 
	 * @see #isActive()
	 * @see #refresh()
	 * @see #close()
	 * @see #addBeanFactoryPostProcessor
	 */
	ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

}
