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

package org.springframework.beans.factory.support;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.AliasRegistry;

/**
 * Interface for registries that hold bean definitions, for example RootBeanDefinition
 * and ChildBeanDefinition instances. Typically implemented by BeanFactories that
 * internally work with the AbstractBeanDefinition hierarchy.
 * 
 * <p> 持有bean定义的注册表接口,例如RootBeanDefinition 和 ChildBeanDefinition 实例,通常通过BeanFactories来实现,
 * 而BeanFactories内部的使用伴随着AbstractBeanDefinition的层次结构
 *
 * <p>This is the only interface in Spring's bean factory packages that encapsulates
 * <i>registration</i> of bean definitions. The standard BeanFactory interfaces
 * only cover access to a <i>fully configured factory instance</i>.
 * 
 * <p> 这是Spring的bean工厂包中唯一封装bean定义注册的接口。 标准BeanFactory接口仅涵盖对完全配置的工厂实例的访问。
 *
 * <p>Spring's bean definition readers expect to work on an implementation of this
 * interface. Known implementors within the Spring core are DefaultListableBeanFactory
 * and GenericApplicationContext.
 * 
 * <p> Spring的bean定义阅读器希望能够处理这个接口的实现。Spring核心中的已知实现者是DefaultListableBeanFactory和GenericApplicationContext。
 *
 * @author Juergen Hoeller
 * @since 26.11.2003
 * @see org.springframework.beans.factory.config.BeanDefinition
 * @see AbstractBeanDefinition
 * @see RootBeanDefinition
 * @see ChildBeanDefinition
 * @see DefaultListableBeanFactory
 * @see org.springframework.context.support.GenericApplicationContext
 * @see org.springframework.beans.factory.xml.XmlBeanDefinitionReader
 * @see PropertiesBeanDefinitionReader
 */
public interface BeanDefinitionRegistry extends AliasRegistry {

	/**
	 * Register a new bean definition with this registry.
	 * Must support RootBeanDefinition and ChildBeanDefinition.
	 * @param beanName the name of the bean instance to register
	 * @param beanDefinition definition of the bean instance to register
	 * @throws BeanDefinitionStoreException if the BeanDefinition is invalid
	 * @throws BeanDefinitionOverrideException if there is already a BeanDefinition
	 * for the specified bean name and we are not allowed to override it
	 * @see GenericBeanDefinition
	 * @see RootBeanDefinition
	 * @see ChildBeanDefinition
	 */
	void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
			throws BeanDefinitionStoreException;

	/**
	 * Remove the BeanDefinition for the given name.
	 * @param beanName the name of the bean instance to register
	 * @throws NoSuchBeanDefinitionException if there is no such bean definition
	 */
	void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;

	/**
	 * Return the BeanDefinition for the given bean name.
	 * 
	 * <p> 为给定bean名称返回 BeanDefinition
	 * 
	 * @param beanName name of the bean to find a definition for
	 * 
	 * <p> 要查找其定义的bean的名称
	 * 
	 * @return the BeanDefinition for the given name (never {@code null})
	 * 
	 * <p> 为给定名称的BeanDefinition(永不为null)
	 * 
	 * @throws NoSuchBeanDefinitionException if there is no such bean definition
	 * 
	 * <p> 如果没有这样的bean定义则抛出异常
	 */
	BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;

	/**
	 * Check if this registry contains a bean definition with the given name.
	 * 
	 * <p> 用给定的名称检测这个注册表是否包含一个bean定义
	 * 
	 * @param beanName the name of the bean to look for
	 * 
	 * <p> 要查找的bean的名称
	 * 
	 * @return if this registry contains a bean definition with the given name
	 * 
	 * <p> 用给定的名称检测这个注册表是否包含一个bean定义
	 */
	boolean containsBeanDefinition(String beanName);

	/**
	 * Return the names of all beans defined in this registry.
	 * 
	 * <p> 返回在这个注册表中的所有bean定义的名称
	 * 
	 * @return the names of all beans defined in this registry,
	 * or an empty array if none defined
	 * 
	 * <p> 返回在这个注册表中的所有bean定义的名称,如果没有bean定义则返回空数组
	 */
	String[] getBeanDefinitionNames();

	/**
	 * Return the number of beans defined in the registry.
	 * @return the number of beans defined in the registry
	 */
	int getBeanDefinitionCount();

	/**
	 * Determine whether the given bean name is already in use within this registry,
	 * i.e. whether there is a local bean or alias registered under this name.
	 * @param beanName the name to check
	 * @return whether the given bean name is already in use
	 */
	boolean isBeanNameInUse(String beanName);

}
