<template>
  <div class="agent-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <h3>AGENT (智能代理) 配置</h3>
          <el-button type="primary" @click="saveConfig">保存配置</el-button>
        </div>
      </template>
      
      <el-form :model="agentConfig" :rules="formRules" ref="agentFormRef" label-width="150px">
        <el-form-item label="代理类型" prop="agentType">
          <el-select v-model="agentConfig.agentType" placeholder="请选择代理类型">
            <el-option label="通用代理" value="general" />
            <el-option label="代码代理" value="code" />
            <el-option label="数据分析代理" value="data" />
            <el-option label="流程代理" value="workflow" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="代理模型" prop="modelId">
          <el-select v-model="agentConfig.modelId" placeholder="请选择代理模型">
            <el-option
              v-for="model in modelOptions"
              :key="model.id"
              :label="model.name"
              :value="model.id"
            />
          </el-select>
        </el-form-item>
        
        <el-form-item label="可用工具" prop="tools">
          <el-select v-model="agentConfig.tools" multiple placeholder="请选择可用工具">
            <el-option label="代码执行" value="code_execution" />
            <el-option label="数据查询" value="data_query" />
            <el-option label="API调用" value="api_call" />
            <el-option label="文档检索" value="document_search" />
            <el-option label="知识图谱" value="knowledge_graph" />
            <el-option label="流程执行" value="workflow_execution" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="最大执行步骤" prop="maxSteps">
          <el-input-number v-model="agentConfig.maxSteps" :min="1" :max="50" :step="1" />
        </el-form-item>
        
        <el-form-item label="温度参数" prop="temperature">
          <el-slider v-model="agentConfig.temperature" :min="0" :max="1" :step="0.1" />
        </el-form-item>
        
        <el-form-item label="启用思考过程" prop="enableThoughtProcess">
          <el-switch v-model="agentConfig.enableThoughtProcess" />
        </el-form-item>
        
        <el-form-item label="启用安全检查" prop="enableSafetyCheck">
          <el-switch v-model="agentConfig.enableSafetyCheck" />
        </el-form-item>
      </el-form>
    </el-card>
    
    <el-card class="mt-20">
      <template #header>
        <div class="card-header">
          <h3>与代理交互</h3>
        </div>
      </template>
      
      <el-input
        v-model="queryText"
        type="textarea"
        placeholder="请输入您的问题或任务..."
        :rows="4"
        class="mb-20"
      />
      
      <el-button type="primary" @click="executeAgent" :loading="executing">
        {{ executing ? '执行中...' : '执行任务' }}
      </el-button>
      
      <el-divider />
      
      <div v-if="agentResponse" class="response-container">
        <h4>代理响应:</h4>
        <div class="response-content">{{ agentResponse }}</div>
      </div>
      
      <div v-if="thoughtProcess.length > 0" class="thought-container mt-20">
        <h4>思考过程:</h4>
        <el-timeline>
          <el-timeline-item
            v-for="(step, index) in thoughtProcess"
            :key="index"
            :timestamp="`步骤 ${index + 1}`"
          >
            <div class="thought-type">{{ step.type }}:</div>
            <div class="thought-content">{{ step.content }}</div>
            <div v-if="step.tool" class="thought-tool">
              使用工具: {{ step.tool }}
              <span v-if="step.toolInput" class="tool-input">输入: {{ step.toolInput }}</span>
              <span v-if="step.toolOutput" class="tool-output">输出: {{ step.toolOutput }}</span>
            </div>
          </el-timeline-item>
        </el-timeline>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElForm, ElFormItem, ElSelect, ElOption, ElSlider, ElSwitch, ElInput, ElButton, ElDivider, ElTimeline, ElTimelineItem } from 'element-plus'

interface AgentConfig {
  agentType: string
  modelId: number | null
  tools: string[]
  maxSteps: number
  temperature: number
  enableThoughtProcess: boolean
  enableSafetyCheck: boolean
}

interface ModelOption {
  id: number
  name: string
}

interface ThoughtStep {
  type: string
  content: string
  tool?: string
  toolInput?: string
  toolOutput?: string
}

const agentConfig = reactive<AgentConfig>({
  agentType: 'general',
  modelId: null,
  tools: [],
  maxSteps: 10,
  temperature: 0.7,
  enableThoughtProcess: true,
  enableSafetyCheck: true
})

const modelOptions = ref<ModelOption[]>([
  { id: 1, name: 'GPT-4 Agent' },
  { id: 2, name: 'Claude 3 Sonnet Agent' },
  { id: 3, name: 'Llama 2 70B Agent' },
  { id: 4, name: 'Gemini Pro Agent' }
])

const queryText = ref('')
const agentResponse = ref('')
const thoughtProcess = ref<ThoughtStep[]>([])
const executing = ref(false)
const agentFormRef = ref<InstanceType<typeof ElForm> | null>(null)

const formRules = {
  agentType: [
    { required: true, message: '请选择代理类型', trigger: 'change' }
  ],
  modelId: [
    { required: true, message: '请选择代理模型', trigger: 'change' }
  ],
  tools: [
    { required: true, message: '请选择至少一个可用工具', trigger: 'change' }
  ],
  maxSteps: [
    { required: true, message: '请输入最大执行步骤', trigger: 'blur' }
  ],
  temperature: [
    { required: true, message: '请输入温度参数', trigger: 'blur' }
  ]
}

const saveConfig = () => {
  agentFormRef.value?.validate((valid: boolean) => {
    if (valid) {
      // 这里可以添加保存配置的逻辑
      ElMessage.success('Agent配置保存成功')
    }
  })
}

const executeAgent = () => {
  if (!queryText.value.trim()) {
    ElMessage.warning('请输入您的问题或任务')
    return
  }
  
  executing.value = true
  agentResponse.value = ''
  thoughtProcess.value = []
  
  // 模拟Agent执行过程
  setTimeout(() => {
    // 模拟思考过程
    thoughtProcess.value = [
      {
        type: '思考',
        content: '我需要理解用户的问题并制定解决方案'
      },
      {
        type: '工具使用',
        content: '使用文档检索工具查找相关信息',
        tool: 'document_search',
        toolInput: queryText.value,
        toolOutput: '找到了3篇相关文档'
      },
      {
        type: '思考',
        content: '分析检索到的文档内容'
      },
      {
        type: '工具使用',
        content: '使用数据查询工具验证信息',
        tool: 'data_query',
        toolInput: '查询相关数据',
        toolOutput: '验证了文档中的关键信息'
      },
      {
        type: '思考',
        content: '综合信息生成最终答案'
      }
    ]
    
    // 模拟生成的答案
    agentResponse.value = '这是智能代理生成的答案。代理通过多步骤思考和工具使用，提供了全面且准确的解决方案。'
    executing.value = false
  }, 3000)
}

onMounted(() => {
  // 加载默认配置
  agentConfig.modelId = 1
  agentConfig.tools = ['document_search', 'data_query', 'api_call']
})
</script>

<style scoped>
.agent-container {
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

.thought-container {
  padding: 15px;
  background-color: #f5f7fa;
  border-radius: 8px;
}

.thought-type {
  font-weight: bold;
  margin-bottom: 5px;
}

.thought-content {
  margin-bottom: 5px;
}

.thought-tool {
  margin-top: 5px;
  padding: 5px;
  background-color: #e6f7ff;
  border-radius: 4px;
  font-size: 14px;
}

.tool-input {
  margin-left: 10px;
  color: #1890ff;
}

.tool-output {
  margin-left: 10px;
  color: #52c41a;
}
</style>