<template>
    <div>
        <div>
            <div style="display: flex;justify-content: space-between">
                <div>
                    <el-input aria-placeholder="请输入员工姓名搜索" prefix-icon="el-icon-search"
                              style="width: 400px; margin-right: 10px" v-model="keyword"
                              clearable @clear="initEmployees" :disabled="showAdvancedSearch"
                              @keydown.enter.native="initEmployees"></el-input>
                    <el-button icon="el-icon-search" type="primary" @click="initEmployees"
                               :disabled="showAdvancedSearch">搜索
                    </el-button>
                    <el-button type="primary" @click="showAdvancedSearch = !showAdvancedSearch">
                        <i :class="showAdvancedSearch?'fa fa-angle-double-up':'fa fa-angle-double-down'"
                           aria-hidden="true"></i>高级搜索
                    </el-button>
                </div>
                <div>
                    <el-upload style="display: inline-flex; margin-right: 10px" :show-file-list="false"
                               :before-upload="beforeUpload" :on-success="onSuccess" :on-error="onError"
                               action="/emp/basic/import">
                        <el-button type="success" :disabled="importDisabled" :icon="importDataButtonIcon">
                            {{ importDataButtonText }}
                        </el-button>
                    </el-upload>
                    <el-button type="success" @click="exportData" icon="el-icon-download">导出数据</el-button>
                    <el-button type="primary" @click="showAddEmployeeView" icon="el-icon-plus">添加用户</el-button>
                </div>
            </div>
            <transition name="slide-fade">
                <div v-show="showAdvancedSearch" class="advancedSearch">
                    <el-row>
                        <el-col :span="5">
                            政治面貌：
                            <el-select size="mini" style="width: 100px" v-model="employee.politicId"
                                       placeholder="请选择政治面貌">
                                <el-option v-for="item in politicsStatus" :key="item.id"
                                           :label="item.name" :value="item.id">
                                </el-option>
                            </el-select>
                        </el-col>
                        <el-col :span="4">
                            民族：
                            <el-select size="mini" style="width: 150px" v-model="employee.nationId"
                                       placeholder="请选择民族">
                                <el-option v-for="item in nations" :key="item.id" :label="item.name"
                                           :value="item.id">
                                </el-option>
                            </el-select>
                        </el-col>
                        <el-col :span="4">
                            职位：
                            <el-select size="mini" style="width: 150px" v-model="employee.posId"
                                       placeholder="请选择职位">
                                <el-option v-for="item in positions" :key="item.id" :label="item.name"
                                           :value="item.id">
                                </el-option>
                            </el-select>
                        </el-col>
                        <el-col :span="4">
                            职称：
                            <el-select size="mini" style="width: 120px" v-model="employee.jobLevelId"
                                       placeholder="请选择职称">
                                <el-option v-for="item in jobLevels" :key="item.id" :label="item.name"
                                           :value="item.id">
                                </el-option>
                            </el-select>
                        </el-col>
                        <el-col :span="7">
                            聘用形式：
                            <el-radio-group v-model="employee.engageForm">
                                <el-radio label="劳动合同">劳动合同</el-radio>
                                <el-radio label="劳务合同">劳务合同</el-radio>
                            </el-radio-group>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="5" style="margin-top: 10px">
                            所属部门：
                            <el-popover placement="right" title="请选择部门" width="130" trigger="manual"
                                        v-model="popVisible">
                                <el-tree default-expand-all :data="allDepartments" :props="departmentProps"
                                         @node-click="handleNodeClick"></el-tree>
                                <div slot="reference" class="departmentSelectDiv"
                                     @click="showDepartmentsView">所属部门
                                </div>
                            </el-popover>
                        </el-col>
                        <el-col :span="10" style="margin-top: 10px">
                            入职日期：
                            <el-date-picker size="mini" v-model="value1" type="daterange" range-separator="至"
                                            start-placeholder="开始时间" end-placeholder="结束时间">
                            </el-date-picker>
                        </el-col>
                        <el-col :span="5" offset="4">
                            <el-button size="mini">取消</el-button>
                            <el-button size="mini" type="primary" icon="el-icon-search">搜索</el-button>
                        </el-col>
                    </el-row>
                </div>
            </transition>
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
                    prop="specialty"
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
                        <el-button class="functionButton" size="mini" @click="showEditEmployeeView(scope.row)">编辑
                        </el-button>
                        <el-button class="functionButton" size="mini" type="primary">查看高级资料</el-button>
                        <el-button class="functionButton" size="mini" type="danger" @click="deleteEmployee(scope.row)">
                            删除
                        </el-button>
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
            :title="title"
            :visible.sync="dialogVisible"
            width="55%">
            <div>
                <el-form :model="employee" :rules="employeeRules" ref="employeeForm">
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
                                    <el-option v-for="item in politicsStatus" :key="item.id"
                                               :label="item.name" :value="item.id">
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
                                    <el-option v-for="item in positions" :key="item.id" :label="item.name"
                                               :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="职称：" prop="jobLevelId">
                                <el-select size="mini" style="width: 120px" v-model="employee.jobLevelId"
                                           placeholder="请选择职称">
                                    <el-option v-for="item in jobLevels" :key="item.id" :label="item.name"
                                               :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="所属部门：" prop="departmentId">
                                <el-popover placement="right" title="请选择部门" width="200" trigger="manual"
                                            v-model="popVisible">
                                    <el-tree default-expand-all :data="allDepartments" :props="departmentProps"
                                             @node-click="handleNodeClick"></el-tree>
                                    <div slot="reference" class="departmentSelectDiv"
                                         @click="showDepartmentsView">{{ selectedDepartment }}
                                    </div>
                                </el-popover>
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
                                <el-input size="mini" style="width: 150px" disabled v-model="employee.workId"
                                          aria-placeholder="工号"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="学历：" prop="tipTopDegree">
                                <el-select size="mini" style="width: 120px" v-model="employee.tipTopDegree"
                                           placeholder="请选择学历">
                                    <el-option v-for="item in tipTopDegrees" :key="item" :label="item" :value="item">
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
                            <el-form-item label="合同截止日期：" prop="endContract">
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
                <el-button type="primary" @click="addEmployeeSubmit">确 定</el-button>
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
                birthday: "1989-12-31",
                idCard: "610122198912311256",
                wedlock: "已婚",
                nationId: 1,
                nativePlace: "陕西",
                politicId: 13,
                email: "laowangg@qq.com",
                phone: "18565558897",
                address: "深圳市南山区",
                departmentId: null,
                jobLevelId: 9,
                posId: 29,
                engageForm: "劳务合同",
                tipTopDegree: "本科",
                specialty: "信息管理与信息技术",
                school: "深圳大学",
                beginDate: "2017-12-31",
                workState: "在职",
                workId: "00000087",
                contractTerm: 2,
                conversionTime: "2018-03-30",
                notworkDate: null,
                beginContract: "2017-12-31",
                endContract: "2019-12-03",
                workAge: null
            },
            // 添加员工对话框是否展示
            dialogVisible: false,
            // 政治面貌
            options: [],
            // 民族
            nations: [],
            // 职位
            positions: [],
            // 职称
            jobLevels: [],
            // 政治面貌
            politicsStatus: [],
            // 学历（数据库使用枚举类型，前端直接展示静态数据）
            tipTopDegrees: ['本科', '大专', '硕士', '博士', '高中', '初中', '小学', '其他'],
            // 部门弹出框是否可见
            popVisible: false,
            // 所有部门
            allDepartments: [],
            // 部门弹窗目录树
            departmentProps: {
                children: 'departmentChildren',
                label: 'name'
            },
            // 新增员工的部门
            selectedDepartment: '',
            // 新增员工的规则
            employeeRules: {
                name: [{required: true, message: '请输入员工姓名', trigger: 'blur'}],
                gender: [{required: true, message: '请输入员工姓别', trigger: 'blur'}],
                birthday: [{required: true, message: '请选择员工出生日期', trigger: 'blur'}],
                idCard: [{required: true, message: '请输入员工身份证号码', trigger: 'blur'}, {
                    pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
                    message: '身份证号码不正确',
                    trigger: 'blur'
                }],
                wedlock: [{required: true, message: '请选择员工婚姻状况', trigger: 'blur'}],
                nationId: [{required: true, message: '请选择员工民族', trigger: 'blur'}],
                nativePlace: [{required: true, message: '请输入员工籍贯', trigger: 'blur'}],
                politicId: [{required: true, message: '请选择员工政治面貌', trigger: 'blur'}],
                email: [{required: true, message: '请输入员工邮箱地址', trigger: 'blur'}, {
                    type: 'email',
                    message: '邮箱格式不正确',
                    trigger: 'blur'
                }],
                phone: [{required: true, message: '请输入员工手机号码', trigger: 'blur'}],
                address: [{required: true, message: '请输入员工住址', trigger: 'blur'}],
                departmentId: [{required: true, message: '请选择员工所属部门', trigger: 'blur'}],
                jobLevelId: [{required: true, message: '请选择员工职称', trigger: 'blur'}],
                posId: [{required: true, message: '请选择员工职位', trigger: 'blur'}],
                engageForm: [{required: true, message: '请选择员工聘用形式', trigger: 'blur'}],
                tipTopDegree: [{required: true, message: '请选择员工最高学历', trigger: 'blur'}],
                specialty: [{required: true, message: '请输入员工所学专业', trigger: 'blur'}],
                school: [{required: true, message: '请输入员工毕业院校', trigger: 'blur'}],
                beginDate: [{required: true, message: '请输入员工入职日期', trigger: 'blur'}],
                workState: [{required: true, message: '请输入员工工作状态', trigger: 'blur'}],
                workId: [{required: true, message: '请输入员工工号', trigger: 'blur'}],
                contractTerm: [{required: true, message: '请输入员工合同期限', trigger: 'blur'}],
                conversionTime: [{required: true, message: '请输入员工转正日期', trigger: 'blur'}],
                notworkDate: [{required: true, message: '请输入员工离职日期', trigger: 'blur'}],
                beginContract: [{required: true, message: '请输入员工合同起始日期', trigger: 'blur'}],
                endContract: [{required: true, message: '请输入员工合同结束日期', trigger: 'blur'}],
                workAge: [{required: true, message: '请输入员工工龄', trigger: 'blur'}],
            },
            // 弹窗名称
            title: '',
            // 导入数据按钮文字
            importDataButtonText: '导入数据',
            // 导入数据按钮图标
            importDataButtonIcon: 'el-icon-upload2',
            // 导入失效
            importDisabled: false,
            // 是否展示高级搜索条件筛选框
            showAdvancedSearch: false,
        }
    },
    mounted() {
        this.initEmployees();
        this.initData();
        // 调整职位加载位置（用于高级搜索框）
        this.initPosition();
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
            this.clearEmployee();
            this.title = '添加员工';
            this.getMaxWorkId();
            this.dialogVisible = true;
        },
        // 添加员工信息时初始化下拉框数据
        initData() {
            // 民族
            if (!window.sessionStorage.getItem("nations")) {
                this.getJsonReq("/emp/basic/nations").then(response => {
                    this.nations = response;
                    window.sessionStorage.setItem("nations", JSON.stringify(response));
                });
            } else {
                this.nations = JSON.parse(window.sessionStorage.getItem("nations"));
            }

            // 职称
            if (!window.sessionStorage.getItem("jobLevels")) {
                this.getJsonReq("/emp/basic/jobLevels").then(response => {
                    if (response) {
                        this.jobLevels = response;
                        window.sessionStorage.setItem("jobLevels", JSON.stringify(response));
                    }
                });
            } else {
                this.jobLevels = JSON.parse(window.sessionStorage.getItem("jobLevels"));
            }

            // 政治面貌
            if (!window.sessionStorage.getItem("politicsStatus")) {
                this.getJsonReq("/emp/basic/politicsStatus").then(response => {
                    if (response) {
                        this.politicsStatus = response;
                        window.sessionStorage.setItem("politicsStatus", JSON.stringify(response));
                    }
                });
            } else {
                this.politicsStatus = JSON.parse(window.sessionStorage.getItem("politicsStatus"));
            }

            // 部门
            if (!window.sessionStorage.getItem("departments")) {
                this.getJsonReq("/emp/basic/departments").then(response => {
                    if (response) {
                        this.allDepartments = response;
                        window.sessionStorage.setItem("departments", JSON.stringify(response));
                    }
                });
            } else {
                this.allDepartments = JSON.parse(window.sessionStorage.getItem("departments"));
            }
        },
        // 初始化职位
        initPosition() {
            // 职位
            if (!window.sessionStorage.getItem("positions")) {
                this.getJsonReq("/emp/basic/position").then(response => {
                    this.positions = response;
                });
            }
        },
        // 获取最小可用 workId
        getMaxWorkId() {
            this.getJsonReq("/emp/basic/maxWorkId").then(response => {
                if (response) {
                    this.employee.workId = response.object;
                }
            });
        },
        // 员工添加页面获取部门目录树
        showDepartmentsView() {
            this.popVisible = !this.popVisible;
        },
        // 部门目录树点击事件
        handleNodeClick(data) {
            this.employee.departmentId = data.id;
            this.selectedDepartment = data.name;
            this.popVisible = !this.popVisible;
        },
        // 添加员工信息
        addEmployeeSubmit() {
            if (this.employee.id) {
                // 更新
                this.$refs['employeeForm'].validate(validate => {
                    if (validate) {
                        this.putJsonReq("/emp/basic/", this.employee).then(response => {
                            if (response) {
                                this.dialogVisible = false;
                                this.initEmployees();
                            }
                        });
                    }
                });
            } else {
                // 添加
                this.$refs['employeeForm'].validate(validate => {
                    if (validate) {
                        this.postJsonReq("/emp/basic/", this.employee).then(response => {
                            if (response) {
                                this.dialogVisible = false;
                                this.initEmployees();
                            }
                        });
                    }
                });
            }
        },
        // 删除员工信息
        deleteEmployee(data) {
            this.$confirm('此操作将永久删除【' + data.name + '】, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.deleteJsonReq("/emp/basic/" + data.id).then(response => {
                    if (response) {
                        this.initEmployees();
                    }
                });
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        // 编辑员工信息
        showEditEmployeeView(data) {
            this.initPosition();
            this.title = '编辑员工信息';
            this.employee = data;
            this.selectedDepartment = data.department.name;
            this.dialogVisible = true;
        },
        // 清空变量数据
        clearEmployee() {
            this.employee = {
                name: "",
                gender: "",
                birthday: "",
                idCard: "",
                wedlock: "",
                nationId: null,
                nativePlace: "",
                politicId: null,
                email: "",
                phone: "",
                address: "",
                departmentId: null,
                jobLevelId: null,
                posId: null,
                engageForm: "",
                tipTopDegree: "",
                specialty: "",
                school: "",
                beginDate: "",
                // workState: "",
                workId: "",
                contractTerm: null,
                conversionTime: "",
                notworkDate: null,
                beginContract: "",
                endContract: "",
                workAge: null
            };
            this.selectedDepartment = '';
        },
        // 导出 Excel 数据
        exportData() {
            window.open("/emp/basic/export", "_parent")
        },
        // 文件上传前
        beforeUpload() {
            this.importDataButtonText = '正在导入...';
            this.importDataButtonIcon = 'el-icon-loading';
            this.importDisabled = true;
        },
        // 文件导入成功
        onSuccess(response, file, fileList) {
            this.importDataButtonText = '导入数据';
            this.importDataButtonIcon = 'el-icon-upload2';
            this.importDisabled = false;
            this.initEmployees();
        },
        // 文件导入失败
        onError(error, file, fileList) {
            this.importDataButtonText = '导入数据';
            this.importDataButtonIcon = 'el-icon-upload2';
            this.importDisabled = false;
        }
    }
}
</script>

<style scoped>
.functionButton {
    padding: 3px;
}

.departmentSelectDiv {
    display: inline-flex;
    width: 150px;
    height: 26px;
    border: 1px solid #dedede;
    border-radius: 5px;
    font-size: 13px;
    cursor: pointer;
    align-items: center;
    padding-left: 14px;
    box-sizing: border-box;
}

.advancedSearch {
    border: 1px solid #409eff;
    border-radius: 5px;
    box-sizing: border-box;
    padding: 5px;
    margin: 10px;
}

/* 设置不同进入和离开动画 */
/* 设置持续时间和动画函数 */
.slide-fade-enter-active {
    transition: all .3s ease;
}

.slide-fade-leave-active {
    transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}

.slide-fade-enter, .slide-fade-leave-to {
    transform: translateX(10px);
    opacity: 0;
}
</style>