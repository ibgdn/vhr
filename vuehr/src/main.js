import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import {initMenu} from "./utils/menus";
import 'font-awesome/css/font-awesome.min.css'

// 封装请求方法 start
import {postKeyValueRequest} from "./utils/api";
import {getJsonRequest} from "./utils/api";
import {postJsonRequest} from "./utils/api";
import {putJsonRequest} from "./utils/api";
import {deleteJsonRequest} from "./utils/api";

Vue.prototype.postKeyValueReq = postKeyValueRequest;
Vue.prototype.getJsonReq = getJsonRequest;
Vue.prototype.postJsonReq = postJsonRequest;
Vue.prototype.putJsonReq = putJsonRequest;
Vue.prototype.deleteJsonReq = deleteJsonRequest;
// 封装请求方法 end

Vue.config.productionTip = false

Vue.use(ElementUI);

// 导航守卫，类似于过滤器链
router.beforeEach((to, from, next) => {
    if (to.path == '/') {
        next();
    } else {
        initMenu(router, store);
        next();
    }
})

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
