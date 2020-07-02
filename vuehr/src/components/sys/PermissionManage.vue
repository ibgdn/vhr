<template>
    <div>
        <div class="permissionManageInput">
            <el-input size="small" placeholder="请输入要添加的角色英文名称" v-model="role.name">
                <template slot="prepend">ROLE_</template>
            </el-input>
            <el-input size="small" placeholder="请输入要添加的角色中文名称" v-model="role.nameZh"></el-input>
            <el-button size="small" type="primary" icon="el-icon-plus">添加角色</el-button>
        </div>
        <div class="permissionManageCollapse">
            <el-collapse accordion>
                <el-collapse-item :title="r.nameZh" :name="r.id" v-for="(r, indexRoles) in roles" :key="indexRoles">
                    <el-card class="box-card">
                        <div slot="header" class="clearfix">
                            <span>可访问的资源</span>
                            <el-button style="float: right; padding: 3px 0;color: #ff0000" icon="el-icon-delete"
                                       type="text"></el-button>
                        </div>
                        <div></div>
                    </el-card>
                </el-collapse-item>
            </el-collapse>
        </div>
    </div>
</template>

<script>
import {getJsonRequest} from "../../utils/api";

export default {
    name: "PermissionManage",
    data() {
        return {
            role: {
                name: '',
                nameZh: ''
            },
            // 获取所有角色
            roles: []
        }
    },
    mounted() {
        this.initRoles();
    },
    methods: {
        initRoles() {
            getJsonRequest("/system/basic/permission/").then(response => {
                if (response) {
                    this.roles = response;
                }
            })
        }
    }
}
</script>

<style>
.permissionManageInput {
    /*作用不大*/
    display: flex;
    justify-content: flex-start;
}

.permissionManageInput .el-input {
    width: 300px;
    margin-right: 8px;
}

.permissionManageCollapse {
    margin-top: 16px;
    width: 800px;
}
</style>