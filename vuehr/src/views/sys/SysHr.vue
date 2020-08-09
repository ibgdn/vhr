<template>
    <div>
        <div class="searchDiv">
            <el-input v-model="keywords" placeholder="搜索用户名获取用户" prefix-icon="el-icon-search"
                      class="searchInput"></el-input>
            <el-button icon="el-icon-search" type="primary">搜索</el-button>
        </div>
        <div class="hrContainer">
            <el-card class="hrCard" v-for="(hr, hrIndex) in hrs" :key="hrIndex">
                <div slot="header" class="clearfix">
                    <span>{{ hr.name }}</span>
                    <el-button style="float: right; padding: 3px 0; color: red" type="text"
                               icon="el-icon-delete"></el-button>
                </div>
                <div>
                    <div class="imgContainer">
                        <img :src="hr.userFace" :alt="hr.name" :title="hr.name" class="userFaceImg">
                    </div>
                    <div class="userInfoContainer">
                        <div>用 户 名：{{ hr.name }}</div>
                        <div>手机号码：{{ hr.phone }}</div>
                        <div>电话号码：{{ hr.telephone }}</div>
                        <div>地 址：{{ hr.address }}</div>
                        <div>用户状态：
                            <el-switch
                                v-model="hr.enabled"
                                @change="enableChange(hr)"
                                active-text="启用"
                                active-color="green"
                                inactive-text="禁用"
                                inactive-color="red">
                            </el-switch>
                        </div>
                        <div>用户角色：
                            <el-tag type="success" style="margin-right: 4px" v-for="(role, roleIndex) in hr.roleList"
                                    :key="roleIndex">{{ role.nameZh }}
                            </el-tag>
                            <!-- 点击更多的弹出框 -->
                            <el-popover
                                placement="right"
                                title="角色列表"
                                width="200"
                                @show="showPop(hr)"
                                @hide="hidePop(hr)"
                                trigger="click">
                                <!-- 角色选择的下拉框 -->
                                <el-select v-model="selectedRoles" multiple placeholder="请选择">
                                    <el-option
                                        v-for="(role, roleIndex) in allRoles"
                                        :key="roleIndex"
                                        :label="role.nameZh"
                                        :value="role.id">
                                    </el-option>
                                </el-select>
                                <!-- 更多按钮 -->
                                <el-button slot="reference" icon="el-icon-more" type="text"></el-button>
                            </el-popover>
                        </div>
                        <div>备 注：{{ hr.remark }}</div>
                    </div>
                </div>
            </el-card>
        </div>
    </div>
</template>

<script>
export default {
    name: "SysHr",
    data() {
        return {
            // 搜索关键字
            keywords: '',
            hrs: [],
            // 所有角色
            allRoles: [],
            // 选中的角色
            selectedRoles: [],
        }
    },
    mounted() {
        this.initHrs();
    },
    methods: {
        // 初始化操作员信息
        initHrs() {
            this.getJsonReq("/system/hr/").then(response => {
                if (response) {
                    this.hrs = response;
                }
            });
        },
        // 操作员用户状态切换
        enableChange(hr) {
            delete hr.roleList;
            this.putJsonReq("/system/hr/", hr).then(response => {
                if (response) {
                    // 正常更新数据后重新刷新数据
                    this.initHrs();
                }
            });
        },
        // 初始化所有用户角色
        getAllRoles() {
            this.getJsonReq("/system/hr/roles").then(response => {
                if (response) {
                    this.allRoles = response;
                }
            });
        },
        // 展示弹窗
        showPop(hr) {
            this.getAllRoles();
            // 将之前选择的角色展示出来
            let preSelectedRoles = hr.roleList;
            this.selectedRoles = [];
            preSelectedRoles.forEach(role => {
                this.selectedRoles.push(role.id);
            });
        },
        // 隐藏弹出框同时调用更新数据的接口
        hidePop(hr) {
            // 是否请求数据更新接口
            let update = false;
            let roles = [];
            // 复制一份数据给 roles，不对源数据直接操作
            Object.assign(roles, hr.roleList);

            if (roles.length != this.selectedRoles.length) {
                update = true;
            } else {
                for (let i = 0; i < roles.length; i++) {
                    for (let j = 0; j < this.selectedRoles.length; j++) {
                        if (roles[i].id === this.selectedRoles[j]) {
                            roles.slice(i, 1);
                            i--;
                            break;
                        }
                    }
                }
                if (roles.length != 0) {
                    update = true;
                }
            }

            if (update) {
                let url = '/system/hr/hrRoles?hrId=' + hr.id;
                this.selectedRoles.forEach(sr => {
                    url += '&rolesId=' + sr;
                });
                this.putJsonReq(url).then(response => {
                    if (response) {
                        this.initHrs();
                    }
                });
            }
        },
    },
}
</script>

<style>
.searchDiv {
    margin-top: 10px;
    display: flex;
    justify-content: center;
}

.searchInput {
    width: 400px;
    margin-right: 10px;
}

.hrContainer {
    margin-top: 10px;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
}

.hrCard {
    width: 350px;
    margin-bottom: 20px;
}

.userFaceImg {
    width: 72px;
    height: 72px;
    border-radius: 72px;
}

.imgContainer {
    width: 100%;
    display: flex;
    justify-content: center;
}

.userInfoContainer {
    margin-top: 20px;
}

.userInfoContainer div {
    font-size: 12px;
    color: #409eff;
}

</style>