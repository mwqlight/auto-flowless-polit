<template>
  <div class="agent-flow-instance">
    <div class="header">
      <div class="title">智能体流程实例</div>
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
        <el-form-item label="实例状态">
          <el-select
            v-model="searchForm.status"
            placeholder="请选择实例状态"
            style="width: 120px"
            @change="handleSearch"
          >
            <el-option label="全部" value="" />
            <el-option label="运行中" value="running" />
            <el-option label="已完成" value="completed" />
            <el-option label="已终止" value="terminated" />
            <el-option label="失败" value="failed" />
          </el-select>
        </el-form-item>
        <el-form-item label="执行时间">
          <el-date-picker
            v-model="searchForm.executionTime"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            @change="handleSearch"
          />
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
        :data="instanceList"
        stripe
        border
        style="width: 100%"
      >
        <el-table-column prop="processInstanceId" label="实例ID" min-width="180" />
        <el-table-column prop="flowName" label="流程名称" min-width="150" />
        <el-table-column prop="flowId" label="流程ID" min-width="150" />
        <el-table-column prop="status" label="状态" min-width="100">
          <template #default="scope">
            <el-tag
              :type="getStatusTagType(scope.row.status)"
            >
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="initiator" label="发起人" min-width="100" />
        <el-table-column prop="executionTime" label="执行时间" min-width="160" />
        <el-table-column prop="completionTime" label="完成时间" min-width="160" />
        <el-table-column prop="duration" label="执行时长" min-width="120" />
        <el-table-column prop="result" label="执行结果" min-width="120">
          <template #default="scope">
            <el-tag
              :type="scope.row.result === 'success' ? 'success' : 'danger'"
            >
              {{ scope.row.result === 'success' ? '成功' : '失败' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" min-width="150" fixed="right">
          <template #default="scope">
            <el-button
              size="small"
              @click="viewDetail(scope.row)"
              v-hasPermi="['agentFlowInstance:view']"
            >
              查看详情
            </el-button>
            <el-button
              size="small"
              type="warning"
              @click="terminateInstance(scope.row)"
              v-hasPermi="['agentFlowInstance:terminate']"
              :disabled="scope.row.status !== 'running'"
            >
              终止
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

    <!-- 实例详情对话框 -->
    <el-dialog
      v-model="detailDialogVisible"
      title="智能体流程实例详情"
      width="800px"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <AgentFlowInstanceDetail
        ref="instanceDetailRef"
        :process-instance-id="currentInstanceId"
        @close="detailDialogVisible = false"
      />
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { Search, Refresh } from '@element-plus/icons-vue';
import AgentFlowInstanceDetail from '../../components/agentFlow/AgentFlowInstanceDetail.vue';
import { queryAgentProcessInstanceList } from '../../api/agentFlow';

const loading = ref(false);
const instanceList = ref<any[]>([]);
const total = ref(0);
const detailDialogVisible = ref(false);
const currentInstanceId = ref('');
const instanceDetailRef = ref<any>(null);

const searchForm = ref({
  flowName: '',
  status: '',
  executionTime: [] as Date[]
});

const pagination = ref({
  pageNum: 1,
  pageSize: 10
});

// 获取状态标签类型
const getStatusTagType = (status: string) => {
  switch (status) {
    case 'running':
      return 'warning';
    case 'completed':
      return 'success';
    case 'terminated':
      return 'info';
    case 'failed':
      return 'danger';
    default:
      return 'info';
  }
};

// 获取状态文本
const getStatusText = (status: string) => {
  switch (status) {
    case 'running':
      return '运行中';
    case 'completed':
      return '已完成';
    case 'terminated':
      return '已终止';
    case 'failed':
      return '失败';
    default:
      return status;
  }
};

// 获取智能体流程实例列表
const getAgentFlowInstanceList = async () => {
  loading.value = true;
  try {
    const params = {
      ...searchForm.value,
      pageNum: pagination.value.pageNum,
      pageSize: pagination.value.pageSize
    };
    const response = await queryAgentProcessInstanceList(params);
    if (response.code === 200) {
      instanceList.value = response.data.records || [];
      total.value = response.data.total || 0;
    } else {
      ElMessage.error('获取智能体流程实例列表失败：' + response.message);
    }
  } catch (error) {
    ElMessage.error('获取智能体流程实例列表失败');
    console.error('获取智能体流程实例列表失败：', error);
  } finally {
    loading.value = false;
  }
};

// 搜索
const handleSearch = () => {
  pagination.value.pageNum = 1;
  getAgentFlowInstanceList();
};

// 重置
const handleReset = () => {
  searchForm.value = {
    flowName: '',
    status: '',
    executionTime: []
  };
  pagination.value.pageNum = 1;
  getAgentFlowInstanceList();
};

// 分页大小改变
const handleSizeChange = (val: number) => {
  pagination.value.pageSize = val;
  pagination.value.pageNum = 1;
  getAgentFlowInstanceList();
};

// 当前页改变
const handleCurrentChange = (val: number) => {
  pagination.value.pageNum = val;
  getAgentFlowInstanceList();
};

// 查看实例详情
const viewDetail = (row: any) => {
  currentInstanceId.value = row.processInstanceId;
  detailDialogVisible.value = true;
};

// 终止实例
const terminateInstance = async (row: any) => {
  try {
    await ElMessageBox.confirm('确定要终止该智能体流程实例吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    });

    // TODO: 调用终止实例的API
    ElMessage.success('智能体流程实例终止成功');
    getAgentFlowInstanceList();
  } catch (error: any) {
    if (error !== 'cancel') {
      ElMessage.error('终止失败：' + (error.message || '未知错误'));
    }
  }
};

onMounted(() => {
  getAgentFlowInstanceList();
});
</script>

<style scoped>
.agent-flow-instance {
  padding: 20px;
}

.header {
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
