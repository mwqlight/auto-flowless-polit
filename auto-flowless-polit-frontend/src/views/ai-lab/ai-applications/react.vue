<template>
  <div class="react-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <h3>REACT (Reasoning and Acting) 配置</h3>
          <el-button type="primary" @click="saveConfig">保存配置</el-button>
        </div>
      </template>
      
      <el-form :model="reactConfig" :rules="formRules" ref="reactFormRef" label-width="150px">
        <el-form-item label="代理模型" prop="modelId">
          <el-select v-model="reactConfig.modelId" placeholder="请选择REACT代理模型">
            <el-option
              v-for="model in modelOptions"
              :key="model.id"
              :label="model.name"
              :value="model.id"
            />
          </el-select>
        </el-form-item>
        
        <el-form-item label="最大推理步骤" prop="maxReasoningSteps">
          <el-input-number v-model="reactConfig.maxReasoningSteps" :min="1" :max="50" :step="1" />
        </el-form-item>
        
        <el-form-item label="可用工具" prop="tools">
          <el-select v-model="reactConfig.tools" multiple placeholder="请选择可用工具">
            <el-option label="网络搜索" value="web_search" />
            <el-option label="代码执行" value="code_execution" />
            <el-option label="数据查询" value="data_query" />
            <el-option label="API调用" value="api_call" />
            <el-option label="文档检索" value="document_search" />
            <el-option label="计算器" value="calculator" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="温度参数" prop="temperature">
          <el-slider v-model="reactConfig.temperature" :min="0" :max="1" :step="0.1" />
        </el-form-item>
        
        <el-form-item label="启用思考过程" prop="enableThoughtProcess">
          <el-switch v-model="reactConfig.enableThoughtProcess" />
        </el-form-item>
        
        <el-form-item label="启用工具使用" prop="enableToolUse">
          <el-switch v-model="reactConfig.enableToolUse" />
        </el-form-item>
        
        <el-form-item label="启用自我反思" prop="enableSelfReflection">
          <el-switch v-model="reactConfig.enableSelfReflection" />
        </el-form-item>
      </el-form>
    </el-card>
    
    <el-card class="mt-20">
      <template #header>
        <div class="card-header">
          <h3>与REACT代理交互</h3>
        </div>
      </template>
      
      <el-input
        v-model="queryText"
        type="textarea"
        placeholder="请输入您的问题或任务..."
        :rows="4"
        class="mb-20"
      />
      
      <el-button type="primary" @click="executeReact" :loading="executing">
        {{ executing ? '执行中...' : '执行任务' }}
      </el-button>
      
      <el-divider />
      
      <div v-if="reactResponse" class="response-container">
        <h4>代理响应:</h4>
        <div class="response-content">{{ reactResponse }}</div>
      </div>
      
      <div v-if="reasoningSteps.length > 0" class="reasoning-container mt-20">
        <h4>推理过程:</h4>
        <el-timeline>
          <el-timeline-item
            v-for="(step, index) in reasoningSteps"
            :key="index"
            :timestamp="`步骤 ${index + 1} - ${step.type}`"
          >
            <div class="step-content">{{ step.content }}</div>
            <div v-if="step.tool" class="step-tool">
              <strong>工具:</strong> {{ step.tool }}
              <span v-if="step.toolInput" class="tool-input"> | 输入: {{ step.toolInput }}</span>
              <span v-if="step.toolOutput" class="tool-output"> | 输出: {{ step.toolOutput }}</span>
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

interface ReactConfig {
  modelId: number | null
  maxReasoningSteps: number
  tools: string[]
  temperature: number
  enableThoughtProcess: boolean
  enableToolUse: boolean
  enableSelfReflection: boolean
}

interface ModelOption {
  id: number
  name: string
}

interface ReasoningStep {
  type: string
  content: string
  tool?: string
  toolInput?: string
  toolOutput?: string
}

const reactConfig = reactive<ReactConfig>({
  modelId: null,
  maxReasoningSteps: 10,
  tools: [],
  temperature: 0.7,
  enableThoughtProcess: true,
  enableToolUse: true,
  enableSelfReflection: true
})

const modelOptions = ref<ModelOption[]>([
  { id: 1, name: 'GPT-4 ReAct' },
  { id: 2, name: 'Claude 3 Sonnet ReAct' },
  { id: 3, name: 'Llama 2 70B ReAct' },
  { id: 4, name: 'Gemini Pro ReAct' }
])

const queryText = ref('')
const reactResponse = ref('')
const reasoningSteps = ref<ReasoningStep[]>([])
const executing = ref(false)
const reactFormRef = ref<InstanceType<typeof ElForm> | null>(null)

const formRules = {
  modelId: [
    { required: true, message: '请选择REACT代理模型', trigger: 'change' }
  ],
  maxReasoningSteps: [
    { required: true, message: '请输入最大推理步骤', trigger: 'blur' }
  ],
  tools: [
    { required: true, message: '请选择至少一个可用工具', trigger: 'change' }
  ],
  temperature: [
    { required: true, message: '请输入温度参数', trigger: 'blur' }
  ]
}

const saveConfig = () => {
  reactFormRef.value?.validate((valid: boolean) => {
    if (valid) {
      // 这里可以添加保存配置的逻辑
      ElMessage.success('REACT配置保存成功')
    }
  })
}

const executeReact = () => {
  if (!queryText.value.trim()) {
    ElMessage.warning('请输入您的问题或任务')
    return
  }
  
  executing.value = true
  reactResponse.value = ''
  reasoningSteps.value = []
  
  // 模拟REACT代理执行过程
  setTimeout(() => {
    // 模拟推理步骤
    reasoningSteps.value = [
      {
        type: '思考',
        content: '我需要理解用户的问题并制定解决方案'
      },
      {
        type: '工具使用',
        content: '使用网络搜索工具查找相关信息',
        tool: 'web_search',
        toolInput: queryText.value,
        toolOutput: '找到了5篇相关文章'
      },
      {
        type: '思考',
        content: '分析搜索结果，提取关键信息'
      },
      {
        type: '工具使用',
        content: '使用计算器验证数据',
        tool: 'calculator',
        toolInput: '123 + 456',
        toolOutput: '579'
      },
      {
        type: '反思',
        content: '检查解决方案的准确性和完整性'
      },
      {
        type: '思考',
        content: '综合信息生成最终答案'
      }
    ]
    
    // 模拟生成的答案
    reactResponse.value = '这是REACT代理生成的答案。代理通过多步骤推理和工具使用，提供了全面且准确的解决方案。'
    executing.value = false
  }, 3500)
}

onMounted(() => {
  // 加载默认配置
  reactConfig.modelId = 1
  reactConfig.tools = ['web_search', 'calculator', 'document_search']
})
</script>

<style scoped>
.react-container {
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

.reasoning-container {
  padding: 15px;
  background-color: #f5f7fa;
  border-radius: 8px;
}

.step-content {
  margin-bottom: 5px;
}

.step-tool {
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