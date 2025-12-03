<template>
  <div class="app-container">
    <el-card>
      <div class="card-header">
        <h2>AI应用管理</h2>
        <el-button type="primary" @click="handleAddApp">添加应用</el-button>
      </div>
      <el-table :data="appList" stripe border @row-click="handleViewDetails">
        <el-table-column prop="id" label="应用ID" width="100" />
        <el-table-column prop="name" label="应用名称" />
        <el-table-column prop="type" label="应用类型" width="120">
          <template #default="scope">
            <el-tag :type="getAppTypeColor(scope.row.type)">
              {{ getAppTypeName(scope.row.type) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-switch
              v-model="scope.row.status"
              :active-value="'active'"
              :inactive-value="'inactive'"
              @change="handleStatusChange(scope.row)"
            >
              <template #active>
                <el-tag type="success" size="small">启用</el-tag>
              </template>
              <template #inactive>
                <el-tag type="info" size="small">禁用</el-tag>
              </template>
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="modelName" label="关联模型" width="150" />
        <el-table-column prop="creator" label="创建人" width="120" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column prop="updateTime" label="更新时间" width="180" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEditApp(scope.row)">
              编辑
            </el-button>
            <el-button type="danger" size="small" @click="handleDeleteApp(scope.row.id)">
              删除
            </el-button>
            <el-button type="success" size="small" @click="handleDeployApp(scope.row)">
              部署
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加/编辑应用对话框 -->
    <el-dialog
      :title="isEditApp ? '编辑应用' : '添加应用'"
      :visible.sync="appDialogVisible"
      width="700px"
      @close="resetAppForm"
    >
      <el-form :model="appForm" :rules="appRules" ref="appFormRef" label-width="120px">
        <el-form-item label="应用名称" prop="name">
          <el-input v-model="appForm.name" placeholder="请输入应用名称" />
        </el-form-item>
        <el-form-item label="应用类型" prop="type">
          <el-select v-model="appForm.type" placeholder="请选择应用类型">
            <el-option label="聊天机器人" value="chatbot" />
            <el-option label="文本生成" value="text-generation" />
            <el-option label="图像生成" value="image-generation" />
            <el-option label="语音识别" value="speech-recognition" />
            <el-option label="自然语言处理" value="nlp" />
            <el-option label="计算机视觉" value="computer-vision" />
          </el-select>
        </el-form-item>
        <el-form-item label="应用描述">
          <el-input v-model="appForm.description" type="textarea" placeholder="请输入应用描述" rows="3" />
        </el-form-item>
        <el-form-item label="关联模型" prop="modelId">
          <el-select v-model="appForm.modelId" placeholder="请选择关联模型">
            <el-option
              v-for="model in modelOptions"
              :key="model.id"
              :label="model.name"
              :value="model.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="应用图标">
          <el-upload
            v-model="appForm.icon"
            :auto-upload="false"
            :show-file-list="false"
            accept="image/*"
          >
            <el-button type="primary" size="small">上传图标</el-button>
          </el-upload>
          <el-image
            v-if="appForm.icon"
            :src="appForm.icon"
            style="width: 50px; height: 50px; margin-left: 10px;"
          />
        </el-form-item>
        <el-form-item label="配置参数">
          <el-input v-model="appForm.config" type="textarea" placeholder="请输入JSON格式的配置参数" rows="5" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="appDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmitApp">提交</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 应用详情对话框 -->
    <el-dialog
      :title="'应用详情 - ' + selectedApp?.name"
      :visible.sync="appDetailsVisible"
      width="900px"
      :before-close="handleCloseDetails"
    >
      <div v-if="selectedApp" class="app-details">
        <el-descriptions :column="2" border style="margin-bottom: 20px;">
          <el-descriptions-item label="应用ID">{{ selectedApp.id }}</el-descriptions-item>
          <el-descriptions-item label="应用名称">{{ selectedApp.name }}</el-descriptions-item>
          <el-descriptions-item label="应用类型">
            <el-tag :type="getAppTypeColor(selectedApp.type)">
              {{ getAppTypeName(selectedApp.type) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="selectedApp.status === 'active' ? 'success' : 'info'">
              {{ selectedApp.status === 'active' ? '启用' : '禁用' }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="关联模型">{{ selectedApp.modelName }}</el-descriptions-item>
          <el-descriptions-item label="创建人">{{ selectedApp.creator }}</el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ selectedApp.createTime }}</el-descriptions-item>
          <el-descriptions-item label="更新时间">{{ selectedApp.updateTime }}</el-descriptions-item>
          <el-descriptions-item label="描述" :span="2">{{ selectedApp.description || '-' }}</el-descriptions-item>
        </el-descriptions>

        <!-- 应用图标 -->
        <el-card v-if="selectedApp.icon" class="icon-card">
          <template #header>
            <div class="card-header">
              <span>应用图标</span>
            </div>
          </template>
          <el-image :src="selectedApp.icon" style="width: 100px; height: 100px;" />
        </el-card>

        <!-- 配置参数 -->
        <el-card v-if="selectedApp.config" class="config-card">
          <template #header>
            <div class="card-header">
              <span>配置参数</span>
            </div>
          </template>
          <pre class="config-content">{{ JSON.stringify(selectedApp.config, null, 2) }}</pre>
        </el-card>

        <!-- 部署信息 -->
        <el-card v-if="selectedApp.deploymentInfo" class="deployment-card">
          <template #header>
            <div class="card-header">
              <span>部署信息</span>
            </div>
          </template>
          <el-descriptions :column="2" border>
            <el-descriptions-item label="部署状态">{{ selectedApp.deploymentInfo.status }}</el-descriptions-item>
            <el-descriptions-item label="部署版本">{{ selectedApp.deploymentInfo.version }}</el-descriptions-item>
            <el-descriptions-item label="访问地址">{{ selectedApp.deploymentInfo.accessUrl }}</el-descriptions-item>
            <el-descriptions-item label="API密钥">{{ selectedApp.deploymentInfo.apiKey }}</el-descriptions-item>
            <el-descriptions-item label="部署时间" :span="2">{{ selectedApp.deploymentInfo.deploymentTime }}</el-descriptions-item>
          </el-descriptions>
        </el-card>

        <!-- 访问日志 -->
        <el-card v-if="selectedApp.accessLogs && selectedApp.accessLogs.length > 0" class="logs-card">
          <template #header>
            <div class="card-header">
              <span>访问日志</span>
            </div>
          </template>
          <el-scrollbar style="height: 200px;">
            <div class="log-item" v-for="(log, index) in selectedApp.accessLogs" :key="index">
              <span class="log-time">{{ log.time }}</span>
              <span class="log-ip">{{ log.ip }}</span>
              <span class="log-action">{{ log.action }}</span>
              <span class="log-result" :class="log.success ? 'success' : 'error'">
                {{ log.success ? '成功' : '失败' }}
              </span>
            </div>
          </el-scrollbar>
        </el-card>
      </div>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="appDetailsVisible = false">关闭</el-button>
          <el-button type="primary" @click="handleDeployApp(selectedApp)">
            重新部署
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

interface App {
  id: number
  name: string
  type: 'chatbot' | 'text-generation' | 'image-generation' | 'speech-recognition' | 'nlp' | 'computer-vision'
  status: 'active' | 'inactive'
  modelId: number
  modelName: string
  description?: string
  icon?: string
  config?: Record<string, any>
  creator: string
  createTime: string
  updateTime: string
  deploymentInfo?: {
    status: string
    version: string
    accessUrl: string
    apiKey: string
    deploymentTime: string
  }
  accessLogs?: {
    time: string
    ip: string
    action: string
    success: boolean
  }[]
}

interface ModelOption {
  id: number
  name: string
}

const appList = ref<App[]>([
  {
    id: 1,
    name: '智能客服机器人',
    type: 'chatbot',
    status: 'active',
    modelId: 1,
    modelName: 'GPT-4',
    description: '基于GPT-4的智能客服机器人，能够处理用户的各种咨询问题',
    icon: 'https://via.placeholder.com/50',
    config: {
      temperature: 0.7,
      max_tokens: 1024,
      top_p: 0.9,
      frequency_penalty: 0,
      presence_penalty: 0
    },
    creator: '张三',
    createTime: '2024-01-01 10:00:00',
    updateTime: '2024-01-03 15:30:00',
    deploymentInfo: {
      status: '已部署',
      version: 'v1.0.0',
      accessUrl: 'https://api.example.com/apps/1',
      apiKey: 'sk_abcdef123456',
      deploymentTime: '2024-01-02 09:00:00'
    },
    accessLogs: [
      { time: '2024-01-04 09:00:00', ip: '192.168.1.100', action: '对话请求', success: true },
      { time: '2024-01-04 09:05:00', ip: '192.168.1.101', action: '对话请求', success: true },
      { time: '2024-01-04 09:10:00', ip: '192.168.1.102', action: '对话请求', success: false },
      { time: '2024-01-04 09:15:00', ip: '192.168.1.103', action: '对话请求', success: true }
    ]
  },
  {
    id: 2,
    name: '文本生成助手',
    type: 'text-generation',
    status: 'active',
    modelId: 2,
    modelName: 'text-davinci-003',
    description: '基于GPT-3.5的文本生成助手，能够生成各种类型的文本内容',
    icon: 'https://via.placeholder.com/50',
    config: {
      temperature: 0.8,
      max_tokens: 2048,
      top_p: 0.9,
      frequency_penalty: 0.5,
      presence_penalty: 0.5
    },
    creator: '李四',
    createTime: '2024-01-02 14:30:00',
    updateTime: '2024-01-03 16:45:00',
    deploymentInfo: {
      status: '已部署',
      version: 'v1.0.0',
      accessUrl: 'https://api.example.com/apps/2',
      apiKey: 'sk_ghijkl789012',
      deploymentTime: '2024-01-02 15:00:00'
    },
    accessLogs: [
      { time: '2024-01-04 09:20:00', ip: '192.168.1.104', action: '文本生成', success: true },
      { time: '2024-01-04 09:25:00', ip: '192.168.1.105', action: '文本生成', success: true },
      { time: '2024-01-04 09:30:00', ip: '192.168.1.106', action: '文本生成', success: true }
    ]
  },
  {
    id: 3,
    name: '图像生成器',
    type: 'image-generation',
    status: 'inactive',
    modelId: 3,
    modelName: 'DALL·E 2',
    description: '基于DALL·E 2的图像生成器，能够根据文本描述生成高质量图像',
    icon: 'https://via.placeholder.com/50',
    config: {
      size: '1024x1024',
      n: 1,
      quality: 'standard',
      style: 'natural'
    },
    creator: '王五',
    createTime: '2024-01-03 09:00:00',
    updateTime: '2024-01-03 10:30:00',
    deploymentInfo: {
      status: '未部署',
      version: '',
      accessUrl: '',
      apiKey: '',
      deploymentTime: ''
    },
    accessLogs: []
  }
])

const modelOptions = ref<ModelOption[]>([
  { id: 1, name: 'GPT-4' },
  { id: 2, name: 'text-davinci-003' },
  { id: 3, name: 'DALL·E 2' }
])

const appDialogVisible = ref(false)
const appDetailsVisible = ref(false)
const isEditApp = ref(false)
const selectedApp = ref<App | null>(null)
const appFormRef = ref()
const appForm = reactive({
  name: '',
  type: '',
  description: '',
  modelId: null,
  icon: '',
  config: '{}'
})

const appRules = {
  name: [
    { required: true, message: '请输入应用名称', trigger: 'blur' },
    { min: 2, max: 50, message: '应用名称长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  type: [
    { required: true, message: '请选择应用类型', trigger: 'change' }
  ],
  modelId: [
    { required: true, message: '请选择关联模型', trigger: 'change' }
  ]
}

const getAppTypeColor = (type: string) => {
  switch (type) {
    case 'chatbot':
      return 'primary'
    case 'text-generation':
      return 'success'
    case 'image-generation':
      return 'warning'
    case 'speech-recognition':
      return 'info'
    case 'nlp':
      return 'danger'
    case 'computer-vision':
      return 'purple'
    default:
      return 'default'
  }
}

const getAppTypeName = (type: string) => {
  switch (type) {
    case 'chatbot':
      return '聊天机器人'
    case 'text-generation':
      return '文本生成'
    case 'image-generation':
      return '图像生成'
    case 'speech-recognition':
      return '语音识别'
    case 'nlp':
      return '自然语言处理'
    case 'computer-vision':
      return '计算机视觉'
    default:
      return type
  }
}

const handleAddApp = () => {
  isEditApp.value = false
  resetAppForm()
  appDialogVisible.value = true
}

const handleEditApp = (app: App) => {
  isEditApp.value = true
  selectedApp.value = app
  appForm.name = app.name
  appForm.type = app.type
  appForm.description = app.description || ''
  appForm.modelId = app.modelId
  appForm.icon = app.icon || ''
  appForm.config = JSON.stringify(app.config || {}, null, 2)
  appDialogVisible.value = true
}

const handleViewDetails = (app: App) => {
  selectedApp.value = app
  appDetailsVisible.value = true
}

const handleDeleteApp = (id: number) => {
  ElMessageBox.confirm('确定要删除该应用吗？', '删除确认', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(() => {
      const index = appList.value.findIndex(app => app.id === id)
      if (index !== -1) {
        appList.value.splice(index, 1)
        ElMessage.success('应用删除成功')
      }
    })
    .catch(() => {
      // 取消删除
    })
}

const handleDeployApp = (app: App) => {
  ElMessageBox.confirm(`确定要部署应用 "${app.name}" 吗？`, '部署确认', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'info'
  })
    .then(() => {
      ElMessage.success('应用部署功能开发中...')
      // 这里可以实现实际的部署逻辑
    })
    .catch(() => {
      // 取消部署
    })
}

const handleStatusChange = (app: App) => {
  ElMessage.success(`应用已${app.status === 'active' ? '启用' : '禁用'}`)
}

const resetAppForm = () => {
  appForm.name = ''
  appForm.type = ''
  appForm.description = ''
  appForm.modelId = null
  appForm.icon = ''
  appForm.config = '{}'
  appFormRef.value?.resetFields()
}

const handleSubmitApp = () => {
  appFormRef.value?.validate((valid: boolean) => {
    if (valid) {
      try {
        JSON.parse(appForm.config)
      } catch (e) {
        ElMessage.error('配置参数格式不正确')
        return
      }

      const model = modelOptions.value.find(m => m.id === appForm.modelId)
      
      if (model) {
        const appData: Partial<App> = {
          name: appForm.name,
          type: appForm.type as 'chatbot' | 'text-generation' | 'image-generation' | 'speech-recognition' | 'nlp' | 'computer-vision',
          description: appForm.description,
          modelId: model.id,
          modelName: model.name,
          icon: appForm.icon,
          config: JSON.parse(appForm.config),
          updateTime: new Date().toISOString().slice(0, 19).replace('T', ' ')
        }

        if (isEditApp.value && selectedApp.value) {
          // 编辑现有应用
          const index = appList.value.findIndex(app => app.id === selectedApp.value!.id)
          if (index !== -1) {
            appList.value[index] = { ...selectedApp.value!, ...appData }
            ElMessage.success('应用更新成功')
          }
        } else {
          // 添加新应用
          const newApp: App = {
            ...appData as App,
            id: Date.now(),
            status: 'inactive',
            creator: '当前用户',
            createTime: new Date().toISOString().slice(0, 19).replace('T', ' '),
            deploymentInfo: {
              status: '未部署',
              version: '',
              accessUrl: '',
              apiKey: '',
              deploymentTime: ''
            },
            accessLogs: []
          }
          appList.value.push(newApp)
          ElMessage.success('应用添加成功')
        }

        appDialogVisible.value = false
      }
    }
  })
}

const handleCloseDetails = () => {
  selectedApp.value = null
}
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.dialog-footer {
  text-align: right;
}

.app-details {
  max-height: 700px;
  overflow-y: auto;
}

.icon-card {
  margin-bottom: 20px;
}

.config-card {
  margin-bottom: 20px;
}

.config-content {
  background-color: #f5f7fa;
  padding: 15px;
  border-radius: 8px;
  font-family: monospace;
  font-size: 13px;
  line-height: 1.6;
}

.deployment-card {
  margin-bottom: 20px;
}

.logs-card {
  margin-bottom: 20px;
}

.log-item {
  display: flex;
  gap: 20px;
  margin-bottom: 8px;
  font-size: 13px;
}

.log-time {
  width: 150px;
  color: #909399;
}

.log-ip {
  width: 120px;
  color: #606266;
}

.log-action {
  flex: 1;
  color: #303133;
}

.log-result {
  width: 60px;
  font-weight: bold;
}

.success {
  color: #67c23a;
}

.error {
  color: #f56c6c;
}
</style>