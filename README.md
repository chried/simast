# simast
simast意在解决项目中重新写一个基本的操作，将一些共性属性以及操作、方法等提取出来，组成一个快速包，让我们的业务直接根据需求，引入特定的包，从而实现快速开发。<br>
框架基于jpa，因此引入本框架，项目一定是使用jpa作为持久层。后续继续完善其他持久化框架。

# 项目结构

|模块名称 | 说明|
|--------- | -------- |
|simast-base | 定义基本属性|
|simast-bom | 第三方库版本控制|
|simast-core | 一些核心操作|
|simast-dao | dao层封装，自定义dao层依赖|
|simast-service | service层封装，自定义service依赖|
|simast-utils | 定义一些常用工具类|

**说明**：如果业务项目只是单项目，没有采用maven分模块，那么直接引入simast-service即可，如果采用maven分模块，那么就按照命名依次引入。

# 模块详细说明

## simast-base 
定义基本属性，业务定义entity模块引入此包。
## simast-bom
第三方版本控制，为了解决分模块下引入第三方架包版本不一致，因此引入的第三方架包，版本必须放在这里，在业务代码引入如下：<br>

 <!--引入版本控制-->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.simast</groupId>
                <artifactId>simast-bom</artifactId>
                <version>1.0-SNAPSHOT</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
        </dependency>
    </dependencies>

## simast-core
核心包，主要定义一些操作，例如全局处理异常，定义AOP处理，定义一些注解的操作等。

## simast-dao
主要封装dao一些特定操作，业务dao模块引入此包。

## simast-service
主要封装service一些特定操作，业务service模块引入此包。

## simast-utils
主要封装一些常用的工具类。 
