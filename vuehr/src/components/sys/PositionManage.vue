<template>
    <div>
        <div>
            <el-input class="addPositionInput"
                      size="small"
                      placeholder="请输入要添加职位名称"
                      prefix-icon="el-icon-plus"
                      v-model="position.name">
            </el-input>
            <el-button icon="el-icon-plus" size="small" type="primary">
                添加
            </el-button>
        </div>
        <div class="positionManageTable">
            <el-table
                    :data="positions"
                    size="small"
                    border
                    stripe
                    style="width: 70%">
                <el-table-column
                        prop="id"
                        label="编号"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="职位名称"
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="createDate"
                        label="创建时间">
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
export default {
    name: "PositionManage",
    data() {
        return {
            position: {
                name: ''
            },
            positions: []
        }
    }
}
</script>

<style>
.addPositionInput {
    width: 300px;
    margin-right: 10px
}

.positionManageTable {
    margin-top: 10px;
}
</style>