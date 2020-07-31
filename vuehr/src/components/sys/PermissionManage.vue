<template>
    <div>
        <div class="permissionManageInput">
            <el-input size="small" placeholder="请输入要添加的角色英文名称" v-model="role.name">
                <template slot="prepend">ROLE_</template>
            </el-input>
            <el-input size="small" placeholder="请输入要添加的角色中文名称" v-model="role.nameZh"
                      @keydown.enter.native="addRole"></el-input>
            <el-button size="small" type="primary" icon="el-icon-plus" @click="addRole">添加角色</el-button>
        </div>
        <div class="permissionManageCollapse">
            <el-collapse accordion @change="changeRoleShowMenus" v-model="activeName">
                <el-collapse-item :title="r.nameZh" :name="r.id" v-for="(r, indexRoles) in roles" :key="indexRoles">
                    <el-card class="box-card">
                        <div slot="header" class="clearfix">
                            <span>可访问的资源</span>
                            <el-button style="float: right; padding: 3px 0;color: #ff0000" icon="el-icon-delete"
                                       @click="deleteRole(r)"
                                       type="text"></el-button>
                        </div>
                        <div>
                            <el-tree show-checkbox node-key="id" :default-checked-keys="selectedMenus" ref="tree"
                                     :key="indexRoles"
                                     :data="allMenus"
                                     :props="defaultProps"></el-tree>
                            <div style="display: flex;justify-content: flex-end">
                                <el-button @click="updateCancel">取消</el-button>
                                <el-button type="primary" @click="menusUpdate(r.id, indexRoles)">确认</el-button>
                            </div>
                        </div>
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
            roles: [],
            // 各个角色下的所有菜单树展示项
            allMenus: [],
            defaultProps: {
                children: 'children',
                label: 'name'
            },
            // 默认所有标签都不打开
            activeName: -1,
            selectedMenus: []
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
        },
        initRoleMenus() {
            getJsonRequest("/system/basic/permission/menus/").then(response => {
                if (response) {
                    this.allMenus = response;
                }
            })
        },
        changeRoleShowMenus(rid) {
            if (rid) {
                this.initRoleMenus();
                this.initSelectedMenus(rid);
            }
        },
        // 获取不同角色菜单权限
        initSelectedMenus(rid) {
            this.getJsonReq("/system/basic/permission/menuIds/" + rid).then(response => {
                if (response) {
                    this.selectedMenus = response;
                }
            })
        },
        // 取消角色权限菜单修改
        updateCancel() {
            this.activeName = -1;
        },
        // 执行角色权限菜单修改
        menusUpdate(rid, index) {
            let tree = this.$refs.tree[index];
            // true 排除非叶子节点
            let checkedKeys = tree.getCheckedKeys(true);
            let url = "/system/basic/permission/?rid=" + rid;
            checkedKeys.forEach(key => {
                url += "&menuIds=" + key;
            })
            this.putJsonReq(url).then(response => {
                if (response) {
                    this.activeName = -1;
                }
            })
        },
        // 添加用户角色
        addRole() {
            if (this.role.name && this.role.nameZh) {
                this.postJsonReq("/system/basic/permission/role", this.role).then(response => {
                    if (response) {
                        this.role.name = '';
                        this.role.nameZh = '';
                        this.initRoles();
                    }
                });
            } else {
                this.$message.error('请完善角色需要添加的数据！');
            }
        },
        // 删除已有角色
        deleteRole(role) {
            this.$confirm('此操作将永久删除【' + role.nameZh + '】, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.deleteJsonReq("/system/basic/permission/role/" + role.id).then(response => {
                    if (response) {
                        this.initRoles();
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
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