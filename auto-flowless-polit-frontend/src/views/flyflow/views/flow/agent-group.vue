<template>
  <div class="agent-flow-group">
    <div class="page-header">
      <el-text tag="b" size="large" type="primary">智能体流程组</el-text>
      <div class="header-actions">
        <el-button :icon="Plus" type="primary" @click="createAgentGroup">创建智能体流程组</el-button>
      </div>
    </div>

    <div class="filter-section">
      <el-input
        v-model="filterParams.name"
        placeholder="请输入智能体流程组名称"
        clearable
        style="width: 300px; margin-right: 10px;"
      >
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>

      <el-button :icon="Refresh" @click="refreshList">刷新</el-button>
    </div>

    <div class="group-list">
      <el-table
        v-loading="loading"
        :data="agentGroupList"
        style="width: 100%"
        @row-click="handleRowClick"
      >
        <el-table-column prop="name" label="智能体流程组名称" min-width="200" />
        <el-table-column prop="description" label="描述" min-width="300" />
        <el-table-column prop="flowCount" label="包含流程数" width="120" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column prop="updateTime" label="更新时间" width="180" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button size="small" @click="editAgentGroup(row.id)">编辑</el-button>
            <el-button size="small" @click="viewAgentGroup(row.id)">查看</el-button>
            <el-button size="small" @click="deleteAgentGroup(row.id)">删除</el-button>
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
import { ElMessage, ElMessageBox } from "element-plus";
import { useRouter } from "vue-router";

// 导入API函数
import {
  getAgentProcessGroupList,
  deleteAgentProcessGroup
} from "../../api/flow/index";

const router = useRouter();

// 加载状态
const loading = ref(false);

// 智能体流程组列表数据
const agentGroupList = ref([]);

// 筛选参数
const filterParams = ref({
  name: ''
});

// 分页参数
const pagination = ref({
  current: 1,
  size: 10,
  total: 0
});

// 页面加载时获取智能体流程组列表
onMounted(() => {
  fetchAgentGroupList();
});

// 获取智能体流程组列表
const fetchAgentGroupList = () => {
  loading.value = true;

  // 构建请求参数
  const requestParams = {
    // 后端API未提供分页查询接口，先传递空参数
  };

  getAgentProcessGroupList(requestParams).then((res) => {
    if (res.code === 200) {
      agentGroupList.value = res.data || [];
      pagination.value.total = agentGroupList.value.length; // 暂时使用列表长度作为总数
    } else {
      ElMessage.error(res.message || '获取智能体流程组列表失败');
    }
  }).catch((error) => {
    ElMessage.error('获取智能体流程组列表失败：' + error.message);
  }).finally(() => {
    loading.value = false;
  });
};

// 刷新列表
const refreshList = () => {
  fetchAgentGroupList();
};

// 处理页面大小变化
const handleSizeChange = (size: number) => {
  pagination.value.size = size;
  pagination.value.current = 1;
  fetchAgentGroupList();
};

// 处理当前页面变化
const handleCurrentChange = (current: number) => {
  pagination.value.current = current;
  fetchAgentGroupList();
};

// 创建智能体流程组
const createAgentGroup = () => {
  // 这里可以打开一个对话框来创建智能体流程组
  ElMessage.info('创建智能体流程组功能将在后续版本中实现');
};

// 编辑智能体流程组
const editAgentGroup = (id: string) => {
  // 这里可以打开一个对话框来编辑智能体流程组
  ElMessage.info('编辑智能体流程组功能将在后续版本中实现');
};

// 查看智能体流程组
const viewAgentGroup = (id: string) => {
  // 跳转到智能体流程组详情页，显示该组包含的所有智能体流程
  router.push(`/flyflow/flow/agent-group/${id}`);
};

// 删除智能体流程组
const deleteAgentGroup = (id: string) => {
  ElMessageBox.confirm(
    '确定要删除该智能体流程组吗？',
    '删除确认',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(() => {
    deleteAgentProcessGroup(id).then((res) => {
      if (res.code === 200) {
        ElMessage.success('智能体流程组删除成功');
        fetchAgentGroupList();
      } else {
        ElMessage.error(res.message || '智能体流程组删除失败');
      }
    }).catch((error) => {
      ElMessage.error('智能体流程组删除失败：' + error.message);
    });
  }).catch(() => {
    // 用户取消删除
  });
}; 

// 处理行点击事件
const handleRowClick = (row: any) => {
  // 点击行时跳转到智能体流程组详情页
  viewAgentGroup(row.id);
};
</script>

<style scoped>
.agent-flow-group {
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

.group-list {
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