<template>
  <div class="rag-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <h3>RAG (检索增强生成) 配置</h3>
          <el-button type="primary" @click="saveConfig">保存配置</el-button>
        </div>
      </template>
      
      <el-form :model="ragConfig" :rules="formRules" ref="ragFormRef" label-width="150px">
        <el-form-item label="模型选择" prop="modelId">
          <el-select v-model="ragConfig.modelId" placeholder="请选择RAG模型">
            <el-option
              v-for="model in modelOptions"
              :key="model.id"
              :label="model.name"
              :value="model.id"
            />
          </el-select>
        </el-form-item>
        
        <el-form-item label="检索数据源" prop="dataSources">
          <el-select v-model="ragConfig.dataSources" multiple placeholder="请选择数据源">
            <el-option label="文档知识库" value="document" />
            <el-option label="数据库" value="database" />
            <el-option label="API接口" value="api" />
            <el-option label="向量数据库" value="vector" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="检索策略" prop="retrievalStrategy">
          <el-select v-model="ragConfig.retrievalStrategy" placeholder="请选择检索策略">
            <el-option label="相似度检索" value="similarity" />
            <el-option label="BM25检索" value="bm25" />
            <el-option label="混合检索" value="hybrid" />
            <el-option label="语义检索" value="semantic" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="返回结果数" prop="topK">
          <el-input-number v-model="ragConfig.topK" :min="1" :max="50" :step="1" />
        </el-form-item>
        
        <el-form-item label="上下文窗口大小" prop="contextWindow">
          <el-input-number v-model="ragConfig.contextWindow" :min="100" :max="10000" :step="100" />
        </el-form-item>
        
        <el-form-item label="温度参数" prop="temperature">
          <el-slider v-model="ragConfig.temperature" :min="0" :max="1" :step="0.1" />
        </el-form-item>
        
        <el-form-item label="是否启用重排序" prop="enableRerank">
          <el-switch v-model="ragConfig.enableRerank" />
        </el-form-item>
      </el-form>
    </el-card>
    
    <el-card class="mt-20">
      <template #header>
        <div class="card-header">
          <h3>RAG 功能使用</h3>
        </div>
      </template>
      
      <el-input
        v-model="queryText"
        type="textarea"
        placeholder="请输入您的问题..."
        :rows="4"
        class="mb-20"
      />
      
      <el-button type="primary" @click="generateResponse" :loading="generating">
        {{ generating ? '生成中...' : '生成答案' }}
      </el-button>
      
      <el-divider />
      
      <div v-if="response" class="response-container">
        <h4>生成结果:</h4>
        <div class="response-content">{{ response }}</div>
      </div>
      
      <div v-if="retrievedDocs.length > 0" class="docs-container mt-20">
        <h4>检索到的相关文档:</h4>
        <el-timeline>
          <el-timeline-item
            v-for="(doc, index) in retrievedDocs"
            :key="index"
            :timestamp="`相关性: ${doc.score.toFixed(2)}`"
          >
            <div class="doc-title">{{ doc.title }}</div>
            <div class="doc-content">{{ doc.content.substring(0, 100) }}...</div>
          </el-timeline-item>
        </el-timeline>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElForm, ElFormItem, ElSelect, ElOption, ElInputNumber, ElSlider, ElSwitch, ElInput, ElButton, ElDivider, ElTimeline, ElTimelineItem } from 'element-plus'

interface RagConfig {
  modelId: number | null
  dataSources: string[]
  retrievalStrategy: string
  topK: number
  contextWindow: number
  temperature: number
  enableRerank: boolean
}

interface ModelOption {
  id: number
  name: string
}

interface RetrievedDoc {
  title: string
  content: string
  score: number
}

const ragConfig = reactive<RagConfig>({
  modelId: null,
  dataSources: [],
  retrievalStrategy: 'similarity',
  topK: 5,
  contextWindow: 2048,
  temperature: 0.7,
  enableRerank: false
})

const modelOptions = ref<ModelOption[]>([
  { id: 1, name: 'GPT-4 RAG' },
  { id: 2, name: 'Llama 2 70B RAG' },
  { id: 3, name: 'Claude 3 Opus RAG' }
])

const queryText = ref('')
const response = ref('')
const retrievedDocs = ref<RetrievedDoc[]>([])
const generating = ref(false)
const ragFormRef = ref<InstanceType<typeof ElForm> | null>(null)

const formRules = {
  modelId: [
    { required: true, message: '请选择RAG模型', trigger: 'change' }
  ],
  dataSources: [
    { required: true, message: '请选择至少一个数据源', trigger: 'change' }
  ],
  retrievalStrategy: [
    { required: true, message: '请选择检索策略', trigger: 'change' }
  ],
  topK: [
    { required: true, message: '请输入返回结果数', trigger: 'blur' }
  ],
  contextWindow: [
    { required: true, message: '请输入上下文窗口大小', trigger: 'blur' }
  ],
  temperature: [
    { required: true, message: '请输入温度参数', trigger: 'blur' }
  ]
}

const saveConfig = () => {
  ragFormRef.value?.validate((valid: boolean) => {
    if (valid) {
      // 这里可以添加保存配置的逻辑
      ElMessage.success('RAG配置保存成功')
    }
  })
}

const generateResponse = () => {
  if (!queryText.value.trim()) {
    ElMessage.warning('请输入您的问题')
    return
  }
  
  generating.value = true
  response.value = ''
  retrievedDocs.value = []
  
  // 模拟RAG生成过程
  setTimeout(() => {
    // 模拟检索到的文档
    retrievedDocs.value = [
      { title: '文档1', content: '这是文档1的内容...', score: 0.92 },
      { title: '文档2', content: '这是文档2的内容...', score: 0.88 },
      { title: '文档3', content: '这是文档3的内容...', score: 0.85 }
    ]
    
    // 模拟生成的答案
    response.value = '这是基于检索到的文档生成的答案。RAG系统通过检索相关文档并结合生成模型，提供了更加准确和可靠的回答。'
    generating.value = false
  }, 2000)
}

onMounted(() => {
  // 加载默认配置
  ragConfig.modelId = 1
  ragConfig.dataSources = ['document', 'vector']
})
</script>

<style scoped>
.rag-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.mt-20 {
  margin-top: 20px;
}

.mb-20 {
  margin-bottom: 20px;
}

.response-container {
  padding: 15px;
  background-color: #f5f7fa;
  border-radius: 8px;
}

.response-content {
  margin-top: 10px;
  white-space: pre-wrap;
}

.docs-container {
  padding: 15px;
  background-color: #f5f7fa;
  border-radius: 8px;
}

.doc-title {
  font-weight: bold;
  margin-bottom: 5px;
}

.doc-content {
  color: #606266;
  font-size: 14px;
}
</style>