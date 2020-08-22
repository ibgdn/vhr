<template>
    <div>
        <div style="display: flex;justify-content: space-between">
            <div>
                <el-input aria-placeholder="请输入员工姓名搜索" prefix-icon="el-icon-search"
                          style="width: 300px; margin-right: 10px" v-model="keyword"
                          clearable @clear="initEmployees"
                          @keydown.enter.native="initEmployees"></el-input>
                <el-button icon="el-icon-search" type="primary" @click="initEmployees">搜索</el-button>
                <el-button type="primary"><i class="fa fa-angle-double-down" aria-hidden="true"></i>高级搜索</el-button>
            </div>
            <div>
                <el-button type="success"><i class="fa fa-level-up" aria-hidden="true"></i>导入数据</el-button>
                <el-button type="success"><i class="fa fa-level-down" aria-hidden="true"></i>导出数据</el-button>
                <el-button type="primary"><i class="el-icon-plus" aria-hidden="true" @click="showAddEmployeeView"></i>添加用户
                </el-button>
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
                    width="150">
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
                    prop="politicsStatus.name"
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
                    label="入职日期"
                    align="left"
                    width="150">
                </el-table-column>
                <el-table-column
                    prop="conversionTime"
                    label="转正日期"
                    align="left"
                    width="150">
                </el-table-column>
                <el-table-column
                    prop="beginContract"
                    label="合同起始日期"
                    align="left"
                    width="150">
                </el-table-column>
                <el-table-column
                    prop="endContract"
                    label="合同截止日期"
                    align="left"
                    width="150">
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
                        <el-button class="functionButton" size="mini">编辑</el-button>
                        <el-button class="functionButton" size="mini" type="primary">查看高级资料</el-button>
                        <el-button class="functionButton" size="mini" type="danger">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="display: flex;justify-content: flex-end">
                <el-pagination
                    background
                    @current-change="currentChange"
                    @size-change="sizeChange"
                    layout="sizes,prev,pager,next,jumper,->,total,slot"
                    :total="total"></el-pagination>
            </div>
        </div>
        <el-dialog
            title="添加员工"
            :visible.sync="dialogVisible"
            width="90%">
            <div>
                <el-form>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="姓名：" prop="name">
                                <el-input size="mini" style="width: 150px" v-model="employee.name"
                                          prefix-icon="el-icon-edit" aria-placeholder="请输入姓名"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="性别：" prop="gender">
                                <el-radio-group v-model="employee.gender">
                                    <el-radio label="男">男</el-radio>
                                    <el-radio label="女">女</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="出生日期：" prop="birthday">
                                <el-date-picker size="mini" style="width: 150px;" v-model="employee.birthday"
                                                value-format="yyyy-MM-dd" type="date"
                                                aria-placeholder="出生日期"></el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="政治面貌：" prop="politicId">
                                <el-select size="mini" style="width: 200px" v-model="employee.politicId"
                                           placeholder="请选择政治面貌">
                                    <el-option v-for="item in politicsStatus" :key="politicsStatus.id"
                                               :label="politicsStatus.name" :value="politicsStatus.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="民族：" prop="nationId">
                                <el-select size="mini" style="width: 150px" v-model="employee.nationId"
                                           placeholder="请选择民族">
                                    <el-option v-for="item in nations" :key="item.id" :label="item.name"
                                               :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="籍贯：" prop="nativePlace">
                                <el-input size="mini" style="width: 120px" v-model="employee.nativePlace"
                                          prefix-icon="el-icon-edit" aria-placeholder="请输入籍贯"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="电子邮箱：" prop="email">
                                <el-input size="mini" style="width: 150px" v-model="employee.email"
                                          prefix-icon="el-icon-message" aria-placeholder="请输入邮箱地址"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="联系地址：" prop="address">
                                <el-input size="mini" style="width: 200px" v-model="employee.address"
                                          prefix-icon="el-icon-edit" aria-placeholder="请输入住址信息"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="职位：" prop="posId">
                                <el-select size="mini" style="width: 150px" v-model="employee.posId"
                                           placeholder="请选择职位">
                                    <el-option v-for="item in options">

                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="职称：" prop="jobLevelId">
                                <el-select size="mini" style="width: 120px" v-model="employee.jobLevelId"
                                           placeholder="请选择职称">
                                    <el-option v-for="item in jobLevels" :key="jobLevels.id" :label="jobLevels.name"
                                               :value="jobLevels.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="所属部门：" prop="departmentId">
                                <el-input size="mini" style="width: 150px" v-model="employee.departmentId"
                                          prefix-icon="el-icon-edit" aria-placeholder="请输入所属部门"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="电话号码：" prop="phone">
                                <el-input size="mini" style="width: 200px" v-model="employee.phone"
                                          prefix-icon="el-icon-phone" aria-placeholder="请输入电话号码"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="工号：" prop="workID">
                                <el-input size="mini" style="width: 150px" v-model="employee.workID"
                                          prefix-icon="el-icon-edit" aria-placeholder="工号"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="学历：" prop="tipTopDegree">
                                <el-select size="mini" style="width: 120px" v-model="employee.tipTopDegree"
                                           placeholder="请选择学历">
                                    <el-option v-for="item in options">

                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="毕业院校：" prop="school">
                                <el-input size="mini" style="width: 150px" v-model="employee.school"
                                          prefix-icon="el-icon-edit" aria-placeholder="请输入毕业院校"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="专业名称：" prop="specialty">
                                <el-input size="mini" style="width: 200px" v-model="employee.specialty"
                                          prefix-icon="el-icon-edit" aria-placeholder="请输入专业名称"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="入职日期：" prop="beginDate">
                                <el-date-picker size="mini" style="width: 130px;" v-model="employee.beginDate"
                                                value-format="yyyy-MM-dd" type="date"
                                                aria-placeholder="入职日期"></el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="转正日期：" prop="conversionTime">
                                <el-date-picker size="mini" style="width: 130px;" v-model="employee.conversionTime"
                                                value-format="yyyy-MM-dd" type="date"
                                                aria-placeholder="转正日期"></el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="合同起始日期：" prop="beginContract">
                                <el-date-picker size="mini" style="width: 130px;" v-model="employee.beginContract"
                                                value-format="yyyy-MM-dd" type="date"
                                                aria-placeholder="合同起始日期"></el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="合同转正日期：" prop="endContract">
                                <el-date-picker size="mini" style="width: 130px;" v-model="employee.endContract"
                                                value-format="yyyy-MM-dd" type="date"
                                                aria-placeholder="合同转正日期"></el-date-picker>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="8">
                            <el-form-item label="身份证号码：" prop="idCard">
                                <el-input size="mini" style="width: 200px" v-model="employee.idCard"
                                          prefix-icon="el-icon-edit" aria-placeholder="请输入身份证号码"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="聘用形式：" prop="engageForm">
                                <el-radio-group v-model="employee.engageForm">
                                    <el-radio label="劳动合同">劳动合同</el-radio>
                                    <el-radio label="劳务合同">劳务合同</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="婚姻状况：" prop="wedlock">
                                <el-radio-group v-model="employee.wedlock">
                                    <el-radio label="已婚">已婚</el-radio>
                                    <el-radio label="未婚">未婚</el-radio>
                                    <el-radio label="离异">离异</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
            </span>
        </el-dialog>
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
            // 员工总量
            total: 0,
            // 页码
            page: 1,
            // 每页显示条数
            size: 10,
            // 搜索关键字
            keyword: '',
            employee: {
                name: "employee",
                gender: "男",
                birthday: "1989-12-31 12:24:31",
                idCard: "610122198912311256",
                wedlock: "已婚",
                nationId: 1,
                nativePlace: "陕西",
                politicId: 13,
                email: "laowangg@qq.com",
                phone: "18565558897",
                address: "深圳市南山区",
                departmentId: 5,
                jobLevelId: 9,
                posId: 29,
                engageForm: "劳务合同",
                tipTopDegree: "本科",
                specialty: "信息管理与信息技术",
                school: "深圳大学",
                beginDate: "2017-12-31 10:00:00",
                workState: "在职",
                workID: "00000087",
                contractTerm: 2,
                conversionTime: "2018-03-30 09:00:00",
                notworkDate: null,
                beginContract: "2017-12-31 08:00:01",
                endContract: "2019-12-03 04:00:00",
                workAge: null
            },
            // 添加员工对话框是否展示
            dialogVisible: false,
            // 政治面貌
            options: [],
            // 民族
            nations: [],
            // 职位
            jobLevels: [],
            // 政治面貌
            politicsStatus: [],
        }
    },
    mounted() {
        this.initEmployees();
        this.initData();
    },
    methods: {
        // 初始化职员信息数据
        initEmployees() {
            this.loading = true;
            this.getJsonReq("/emp/basic/?page=" + this.page + "&size=" + this.size + "&keyword=" + this.keyword).then(response => {
                this.loading = false;
                if (response) {
                    this.employees = response.data;
                    this.total = response.total;
                }
            });
        },
        // 页码切换
        currentChange(currentPage) {
            this.page = currentPage;
            this.initEmployees();
        },
        // 每页显示数量切换
        sizeChange(pageSize) {
            this.size = pageSize;
            this.initEmployees();
        },
        // 添加员工弹出框
        showAddEmployeeView() {
            this.dialogVisible = true;
        },
        // 添加员工信息时初始化下拉框数据
        initData() {
            if (!window.sessionStorage.getItem("nations")) {
                this.getJsonReq("/emp/basic/nations").then(response => {
                    this.nations = response;
                });
            }

            if (!window.sessionStorage.getItem("jobLevels")) {
                this.getJsonReq("/emp/basic/jobLevels").then(response => {
                    console.log(response);
                    this.jobLevels = response;
                    console.log(this.jobLevels);
                });
            }

            if (!window.sessionStorage.getItem("politicsStatus")) {
                this.getJsonReq("/emp/basic/politicsStatus").then(response => {
                    console.log(response);
                    this.politicsStatus = response;
                    console.log(this.politicsStatus)
                });
            }
        }
    }
}
</script>

<style scoped>
.functionButton {
    padding: 3px;
}
</style>