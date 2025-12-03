<template>
  <div class="agent-flow-list">
    <div class="page-header">
      <el-text tag="b" size="large" type="primary">智能体流程列表</el-text>
      <div class="header-actions">
        <el-button :icon="Plus" type="primary" @click="createAgentFlow">创建智能体流程</el-button>
      </div>
    </div>

    <div class="filter-section">
      <el-input
        v-model="filterParams.name"
        placeholder="请输入智能体流程名称"
        clearable
        style="width: 300px; margin-right: 10px;"
      >
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>

      <el-select
        v-model="filterParams.status"
        placeholder="请选择状态"
        clearable
        style="width: 150px; margin-right: 10px;"
      >
        <el-option label="草稿" value="draft" />
        <el-option label="已发布" value="published" />
        <el-option label="已停用" value="disabled" />
      </el-select>

      <el-button :icon="Refresh" @click="refreshList">刷新</el-button>
    </div>

    <div class="flow-list">
      <el-table
        v-loading="loading"
        :data="agentFlowList"
        style="width: 100%"
        @row-click="handleRowClick"
      >
        <el-table-column prop="name" label="智能体流程名称" min-width="200" />
        <el-table-column prop="description" label="描述" min-width="300" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusTagType(row.status)">{{ getStatusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column prop="updateTime" label="更新时间" width="180" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button size="small" @click="editAgentFlow(row.id)">编辑</el-button>
            <el-button size="small" @click="viewAgentFlow(row.id)">查看</el-button>
            <el-button size="small" @click="deleteAgentFlow(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
          v-model:current-page="pagination.current"
          v-model:page-size="pagination.size"
          :total="pagination.total"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue";
import { Plus, Search, Refresh } from "@element-plus/icons-vue";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";

// 导入API函数
import {
  getListByUniqueId,
  deleteFlow
} from "../../api/flow/index";

const router = useRouter();

// 加载状态
const loading = ref(false);

// 智能体流程列表数据
const agentFlowList = ref([]);

// 筛选参数
const filterParams = ref({
  name: '',
  status: ''
});

// 分页参数
const pagination = ref({
  current: 1,
  size: 10,
  total: 0
});

// 页面加载时获取智能体流程列表
onMounted(() => {
  fetchAgentFlowList();
});

// 获取智能体流程列表
const fetchAgentFlowList = () => {
  loading.value = true;

  // 目前后端API没有提供分页查询智能体流程列表的接口
  // 暂时使用根据唯一标识查询流程列表的接口，后续需要后端补充分页查询接口
  getListByUniqueId('').then((res) => {
    if (res.code === 200) {
      agentFlowList.value = res.data || [];
      pagination.value.total = agentFlowList.value.length;
    } else {
      ElMessage.error(res.message || '获取智能体流程列表失败');
    }
  }).catch((error) => {
    ElMessage.error('获取智能体流程列表失败：' + error.message);
  }).finally(() => {
    loading.value = false;
  });
};

// 刷新列表
const refreshList = () => {
  fetchAgentFlowList();
};

// 处理页面大小变化
const handleSizeChange = (size: number) => {
  pagination.value.size = size;
  pagination.value.current = 1;
  fetchAgentFlowList();
};

// 处理当前页面变化
const handleCurrentChange = (current: number) => {
  pagination.value.current = current;
  fetchAgentFlowList();
};

// 创建智能体流程
const createAgentFlow = () => {
  router.push('/agent-flow/create');
};

// 编辑智能体流程
const editAgentFlow = (id: string) => {
  router.push(`/agent-flow/create?id=${id}`);
};

// 查看智能体流程
const viewAgentFlow = (id: string) => {
  router.push(`/agent-flow/view?id=${id}`);
};

// 删除智能体流程
const deleteAgentFlow = (id: string) => {
  ElMessageBox.confirm(
    '确定要删除该智能体流程吗？',
    '删除确认',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(() => {
    deleteFlow(id).then((res) => {
      if (res.code === 200) {
        ElMessage.success('智能体流程删除成功');
        fetchAgentFlowList();
      } else {
        ElMessage.error(res.message || '智能体流程删除失败');
      }
    }).catch((error) => {
      ElMessage.error('智能体流程删除失败：' + error.message);
    });
  }).catch(() => {
    // 用户取消删除
  });
};

// 处理行点击事件
const handleRowClick = (row: any) => {
  // 点击行时跳转到智能体流程详情页
  viewAgentFlow(row.id);
};

// 获取状态标签类型
const getStatusTagType = (status: string) => {
  switch (status) {
    case 'published':
      return 'success';
    case 'draft':
      return 'warning';
    case 'disabled':
      return 'danger';
    default:
      return 'info';
  }
};

// 获取状态文本
const getStatusText = (status: string) => {
  switch (status) {
    case 'published':
      return '已发布';
    case 'draft':
      return '草稿';
    case 'disabled':
      return '已停用';
    default:
      return '未知';
  }
};
</script>

<style scoped>
.agent-flow-list {
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.header-actions {
  display: flex;
  align-items: center;
}

.filter-section {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  padding: 15px;
  background-color: #f5f7fa;
  border-radius: 4px;
}

.flow-list {
  background-color: #fff;
  border-radius: 4px;
  overflow: hidden;
}

.pagination {
  padding: 20px;
  display: flex;
  justify-content: center;
}
</style>