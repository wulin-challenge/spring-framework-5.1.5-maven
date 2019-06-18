/*
 * Copyright 2002-2019 the original author or authors.
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

package org.springframework.core.io.support;

import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;
import org.springframework.util.ResourceUtils;

/**
 * Utility class for determining whether a given URL is a resource
 * location that can be loaded via a {@link ResourcePatternResolver}.
 * 
 * <p> 实用的工具类,该类确定一个给定的url是否是一个资源位置,这个资源位置可以通过一个{@link ResourcePatternResolver}加载
 *
 * <p>Callers will usually assume that a location is a relative path
 * if the {@link #isUrl(String)} method returns {@code false}.
 * 
 * <p> 如果isUrl（String）方法返回false，则调用者通常会假定某个位置是相对路径。
 *
 * @author Juergen Hoeller
 * @since 1.2.3
 */
public abstract class ResourcePatternUtils {

	/**
	 * Return whether the given resource location is a URL: either a
	 * special "classpath" or "classpath*" pseudo URL or a standard URL.
	 * @param resourceLocation the location String to check
	 * @return whether the location qualifies as a URL
	 * @see ResourcePatternResolver#CLASSPATH_ALL_URL_PREFIX
	 * @see org.springframework.util.ResourceUtils#CLASSPATH_URL_PREFIX
	 * @see org.springframework.util.ResourceUtils#isUrl(String)
	 * @see java.net.URL
	 */
	public static boolean isUrl(@Nullable String resourceLocation) {
		return (resourceLocation != null &&
				(resourceLocation.startsWith(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX) ||
						ResourceUtils.isUrl(resourceLocation)));
	}

	/**
	 * Return a default {@link ResourcePatternResolver} for the given {@link ResourceLoader}.
	 * 
	 * <p> 为给定的{@link ResourceLoader}返回一个默认的 {@link ResourcePatternResolver}
	 * 
	 * <p>This might be the {@code ResourceLoader} itself, if it implements the
	 * {@code ResourcePatternResolver} extension, or a default
	 * {@link PathMatchingResourcePatternResolver} built on the given {@code ResourceLoader}.
	 * 
	 * <p> 这可能是ResourceLoader本身，如果它实现ResourcePatternResolver扩展，
	 * 或者是在给定ResourceLoader上构建的默认PathMatchingResourcePatternResolver。
	 * 
	 * @param resourceLoader the ResourceLoader to build a pattern resolver for
	 * (may be {@code null} to indicate a default ResourceLoader)
	 * 
	 * <p> 为ResourceLoader构建一个模式解析器(可以是null,将指明一个默认的ResourceLoader)
	 * 
	 * @return the ResourcePatternResolver - 返回一个 ResourcePatternResolver
	 * 
	 * @see PathMatchingResourcePatternResolver
	 */
	public static ResourcePatternResolver getResourcePatternResolver(@Nullable ResourceLoader resourceLoader) {
		if (resourceLoader instanceof ResourcePatternResolver) {
			return (ResourcePatternResolver) resourceLoader;
		}
		else if (resourceLoader != null) {
			return new PathMatchingResourcePatternResolver(resourceLoader);
		}
		else {
			return new PathMatchingResourcePatternResolver();
		}
	}

}
