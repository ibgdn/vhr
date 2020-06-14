import axios from 'axios';
import {Message} from 'element-ui';
import router from '../router';

axios.interceptors.response.use(success => {
    if (success.status && success.status == 200 && success.data.status == 500) {
        Message.error({message: success.data.message})
        return;
    }

    if (success.data.message) {
        Message.success({message: success.data.message})
    }
    return success.data;
}, error => {
    if (error.response.status == 401) {
        Message.error({message: error.response.data.message ? error.response.data.message : '用户尚未登录'})
        router.replace('/');
    } else if (error.response.status == 403) {
        Message.error({message: '权限不足，请联系管理员'})
    } else if (error.response.status == 404 || error.response.status == 504) {
        Message.error({message: '寻找的文件无法找到'})
    } else {
        if (error.response.data.message) {
            Message.error({message: error.response.data.message})
        } else {
            Message.error({message: '未知错误'})
        }
    }
    return;
})

let base = '';

// 封装请求
export const postKeyValueRequest = (url, params) => {
    return axios({
        method: 'post',
        url: `${base}${url}`,
        data: params,
        // 转换 params 的格式（由 json 转换为 key、value）
        transformRequest: [function (data) {
            let ret = '';
            for (let dataKey in data) {
                ret += encodeURIComponent(dataKey) + '=' + encodeURIComponent(data[dataKey]) + '&'
            }
            console.log(ret);
            return ret;
        }],
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    });
};
