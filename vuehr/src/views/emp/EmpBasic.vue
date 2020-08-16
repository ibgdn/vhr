<template>
    <div>
        <div style="display: flex;justify-content: space-between">
            <div>
                <el-input aria-placeholder="请输入员工姓名搜索" prefix-icon="el-icon-search"
                          style="width: 300px; margin-right: 10px"></el-input>
                <el-button icon="el-icon-search" type="primary">搜索</el-button>
                <el-button type="primary"><i class="fa fa-angle-double-down" aria-hidden="true"></i>高级搜索</el-button>
            </div>
            <div>
                <el-button type="success"><i class="fa fa-level-up" aria-hidden="true"></i>导入数据</el-button>
                <el-button type="success"><i class="fa fa-level-down" aria-hidden="true"></i>导出数据</el-button>
                <el-button type="primary"><i class="el-icon-plus" aria-hidden="true"></i>添加用户</el-button>
            </div>
        </div>
        <div style="margin-top: 10px">
            <el-table
                style="width: 100%"
                :data="employees"
                stripe
                border
                v-loading="loading"
                element-loading-text="正在加载..."
                element-loading-spinner="el-icon-loading"
                element-loading-background="rgba(0, 0, 0, 0.8)">
                <el-table-column
                    type="selection"
                    width="55">
                </el-table-column>
                <el-table-column
                    prop="name"
                    fixed
                    align="left"
                    label="姓名"
                    width="90">
                </el-table-column>
                <el-table-column
                    prop="workId"
                    label="工号"
                    align="left"
                    width="85">
                </el-table-column>
                <el-table-column
                    prop="gender"
                    label="性别"
                    align="left"
                    width="85">
                </el-table-column>
                <el-table-column
                    prop="birthday"
                    label="出生日期"
                    align="left"
                    width="95">
                </el-table-column>
                <el-table-column
                    prop="idCard"
                    label="身份证号码"
                    align="left"
                    width="150">
                </el-table-column>
                <el-table-column
                    prop="wedlock"
                    label="婚姻状况"
                    width="70">
                </el-table-column>
                <el-table-column
                    prop="nation.name"
                    label="民族"
                    width="50">
                </el-table-column>
                <el-table-column
                    prop="nativePlace"
                    label="籍贯"
                    width="80">
                </el-table-column>
                <el-table-column
                    prop="politicsstatus.name"
                    label="政治面貌">
                </el-table-column>
                <el-table-column
                    prop="email"
                    label="电子邮件"
                    align="left"
                    width="180">
                </el-table-column>
                <el-table-column
                    prop="phone"
                    label="电话号码"
                    align="left"
                    width="100">
                </el-table-column>
                <el-table-column
                    prop="address"
                    label="联系地址"
                    align="left"
                    width="220">
                </el-table-column>
                <el-table-column
                    prop="department.name"
                    label="所属部门"
                    align="left"
                    width="100">
                </el-table-column>
                <el-table-column
                    prop="position.name"
                    label="职位"
                    width="100">
                </el-table-column>
                <el-table-column
                    prop="jobLevel.name"
                    label="职称"
                    width="100">
                </el-table-column>
                <el-table-column
                    prop="engageForm"
                    label="聘用形式"
                    align="left"
                    width="100">
                </el-table-column>
                <el-table-column
                    prop="tipTopDegree"
                    label="最高学历"
                    align="left"
                    width="80">
                </el-table-column>
                <el-table-column
                    prop="speciality"
                    label="专业"
                    align="left"
                    width="150">
                </el-table-column>
                <el-table-column
                    prop="school"
                    label="毕业院校"
                    align="left"
                    width="150">
                </el-table-column>
                <el-table-column
                    prop="beginDate"
                    label="入职时间"
                    align="left"
                    width="95">
                </el-table-column>
                <el-table-column
                    prop="conversionTime"
                    label="转正时间"
                    align="left"
                    width="150">
                </el-table-column>
                <el-table-column
                    prop="beginContract"
                    label="合同起始日期"
                    align="left"
                    width="95">
                </el-table-column>
                <el-table-column
                    label="合同期限"
                    align="left"
                    width="100">
                    <template slot-scope="scope">
                        <el-tag>{{ scope.row.contractTerm }}</el-tag>
                        年
                    </template>
                </el-table-column>
                <el-table-column
                    fixed="right"
                    label="操作"
                    width="200">
                    <template slot-scope="scope">
                        <el-button>编辑</el-button>
                        <el-button>查看高级资料</el-button>
                        <el-button>删除</el-button>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="conversionTime"
                    label="转正时间"
                    align="left"
                    width="150">
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
export default {
    name: "EmpBasic",
    data() {
        return {
            // 加载等待框默认不加载
            loading: false,
            // 员工
            employees: [],

        }
    }
}
</script>

<style scoped>

</style>