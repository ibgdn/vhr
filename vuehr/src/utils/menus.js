import {getJsonRequest} from "./api";

export const initMenu = (router, store) => {
    // store/index.js 中的 store
    if (store.state.routes.length > 0) {
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
                if (component.startWith("Emp")) {
                    require(['../views/emp/' + component + '.vue'], resolve);
                } else if (component.startWith("Per")) {
                    require(['../views/per/' + component + '.vue'], resolve)
                } else if (component.startWith("Sal")) {
                    require(['../views/sal/' + component + '.vue'], resolve)
                } else if (component.startWith("Sta")) {
                    require(['../views/sta/' + component + '.vue'], resolve)
                } else if (component.startWith("Sys")) {
                    require(['../views/sys/' + component + '.vue'], resolve)
                }
            }
        }
        fmRoutes.push(fmRouter);
    })
    return fmRoutes;
}

