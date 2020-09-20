<template>
    <div>
        <div style="display: flex; justify-content: space-between">
            <el-button icon="el-icon-plus" type="primary" @click="showAddSalaryView">添加工资账套</el-button>
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
                    <template slot-scope="scope">
                        <el-button type="primary" size="mini">编辑</el-button>
                        <el-button type="danger" size="mini" @click="deleteSalary(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <el-dialog title="添加工资账套" :visible.sync="dialogVisible" width="50%">
            <div style="display: flex;justify-content: space-around; align-items: center">
                <el-steps direction="vertical" :active="addSalaryStepActiveIndex">
                    <el-step :title="itemName" v-for="(itemName, stepIndex) in addSalaryStepItem"
                             :key="stepIndex"></el-step>
                </el-steps>
                <!-- for 循环的参数顺序不可随意调整 -->
                <el-input v-model="salary[itemTitle]" v-for="(itemValue, itemTitle, itemIndex) in salary"
                          :key="itemIndex" :placeholder="'请输入'+ addSalaryStepItem[itemIndex] + '...'"
                          style="width: 200px"
                          v-show="itemIndex == addSalaryStepActiveIndex"></el-input>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addSalaryPreStep">
                    {{ addSalaryStepActiveIndex == this.maxSalaryStepIndex ? '取消' : '上一步' }}
                </el-button>
                <el-button @click="addSalaryNextStep" type="primary">
                    {{ addSalaryStepActiveIndex == this.maxSalaryStepIndex ? '完成' : '下一步' }}
                </el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "SalSob",
    data() {
        return {
            salaries: [],
            // 工资套账对话框是否展示
            dialogVisible: false,
            // 工资套账添加步骤项
            addSalaryStepItem: [
                '账套名称', '基本工资', '奖金', '午餐补助', '交通补助', '养老金比率', '养老金基数', '医疗保险比率', '医疗保险基数', '公积金比率', '公积金基数'
            ],
            // 工资套账添加步骤当前激活展示项索引
            addSalaryStepActiveIndex: 0,
            // 工资
            salary: {
                // 账套名称
                name: "",
                // 基本工资
                basicSalary: 0,
                // 奖金
                bonus: 0,
                // 午餐补助
                lunchSalary: 0,
                // 交通补助
                trafficSalary: 0,
                // 养老金比率
                pensionPer: 0,
                // 养老金基数
                pensionBase: 0,
                // 医疗保险比率
                medicalPer: 0,
                // 医疗保险基数
                medicalBase: 0,
                // 公积金比率
                accumulationFundPer: 0,
                // 公积金基数
                accumulationFundBase: 0,
            },
            // 添加工资账套的最小索引
            miniSalaryStepIndex: 0,
            // 添加工资账套的最大索引
            maxSalaryStepIndex: 10,
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
        // 添加工资账套对话框
        showAddSalaryView() {
            this.dialogVisible = true;
        },
        // 添加工资账套点击下一步
        addSalaryNextStep() {
            if (this.addSalaryStepActiveIndex >= this.maxSalaryStepIndex) {
                this.postJsonReq("/salary/sob/", this.salary).then(response => {
                    if (response) {
                        this.initSalaries();
                        this.dialogVisible = false;
                    }
                })
                return;
            }
            this.addSalaryStepActiveIndex++;
        },
        // 添加工资账套点击上一步
        addSalaryPreStep() {
            if (this.addSalaryStepActiveIndex == this.miniSalaryStepIndex) {
                return;
            } else if (this.addSalaryStepActiveIndex == this.maxSalaryStepIndex) {
                this.salary = {
                    // 账套名称
                    name: "",
                    // 基本工资
                    basicSalary: 0,
                    // 奖金
                    bonus: 0,
                    // 午餐补助
                    lunchSalary: 0,
                    // 交通补助
                    trafficSalary: 0,
                    // 养老金比率
                    pensionPer: 0,
                    // 养老金基数
                    pensionBase: 0,
                    // 医疗保险比率
                    medicalPer: 0,
                    // 医疗保险基数
                    medicalBase: 0,
                    // 公积金比率
                    accumulationFundPer: 0,
                    // 公积金基数
                    accumulationFundBase: 0,
                }
                this.dialogVisible = false;
                this.addSalaryStepActiveIndex = 0;
                return;
            }
            this.addSalaryStepActiveIndex--;
        },
        // 删除工资账套信息
        deleteSalary(data) {
            this.$confirm('此操作将永久删除【' + data.name + '】工资账套, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.deleteJsonReq("/salary/sob/" + data.id).then(response => {
                    if (response) {
                        this.initSalaries();
                    }
                });
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
    }
}
</script>

<style scoped>

</style>