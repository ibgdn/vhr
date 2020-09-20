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
                        <el-tooltip placement="right" v-if="scope.row.salary">
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
                        <el-tag v-else>暂未设置</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-popover placement="left" title="修改工资套账" width="200" trigger="click"
                                    @show="showSalaryPop(scope.row.salary)" @hide="hideSalaryPop(scope.row)">
                            <div>
                                <el-select v-model="currentSalary" placeholder="请选择工资套账">
                                    <el-option v-for="salary in salaries" :key="salary.id" :label="salary.name"
                                               :value="salary.id"></el-option>
                                </el-select>
                            </div>
                            <el-button slot="reference" type="danger" size="mini">修改工资账套</el-button>
                        </el-popover>
                    </template>
                </el-table-column>
            </el-table>
            <div style="display: flex; justify-content: flex-end">
                <el-pagination background layout="sizes, prev, pager, next, jumper, ->, total, slot" :total="total"
                               @current-change="currentChange" @size-change="sizeChange">
                </el-pagination>
            </div>
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
            // 工资账套数据
            salaries: [],
            // 当前所选工资账套数据
            currentSalary: null,
            // 数据总量
            total: 0,
            // 当前页码
            pageIndex: 1,
            // 每页数量
            pageSize: 10,
        }
    },
    mounted() {
        this.initEmployees();
        this.initSalaries();
    },
    methods: {
        // 初始化员工信息
        initEmployees() {
            this.getJsonReq("/salary/sobcfg/?pageIndex=" + this.pageIndex + "&pageSize=" + this.pageSize).then(response => {
                if (response) {
                    this.employees = response.data;
                    this.total = response.total;
                }
            });
        },
        // 初始化工资账套信息
        initSalaries() {
            this.getJsonReq("/salary/sobcfg/allSalaries").then(response => {
                if (response) {
                    this.salaries = response;
                }
            });
        },
        // 展示工资套账弹出信息
        showSalaryPop(data) {
            if (data) {
                this.currentSalary = data.name;
            } else {
                this.currentSalary = null;
            }
        },
        // 关闭工资套账弹出信息
        hideSalaryPop(data) {
            if (this.salary) {
                // 有数据的时候再提交
                this.putJsonReq("/salary/sobcfg/?eid=" + data.id + "&sid=" + this.currentSalary).then(response => {
                    if (response) {
                        this.initEmployees();
                    }
                });
            }
        },
        // 当前页码改变
        currentChange(pageIndex) {
            this.pageIndex = pageIndex;
            this.initEmployees();
        },
        // 每页容量修改
        sizeChange(pageSize) {
            this.pageSize = pageSize;
            this.initEmployees();
        },
    },
}
</script>

<style scoped>

</style>