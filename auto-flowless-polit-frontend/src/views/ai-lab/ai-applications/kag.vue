<template>
  <div class="kag-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <h3>KAG (知识图谱增强) 配置</h3>
          <el-button type="primary" @click="saveConfig">保存配置</el-button>
        </div>
      </template>
      
      <el-form :model="kagConfig" :rules="formRules" ref="kagFormRef" label-width="150px">
        <el-form-item label="知识图谱选择" prop="graphId">
          <el-select v-model="kagConfig.graphId" placeholder="请选择知识图谱">
            <el-option
              v-for="graph in graphOptions"
              :key="graph.id"
              :label="graph.name"
              :value="graph.id"
            />
          </el-select>
        </el-form-item>
        
        <el-form-item label="实体抽取模型" prop="entityExtractorId">
          <el-select v-model="kagConfig.entityExtractorId" placeholder="请选择实体抽取模型">
            <el-option
              v-for="model in entityModelOptions"
              :key="model.id"
              :label="model.name"
              :value="model.id"
            />
          </el-select>
        </el-form-item>
        
        <el-form-item label="关系抽取模型" prop="relationExtractorId">
          <el-select v-model="kagConfig.relationExtractorId" placeholder="请选择关系抽取模型">
            <el-option
              v-for="model in relationModelOptions"
              :key="model.id"
              :label="model.name"
              :value="model.id"
            />
          </el-select>
        </el-form-item>
        
        <el-form-item label="置信度阈值" prop="confidenceThreshold">
          <el-slider v-model="kagConfig.confidenceThreshold" :min="0" :max="1" :step="0.1" />
        </el-form-item>
        
        <el-form-item label="最大实体数" prop="maxEntities">
          <el-input-number v-model="kagConfig.maxEntities" :min="1" :max="20" :step="1" />
        </el-form-item>
        
        <el-form-item label="启用实体链接" prop="enableEntityLinking">
          <el-switch v-model="kagConfig.enableEntityLinking" />
        </el-form-item>
        
        <el-form-item label="启用关系预测" prop="enableRelationPrediction">
          <el-switch v-model="kagConfig.enableRelationPrediction" />
        </el-form-item>
      </el-form>
    </el-card>
    
    <el-card class="mt-20">
      <template #header>
        <div class="card-header">
          <h3>KAG 功能使用</h3>
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
      
      <div v-if="extractedEntities.length > 0" class="entities-container mt-20">
        <h4>提取到的实体:</h4>
        <el-timeline>
          <el-timeline-item
            v-for="(entity, index) in extractedEntities"
            :key="index"
            :timestamp="`类型: ${entity.type}，置信度: ${entity.confidence.toFixed(2)}`"
          >
            <div class="entity-name">{{ entity.name }}</div>
            <div class="entity-relations" v-if="entity.relations.length > 0">
              关系: 
              <el-tag v-for="relation in entity.relations" :key="relation.id" size="small" type="info">
                {{ relation.name }} -> {{ relation.target }}
              </el-tag>
            </div>
          </el-timeline-item>
        </el-timeline>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElForm, ElFormItem, ElSelect, ElOption, ElSlider, ElSwitch, ElInput, ElButton, ElDivider, ElTimeline, ElTimelineItem, ElTag } from 'element-plus'

interface KagConfig {
  graphId: number | null
  entityExtractorId: number | null
  relationExtractorId: number | null
  confidenceThreshold: number
  maxEntities: number
  enableEntityLinking: boolean
  enableRelationPrediction: boolean
}

interface GraphOption {
  id: number
  name: string
}

interface ModelOption {
  id: number
  name: string
}

interface ExtractedEntity {
  name: string
  type: string
  confidence: number
  relations: { id: number; name: string; target: string }[]
}

const kagConfig = reactive<KagConfig>({
  graphId: null,
  entityExtractorId: null,
  relationExtractorId: null,
  confidenceThreshold: 0.7,
  maxEntities: 10,
  enableEntityLinking: true,
  enableRelationPrediction: true
})

const graphOptions = ref<GraphOption[]>([
  { id: 1, name: '企业知识图谱' },
  { id: 2, name: '产品知识图谱' },
  { id: 3, name: '行业知识图谱' },
  { id: 4, name: '学术知识图谱' }
])

const entityModelOptions = ref<ModelOption[]>([
  { id: 1, name: 'BERT Entity Extractor' },
  { id: 2, name: 'spaCy NER' },
  { id: 3, name: 'Custom Entity Model' }
])

const relationModelOptions = ref<ModelOption[]>([
  { id: 1, name: 'BERT Relation Extractor' },
  { id: 2, name: 'REBEL' },
  { id: 3, name: 'Custom Relation Model' }
])

const queryText = ref('')
const response = ref('')
const extractedEntities = ref<ExtractedEntity[]>([])
const generating = ref(false)
const kagFormRef = ref<InstanceType<typeof ElForm> | null>(null)

const formRules = {
  graphId: [
    { required: true, message: '请选择知识图谱', trigger: 'change' }
  ],
  entityExtractorId: [
    { required: true, message: '请选择实体抽取模型', trigger: 'change' }
  ],
  relationExtractorId: [
    { required: true, message: '请选择关系抽取模型', trigger: 'change' }
  ],
  confidenceThreshold: [
    { required: true, message: '请输入置信度阈值', trigger: 'blur' }
  ],
  maxEntities: [
    { required: true, message: '请输入最大实体数', trigger: 'blur' }
  ]
}

const saveConfig = () => {
  kagFormRef.value?.validate((valid: boolean) => {
    if (valid) {
      // 这里可以添加保存配置的逻辑
      ElMessage.success('KAG配置保存成功')
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
  extractedEntities.value = []
  
  // 模拟KAG生成过程
  setTimeout(() => {
    // 模拟提取到的实体和关系
    extractedEntities.value = [
      {
        name: '产品A',
        type: '产品',
        confidence: 0.95,
        relations: [
          { id: 1, name: '属于', target: '产品线B' },
          { id: 2, name: '包含', target: '功能C' }
        ]
      },
      {
        name: '技术D',
        type: '技术',
        confidence: 0.92,
        relations: [
          { id: 3, name: '应用于', target: '产品A' }
        ]
      },
      {
        name: '市场E',
        type: '市场',
        confidence: 0.88,
        relations: [
          { id: 4, name: '针对', target: '用户群体F' }
        ]
      }
    ]
    
    // 模拟生成的答案
    response.value = '这是基于知识图谱增强生成的答案。KAG系统通过提取实体和关系，结合知识图谱中的结构化信息，提供了更加精准和结构化的回答。'
    generating.value = false
  }, 2500)
}

onMounted(() => {
  // 加载默认配置
  kagConfig.graphId = 1
  kagConfig.entityExtractorId = 1
  kagConfig.relationExtractorId = 1
})
</script>

<style scoped>
.kag-container {
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

.entities-container {
  padding: 15px;
  background-color: #f5f7fa;
  border-radius: 8px;
}

.entity-name {
  font-weight: bold;
  margin-bottom: 5px;
}

.entity-relations {
  margin-top: 5px;
}
</style>