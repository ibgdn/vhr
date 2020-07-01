<template>
    <div>
        <div>
            <el-input size="small" v-model="jobLevel.name" style="width: 300px" prefix-icon="el-icon-plus"
                      placeholder="请输入要添加的职称名称"></el-input>
            <el-select v-model="jobLevel.titleLevel" placeholder="职称等级" size="small"
                       style="margin-left: 5px; margin-right: 5px">
                <el-option
                        v-for="item in titleLevels"
                        :key="item"
                        :label="item"
                        :value="item">
                </el-option>
            </el-select>
            <el-button icon="el-icon-plus" size="small" type="primary" @click="addJobLevel">添加职称</el-button>
        </div>
        <div style="margin-top: 10px">
            <el-table
                    :data="jobLevels"
                    size="small"
                    border
                    stripe
                    style="width: 70%">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="id"
                        label="编号"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="职称名称"
                        width="160">
                </el-table-column>
                <el-table-column
                        prop="titleLevel"
                        label="职称级别"
                        width="160">
                </el-table-column>
                <el-table-column
                        prop="createDate"
                        label="创建时间"
                        width="160">
                </el-table-column>
                <el-table-column
                        label="是否启用"
                        width="160">
                    <template slot-scope="scope">
                        <el-tag type="success" v-if="scope.row.enabled">已启用</el-tag>
                        <el-tag type="danger" v-else>未启用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button
                                @click="showEditView(scope.row)"
                                size="mini">编辑
                        </el-button>
                        <el-button
                                @click="deleteJobLevel(scope.row)"
                                size="mini"
                                type="danger">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <el-dialog
                title="职称修改"
                :visible.sync="dialogVisible"
                width="30%">
            <div>
                <table>
                    <tr>
                        <td>
                            <el-tag>职称名</el-tag>
                        </td>
                        <td>
                            <el-input size="small" v-model="updatedJobLevel.name"></el-input>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <el-tag>职称级别</el-tag>
                        </td>
                        <td>
                            <el-select v-model="updatedJobLevel.titleLevel" placeholder="职称等级" size="small"
                                       style="margin-left: 5px; margin-right: 5px">
                                <el-option
                                        v-for="item in titleLevels"
                                        :key="item"
                                        :label="item"
                                        :value="item">
                                </el-option>
                            </el-select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <el-tag>是否启用</el-tag>
                        </td>
                        <td>
                            <el-switch v-model="updatedJobLevel.enabled"
                                       active-text="启用" inactive-text="未启用">
                            </el-switch>
                        </td>
                    </tr>
                </table>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button size="small" @click="dialogVisible = false">取 消</el-button>
                <el-button size="small" type="primary" @click="updateJobLevel">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "JobLevelManage",
    data() {
        return {
            jobLevel: {
                name: '',
                titleLevel: ''
            },
            jobLevels: [],
            titleLevels: [
                '正高级',
                '副高级',
                '中级',
                '初级',
                '原级'
            ],
            dialogVisible: false,
            updatedJobLevel: {
                name: '',
                titleLevel: '',
                enabled: false
            }
        }
    },
    // mounted 方法
    mounted() {
        this.initJobLevels();
    },
    // methods 属性
    methods: {
        // 初始化职级
        initJobLevels() {
            this.getJsonReq("/system/basic/jobLevel/").then(response => {
                if (response) {
                    this.jobLevels = response;
                }
            })
        },
        // 添加职级信息
        addJobLevel() {
            if (this.jobLevel.name && this.jobLevel.titleLevel) {
                this.postJsonReq("/system/basic/jobLevel/", this.jobLevel).then(response => {
                    if (response) {
                        this.initJobLevels();
                        // 正常添加职级信息后，清空录入框中的职级信息
                        this.jobLevel = {
                            name: '',
                            titleLevel: ''
                        };
                    }
                })
            } else {
                this.$message.error("职级名称或职级等级不可以为空！");
            }
        },
        // 展示职称编辑窗口
        showEditView(data) {
            Object.assign(this.updatedJobLevel, data);
            this.dialogVisible = true;
        },
        // 职称编辑窗口点击“确定”按钮
        updateJobLevel(data) {
            this.putJsonReq("/system/basic/jobLevel/", this.updatedJobLevel).then(response => {
                if (response) {
                    this.initJobLevels();
                    this.dialogVisible = false;
                }
            })
        },
        // 删除职级信息
        deleteJobLevel(data) {
            this.$confirm('此操作将永久删除【' + data.name + '】职称, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.deleteJsonReq("/system/basic/jobLevel/" + data.id).then(response => {
                    if (response) {
                        this.initJobLevels();
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        }
    }
}
</script>

<style scoped>

</style>