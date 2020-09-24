# vhr

技术学习提交，如有侵权，请告知，删除。

***

#### vuehr 启动

1. 创建 npm serve；
2. package.json 选择 vhr/vuehr/package.json；
3. Command 选择 run；
4. 配置 Node interpreter 及 Package manager。

前端页面访问地址：`http://localhost:8080/`



#### vhr 启动

启动 vhr/vhrserver/vhr-web/src/main/java/com/ibgdn/VhrApplication.java

后台访问地址：`http://localhost:8081`



#### Docker

查看 docker 进程

```
docker ps -a
```

启动 docker 进程

```
docker run -d --hostname my-rabbit --name customize-rabbit-name -p 5672:5672 -p 15672:15672 rabbitmq:3-management 
```

移除现有 docker 进程

```
docker rm $(docker ps -a -q)
```



#### Chat

需要添加 `https://github.com/is-liyiwei/vue-Chat-demo`

安装依赖模块：

```
npm install sass-loader --save-dev
npm install sass --save-dev
```



由于 Vue 版本更新，页面没有再做调整，聊天功能暂缓开发。