<template>
  <div class="app-container">
    <el-card>
      <div class="card-header">
        <h2>AI模型评估</h2>
        <el-button type="primary" @click="handleNewEvaluation">新建评估任务</el-button>
      </div>
      <el-table :data="evaluationList" stripe border @row-click="handleViewDetails">
        <el-table-column prop="id" label="任务ID" width="100" />
        <el-table-column prop="modelName" label="模型名称" />
        <el-table-column prop="datasetName" label="数据集" />
        <el-table-column prop="evaluationType" label="评估类型" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="accuracy" label="准确率" width="100">
          <template #default="scope">
            {{ scope.row.accuracy ? (scope.row.accuracy * 100).toFixed(2) + '%' : '-' }}
          </template>
        </el-table-column>
        <el-table-column prop="precision" label="精确率" width="100">
          <template #default="scope">
            {{ scope.row.precision ? (scope.row.precision * 100).toFixed(2) + '%' : '-' }}
          </template>
        </el-table-column>
        <el-table-column prop="recall" label="召回率" width="100">
          <template #default="scope">
            {{ scope.row.recall ? (scope.row.recall * 100).toFixed(2) + '%' : '-' }}
          </template>
        </el-table-column>
        <el-table-column prop="f1Score" label="F1值" width="100">
          <template #default="scope">
            {{ scope.row.f1Score ? (scope.row.f1Score * 100).toFixed(2) + '%' : '-' }}
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleViewDetails(scope.row)" :disabled="scope.row.status !== 'completed'">
              查看详情
            </el-button>
            <el-button type="danger" size="small" @click="handleDeleteEvaluation(scope.row.id)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新建评估任务对话框 -->
    <el-dialog
      :title="'新建评估任务'"
      :visible.sync="newEvaluationVisible"
      width="700px"
      @close="resetEvaluationForm"
    >
      <el-form :model="evaluationForm" :rules="evaluationRules" ref="evaluationFormRef" label-width="120px">
        <el-form-item label="模型选择" prop="modelId">
          <el-select v-model="evaluationForm.modelId" placeholder="请选择要评估的模型">
            <el-option
              v-for="model in modelOptions"
              :key="model.id"
              :label="model.name"
              :value="model.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="数据集选择" prop="datasetId">
          <el-select v-model="evaluationForm.datasetId" placeholder="请选择评估数据集">
            <el-option
              v-for="dataset in datasetOptions"
              :key="dataset.id"
              :label="dataset.name"
              :value="dataset.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="评估类型" prop="evaluationType">
          <el-select v-model="evaluationForm.evaluationType" placeholder="请选择评估类型">
            <el-option label="分类评估" value="classification" />
            <el-option label="回归评估" value="regression" />
            <el-option label="生成评估" value="generation" />
            <el-option label="检索评估" value="retrieval" />
          </el-select>
        </el-form-item>
        <el-form-item label="评估指标" prop="metrics">
          <el-select v-model="evaluationForm.metrics" multiple placeholder="请选择评估指标">
            <el-option label="准确率(Accuracy)" value="accuracy" />
            <el-option label="精确率(Precision)" value="precision" />
            <el-option label="召回率(Recall)" value="recall" />
            <el-option label="F1值(F1 Score)" value="f1_score" />
            <el-option label="AUC" value="auc" />
            <el-option label="损失值(Loss)" value="loss" />
            <el-option label="BLEU" value="bleu" />
            <el-option label="ROUGE" value="rouge" />
          </el-select>
        </el-form-item>
        <el-form-item label="批量大小" prop="batchSize">
          <el-input-number v-model="evaluationForm.batchSize" :min="1" :max="1024" :step="1" placeholder="请输入批量大小" />
        </el-form-item>
        <el-form-item label="评估描述">
          <el-input v-model="evaluationForm.description" type="textarea" placeholder="请输入评估任务描述" rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="newEvaluationVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmitEvaluation">提交</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 评估详情对话框 -->
    <el-dialog
      :title="'评估详情 - ' + selectedEvaluation?.name"
      :visible.sync="evaluationDetailsVisible"
      width="900px"
      :before-close="handleCloseDetails"
    >
      <div v-if="selectedEvaluation" class="evaluation-details">
        <el-descriptions :column="2" border style="margin-bottom: 20px;">
          <el-descriptions-item label="任务ID">{{ selectedEvaluation.id }}</el-descriptions-item>
          <el-descriptions-item label="模型名称">{{ selectedEvaluation.modelName }}</el-descriptions-item>
          <el-descriptions-item label="数据集">{{ selectedEvaluation.datasetName }}</el-descriptions-item>
          <el-descriptions-item label="评估类型">{{ getEvaluationTypeText(selectedEvaluation.evaluationType) }}</el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="getStatusType(selectedEvaluation.status)">
              {{ getStatusText(selectedEvaluation.status) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ selectedEvaluation.createTime }}</el-descriptions-item>
          <el-descriptions-item label="开始时间">{{ selectedEvaluation.startedTime || '-' }}</el-descriptions-item>
          <el-descriptions-item label="完成时间">{{ selectedEvaluation.completedTime || '-' }}</el-descriptions-item>
          <el-descriptions-item label="耗时">{{ formatDuration(selectedEvaluation.duration) }}</el-descriptions-item>
          <el-descriptions-item label="描述">{{ selectedEvaluation.description || '-' }}</el-descriptions-item>
        </el-descriptions>

        <!-- 评估指标概览 -->
        <el-card v-if="selectedEvaluation.metrics" class="metrics-card">
          <template #header>
            <div class="card-header">
              <span>评估指标概览</span>
            </div>
          </template>
          <el-row :gutter="15">
            <el-col :span="6">
              <div class="metric-item">
                <div class="metric-label">准确率</div>
                <div class="metric-value">{{ (selectedEvaluation.metrics.accuracy * 100).toFixed(2) }}%</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="metric-item">
                <div class="metric-label">精确率</div>
                <div class="metric-value">{{ (selectedEvaluation.metrics.precision * 100).toFixed(2) }}%</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="metric-item">
                <div class="metric-label">召回率</div>
                <div class="metric-value">{{ (selectedEvaluation.metrics.recall * 100).toFixed(2) }}%</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="metric-item">
                <div class="metric-label">F1值</div>
                <div class="metric-value">{{ (selectedEvaluation.metrics.f1Score * 100).toFixed(2) }}%</div>
              </div>
            </el-col>
            <el-col :span="6" v-if="selectedEvaluation.metrics.auc !== undefined">
              <div class="metric-item">
                <div class="metric-label">AUC</div>
                <div class="metric-value">{{ selectedEvaluation.metrics.auc.toFixed(4) }}</div>
              </div>
            </el-col>
            <el-col :span="6" v-if="selectedEvaluation.metrics.loss !== undefined">
              <div class="metric-item">
                <div class="metric-label">损失值</div>
                <div class="metric-value">{{ selectedEvaluation.metrics.loss.toFixed(4) }}</div>
              </div>
            </el-col>
            <el-col :span="6" v-if="selectedEvaluation.metrics.bleu !== undefined">
              <div class="metric-item">
                <div class="metric-label">BLEU</div>
                <div class="metric-value">{{ (selectedEvaluation.metrics.bleu * 100).toFixed(2) }}%</div>
              </div>
            </el-col>
            <el-col :span="6" v-if="selectedEvaluation.metrics.rouge !== undefined">
              <div class="metric-item">
                <div class="metric-label">ROUGE</div>
                <div class="metric-value">{{ (selectedEvaluation.metrics.rouge * 100).toFixed(2) }}%</div>
              </div>
            </el-col>
          </el-row>
        </el-card>

        <!-- 混淆矩阵 -->
        <el-card v-if="selectedEvaluation.confusionMatrix" class="confusion-matrix-card">
          <template #header>
            <div class="card-header">
              <span>混淆矩阵</span>
            </div>
          </template>
          <div class="confusion-matrix-container">
            <table class="confusion-matrix">
              <thead>
                <tr>
                  <th></th>
                  <th v-for="(label, index) in selectedEvaluation.classLabels" :key="index">预测{{ label }}</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(row, i) in selectedEvaluation.confusionMatrix" :key="i">
                  <th>实际{{ selectedEvaluation.classLabels[i] }}</th>
                  <td v-for="(value, j) in row" :key="j" :class="getCellClass(value, i, j)">
                    {{ value }}
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-card>

        <!-- 分类报告 -->
        <el-card v-if="selectedEvaluation.classificationReport" class="classification-report-card">
          <template #header>
            <div class="card-header">
              <span>分类报告</span>
            </div>
          </template>
          <el-scrollbar style="height: 300px;">
            <table class="classification-report">
              <thead>
                <tr>
                  <th>类别</th>
                  <th>精确率</th>
                  <th>召回率</th>
                  <th>F1值</th>
                  <th>支持数</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(report, label) in selectedEvaluation.classificationReport" :key="label">
                  <th>{{ label }}</th>
                  <td>{{ (report.precision * 100).toFixed(2) }}%</td>
                  <td>{{ (report.recall * 100).toFixed(2) }}%</td>
                  <td>{{ (report.f1Score * 100).toFixed(2) }}%</td>
                  <td>{{ report.support }}</td>
                </tr>
              </tbody>
            </table>
          </el-scrollbar>
        </el-card>

        <!-- 评估日志 -->
        <el-card v-if="selectedEvaluation.logs && selectedEvaluation.logs.length > 0" class="logs-card">
          <template #header>
            <div class="card-header">
              <span>评估日志</span>
            </div>
          </template>
          <el-scrollbar style="height: 200px;">
            <div class="log-item" v-for="(log, index) in selectedEvaluation.logs" :key="index">
              <span class="log-time">{{ log.time }}</span>
              <span class="log-content">{{ log.content }}</span>
            </div>
          </el-scrollbar>
        </el-card>
      </div>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="evaluationDetailsVisible = false">关闭</el-button>
          <el-button type="primary" @click="handleExportReport" :disabled="selectedEvaluation?.status !== 'completed'">
            导出报告
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import * as echarts from 'echarts'

interface EvaluationTask {
  id: number
  name: string
  modelId: number
  modelName: string
  datasetId: number
  datasetName: string
  evaluationType: 'classification' | 'regression' | 'generation' | 'retrieval'
  status: 'pending' | 'running' | 'completed' | 'failed'
  metrics?: {
    accuracy: number
    precision: number
    recall: number
    f1Score: number
    auc?: number
    loss?: number
    bleu?: number
    rouge?: number
  }
  confusionMatrix?: number[][]
  classLabels?: string[]
  classificationReport?: Record<string, {
    precision: number
    recall: number
    f1Score: number
    support: number
  }>
  logs?: { time: string; content: string }[]
  description?: string
  createTime: string
  startedTime?: string
  completedTime?: string
  duration: number
}

interface ModelOption {
  id: number
  name: string
}

interface DatasetOption {
  id: number
  name: string
}

const evaluationList = ref<EvaluationTask[]>([
  {
    id: 1,
    name: 'GPT-4 文本分类评估',
    modelId: 1,
    modelName: 'GPT-4',
    datasetId: 1,
    datasetName: '文本分类数据集',
    evaluationType: 'classification',
    status: 'completed',
    metrics: {
      accuracy: 0.923,
      precision: 0.918,
      recall: 0.926,
      f1Score: 0.922,
      auc: 0.978,
      loss: 0.215
    },
    confusionMatrix: [
      [185, 10, 5],
      [8, 190, 2],
      [3, 7, 190]
    ],
    classLabels: ['类别A', '类别B', '类别C'],
    classificationReport: {
      '类别A': { precision: 0.94, recall: 0.925, f1Score: 0.932, support: 200 },
      '类别B': { precision: 0.913, recall: 0.95, f1Score: 0.931, support: 200 },
      '类别C': { precision: 0.964, recall: 0.95, f1Score: 0.957, support: 200 }
    },
    logs: [
      { time: '2024-01-04 09:00:00', content: '评估任务开始' },
      { time: '2024-01-04 09:05:00', content: '加载模型和数据集' },
      { time: '2024-01-04 09:10:00', content: '开始评估...' },
      { time: '2024-01-04 09:30:00', content: '评估完成，正在生成报告' },
      { time: '2024-01-04 09:35:00', content: '评估任务完成' }
    ],
    description: '评估GPT-4模型在文本分类任务上的性能',
    createTime: '2024-01-04 08:50:00',
    startedTime: '2024-01-04 09:00:00',
    completedTime: '2024-01-04 09:35:00',
    duration: 2100
  },
  {
    id: 2,
    name: '文本嵌入模型检索评估',
    modelId: 2,
    modelName: 'text-embedding-ada-002',
    datasetId: 2,
    datasetName: '问答数据集',
    evaluationType: 'retrieval',
    status: 'completed',
    metrics: {
      accuracy: 0.887,
      precision: 0.872,
      recall: 0.895,
      f1Score: 0.883,
      loss: 0.187
    },
    logs: [
      { time: '2024-01-04 10:00:00', content: '评估任务开始' },
      { time: '2024-01-04 10:10:00', content: '加载嵌入模型和数据集' },
      { time: '2024-01-04 10:20:00', content: '开始生成嵌入...' },
      { time: '2024-01-04 10:45:00', content: '开始检索评估...' },
      { time: '2024-01-04 11:15:00', content: '评估任务完成' }
    ],
    description: '评估文本嵌入模型在信息检索任务上的性能',
    createTime: '2024-01-04 09:50:00',
    startedTime: '2024-01-04 10:00:00',
    completedTime: '2024-01-04 11:15:00',
    duration: 4500
  },
  {
    id: 3,
    name: '自定义模型评估',
    modelId: 3,
    modelName: 'Custom Model 1',
    datasetId: 3,
    datasetName: '自定义数据集',
    evaluationType: 'regression',
    status: 'pending',
    logs: [],
    description: '评估自定义回归模型的性能',
    createTime: '2024-01-04 11:30:00',
    duration: 0
  }
])

const modelOptions = ref<ModelOption[]>([
  { id: 1, name: 'GPT-4' },
  { id: 2, name: 'text-embedding-ada-002' },
  { id: 3, name: 'Custom Model 1' }
])

const datasetOptions = ref<DatasetOption[]>([
  { id: 1, name: '文本分类数据集' },
  { id: 2, name: '问答数据集' },
  { id: 3, name: '自定义数据集' }
])

const newEvaluationVisible = ref(false)
const evaluationDetailsVisible = ref(false)
const selectedEvaluation = ref<EvaluationTask | null>(null)
const evaluationFormRef = ref()
const evaluationForm = reactive({
  modelId: null,
  datasetId: null,
  evaluationType: '',
  metrics: [],
  batchSize: 32,
  description: ''
})

const evaluationRules = {
  modelId: [
    { required: true, message: '请选择要评估的模型', trigger: 'change' }
  ],
  datasetId: [
    { required: true, message: '请选择评估数据集', trigger: 'change' }
  ],
  evaluationType: [
    { required: true, message: '请选择评估类型', trigger: 'change' }
  ],
  metrics: [
    { required: true, message: '请选择评估指标', trigger: 'change' }
  ],
  batchSize: [
    { required: true, message: '请输入批量大小', trigger: 'blur' },
    { type: 'number', min: 1, max: 1024, message: '批量大小在 1 到 1024 之间', trigger: 'blur' }
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

const getEvaluationTypeText = (type: string) => {
  switch (type) {
    case 'classification':
      return '分类评估'
    case 'regression':
      return '回归评估'
    case 'generation':
      return '生成评估'
    case 'retrieval':
      return '检索评估'
    default:
      return type
  }
}

const formatDuration = (seconds: number): string => {
  const hours = Math.floor(seconds / 3600)
  const minutes = Math.floor((seconds % 3600) / 60)
  const secs = seconds % 60
  return `${hours}h ${minutes}m ${secs}s`
}

const getCellClass = (value: number, actual: number, predicted: number): string => {
  if (actual === predicted) {
    return 'correct-prediction'
  }
  return 'incorrect-prediction'
}

const handleNewEvaluation = () => {
  resetEvaluationForm()
  newEvaluationVisible.value = true
}

const handleViewDetails = (task: EvaluationTask) => {
  selectedEvaluation.value = task
  evaluationDetailsVisible.value = true
}

const handleDeleteEvaluation = (id: number) => {
  ElMessageBox.confirm('确定要删除该评估任务吗？', '删除确认', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(() => {
      const index = evaluationList.value.findIndex(task => task.id === id)
      if (index !== -1) {
        evaluationList.value.splice(index, 1)
        ElMessage.success('评估任务删除成功')
      }
    })
    .catch(() => {
      // 取消删除
    })
}

const handleExportReport = () => {
  if (selectedEvaluation.value && selectedEvaluation.value.status === 'completed') {
    ElMessage.success('报告导出功能开发中...')
    // 这里可以实现实际的导出逻辑
  }
}

const resetEvaluationForm = () => {
  evaluationForm.modelId = null
  evaluationForm.datasetId = null
  evaluationForm.evaluationType = ''
  evaluationForm.metrics = []
  evaluationForm.batchSize = 32
  evaluationForm.description = ''
  evaluationFormRef.value?.resetFields()
}

const handleSubmitEvaluation = () => {
  evaluationFormRef.value?.validate((valid: boolean) => {
    if (valid) {
      const model = modelOptions.value.find(m => m.id === evaluationForm.modelId)
      const dataset = datasetOptions.value.find(d => d.id === evaluationForm.datasetId)
      
      if (model && dataset) {
        const newTask: EvaluationTask = {
          id: Date.now(),
          name: `${model.name} ${getEvaluationTypeText(evaluationForm.evaluationType)}`,
          modelId: model.id,
          modelName: model.name,
          datasetId: dataset.id,
          datasetName: dataset.name,
          evaluationType: evaluationForm.evaluationType as 'classification' | 'regression' | 'generation' | 'retrieval',
          status: 'pending',
          logs: [],
          description: evaluationForm.description,
          createTime: new Date().toISOString().slice(0, 19).replace('T', ' '),
          duration: 0
        }
        evaluationList.value.push(newTask)
        newEvaluationVisible.value = false
        ElMessage.success('评估任务已创建')
      }
    }
  })
}

const handleCloseDetails = () => {
  selectedEvaluation.value = null
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

.evaluation-details {
  max-height: 700px;
  overflow-y: auto;
}

.metrics-card {
  margin-bottom: 20px;
}

.metric-item {
  text-align: center;
  padding: 15px;
  background-color: #f5f7fa;
  border-radius: 8px;
}

.metric-label {
  font-size: 14px;
  color: #606266;
  margin-bottom: 8px;
}

.metric-value {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
}

.confusion-matrix-card {
  margin-bottom: 20px;
}

.confusion-matrix-container {
  overflow-x: auto;
}

.confusion-matrix {
  width: 100%;
  border-collapse: collapse;
}

.confusion-matrix th,
.confusion-matrix td {
  border: 1px solid #ebeef5;
  padding: 10px;
  text-align: center;
}

.confusion-matrix th {
  background-color: #f5f7fa;
  font-weight: bold;
}

.correct-prediction {
  background-color: #f0f9eb;
  color: #67c23a;
  font-weight: bold;
}

.incorrect-prediction {
  background-color: #fef0f0;
  color: #f56c6c;
  font-weight: bold;
}

.classification-report-card {
  margin-bottom: 20px;
}

.classification-report {
  width: 100%;
  border-collapse: collapse;
}

.classification-report th,
.classification-report td {
  border: 1px solid #ebeef5;
  padding: 10px;
  text-align: center;
}

.classification-report th {
  background-color: #f5f7fa;
  font-weight: bold;
}

.logs-card {
  margin-bottom: 20px;
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