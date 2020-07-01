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
            <el-button icon="el-icon-plus" size="small" type="primary">添加职称</el-button>
        </div>
        <div style="margin-top: 10px">
            <el-table
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
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button
                                size="mini">编辑
                        </el-button>
                        <el-button
                                size="mini"
                                type="danger">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
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
            titleLevels: [
                '正高级',
                '副高级',
                '中级',
                '初级',
                '原级'
            ]
        }
    }
}
</script>

<style scoped>

</style>