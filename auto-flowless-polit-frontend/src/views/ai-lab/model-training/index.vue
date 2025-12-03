<template>
  <div class="app-container">
    <el-card>
      <div class="card-header">
        <h2>AI模型训练</h2>
        <el-button type="primary" @click="handleNewTraining">新建训练任务</el-button>
      </div>
      <el-table :data="trainingList" stripe border>
        <el-table-column prop="id" label="任务ID" width="100" />
        <el-table-column prop="modelName" label="模型名称" />
        <el-table-column prop="datasetName" label="数据集" />
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
        <el-table-column prop="currentEpoch" label="当前轮次" width="100">
          <template #default="scope">
            {{ scope.row.currentEpoch }} / {{ scope.row.totalEpochs }}
          </template>
        </el-table-column>
        <el-table-column prop="accuracy" label="准确率" width="100">
          <template #default="scope">
            {{ scope.row.accuracy ? (scope.row.accuracy * 100).toFixed(2) + '%' : '-' }}
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="240" fixed="right">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleViewDetails(scope.row)" :disabled="scope.row.status === 'pending'">
              查看详情
            </el-button>
            <el-button type="warning" size="small" @click="handleStartTraining(scope.row.id)" :disabled="scope.row.status !== 'pending'">
              开始训练
            </el-button>
            <el-button type="danger" size="small" @click="handleStopTraining(scope.row.id)" :disabled="scope.row.status !== 'running'">
              停止训练
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新建训练任务对话框 -->
    <el-dialog
      :title="'新建训练任务'"
      :visible.sync="newTrainingVisible"
      width="700px"
      @close="resetTrainingForm"
    >
      <el-form :model="trainingForm" :rules="trainingRules" ref="trainingFormRef" label-width="120px">
        <el-form-item label="模型选择" prop="modelId">
          <el-select v-model="trainingForm.modelId" placeholder="请选择要训练的模型">
            <el-option
              v-for="model in modelOptions"
              :key="model.id"
              :label="model.name"
              :value="model.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="数据集选择" prop="datasetId">
          <el-select v-model="trainingForm.datasetId" placeholder="请选择训练数据集">
            <el-option
              v-for="dataset in datasetOptions"
              :key="dataset.id"
              :label="dataset.name"
              :value="dataset.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="训练轮次" prop="epochs">
          <el-input-number v-model="trainingForm.epochs" :min="1" :max="1000" :step="1" placeholder="请输入训练轮次" />
        </el-form-item>
        <el-form-item label="学习率" prop="learningRate">
          <el-input-number v-model="trainingForm.learningRate" :min="0.00001" :max="1" :step="0.00001" :precision="5" placeholder="请输入学习率" />
        </el-form-item>
        <el-form-item label="批量大小" prop="batchSize">
          <el-input-number v-model="trainingForm.batchSize" :min="1" :max="1024" :step="1" placeholder="请输入批量大小" />
        </el-form-item>
        <el-form-item label="训练描述">
          <el-input v-model="trainingForm.description" type="textarea" placeholder="请输入训练任务描述" rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="newTrainingVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmitTraining">提交</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 训练详情对话框 -->
    <el-dialog
      :title="'训练详情 - ' + selectedTraining?.name"
      :visible.sync="trainingDetailsVisible"
      width="800px"
    >
      <div v-if="selectedTraining" class="training-details">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="任务ID">{{ selectedTraining.id }}</el-descriptions-item>
          <el-descriptions-item label="模型名称">{{ selectedTraining.modelName }}</el-descriptions-item>
          <el-descriptions-item label="数据集">{{ selectedTraining.datasetName }}</el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="getStatusType(selectedTraining.status)">
              {{ getStatusText(selectedTraining.status) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="进度">
            <el-progress
              :percentage="selectedTraining.progress"
              :status="selectedTraining.status === 'completed' ? 'success' : selectedTraining.status === 'failed' ? 'exception' : 'active'"
              :stroke-width="8"
            />
          </el-descriptions-item>
          <el-descriptions-item label="训练轮次">{{ selectedTraining.currentEpoch }} / {{ selectedTraining.totalEpochs }}</el-descriptions-item>
          <el-descriptions-item label="学习率">{{ selectedTraining.learningRate }}</el-descriptions-item>
          <el-descriptions-item label="批量大小">{{ selectedTraining.batchSize }}</el-descriptions-item>
          <el-descriptions-item label="准确率">{{ selectedTraining.accuracy ? (selectedTraining.accuracy * 100).toFixed(2) + '%' : '-' }}</el-descriptions-item>
          <el-descriptions-item label="损失值">{{ selectedTraining.loss ? selectedTraining.loss.toFixed(4) : '-' }}</el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ selectedTraining.createTime }}</el-descriptions-item>
          <el-descriptions-item label="开始时间">{{ selectedTraining.startedTime || '-' }}</el-descriptions-item>
          <el-descriptions-item label="完成时间">{{ selectedTraining.completedTime || '-' }}</el-descriptions-item>
          <el-descriptions-item label="描述">{{ selectedTraining.description || '-' }}</el-descriptions-item>
        </el-descriptions>

        <div v-if="selectedTraining.status === 'completed' || selectedTraining.status === 'running'" class="training-charts">
          <h3>训练曲线</h3>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-card>
                <template #header>
                  <div class="card-header">
                    <span>准确率</span>
                  </div>
                </template>
                <div id="accuracyChart" style="height: 300px;"></div>
              </el-card>
            </el-col>
            <el-col :span="12">
              <el-card>
                <template #header>
                  <div class="card-header">
                    <span>损失值</span>
                  </div>
                </template>
                <div id="lossChart" style="height: 300px;"></div>
              </el-card>
            </el-col>
          </el-row>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
// 假设我们使用ECharts来绘制训练曲线
import * as echarts from 'echarts'

interface TrainingTask {
  id: number
  name: string
  modelId: number
  modelName: string
  datasetId: number
  datasetName: string
  status: 'pending' | 'running' | 'completed' | 'failed'
  progress: number
  currentEpoch: number
  totalEpochs: number
  learningRate: number
  batchSize: number
  accuracy?: number
  loss?: number
  description?: string
  createTime: string
  startedTime?: string
  completedTime?: string
  metrics?: {
    epoch: number
    accuracy: number
    loss: number
  }[]
}

interface ModelOption {
  id: number
  name: string
}

interface DatasetOption {
  id: number
  name: string
}

const trainingList = ref<TrainingTask[]>([
  {
    id: 1,
    name: 'GPT-4 微调训练',
    modelId: 1,
    modelName: 'GPT-4',
    datasetId: 1,
    datasetName: '自定义对话数据集',
    status: 'running',
    progress: 65,
    currentEpoch: 13,
    totalEpochs: 20,
    learningRate: 0.0001,
    batchSize: 32,
    accuracy: 0.875,
    loss: 0.321,
    description: '使用自定义对话数据集微调GPT-4模型',
    createTime: '2024-01-03 09:30:00',
    startedTime: '2024-01-03 10:00:00',
    metrics: Array.from({ length: 13 }, (_, i) => ({
      epoch: i + 1,
      accuracy: 0.7 + Math.random() * 0.2,
      loss: 0.8 - Math.random() * 0.5
    }))
  },
  {
    id: 2,
    name: '文本嵌入模型训练',
    modelId: 2,
    modelName: 'text-embedding-ada-002',
    datasetId: 2,
    datasetName: '文本分类数据集',
    status: 'completed',
    progress: 100,
    currentEpoch: 10,
    totalEpochs: 10,
    learningRate: 0.00005,
    batchSize: 64,
    accuracy: 0.923,
    loss: 0.187,
    description: '训练文本嵌入模型用于文本分类任务',
    createTime: '2024-01-02 15:00:00',
    startedTime: '2024-01-02 15:30:00',
    completedTime: '2024-01-02 17:45:00',
    metrics: Array.from({ length: 10 }, (_, i) => ({
      epoch: i + 1,
      accuracy: 0.8 + Math.random() * 0.15,
      loss: 0.5 - Math.random() * 0.35
    }))
  }
])

const modelOptions = ref<ModelOption[]>([
  { id: 1, name: 'GPT-4' },
  { id: 2, name: 'text-embedding-ada-002' },
  { id: 3, name: 'Custom Model 1' }
])

const datasetOptions = ref<DatasetOption[]>([
  { id: 1, name: '自定义对话数据集' },
  { id: 2, name: '文本分类数据集' },
  { id: 3, name: '问答数据集' }
])

const newTrainingVisible = ref(false)
const trainingDetailsVisible = ref(false)
const selectedTraining = ref<TrainingTask | null>(null)
const trainingFormRef = ref()
const trainingForm = reactive({
  modelId: null,
  datasetId: null,
  epochs: 10,
  learningRate: 0.0001,
  batchSize: 32,
  description: ''
})

const trainingRules = {
  modelId: [
    { required: true, message: '请选择要训练的模型', trigger: 'change' }
  ],
  datasetId: [
    { required: true, message: '请选择训练数据集', trigger: 'change' }
  ],
  epochs: [
    { required: true, message: '请输入训练轮次', trigger: 'blur' },
    { type: 'number', min: 1, max: 1000, message: '训练轮次在 1 到 1000 之间', trigger: 'blur' }
  ],
  learningRate: [
    { required: true, message: '请输入学习率', trigger: 'blur' },
    { type: 'number', min: 0.00001, max: 1, message: '学习率在 0.00001 到 1 之间', trigger: 'blur' }
  ],
  batchSize: [
    { required: true, message: '请输入批量大小', trigger: 'blur' },
    { type: 'number', min: 1, max: 1024, message: '批量大小在 1 到 1024 之间', trigger: 'blur' }
  ]
}

const accuracyChart = ref<echarts.ECharts | null>(null)
const lossChart = ref<echarts.ECharts | null>(null)

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

const handleNewTraining = () => {
  resetTrainingForm()
  newTrainingVisible.value = true
}

const handleViewDetails = (task: TrainingTask) => {
  selectedTraining.value = task
  trainingDetailsVisible.value = true
}

const handleStartTraining = (id: number) => {
  const task = trainingList.value.find(t => t.id === id)
  if (task && task.status === 'pending') {
    task.status = 'running'
    task.startedTime = new Date().toISOString().slice(0, 19).replace('T', ' ')
    // 模拟训练进度更新
    simulateTrainingProgress(task)
    ElMessage.success('训练任务已开始')
  }
}

const handleStopTraining = (id: number) => {
  ElMessageBox.confirm('确定要停止该训练任务吗？', '停止训练', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(() => {
      const task = trainingList.value.find(t => t.id === id)
      if (task && task.status === 'running') {
        task.status = 'completed'
        task.progress = 100
        task.completedTime = new Date().toISOString().slice(0, 19).replace('T', ' ')
        ElMessage.success('训练任务已停止')
      }
    })
    .catch(() => {
      // 取消停止
    })
}

const simulateTrainingProgress = (task: TrainingTask) => {
  const interval = setInterval(() => {
    if (task.status !== 'running') {
      clearInterval(interval)
      return
    }

    task.progress += Math.floor(Math.random() * 10)
    if (task.progress >= 100) {
      task.progress = 100
      task.status = 'completed'
      task.currentEpoch = task.totalEpochs
      task.completedTime = new Date().toISOString().slice(0, 19).replace('T', ' ')
      task.accuracy = 0.7 + Math.random() * 0.25
      task.loss = 0.8 - Math.random() * 0.6
      clearInterval(interval)
      ElMessage.success(`训练任务 ${task.name} 已完成`)
    } else {
      task.currentEpoch = Math.floor((task.progress / 100) * task.totalEpochs)
      // 模拟准确率和损失值变化
      if (task.metrics) {
        task.accuracy = task.metrics[task.metrics.length - 1]?.accuracy || 0
        task.loss = task.metrics[task.metrics.length - 1]?.loss || 0
      }
    }
  }, 2000)
}

const resetTrainingForm = () => {
  trainingForm.modelId = null
  trainingForm.datasetId = null
  trainingForm.epochs = 10
  trainingForm.learningRate = 0.0001
  trainingForm.batchSize = 32
  trainingForm.description = ''
  trainingFormRef.value?.resetFields()
}

const handleSubmitTraining = () => {
  trainingFormRef.value?.validate((valid: boolean) => {
    if (valid) {
      const model = modelOptions.value.find(m => m.id === trainingForm.modelId)
      const dataset = datasetOptions.value.find(d => d.id === trainingForm.datasetId)
      
      if (model && dataset) {
        const newTask: TrainingTask = {
          id: Date.now(),
          name: `${model.name} 训练任务`,
          modelId: model.id,
          modelName: model.name,
          datasetId: dataset.id,
          datasetName: dataset.name,
          status: 'pending',
          progress: 0,
          currentEpoch: 0,
          totalEpochs: trainingForm.epochs,
          learningRate: trainingForm.learningRate,
          batchSize: trainingForm.batchSize,
          description: trainingForm.description,
          createTime: new Date().toISOString().slice(0, 19).replace('T', ' '),
          metrics: []
        }
        trainingList.value.push(newTask)
        newTrainingVisible.value = false
        ElMessage.success('训练任务已创建')
      }
    }
  })
}

const initCharts = () => {
  if (accuracyChart.value && lossChart.value) {
    updateCharts()
    return
  }

  // 初始化准确率图表
  const accuracyChartDom = document.getElementById('accuracyChart')
  if (accuracyChartDom) {
    accuracyChart.value = echarts.init(accuracyChartDom)
  }

  // 初始化损失值图表
  const lossChartDom = document.getElementById('lossChart')
  if (lossChartDom) {
    lossChart.value = echarts.init(lossChartDom)
  }

  updateCharts()
}

const updateCharts = () => {
  if (!selectedTraining.value || !selectedTraining.value.metrics) return

  const epochs = selectedTraining.value.metrics.map(m => m.epoch)
  const accuracies = selectedTraining.value.metrics.map(m => m.accuracy)
  const losses = selectedTraining.value.metrics.map(m => m.loss)

  // 更新准确率图表
  const accuracyOption: echarts.EChartsOption = {
    tooltip: {
      trigger: 'axis'
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: epochs
    },
    yAxis: {
      type: 'value',
      min: 0,
      max: 1
    },
    series: [
      {
        name: '准确率',
        type: 'line',
        stack: 'Total',
        data: accuracies,
        smooth: true,
        itemStyle: {
          color: '#67C23A'
        }
      }
    ]
  }

  // 更新损失值图表
  const lossOption: echarts.EChartsOption = {
    tooltip: {
      trigger: 'axis'
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: epochs
    },
    yAxis: {
      type: 'value',
      min: 0,
      max: 1
    },
    series: [
      {
        name: '损失值',
        type: 'line',
        stack: 'Total',
        data: losses,
        smooth: true,
        itemStyle: {
          color: '#F56C6C'
        }
      }
    ]
  }

  accuracyChart.value?.setOption(accuracyOption)
  lossChart.value?.setOption(lossOption)
}

// 监听训练详情对话框显示，初始化图表
watch(() => trainingDetailsVisible.value, (newVal) => {
  if (newVal) {
    setTimeout(() => {
      initCharts()
    }, 100)
  }
})

// 窗口大小变化时，调整图表大小
window.addEventListener('resize', () => {
  accuracyChart.value?.resize()
  lossChart.value?.resize()
})
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

.training-details {
  max-height: 600px;
  overflow-y: auto;
}

.training-charts {
  margin-top: 20px;
}
</style>