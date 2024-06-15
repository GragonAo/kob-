
# 项目运行指南

## 前端项目运行

首先，确保你的系统已经安装了 Node.js 和 npm。
本项目采用了uniapp开发
1. **安装依赖**：
    ```bash
    npm install
    ```

2. **运行开发服务器**：
web
    ```bash
    npm run dev:h5
    ```
weixin
    ```bash
    npm run dev:weixin
    ```

## 后端项目运行

确保你的系统已经安装了 Java17 和 Maven。

1. **安装依赖**：
    ```bash
    mvn clean install
    ```
2. **使用 JAR 文件运行**：
        ```bash
    mvn clean package
    ```
3. **运行服务器**：
登录服务器
    ```bash
    java -jar backend-0.0.1-SNAPSHOT.jar
    ```
匹配服务器
    ```bash
    java -jar matchingsystem-0.0.1-SNAPSHOT.jar
    ```

## 注意事项

- 确保在运行以上命令之前，已经进入到项目的根目录。
- 如果在安装依赖或运行服务器过程中遇到问题，请检查相应的错误信息并进行调试。
- 在生产环境中，建议使用 `npm run build` 生成前端的静态文件，并使用合适的 Web 服务器（如 Nginx、Apache）和 WSGI 服务器（如 Gunicorn、uWSGI）来部署后端项目。