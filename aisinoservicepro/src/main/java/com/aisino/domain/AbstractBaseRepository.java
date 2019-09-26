package com.aisino.domain;

/**
 * Created by Martin.Ou on 2014/9/3.
 */

import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;

/**<?xml version="1.0" encoding="UTF-8"?>
 <project xmlns="http://maven.apache.org/POM/4.0.0"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
 <modelVersion>4.0.0</modelVersion>

 <groupId>com.aisino</groupId>
 <artifactId>zzs_CLProj</artifactId>
 <version>1.0-SNAPSHOT</version>


 <build>
 <plugins>
 <plugin>
 <groupId>org.apache.maven.plugins</groupId>
 <artifactId>maven-compiler-plugin</artifactId>
 <version>3.3</version>
 <configuration>
 <encoding>UTF-8</encoding>
 </configuration>
 </plugin>
 <plugin>
 <groupId>org.apache.tomcat.maven</groupId>
 <artifactId>tomcat7-maven-plugin</artifactId>
 <version>2.1</version>
 <configuration>
 <path>/zzscl</path>
 <charset>UTF-8</charset>
 <port>8083</port>
 <uriEncoding>UTF-8</uriEncoding>
 <server>tomcat7</server>
 </configuration>
 </plugin>
 </plugins>
 </build>

 <properties>
 <spring.version>3.2.10.RELEASE</spring.version>
 <spring.rabbit.version>1.3.5.RELEASE</spring.rabbit.version>
 <maven.compiler.source>1.6</maven.compiler.source>
 <maven.compiler.target>1.6</maven.compiler.target>
 <maven.compiler.compilerVersion>1.6</maven.compiler.compilerVersion>
 </properties>
 <dependencies>
 <!--<dependency>-->
 <!--<groupId>com.aisino</groupId>-->
 <!--<artifactId>51AISINOServicePro</artifactId>-->
 <!--<version>0.0.1-SNAPSHOT</version>-->
 <!--</dependency>-->
 <dependency>
 <groupId>com.aisino</groupId>
 <artifactId>51AISINOServicePro</artifactId>
 <version>0.0.1-SNAPSHOT</version>
 </dependency>
 <dependency>
 <groupId>com.aisino</groupId>
 <artifactId>zzs_CLProtocolUtil</artifactId>
 <version>0.0.1-SNAPSHOT</version>
 </dependency>
 <dependency>
 <groupId>com.aisino</groupId>
 <artifactId>aisino080303.jar</artifactId>
 <version>080303</version>
 </dependency>
 <dependency>
 <groupId>com.aisino</groupId>
 <artifactId>AisinoUtils20120918</artifactId>
 <version>1.0.0</version>
 </dependency>
 <dependency>
 <groupId>aopalliance</groupId>
 <artifactId>aopalliance</artifactId>
 <version>1.0</version>
 </dependency>
 <dependency>
 <groupId>org.aspectj</groupId>
 <artifactId>aspectjweaver</artifactId>
 <version>1.8.13</version>
 </dependency>
 <dependency>
 <groupId>commons-beanutils</groupId>
 <artifactId>commons-beanutils</artifactId>
 <version>1.7.0</version>
 </dependency>
 <dependency>
 <groupId>commons-codec</groupId>
 <artifactId>commons-codec</artifactId>
 <version>1.7</version>
 </dependency>
 <dependency>
 <groupId>commons-fileupload</groupId>
 <artifactId>commons-fileupload</artifactId>
 <version>1.2</version>
 </dependency>
 <dependency>
 <groupId>commons-httpclient</groupId>
 <artifactId>commons-httpclient</artifactId>
 <version>3.0</version>
 </dependency>
 <dependency>
 <groupId>commons-io</groupId>
 <artifactId>commons-io</artifactId>
 <version>1.3.2</version>
 </dependency>
 <dependency>
 <groupId>commons-lang</groupId>
 <artifactId>commons-lang</artifactId>
 <version>2.6</version>
 </dependency>
 <dependency>
 <groupId>commons-logging</groupId>
 <artifactId>commons-logging</artifactId>
 <version>1.1.3</version>
 </dependency>
 <dependency>
 <groupId>commons-net</groupId>
 <artifactId>commons-net</artifactId>
 <version>2.0</version>
 </dependency>
 <dependency>
 <groupId>commons-net</groupId>
 <artifactId>commons-net-ftp</artifactId>
 <version>2.0</version>
 </dependency>
 <dependency>
 <groupId>org.apache.curator</groupId>
 <artifactId>curator-client</artifactId>
 <version>2.6.0</version>
 </dependency>
 <dependency>
 <groupId>org.apache.curator</groupId>
 <artifactId>curator-framework</artifactId>
 <version>2.6.0</version>
 </dependency>
 <dependency>
 <groupId>org.apache.curator</groupId>
 <artifactId>curator-recipes</artifactId>
 <version>2.6.0</version>
 </dependency>
 <dependency>
 <groupId>org.apache.cxf</groupId>
 <artifactId>cxf</artifactId>
 <version>2.2.4</version>
 </dependency>
 <dependency>
 <groupId>dom4j</groupId>
 <artifactId>dom4j</artifactId>
 <version>1.6.1</version>
 </dependency>
 <dependency>
 <groupId>com.alibaba</groupId>
 <artifactId>druid</artifactId>
 <version>1.0.8</version>
 </dependency>
 <dependency>
 <groupId>com.aisino</groupId>
 <artifactId>EIECPdf</artifactId>
 <version>1.3</version>
 </dependency>
 <dependency>
 <groupId>com.aisino.einvvat</groupId>
 <artifactId>einvvat.signPdf.client</artifactId>
 <version>1.0.8</version>
 </dependency>
 <dependency>
 <groupId>com.google.code.gson</groupId>
 <artifactId>gson</artifactId>
 <version>2.3</version>
 </dependency>
 <dependency>
 <groupId>com.google.guava</groupId>
 <artifactId>guava</artifactId>
 <version>18.0</version>
 </dependency>
 <dependency>
 <groupId>org.apache.httpcomponents</groupId>
 <artifactId>httpclient</artifactId>
 <version>4.3.5</version>
 </dependency>
 <dependency>
 <groupId>org.apache.httpcomponents</groupId>
 <artifactId>httpcore</artifactId>
 <version>4.3.2</version>
 </dependency>
 <dependency>
 <groupId>com.lowagie</groupId>
 <artifactId>itext</artifactId>
 <version>2.1.4</version>
 </dependency>

 <dependency>
 <groupId>cn.lesper</groupId>
 <artifactId>iTextAsian</artifactId>
 <version>1.0</version>
 </dependency>

 <dependency>
 <groupId>org.codehaus.jackson</groupId>
 <artifactId>jackson-core-asl</artifactId>
 <version>1.9.13</version>
 </dependency>
 <dependency>
 <groupId>org.codehaus.jackson</groupId>
 <artifactId>jackson-mapper-asl</artifactId>
 <version>1.9.13</version>
 </dependency>
 <dependency>
 <groupId>com.sheca</groupId>
 <artifactId>javasafeengine</artifactId>
 <version>1.4</version>
 </dependency>
 <dependency>
 <groupId>javax.xml.bind</groupId>
 <artifactId>jaxb-api</artifactId>
 <version>2.1</version>
 </dependency>
 <dependency>
 <groupId>joda-time</groupId>
 <artifactId>joda-time</artifactId>
 <version>2.3</version>
 </dependency>

 <dependency>
 <groupId>com.sun.xml.bind</groupId>
 <artifactId>jaxb-impl</artifactId>
 <version>2.1.12</version>
 </dependency>
 <dependency>
 <groupId>javax.xml.ws</groupId>
 <artifactId>jaxws-api</artifactId>
 <version>2.0</version>
 </dependency>
 <dependency>
 <groupId>jdom</groupId>
 <artifactId>jdom</artifactId>
 <version>1.0</version>
 </dependency>
 <dependency>
 <groupId>ch.qos.logback</groupId>
 <artifactId>logback-classic</artifactId>
 <version>1.1.2</version>
 </dependency>
 <dependency>
 <groupId>ch.qos.logback</groupId>
 <artifactId>logback-core</artifactId>
 <version>1.1.2</version>
 </dependency>
 <dependency>
 <groupId>org.mybatis</groupId>
 <artifactId>mybatis</artifactId>
 <version>3.2.7</version>
 </dependency>
 <dependency>
 <groupId>org.mybatis</groupId>
 <artifactId>mybatis-spring</artifactId>
 <version>1.2.2</version>
 </dependency>
 <dependency>
 <groupId>org.apache.neethi</groupId>
 <artifactId>neethi</artifactId>
 <version>2.0.4</version>
 </dependency>
 <dependency>
 <groupId>org.jboss.netty</groupId>
 <artifactId>netty</artifactId>
 <version>3.2.10.Final</version>
 </dependency>
 <dependency>
 <groupId>org.quartz-scheduler</groupId>
 <artifactId>quartz</artifactId>
 <version>2.2.1</version>
 </dependency>
 <!-- https://mvnrepository.com/artifact/com.rabbitmq/rabbitmq-client -->
 <!-- <dependency>
 <groupId>com.rabbitmq</groupId>
 <artifactId>rabbitmq-client</artifactId>
 <version>1.3.0</version>
 </dependency> -->
 <dependency>
 <groupId>org.slf4j</groupId>
 <artifactId>slf4j-api</artifactId>
 <version>1.7.7</version>
 </dependency>
 <dependency>
 <groupId>org.slf4j</groupId>
 <artifactId>slf4j-log4j12</artifactId>
 <version>1.7.7</version>
 </dependency>
 <dependency>
 <groupId>org.springframework</groupId>
 <artifactId>spring-core</artifactId>
 <version>${spring.version}</version>
 </dependency>
 <dependency>
 <groupId>org.springframework</groupId>
 <artifactId>spring-context</artifactId>
 <version>${spring.version}</version>
 </dependency>
 <dependency>
 <groupId>org.springframework</groupId>
 <artifactId>spring-context-support</artifactId>
 <version>${spring.version}</version>
 </dependency>
 <dependency>
 <groupId>org.springframework</groupId>
 <artifactId>spring-web</artifactId>
 <version>${spring.version}</version>
 </dependency>
 <dependency>
 <groupId>org.springframework</groupId>
 <artifactId>spring-webmvc</artifactId>
 <version>${spring.version}</version>
 </dependency>
 <dependency>
 <groupId>org.springframework</groupId>
 <artifactId>spring-tx</artifactId>
 <version>${spring.version}</version>
 </dependency>
 <dependency>
 <groupId>org.springframework.amqp</groupId>
 <artifactId>spring-amqp</artifactId>
 <version>${spring.rabbit.version}</version>
 </dependency>
 <dependency>
 <groupId>org.springframework</groupId>
 <artifactId>spring-beans</artifactId>
 <version>${spring.version}</version>
 </dependency>
 <dependency>
 <groupId>org.springframework</groupId>
 <artifactId>spring-aop</artifactId>
 <version>${spring.version}</version>
 </dependency>
 <dependency>
 <groupId>org.springframework</groupId>
 <artifactId>spring-expression</artifactId>
 <version>${spring.version}</version>
 </dependency>
 <dependency>
 <groupId>org.springframework</groupId>
 <artifactId>spring-jdbc</artifactId>
 <version>${spring.version}</version>
 </dependency>
 <dependency>
 <groupId>org.springframework</groupId>
 <artifactId>spring-test</artifactId>
 <version>${spring.version}</version>
 </dependency>
 <dependency>
 <groupId>org.springframework</groupId>
 <artifactId>spring-orm</artifactId>
 <version>${spring.version}</version>
 </dependency>
 <dependency>
 <groupId>org.springframework</groupId>
 <artifactId>spring-aspects</artifactId>
 <version>${spring.version}</version>
 </dependency>
 <dependency>
 <groupId>org.springframework.amqp</groupId>
 <artifactId>spring-rabbit</artifactId>
 <version>${spring.rabbit.version}</version>
 </dependency>
 <dependency>
 <groupId>wsdl4j</groupId>
 <artifactId>wsdl4j</artifactId>
 <version>1.6.3</version>
 </dependency>
 <dependency>
 <groupId>org.apache.ws.security</groupId>
 <artifactId>wss4j</artifactId>
 <version>1.6.16</version>
 </dependency>
 <dependency>
 <groupId>org.codehaus.woodstox</groupId>
 <artifactId>wstx-asl</artifactId>
 <version>3.2.0</version>
 </dependency>
 <dependency>
 <groupId>org.codehaus.xfire</groupId>
 <artifactId>xfire-aegis</artifactId>
 <version>1.2.6</version>
 </dependency>
 <dependency>
 <groupId>org.codehaus.xfire</groupId>
 <artifactId>xfire-core</artifactId>
 <version>1.2.6</version>
 </dependency>
 <dependency>
 <groupId>org.apache.xmlbeans</groupId>
 <artifactId>xmlbeans</artifactId>
 <version>2.6.0</version>
 </dependency>
 <dependency>
 <groupId>org.apache.ws.commons.schema</groupId>
 <artifactId>XmlSchema</artifactId>
 <version>1.4.5</version>
 </dependency>

 <dependency>
 <groupId>com.thoughtworks.xstream</groupId>
 <artifactId>xstream</artifactId>
 <version>1.3.1</version>
 </dependency>
 <dependency>
 <groupId>org.apache.zookeeper</groupId>
 <artifactId>zookeeper</artifactId>
 <version>3.4.6</version>
 </dependency>
 </dependencies>


 </project>
 * Created by IntelliJ IDEA.
 * User: Martin.Ou
 * Date: 11/24/11
 * Time: 4:01 PM
 *
 * @see BaseRepository
 */
public abstract class AbstractBaseRepository<T extends AbstractBaseDomain> extends
        SqlSessionDaoSupport implements BaseRepository<T> {

    @Override
    public Integer insert(String stmtName, final T entity) {
        hasText(stmtName, "must speci mybatis statementname");
        notNull(entity, "parameter required!");

        Integer inserted;

        inserted = getSqlSession().insert(stmtName, entity);

        return inserted;
    }

    @Override
    public List<T> findAll(String stmtName) {
        hasText(stmtName, "must speci mybatis1 statementname");

        return getSqlSession().selectList(stmtName);


    }

    @Override
    public List<T> findByCondition(String stmtName, final Object paramMap) {
        hasText(stmtName, "must speci mybatis statementname");
        notNull(paramMap, "parameter required!");

        return getSqlSession().selectList(stmtName, paramMap);
    }

    @Override
    public Integer update(String stmtName, final Object params) {
        hasText(stmtName, "must speci mybatis statementname");
        notNull(params, "parameter required!");
        Integer updated;


        updated = getSqlSession().update(stmtName, params);
        return updated;
    }

    @Override
    public Integer delete(String stmtName, final Object params) {
        hasText(stmtName, "must speci mybatis statementname");
        notNull(params, "parameter required!");
        Integer deleted;


        deleted = getSqlSession().update(stmtName, params);

        return deleted;
    }

    @Override
    public Integer getCount(String stmtName) {
        hasText(stmtName, "must speci mybatis statementname");

        return ((Long) getSqlSession().selectOne(stmtName)).intValue();
    }

    @Override
    public Integer getCountByCondition(String stmtName, final Object paramMap) {
        hasText(stmtName, "must speci mybatis statementname");
        notNull(paramMap, "parameter required!");

        return (Integer) getSqlSession().selectOne(stmtName, paramMap);
    }

    @Override
    public T get(String stmtName, final Object paramMap) {
        hasText(stmtName, "must speci mybatis statementname");
        notNull(paramMap, "parameter required!");

        return (T) getSqlSession().selectOne(stmtName, paramMap);
    }
}
