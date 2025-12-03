<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!-- 统计卡片 -->
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-title">总任务数</div>
            <div class="stat-value">{{ stats.totalTasks }}</div>
            <div class="stat-change">
              <span class="el-icon-arrow-up"></span>
              <span class="increase">+{{ stats.taskIncrease }}%</span>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-title">运行中任务</div>
            <div class="stat-value">{{ stats.runningTasks }}</div>
            <div class="stat-change">
              <span class="el-icon-arrow-up"></span>
              <span class="increase">+{{ stats.runningIncrease }}%</span>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-title">已完成任务</div>
            <div class="stat-value">{{ stats.completedTasks }}</div>
            <div class="stat-change">
              <span class="el-icon-arrow-up"></span>
              <span class="increase">+{{ stats.completedIncrease }}%</span>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-title">失败任务</div>
            <div class="stat-value">{{ stats.failedTasks }}</div>
            <div class="stat-change">
              <span class="el-icon-arrow-down"></span>
              <span class="decrease">{{ stats.failedIncrease }}%</span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <!-- 资源使用率图表 -->
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>系统资源使用率</span>
              <el-select v-model="resourceTimeRange" size="small" style="width: 120px;">
                <el-option label="实时" value="realtime" />
                <el-option label="最近5分钟" value="5min" />
                <el-option label="最近15分钟" value="15min" />
                <el-option label="最近30分钟" value="30min" />
              </el-select>
            </div>
          </template>
          <div class="resource-charts">
            <div class="chart-item">
              <div class="chart-label">CPU使用率</div>
              <el-progress
                :percentage="systemMetrics.cpuUsage"
                :status="systemMetrics.cpuUsage > 80 ? 'exception' : systemMetrics.cpuUsage > 50 ? 'warning' : 'success'"
                :stroke-width="12"
                :show-text="true"
              />
            </div>
            <div class="chart-item">
              <div class="chart-label">内存使用率</div>
              <el-progress
                :percentage="systemMetrics.memoryUsage"
                :status="systemMetrics.memoryUsage > 80 ? 'exception' : systemMetrics.memoryUsage > 50 ? 'warning' : 'success'"
                :stroke-width="12"
                :show-text="true"
              />
            </div>
            <div class="chart-item">
              <div class="chart-label">磁盘使用率</div>
              <el-progress
                :percentage="systemMetrics.diskUsage"
                :status="systemMetrics.diskUsage > 80 ? 'exception' : systemMetrics.diskUsage > 50 ? 'warning' : 'success'"
                :stroke-width="12"
                :show-text="true"
              />
            </div>
            <div class="chart-item">
              <div class="chart-label">GPU使用率</div>
              <el-progress
                :percentage="systemMetrics.gpuUsage"
                :status="systemMetrics.gpuUsage > 80 ? 'exception' : systemMetrics.gpuUsage > 50 ? 'warning' : 'success'"
                :stroke-width="12"
                :show-text="true"
              />
            </div>
          </div>
        </el-card>
      </el-col>

      <!-- 任务状态分布 -->
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>任务状态分布</span>
            </div>
          </template>
          <div class="status-distribution">
            <div class="distribution-item">
              <span class="status-dot running"></span>
              <span class="status-text">运行中</span>
              <span class="status-count">{{ stats.runningTasks }}</span>
              <el-progress
                :percentage="(stats.runningTasks / stats.totalTasks) * 100"
                :stroke-width="6"
                :show-text="true"
                color="#E6A23C"
              />
            </div>
            <div class="distribution-item">
              <span class="status-dot pending"></span>
              <span class="status-text">待处理</span>
              <span class="status-count">{{ stats.pendingTasks }}</span>
              <el-progress
                :percentage="(stats.pendingTasks / stats.totalTasks) * 100"
                :stroke-width="6"
                :show-text="true"
                color="#909399"
              />
            </div>
            <div class="distribution-item">
              <span class="status-dot completed"></span>
              <span class="status-text">已完成</span>
              <span class="status-count">{{ stats.completedTasks }}</span>
              <el-progress
                :percentage="(stats.completedTasks / stats.totalTasks) * 100"
                :stroke-width="6"
                :show-text="true"
                color="#67C23A"
              />
            </div>
            <div class="distribution-item">
              <span class="status-dot failed"></span>
              <span class="status-text">失败</span>
              <span class="status-count">{{ stats.failedTasks }}</span>
              <el-progress
                :percentage="(stats.failedTasks / stats.totalTasks) * 100"
                :stroke-width="6"
                :show-text="true"
                color="#F56C6C"
              />
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 任务列表 -->
    <el-card style="margin-top: 20px;">
      <template #header>
        <div class="card-header">
          <span>实时任务监控</span>
          <el-button type="primary" size="small" @click="refreshTasks">
            <el-icon><Refresh /></el-icon>
            刷新
          </el-button>
        </div>
      </template>
      <el-table :data="taskList" stripe border @row-click="handleTaskClick">
        <el-table-column prop="id" label="任务ID" width="100" />
        <el-table-column prop="name" label="任务名称" />
        <el-table-column prop="taskType" label="任务类型" width="120" />
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
        <el-table-column prop="cpuUsage" label="CPU使用率" width="120">
          <template #default="scope">
            <el-progress
              :percentage="scope.row.cpuUsage"
              :status="scope.row.cpuUsage > 80 ? 'exception' : scope.row.cpuUsage > 50 ? 'warning' : 'success'"
              :stroke-width="8"
              :show-text="true"
            />
          </template>
        </el-table-column>
        <el-table-column prop="memoryUsage" label="内存使用率" width="120">
          <template #default="scope">
            <el-progress
              :percentage="scope.row.memoryUsage"
              :status="scope.row.memoryUsage > 80 ? 'exception' : scope.row.memoryUsage > 50 ? 'warning' : 'success'"
              :stroke-width="8"
              :show-text="true"
            />
          </template>
        </el-table-column>
        <el-table-column prop="gpuUsage" label="GPU使用率" width="120">
          <template #default="scope">
            <el-progress
              :percentage="scope.row.gpuUsage"
              :status="scope.row.gpuUsage > 80 ? 'exception' : scope.row.gpuUsage > 50 ? 'warning' : 'success'"
              :stroke-width="8"
              :show-text="true"
            />
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column prop="runningTime" label="运行时间" width="120">
          <template #default="scope">
            {{ formatRunningTime(scope.row.runningTime) }}
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 任务详情面板 -->
    <el-drawer
      v-model="drawerVisible"
      title="任务详情"
      direction="rtl"
      :size="'50%'"
    >
      <div v-if="selectedTask" class="task-details">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="任务ID">{{ selectedTask.id }}</el-descriptions-item>
          <el-descriptions-item label="任务名称">{{ selectedTask.name }}</el-descriptions-item>
          <el-descriptions-item label="任务类型">{{ selectedTask.taskType }}</el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="getStatusType(selectedTask.status)">
              {{ getStatusText(selectedTask.status) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="进度">
            <el-progress
              :percentage="selectedTask.progress"
              :status="selectedTask.status === 'completed' ? 'success' : selectedTask.status === 'failed' ? 'exception' : 'active'"
              :stroke-width="8"
            />
          </el-descriptions-item>
          <el-descriptions-item label="CPU使用率">
            <el-progress
              :percentage="selectedTask.cpuUsage"
              :status="selectedTask.cpuUsage > 80 ? 'exception' : selectedTask.cpuUsage > 50 ? 'warning' : 'success'"
              :stroke-width="8"
              :show-text="true"
            />
          </el-descriptions-item>
          <el-descriptions-item label="内存使用率">
            <el-progress
              :percentage="selectedTask.memoryUsage"
              :status="selectedTask.memoryUsage > 80 ? 'exception' : selectedTask.memoryUsage > 50 ? 'warning' : 'success'"
              :stroke-width="8"
              :show-text="true"
            />
          </el-descriptions-item>
          <el-descriptions-item label="GPU使用率">
            <el-progress
              :percentage="selectedTask.gpuUsage"
              :status="selectedTask.gpuUsage > 80 ? 'exception' : selectedTask.gpuUsage > 50 ? 'warning' : 'success'"
              :stroke-width="8"
              :show-text="true"
            />
          </el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ selectedTask.createTime }}</el-descriptions-item>
          <el-descriptions-item label="开始时间">{{ selectedTask.startedTime || '-' }}</el-descriptions-item>
          <el-descriptions-item label="预计完成时间">{{ selectedTask.estimatedCompletion || '-' }}</el-descriptions-item>
          <el-descriptions-item label="运行时间">{{ formatRunningTime(selectedTask.runningTime) }}</el-descriptions-item>
        </el-descriptions>

        <div class="task-metrics" style="margin-top: 20px;">
          <h3>实时指标</h3>
          <el-row :gutter="20">
            <el-col :span="8">
              <el-card>
                <div class="metric-item">
                  <div class="metric-label">CPU温度</div>
                  <div class="metric-value">{{ selectedTask.cpuTemperature }}°C</div>
                </div>
              </el-card>
            </el-col>
            <el-col :span="8">
              <el-card>
                <div class="metric-item">
                  <div class="metric-label">GPU温度</div>
                  <div class="metric-value">{{ selectedTask.gpuTemperature }}°C</div>
                </div>
              </el-card>
            </el-col>
            <el-col :span="8">
              <el-card>
                <div class="metric-item">
                  <div class="metric-label">网络带宽</div>
                  <div class="metric-value">{{ selectedTask.networkBandwidth }} MB/s</div>
                </div>
              </el-card>
            </el-col>
          </el-row>
        </div>

        <div class="task-logs" style="margin-top: 20px;">
          <h3>任务日志</h3>
          <el-scrollbar style="height: 200px;">
            <div class="log-item" v-for="(log, index) in selectedTask.logs" :key="index">
              <span class="log-time">{{ log.time }}</span>
              <span class="log-content">{{ log.content }}</span>
            </div>
          </el-scrollbar>
        </div>

        <div class="task-actions" style="margin-top: 20px; text-align: right;">
          <el-button @click="drawerVisible = false">关闭</el-button>
          <el-button type="warning" @click="handlePauseTask" :disabled="selectedTask.status !== 'running'">暂停</el-button>
          <el-button type="danger" @click="handleStopTask" :disabled="selectedTask.status !== 'running'">停止</el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Refresh } from '@element-plus/icons-vue'

interface Task {
  id: number
  name: string
  taskType: string
  status: 'pending' | 'running' | 'completed' | 'failed'
  progress: number
  cpuUsage: number
  memoryUsage: number
  gpuUsage: number
  cpuTemperature: number
  gpuTemperature: number
  networkBandwidth: number
  createTime: string
  startedTime?: string
  estimatedCompletion?: string
  runningTime: number
  logs: { time: string; content: string }[]
}

interface SystemMetrics {
  cpuUsage: number
  memoryUsage: number
  diskUsage: number
  gpuUsage: number
}

interface Stats {
  totalTasks: number
  runningTasks: number
  pendingTasks: number
  completedTasks: number
  failedTasks: number
  taskIncrease: number
  runningIncrease: number
  completedIncrease: number
  failedIncrease: number
}

const taskList = ref<Task[]>([
  {
    id: 1,
    name: 'GPT-4 微调训练',
    taskType: '模型训练',
    status: 'running',
    progress: 65,
    cpuUsage: 78,
    memoryUsage: 62,
    gpuUsage: 89,
    cpuTemperature: 68,
    gpuTemperature: 72,
    networkBandwidth: 125,
    createTime: '2024-01-03 09:30:00',
    startedTime: '2024-01-03 10:00:00',
    estimatedCompletion: '2024-01-03 12:30:00',
    runningTime: 7200,
    logs: [
      { time: '2024-01-03 10:00:00', content: '任务开始执行' },
      { time: '2024-01-03 10:15:00', content: '完成第1轮训练，准确率: 78.5%' },
      { time: '2024-01-03 10:30:00', content: '完成第2轮训练，准确率: 82.3%' },
      { time: '2024-01-03 10:45:00', content: '完成第3轮训练，准确率: 85.1%' },
      { time: '2024-01-03 11:00:00', content: '完成第4轮训练，准确率: 86.7%' }
    ]
  },
  {
    id: 2,
    name: '文本嵌入模型评估',
    taskType: '模型评估',
    status: 'running',
    progress: 45,
    cpuUsage: 52,
    memoryUsage: 48,
    gpuUsage: 35,
    cpuTemperature: 58,
    gpuTemperature: 62,
    networkBandwidth: 45,
    createTime: '2024-01-03 10:15:00',
    startedTime: '2024-01-03 10:30:00',
    estimatedCompletion: '2024-01-03 11:30:00',
    runningTime: 3600,
    logs: [
      { time: '2024-01-03 10:30:00', content: '任务开始执行' },
      { time: '2024-01-03 10:40:00', content: '加载测试数据集' },
      { time: '2024-01-03 10:50:00', content: '开始评估...' },
      { time: '2024-01-03 11:00:00', content: '完成25%评估，准确率: 88.2%' }
    ]
  },
  {
    id: 3,
    name: '数据预处理任务',
    taskType: '数据处理',
    status: 'pending',
    progress: 0,
    cpuUsage: 0,
    memoryUsage: 0,
    gpuUsage: 0,
    cpuTemperature: 0,
    gpuTemperature: 0,
    networkBandwidth: 0,
    createTime: '2024-01-03 11:00:00',
    runningTime: 0,
    logs: []
  },
  {
    id: 4,
    name: '图像分类模型推理',
    taskType: '推理任务',
    status: 'completed',
    progress: 100,
    cpuUsage: 0,
    memoryUsage: 0,
    gpuUsage: 0,
    cpuTemperature: 0,
    gpuTemperature: 0,
    networkBandwidth: 0,
    createTime: '2024-01-03 08:00:00',
    startedTime: '2024-01-03 08:15:00',
    estimatedCompletion: '2024-01-03 09:00:00',
    runningTime: 3600,
    logs: [
      { time: '2024-01-03 08:15:00', content: '任务开始执行' },
      { time: '2024-01-03 08:30:00', content: '完成50%推理' },
      { time: '2024-01-03 08:45:00', content: '完成80%推理' },
      { time: '2024-01-03 08:55:00', content: '任务完成' }
    ]
  }
])

const systemMetrics = reactive<SystemMetrics>({
  cpuUsage: 65,
  memoryUsage: 72,
  diskUsage: 45,
  gpuUsage: 82
})

const stats = reactive<Stats>({
  totalTasks: 4,
  runningTasks: 2,
  pendingTasks: 1,
  completedTasks: 1,
  failedTasks: 0,
  taskIncrease: 15,
  runningIncrease: 20,
  completedIncrease: 10,
  failedIncrease: 0
})

const resourceTimeRange = ref('realtime')
const drawerVisible = ref(false)
const selectedTask = ref<Task | null>(null)
let updateInterval: number | null = null

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

const formatRunningTime = (seconds: number): string => {
  const hours = Math.floor(seconds / 3600)
  const minutes = Math.floor((seconds % 3600) / 60)
  const secs = seconds % 60
  return `${hours}h ${minutes}m ${secs}s`
}

const handleTaskClick = (task: Task) => {
  selectedTask.value = task
  drawerVisible.value = true
}

const handlePauseTask = () => {
  if (selectedTask.value && selectedTask.value.status === 'running') {
    ElMessageBox.confirm('确定要暂停该任务吗？', '暂停任务', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(() => {
        selectedTask.value!.status = 'pending'
        ElMessage.success('任务已暂停')
      })
      .catch(() => {
        // 取消暂停
      })
  }
}

const handleStopTask = () => {
  if (selectedTask.value && selectedTask.value.status === 'running') {
    ElMessageBox.confirm('确定要停止该任务吗？', '停止任务', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(() => {
        selectedTask.value!.status = 'completed'
        selectedTask.value!.progress = 100
        selectedTask.value!.logs.push({
          time: new Date().toISOString().slice(0, 19).replace('T', ' '),
          content: '任务被手动停止'
        })
        ElMessage.success('任务已停止')
      })
      .catch(() => {
        // 取消停止
      })
  }
}

const refreshTasks = () => {
  ElMessage.success('任务列表已刷新')
  // 模拟刷新数据
  updateTaskMetrics()
}

const updateTaskMetrics = () => {
  // 模拟实时更新任务指标
  taskList.value.forEach(task => {
    if (task.status === 'running') {
      // 更新进度
      if (task.progress < 100) {
        task.progress += Math.floor(Math.random() * 5)
        if (task.progress > 100) task.progress = 100
      }

      // 更新资源使用率
      task.cpuUsage = Math.floor(Math.random() * 50) + 30 // 30-80%
      task.memoryUsage = Math.floor(Math.random() * 40) + 40 // 40-80%
      task.gpuUsage = Math.floor(Math.random() * 60) + 20 // 20-80%
      task.cpuTemperature = Math.floor(Math.random() * 20) + 50 // 50-70°C
      task.gpuTemperature = Math.floor(Math.random() * 25) + 55 // 55-80°C
      task.networkBandwidth = Math.floor(Math.random() * 100) + 20 // 20-120 MB/s
      task.runningTime += 5

      // 随机添加日志
      if (Math.random() > 0.8) {
        task.logs.push({
          time: new Date().toISOString().slice(0, 19).replace('T', ' '),
          content: `进度更新: ${task.progress}%，CPU: ${task.cpuUsage}%，内存: ${task.memoryUsage}%`
        })
      }

      // 如果任务完成
      if (task.progress >= 100) {
        task.status = 'completed'
        task.logs.push({
          time: new Date().toISOString().slice(0, 19).replace('T', ' '),
          content: '任务完成'
        })
      }
    }
  })

  // 更新系统指标
  systemMetrics.cpuUsage = Math.floor(Math.random() * 30) + 40 // 40-70%
  systemMetrics.memoryUsage = Math.floor(Math.random() * 25) + 55 // 55-80%
  systemMetrics.diskUsage = Math.floor(Math.random() * 10) + 40 // 40-50%
  systemMetrics.gpuUsage = Math.floor(Math.random() * 40) + 50 // 50-90%

  // 更新统计数据
  stats.runningTasks = taskList.value.filter(t => t.status === 'running').length
  stats.pendingTasks = taskList.value.filter(t => t.status === 'pending').length
  stats.completedTasks = taskList.value.filter(t => t.status === 'completed').length
  stats.failedTasks = taskList.value.filter(t => t.status === 'failed').length
  stats.totalTasks = taskList.value.length
}

// 启动实时更新
onMounted(() => {
  updateInterval = window.setInterval(() => {
    updateTaskMetrics()
  }, 5000) // 每5秒更新一次
})

// 停止实时更新
onUnmounted(() => {
  if (updateInterval) {
    clearInterval(updateInterval)
  }
})
</script>

<style scoped>
.stat-card {
  height: 100%;
}

.stat-content {
  text-align: center;
}

.stat-title {
  font-size: 14px;
  color: #606266;
  margin-bottom: 8px;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 4px;
}

.stat-change {
  font-size: 12px;
}

.increase {
  color: #67C23A;
}

.decrease {
  color: #F56C6C;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.resource-charts {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.chart-item {
  display: flex;
  align-items: center;
  gap: 15px;
}

.chart-label {
  width: 80px;
  font-size: 14px;
  color: #606266;
}

.status-distribution {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.distribution-item {
  display: flex;
  align-items: center;
  gap: 10px;
}

.status-dot {
  width: 12px;
  height: 12px;
  border-radius: 50%;
}

.status-dot.running {
  background-color: #E6A23C;
}

.status-dot.pending {
  background-color: #909399;
}

.status-dot.completed {
  background-color: #67C23A;
}

.status-dot.failed {
  background-color: #F56C6C;
}

.status-text {
  width: 80px;
  font-size: 14px;
}

.status-count {
  width: 30px;
  font-size: 14px;
  font-weight: bold;
  text-align: right;
}

.task-details {
  height: 100%;
  overflow-y: auto;
}

.metric-item {
  text-align: center;
}

.metric-label {
  font-size: 14px;
  color: #606266;
  margin-bottom: 8px;
}

.metric-value {
  font-size: 20px;
  font-weight: bold;
  color: #303133;
}

.log-item {
  display: flex;
  gap: 10px;
  margin-bottom: 8px;
}

.log-time {
  width: 150px;
  font-size: 12px;
  color: #909399;
}

.log-content {
  font-size: 13px;
  color: #303133;
}
</style>