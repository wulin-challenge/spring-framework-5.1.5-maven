/*
 * Copyright 2002-2014 the original author or authors.
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

package org.springframework.beans.factory.annotation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.lang.Nullable;

/**
 * Extended {@link org.springframework.beans.factory.config.BeanDefinition}
 * interface that exposes {@link org.springframework.core.type.AnnotationMetadata}
 * about its bean class - without requiring the class to be loaded yet.
 * 
 * <p> 扩展org.springframework.beans.factory.config.BeanDefinition接口，公开关于其bean类的
 * org.springframework.core.type.AnnotationMetadata  - 无需加载类。
 *
 * @author Juergen Hoeller
 * @since 2.5
 * @see AnnotatedGenericBeanDefinition
 * @see org.springframework.core.type.AnnotationMetadata
 */
public interface AnnotatedBeanDefinition extends BeanDefinition {

	/**
	 * Obtain the annotation metadata (as well as basic class metadata)
	 * for this bean definition's bean class.
	 * 
	 * <p> 获取注解元数据(已经基于类的元数据)为了这个bean定义的beanclass
	 * 
	 * @return the annotation metadata object (never {@code null})
	 * 
	 * <p> 注解元数据对象(永不为null)
	 */
	AnnotationMetadata getMetadata();

	/**
	 * Obtain metadata for this bean definition's factory method, if any.
	 * 
	 * <p> 为这个bean定义的工厂方法获取元数据,如果有
	 * 
	 * @return the factory method metadata, or {@code null} if none
	 * 
	 * <p> 工厂的元数据,如果没有则返回null
	 * @since 4.1.1
	 */
	@Nullable
	MethodMetadata getFactoryMethodMetadata();

}
