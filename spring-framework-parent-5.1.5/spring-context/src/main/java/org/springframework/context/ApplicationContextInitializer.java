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

/**
 * Callback interface for initializing a Spring {@link ConfigurableApplicationContext}
 * prior to being {@linkplain ConfigurableApplicationContext#refresh() refreshed}.
 * 
 * <p> 回调接口，用于在刷新之前初始化Spring ConfigurableApplicationContext。
 *
 * <p>Typically used within web applications that require some programmatic initialization
 * of the application context. For example, registering property sources or activating
 * profiles against the {@linkplain ConfigurableApplicationContext#getEnvironment()
 * context's environment}. See {@code ContextLoader} and {@code FrameworkServlet} support
 * for declaring a "contextInitializerClasses" context-param and init-param, respectively.
 * 
 * <p> 在web 应用程序中被典型使用,它需要应用程序上下文进行某些编程初始化.例如,针对上下文的环境注册属性源或者激活配置文件,
 * 请参阅ContextLoader和FrameworkServlet支持，分别声明“contextInitializerClasses”context-param
 * 和init-param。
 *  
 *
 * <p>{@code ApplicationContextInitializer} processors are encouraged to detect
 * whether Spring's {@link org.springframework.core.Ordered Ordered} interface has been
 * implemented or if the @{@link org.springframework.core.annotation.Order Order}
 * annotation is present and to sort instances accordingly if so prior to invocation.
 * 
 * <p> 鼓励{@code ApplicationContextInitializer}处理器检测是否已经实现Spring的{@link org.springframework.core.Ordered Ordered}
 * 接口,或者是否存在@{@link org.springframework.core.annotation.Order Order}注解,并且如何在调用之前应相应地对实例进行排序
 *
 * @author Chris Beams
 * @since 3.1
 * @param <C> the application context type 应用程序上下文类型
 * @see org.springframework.web.context.ContextLoader#customizeContext
 * @see org.springframework.web.context.ContextLoader#CONTEXT_INITIALIZER_CLASSES_PARAM
 * @see org.springframework.web.servlet.FrameworkServlet#setContextInitializerClasses
 * @see org.springframework.web.servlet.FrameworkServlet#applyInitializers
 */
public interface ApplicationContextInitializer<C extends ConfigurableApplicationContext> {

	/**
	 * Initialize the given application context.
	 * 
	 * <p> 初始化给定应用程序上下文
	 * 
	 * @param applicationContext the application to configure
	 * 
	 * <p> 要配置的应用程序
	 */
	void initialize(C applicationContext);

}
