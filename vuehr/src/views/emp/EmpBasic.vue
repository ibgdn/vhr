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
        <div></div>
    </div>
</template>

<script>
export default {
    name: "EmpBasic"
}
</script>

<style scoped>

</style>