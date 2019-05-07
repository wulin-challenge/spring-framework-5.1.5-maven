# spring-framework-5.1.5-maven
spring framework 5.1.5的maven版本

[spring framework官方源码地址](https://github.com/spring-projects/spring-framework),此处我取的release中的5.1.5版本

## 顶层目录说明
1. normal-to-maven 将普通eclipse工程的依赖jar装maven的依赖
2.spring-framework-parent-5.1.5 这是spring framework的5.1.5版本的源码

## 如何将spring的gradle的源码转为eclipse的普通工程?
请见文章: [记一次spring5源码完整编译过程](https://blog.csdn.net/coder_no/article/details/83315981)  
将安装eclipse的groovy插件一定要查看自己的eclipse版本与之对应才行,请前文章  
[eclipse-4.4.2安装Groovy插件(其他版本eclipse可参考)](http://www.bubuko.com/infodetail-1350882.html)  
[groovy的eclipse插件版对应查看地址](https://github.com/groovy/groovy-eclipse/wiki)

## 如果将eclipse的普通工程转为maven工程?
我的解决办法是自己创建spring framework的maven版本的工程,然后通过...来帮助解决maven依赖问题

## 如何解决spring-beans中GroovyBeanDefinitionReader报错的问题
GroovyBeanDefinitionReader报错是因为需要引用GroovyDynamicElementReader,而GroovyDynamicElementReader需要groovy的GROOVY_DSL_SUPPORT的支持
解决步骤是,此处有一个前提是你已经在eclipse正取安装groovy的插件  
1. 在自己创建的spring-beans的所在目录找到.classpath,并打开,加入如下配置
```
<classpathentry exported="true" kind="con" path="GROOVY_DSL_SUPPORT">
		<attributes>
			<attribute name="maven.pomderived" value="true"/>
		</attributes>
</classpathentry>
```
2. 在自己创建的spring-beans的所在目录找到.project,并打开,加入如下配置
```
<natures>
		<nature>org.eclipse.jdt.core.javanature</nature>
		<nature>org.eclipse.m2e.core.maven2Nature</nature>
		<nature>org.eclipse.jdt.groovy.core.groovyNature</nature>
</natures>
```
3. 最后在eclipse中clean一下spring-beans即可


