# 技术预览 #

在完整的手册编写完成之前，各位读者可以通过本文了解到大体的Sumer项目的工作方式，并且可以及时实际动手试一下。同时，目前sumer建立的平台项目已经可以作为实际的项目的启动，目前已经可以作为轻量级JavaEE项目的初始导入了。

用户可以通过一个[setup.xml](http://sumerframework.googlecode.com/svn/trunk/platform/setup.xml)文件来开始[新项目的建立过程](PreviewNewProject.md)。

当用户完成了setup.xml所包含的安装步骤后，将得到一个基于eclipse的标准web项目，包括：

  * 使用Ivy进行项目依赖项管理，并且项目配置中已经设置好IvyDE在Eclipse中的相关设置。
  * 集成Spring框架，所有初始的Spring容器的配置（包括事务和AOP）以及Spring MVC相关的配置（包括Tiles2等）已经配置完成。
  * 集成Spring Security做为安全框架，配置好其相关的Filter等组件。
  * 集成Spring Webflow相关的配置，可以直接编写Spring Webflow流程。
  * Apache Ibator的集成
  * 基本的出错处理界面
  * dojo库的集成
  * 用于演示目的的Dashboard及样本Webflow
  * Sumer UI体系组件

在eclipse中可以直接放入服务器运行。

![http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/010.png](http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/010.png)

服务器启动后，就可以通过浏览器直接访问这一个基于Spring容器的轻量级JavaEE项目了。

![http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/011.png](http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/011.png)

点击进入Dashboard后的界面，这是个基于标准Spring MVC做的页面，以及使用了DOJO的效果组件。

![http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/012.png](http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/012.png)

点击“快速开始”图标，进入演示流程，这是用Spring Webflow技术开发的页面流程，并且使用Sumer WebUI来进行界面的配置而成的。

![http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/013.png](http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/013.png)

![http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/014.png](http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/014.png)