<template>
  <div class="agent-flow-create">
    <el-steps :active="activeStep" finish-status="success" class="steps">
      <el-step title="基础信息" />
      <el-step title="表单设计" />
      <el-step title="流程设计" />
    </el-steps>

    <div class="step-content">
      <!-- 基础信息 -->
      <div v-if="activeStep === 0" class="step-form">
        <el-form
          ref="baseFormRef"
          :model="baseForm"
          label-width="120px"
          class="form"
        >
          <el-form-item
            label="流程名称"
            prop="flowName"
            :rules="[{ required: true, message: '请输入流程名称', trigger: 'blur' }]"
          >
            <el-input
              v-model="baseForm.flowName"
              placeholder="请输入流程名称"
              :disabled="readOnly"
            />
          </el-form-item>

          <el-form-item
            label="唯一标识"
            prop="uniqueId"
            :rules="[
              { required: true, message: '请输入唯一标识', trigger: 'blur' },
              { pattern: /^[a-zA-Z0-9_-]+$/, message: '唯一标识只能包含字母、数字、下划线和减号', trigger: 'blur' }
            ]"
          >
            <el-input
              v-model="baseForm.uniqueId"
              placeholder="请输入唯一标识（只能包含字母、数字、下划线和减号）"
              :disabled="readOnly || hasFlowId"
            />
          </el-form-item>

          <el-form-item label="流程描述">
            <el-input
              v-model="baseForm.description"
              placeholder="请输入流程描述"
              type="textarea"
              :rows="3"
              :disabled="readOnly"
            />
          </el-form-item>

          <el-form-item label="版本号">
            <el-input
              v-model="baseForm.version"
              placeholder="请输入版本号"
              :disabled="readOnly"
            />
          </el-form-item>

          <el-form-item label="是否启用">
            <el-switch
              v-model="baseForm.status"
              :disabled="readOnly"
              active-text="启用"
              inactive-text="停用"
            />
          </el-form-item>
        </el-form>
      </div>

      <!-- 表单设计 -->
      <div v-if="activeStep === 1" class="step-form">
        <el-form
          ref="formDesignRef"
          :model="formDesign"
          label-width="120px"
          class="form"
        >
          <el-form-item
            label="表单名称"
            prop="formName"
            :rules="[{ required: true, message: '请输入表单名称', trigger: 'blur' }]"
          >
            <el-input
              v-model="formDesign.formName"
              placeholder="请输入表单名称"
              :disabled="readOnly"
            />
          </el-form-item>

          <el-form-item
            label="表单JSON"
            prop="formJson"
            :rules="[{ required: true, message: '请输入表单JSON', trigger: 'blur' }]"
          >
            <el-input
              v-model="formDesign.formJson"
              placeholder="请输入表单JSON配置"
              type="textarea"
              :rows="10"
              :disabled="readOnly"
            />
          </el-form-item>

          <el-form-item label="表单描述">
            <el-input
              v-model="formDesign.formDescription"
              placeholder="请输入表单描述"
              type="textarea"
              :rows="3"
              :disabled="readOnly"
            />
          </el-form-item>
        </el-form>
      </div>

      <!-- 流程设计 -->
      <div v-if="activeStep === 2" class="step-form">
        <el-form
          ref="flowDesignRef"
          :model="flowDesign"
          label-width="120px"
          class="form"
        >
          <el-form-item
            label="流程JSON"
            prop="flowJson"
            :rules="[{ required: true, message: '请输入流程JSON', trigger: 'blur' }]"
          >
            <el-input
              v-model="flowDesign.flowJson"
              placeholder="请输入流程JSON配置"
              type="textarea"
              :rows="15"
              :disabled="readOnly"
            />
          </el-form-item>

          <el-form-item label="流程描述">
            <el-input
              v-model="flowDesign.flowDescription"
              placeholder="请输入流程描述"
              type="textarea"
              :rows="3"
              :disabled="readOnly"
            />
          </el-form-item>
        </el-form>
      </div>
    </div>

    <div class="step-buttons" v-if="!readOnly">
      <el-button @click="handleCancel" v-if="activeStep > 0">上一步</el-button>
      <el-button type="primary" @click="handleNext" v-if="activeStep < 2">下一步</el-button>
      <el-button type="primary" @click="handleSave" v-if="activeStep === 2">保存</el-button>
      <el-button type="success" @click="handlePublish" v-if="activeStep === 2">发布</el-button>
      <el-button @click="$emit('cancel')">取消</el-button>
    </div>

    <div class="step-buttons" v-else>
      <el-button @click="$emit('cancel')">关闭</el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { addAgentFlow, getAgentFlowDetail } from '../../api/agentFlow';

const props = defineProps<{
  flowId?: string;
  readOnly?: boolean;
}>();

const emit = defineEmits<{
  (e: 'success'): void;
  (e: 'cancel'): void;
}>();

const activeStep = ref(0);
const baseFormRef = ref<any>(null);
const formDesignRef = ref<any>(null);
const flowDesignRef = ref<any>(null);

const hasFlowId = computed(() => !!props.flowId);

// 基础信息表单
const baseForm = ref({
  flowName: '',
  uniqueId: '',
  description: '',
  version: '1.0',
  status: true
});

// 表单设计
const formDesign = ref({
  formName: '',
  formJson: '',
  formDescription: ''
});

// 流程设计
const flowDesign = ref({
  flowJson: '',
  flowDescription: ''
});

// 获取智能体流程详情
const getAgentFlowDetailInfo = async () => {
  if (!props.flowId) return;

  try {
    const response = await getAgentFlowDetail(props.flowId);
    if (response.code === 200) {
      const data = response.data;
      // 填充基础信息
      baseForm.value = {
        flowName: data.flowName || '',
        uniqueId: data.uniqueId || '',
        description: data.description || '',
        version: data.version || '1.0',
        status: data.status === 1
      };
      // 填充表单设计
      formDesign.value = {
        formName: data.formName || '',
        formJson: data.formJson || '',
        formDescription: data.formDescription || ''
      };
      // 填充流程设计
      flowDesign.value = {
        flowJson: data.flowJson || '',
        flowDescription: data.flowDescription || ''
      };
    } else {
      ElMessage.error('获取智能体流程详情失败：' + response.message);
    }
  } catch (error) {
    ElMessage.error('获取智能体流程详情失败');
    console.error('获取智能体流程详情失败：', error);
  }
};

// 验证当前步骤
const validateCurrentStep = async () => {
  let isValid = false;
  switch (activeStep.value) {
    case 0:
      isValid = await baseFormRef.value?.validate();
      break;
    case 1:
      isValid = await formDesignRef.value?.validate();
      break;
    case 2:
      isValid = await flowDesignRef.value?.validate();
      break;
    default:
      isValid = true;
  }
  return isValid;
};

// 下一步
const handleNext = async () => {
  const isValid = await validateCurrentStep();
  if (isValid) {
    activeStep.value++;
  }
};

// 上一步
const handleCancel = () => {
  activeStep.value--;
};

// 保存（草稿）
const handleSave = async () => {
  await submitForm(false);
};

// 发布
const handlePublish = async () => {
  await submitForm(true);
};

// 提交表单
const submitForm = async (isPublish: boolean) => {
  const isValid = await validateCurrentStep();
  if (!isValid) return;

  try {
    const formData = {
      id: props.flowId,
      flowName: baseForm.value.flowName,
      uniqueId: baseForm.value.uniqueId,
      description: baseForm.value.description,
      version: baseForm.value.version,
      status: baseForm.value.status ? 1 : 0,
      formName: formDesign.value.formName,
      formJson: formDesign.value.formJson,
      formDescription: formDesign.value.formDescription,
      flowJson: flowDesign.value.flowJson,
      flowDescription: flowDesign.value.flowDescription,
      isPublish: isPublish
    };

    const response = await addAgentFlow(formData);
    if (response.code === 200) {
      ElMessage.success(isPublish ? '智能体流程发布成功' : '智能体流程保存成功');
      emit('success');
    } else {
      ElMessage.error('操作失败：' + response.message);
    }
  } catch (error: any) {
    ElMessage.error('操作失败：' + (error.message || '未知错误'));
    console.error('提交智能体流程失败：', error);
  }
};

onMounted(() => {
  if (props.flowId) {
    getAgentFlowDetailInfo();
  }
});
</script>

<style scoped>
.agent-flow-create {
  padding: 20px;
}

.steps {
  margin-bottom: 30px;
}

.step-content {
  min-height: 400px;
  padding: 20px;
  background: #f5f7fa;
  border-radius: 8px;
}

.step-form {
  max-height: 500px;
  overflow-y: auto;
}

.form {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
}

.step-buttons {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 30px;
  padding-top: 20px;
  border-top: 1px solid #e4e7ed;
}
</style>
