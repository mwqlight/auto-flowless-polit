<template>
  <div class="agent-process-create">
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <span>智能体流程创建</span>
        </div>
      </template>
      
      <el-steps :active="activeStep" finish-status="success" align-center>
        <el-step title="基础信息" description="设置流程基本信息"></el-step>
        <el-step title="智能体配置" description="配置智能体参数"></el-step>
        <el-step title="流程设计" description="设计流程节点"></el-step>
        <el-step title="完成" description="发布流程"></el-step>
      </el-steps>

      <!-- 步骤1: 基础信息 -->
      <div v-if="activeStep === 0" class="step-content">
        <el-form ref="step1Form" :model="step1Form" label-width="120px">
          <el-form-item label="流程名称" prop="name" required>
            <el-input v-model="step1Form.name" placeholder="请输入流程名称" />
          </el-form-item>
          <el-form-item label="图标配置" prop="logo" required>
            <el-input v-model="step1Form.logo" placeholder="请输入图标配置" />
          </el-form-item>
          <el-form-item label="分组ID" prop="groupId" required>
            <el-input v-model.number="step1Form.groupId" placeholder="请输入分组ID" type="number" />
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="step1Form.remark" placeholder="请输入备注" type="textarea" />
          </el-form-item>
          <el-form-item label="管理员" prop="admin" required>
            <el-input v-model="step1Form.admin" placeholder="请输入管理员" />
          </el-form-item>
        </el-form>
      </div>

      <!-- 步骤2: 智能体配置 -->
      <div v-if="activeStep === 1" class="step-content">
        <el-form ref="step2Form" :model="step2Form" label-width="120px">
          <el-form-item label="最大执行步骤" prop="maxSteps">
            <el-input v-model.number="step2Form.maxSteps" placeholder="请输入最大执行步骤" type="number" />
          </el-form-item>
          <el-form-item label="温度参数" prop="temperature">
            <el-input v-model.number="step2Form.temperature" placeholder="请输入温度参数" type="number" step="0.1" />
          </el-form-item>
          <el-form-item label="启用思考过程" prop="enableThoughtProcess">
            <el-switch v-model="step2Form.enableThoughtProcess" />
          </el-form-item>
          <el-form-item label="启用安全检查" prop="enableSafetyCheck">
            <el-switch v-model="step2Form.enableSafetyCheck" />
          </el-form-item>
          <el-form-item label="智能体配置" prop="agentConfig">
            <el-input v-model="step2Form.agentConfig" placeholder="请输入智能体配置" type="textarea" rows="5" />
          </el-form-item>
        </el-form>
      </div>

      <!-- 步骤3: 流程设计 -->
      <div v-if="activeStep === 2" class="step-content">
        <el-form ref="step3Form" :model="step3Form" label-width="120px">
          <el-form-item label="表单设置内容" prop="formItems" required>
            <el-input v-model="step3Form.formItems" placeholder="请输入表单设置内容" type="textarea" rows="5" />
          </el-form-item>
          <el-form-item label="PC表单内容" prop="formItemsPc">
            <el-input v-model="step3Form.formItemsPc" placeholder="请输入PC表单内容" type="textarea" rows="5" />
          </el-form-item>
          <el-form-item label="流程设置内容" prop="process" required>
            <el-input v-model="step3Form.process" placeholder="请输入流程设置内容" type="textarea" rows="10" />
          </el-form-item>
        </el-form>
      </div>

      <!-- 步骤4: 完成 -->
      <div v-if="activeStep === 3" class="step-content">
        <div class="success-message">
          <el-icon :size="48" color="#67C23A"><Check /></el-icon>
          <h3>智能体流程创建成功！</h3>
          <p>您的智能体流程已成功创建，可以开始使用了。</p>
        </div>
      </div>

      <!-- 步骤导航 -->
      <div class="step-navigation">
        <el-button v-if="activeStep > 0" @click="prevStep">上一步</el-button>
        <el-button v-if="activeStep < 3" type="primary" @click="nextStep">下一步</el-button>
        <el-button v-if="activeStep === 3" type="success" @click="finish">完成</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { Check } from '@element-plus/icons-vue'
import { createAgentProcess } from '@/api/ai-lab/agent-process'

// 步骤状态
const activeStep = ref(0)

// 表单数据
const step1Form = ref({
  name: '',
  logo: '',
  groupId: null,
  remark: '',
  admin: ''
})

const step2Form = ref({
  maxSteps: 10,
  temperature: 0.7,
  enableThoughtProcess: true,
  enableSafetyCheck: true,
  agentConfig: ''
})

const step3Form = ref({
  formItems: '',
  formItemsPc: '',
  process: ''
})

// 下一步
const nextStep = () => {
  if (activeStep.value === 0) {
    // 验证步骤1
    if (!step1Form.value.name || !step1Form.value.logo || !step1Form.value.groupId || !step1Form.value.admin) {
      ElMessage.error('请填写所有必填项')
      return
    }
  } else if (activeStep.value === 1) {
    // 验证步骤2
    if (!step2Form.value.maxSteps || !step2Form.value.temperature) {
      ElMessage.error('请填写所有必填项')
      return
    }
  } else if (activeStep.value === 2) {
    // 验证步骤3
    if (!step3Form.value.formItems || !step3Form.value.process) {
      ElMessage.error('请填写所有必填项')
      return
    }
    // 创建流程
    createProcess()
    return
  }
  activeStep.value++
}

// 上一步
const prevStep = () => {
  activeStep.value--
}

// 创建流程
const createProcess = async () => {
  try {
    const formData = {
      ...step1Form.value,
      ...step2Form.value,
      ...step3Form.value
    }
    await createAgentProcess(formData)
    ElMessage.success('智能体流程创建成功')
    activeStep.value++
  } catch (error) {
    ElMessage.error('智能体流程创建失败')
  }
}

// 完成
const finish = () => {
  ElMessage.success('流程创建完成')
  // 跳转到流程列表
  window.location.href = '/ai-lab/agent-process/list'
}
</script>

<style scoped>
.agent-process-create {
  padding: 20px;
}

.card-header {
  font-size: 18px;
  font-weight: bold;
}

.step-content {
  padding: 30px 0;
}

.step-navigation {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 30px;
}

.success-message {
  text-align: center;
  padding: 50px 0;
}

.success-message h3 {
  margin-top: 20px;
  color: #67C23A;
}

.success-message p {
  margin-top: 10px;
  color: #999;
}
</style>