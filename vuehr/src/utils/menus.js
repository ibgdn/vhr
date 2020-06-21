import {getJsonRequest} from "./api";

export const initMenu = (router, store) => {
    // store/index.js 中的 store
    if (store.state.routes.length > 0) {
        // 如果 store 有数据，说明是正常跳转，不是用户按刷新（F5）的跳转；
        // 否则如果是刷新的跳转，那么 store.state.routes.length = 0，将不会走此处逻辑，数据会重新加载。
        return;
    }

    // 请求接口，获取菜单数据
    getJsonRequest("/system/config/menu").then(data => {

        if (data) {
            let fmtRoutes = formatRoutes(data);
            router.addRoutes(fmtRoutes);
            // 调用初始化方法”initroutes”初始化数据
            store.commit('initRoutes', fmtRoutes);
        }
    })
}

export const formatRoutes = (routes) => {
    let fmRoutes = [];
    routes.forEach(router => {
        let {
            path,
            component,
            name,
            meta,
            iconCls,
            children
        } = router;

        if (children && children instanceof Array) {
            // 两级及以上菜单需要递归调用
            children = formatRoutes(children);
        }

        // 定义组件
        let fmRouter = {
            path: path,
            name: name,
            meta: meta,
            iconCls: iconCls,
            children: children,
            component(resolve) {
                // 导入组件
                if (component.startsWith("Emp")) {
                    require(['../views/emp/' + component + '.vue'], resolve);
                } else if (component.startsWith("Per")) {
                    require(['../views/per/' + component + '.vue'], resolve)
                } else if (component.startsWith("Sal")) {
                    require(['../views/sal/' + component + '.vue'], resolve)
                } else if (component.startsWith("Sta")) {
                    require(['../views/sta/' + component + '.vue'], resolve)
                } else if (component.startsWith("Sys")) {
                    require(['../views/sys/' + component + '.vue'], resolve)
                }
            }
        }
        fmRoutes.push(fmRouter);
    })
    return fmRoutes;
}

