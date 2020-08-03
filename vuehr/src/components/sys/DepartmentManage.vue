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
            }
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
                    console.log(response);
                    this.departments = response;
                }
            })
        },
        // 展示添加部门信息的对话框
        showAddDepartmentView(data) {
            console.log(data);
        },
        // 删除部门
        deleteDepartment(data) {
            console.log(data);
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