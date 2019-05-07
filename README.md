# spring-framework-5.1.5-maven
spring framework 5.1.5的maven版本

[spring framework官方源码地址](https://github.com/spring-projects/spring-framework),此处我取的release中的5.1.5版本

## 如何将spring的gradle的源码转为eclipse的普通工程?
请见文章: [记一次spring5源码完整编译过程](https://blog.csdn.net/coder_no/article/details/83315981)  
将安装eclipse的groovy插件一定要查看自己的eclipse版本与之对应才行,请前文章  
[eclipse-4.4.2安装Groovy插件(其他版本eclipse可参考)](http://www.bubuko.com/infodetail-1350882.html)  
[groovy的eclipse插件版对应查看地址](https://github.com/groovy/groovy-eclipse/wiki)

## 如果将eclipse的普通工程转为maven工程?
我的解决办法是自己创建spring framework的maven版本的工程,然后通过...来帮助解决maven依赖问题
