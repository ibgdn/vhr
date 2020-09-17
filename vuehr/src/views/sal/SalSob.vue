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
                    <template>
                        <el-button type="primary" size="mini">编辑</el-button>
                        <el-button type="danger" size="mini">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <el-dialog title="添加工资账套" :visible.sync="dialogVisible" width="70%">
            <div style="display: flex;justify-content: space-around; align-items: center">
                <el-steps direction="vertical" :active="addSalaryStepActiveIndex">
                    <el-step :title="itemName" v-for="(itemName, stepIndex) in addSalaryStepItem"
                             :key="stepIndex"></el-step>
                </el-steps>
                <el-input v-for="(itemName, stepIndex) in addSalaryStepItem" :key="stepIndex"
                          :placeholder="'请输入'+ itemName + '...'" style="width: 200px"
                          v-show="stepIndex == addSalaryStepActiveIndex"></el-input>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addSalaryPreStep">
                    {{ addSalaryStepActiveIndex == 9 ? '取消' : '上一步' }}
                </el-button>
                <el-button @click="addSalaryNextStep" type="primary">
                    {{ addSalaryStepActiveIndex == 9 ? '完成' : '下一步' }}
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
                '基本工资', '交通补助', '午餐补助', '奖金', '养老金比率', '养老金基数', '医疗保险比率', '医疗保险基数', '公积金比率', '公积金基数'
            ],
            // 工资套账添加步骤当前激活展示项索引
            addSalaryStepActiveIndex: 0,
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
            if (this.addSalaryStepActiveIndex >= 9) {
                alert("ok");
                return;
            }
            this.addSalaryStepActiveIndex++;
        },
        // 添加工资账套点击上一步
        addSalaryPreStep() {
            if (this.addSalaryStepActiveIndex == 0) {
                return;
            } else if (this.addSalaryStepActiveIndex == 9) {
                this.dialogVisible = false;
                return;
            }
            this.addSalaryStepActiveIndex--;
        },
    }
}
</script>

<style scoped>

</style>