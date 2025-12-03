<template>
  <div class="embedding-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <h3>EMBEDDING (嵌入服务) 配置</h3>
          <el-button type="primary" @click="saveConfig">保存配置</el-button>
        </div>
      </template>
      
      <el-form :model="embeddingConfig" :rules="formRules" ref="embeddingFormRef" label-width="150px">
        <el-form-item label="嵌入模型" prop="modelId">
          <el-select v-model="embeddingConfig.modelId" placeholder="请选择嵌入模型">
            <el-option
              v-for="model in modelOptions"
              :key="model.id"
              :label="model.name"
              :value="model.id"
            />
          </el-select>
        </el-form-item>
        
        <el-form-item label="嵌入维度" prop="dimension">
          <el-input-number v-model="embeddingConfig.dimension" :min="128" :max="12288" :step="128" />
        </el-form-item>
        
        <el-form-item label="归一化" prop="normalize">
          <el-switch v-model="embeddingConfig.normalize" />
        </el-form-item>
        
        <el-form-item label="批处理大小" prop="batchSize">
          <el-input-number v-model="embeddingConfig.batchSize" :min="1" :max="100" :step="1" />
        </el-form-item>
        
        <el-form-item label="并发请求数" prop="concurrency">
          <el-input-number v-model="embeddingConfig.concurrency" :min="1" :max="10" :step="1" />
        </el-form-item>
      </el-form>
    </el-card>
    
    <el-card class="mt-20">
      <template #header>
        <div class="card-header">
          <h3>嵌入服务使用</h3>
        </div>
      </template>
      
      <el-input
        v-model="textToEmbed"
        type="textarea"
        placeholder="请输入要生成嵌入的文本..."
        :rows="4"
        class="mb-20"
      />
      
      <el-button type="primary" @click="generateEmbedding" :loading="generating">
        {{ generating ? '生成中...' : '生成嵌入' }}
      </el-button>
      
      <el-divider />
      
      <div v-if="embeddingResult" class="result-container">
        <h4>嵌入结果:</h4>
        <div class="embedding-preview">
          {{ embeddingResult.embedding.slice(0, 10).join(', ') }}... (共 {{ embeddingResult.embedding.length }} 个维度)
        </div>
        <el-button type="primary" size="small" @click="copyEmbedding" class="mt-10">
          复制嵌入向量
        </el-button>
      </div>
      
      <div v-if="similarityResult" class="similarity-container mt-20">
        <h4>文本相似度:</h4>
        <el-progress :percentage="similarityResult.similarity * 100" :color="getProgressColor(similarityResult.similarity)" />
        <div class="similarity-text mt-10">
          相似度: {{ (similarityResult.similarity * 100).toFixed(2) }}%
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElForm, ElFormItem, ElSelect, ElOption, ElInputNumber, ElSwitch, ElInput, ElButton, ElDivider, ElProgress } from 'element-plus'

interface EmbeddingConfig {
  modelId: number | null
  dimension: number
  normalize: boolean
  batchSize: number
  concurrency: number
}

interface ModelOption {
  id: number
  name: string
}

interface EmbeddingResult {
  embedding: number[]
  model: string
  dimension: number
}

interface SimilarityResult {
  similarity: number
  text1: string
  text2: string
}

const embeddingConfig = reactive<EmbeddingConfig>({
  modelId: null,
  dimension: 1024,
  normalize: true,
  batchSize: 10,
  concurrency: 5
})

const modelOptions = ref<ModelOption[]>([
  { id: 1, name: 'text-embedding-ada-002' },
  { id: 2, name: 'text-embedding-3-small' },
  { id: 3, name: 'text-embedding-3-large' },
  { id: 4, name: 'all-MiniLM-L6-v2' },
  { id: 5, name: 'bge-base-en-v1.5' }
])

const textToEmbed = ref('')
const embeddingResult = ref<EmbeddingResult | null>(null)
const similarityResult = ref<SimilarityResult | null>(null)
const generating = ref(false)
const embeddingFormRef = ref<InstanceType<typeof ElForm> | null>(null)

const formRules = {
  modelId: [
    { required: true, message: '请选择嵌入模型', trigger: 'change' }
  ],
  dimension: [
    { required: true, message: '请输入嵌入维度', trigger: 'blur' }
  ],
  batchSize: [
    { required: true, message: '请输入批处理大小', trigger: 'blur' }
  ],
  concurrency: [
    { required: true, message: '请输入并发请求数', trigger: 'blur' }
  ]
}

const saveConfig = () => {
  embeddingFormRef.value?.validate((valid: boolean) => {
    if (valid) {
      // 这里可以添加保存配置的逻辑
      ElMessage.success('嵌入服务配置保存成功')
    }
  })
}

const generateEmbedding = () => {
  if (!textToEmbed.value.trim()) {
    ElMessage.warning('请输入要生成嵌入的文本')
    return
  }
  
  generating.value = true
  embeddingResult.value = null
  similarityResult.value = null
  
  // 模拟嵌入生成过程
  setTimeout(() => {
    // 模拟生成的嵌入向量
    const embedding = Array.from({ length: embeddingConfig.dimension }, () => Math.random() * 2 - 1)
    
    embeddingResult.value = {
      embedding,
      model: modelOptions.value.find(m => m.id === embeddingConfig.modelId)?.name || 'unknown',
      dimension: embeddingConfig.dimension
    }
    
    // 模拟文本相似度计算（使用示例文本）
    const sampleText = '这是一个示例文本，用于计算相似度'
    const similarity = Math.max(0.3, Math.min(0.95, Math.random()))
    
    similarityResult.value = {
      similarity,
      text1: textToEmbed.value.substring(0, 50) + (textToEmbed.value.length > 50 ? '...' : ''),
      text2: sampleText
    }
    
    generating.value = false
  }, 2000)
}

const copyEmbedding = () => {
  if (!embeddingResult.value) return
  
  const embeddingString = JSON.stringify(embeddingResult.value.embedding)
  navigator.clipboard.writeText(embeddingString)
    .then(() => {
      ElMessage.success('嵌入向量已复制到剪贴板')
    })
    .catch(() => {
      ElMessage.error('复制失败')
    })
}

const getProgressColor = (similarity: number) => {
  if (similarity > 0.8) return '#52c41a' // 绿色
  if (similarity > 0.6) return '#faad14' // 黄色
  if (similarity > 0.4) return '#fa8c16' // 橙色
  return '#f5222d' // 红色
}

onMounted(() => {
  // 加载默认配置
  embeddingConfig.modelId = 1
})
</script>

<style scoped>
.embedding-container {
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

.mt-10 {
  margin-top: 10px;
}

.mb-20 {
  margin-bottom: 20px;
}

.result-container {
  padding: 15px;
  background-color: #f5f7fa;
  border-radius: 8px;
}

.embedding-preview {
  margin-top: 10px;
  font-family: monospace;
  font-size: 14px;
  line-height: 1.5;
  word-break: break-all;
}

.similarity-container {
  padding: 15px;
  background-color: #f5f7fa;
  border-radius: 8px;
}

.similarity-text {
  margin-top: 5px;
  text-align: center;
  font-weight: bold;
}
</style>