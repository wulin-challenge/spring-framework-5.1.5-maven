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

package org.springframework.context.event;

import java.util.concurrent.Executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;
import org.springframework.util.ErrorHandler;

/**
 * Simple implementation of the {@link ApplicationEventMulticaster} interface.
 * 
 * <p> {@link ApplicationEventMulticaster}的简单实现
 *
 * <p>Multicasts all events to all registered listeners, leaving it up to
 * the listeners to ignore events that they are not interested in.
 * Listeners will usually perform corresponding {@code instanceof}
 * checks on the passed-in event object.
 * 
 * <p> 将所有事件多播到所有已注册的侦听器，将其留给侦听器以忽略他们不感兴趣的事件。侦听器通常会对传入的事件对象执行相应的 instanceof 检查。
 *
 * <p>By default, all listeners are invoked in the calling thread.
 * This allows the danger of a rogue listener blocking the entire application,
 * but adds minimal overhead. Specify an alternative task executor to have
 * listeners executed in different threads, for example from a thread pool.
 * 
 * <p> 默认情况下，在调用线程中调用所有侦听器。 这允许恶意侦听器阻塞整个应用程序的危险，但增加了最小的开销。 
 *  指定另一个任务执行者以便侦听器在不同的线程中执行，例如从线程池中执行。
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Stephane Nicoll
 * @see #setTaskExecutor
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

	@Nullable
	private Executor taskExecutor;

	@Nullable
	private ErrorHandler errorHandler;


	/**
	 * Create a new SimpleApplicationEventMulticaster.
	 * 
	 * <p> 创建一个新的SimpleApplicationEventMulticaster
	 */
	public SimpleApplicationEventMulticaster() {
	}

	/**
	 * Create a new SimpleApplicationEventMulticaster for the given BeanFactory.
	 * 
	 * <p> 为给定的BeanFactory创建一个新的 SimpleApplicationEventMulticaster
	 */
	public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
		setBeanFactory(beanFactory);
	}


	/**
	 * Set a custom executor (typically a {@link org.springframework.core.task.TaskExecutor})
	 * to invoke each listener with.
	 * <p>Default is equivalent to {@link org.springframework.core.task.SyncTaskExecutor},
	 * executing all listeners synchronously in the calling thread.
	 * <p>Consider specifying an asynchronous task executor here to not block the
	 * caller until all listeners have been executed. However, note that asynchronous
	 * execution will not participate in the caller's thread context (class loader,
	 * transaction association) unless the TaskExecutor explicitly supports this.
	 * @see org.springframework.core.task.SyncTaskExecutor
	 * @see org.springframework.core.task.SimpleAsyncTaskExecutor
	 */
	public void setTaskExecutor(@Nullable Executor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	/**
	 * Return the current task executor for this multicaster.
	 */
	@Nullable
	protected Executor getTaskExecutor() {
		return this.taskExecutor;
	}

	/**
	 * Set the {@link ErrorHandler} to invoke in case an exception is thrown
	 * from a listener.
	 * <p>Default is none, with a listener exception stopping the current
	 * multicast and getting propagated to the publisher of the current event.
	 * If a {@linkplain #setTaskExecutor task executor} is specified, each
	 * individual listener exception will get propagated to the executor but
	 * won't necessarily stop execution of other listeners.
	 * <p>Consider setting an {@link ErrorHandler} implementation that catches
	 * and logs exceptions (a la
	 * {@link org.springframework.scheduling.support.TaskUtils#LOG_AND_SUPPRESS_ERROR_HANDLER})
	 * or an implementation that logs exceptions while nevertheless propagating them
	 * (e.g. {@link org.springframework.scheduling.support.TaskUtils#LOG_AND_PROPAGATE_ERROR_HANDLER}).
	 * @since 4.1
	 */
	public void setErrorHandler(@Nullable ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	/**
	 * Return the current error handler for this multicaster.
	 * 
	 * <p> 为这个多播器返回当前错误处理器
	 * @since 4.1
	 */
	@Nullable
	protected ErrorHandler getErrorHandler() {
		return this.errorHandler;
	}


	@Override
	public void multicastEvent(ApplicationEvent event) {
		multicastEvent(event, resolveDefaultEventType(event));
	}

	@Override
	public void multicastEvent(final ApplicationEvent event, @Nullable ResolvableType eventType) {
		ResolvableType type = (eventType != null ? eventType : resolveDefaultEventType(event));
		for (final ApplicationListener<?> listener : getApplicationListeners(event, type)) {
			Executor executor = getTaskExecutor();
			if (executor != null) {
				executor.execute(() -> invokeListener(listener, event));
			}
			else {
				invokeListener(listener, event);
			}
		}
	}

	private ResolvableType resolveDefaultEventType(ApplicationEvent event) {
		return ResolvableType.forInstance(event);
	}

	/**
	 * Invoke the given listener with the given event.
	 * 
	 * <p> 用给定事件调用给定监听器
	 * 
	 * @param listener the ApplicationListener to invoke
	 * 
	 * <p> 要调用的 ApplecationListener
	 * 
	 * @param event the current event to propagate
	 * 
	 * <p> 要传播的当前事件
	 * 
	 * @since 4.1
	 */
	protected void invokeListener(ApplicationListener<?> listener, ApplicationEvent event) {
		ErrorHandler errorHandler = getErrorHandler();
		if (errorHandler != null) {
			try {
				doInvokeListener(listener, event);
			}
			catch (Throwable err) {
				errorHandler.handleError(err);
			}
		}
		else {
			doInvokeListener(listener, event);
		}
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private void doInvokeListener(ApplicationListener listener, ApplicationEvent event) {
		try {
			listener.onApplicationEvent(event);
		}
		catch (ClassCastException ex) {
			String msg = ex.getMessage();
			if (msg == null || matchesClassCastMessage(msg, event.getClass())) {
				// Possibly a lambda-defined listener which we could not resolve the generic event type for
				// -> let's suppress the exception and just log a debug message.
				Log logger = LogFactory.getLog(getClass());
				if (logger.isDebugEnabled()) {
					logger.debug("Non-matching event type for listener: " + listener, ex);
				}
			}
			else {
				throw ex;
			}
		}
	}

	private boolean matchesClassCastMessage(String classCastMessage, Class<?> eventClass) {
		// On Java 8, the message starts with the class name: "java.lang.String cannot be cast..."
		if (classCastMessage.startsWith(eventClass.getName())) {
			return true;
		}
		// On Java 11, the message starts with "class ..." a.k.a. Class.toString()
		if (classCastMessage.startsWith(eventClass.toString())) {
			return true;
		}
		// On Java 9, the message used to contain the module name: "java.base/java.lang.String cannot be cast..."
		int moduleSeparatorIndex = classCastMessage.indexOf('/');
		if (moduleSeparatorIndex != -1 && classCastMessage.startsWith(eventClass.getName(), moduleSeparatorIndex + 1)) {
			return true;
		}
		// Assuming an unrelated class cast failure...
		return false;
	}

}
