<template>
  <div class="agent-flow-list">
    <div class="header">
      <div class="title">智能体流程</div>
      <el-button type="primary" @click="createFlow">
        <el-icon><Plus /></el-icon>
        创建智能体流程
      </el-button>
    </div>

    <div class="search-bar">
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="流程名称">
          <el-input
            v-model="searchForm.flowName"
            placeholder="请输入流程名称"
            style="width: 200px"
            @keyup.enter="handleSearch"
          />
        </el-form-item>
        <el-form-item label="流程状态">
          <el-select
            v-model="searchForm.status"
            placeholder="请选择流程状态"
            style="width: 120px"
            @change="handleSearch"
          >
            <el-option label="全部" value="" />
            <el-option label="启用" value="1" />
            <el-option label="停用" value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">
            <el-icon><Search /></el-icon>
            搜索
          </el-button>
          <el-button @click="handleReset">
            <el-icon><Refresh /></el-icon>
            重置
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="table-container">
      <el-table
        v-loading="loading"
        :data="flowList"
        stripe
        border
        style="width: 100%"
      >
        <el-table-column prop="flowName" label="流程名称" min-width="180" />
        <el-table-column prop="uniqueId" label="唯一标识" min-width="150" />
        <el-table-column prop="description" label="流程描述" min-width="200" show-overflow-tooltip />
        <el-table-column prop="status" label="状态" min-width="80">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
              {{ scope.row.status === 1 ? '启用' : '停用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="isMain" label="是否主流程" min-width="100">
          <template #default="scope">
            <el-tag :type="scope.row.isMain === 1 ? 'primary' : 'info'">
              {{ scope.row.isMain === 1 ? '是' : '否' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" min-width="160" />
        <el-table-column prop="updateTime" label="更新时间" min-width="160" />
        <el-table-column label="操作" min-width="200" fixed="right">
          <template #default="scope">
            <el-button
              type="primary"
              size="small"
              @click="editFlow(scope.row)"
              v-hasPermi="['agentFlow:edit']"
            >
              编辑
            </el-button>
            <el-button
              size="small"
              @click="viewDetail(scope.row)"
              v-hasPermi="['agentFlow:view']"
            >
              查看
            </el-button>
            <el-button
              size="small"
              type="success"
              @click="executeFlow(scope.row)"
              v-hasPermi="['agentFlow:execute']"
              :disabled="scope.row.status !== 1"
            >
              执行
            </el-button>
            <el-button
              size="small"
              type="warning"
              @click="toggleStatus(scope.row)"
              v-hasPermi="['agentFlow:status']"
            >
              {{ scope.row.status === 1 ? '停用' : '启用' }}
            </el-button>
            <el-button
              size="small"
              type="danger"
              @click="deleteFlow(scope.row)"
              v-hasPermi="['agentFlow:delete']"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-if="total > 0"
        class="pagination"
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pagination.pageNum"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pagination.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>

    <!-- 创建/编辑智能体流程对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="900px"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <AgentFlowCreate
        ref="agentFlowCreateRef"
        :flow-id="currentFlowId"
        :read-only="readOnly"
        @success="handleCreateSuccess"
        @cancel="dialogVisible = false"
      />
    </el-dialog>

    <!-- 执行智能体流程对话框 -->
    <el-dialog
      v-model="executeDialogVisible"
      title="执行智能体流程"
      width="600px"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <AgentFlowExecute
        ref="agentFlowExecuteRef"
        :flow-id="currentExecuteFlowId"
        @success="handleExecuteSuccess"
        @cancel="executeDialogVisible = false"
      />
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { Plus, Search, Refresh } from '@element-plus/icons-vue';
import AgentFlowCreate from '../../components/agentFlow/AgentFlowCreate.vue';
import AgentFlowExecute from '../../components/agentFlow/AgentFlowExecute.vue';
import {
  queryAgentProcessInstanceList,
  disableAgentFlow,
  enableAgentFlow,
  deleteAgentFlow
} from '../../api/agentFlow';

const loading = ref(false);
const flowList = ref<any[]>([]);
const total = ref(0);
const dialogVisible = ref(false);
const executeDialogVisible = ref(false);
const dialogTitle = ref('');
const currentFlowId = ref('');
const currentExecuteFlowId = ref('');
const readOnly = ref(false);
const agentFlowCreateRef = ref<any>(null);
const agentFlowExecuteRef = ref<any>(null);

const searchForm = ref({
  flowName: '',
  status: ''
});

const pagination = ref({
  pageNum: 1,
  pageSize: 10
});

// 获取智能体流程列表
const getAgentFlowList = async () => {
  loading.value = true;
  try {
    const params = {
      ...searchForm.value,
      pageNum: pagination.value.pageNum,
      pageSize: pagination.value.pageSize
    };
    const response = await queryAgentProcessInstanceList(params);
    if (response.code === 200) {
      flowList.value = response.data.records || [];
      total.value = response.data.total || 0;
    } else {
      ElMessage.error('获取智能体流程列表失败：' + response.message);
    }
  } catch (error) {
    ElMessage.error('获取智能体流程列表失败');
    console.error('获取智能体流程列表失败：', error);
  } finally {
    loading.value = false;
  }
};

// 搜索
const handleSearch = () => {
  pagination.value.pageNum = 1;
  getAgentFlowList();
};

// 重置
const handleReset = () => {
  searchForm.value = {
    flowName: '',
    status: ''
  };
  pagination.value.pageNum = 1;
  getAgentFlowList();
};

// 分页大小改变
const handleSizeChange = (val: number) => {
  pagination.value.pageSize = val;
  pagination.value.pageNum = 1;
  getAgentFlowList();
};

// 当前页改变
const handleCurrentChange = (val: number) => {
  pagination.value.pageNum = val;
  getAgentFlowList();
};

// 创建智能体流程
const createFlow = () => {
  dialogTitle.value = '创建智能体流程';
  currentFlowId.value = '';
  readOnly.value = false;
  dialogVisible.value = true;
};

// 编辑智能体流程
const editFlow = (row: any) => {
  dialogTitle.value = '编辑智能体流程';
  currentFlowId.value = row.id;
  readOnly.value = false;
  dialogVisible.value = true;
};

// 查看智能体流程详情
const viewDetail = (row: any) => {
  dialogTitle.value = '查看智能体流程详情';
  currentFlowId.value = row.id;
  readOnly.value = true;
  dialogVisible.value = true;
};

// 执行智能体流程
const executeFlow = (row: any) => {
  currentExecuteFlowId.value = row.id;
  executeDialogVisible.value = true;
};

// 切换智能体流程状态
const toggleStatus = async (row: any) => {
  try {
    const message = row.status === 1 ? '确定要停用该智能体流程吗？' : '确定要启用该智能体流程吗？';
    await ElMessageBox.confirm(message, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    });

    if (row.status === 1) {
      // 停用
      await disableAgentFlow(row.id);
      ElMessage.success('智能体流程停用成功');
    } else {
      // 启用
      await enableAgentFlow(row.id);
      ElMessage.success('智能体流程启用成功');
    }

    getAgentFlowList();
  } catch (error: any) {
    if (error !== 'cancel') {
      ElMessage.error('操作失败：' + (error.message || '未知错误'));
    }
  }
};

// 删除智能体流程
const deleteFlow = async (row: any) => {
  try {
    await ElMessageBox.confirm('确定要删除该智能体流程吗？删除后不可恢复！', '警告', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'error'
    });

    await deleteAgentFlow(row.id);
    ElMessage.success('智能体流程删除成功');
    getAgentFlowList();
  } catch (error: any) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败：' + (error.message || '未知错误'));
    }
  }
};

// 创建/编辑成功回调
const handleCreateSuccess = () => {
  dialogVisible.value = false;
  getAgentFlowList();
};

// 执行成功回调
const handleExecuteSuccess = () => {
  executeDialogVisible.value = false;
  getAgentFlowList();
};

onMounted(() => {
  getAgentFlowList();
});
</script>

<style scoped>
.agent-flow-list {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.title {
  font-size: 20px;
  font-weight: 600;
  color: #303133;
}

.search-bar {
  margin-bottom: 20px;
}

.search-form {
  background: #f5f7fa;
  padding: 15px;
  border-radius: 8px;
}

.table-container {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
}

.pagination {
  margin-top: 20px;
  text-align: right;
}
</style>
