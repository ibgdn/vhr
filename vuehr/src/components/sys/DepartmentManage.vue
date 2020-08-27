<template>
    <div style="width: 500px">
        <el-input
            placeholder="输入部门名称进行搜索"
            prefix-icon="el-icon-search"
            v-model="filterText">
        </el-input>

        <el-tree
            :data="departments"
            :props="defaultProps"
            :filter-node-method="filterNode"
            :expand-on-click-node="false"
            ref="tree">
            <span class="custom-tree-node" slot-scope="{ node, data }">
                <span>{{ data.name }}</span>
                <span>
                    <el-button
                        class="departmentBtn"
                        type="primary"
                        size="mini"
                        @click="() => showAddDepartmentView(data)">
                        添加部门
                    </el-button>
                    <el-button
                        class="departmentBtn"
                        type="danger"
                        size="mini"
                        @click="() => deleteDepartment(data)">
                        删除部门
                    </el-button>
                </span>
            </span>
        </el-tree>
        <el-dialog
            title="添加部门"
            :visible.sync="dialogVisible"
            width="30%">
            <div>
                <table>
                    <tr>
                        <td>
                            <el-tag>上级部门</el-tag>
                        </td>
                        <td>{{ depParentName }}</td>
                    </tr>
                    <tr>
                        <td>
                            <el-tag>待添加部门名称</el-tag>
                        </td>
                        <td>
                            <el-input v-model="department.name" placeholder="请输入要添加的部门名称"></el-input>
                        </td>
                    </tr>
                </table>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addDepartment">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "DepartmentManage",
    data() {
        return {
            // 顶部搜索栏录入数据
            filterText: '',
            // 部门信息
            departments: [],
            // 树形展示数据
            defaultProps: {
                children: 'departmentChildren',
                label: 'name'
            },
            // 添加部门的弹出窗口默认不可见
            dialogVisible: false,
            // 部门信息
            department: {
                name: '',
                parentId: -1
            },
            // 上级部门名称
            depParentName: '',
        }
    },
    watch: {
        filterText(val) {
            this.$refs.tree.filter(val);
        }
    },
    mounted() {
        this.initDepartments();
    },
    methods: {
        filterNode(value, data) {
            // value（筛选名称）清空时，相关级别目录树会打开
            if (!value) return true;
            return data.name.indexOf(value) !== -1;
        },
        // 初始化部门信息数据
        initDepartments() {
            this.getJsonReq("/system/basic/department/").then(response => {
                if (response) {
                    this.departments = response;
                }
            })
        },
        // 展示添加部门信息的对话框
        showAddDepartmentView(data) {
            this.depParentName = data.name;
            this.department.parentId = data.id;
            this.dialogVisible = true;
        },
        // 清除部门添加弹窗信息
        clearAddDepartmentDialog() {
            this.department = {
                name: '',
                parentId: -1
            };
            this.depParentName = '';
        },
        // 添加部门后自动切换到对应部门父级（不折叠）
        addDepartment2Departments(departments, object) {
            for (let i = 0; i < departments.length; i++) {
                let department = departments[i];
                if (department.id === object.parentId) {
                    department.departmentChildren = department.departmentChildren.concat(object);
                    if (department.departmentChildren.left > 0) {
                        department.parent = true;
                    }
                    return;
                } else {
                    this.addDepartment2Departments(department.departmentChildren, object);
                }
            }
        },
        // 添加部门
        addDepartment() {
            this.postJsonReq("/system/basic/department/", this.department).then(response => {
                this.addDepartment2Departments(this.departments, response.object);
                this.dialogVisible = false;
                this.clearAddDepartmentDialog();
            })
        },
        // 删除部门
        deleteDepartment(data) {
            if (data.parent) {
                this.$message.error("该部门" + data.name + "存在子部门，删除失败");
            } else {
                this.$confirm('此操作将永久删除【' + data.name + '】部门, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteJsonReq("/system/basic/department/" + data.id).then(response => {
                        if (response) {
                            this.removeDepartmentFromDepartments(null, this.departments, data.id);
                        }
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            }
        },
        // 在当前目录树中移除已经删除的部门
        removeDepartmentFromDepartments(part, departments, id) {
            for (let i = 0; i < departments.length; i++) {
                let department = departments[i];
                if (department.id === id) {
                    // 从索引 i 的位置，删除1个
                    departments.splice(i, 1);
                    if (departments.length === 0) {
                        part.parent = false;
                    }
                    return;
                } else {
                    this.removeDepartmentFromDepartments(department, department.departmentChildren, id);
                }
            }
        },
    }
}
</script>

<style>
.departmentBtn {
    padding: 2px;
}

.custom-tree-node {
    display: flex;
    justify-content: space-between;
    width: 100%;
}
</style>