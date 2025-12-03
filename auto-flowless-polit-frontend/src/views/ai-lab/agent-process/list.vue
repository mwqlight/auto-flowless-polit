<template>
  <div class="agent-process-list">
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <span>智能体流程列表</span>
          <el-button type="primary" @click="goToCreate">创建流程</el-button>
        </div>
      </template>
      
      <!-- 搜索表单 -->
      <el-form :model="searchForm" inline class="search-form">
        <el-form-item label="流程名称">
          <el-input v-model="searchForm.name" placeholder="请输入流程名称" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择状态">
            <el-option label="正常" value="0"></el-option>
            <el-option label="停用" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="search">搜索</el-button>
          <el-button @click="reset">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 流程列表 -->
      <el-table :data="processList" border stripe style="width: 100%">
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="name" label="流程名称"></el-table-column>
        <el-table-column prop="logo" label="图标" width="80">
          <template #default="scope">
            <el-image :src="scope.row.logo" :preview-src-list="[scope.row.logo]" style="width: 40px; height: 40px;"></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="admin" label="管理员" width="120"></el-table-column>
        <el-table-column prop="version" label="版本" width="80"></el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === 0 ? 'success' : 'warning'">
              {{ scope.row.status === 0 ? '正常' : '停用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
        <el-table-column prop="updateTime" label="更新时间" width="180"></el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button size="small" @click="viewProcess(scope.row)">查看</el-button>
            <el-button size="small" type="primary" @click="editProcess(scope.row)">编辑</el-button>
            <el-button v-if="scope.row.status === 0" size="small" type="warning" @click="stopProcess(scope.row)">停用</el-button>
            <el-button v-if="scope.row.status === 1" size="small" type="success" @click="publishProcess(scope.row)">发布</el-button>
            <el-button size="small" type="danger" @click="deleteProcess(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        class="pagination"
        background
        layout="prev, pager, next, jumper, ->, total"
        :total="total"
        :current-page="currentPage"
        :page-size="pageSize"
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
      ></el-pagination>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { listAgentProcess, publishAgentProcess, stopAgentProcess, deleteAgentProcess } from '@/api/ai-lab/agent-process'

// 搜索表单
const searchForm = ref({
  name: '',
  status: null
})

// 流程列表
const processList = ref([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)

// 页面加载时获取流程列表
onMounted(() => {
  getProcessList()
})

// 获取流程列表
const getProcessList = async () => {
  try {
    const params = {
      ...searchForm.value,
      page: currentPage.value,
      size: pageSize.value
    }
    const response = await listAgentProcess(params)
    processList.value = response.data
    total.value = response.total
  } catch (error) {
    ElMessage.error('获取流程列表失败')
  }
}

// 搜索
const search = () => {
  currentPage.value = 1
  getProcessList()
}

// 重置
const reset = () => {
  searchForm.value = {
    name: '',
    status: null
  }
  currentPage.value = 1
  getProcessList()
}

// 页码变化
const handleCurrentChange = (page: number) => {
  currentPage.value = page
  getProcessList()
}

// 每页条数变化
const handleSizeChange = (size: number) => {
  pageSize.value = size
  currentPage.value = 1
  getProcessList()
}

// 跳转到创建页面
const goToCreate = () => {
  window.location.href = '/ai-lab/agent-process/create'
}

// 查看流程
const viewProcess = (row: any) => {
  ElMessage.info('查看流程：' + row.name)
}

// 编辑流程
const editProcess = (row: any) => {
  ElMessage.info('编辑流程：' + row.name)
}

// 发布流程
const publishProcess = async (row: any) => {
  try {
    await publishAgentProcess(row.id)
    ElMessage.success('流程发布成功')
    getProcessList()
  } catch (error) {
    ElMessage.error('流程发布失败')
  }
}

// 停用流程
const stopProcess = async (row: any) => {
  try {
    await stopAgentProcess(row.id)
    ElMessage.success('流程停用成功')
    getProcessList()
  } catch (error) {
    ElMessage.error('流程停用失败')
  }
}

// 删除流程
const deleteProcess = async (row: any) => {
  try {
    await ElMessageBox.confirm('确定要删除该流程吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await deleteAgentProcess(row.id)
    ElMessage.success('流程删除成功')
    getProcessList()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('流程删除失败')
    }
  }
}
</script>

<style scoped>
.agent-process-list {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 18px;
  font-weight: bold;
}

.search-form {
  margin-bottom: 20px;
}

.pagination {
  margin-top: 20px;
  text-align: right;
}
</style>