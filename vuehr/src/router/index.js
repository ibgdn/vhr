import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Home from '../views/Home.vue'
import FriendChat from '../views/chat/FriendChat'

Vue.use(VueRouter)

// 解决 ElementUI 导航栏中的 vue-router 在3.0版本以上重复点菜单报错问题（路由重复）
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

const routes = [
    {
        path: '/',
        name: 'Login',
        component: Login,
        hidden: true
    },
    {
        path: '/home',
        name: 'Home',
        component: Home,
        hidden: true,
        meta: {
            roles: ['admin', 'user']
        },
        children: [
            {
                path: '/chat',
                name: '在线聊天',
                component: FriendChat,
                hidden: true
            },
        ]
    }
]

const router = new VueRouter({
    routes
})

export default router
