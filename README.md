# Dealer Manager

这是一个基于 Spring Boot 的经销商管理系统。

## 技术栈

- Spring Boot 2.7.18
- MyBatis Plus
- Maven
- MySQL

## 功能模块

- 用户管理
- 经销商管理
- 运单管理
- 安全调用配置

## 快速开始

1. 克隆项目
2. 配置数据库连接
3. 运行项目: `mvn spring-boot:run`

## 配置文件

- application-dev.properties - 开发环境配置
- application-prod.properties - 生产环境配置

## 项目结构

```
src/main/java/com/dealermanager
├── annotation
├── config
├── constants
├── controller
├── entity
├── exception
├── interceptor
├── mapper_dealer
├── model
├── service
│   └── impl
└── util
```