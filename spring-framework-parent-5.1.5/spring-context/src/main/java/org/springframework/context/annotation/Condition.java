/*
 * Copyright 2002-2017 the original author or authors.
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

package org.springframework.context.annotation;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * A single {@code condition} that must be {@linkplain #matches matched} in order
 * for a component to be registered.
 * 
 * <p> 必须匹配的单个条件才能注册组件。
 *
 * <p>Conditions are checked immediately before the bean-definition is due to be
 * registered and are free to veto registration based on any criteria that can
 * be determined at that point.
 * 
 * <p> 在bean定义被注册之前立即检查条件，并且基于就在那时可以确定的任何标准自由否决注册
 *
 * <p>Conditions must follow the same restrictions as {@link BeanFactoryPostProcessor}
 * and take care to never interact with bean instances. For more fine-grained control
 * of conditions that interact with {@code @Configuration} beans consider the
 * {@link ConfigurationCondition} interface.
 * 
 * <p> 条件必须遵循与BeanFactoryPostProcessor相同的限制，并注意永远不要与bean实例交互。 
 * 对于更细粒度控制与{@code @Configuration}交换的条件,请考虑configurationCondition接口。
 *
 * @author Phillip Webb
 * @since 4.0
 * @see ConfigurationCondition
 * @see Conditional
 * @see ConditionContext
 */
@FunctionalInterface
public interface Condition {

	/**
	 * Determine if the condition matches.
	 * 
	 * <p> 确定是否条件匹配
	 * 
	 * @param context the condition context - 条件上下文
	 * 
	 * @param metadata metadata of the {@link org.springframework.core.type.AnnotationMetadata class}
	 * or {@link org.springframework.core.type.MethodMetadata method} being checked
	 * 
	 * <p> 将要检测的类或者方法的元数据
	 * 
	 * @return {@code true} if the condition matches and the component can be registered,
	 * or {@code false} to veto the annotated component's registration
	 * 
	 * <p> 如果条件匹配并且组件可以被注册,则返回true,否则返回false,否决注解组件的注册
	 */
	boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata);

}
