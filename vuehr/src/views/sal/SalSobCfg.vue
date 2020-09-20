<template>
    <div>
        <div>
            <el-table :data="employees" size="mini" border stripe>
                <el-table-column width="60" align="left" type="selection"></el-table-column>
                <el-table-column width="120" align="left" prop="name" label="姓名" fixed></el-table-column>
                <el-table-column width="120" align="left" prop="workId" label="工号"></el-table-column>
                <el-table-column width="200" align="left" prop="email" label="电子邮件"></el-table-column>
                <el-table-column width="120" align="left" prop="phone" label="手机号码"></el-table-column>
                <el-table-column width="120" align="left" prop="department.name" lable="所属部门"></el-table-column>
                <el-table-column label="所属工资账套" align="center">
                    <template slot-scope="scope">
                        <el-tooltip placement="right">
                            <div slot="content">
                                <table>
                                    <tr>
                                        <td>基本工资</td>
                                        <td>{{ scope.row.salary.basicSalary }}</td>
                                    </tr>
                                    <tr>
                                        <td>奖金</td>
                                        <td>{{ scope.row.salary.bonus }}</td>
                                    </tr>
                                    <tr>
                                        <td>午餐补助</td>
                                        <td>{{ scope.row.salary.lunchSalary }}</td>
                                    </tr>
                                    <tr>
                                        <td>交通补助</td>
                                        <td>{{ scope.row.salary.trafficSalary }}</td>
                                    </tr>
                                    <tr>
                                        <td>养老金比率</td>
                                        <td>{{ scope.row.salary.pensionPer }}</td>
                                    </tr>
                                    <tr>
                                        <td>养老金基数</td>
                                        <td>{{ scope.row.salary.pensionBase }}</td>
                                    </tr>
                                    <tr>
                                        <td>医疗保险比率</td>
                                        <td>{{ scope.row.salary.medicalPer }}</td>
                                    </tr>
                                    <tr>
                                        <td>医疗保险基数</td>
                                        <td>{{ scope.row.salary.medicalBase }}</td>
                                    </tr>
                                    <tr>
                                        <td>公积金比率</td>
                                        <td>{{ scope.row.salary.accumulationFundPer }}</td>
                                    </tr>
                                    <tr>
                                        <td>公积金基数</td>
                                        <td>{{ scope.row.salary.accumulationFundBase }}</td>
                                    </tr>
                                    <tr>
                                        <td>启用时间</td>
                                        <td>{{ scope.row.salary.createDate }}</td>
                                    </tr>
                                </table>
                            </div>
                            <el-tag>{{ scope.row.salary.name }}</el-tag>
                        </el-tooltip>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button type="danger" size="mini">修改工资账套</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
export default {
    name: "SalSobCfg",
    data() {
        return {
            // 员工对象
            employees: [],
        }
    },
    mounted() {
        this.initEmployees();
    },
    methods: {
        // 初始化员工信息
        initEmployees() {
            this.getJsonReq("/salary/sobcfg/").then(response => {
                if (response) {
                    this.employees = response.data;
                }
            });
        },
    },
}
</script>

<style scoped>

</style>