# tomcat7配置session共享踩坑实录
> 百度的结果可以信，但不可全信。百度结果的特点：别指望用一篇博客内容就解决你所有问题，因为你也不知道
这篇博客是不是抄袭别人的23333333.所以人生苦短，我看官档。
官方文档和issue才是解决问题的硬道理。
> 
> PS:写博客要按照基本法啊，实践才是检验真理的唯一标准。

那么，就直入主题了，本文就对tomcat7整合memcached会话共享做一次总结。

## 原料

首先需要准备一份全新未开封的tomcat，这里用到的版本是apache-tomcat-7.0.90。

然后我们需要获取会话共享的配置jar包，这里就使用最简的配置，如果你想避免踩坑，那么建议直接用我这里提供的版本。
下载的话直接去maven中央仓库搜坐标，搜索结果里直接download jar包即可。

依赖的jar包列表如下：

    javolution-5.5.1.jar                        
    memcached-session-manager-1.8.2.jar
    memcached-session-manager-tc7-1.8.2.jar
    msm-javolution-serializer-1.8.2.jar
    spymemcached-2.10.2.jar

然后就可以开整了。

当然，你需要有一份memcached集群，两台就够了。这个不属于本文的讲解范围，请自行搭建:)。

## 实战
### 1. 解压tomcat

将全新未拆封的tomcat解压缩即可，没错，就是这么简单，解压命令

    tar -zxvf apache-tomcat-7.0.90.zip

### 2. 放置jar包

将上述的几个jar包下载下来并直接放置到${catalina-home}/lib下，也就是解压后的tomcat的lib目录下。

### 3. 修改配置文件

如果没有业务上的配置，那么这里就是最后一步了。将下面这段配置放到context.xml中。这里我们使用的是非粘滞会话。

    <Manager       
        className="de.javakaffee.web.msm.MemcachedBackupSessionManager" 
        memcachedNodes="n1:127.0.0.1:11211,n2:127.0.0.1:11212" 
        sticky="false" 
        sessionBackupAsync="false" 
        requestUriIgnorePattern=".*\.(ico|png|gif|jpg|jpeg|bmp|css|js|html|htm)$" 
        transcoderFactoryClass="de.javakaffee.web.msm.JavaSerializationTranscoderFactory" 
      />

非粘滞会话(sticky="false")下，不需要配置failoverNodes。

### 4. 启动应用服务

在这一步，如果你有业务性配置，请务必保证配置准确，然后就可以正常启动tomcat容器，如果看到如下的日志，那么
恭喜你，配置已经生效了。

    Sep 04, 2018 11:41:16 AM org.apache.catalina.startup.HostConfig deployDirectory
    INFO: Deployment of web application directory /app/stock/apache-tomcat-7.0.90-gh/webapps/examples has finished in 292 ms
    Sep 04, 2018 11:41:16 AM org.apache.catalina.startup.HostConfig deployDirectory
    INFO: Deploying web application directory /app/stock/apache-tomcat-7.0.90-gh/webapps/docs
    Sep 04, 2018 11:41:17 AM org.apache.catalina.startup.TldConfig execute
    INFO: At least one JAR was scanned for TLDs yet contained no TLDs. Enable debug logging for this logger for a complete list 
    of JARs that were scanned but no TLDs were found in them. Skipping unneeded JARs during scanning can improve startup time and JSP compilation time.
    Sep 04, 2018 11:41:17 AM de.javakaffee.web.msm.MemcachedSessionService startInternal
    INFO:  starts initialization... (configured nodes definition n1:10.192.8.19:41211,n2:10.2.32.34:41211, failover nodes null)
    2018-09-04 11:41:17.199 INFO net.spy.memcached.MemcachedConnection:  Added {QA sa=/10.192.8.19:41211, #Rops=0, #Wops=0, #iq=0, 
    topRop=null, topWop=null, toWrite=0, interested=0} to connect queue
    2018-09-04 11:41:17.199 INFO net.spy.memcached.MemcachedConnection:  Added {QA sa=/10.2.32.34:41211, #Rops=0, #Wops=0, #iq=0, 
    topRop=null, topWop=null, toWrite=0, interested=0} to connect queue
    2018-09-04 11:41:17.200 INFO net.spy.memcached.MemcachedConnection:  
    Connection state changed for sun.nio.ch.SelectionKeyImpl@76b9a2ef
    2018-09-04 11:41:17.200 INFO net.spy.memcached.MemcachedConnection:  
    Connection state changed for sun.nio.ch.SelectionKeyImpl@5f404de3
    Sep 04, 2018 11:41:17 AM de.javakaffee.web.msm.RequestTrackingHostValve <init>
    INFO: Setting ignorePattern to .*\.(png|gif|jpg|css|js|ico|jpeg|htm|html)$
    Sep 04, 2018 11:41:17 AM de.javakaffee.web.msm.MemcachedSessionService setLockingMode
    INFO: Setting lockingMode to NONE
    Sep 04, 2018 11:41:17 AM de.javakaffee.web.msm.MemcachedSessionService createTranscoderFactory
    INFO: Creating transcoder factory de.javakaffee.web.msm.JavaSerializationTranscoderFactory
    Sep 04, 2018 11:41:17 AM de.javakaffee.web.msm.MemcachedSessionService startInternal
    INFO: --------
    -  finished initialization:
    - sticky: false
    - operation timeout: 1000
    - node ids: [n1, n2]
    - failover node ids: []
    - storage key prefix: null
    --------
    Sep 04, 2018 11:41:17 AM org.apache.catalina.startup.HostConfig deployDirectory
    INFO: Deployment of web application directory /app/stock/apache-tomcat-7.0.90-gh/webapps/docs has finished in 295 ms
    Sep 04, 2018 11:41:17 AM org.apache.coyote.AbstractProtocol start
    INFO: Starting ProtocolHandler ["http-bio-18080"]
    Sep 04, 2018 11:41:17 AM org.apache.coyote.AbstractProtocol start
    INFO: Starting ProtocolHandler ["ajp-bio-8009"]
    Sep 04, 2018 11:41:17 AM org.apache.catalina.startup.Catalina start
    INFO: Server startup in 33928 ms


现在需要做的就是把这个配置在其余的tomcat上配置即可。

## 关于序列化
其实官方提供了很多中序列化方式，较为复杂的配置方式为kryo，如果你要使用这个方式，请保证添加了对应的依赖。

    •	kryo-serializer：
            msm-kryo-serializer，
            kryo-serializers-0.34 +，
            kryo-3.x，minlog，
            reflectasm，
            asm-5.x，
            objenesis-2.x

依赖不少吧，如果你对性能没有强烈的要求，那么使用默认的JDK序列化就好。

这里我罗列一下常见的序列化方式，我们采用的就是其中的Java序列化。

    •	Java序列化： de.javakaffee.web.msm.JavaSerializationTranscoderFactory
    •	基于Kryo的序列化：de.javakaffee.web.msm.serializer.kryo.KryoTranscoderFactory
    •	基于Javolution的序列化： de.javakaffee.web.msm.serializer.javolution.JavolutionTranscoderFactory
    •	基于XStream的序列化： de.javakaffee.web.msm.serializer.xstream.XStreamTranscoderFactory


## 参考链接
根据这篇文章你应当能够搭建一个可用的memcached会话共享集群，但是更多的深入的配置就不得不去看官方文档了，这里
我附上官方链接及我参考过的觉得还可以的几篇博客链接（每篇都有坑，也都有借鉴之处）。

https://github.com/magro/memcached-session-manager/wiki/SetupAndConfiguration

https://www.cnblogs.com/h--d/p/6729172.html

jar包版本：

https://www.cnblogs.com/linuxboke/p/5497774.html



对于这些博客链接请自行识别坑在何处，当然如果你按照我文章的jar版本去配置的话，相信是不会踩坑的，如果有坑的话，
请务必保证你的版本和环境（centos7,jdk1.7）和我的一致再决定是否顺着网线来砍我哦（括弧笑）。

