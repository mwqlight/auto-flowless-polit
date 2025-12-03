<template>
  <div class="app-container">
    <el-card>
      <div class="card-header">
        <h2>AI任务管理</h2>
        <el-button type="primary" @click="handleAddTask">添加任务</el-button>
      </div>
      <el-table :data="taskList" stripe border>
        <el-table-column prop="id" label="任务ID" width="100" />
        <el-table-column prop="name" label="任务名称" />
        <el-table-column prop="taskType" label="任务类型" />
        <el-table-column prop="modelName" label="使用模型" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="progress" label="进度" width="120">
          <template #default="scope">
            <el-progress
              :percentage="scope.row.progress"
              :status="scope.row.status === 'completed' ? 'success' : scope.row.status === 'failed' ? 'exception' : 'active'"
              :stroke-width="8"
            />
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column prop="completedTime" label="完成时间" width="180" v-if="scope.row.status === 'completed' || scope.row.status === 'failed'" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEditTask(scope.row)">
              编辑
            </el-button>
            <el-button type="danger" size="small" @click="handleDeleteTask(scope.row.id)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加/编辑任务对话框 -->
    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="600px"
      @close="resetForm"
    >
      <el-form :model="taskForm" :rules="taskRules" ref="taskFormRef" label-width="120px">
        <el-form-item label="任务名称" prop="name">
          <el-input v-model="taskForm.name" placeholder="请输入任务名称" />
        </el-form-item>
        <el-form-item label="任务类型" prop="taskType">
          <el-select v-model="taskForm.taskType" placeholder="请选择任务类型">
            <el-option label="模型训练" value="training" />
            <el-option label="模型评估" value="evaluation" />
            <el-option label="数据处理" value="data_processing" />
            <el-option label="推理任务" value="inference" />
          </el-select>
        </el-form-item>
        <el-form-item label="使用模型" prop="modelId">
          <el-select v-model="taskForm.modelId" placeholder="请选择使用的模型">
            <el-option
              v-for="model in modelOptions"
              :key="model.id"
              :label="model.name"
              :value="model.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="任务优先级" prop="priority">
          <el-select v-model="taskForm.priority" placeholder="请选择任务优先级">
            <el-option label="低" value="low" />
            <el-option label="中" value="medium" />
            <el-option label="高" value="high" />
          </el-select>
        </el-form-item>
        <el-form-item label="任务描述">
          <el-input v-model="taskForm.description" type="textarea" placeholder="请输入任务描述" rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">提交</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

interface Task {
  id: number
  name: string
  taskType: 'training' | 'evaluation' | 'data_processing' | 'inference'
  modelId: number
  modelName: string
  status: 'pending' | 'running' | 'completed' | 'failed'
  progress: number
  priority: 'low' | 'medium' | 'high'
  description?: string
  createTime: string
  startedTime?: string
  completedTime?: string
}

interface ModelOption {
  id: number
  name: string
}

const taskList = ref<Task[]>([
  {
    id: 1,
    name: 'GPT-4 微调训练',
    taskType: 'training',
    modelId: 1,
    modelName: 'GPT-4',
    status: 'running',
    progress: 65,
    priority: 'high',
    description: '使用自定义数据集微调GPT-4模型',
    createTime: '2024-01-03 09:30:00',
    startedTime: '2024-01-03 10:00:00'
  },
  {
    id: 2,
    name: '文本嵌入模型评估',
    taskType: 'evaluation',
    modelId: 2,
    modelName: 'text-embedding-ada-002',
    status: 'completed',
    progress: 100,
    priority: 'medium',
    description: '评估文本嵌入模型的性能',
    createTime: '2024-01-02 15:00:00',
    startedTime: '2024-01-02 15:30:00',
    completedTime: '2024-01-02 17:45:00'
  }
])

const modelOptions = ref<ModelOption[]>([
  { id: 1, name: 'GPT-4' },
  { id: 2, name: 'text-embedding-ada-002' },
  { id: 3, name: 'Custom Model 1' }
])

const dialogVisible = ref(false)
const dialogTitle = ref('添加任务')
const taskFormRef = ref()
const taskForm = reactive({
  name: '',
  taskType: '',
  modelId: null,
  priority: 'medium',
  description: ''
})

const taskRules = {
  name: [
    { required: true, message: '请输入任务名称', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  taskType: [
    { required: true, message: '请选择任务类型', trigger: 'change' }
  ],
  modelId: [
    { required: true, message: '请选择使用的模型', trigger: 'change' }
  ]
}

const getStatusType = (status: string) => {
  switch (status) {
    case 'running':
      return 'warning'
    case 'completed':
      return 'success'
    case 'failed':
      return 'danger'
    default:
      return 'info'
  }
}

const getStatusText = (status: string) => {
  switch (status) {
    case 'pending':
      return '待处理'
    case 'running':
      return '运行中'
    case 'completed':
      return '已完成'
    case 'failed':
      return '失败'
    default:
      return status
  }
}

const handleAddTask = () => {
  dialogTitle.value = '添加任务'
  resetForm()
  dialogVisible.value = true
}

const handleEditTask = (task: Task) => {
  dialogTitle.value = '编辑任务'
  Object.assign(taskForm, task)
  dialogVisible.value = true
}

const handleDeleteTask = (id: number) => {
  ElMessageBox.confirm('确定要删除该任务吗？', '删除确认', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(() => {
      const index = taskList.value.findIndex(task => task.id === id)
      if (index !== -1) {
        taskList.value.splice(index, 1)
        ElMessage.success('任务删除成功')
      }
    })
    .catch(() => {
      // 取消删除
    })
}

const resetForm = () => {
  taskForm.name = ''
  taskForm.taskType = ''
  taskForm.modelId = null
  taskForm.priority = 'medium'
  taskForm.description = ''
  taskFormRef.value?.resetFields()
}

const handleSubmit = () => {
  taskFormRef.value?.validate((valid: boolean) => {
    if (valid) {
      const model = modelOptions.value.find(m => m.id === taskForm.modelId)
      if (model) {
        if (taskForm.id) {
          // 编辑任务
          const index = taskList.value.findIndex(task => task.id === taskForm.id)
          if (index !== -1) {
            taskList.value[index] = {
              ...taskList.value[index],
              ...taskForm,
              modelName: model.name
            }
            ElMessage.success('任务编辑成功')
          }
        } else {
          // 添加任务
          const newTask: Task = {
            ...taskForm as Omit<Task, 'id' | 'modelName' | 'status' | 'progress' | 'createTime'>,
            id: Date.now(),
            modelName: model.name,
            status: 'pending',
            progress: 0,
            createTime: new Date().toISOString().slice(0, 19).replace('T', ' ')
          }
          taskList.value.push(newTask)
          ElMessage.success('任务添加成功')
        }
        dialogVisible.value = false
      }
    }
  })
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
</style>