<template>
    <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="部门管理" name="DepartmentManage">
            <DepartmentManage></DepartmentManage>
        </el-tab-pane>
        <el-tab-pane label="职位管理" name="PositionManage">
            <PositionManage></PositionManage>
        </el-tab-pane>
        <el-tab-pane label="职称管理" name="JobLevelManage">
            <JobLevelManage></JobLevelManage>
        </el-tab-pane>
        <el-tab-pane label="奖惩规则" name="RewardPunishmentManage">
            <RewardPunishmentManage></RewardPunishmentManage>
        </el-tab-pane>
        <el-tab-pane label="权限组" name="PermissionManage">
            <PermissionManage></PermissionManage>
        </el-tab-pane>
    </el-tabs>
</template>

<script>
/*引入组件*/
import DepartmentManage from "../../components/sys/DepartmentManage";
import JobLevelManage from "../../components/sys/JobLevelManage";
import PermissionManage from "../../components/sys/PermissionManage";
import PositionManage from "../../components/sys/PositionManage";
import RewardPunishmentManage from "../../components/sys/RewardPunishmentManage";

export default {
    name: "SysBasic",
    data() {
        return {
            activeName: 'PermissionManage'
        }
    },
    /*注册组件*/
    components: {
        DepartmentManage,
        JobLevelManage,
        PermissionManage,
        PositionManage,
        RewardPunishmentManage
    }
}
</script>

<style scoped>

</style>