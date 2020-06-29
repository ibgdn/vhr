<template>
    <div>
        <div>
            <el-input class="addPositionInput"
                      size="small"
                      placeholder="请输入要添加职位名称"
                      prefix-icon="el-icon-plus"
                      @keydown.enter.native="addPosition()"
                      v-model="position.name">
            </el-input>
            <el-button icon="el-icon-plus" size="small" type="primary" @click="addPosition">
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
                        label="职位名称"
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
                                size="mini"
                                @click="showPositionEditDialog(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="handleDelete(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <el-dialog
                title="职位修改"
                :visible.sync="dialogVisible"
                width="30%">
            <div>
                <el-tag>职位名称：</el-tag>
                <el-input class="updatePositionInput" size="small" v-model="updatePosition.name"></el-input>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button size="small" @click="dialogVisible = false">取 消</el-button>
                <el-button size="small" type="primary" @click="handleEdit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import {putJsonRequest} from "../../utils/api";

export default {
    name: "PositionManage",
    data() {
        return {
            position: {
                name: ''
            },
            positions: [],
            dialogVisible: false,
            updatePosition: {
                name: ''
            }
        }
    },
    mounted() {
        // 钩子方法
        this.initPositions();
    },
    methods: {
        // 初始化时调用该方法
        initPositions() {
            this.getJsonReq("/system/basic/position/").then(response => {
                if (response) {
                    this.positions = response;
                }
            })
        },
        // 添加按钮
        addPosition() {
            if (this.position.name) {
                this.postJsonReq("/system/basic/position/", this.position).then(response => {
                    if (response) {
                        // 正常调用添加接口，刷新页面数据，并清空录入信息
                        this.initPositions();
                        this.position.name = '';
                    }
                })
            } else {
                this.$message.error('职位名称不可以为空');
            }
        },
        // 显示编辑职位名称对话框
        showPositionEditDialog(index, data) {
            // this.updatePosition = data;
            // 编辑过程中，页面展示名称不随录入框的修改而改变
            Object.assign(this.updatePosition, data);
            this.dialogVisible = true;
        },
        // 编辑按钮
        handleEdit() {
            putJsonRequest("/system/basic/position/", this.updatePosition).then(response => {
                if (response) {
                    this.initPositions();
                    this.updatePosition.name = '';
                    // 隐藏对话框
                    this.dialogVisible = false;
                }
            })
        },
        // 删除按钮
        handleDelete(index, data) {
            this.$confirm('此操作将永久删除【' + data.name + '】职位, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.deleteJsonReq("/system/basic/position/" + data.id).then(response => {
                    if (response) {
                        this.initPositions();
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

<style>
.addPositionInput {
    width: 300px;
    margin-right: 10px
}

.positionManageTable {
    margin-top: 10px;
}

.updatePositionInput {
    width: 200px;
    margin-left: 10px;
}
</style>