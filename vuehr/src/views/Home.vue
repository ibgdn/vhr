<template>
    <div>
        <el-container>
            <el-header class="homeHeader">
                <div class="title">微人事</div>
                <el-dropdown class="userInfo" @command="commandHandler">
  <span class="el-dropdown-link">
      {{ user.name }}<i><img :src="user.userface" alt=""></i>
  </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
                        <el-dropdown-item command="setting">设置</el-dropdown-item>
                        <el-dropdown-item command="logout" divided>注销登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-header>
            <el-container>
                <el-aside width="200px">
                    <!-- 使用 router 或 @select="menuClick" 完成跳转 -->
                    <!-- unique-opened 一次只打开一个菜单-->
                    <el-menu router unique-opened>
                        <!-- <el-submenu index="1" v-for="(item,indexI) in this.$router.options.routes" v-if="!item.hidden" -->
                        <el-submenu :index="indexI + ''" v-for="(item,indexI) in routes" v-if="!item.hidden"
                                    :key="indexI">
                            <template slot="title">
                                <!-- <i class="el-icon-location"></i> -->
                                <i style="color: #409eff; margin-right: 5px" :class="item.iconCls"></i>
                                <span>{{ item.name }}</span>
                            </template>
                            <!-- child.path 是变量，需要在前边加冒号 -->
                            <el-menu-item :index="child.path" v-for="(child, indexJ) in item.children" :key="indexJ">
                                {{ child.name }}
                            </el-menu-item>
                        </el-submenu>
                    </el-menu>
                </el-aside>
                <el-main>
                    <!--非首页展示面包屑导航-->
                    <el-breadcrumb separator-class="el-icon-arrow-right"
                                   v-if="this.$router.currentRoute.path != '/home'">
                        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                        <el-breadcrumb-item><a href="/">{{ this.$router.currentRoute.name }}</a></el-breadcrumb-item>
                    </el-breadcrumb>
                    <!--仅首页展示面包屑导航-->
                    <div class="homeWelcome" v-if="this.$router.currentRoute.path == '/home'">
                        欢迎来到微人事！
                    </div>
                    <router-view/>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
export default {
    name: "Home",
    data() {
        return {
            user: JSON.parse(window.sessionStorage.getItem("user"))
        }
    },
    computed: {
        routes() {
            return this.$store.state.routes;
        }
    },
    methods: {
        commandHandler(cmd) {
            if (cmd == 'logout') {
                this.$confirm('此操作将注销登录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    // 请求注销登录，清除记录，跳转登录页
                    this.getJsonReq("/logout");
                    window.sessionStorage.removeItem("user");
                    // 退出时，清空菜单选项，以便不同用户实时刷新
                    this.$store.commit('initRoutes', [])
                    this.$router.replace("/")
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消注销'
                    });
                });
            }
        },
        menuClick(index, indexPath) {
            this.$router.push(index);
            // console.log(index);
            // console.log(indexPath);
        }
    }
}
</script>

<style scoped>
.homeHeader {
    background-color: #409eff;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0px 15px;
    box-sizing: border-box;
}

.homeHeader .title {
    font-size: 30px;
    font-family: 微软雅黑;
    color: #ffffff / /;
}

.homeHeader .userInfo {
    cursor: pointer;
}

.el-dropdown-link img {
    width: 48px;
    height: 48px;
    border-radius: 25px;
    margin-left: 10px;
}

.el-dropdown-link {
    font-size: 18px;
    display: flex;
    align-items: center;
}

.homeWelcome {
    text-align: center;
    font-size: 30px;
    font-family: 华文行楷, serif;
    color: #409eff;
    padding-top: 50px;
}
</style>