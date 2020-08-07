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
                                active-text="启用"
                                active-color="green"
                                inactive-text="禁用"
                                inactive-color="red">
                            </el-switch>
                        </div>
                        <div>用户角色：
                            <el-tag type="success" stype="margin-right: 2px" v-for="(role, roleIndex) in hr.roleList"
                                    :key="roleIndex">{{ role.nameZh }}
                            </el-tag>
                            <el-button icon="el-icon-more" type="text"></el-button>
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
        }
    },
    mounted() {
        this.initHrs();
    },
    methods: {
        initHrs() {
            this.getJsonReq("/system/hr/").then(response => {
                if (response) {
                    console.log(response);
                    this.hrs = response;
                }
            });
        }
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