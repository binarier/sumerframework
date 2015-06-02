# 新建一个项目 #

在开始之前，开发机需要[安装Ant和Ivy](Preview_AntIvy.md)，并且将Ant的执行目录放到Path中，以便在命令行中可以使用ANT命令。这里假定各位读者拥有Ivy和Ant的基础知识。

## 项目的建立 ##

建立项目目录，然后在目录中放入唯一的安装文件[setup.xml](http://sumerframework.googlecode.com/svn/trunk/platform/setup.xml)，然后执行`ant -f setup.xml`，如下图所示

![http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/001.png](http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/001.png)

这个由Ant脚本编写的安装程序需要收集一组Ivy相关信息：
  * 组织名，新项目在Ivy中对应的org
  * 模块名，新项目在Ivy中对应的module
  * 初始版本，默认值为0.1.0，按版本规范的最初版本
  * 本地Ivy库，指定一个本地目录以发布项目模块，如果不需要发布，则不用管这个选项。

出于演示目的，这里都取默认值，即建了一个模块，相当于Ivy依赖项：
```
<dependency org="com.huateng.myproject" name="com.huateng.myproject.module" rev="0.1.0"/>
```

接下来安装程序继续，如下图所示：

![http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/002.png](http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/002.png)

setup.xml会建立临时的ivy控制文件，以便从sumer的ivy库里取出最新版本的platform模版包，解压形成基础的项目，并且对一些基础设置，如项目名等信息按收集的项目信息进行更改。

## 首次运行Shell ##

项目建立完成后，在项目主目录直接输入ant，就可以进入SumerShell的提示符。如果是第一次运行，Ivy会按初始配置下载运行所需要的依赖项，可能要花点时间。
目前的SumerShell还只是个框架，但它是可扩展的结构，每次运行时，会按Ivy的配置，查找最新版本进行更新。可以通过Help命令来查看当前支持哪些功能。
![http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/003.png](http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/003.png)

## 在Eclipse中导入项目 ##

与Spring ROO不同，Sumer新建的项目是完全按Eclipse方式配置的，所以不需要利用eclipse:eclipse之类的命令来初始化设置，可以直接导入Eclipse。
为了避免潜在的问题，这里建议用户使用SpringSource Tool Suite (STS) 来进行开发。

### 新建一个Workspace ###

![http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/004.png](http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/004.png)

如果用户已经有Workspace，则可以直接在其中导入项目，但要注意Workspace的编码，Sumer项目的源码和文档的开发都是基于UTF-8的，在默认编码（中文Windows为GBK）下可能有未知的麻烦。
这里建议，如果可能的话，新建一个独立的workspace来开发Sumer项目，并且在Preferences->General->Workspace里将workspace的编码设置为UTF-8，如下图所示：

![http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/005.png](http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/005.png)

### Import项目 ###

选择File->Import

![http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/006.png](http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/006.png)

选择General -> Existing Project into Workspace

![http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/007.png](http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/007.png)

选定刚才建立的项目目录，点击Finish

![http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/008.png](http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/008.png)

至此，一个基础的项目已经建立完成。新建项目中的一个红色图标是因为ibator-config.xml是个空文件，违反了schema校验，这在生成数据库包装后就不会出现了。

![http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/009.png](http://sumerframework.googlecode.com/svn/trunk/doc/docbook/preview/images/009.png)