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

package org.springframework.context.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a component is only eligible for registration when all
 * {@linkplain #value specified conditions} match.
 * 
 * <p> 当所有指定的条件匹配时,表明一个组件才有资格注册
 *
 * <p>A <em>condition</em> is any state that can be determined programmatically
 * before the bean definition is due to be registered (see {@link Condition} for details).
 * 
 * <p> 一个条件在bean定义被注册之前,可以通过编程确定是任何状态(更详细的请见{@link Condition})
 *
 * <p>The {@code @Conditional} annotation may be used in any of the following ways:
 * 
 * <p> {@code @Conditional}注解可以用下列任何方式使用
 * 
 * <ul>
 * <li>as a type-level annotation on any class directly or indirectly annotated with
 * {@code @Component}, including {@link Configuration @Configuration} classes</li>
 * 
 * <li>作为一个类型级的注解,在任何类上用{@code @Component}(包括{@link Configuration @Configuration}类)
 * 直接地或者间接地注解</li> 
 * 
 * <li>as a meta-annotation, for the purpose of composing custom stereotype
 * annotations</li>
 * 
 * <li>作为元注释，用于组成自定义构造类型注释</li>
 * 
 * <li>as a method-level annotation on any {@link Bean @Bean} method</li>
 * 
 * <li>在任何{@link Bean @Bean}方法上作为一个方法级注解</li>
 * </ul>
 *
 * <p>If a {@code @Configuration} class is marked with {@code @Conditional},
 * all of the {@code @Bean} methods, {@link Import @Import} annotations, and
 * {@link ComponentScan @ComponentScan} annotations associated with that
 * class will be subject to the conditions.
 * 
 * <p> 如果使用@Conditional标记@Configuration类，则与该类关联的所有@Bean方法，
 * @ Import注释和@ComponentScan注释都将受条件限制。
 *
 * <p><strong>NOTE</strong>: Inheritance of {@code @Conditional} annotations
 * is not supported; any conditions from superclasses or from overridden
 * methods will not be considered. In order to enforce these semantics,
 * {@code @Conditional} itself is not declared as
 * {@link java.lang.annotation.Inherited @Inherited}; furthermore, any
 * custom <em>composed annotation</em> that is meta-annotated with
 * {@code @Conditional} must not be declared as {@code @Inherited}.
 * 
 * <p> 注意：不支持继承@Conditional注释; 来自超类或来自重写方法的任何条件都不会被考虑。 为了强制执行这些语义，
 * @ Conditional本身不会声明为@Inherited; 此外，任何使用@Conditional元注释的自定义组合注释都不能声明为@Inherited。
 *
 * @author Phillip Webb
 * @author Sam Brannen
 * @since 4.0
 * @see Condition
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Conditional {

	/**
	 * All {@link Condition Conditions} that must {@linkplain Condition#matches match}
	 * in order for the component to be registered.
	 * 
	 * <p> 必须匹配所有条件才能注册组件。
	 * 
	 */
	Class<? extends Condition>[] value();

}
