<template>
    <div>
        <el-form :rules="rules" ref="loginForm" :model="loginForm" class="loginContainer">
            <h3 class="loginTitle">系统登录</h3>
            <el-form-item prop="username">
                <el-input type="text" v-model="loginForm.username" auto-complete="off"
                          placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input type="password" v-model="loginForm.password" auto-complete="off"
                          placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-checkbox class="loginRemember" v-model="checked"></el-checkbox>
            <el-button type="primary" style="width: 100%" @click="loginSubmit">登录</el-button>
        </el-form>
    </div>
</template>

<script>
import {postKeyValueRequest} from "../utils/api";

export default {
    name: "Login",
    data() {
        return {
            loginForm: {
                username: 'admin',
                password: '123456'
            },
            checked: true,
            rules: {
                username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
                password: [{required: true, message: '请输入密码', trigger: 'blur'}]
            }
        }
    },
    methods: {
        loginSubmit() {
            this.$refs.loginForm.validate((valid) => {
                if (valid) {
                    // alert("submit!")
                    postKeyValueRequest('/doLogin', this.loginForm).then(response => {
                        if (response) {
                            // alert(JSON.stringify(response));
                            window.sessionStorage.setItem('user', JSON.stringify(response.data));
                            // replace 跳转之后不可以回退到上一页， push 跳转之后可以回退
                            this.$router.replace('/home');
                        }
                    })
                } else {
                    this.$message.error('请输入录入框内容！')
                }
            })
        }
    }
}
</script>

<style>

.loginContainer {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 15px 35px 25px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
}

.loginTitle {
    margin: 15px auto 20px auto;
    text-align: center;
    color: #505458;
}

.loginRemember {
    text-align: left;
    margin: 0px 0px 20px 0px;
}

</style>