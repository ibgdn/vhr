<template>
    <div>
        <div style="display: flex; justify-content: space-between">
            <el-button icon="el-icon-plus" type="primary">添加工资账套</el-button>
            <el-button icon="el-icon-refresh" type="success">刷新</el-button>
        </div>
        <div style="margin-top: 10px">
            <el-table :data="salaries" border stripe>
                <el-table-column type="selection" width="40"></el-table-column>
                <el-table-column width="120" prop="name" label="账套名称"></el-table-column>
                <el-table-column width="70" prop="basicSalary" label="基本工资"></el-table-column>
                <el-table-column width="70" prop="bonus" label="奖金"></el-table-column>
                <el-table-column width="70" prop="lunchSalary" label="午餐补助"></el-table-column>
                <el-table-column width="70" prop="trafficSalary" label="交通补助"></el-table-column>
                <el-table-column width="100" prop="createDate" label="启用时间"></el-table-column>
                <el-table-column label="养老金" align="center">
                    <el-table-column width="70" prop="pensionPer" label="比率"></el-table-column>
                    <el-table-column width="70" prop="pensionBase" label="基数"></el-table-column>
                </el-table-column>
                <el-table-column label="医疗保险" align="center">
                    <el-table-column width="70" prop="medicalPer" label="比率"></el-table-column>
                    <el-table-column width="70" prop="medicalBase" label="基数"></el-table-column>
                </el-table-column>
                <el-table-column label="公积金" align="center">
                    <el-table-column width="70" prop="accumulationFundPer" label="比率"></el-table-column>
                    <el-table-column width="70" prop="accumulationFundBase" label="基数"></el-table-column>
                </el-table-column>
                <el-table-column label="操作">
                    <template>
                        <el-button type="primary" size="mini">编辑</el-button>
                        <el-button type="danger" size="mini">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
export default {
    name: "SalSob",
    data() {
        return {
            salaries: [],
        }
    },
    mounted() {
        this.initSalaries();
    },
    methods: {
        initSalaries() {
            this.getJsonReq("/salary/sob/").then(response => {
                if (response) {
                    this.salaries = response;
                }
            });
        },
    }
}
</script>

<style scoped>

</style>